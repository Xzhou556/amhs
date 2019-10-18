package amhs.amhs.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class JWTFilter extends BasicHttpAuthenticationFilter {
    private static final Logger LOG = Logger.getLogger(JWTFilter.class);
    // 登录标识
    private static String LOGIN_SIGN = "Authorization";
    /**
     * 检测用户是否登录
     * 检测header里面是否包含Authorization字段即可
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req= (HttpServletRequest) request;
        String authorization = req.getHeader(LOGIN_SIGN);
        return  authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            HttpServletRequest req= (HttpServletRequest) request;
            String header = req.getHeader(LOGIN_SIGN);
            JWTToken token = new JWTToken(header);
            getSubject(request,response).login(token);
            LOG.info("JWT验证用户信息成功");
            return  true;
        }catch (Exception e){
            /**
             * 原生的shiro验证失败会进入全局异常 但是 和JWT结合以后却不进入了  之前一直想不通
             *   原因是 JWT直接在过滤器里验证  验证成功与否 都是直接返回到过滤器中 成功在进入controller
             *    失败直接返回进入springboot自定义异常处理页面
             */
            JSONObject json= new JSONObject();
            json.put("result","401");
            json.put("resultCode","token无效，请重新获取。");
            json.put("resultData","null");
            PrintWriter out = null;
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            LOG.info("返回是");
            LOG.info(json.toString());
            out = httpServletResponse.getWriter();
            out.append(json.toString());

        }
        return false;

    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request,response);
            }catch (Exception e){
                LOG.error(e.getMessage(),e.getCause()); //throw new AuthorizationException("权限不足",e);
            }

        }
        return  true;
    }
    /**
     * 对跨域提供支持
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception{
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return  false;
        }
        return  super.preHandle(request,response);
    }
    /**
     * 将非法请求跳转到 /401
     */

    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/401");
        } catch (IOException e) {
            e.getMessage();
        }
    }

}

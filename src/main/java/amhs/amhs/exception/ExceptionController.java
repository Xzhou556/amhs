package amhs.amhs.exception;

import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {

    /**
     * 信息无法读取
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult handleHttpMessageNotReadableException(Exception e){
        e.printStackTrace();
        return new ResultGenerator().getFailResult("400","无法获取");

    }

    /**
     * 处理参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult handleMethodArgumentNotValidException(Exception e){
        return new ResultGenerator().getFailResult("400","参数验证失败");
    }



    /**
     * 数学计算错误
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult handleArithmeticException(ArithmeticException e){
        return new ResultGenerator().getFailResult("500","服务器内部错误");
    }

    /**
     * 登陆错误
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResult handleAuthenticationException(AuthenticationException e){

        return new ResultGenerator().getFailResult("401","登陆错误");
    }

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResult handleUnknownAccountException(UnknownAccountException e){

        return new ResultGenerator().getFailResult("401","请登录");
    }


    /**
     * 没有权限——shiro
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RestResult handleUnauthorizedException(UnauthorizedException e){
        return new ResultGenerator().getFailResult("403","没有权限");
    }
}

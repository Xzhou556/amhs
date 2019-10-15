package amhs.amhs.shiro;

import amhs.amhs.dao.RoleMenuDao;
import amhs.amhs.entity.RoleMenu;
import amhs.amhs.entity.UserInfo;
import amhs.amhs.service.UserInfoService;
import amhs.amhs.utils.JWTToken;
import amhs.amhs.utils.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RoleMenuDao roleMenuDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = JWTUtil.getUsername(principals.toString());
        UserInfo userInfo = userInfoService.findByAccount(account);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<RoleMenu> byRole = roleMenuDao.findByRole(userInfo.getRole());
        for (RoleMenu roleMenu : byRole) {
            info.addStringPermission(roleMenu.getMenu().getPeimissions());
        }
        Set<String> role = new HashSet<>();
        role.add(userInfo.getRole().getName());
        info.setRoles(role);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String tokenn = (String) token.getCredentials();
        String username = JWTUtil.getUsername(tokenn);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        UserInfo byAccount = userInfoService.findByAccount(username);
        if (byAccount != null) {
            // 密码验证
            if (!JWTUtil.verify(tokenn, username, byAccount.getPassword())) {
                throw new AuthenticationException("Username or password error");
            }

            return new SimpleAuthenticationInfo(tokenn, tokenn, "realm");
        } else {
            throw new UnknownAccountException("900");
        }


    }

    /**
     * 使用JWT替代原生Token
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

}

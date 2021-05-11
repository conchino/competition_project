package com.competition.project.config;

import com.competition.project.entity.LoginAccount;
import com.competition.project.service.LoginAccountService;
import com.competition.project.service.PermissionService;
import com.competition.project.utils.MD5SaltUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;


public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private LoginAccountService loginAccountService;
    @Autowired
    private PermissionService permissionService;

    /* 用户授权 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-------用户授权--------");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo SAInfo = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        System.out.println("username:  "+username);
        // 根据登陆账号获取权限
        String perm = permissionService.getPermNameById(loginAccountService.getLoginByAccount(username).getPerm());
        System.out.println("permission:  "+perm);
        // 添加权限
        switch (perm) {
            case "权限1" -> stringSet.add("user:perm_1");
            case "权限2" -> stringSet.add("user:perm_2");
            case "权限3" -> stringSet.add("user:perm_3");
        }
        System.out.println("stringSet:   "+stringSet);
        SAInfo.setStringPermissions(stringSet);
        return SAInfo;
    }

    /**
     * 获取即将需要认证的信息
     * @return
     */
    @Override
    protected SimpleAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证--------");
        // 从客户端接收的用户名和密码
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        System.out.println("获得username:  "+userName+"   "+"获得password:   "+userPwd);
        // 判断账号是否存在
        if (userName == null) {
            throw new AccountException("用户名不正确");
        }else if (loginAccountService.judgeAccountByCode(userName)){
            throw new UnknownAccountException("该账号不存在");
        }
        //根据账号从数据库获取对象
        LoginAccount loginAccount = loginAccountService.getLoginByAccount(userName);
        // 获取密码和盐
        String password = loginAccount.getPassword();
        String salt = loginAccount.getSalt();
        if (loginAccount.getFeasible()==0){
            throw new AccountException("该账号尚不可使用!");
        }
        // 使用自定义加密工具验证密码
        if (!MD5SaltUtil.checkCode(userPwd,password,salt)) {
            throw new IncorrectCredentialsException("密码不正确");
        }
        return new SimpleAuthenticationInfo(userName, userPwd, getName());
    }

}


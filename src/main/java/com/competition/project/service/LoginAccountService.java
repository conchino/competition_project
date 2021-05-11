package com.competition.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.competition.project.entity.LoginAccount;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  登录账号Service类
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
public interface LoginAccountService extends IService<LoginAccount> {
    /* 根据账号获取工号 */
    String getWorkIdByAccount(String account);
    /* 查询所有账号 */
    List<LoginAccount> queryAllAccount();
    /* 分页查询账号 */
    Map<String,Object> queryAccountsByPage(long current,long limit);
    /* 根据权限查找账号 */
    List<LoginAccount> queryAccountsByPerm(int perm);
    /* 根据工号查询账号 */
    String queryAccountByWorkId(String workId);
    /* 根据工号获取权限 */
    String getPermByWorkId(String workId);
    /* 根据账号号码查询账号对象 */
    LoginAccount getLoginByAccount(String account);
    /* 查看有无工号对应账号存在 */
    boolean notExistAccount(String workId);
    /* 查看有无号码对应账号存在 */
    Boolean judgeAccountByCode(String code);
    /* 申请账号 */
    Boolean applyLoginAccount(String workId,String password,Integer perm);
    /* 查询账号状态 */
    String checkAccountStatus(String account);
    /* 查找待审核账号，权限。使用者工号 */
    List<Map<String,Object>> queryPendingAccounts();
    /* 分页查询待审核账号 */
    List<Map<String, Object>> queryPendingAccounts(Long current, Long limit);
    /* 审核通过操作 */
    boolean toExamineAccount(String account);
    /* 审核不通过 */
    boolean notAllowAccount(String account);
    /* 根据账号获取权限 */
    Integer getPermissionByAccount(String account);
    /* 统计已分配账号占比 */
    Float countAssignAccount();
    /* 获取上一次登陆时间 */
    Date LastLoginTime(String account);
    /* 登陆更新时间 */
    Boolean updateLoginTime(String account);
}

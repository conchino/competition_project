package com.competition.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.competition.project.entity.LoginAccount;
import com.competition.project.mapper.LoginAccountMapper;
import com.competition.project.service.LoginAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.competition.project.utils.IDUtils;
import com.competition.project.utils.MD5SaltUtil;
import com.competition.project.utils.SaltUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@Service
public class LoginAccountServiceImpl extends ServiceImpl<LoginAccountMapper, LoginAccount> implements LoginAccountService {
    @Autowired
    @Lazy
    private LoginAccountService loginAccountService;

    @Override
    public String getWorkIdByAccount(String account) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.select("user_work_id").eq("account",account);
        LoginAccount loginAccount = loginAccountService.getOne(wrapper);
        return loginAccount.getUserWorkId();
    }

    @Override
    public List<LoginAccount> queryAllAccount() {
        return loginAccountService.list();
    }

    @Override
    public Map<String, Object> queryAccountsByPage(long current, long limit) {
        Page<LoginAccount> accountPage = new Page<>(current,limit);
        Page<LoginAccount> resultPage = loginAccountService.page(accountPage, null);
        long total = resultPage.getTotal();
        List<LoginAccount> records = resultPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("records",records);
        return map;
    }

    @Override
    public List<LoginAccount> queryAccountsByPerm(int perm) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("perm",perm);
        List<LoginAccount> accountList = loginAccountService.list(wrapper);
        return accountList;
    }

    @Override
    public String queryAccountByWorkId(String workId) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.select("account")
               .eq("user_work_id",workId);
        LoginAccount loginAccount = loginAccountService.getOne(wrapper);
        return loginAccount.getAccount();
    }

    @Override
    public String getPermByWorkId(String workId) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.select("perm").eq("user_work_id",workId);
        LoginAccount account = loginAccountService.getOne(wrapper);
        return account.getPerm().toString();
    }

    @Override
    public LoginAccount getLoginByAccount(String account) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.select("account","password","salt","perm","user_work_id","feasible")
                .eq("account",account);
        return loginAccountService.getOne(wrapper);
    }

    @Override
    public boolean notExistAccount(String workId) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("user_work_id",workId);
        int count = loginAccountService.count(wrapper);
        return !(count>0);
    }

    @Override
    public Boolean judgeAccountByCode(String code) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("account",code);
        int count = loginAccountService.count(wrapper);
        return !(count>0);
    }

    @Override
    public Boolean applyLoginAccount(String workId, String password, Integer perm) {
                // 如果同意申请，获取随机盐，将密码加密
                String salt = SaltUtils.getSalt();
                // 将密码加盐加密，得到密钥
                String key = MD5SaltUtil.createCode(password+salt);
                // 生成时间戳随机账号
                String account = IDUtils.getID();
                // 将以上用户信息插入到账号表中，设置该账号为不可用(feasible = 0)
                return loginAccountService.save(new LoginAccount(account, key, salt, perm, workId, 0));
    }

    @Override
    public String checkAccountStatus(String account) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.select("feasible").eq("account",account);
        LoginAccount loginAccount = loginAccountService.getOne(wrapper);
        if (loginAccount==null) return "账号不存在";
        return (loginAccount.getFeasible()==0)?"账号不可用":"账号可用";
    }

    @Override
    public List<Map<String,Object>> queryPendingAccounts() {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        // 查找账号，权限。使用者工号
        wrapper.select("account","perm","user_work_id");
        wrapper.eq("feasible",0);   // 查询feasible为0的不可用账号
        List<LoginAccount> accountList = loginAccountService.list(wrapper);
        List<Map<String,Object>> infoList = new ArrayList<>();
        for (LoginAccount loginAccount : accountList) {
            Map<String, Object> map = new HashMap<>();
            map.put("account",loginAccount.getAccount());
            map.put("perm",loginAccount.getPerm());
            map.put("workId",loginAccount.getUserWorkId());
            infoList.add(map);
        }
        return infoList;
    }

    @Override
    public List<Map<String, Object>> queryPendingAccounts(Long current, Long limit) {
        Page<LoginAccount> page = new Page<>(current,limit);
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        // 查找账号，权限。使用者工号
        wrapper.select("account","perm","user_work_id");
        wrapper.eq("feasible",0);   // 查询feasible为0的不可用账号
        Page<LoginAccount> loginAccountPage = loginAccountService.page(page, wrapper);
        List<LoginAccount> accountList = loginAccountPage.getRecords();
        List<Map<String,Object>> infoList = new ArrayList<>();
        for (LoginAccount loginAccount : accountList) {
            Map<String, Object> map = new HashMap<>();
            map.put("account",loginAccount.getAccount());
            map.put("perm",loginAccount.getPerm());
            map.put("workId",loginAccount.getUserWorkId());
            infoList.add(map);
        }
        return infoList;
    }

    @Override
    public boolean toExamineAccount(String account) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("account",account);
        LoginAccount loginAccount = loginAccountService.getOne(wrapper);
        // 将账号设为可用
        loginAccount.setFeasible(1);
        return loginAccountService.update(loginAccount, wrapper);
    }

    @Override
    public boolean notAllowAccount(String account) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("account",account);
        LoginAccount loginAccount = loginAccountService.getOne(wrapper);
        // 设为2，审核不通过
        loginAccount.setFeasible(2);
        return loginAccountService.update(loginAccount, wrapper);
    }

    @Override
    public Integer getPermissionByAccount(String account) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("account",account);
        LoginAccount loginAccount = loginAccountService.getOne(wrapper);
        return loginAccount.getPerm();
    }

    @Override
    public Float countAssignAccount() {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("feasible",1);
        // 使用BigDecimal 对象取小数点后两位值
        BigDecimal bigDecimal = BigDecimal.valueOf((float) loginAccountService.count(wrapper) / (float) loginAccountService.count());
        // 取两位小数，并设置舍入模式    RoundingMode.HALF_UP: 四舍五入
        return bigDecimal.setScale(3, RoundingMode.HALF_UP).floatValue();
    }

    @Override
    public Date LastLoginTime(String account) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.select("update_time").eq("account",account);
        return loginAccountService.getOne(wrapper).getUpdateTime();
    }

    /* 更新登陆时间 */
    @Override
    public Boolean updateLoginTime(String account) {
        QueryWrapper<LoginAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("account",account);
        return loginAccountService.update(new LoginAccount(new Date()),wrapper);
    }


}

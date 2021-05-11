package com.competition;

import com.competition.project.entity.LoginAccount;
import com.competition.project.service.EmployeesService;
import com.competition.project.service.LoginAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


@SpringBootTest
class CompetitionProjectApplicationTests {
    @Autowired
    private EmployeesService employeesService;
    @Autowired
    private LoginAccountService loginAccountService;

    @Test
    void contextLoads() {
        employeesService.selectAllEmployees().forEach(System.out::println);
    }

    @Test
    public void queryAllAccount(){  //查找所有账号
        loginAccountService.queryAllAccount().forEach(System.out::println);
    }

    @Test
    public void queryAccountByPage(){
        Map<String, Object> map = loginAccountService.queryAccountsByPage(1, 5);
        long total = (long) map.get("total");
        List<LoginAccount> records = (List<LoginAccount>) map.get("records");
        System.out.println("总记录:  "+total);
        System.out.println("记录:  ");
        records.forEach(System.out::println);
    }

    @Test
    public void selectEmployeesById(){
        if (employeesService.selectEmployeesById("123654")!=null){
            System.out.println("查询结果不为空！");
        }
        System.out.println(employeesService.selectEmployeesById("123654"));
    }

    @Test
    public void isExistEmployees(){ // 根据工号判断是否存在该员工
        System.out.println(employeesService.isExistEmployees("123654"));
    }

    @Test
    public void applyLoginAccount(){
        if (loginAccountService.applyLoginAccount("123654","123456",2)) {
            System.out.println("申请成功 !!!");
        }else System.out.println("申请失败 !!!");
    }

    @Test
    public void queryAccountByWorkId(){
        System.out.println(loginAccountService.queryAccountByWorkId("123654"));
    }

    @Test
    public void applyLoginAccountController(){
        String workId = "165151";
        String password = "1236541233";
        int perm = 2;
        // 首先根据工号查询员工，判断是否存在该工号对应的员工
        if (employeesService.isExistEmployees(workId)) {
            // 再判断该员工是否已存在账号
            if (loginAccountService.notExistAccount(workId)) {
                if (loginAccountService.applyLoginAccount(workId,password,perm)) {
                    String account = loginAccountService.queryAccountByWorkId(workId);
                    Map<String, Object> map = new HashMap<>();
                    map.put("account",account);
                    System.out.println("账号创建成功 !");
                    return;
                }
                System.out.println("账号创建失败 !");
                return;
            }
            System.out.println("该用户已存在账号 !");
            return;
        }
        System.out.println("该工号不存在 !");
    }

    @Test
    public void queryPendingAccounts(){
        List<Map<String, Object>> mapList = loginAccountService.queryPendingAccounts();
        System.out.println(mapList.size());
        System.out.print("account    perm   workId  \n");
        for (Map<String, Object> map : mapList) {
            System.out.print(map.get("account")+" -- ");
            System.out.print(map.get("perm")+" -- ");
            System.out.print(map.get("workId")+"\n");
        }
    }

   @Test
    public void toExamineAccount(){
       if (loginAccountService.toExamineAccount("293739416")) {
           System.out.println("审核完成 ！");
       }else System.out.println("审核失败 ！");
   }

   @Test
   public void notAllowAccount(){
       if (loginAccountService.notAllowAccount("293739416")) {
           System.out.println("审核完成 ！");
       }else System.out.println("审核失败 ！");
   }

   @Test
   public void checkAccountStatus(){
       System.out.println(loginAccountService.checkAccountStatus("293739416"));
   }
}

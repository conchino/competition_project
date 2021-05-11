package com.competition;

import com.competition.project.service.LoginAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;

@SpringBootTest
public class LoginAccountTest {
    @Autowired
    private LoginAccountService loginAccountService;

    @Test
    public void countAssignAccount(){
        Float ratio = loginAccountService.countAssignAccount();
        System.out.println(ratio*100+"%");
        String strRatio = String.valueOf(ratio*100);
        strRatio = strRatio.substring(0,strRatio.indexOf("."));
        System.out.println(strRatio+"%");
    }

    @Test
    public void updateLoginTime(){
        System.out.println(loginAccountService.updateLoginTime("293739416"));
    }

    @Test
    public void LastLoginTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(loginAccountService.LastLoginTime("293739416")));
    }
}

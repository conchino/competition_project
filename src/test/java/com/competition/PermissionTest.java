package com.competition;

import com.competition.project.service.LoginAccountService;
import com.competition.project.service.PermissionService;
import com.competition.project.dto.PermissionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class PermissionTest {
    @Autowired
    private LoginAccountService loginAccountService;
    @Autowired
    private PermissionService permissionService;

    @Test
    public void getPermNameByAccount(){
        System.out.println(permissionService.getPermNameById(loginAccountService.getLoginByAccount("293739416").getPerm()));
    }

    @Test
    public void getAllPermission(){
        List<PermissionDTO> permissionDTOList = permissionService.getPermissionList();
        System.out.println("permissionList的类:  "+ permissionDTOList.getClass().toString().substring(permissionDTOList.getClass().toString().lastIndexOf(".")+1));

        // 使用迭代器遍历 ArrayList
        Iterator<PermissionDTO> iterator = permissionDTOList.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}

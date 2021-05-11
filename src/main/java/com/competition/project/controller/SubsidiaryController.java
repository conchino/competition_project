package com.competition.project.controller;

import com.competition.project.annotation.Log;
import com.competition.project.dto.SubsidiaryDTO;
import com.competition.project.entity.Department;
import com.competition.project.entity.Position;
import com.competition.project.enumeration.OperationType;
import com.competition.project.service.DepartmentService;
import com.competition.project.service.PositionService;
import com.competition.project.service.SubsidiaryService;
import com.competition.project.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/project/subsidiary")
public class SubsidiaryController {
    @Autowired
    private SubsidiaryService subsidiaryService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PositionService positionService;


    @RequiresPermissions("user:perm_1")
    @RequestMapping(value = "/querySubByPage/{current}/{limit}",method = RequestMethod.GET)
    public Result querySubByPage(@PathVariable("current")long current,
                                 @PathVariable("limit")long limit){
        Map<String, Object> subByPage = subsidiaryService.querySubByPage(current, limit);
        return Result.ok().data(subByPage);
    }


    @RequestMapping(value = "/getAllSubsidiary",method = RequestMethod.GET)
    public Result getAllSubsidiary(){
        List<SubsidiaryDTO> allSubsidiary = subsidiaryService.getAllSubsidiary();
        Integer subsidiaryCount = subsidiaryService.getSubsidiaryCount();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",subsidiaryCount);
        map.put("records",allSubsidiary);
        return Result.ok().data(map);
    }

    @ApiOperation("添加公司")
    @RequiresPermissions("user:perm_1")
    @RequestMapping(value = "/addSubsidiary",method = RequestMethod.GET)
    public Result addSubsidiary(@RequestParam("subId") String subId,@RequestParam("subName") String subName){
//    public Result addSubsidiary(@RequestBody JSONObject jsonObject){
//        String subId = (String) jsonObject.get("subId");
//        String subName = (String) jsonObject.get("subName");

        if (subsidiaryService.isExistSub(subId)) {
            return Result.error().message("该公司序号已存在!");
        }
        if (subsidiaryService.addSubsidiary(subId,subName)) {
            return Result.ok().message("创建成功!");
        }else return Result.error().message("创建失败!");
    }

    @Log(operationType = OperationType.DELETE, operationName = "删除公司及其下部门职位")
    @ApiOperation("删除公司并递归删除其下部门职位")
    @RequiresPermissions("user:perm_1")
    @RequestMapping(value = "/delSubById/{subId}",method = RequestMethod.GET)
    public Result delSubById(@PathVariable("subId") String subId){

        if (subsidiaryService.isExistSub(subId)) {
            List<Department> departmentList = departmentService.queryDepartBySub(subId);
            for (Department department : departmentList) {
                List<Position> positionList = positionService.queryPostByDepart(department.getDepartId());
                for (Position position : positionList) {
                    if (!positionService.delPost(position.getPostId())) {
                        return Result.error().message(position.getPostName()+" 删除失败!");
                    }
                }
                if (!departmentService.delDepartment(department.getDepartId())) {
                    return Result.error().message(department.getDepartName()+" 删除失败!");
                }
            }
            if (subsidiaryService.delSubById(subId)) {
                return Result.ok().message("公司"+subId+" 删除成功!");
            }else Result.error().message("删除失败!");
        }
        return Result.error().message("无对应的公司存在!");
    }

    @Log(operationType = OperationType.UPDATE, operationName = "修改公司信息")
    @ApiOperation("修改公司信息")
    @RequiresPermissions("user:perm_1")
    @RequestMapping("/updateSubInfo")
    public Result updateSubInfo(@RequestParam("upId")String upId,@RequestParam("subId")String subId,@RequestParam("subName")String subName){
        if (subsidiaryService.updateSubInfo(upId,subId,subName)) {
            return Result.ok().message("修改成功!");
        }
        return Result.error().message("修改失败!");
    }


    @RequiresPermissions("user:perm_1")
    @RequestMapping("/queryDepartBySub")
    public Result queryDepartBySub(@RequestParam("belongs") String belongs,@RequestParam("current")long current, @RequestParam("limit")long limit){
        return Result.ok().data(departmentService.queryDepartBySub(belongs,current,limit));
    }

    @Log(operationType = OperationType.SELECT, operationName = "模糊查询公司")
    @RequiresPermissions("user:perm_1")
    @RequestMapping(value = "/querySubsidiaryByInPut/{current}/{limit}", method = RequestMethod.GET)
    public Result querySubsidiaryByInPut(@RequestParam("info") String info,
                                         @PathVariable("current")long current,
                                         @PathVariable("limit")long limit){
        return Result.ok().data(subsidiaryService.querySubsidiaryByInPut(info,current,limit));
    }
}

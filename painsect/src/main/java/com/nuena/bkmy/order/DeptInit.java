package com.nuena.bkmy.order;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.DeptInfo;
import com.nuena.bkmy.facade.DeptInfoFacade;
import com.nuena.bkmy.service.impl.DeptInfoServiceImpl;
import com.nuena.util.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(1)
@Component
public class DeptInit implements ApplicationRunner {

    @Value("${dept.insect.finished}")
    private boolean dept_insect_finished;
    @Autowired
    @Qualifier("deptInfoServiceImpl")
    private DeptInfoServiceImpl deptInfoService;
    @Autowired
    private DeptInfoFacade deptInfoFacade;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (dept_insect_finished) {
            return;
        }
        initData();
    }

    /**
     * 初始化，插入科室信息
     */
    private void initData() {
        List<DeptInfo> saveDeptInfoList = Lists.newArrayList();

        QueryWrapper<DeptInfo> deptInfoQe1 = new QueryWrapper<>();
        deptInfoQe1.isNotNull("parent_dept_id");
        deptInfoFacade.remove(deptInfoQe1);

        QueryWrapper<DeptInfo> deptInfoQe2 = new QueryWrapper<>();
        deptInfoQe2.isNull("parent_dept_id");
        List<DeptInfo> parentDepts = deptInfoFacade.list(deptInfoQe2);
        parentDepts.forEach(parentDept -> {
            String deptDataJson = HttpTool.post("https://www.baikemy.com/disease/department/list/" + parentDept.getDeptId(), "utf-8");
            Map<String, Object> msgMap = (Map) JSON.parse(deptDataJson);
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) msgMap.get("data");
            mapList.forEach(map -> {
                DeptInfo deptInfo = new DeptInfo();
                deptInfo.setDeptId(map.get("dId").toString());
                deptInfo.setDeptName(map.get("name").toString());
                deptInfo.setParentDeptId(parentDept.getDeptId());
                deptInfo.setParentDeptName(parentDept.getDeptName());
                deptInfo.setCreateTime(new Date());
                saveDeptInfoList.add(deptInfo);
            });
        });

        deptInfoService.saveBatch(saveDeptInfoList);
    }

}

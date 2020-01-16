package com.nuena.bkmy.order;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.DeptInfo;
import com.nuena.bkmy.facade.DeptInfoFacade;
import com.nuena.bkmy.service.impl.DeptInfoServiceImpl;
import com.nuena.util.HttpTool;
import com.nuena.util.ListUtil;
import com.nuena.util.StringUtil;
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
import java.util.Random;

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
//        System.out.println(ResourceUtils.getURL("classpath:").getPath());
        if (dept_insect_finished) {
            return;
        }
        initData();
    }

    /**
     * 初始化，插入科室信息
     */
    private void initData() {
        QueryWrapper<DeptInfo> deptInfoQe = new QueryWrapper<>();
        deptInfoFacade.remove(deptInfoQe);

        DeptInfo deptInfo = new DeptInfo();
        deptInfo.setDeptId("0");
        deptInfo.setDeptName("全部");
        deptInfo.setCreateTime(new Date());

        List<DeptInfo> saveDeptInfoList = searchDept(deptInfo, new Random());
        saveDeptInfoList.add(0, deptInfo);

        deptInfoService.saveBatch(saveDeptInfoList);
    }

    /**
     * 递归挖取科室目录
     *
     * @param deptInfo
     * @param rd
     * @return
     */
    private List<DeptInfo> searchDept(DeptInfo deptInfo, Random rd) {
        List<DeptInfo> ret = Lists.newArrayList();

        List<DeptInfo> sonDeptList = getSonDeptByParDept(deptInfo, rd);
        if (ListUtil.isNotEmpty(sonDeptList)) {
            sonDeptList.forEach(sonDept -> {
                ret.addAll(searchDept(sonDept, rd));
            });
            ret.addAll(sonDeptList);
        }

        return ret;
    }

    /**
     * 根据父级部门获取子级部门
     *
     * @param deptInfo
     * @param rd
     * @return
     */
    private List<DeptInfo> getSonDeptByParDept(DeptInfo deptInfo, Random rd) {
        try {
            Thread.sleep(1000 * rd.nextInt(8) + 2000);
        } catch (Exception e) {
        }

        List<DeptInfo> deptInfoList = Lists.newArrayList();
        String deptDataJson = HttpTool.post("https://www.baikemy.com/disease/department/list/" + deptInfo.getDeptId(), "utf-8");
        System.out.println(deptDataJson);
        if (StringUtil.isNotBlank(deptDataJson)) {
            Map<String, Object> msgMap = (Map) JSON.parse(deptDataJson);
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) msgMap.get("data");
            mapList.forEach(map -> {
                DeptInfo sonDeptInfo = new DeptInfo();
                sonDeptInfo.setDeptId(map.get("dId").toString());
                sonDeptInfo.setDeptName(map.get("name").toString());
                sonDeptInfo.setParentDeptId(deptInfo.getDeptId());
                sonDeptInfo.setParentDeptName(deptInfo.getDeptName());
                sonDeptInfo.setCreateTime(new Date());
                deptInfoList.add(sonDeptInfo);
            });
        }
        return deptInfoList;
    }

}
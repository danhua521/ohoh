package com.nuena.jjjk.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.jjjk.entity.JjjkDeptDiseaseMapping;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.service.impl.JjjkDeptInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/2/28 11:03
 */
@Component
public class JjjkDeptInfoFacade extends JjjkDeptInfoServiceImpl {

    @Autowired
    private JjjkDeptDiseaseMappingFacade jjjkDeptDiseaseMappingFacade;

    /**
     * 获取未下载疾病的科室
     *
     * @return
     */
    public List<JjjkDeptInfo> getNoLoadDisDept() {
        QueryWrapper<JjjkDeptInfo> deptInfoQe = new QueryWrapper<>();
        deptInfoQe.isNotNull("parent_dept_id");
        deptInfoQe.ne("parent_dept_id", "0");
        List<JjjkDeptInfo> deptInfoList = list(deptInfoQe);

        QueryWrapper<JjjkDeptDiseaseMapping> deptDiseaseMappingQe = new QueryWrapper<>();
        deptDiseaseMappingQe.select("dept_wz_id");
        List<String> loadDisDeptIdList = jjjkDeptDiseaseMappingFacade.list(deptDiseaseMappingQe).stream().map(i -> i.getDeptWzId()).distinct().collect(Collectors.toList());

        deptInfoList = deptInfoList.stream().filter(i -> !loadDisDeptIdList.contains(i.getDeptId())).collect(Collectors.toList());

        return deptInfoList;
    }

}
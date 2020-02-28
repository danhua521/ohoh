package com.nuena.jjjk.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.service.impl.JjjkDeptInfoServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/2/28 11:03
 */
@Component
public class JjjkDeptInfoFacade extends JjjkDeptInfoServiceImpl {

    /**
     * 获取未下载疾病的科室
     *
     * @return
     */
    public List<JjjkDeptInfo> getNoLoadDisDept() {
        QueryWrapper<JjjkDeptInfo> deptInfoQe = new QueryWrapper<>();
        deptInfoQe.isNotNull("parent_dept_id");
        deptInfoQe.ne("parent_dept_id", "0");
        return list(deptInfoQe);
    }

}
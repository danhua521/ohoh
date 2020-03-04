package com.nuena.jjjk.order;

import com.google.common.collect.Lists;
import com.nuena.jjjk.entity.JjjkBodypart;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.facade.JjjkBodypartFacade;
import com.nuena.jjjk.facade.JjjkDeptInfoFacade;
import com.nuena.jjjk.facade.JjjkSymptomLibFacade;
import com.nuena.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(2)
@Component("jjjkSymptomIdInit")
public class SymptomIdInit implements ApplicationRunner {

    @Value("${jjjk.symptom.id.insect.finished}")
    private boolean symptom_id_insect_finished;
    @Autowired
    private JjjkDeptInfoFacade deptInfoFacade;
    @Autowired
    private JjjkBodypartFacade bodypartFacade;
    @Autowired
    private JjjkSymptomLibFacade symptomLibFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (symptom_id_insect_finished) {
            return;
        }

        List<JjjkDeptInfo> deptInfoList = deptInfoFacade.getNoLoadSymDept();
        List<String> loadedDeptIdList = Lists.newArrayList();
        List<JjjkDeptInfo> noLoadSymDeptList = null;
        while (ListUtil.isNotEmpty(
                noLoadSymDeptList = deptInfoList.stream()
                        .filter(i -> !loadedDeptIdList.contains(i.getDeptId()))
                        .collect(Collectors.toList())
        )) {
            noLoadSymDeptList.forEach(noLoadSymDept -> {
                try {
                    symptomLibFacade.loadSym(noLoadSymDept);
                    loadedDeptIdList.add(noLoadSymDept.getDeptId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        List<JjjkBodypart> bodypartList = bodypartFacade.getNoLoadSymPart();
        List<String> loadedPartIdList = Lists.newArrayList();
        List<JjjkBodypart> noLoadSymPartList = null;
        while (ListUtil.isNotEmpty(
                noLoadSymPartList = bodypartList.stream()
                        .filter(i -> !loadedPartIdList.contains(i.getPartId()))
                        .collect(Collectors.toList())
        )) {
            noLoadSymPartList.forEach(noLoadSymPart -> {
                try {
                    symptomLibFacade.loadSym(noLoadSymPart);
                    loadedPartIdList.add(noLoadSymPart.getPartId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        symptomLibFacade.loadOtherSym();
    }

}
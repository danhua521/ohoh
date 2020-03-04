package com.nuena.jjjk.order;

import com.google.common.collect.Lists;
import com.nuena.jjjk.entity.JjjkBodypart;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.facade.JjjkBodypartFacade;
import com.nuena.jjjk.facade.JjjkDeptInfoFacade;
import com.nuena.jjjk.facade.JjjkDiseaseLibFacade;
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
@Component("jjjkDiseaseIdInit")
public class DiseaseIdInit implements ApplicationRunner {

    @Value("${jjjk.disease.id.insect.finished}")
    private boolean disease_id_insect_finished;
    @Autowired
    private JjjkDeptInfoFacade deptInfoFacade;
    @Autowired
    private JjjkDiseaseLibFacade diseaseLibFacade;
    @Autowired
    private JjjkBodypartFacade bodypartFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (disease_id_insect_finished) {
            return;
        }

        List<JjjkDeptInfo> deptInfoList = deptInfoFacade.getNoLoadDisDept();
        List<String> loadedDeptIdList = Lists.newArrayList();
        List<JjjkDeptInfo> noLoadDisDeptList = null;
        while (ListUtil.isNotEmpty(
                noLoadDisDeptList = deptInfoList.stream()
                        .filter(i -> !loadedDeptIdList.contains(i.getDeptId()))
                        .collect(Collectors.toList())
        )) {
            noLoadDisDeptList.forEach(noLoadDisDept -> {
                try {
                    diseaseLibFacade.loadDis(noLoadDisDept);
                    loadedDeptIdList.add(noLoadDisDept.getDeptId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        List<JjjkBodypart> bodypartList = bodypartFacade.getNoLoadDisPart();
        List<String> loadedPartIdList = Lists.newArrayList();
        List<JjjkBodypart> noLoadDisPartList = null;
        while (ListUtil.isNotEmpty(
                noLoadDisPartList = bodypartList.stream()
                        .filter(i -> !loadedPartIdList.contains(i.getPartId()))
                        .collect(Collectors.toList())
        )) {
            noLoadDisPartList.forEach(noLoadDisPart -> {
                try {
                    diseaseLibFacade.loadDis(noLoadDisPart);
                    loadedPartIdList.add(noLoadDisPart.getPartId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        diseaseLibFacade.loadOtherDis();
    }

}
package com.nuena.jjjk.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuena.jjjk.entity.JjjkBodypart;
import com.nuena.jjjk.entity.JjjkPartDiseaseMapping;
import com.nuena.jjjk.entity.JjjkPartSymptomMapping;
import com.nuena.jjjk.service.impl.JjjkBodypartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/3/4 14:02
 */
@Component
public class JjjkBodypartFacade extends JjjkBodypartServiceImpl {

    @Autowired
    private JjjkPartSymptomMappingFacade jjjkPartSymptomMappingFacade;
    @Autowired
    private JjjkPartDiseaseMappingFacade jjjkPartDiseaseMappingFacade;

    /**
     * 获取未下载疾病的部位
     *
     * @return
     */
    public List<JjjkBodypart> getNoLoadDisPart() {
        QueryWrapper<JjjkBodypart> bodypartQe = new QueryWrapper<>();
        bodypartQe.isNotNull("parent_part_id");
        bodypartQe.ne("parent_part_id", "0");
        List<JjjkBodypart> bodypartList = list(bodypartQe);

        QueryWrapper<JjjkPartDiseaseMapping> partDiseaseMappingQe = new QueryWrapper<>();
        partDiseaseMappingQe.select("part_wz_id");
        List<String> loadDisPartIdList = jjjkPartDiseaseMappingFacade.list(partDiseaseMappingQe).stream().map(i -> i.getPartWzId()).distinct().collect(Collectors.toList());

        bodypartList = bodypartList.stream().filter(i -> !loadDisPartIdList.contains(i.getPartId())).collect(Collectors.toList());

        return bodypartList;
    }

    /**
     * 获取未下载症状的部位
     *
     * @return
     */
    public List<JjjkBodypart> getNoLoadSymPart() {
        QueryWrapper<JjjkBodypart> bodypartQe = new QueryWrapper<>();
        bodypartQe.isNotNull("parent_part_id");
        bodypartQe.ne("parent_part_id", "0");
        List<JjjkBodypart> bodypartList = list(bodypartQe);

        QueryWrapper<JjjkPartSymptomMapping> partSymptomMappingQe = new QueryWrapper<>();
        partSymptomMappingQe.select("part_wz_id");
        List<String> loadSymPartIdList = jjjkPartSymptomMappingFacade.list(partSymptomMappingQe).stream().map(i -> i.getPartWzId()).distinct().collect(Collectors.toList());

        bodypartList = bodypartList.stream().filter(i -> !loadSymPartIdList.contains(i.getPartId())).collect(Collectors.toList());

        return bodypartList;
    }

}
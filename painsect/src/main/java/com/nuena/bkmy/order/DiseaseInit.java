package com.nuena.bkmy.order;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.netflix.discovery.converters.Auto;
import com.nuena.bkmy.entity.DiseaseInfo;
import com.nuena.bkmy.facade.DiseaseInfoFacade;
import com.nuena.bkmy.service.impl.DiseaseInfoServiceImpl;
import com.nuena.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(1)
@Component
public class DiseaseInit implements ApplicationRunner {

    @Autowired
    @Qualifier("diseaseInfoServiceImpl")
    private DiseaseInfoServiceImpl diseaseInfoService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initData();
    }

    private void initData() {
        List<DiseaseInfo> diseaseInfoSaveList = Lists.newArrayList();
        String path = null;
        String fileContent = null;
        Date now = new Date();
        for (int i = 1; i <= 11; i++) {
            path = "C:\\Users\\Administrator\\Desktop\\百科名医网\\疾病json" + i + ".txt";
            fileContent = FileUtil.fileRead(path);
            Map<String, Object> msgMap = (Map) JSON.parse(fileContent);
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) msgMap.get("data");
            mapList.forEach(map -> {
                DiseaseInfo diseaseInfo = new DiseaseInfo();
                diseaseInfo.setDisId(map.get("id").toString());
                diseaseInfo.setDisName(map.get("name").toString());
                diseaseInfo.setCreateTime(now);
                diseaseInfoSaveList.add(diseaseInfo);
            });
        }
        diseaseInfoService.saveBatch(diseaseInfoSaveList.stream().distinct().collect(Collectors.toList()));
    }


}

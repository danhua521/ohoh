package com.nuena.bkmy.order;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.netflix.discovery.converters.Auto;
import com.nuena.bkmy.entity.DiseaseInfo;
import com.nuena.bkmy.facade.DiseaseInfoFacade;
import com.nuena.bkmy.service.impl.DiseaseInfoServiceImpl;
import com.nuena.util.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    @Autowired
    private DiseaseInfoFacade diseaseInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initData();
    }

    /**
     * 初始化，插入疾病信息
     */
    private void initData() {
        QueryWrapper<DiseaseInfo> diseaseInfoQe = new QueryWrapper<>();
        diseaseInfoQe.like("dis_url","/disease/detail");
        diseaseInfoFacade.remove(diseaseInfoQe);

        Date now = new Date();
        List<DiseaseInfo> diseaseInfoList = Lists.newArrayList();
        diseaseInfoList.addAll(getDisFromJbym());

        List<String> disIdList = diseaseInfoList.stream().map(i->i.getDisId()).collect(Collectors.toList());
        for (DiseaseInfo i:getDisFromJsonTxt()){
            if (!disIdList.contains(i.getDisId())){
                diseaseInfoList.add(i);
            }
        }

        diseaseInfoList = diseaseInfoList.stream().distinct().collect(Collectors.toList());
        diseaseInfoList.forEach(i -> {
            i.setCreateTime(now);
        });
        diseaseInfoService.saveBatch(diseaseInfoList);
    }

    /**
     * 从疾病的json文件组中抓取疾病信息
     *
     * @return
     */
    private List<DiseaseInfo> getDisFromJsonTxt() {
        List<DiseaseInfo> diseaseInfoList = Lists.newArrayList();
        String path = null;
        String fileContent = null;
        for (int i = 1; i <= 11; i++) {
            path = "C:\\Users\\Administrator\\Desktop\\百科名医网\\疾病json" + i + ".txt";
            fileContent = FileUtil.fileRead(path);
            Map<String, Object> msgMap = (Map) JSON.parse(fileContent);
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) msgMap.get("data");
            mapList.forEach(map -> {
                DiseaseInfo diseaseInfo = new DiseaseInfo();
                diseaseInfo.setDisId(map.get("id").toString());
                diseaseInfo.setDisName(map.get("name").toString());
                diseaseInfo.setDisUrl("https://www.baikemy.com/disease/detail/" + diseaseInfo.getDisId());
                diseaseInfoList.add(diseaseInfo);
            });
        }
        return diseaseInfoList;
    }

    /**
     * 从疾病的疾病页面文件中抓取疾病信息
     *
     * @return
     */
    private List<DiseaseInfo> getDisFromJbym() {
        List<DiseaseInfo> diseaseInfoList = Lists.newArrayList();
        String html = FileUtil.fileRead("C:\\Users\\Administrator\\Desktop\\百科名医网\\疾病页面.txt");
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String disName = typeInfoLiElement.text();
            if (!disName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String disId = href.substring(16);
                DiseaseInfo diseaseInfo = new DiseaseInfo();
                diseaseInfo.setDisId(disId);
                diseaseInfo.setDisName(disName);
                diseaseInfo.setDisUrl("https://www.baikemy.com" + href);
                diseaseInfoList.add(diseaseInfo);
            }
        });
        return diseaseInfoList;
    }


}

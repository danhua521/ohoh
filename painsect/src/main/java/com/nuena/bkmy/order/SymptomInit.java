package com.nuena.bkmy.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.SymptomInfo;
import com.nuena.bkmy.facade.SymptomInfoFacade;
import com.nuena.bkmy.service.impl.SymptomInfoServiceImpl;
import com.nuena.util.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/14 15:45
 */
@Order(1)
@Component
public class SymptomInit implements ApplicationRunner {

    @Value("${symptom.entry.insect.finished}")
    private boolean symptom_entry_insect_finished;
    @Autowired
    @Qualifier("symptomInfoServiceImpl")
    private SymptomInfoServiceImpl symptomInfoService;
    @Autowired
    private SymptomInfoFacade symptomInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (symptom_entry_insect_finished) {
            return;
        }
        initData();
    }

    /**
     * 初始化，插入症状信息
     */
    private void initData() {
        QueryWrapper<SymptomInfo> symptomInfoQe = new QueryWrapper<>();
        symptomInfoQe.like("sym_url", "/disease/detail");
        symptomInfoFacade.remove(symptomInfoQe);

        List<SymptomInfo> symptomInfoList = getSymFromZzym();

        symptomInfoService.saveBatch(symptomInfoList);
    }

    /**
     * 从症状的症状页面文件中抓取症状信息
     *
     * @return
     */
    private List<SymptomInfo> getSymFromZzym() {
        Date now = new Date();
        List<SymptomInfo> symptomInfoList = Lists.newArrayList();
        String html = FileUtil.fileRead("C:\\Users\\Administrator\\Desktop\\百科名医网\\症状页面.txt");
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String symName = typeInfoLiElement.text();
            if (!symName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String symId = href.substring(16);
                SymptomInfo symptomInfo = new SymptomInfo();
                symptomInfo.setSymId(symId);
                symptomInfo.setSymName(symName);
                symptomInfo.setSymUrl("https://www.baikemy.com" + href);
                symptomInfo.setCreateTime(now);
                symptomInfoList.add(symptomInfo);
            }
        });
        return symptomInfoList;
    }

}
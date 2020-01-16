package com.nuena.bkmy.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.bkmy.entity.ExamineInfo;
import com.nuena.bkmy.facade.ExamineInfoFacade;
import com.nuena.bkmy.service.impl.ExamineInfoServiceImpl;
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
@Order(4)
@Component
public class ExamineInit implements ApplicationRunner {

    @Value("${examine.entry.insect.finished}")
    private boolean examine_entry_insect_finished;
    @Autowired
    @Qualifier("examineInfoServiceImpl")
    private ExamineInfoServiceImpl examineInfoService;
    @Autowired
    private ExamineInfoFacade examineInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (examine_entry_insect_finished) {
            return;
        }
        initData();
    }

    /**
     * 初始化，插入检查信息
     */
    private void initData() {
        QueryWrapper<ExamineInfo> examineInfoQe = new QueryWrapper<>();
        examineInfoQe.like("exa_url", "/disease/detail");
        examineInfoFacade.remove(examineInfoQe);

        List<ExamineInfo> examineInfoList = getExaFromJcym();

        examineInfoService.saveBatch(examineInfoList);
    }

    /**
     * 从检查的检查页面文件中抓取检查信息
     *
     * @return
     */
    private List<ExamineInfo> getExaFromJcym() {
        Date now = new Date();
        List<ExamineInfo> examineInfoList = Lists.newArrayList();
        String html = FileUtil.fileRead("C:\\Users\\Administrator\\Desktop\\百科名医网\\检查页面.txt");
        Document doc = Jsoup.parse(html);
        Element typeInfoElement = doc.getElementsByClass("typeInfo").last();
        Elements typeInfoLiElements = typeInfoElement.getElementsByClass("typeInfo_Li");
        typeInfoLiElements.forEach(typeInfoLiElement -> {
            String exaName = typeInfoLiElement.text();
            if (!exaName.equals("更多")) {
                String href = typeInfoLiElement.select("a").attr("href");
                String exaId = href.substring(16);
                ExamineInfo examineInfo = new ExamineInfo();
                examineInfo.setExaId(exaId);
                examineInfo.setExaName(exaName);
                examineInfo.setExaUrl("https://www.baikemy.com" + href);
                examineInfo.setCreateTime(now);
                examineInfoList.add(examineInfo);
            }
        });
        return examineInfoList;
    }

}
package com.nuena.jjjk.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.jjjk.entity.JjjkBodypart;
import com.nuena.jjjk.facade.JjjkBodypartFacade;
import com.nuena.jjjk.service.impl.JjjkBodypartServiceImpl;
import com.nuena.util.HttpTool;
import com.nuena.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
@Component("jjjkBodypartInit")
public class BodypartInit implements ApplicationRunner {

    @Value("${jjjk.bodypart.insect.finished}")
    private boolean bodypart_insect_finished;
    @Autowired
    @Qualifier("jjjkBodypartServiceImpl")
    private JjjkBodypartServiceImpl bodypartService;
    @Autowired
    private JjjkBodypartFacade bodypartFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (bodypart_insect_finished) {
            return;
        }
        initData();
    }

    /**
     * 初始化，插入科室信息
     */
    private void initData() {
        QueryWrapper<JjjkBodypart> bodypartQe = new QueryWrapper<>();
        bodypartFacade.remove(bodypartQe);

        JjjkBodypart bodypart = new JjjkBodypart();
        bodypart.setPartId("0");
        bodypart.setPartName("全部");
        bodypart.setCreateTime(new Date());

        List<JjjkBodypart> saveBodypartList = searchBodypart();
        saveBodypartList.add(0, bodypart);

        bodypartService.saveBatch(saveBodypartList);
    }

    /**
     * 获取身体部位
     *
     * @return
     */
    private List<JjjkBodypart> searchBodypart() {
        List<JjjkBodypart> bodypartList = Lists.newArrayList();
        String html = HttpTool.get("https://jb.9939.com/jbzz/");
        if (StringUtil.isBlank(html)) {
            return bodypartList;
        }

        Document doc = Jsoup.parse(html);
        Element partListElement = doc.getElementsByClass("part-list").get(0);
        partListElement.select("div").forEach(divElement -> {
            JjjkBodypart bodypart = getBodypartFromA(divElement.selectFirst("a"));
            bodypart.setParentPartId("0");
            bodypart.setParentPartName("全部");
            bodypartList.add(bodypart);
        });

        partListElement.select("dl").forEach(dlElement -> {
            JjjkBodypart bodypart = getBodypartFromA(dlElement.selectFirst("dt").selectFirst("a"));
            bodypart.setParentPartId("0");
            bodypart.setParentPartName("全部");
            bodypartList.add(bodypart);

            dlElement.selectFirst("dd").select("a").forEach(aElement -> {
                JjjkBodypart sonJjjkBodypart = getBodypartFromA(aElement);
                sonJjjkBodypart.setParentPartId(bodypart.getPartId());
                sonJjjkBodypart.setParentPartName(bodypart.getPartName());
                bodypartList.add(sonJjjkBodypart);
            });
        });
        return bodypartList;
    }

    private JjjkBodypart getBodypartFromA(Element aElement) {
        String href = aElement.attr("href");
        String partId = href.substring(25, href.lastIndexOf("/"));
        JjjkBodypart bodypart = new JjjkBodypart();
        bodypart.setPartId(partId);
        bodypart.setPartName(aElement.text());
        bodypart.setCreateTime(new Date());
        return bodypart;
    }

}
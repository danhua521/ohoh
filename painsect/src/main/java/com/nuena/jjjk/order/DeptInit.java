package com.nuena.jjjk.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.jjjk.entity.JjjkDeptInfo;
import com.nuena.jjjk.facade.JjjkDeptInfoFacade;
import com.nuena.jjjk.service.impl.JjjkDeptInfoServiceImpl;
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
@Component("jjjkDeptInit")
public class DeptInit implements ApplicationRunner {

    @Value("${jjjk.dept.insect.finished}")
    private boolean dept_insect_finished;
    @Autowired
    @Qualifier("jjjkDeptInfoServiceImpl")
    private JjjkDeptInfoServiceImpl deptInfoService;
    @Autowired
    private JjjkDeptInfoFacade deptInfoFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (dept_insect_finished) {
            return;
        }
        initData();
    }

    /**
     * 初始化，插入科室信息
     */
    private void initData() {
        QueryWrapper<JjjkDeptInfo> deptInfoQe = new QueryWrapper<>();
        deptInfoFacade.remove(deptInfoQe);

        JjjkDeptInfo deptInfo = new JjjkDeptInfo();
        deptInfo.setDeptId("0");
        deptInfo.setDeptName("全部");
        deptInfo.setCreateTime(new Date());

        List<JjjkDeptInfo> saveDeptInfoList = searchDept();
        saveDeptInfoList.add(0, deptInfo);

        deptInfoService.saveBatch(saveDeptInfoList);
    }

    /**
     * 获取科室
     *
     * @return
     */
    private List<JjjkDeptInfo> searchDept() {
        List<JjjkDeptInfo> deptInfoList = Lists.newArrayList();
        String html = HttpTool.get("https://jb.9939.com/jbzz/");
        if (StringUtil.isBlank(html)) {
            return deptInfoList;
        }

        Document doc = Jsoup.parse(html);
        Element partListElement = doc.getElementsByClass("part-list").get(1);
        partListElement.select("div").forEach(divElement -> {
            JjjkDeptInfo jjjkDeptInfo = getDeptFromA(divElement.selectFirst("a"));
            jjjkDeptInfo.setParentDeptId("0");
            jjjkDeptInfo.setParentDeptName("全部");
            deptInfoList.add(jjjkDeptInfo);
        });

        partListElement.select("dl").forEach(dlElement -> {
            JjjkDeptInfo jjjkDeptInfo = getDeptFromA(dlElement.selectFirst("dt").selectFirst("a"));
            jjjkDeptInfo.setParentDeptId("0");
            jjjkDeptInfo.setParentDeptName("全部");
            deptInfoList.add(jjjkDeptInfo);

            dlElement.selectFirst("dd").select("a").forEach(aElement -> {
                JjjkDeptInfo sonJjjkDeptInfo = getDeptFromA(aElement);
                sonJjjkDeptInfo.setParentDeptId(jjjkDeptInfo.getDeptId());
                sonJjjkDeptInfo.setParentDeptName(jjjkDeptInfo.getDeptName());
                deptInfoList.add(sonJjjkDeptInfo);
            });
        });
        return deptInfoList;
    }

    private JjjkDeptInfo getDeptFromA(Element aElement) {
        String href = aElement.attr("href");
        String deptId = href.substring(25, href.lastIndexOf("/"));
        JjjkDeptInfo deptInfo = new JjjkDeptInfo();
        deptInfo.setDeptId(deptId);
        deptInfo.setDeptName(aElement.text());
        deptInfo.setCreateTime(new Date());
        return deptInfo;
    }

}
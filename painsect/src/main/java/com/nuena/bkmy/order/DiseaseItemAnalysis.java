package com.nuena.bkmy.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nuena.bkmy.entity.DiseaseRawData;
import com.nuena.bkmy.facade.DiseaseInfoFacade;
import com.nuena.bkmy.facade.DiseaseRawDataFacade;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/1/17 17:05
 */
@Order(1600)
@Component
public class DiseaseItemAnalysis implements ApplicationRunner {

    @Autowired
    private DiseaseRawDataFacade diseaseRawDataFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        List<String> list = Lists.newArrayList();
//        QueryWrapper<DiseaseRawData> diseaseRawDataQe = new QueryWrapper<>();
//        diseaseRawDataQe.select("dis_html");
//        diseaseRawDataFacade.list(diseaseRawDataQe).forEach(i->{
//            list.addAll(anaEvery(i.getDisHtml()));
//        });
//
//        System.out.println(list);
//        Map<String, Long> result =
//                list.stream().collect(
//                        Collectors.groupingBy(
//                                Function.identity(), Collectors.counting()
//                        )
//                );
//        System.out.println(result);
    }

    private List<String> anaEvery(String html){
        Document doc1 = Jsoup.parse(html);
        return doc1.getElementsByClass("s_directory_flag").eachText();
    }




}

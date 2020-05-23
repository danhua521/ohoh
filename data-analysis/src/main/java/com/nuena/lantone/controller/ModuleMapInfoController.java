package com.nuena.lantone.controller;


import com.nuena.order.ChangxXmlDataAnalysisFacade;
import com.nuena.order.TaiZhouXmlDataAnalysisFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author rgb
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/moduleInfo")
public class ModuleMapInfoController {

    @Autowired
    private TaiZhouXmlDataAnalysisFacade taiZhouXmlDataAnalysisFacade;
    @Autowired
    private ChangxXmlDataAnalysisFacade changxXmlDataAnalysisFacade;

    @GetMapping("/getModuleMapInfoJson")
    public String getModuleMapInfoJson(String id) {
        if (id.equals("1")) {
            return changxXmlDataAnalysisFacade.getModeMappingInfo();
        } else {
            return taiZhouXmlDataAnalysisFacade.getModeMappingInfo();
        }
    }

}
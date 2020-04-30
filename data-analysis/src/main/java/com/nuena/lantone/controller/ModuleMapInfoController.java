package com.nuena.lantone.controller;


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

    @GetMapping("/getModuleMapInfoJson")
    public String getModuleMapInfoJson() {
        return taiZhouXmlDataAnalysisFacade.getModeMappingInfo();
    }

}

package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.huazo.entity.MrMrcontent;
import com.nuena.huazo.service.impl.MrMrcontentServiceImpl;
import com.nuena.util.GZIPUtils;
import com.nuena.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/4/9 15:42
 */
@Component
public class ChangxFacade {

    @Autowired
    @Qualifier("mrMrcontentServiceImpl")
    private MrMrcontentServiceImpl mrMrcontentService;

    public List<MrMrcontent> getNoJmData() {
        QueryWrapper<MrMrcontent> mrMrcontentQe = new QueryWrapper<>();
        mrMrcontentQe.select("bljlid", "bljlnr");
        mrMrcontentQe.last("where XMLNR is null and ROWNUM<200");
        return mrMrcontentService.list(mrMrcontentQe);
    }

    @Transactional(transactionManager = "db2TransactionManager")
    public void noJmDataToJm(List<MrMrcontent> mrMrcontents) throws Exception {
        List<MrMrcontent> uptMrMrcontents = Lists.newArrayList();
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
        for (MrMrcontent mrMrcontent : mrMrcontents) {
            String xmlnr = GZIPUtils.uncompressToString(mrMrcontent.getBljlnr().getBytes(1, (int) mrMrcontent.getBljlnr().length()));
            MrMrcontent uptMrMrcontent = new MrMrcontent();
            uptMrMrcontent.setBljlid(mrMrcontent.getBljlid());
            if (pattern.matcher(xmlnr).find()) {
                uptMrMrcontent.setXmlnr(xmlnr);
            } else {
                uptMrMrcontent.setXmlnr("无法解密");
            }
            uptMrMrcontents.add(uptMrMrcontent);
        }
        if (ListUtil.isNotEmpty(uptMrMrcontents)) {
            mrMrcontentService.updateBatchById(uptMrMrcontents);
        }
        uptMrMrcontents.clear();
    }

}
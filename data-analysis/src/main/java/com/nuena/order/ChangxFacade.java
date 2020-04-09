package com.nuena.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.nuena.huazo.entity.MrMrcontent;
import com.nuena.huazo.service.impl.MrMrcontentServiceImpl;
import com.nuena.util.GZIPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
    private MrMrcontentServiceImpl mrMrcontentServiceImpl;

    @Transactional(transactionManager = "db2TransactionManager")
    public void dataAw() throws Exception {
        QueryWrapper<MrMrcontent> mrMrcontentQe = new QueryWrapper<>();
        List<MrMrcontent> mrMrcontents = mrMrcontentServiceImpl.list(mrMrcontentQe);
        List<MrMrcontent> uptMrMrcontents = Lists.newArrayList();
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
        int count = 0;
        for (MrMrcontent mrMrcontent : mrMrcontents) {
            try {
                MrMrcontent uptMrMrcontent = new MrMrcontent();
                uptMrMrcontent.setBljlid(mrMrcontent.getBljlid());
                String msg = GZIPUtils.uncompressToString(mrMrcontent.getBljlnr().getBytes(1, (int) mrMrcontent.getBljlnr().length()));
                if (!pattern.matcher(msg).find()) {
                    mrMrcontent.setXmlnr("0");
                    count++;
                } else {
                    mrMrcontent.setXmlnr("1");
                }
            } catch (SQLException E) {
            }

        }

        mrMrcontentServiceImpl.updateBatchById(uptMrMrcontents);
        System.out.println(count);
        System.out.println(mrMrcontents.size());
    }

}
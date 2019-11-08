package com.nuena.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @Description:
 * @author: rengb
 * @time: 2019/11/6 14:51
 */
@Data
public class UserInfoListPageVO extends Page {

    private String wxName;

}

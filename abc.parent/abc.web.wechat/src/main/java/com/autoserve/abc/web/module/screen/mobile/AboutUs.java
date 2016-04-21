package com.autoserve.abc.web.module.screen.mobile;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ArticleInfoDO;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;

public class AboutUs {

    private final static String mobileColumnKey = "About_Us_Mobile"; //手机端关于我们栏目关键字，不能被修改

    @Resource
    private ArticleInfoService  articleInfoService;

    public void execute(Context context, Navigator nav, ParameterParser params) {

        String fromKind = params.getString("fromKind");
        //查询手机文章信息
        PageResult<ArticleInfoDO> pageResult = articleInfoService.queryListByKeyWord(mobileColumnKey,
                new PageCondition());
        context.put("articleInfoDOList", pageResult.getData());
        context.put("fromKind", fromKind);
    }
}

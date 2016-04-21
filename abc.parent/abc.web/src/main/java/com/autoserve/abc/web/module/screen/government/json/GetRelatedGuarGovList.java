package com.autoserve.abc.web.module.screen.government.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.search.GovReviewSearchDO;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.GovProvideGuarState;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;

import javax.annotation.Resource;

/**
 * 获取担保机构列表（启用状态的,且为给定govId的机构做担保的担保机构）
 * （担保机构：abc_government表的是否提供担保字段为1）
 *
 * @author RJQ 2014/12/11 16:56.
 */
public class GetRelatedGuarGovList {
    @Resource
    private GovernmentService govService;

    public JsonPageVO<GovPlainJDO> execute(ParameterParser params) {
        //建立分页查询条件
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        GovReviewSearchDO searchDO = new GovReviewSearchDO();
        searchDO.setGovIsEnable(EntityState.STATE_ENABLE.getState());
        searchDO.setIsOfferGuar(GovProvideGuarState.PROVIDE.getState());
        Integer govId = params.getInt("govId");
        searchDO.setGuareeGovId(govId == 0 ? null : govId);//根据govId查询为此机构做担保的担保机构
        searchDO.setGovName(params.getString("govName"));

        PageResult<GovPlainJDO> pageResult = govService.queryList(searchDO, pageCondition);
        JsonPageVO<GovPlainJDO> pageVO = new JsonPageVO<GovPlainJDO>();
        if (pageResult.getData() != null) {
            pageVO.setRows(pageResult.getData());
            pageVO.setTotal(pageResult.getData().size());
        }
        return pageVO;
    }
}

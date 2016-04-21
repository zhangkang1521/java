/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.RecentDeal;

/**
 * 类BusinessRecordDao.java的实现描述：
 * 
 * @author J.YL 2014年11月21日 下午2:48:53
 */
public interface DealRecordDao extends BaseDao<DealRecordDO, Integer> {
    /**
     * 通过第三方支付的资金支付交易号更新交易记录状态。
     * 
     * @param dealRecordDo
     * @return
     */
    public int updateDealRecordState(DealRecordDO dealRecordDo);
    /**
     * 通过主键更新交易记录状态。
     * 
     * @param dealRecordDo
     * @return
     */
    public int updateDealRecordStateById(DealRecordDO dealRecordDo);

    /**
     * 根据交易流水号查处对应的交易记录
     * 
     * @param innerSeqNo
     * @return
     */
    public List<DealRecordDO> findDealRecordsByInnerSeqNo(String innerSeqNo);

    /**
     * 根据交易流水号和交易类型查处对应的交易记录
     * 
     * @param innerSeqNo
     * @return
     */
    public List<DealRecordDO> findDealRecordsByInnerSeqNoAndType(@Param("innerSeqNo") String innerSeqNo,
                                                                 @Param("drDetailType") int type);

    /**
     * 根据交易流水号查处对应的交易记录
     * 
     * @param innerSeqNo
     * @return
     */
    public int updateDealRecordsByInnerSeqNo(String innerSeqNo);

    /**
     * 根据批量交易流水号查处对应的批量交易记录
     */
    public List<DealRecordDO> findDealRecordsByInnerSeqNos(List<String> seqNos);

    /**
     * 最近交易
     */
    List<RecentDeal> findMyRecentDeal(@Param("userId") String userId);

    /**
     * 根据参数查询交易记录
     */
    List<DealRecordDO> findDealByParams(@Param("dealRecordDo")DealRecordDO dealRecordDo,@Param("pageCondition")PageCondition pageCondition,  
    		@Param("startDate")String startDate,  @Param("endDate")String endDate);
    /**
     * 根据参数查询交易记录
     */
    int countDealByParams(@Param("dealRecordDo")DealRecordDO dealRecordDo, @Param("startDate")String startDate, @Param("endDate")String endDate);
}

/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.entity.BatchPayQuery;
import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.biz.enums.EasyPayTradeState;
import com.autoserve.abc.service.biz.impl.cash.ToCashQueryList;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.easypay.BatchPaySignature;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.easypay.BatchPayUtils;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.google.common.base.Splitter;

/**
 * 提现状态查询定时任务
 *
 * @author J.YL 2014年12月1日 下午2:08:27
 */
public class ToCashJob implements BaseJob {
    //提现状态查询返回数据中提现状态在数据中的index
    public static final int   INDEX = 17;
    @Resource
    private DealRecordService dealRecordService;

    /**
     * 提现结果查询job
     */
    @Override
    public void run() {
        Logger logger = LoggerFactory.getLogger(ToCashJob.class);
        List<BatchPayQuery> list = ToCashQueryList.pollAll();
        List<ToCashNotify> notifyList = new ArrayList<ToCashJob.ToCashNotify>();
        String seqNo = null;
        for (BatchPayQuery query : list) {
            try {
                ToCashNotify notify = new ToCashNotify();
                String result = BatchPayUtils.queryByBatchNO(query);
                if (!result.startsWith("<Resp>")) {
                    Base64 decode = new Base64();
                    byte[] rb = decode.decode(result);
                    result = BatchPaySignature.decrypt(rb);
                }
                Document doc = null;
                doc = DocumentHelper.parseText(result);
                Element root = doc.getRootElement();
                Element batchCurrnum = root.element("batchCurrnum");
                Element batchContent = root.element("batchContent");
                Element detailInfo = batchContent.element("detailInfo");
                String batchCurrString = batchCurrnum.getTextTrim();
                //batchCurrString由seqno和时间组成，将最后8位时间字符串去除
                seqNo = batchCurrString.substring(0, batchCurrString.length() - 8);
                String detailInfoString = detailInfo.getTextTrim();
                List<String> detail = Splitter.on(',').splitToList(detailInfoString);
                String state = detail.get(INDEX);
                //处理成功
                if (state.equals(EasyPayConfig.BatchToCashState.PROCESSED.des)) {
                    notify.setSeqNo(seqNo);
                    notify.setTotalFee(new BigDecimal(detail.get(7)));
                    notify.setTradeStatus(EasyPayTradeState.TRADE_FINISHED.getState());
                    notifyList.add(notify);
                } else if (state.equals(EasyPayConfig.BatchToCashState.EASYPAY_REFUSE.getDes())
                        || state.equals(EasyPayConfig.BatchToCashState.MERCHANT_REFUSE.getDes())) {//处理失败
                    notify.setSeqNo(seqNo);
                    notify.setTotalFee(new BigDecimal(detail.get(7)));
                    notify.setTradeStatus(EasyPayTradeState.TRADE_FAILURE.getState());
                    notifyList.add(notify);
                } else {//处理中 重新放回队列中。
                    ToCashQueryList.add(query);
                }
            } catch (Exception ex) {
                logger.error("ToCashJob，Seq：{}", seqNo, ex);
                //等待下次重试
                ToCashQueryList.add(query);
            }
        }
        for (ToCashNotify toCash : notifyList) {
            dealRecordService.modifyDealRecordState(toCash.seqNo, toCash.tradeStatus, toCash.totalFee);
        }
    }

    public static class ToCashNotify {
        private String     seqNo;
        private String     tradeStatus;
        private BigDecimal totalFee;

        public String getSeqNo() {

            return seqNo;
        }

        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }

        public String getTradeStatus() {
            return tradeStatus;
        }

        public void setTradeStatus(String tradeStatus) {
            this.tradeStatus = tradeStatus;
        }

        public BigDecimal getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(BigDecimal totalFee) {
            this.totalFee = totalFee;
        }

    }
}

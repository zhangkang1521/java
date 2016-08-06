package org.zk.fee;

import java.math.BigDecimal;

/**
 * Created by root on 8/6/16.
 */
public interface FeeService {

    /**
     * this method is write dead
     * @param type
     * @param manageFee
     * @param riskFee
     * @return
     */
    BigDecimal calc(String type, BigDecimal manageFee, BigDecimal riskFee);

    BigDecimal calc(String type, BigDecimal manageFee, BigDecimal riskFee, BigDecimal... fee);
}

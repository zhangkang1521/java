package org.zk.fee;

import java.math.BigDecimal;

/**
 * Created by root on 8/6/16.
 */
public class HouseFeeServiceImpl implements FeeService {
    public BigDecimal calc(String type, BigDecimal manageFee, BigDecimal riskFee) {
        System.out.println("house old");
        return manageFee;
    }

    public BigDecimal calc(String type, BigDecimal manageFee, BigDecimal riskFee, BigDecimal... fee) {
        return this.calc(type, manageFee, riskFee);
    }
}

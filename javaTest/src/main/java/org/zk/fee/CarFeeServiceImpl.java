package org.zk.fee;

import java.math.BigDecimal;

/**
 * Created by root on 8/6/16.
 */
public class CarFeeServiceImpl implements FeeService{

    public BigDecimal calc(String type, BigDecimal manageFee, BigDecimal riskFee) {
        System.out.println("carFee service old");
        return manageFee.add(riskFee);
    }

    public BigDecimal calc(String type, BigDecimal manageFee, BigDecimal riskFee, BigDecimal... fee) {
        System.out.println("carFee new");
        return null;
    }
}

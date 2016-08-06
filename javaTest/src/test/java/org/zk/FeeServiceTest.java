package org.zk;

import org.testng.annotations.Test;
import org.zk.fee.CarFeeServiceImpl;
import org.zk.fee.FeeService;
import org.zk.fee.HouseFeeServiceImpl;

import java.math.BigDecimal;

/**
 * Created by root on 8/6/16.
 */
public class FeeServiceTest {

    FeeService feeService;

    @Test
    public void testCarFee(){
        feeService = new CarFeeServiceImpl();
        System.out.println(feeService.calc("car", new BigDecimal(1), new BigDecimal(2), BigDecimal.TEN));
    }

    @Test
    public void testHourseFee(){
        feeService = new HouseFeeServiceImpl();
        System.out.println(feeService.calc("house", new BigDecimal(1), new BigDecimal(2)));
    }
}

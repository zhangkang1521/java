/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ProductRateDO;
import com.autoserve.abc.dao.dataobject.ProductRateInfoDO;
import com.autoserve.abc.dao.intf.ProductRateDao;

/**
 * 类ProductRateTest.java的实现描述：产品期限利率ＤＯ测试
 * 
 * @author wei.huimin 2014年12月11日 下午6:09:23
 */
public class ProductRateTest extends BaseDaoTest {

    @Resource
    private ProductRateDao dao;

    @Test
    public void findProductAllInfoByProductIdTest() {
        List<ProductRateInfoDO> lstProductRateInfoDO = dao.findProductAllInfoByProductId(2);
        System.out.println("findProductAllInfoByProductIdTest 啊哈哈哈哈   " + JSON.toJSON(lstProductRateInfoDO));
    }

    @Test
    public void findProductAllInfoTest() {
        PageCondition pageCondition = new PageCondition();
        List<ProductRateInfoDO> lstProductRateInfoDO = dao.findProductAllInfo(pageCondition);
        System.out.println("findProductAllInfoTest 啊哈哈哈哈   " + JSON.toJSON(lstProductRateInfoDO));
    }
    
    @Test
    public void findRateCountByParam() {
      ProductRateDO productRateDO = new ProductRateDO();
      productRateDO.setProductId(72);
        int count = dao.findRateCountByParam(productRateDO);
        System.out.println("findRateCountByParam 啊哈哈哈哈   " + count);
    }
    @Test
    public void findAllCountByParamTest() {
      ProductRateInfoDO productRateInfoDO = new ProductRateInfoDO();
      productRateInfoDO.setProductName("haha");
        int count = dao.findCountByParam(productRateInfoDO);
        System.out.println("findAllCountByParamTest 啊哈哈哈哈   " + count);
    }

    @Test
    public void findProductRateInfoListByParam() {
        PageCondition pageCondition = new PageCondition();
        ProductRateInfoDO productRateInfoDO = new ProductRateInfoDO();
        productRateInfoDO.setProductName("test");
        List<ProductRateInfoDO> lstProductRateInfoDO = dao.findProductRateInfoListByParam(productRateInfoDO,
                pageCondition);
        System.out.println("findProductRateInfoListByParam 啊哈哈哈哈   " + lstProductRateInfoDO.size() + " hahah "
                + JSON.toJSON(lstProductRateInfoDO));
    }

    @Test
    public void findByIdTest() {
        ProductRateDO productRateDO = dao.findById(8);
        System.out.println(JSON.toJSON(productRateDO));
    }

    @Test
    public void insertTest() {
        ProductRateDO productRateDO = new ProductRateDO();
        productRateDO.setProductRateId(1);
        productRateDO.setProductId(2);
        productRateDO.setProductRate(new BigDecimal(3));
        productRateDO.setProductMinPeriod(4);
        productRateDO.setProductMaxPeriod(5);
        dao.insert(productRateDO);
    }

    @Test
    public void updateTest() {
        ProductRateDO productRateDO = new ProductRateDO();
        productRateDO.setProductRateId(1);
        productRateDO.setProductId(2);
        productRateDO.setProductRate(new BigDecimal(3));
        productRateDO.setProductMinPeriod(4);
        productRateDO.setProductMaxPeriod(5);

        int flag = dao.update(productRateDO);
        System.out.println("adfadf " + flag);
    }

    @Test
    public void delete() {
        dao.delete(1);
    }

    @Test
    public void deleteByProductId() {
        dao.deleteByProductId(5);
    }

}

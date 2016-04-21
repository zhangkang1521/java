/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao;

import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ProductInfoDO;
import com.autoserve.abc.dao.intf.ProductInfoDao;

/**
 * 类ProductInfoTest.java的实现描述：TODO 类实现描述
 * 
 * @author weihuimin 2014年12月11日 
 */
public class ProductInfoTest extends BaseDaoTest {

    @Resource
    private ProductInfoDao dao;
    
    @Test
    public void findByIdTest() {
    	ProductInfoDO productInfoDO = dao.findById(12345);
        System.out.println(JSON.toJSON(productInfoDO));
    }

    @Test
    public void insertTest() {
    	ProductInfoDO productInfoDO = new ProductInfoDO();
        
        productInfoDO.setProductId(1);
        productInfoDO.setProductName("str2");
        productInfoDO.setProductMark("str3");

        dao.insert(productInfoDO);
    }
    
    @Test
    public void updateTest() {
    	ProductInfoDO productInfoDO = new ProductInfoDO();

        productInfoDO.setProductId(1);
        productInfoDO.setProductName("str2");
        productInfoDO.setProductMark("str3");
        int flag = dao.update(productInfoDO);
        System.out.println("adfadf " + flag);
    }

    @Test
    public void delete() {
    	dao.delete(1);
    }
    
    @Test
    public void testFindList() {
        PageCondition pageCondition = new PageCondition();

        List<ProductInfoDO> list = dao.findListByParam(null,pageCondition);
        System.out.println("testProductInfoListByParam 哈哈哈2"+list.size());
    }

    @Test
    public void testFindListByParam() {
        PageCondition pageCondition = new PageCondition();
    	ProductInfoDO productInfoDO = new ProductInfoDO();

        productInfoDO.setProductId(5);
        List<ProductInfoDO> list = dao.findListByParam(productInfoDO, pageCondition);
        System.out.println("testProductInfoListByParam 哈哈哈2"+list.size());
    }
    
}

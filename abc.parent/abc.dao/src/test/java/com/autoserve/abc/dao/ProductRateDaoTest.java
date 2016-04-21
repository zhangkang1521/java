package com.autoserve.abc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.ProductRateInfoDO;
import com.autoserve.abc.dao.intf.ProductRateDao;

/**
 * @author RJQ 2014/12/17 16:41.
 */
public class ProductRateDaoTest extends BaseDaoTest {
    @Autowired
    private ProductRateDao productRateDao;

    @Test
    public void testSearch() {

        ProductRateInfoDO pojo = new ProductRateInfoDO();
        pojo.setProductName("品");
        System.out.println(productRateDao.findProductRateInfoListByParam(pojo, null));
    }

}

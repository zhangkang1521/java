package org.zk.batch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Date;

/**
 * Created by zhangkang on 2016/5/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = {"classpath:applicationContext.xml","classpath:batch.xml"})
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
public class BatchJobTest2 {

//    @Autowired
//    private Job querySmsStatusJob;
//
//
//    @Autowired
//    private JobLauncher jobLauncher;


    @Test
    public void testDayendJob(){
//        JobParameters jobParams = createJobParameters();
//        JobExecution jobExecution = null;
//        try {
//            jobExecution = jobLauncher.run(querySmsStatusJob, jobParams);
//        } catch (JobExecutionAlreadyRunningException e) {
//            e.printStackTrace();
//        } catch (JobRestartException e) {
//            e.printStackTrace();
//        } catch (JobInstanceAlreadyCompleteException e) {
//            e.printStackTrace();
//        } catch (JobParametersInvalidException e) {
//            e.printStackTrace();
//        }
//        Assert.assertEquals("successful",jobExecution.getStatus(), BatchStatus.COMPLETED);
    }



    private JobParameters createJobParameters() {
        JobParameters jobParams = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
        return jobParams;
    }

}

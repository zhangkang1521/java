/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.plan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

import com.alibaba.citrus.util.Assert;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.util.Arith;

/**
 * 月利率计算方式下，还款计划和收益计划生成服务
 * 
 * @author segen189 2014年11月28日 上午12:37:15
 */
public class PlanBuilderByDayRate implements PlanBuilder {
    private static final PlanBuilderByDayRate singleton = new PlanBuilderByDayRate();

    private PlanBuilderByDayRate() {
    }

    public static PlanBuilderByDayRate getInstance() {
        return singleton;
    }

    /**
     * 计算月利率<br>
     * 月利率 ＝ 年化收益率 * / 12 / 100
     * 
     * @param annualYield 年化收益率 如 10.10D 代表 10.10%
     * @return double 月利率
     */
    private double getMonthRate(double annualRate) {
        // 月利率 ＝ 年化收益率 / 12 / 100
        return annualRate / 12 / 100;
    }

    /**
     * 计算日利率<br>
     * 日利率 ＝ 年化收益率 / 年化天数 / 100
     * 
     * @param annualYield 年化收益率 如 10.10D 代表 10.10%
     * @param annualDays 一年按多少天计算
     * @return double 日利率
     */
    private double getDayRate(double annualRate, int annualDays) {
        // 日利率 ＝ 年化收益率 / 年化天数 / 100
        double dayRate = annualRate / annualDays / 100;
        return dayRate;
    }

    @Override
    public List<PaymentPlan> buildPaymentPlanList(Loan pojo, double serveFee, int fullTransRecordId, int resultPeriod) {
        DateTime fullDaytime = DateTime.now();

        int totalMonths = this.buildTotalMonths(pojo, fullDaytime);

        if (totalMonths % resultPeriod != 0) {
            return null;
        }
        Assert.assertTrue(totalMonths % resultPeriod == 0, "还款总期数必须要能被借款总月数整除");

        List<PaymentPlan> planList = buildSequentialPaymentPlanList(pojo, serveFee, fullTransRecordId, resultPeriod,
                fullDaytime);
        
        return planList;
    }

    /**
     * 计算期数
     * 
     * @param pojo
     * @param fullDaytime
     * @return
     */
    @Override
    public int buildTotalMonths(Loan pojo, DateTime fullDaytime) {

        DateTime expire = new DateTime(pojo.getLoanExpireDate());

        int totalMonths = getMonthSpace(fullDaytime.toDate(), pojo.getLoanExpireDate());

        if (expire.getDayOfMonth() > pojo.getLoanPayDate() && pojo.getLoanPayDate() > fullDaytime.getDayOfMonth()) {
            totalMonths = totalMonths + 1;
        }
        //??
        if (expire.getDayOfMonth() < pojo.getLoanPayDate() && pojo.getLoanPayDate() < fullDaytime.getDayOfMonth()) {
            return totalMonths;
        }
        if (expire.getDayOfMonth() != pojo.getLoanPayDate() && expire.getDayOfMonth() <= fullDaytime.getDayOfMonth()
                && pojo.getLoanPayDate() != fullDaytime.getDayOfMonth()) {
            totalMonths = totalMonths + 1;
        }
        
        
        return totalMonths;
    }

    //生成还款计划列表
    private List<PaymentPlan> buildSequentialPaymentPlanList(Loan pojo, double serveFee, int fullTransRecordId,
                                                             int totalMonths, DateTime fullDaytime) {
        List<PaymentPlan> planList = new ArrayList<PaymentPlan>();
        // 本金
        double loanCapital = pojo.getLoanCurrentValidInvest().doubleValue();
        // 日利率按360天计算
        double dayRate = getDayRate(pojo.getLoanRate().doubleValue(), 360);
        //每期顺延日期
        Date flagDay = fullDaytime.toDate();

        //计算应还的总利息
    	int daycounts=this.getDifferenceDays(flagDay, pojo.getLoanExpireDate());
    	BigDecimal PayInterest = new BigDecimal(loanCapital * dayRate * daycounts).setScale(2, BigDecimal.ROUND_HALF_UP);
    	
    	BigDecimal Interestcount=BigDecimal.ZERO;   //除最后一期还款的利息之和
    	
        for (int period = 1; period <= totalMonths; period++) {
            PaymentPlan plan = new PaymentPlan();

            plan.setLoanPeriod(period);
            plan.setLoanId(pojo.getLoanId());
            plan.setPayServiceFee(new BigDecimal(serveFee));
            plan.setPayState(PayState.INACTIVED);
            plan.setFullTransRecordId(fullTransRecordId);
            plan.setLoanee(pojo.getLoanUserId());
            plan.setIsClear(false);
            plan.setReplaceState(false);

            // 计算还款日
            switch (pojo.getLoanClearType()) {
                case FIXED_DAY: {
                    //指定还款日
                    if (fullDaytime.getDayOfMonth() >= pojo.getLoanPayDate()) {
                        if (period == totalMonths) {
                            plan.setPaytime(pojo.getLoanExpireDate());
                        } else {
                            plan.setPaytime(fullDaytime.plusMonths(period)
                                    .withField(DateTimeFieldType.dayOfMonth(), pojo.getLoanPayDate()).toDate());
                        }
                    } else if (fullDaytime.getDayOfMonth() < pojo.getLoanPayDate()) {
                        if (period == totalMonths) {
                            plan.setPaytime(pojo.getLoanExpireDate());
                        } else {
                            plan.setPaytime(fullDaytime.plusMonths(period - 1)
                                    .withField(DateTimeFieldType.dayOfMonth(), pojo.getLoanPayDate()).toDate());
                        }
                    }
                }
                    break;
//                case UN_FIXED_DAY: {
//                    // 非固定还款日，下一期还款日重新计算
//                    // 最后一期限在满标日顺延续n期
//                    // 满标日期在1号－15号，下一期还款日在下一期的1号
//                    if (period == totalMonths) {
//                        plan.setPaytime(fullDaytime.plusMonths(period).toDate());
//                    } else if (fullDaytime.getDayOfMonth() <= 15) {
//                        plan.setPaytime(fullDaytime.plusMonths(period).withField(DateTimeFieldType.dayOfMonth(), 1)
//                                .toDate());
//                    }
//                    // 满标日期在16号及以后，下一期还款日在下一期的16号
//                    else {
//                        plan.setPaytime(fullDaytime.plusMonths(period).withField(DateTimeFieldType.dayOfMonth(), 16)
//                                .toDate());
//                    }
//                }
//                    break;
                default:
                    throw new IllegalArgumentException("Illegal LoanClearType");
            }

            // 计算每期还款总额、每期所还本金、每期所还利息
            switch (pojo.getLoanPayType()) {
                case AYHX_DQHB: {
                    /**
                     * 按月还息到期还本算法
                     */             	
                    //计算最每期要还利息天数
                    int days = this.getDifferenceDays(flagDay, plan.getPaytime());
                    flagDay = plan.getPaytime();
                    //利息
                    BigDecimal everyPayInterest =new BigDecimal(loanCapital * dayRate * days).setScale(2, BigDecimal.ROUND_HALF_UP);
                    
                    // 最后一期还本金+利息
                    if (period == totalMonths) {                  	
                        plan.setPayTotalMoney(new BigDecimal(loanCapital).add(PayInterest.subtract(Interestcount)));
                        plan.setPayInterest(PayInterest.subtract(Interestcount));
                        plan.setPayCapital(new BigDecimal(loanCapital));
                        planList.add(plan);
                    }else {
                        plan.setPayTotalMoney(everyPayInterest);
                        plan.setPayInterest(everyPayInterest);
                        plan.setPayCapital(BigDecimal.valueOf(0));
                        planList.add(plan);
                        Interestcount=Interestcount.add(everyPayInterest);   //利息累计
                    }
                }
                    break;
                case DQBX: {
                    // 到期本息算法
                    if (period == totalMonths) {
                        //利随本清只有一期
                        plan.setLoanPeriod(1);
                        int days = this.getDifferenceDays(flagDay, pojo.getLoanExpireDate());
                        plan.setPayCapital(pojo.getLoanCurrentValidInvest());
                        plan.setPayInterest(new BigDecimal(loanCapital * dayRate * days).setScale(2, BigDecimal.ROUND_HALF_UP));
                        plan.setPayTotalMoney(plan.getPayInterest().add(plan.getPayCapital()));
                        planList.add(plan);
                    }
                }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal LoanPayType");
            }

        }

        return planList;
    }

    @Override
    public List<IncomePlan> buildIncomePlanList(Loan pojo, List<Invest> investList, List<PaymentPlan> paymentList,
                                                int fullTransRecordId) {
        List<IncomePlan> planList = new LinkedList<IncomePlan>();
        int numOfPeriods=paymentList.size();   //期数
        
        //用map存放每个投资者除最后一期所还的本金之和
        Map<Integer,BigDecimal> map=new HashMap<Integer,BigDecimal>();
        for(Invest invest:investList){
        	map.put(invest.getId(), BigDecimal.ZERO);
        }
        //说明:（1.每一期的最后一个投资者    2.每个投资者的最后一期）
        //1.防止出现每期收益计划的总钱数和每期还款计划的钱数不是完全相等的情况，最后一个投资者的收益不能按百分比计算，应该是还款的钱数减去前几个投资人的收益和。 
        //2.对于每个投资者来说，投资金额应该等于各期所还本金之和。所以对于投资者的最后一期收益所获的本金=投资者有效投资金额-前几期所还的本金之和。        
        for (int i=0;i<paymentList.size();i++) {
        	 PaymentPlan paymentPlan=paymentList.get(i);
        	 BigDecimal capitalSum=BigDecimal.ZERO;   //除最后一个投资人所分发的本金之和
             BigDecimal interstSum=BigDecimal.ZERO;   //除最后一个投资人所分发的利息之和
	        	for(int j=0;j<investList.size();j++){
	        		IncomePlan incomePlan = new IncomePlan();
	        		Invest invest=investList.get(j);
	                incomePlan.setFullTransRecordId(fullTransRecordId);
	                incomePlan.setPaymentPlanId(paymentPlan.getId());
	                incomePlan.setInvestId(invest.getId());
	                incomePlan.setLoanId(invest.getOriginId());
	                incomePlan.setBeneficiary(invest.getUserId());
	                incomePlan.setPaytime(paymentPlan.getPaytime());
	                incomePlan.setLoanPeriod(paymentPlan.getLoanPeriod());
	                incomePlan.setIncomePlanState(IncomePlanState.INACTIVED);
	        		if(j==investList.size()-1){
	        			 if(i==numOfPeriods){   //每个投资者的最后一期(本金计算)
	        				 incomePlan.setPayCapital(invest.getValidInvestMoney().subtract(map.get(invest.getId())));
	        			 }else{
	        				 incomePlan.setPayCapital(paymentPlan.getPayCapital().subtract(capitalSum));
	        				 map.put(invest.getId(), map.get(invest.getId()).add(incomePlan.getPayCapital()));
	        			 }	        			
	                     incomePlan.setPayInterest(paymentPlan.getPayInterest().subtract(interstSum)); 
	                     incomePlan.setPayTotalMoney(incomePlan.getPayCapital().add(incomePlan.getPayInterest()));	                     
	        		}else{
	        			 double percent = Arith.div(invest.getValidInvestMoney(), pojo.getLoanCurrentValidInvest()).doubleValue();		                   
	        			 if(i==numOfPeriods){   //每个投资者的最后一期(本金计算)
	        				 incomePlan.setPayCapital(invest.getValidInvestMoney().subtract(map.get(invest.getId())));
	        			 }else{
	        				 incomePlan.setPayCapital(new BigDecimal(paymentPlan.getPayCapital().doubleValue() * percent).setScale(2, BigDecimal.ROUND_HALF_UP));
	        				 map.put(invest.getId(), map.get(invest.getId()).add(incomePlan.getPayCapital()));
	        			 }	                    
	                    incomePlan.setPayInterest(new BigDecimal(paymentPlan.getPayInterest().doubleValue() * percent).setScale(2, BigDecimal.ROUND_HALF_UP)); 
	                    incomePlan.setPayTotalMoney(incomePlan.getPayCapital().add(incomePlan.getPayInterest()));	                   
	                    capitalSum=capitalSum.add(incomePlan.getPayCapital());   //本金累计
	                    interstSum=interstSum.add(incomePlan.getPayInterest());  //利息累计
	        		}	        		
	                planList.add(incomePlan);
	        	}
        }

        return planList;
    }

    @Override
    public List<IncomePlan> buildTransferIncomePlanList(TransferLoan pojo, List<IncomePlan> transferIncomeList,
                                                        List<Invest> investList, int fullTransRecordId) {
        List<IncomePlan> planList = new LinkedList<IncomePlan>();
        for(IncomePlan oldPlan : transferIncomeList){
        	BigDecimal capitalSum=BigDecimal.ZERO;   //除最后一个投资人所分发的本金之和
            BigDecimal interstSum=BigDecimal.ZERO;   //除最后一个投资人所分发的利息之和
            for(int i=0;i<investList.size();i++){
            	Invest invest=investList.get(i);
            	IncomePlan newPlan = new IncomePlan();
                newPlan.setFullTransRecordId(fullTransRecordId);
                newPlan.setPaymentPlanId(oldPlan.getPaymentPlanId());
                newPlan.setInvestId(invest.getId());
                newPlan.setLoanId(invest.getOriginId());
                newPlan.setBeneficiary(invest.getUserId());
                if(i==investList.size()-1){
                	newPlan.setPayCapital(oldPlan.getPayCapital().subtract(capitalSum));
                	newPlan.setPayInterest(oldPlan.getPayInterest().subtract(interstSum)); 
                	newPlan.setPayTotalMoney(newPlan.getPayCapital().add(newPlan.getPayInterest()));	
                }else{
                	 double percent = Arith.div(invest.getValidInvestMoney(), pojo.getTransferMoney()).doubleValue();
                	 newPlan.setPayCapital(new BigDecimal(oldPlan.getPayCapital().doubleValue() * percent).setScale(2, BigDecimal.ROUND_HALF_UP));
                     newPlan.setPayInterest(new BigDecimal(oldPlan.getPayInterest().doubleValue() * percent).setScale(2, BigDecimal.ROUND_HALF_UP));
                     newPlan.setPayTotalMoney(newPlan.getPayCapital().add(newPlan.getPayInterest()));
                     capitalSum=capitalSum.add(newPlan.getPayCapital());   //本金累计
	                 interstSum=interstSum.add(newPlan.getPayInterest());  //利息累计
                }             
                newPlan.setPaytime(oldPlan.getPaytime());
                newPlan.setLoanPeriod(oldPlan.getLoanPeriod());
                newPlan.setIncomePlanState(IncomePlanState.INACTIVED);
                planList.add(newPlan);
            }
        }
        return planList;
    }

    @Override
    public List<IncomePlan> buildBuyIncomePlanList(BuyLoan pojo, List<IncomePlan> buyIncomeList,
                                                   List<Invest> investList, int fullTransRecordId) {
        List<IncomePlan> planList = new LinkedList<IncomePlan>();

        Map<Integer, Integer> userIdToInvestIdMap = new HashMap<Integer, Integer>();
        for (Invest invest : investList) {
            userIdToInvestIdMap.put(invest.getUserId(), invest.getId());
        }

        // 增加收购人的未收益的收益计划
        for (IncomePlan oldPlan : buyIncomeList) {
            IncomePlan newPlan = new IncomePlan();

            newPlan.setFullTransRecordId(fullTransRecordId);
            newPlan.setPaymentPlanId(oldPlan.getPaymentPlanId());
            newPlan.setInvestId(userIdToInvestIdMap.get(oldPlan.getBeneficiary()));
            newPlan.setLoanId(oldPlan.getLoanId());
            newPlan.setBeneficiary(pojo.getUserId());
            newPlan.setPayCapital(oldPlan.getPayCapital());
            newPlan.setPayInterest(oldPlan.getPayInterest());
            newPlan.setPayFine(oldPlan.getPayFine());
            newPlan.setPayTotalMoney(oldPlan.getPayTotalMoney());
            newPlan.setPaytime(oldPlan.getPaytime());
            newPlan.setLoanPeriod(oldPlan.getLoanPeriod());
            newPlan.setRemainFine(oldPlan.getRemainFine());
            newPlan.setIncomePlanState(IncomePlanState.INACTIVED);

            planList.add(newPlan);
        }

        return planList;
    }

    /**
     * 计算两个时间相差月份
     * 
     * @param date1
     * @param date2
     * @return
     */
    public int getMonthSpace(Date date1, Date date2) {

        int result = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            c1.setTime(date1);
            c2.setTime(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (c1.before(c2)) {
            result++;
            c1.add(Calendar.MONTH, 1);
        }

        return result == 0 ? 1 : Math.abs(result);

    }

    /**
     * 计算两个时间相差天数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public int getDifferenceDays(Date date1, Date date2) {

        int loanDays = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            c1.setTime(date1);
            c2.setTime(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (c1.before(c2)) {
            loanDays++;
            c1.add(Calendar.DAY_OF_YEAR, 1);
        }

        return loanDays;
    }

	@Override
	public List<IncomePlan> distributionPenalty(Loan loan,List<Invest> invests,List<IncomePlan> incomePlans ,BigDecimal pulishMoney) {
		BigDecimal sumPulishMoney=new BigDecimal(0);      //除最后一个投资人所分配的罚金总额
		for(int i=0;i<invests.size();i++){
			Invest invest=invests.get(i);
			for(IncomePlan incomePlan:incomePlans){
				if(incomePlan.getInvestId().equals(invest.getId())){
					if(i==invests.size()-1){
						incomePlan.setPayFine(incomePlan.getPayFine().add(pulishMoney.subtract(sumPulishMoney)));
						incomePlan.setPayTotalMoney(incomePlan.getPayTotalMoney().add(pulishMoney.subtract(sumPulishMoney)));
					}else{
						double percent = Arith.div(invest.getValidInvestMoney(), loan.getLoanCurrentValidInvest()).doubleValue();
						 BigDecimal currentPulish=new BigDecimal(pulishMoney.doubleValue()*percent).setScale(2, BigDecimal.ROUND_HALF_UP);
						 //应还罚金
						 incomePlan.setPayFine(incomePlan.getPayFine().add(currentPulish));
						 //应还总额
						 incomePlan.setPayTotalMoney(incomePlan.getPayTotalMoney().add(currentPulish));
						 sumPulishMoney=sumPulishMoney.add(currentPulish);
					}			 
				}
			}
		}
		return incomePlans;
	}
}

package com.autoserve.abc.dao.dataobject;

/**
 * 机构text字段信息
 * @author RJQ 2014/11/13 18:11.
 */
public class GovernmentWithBLOBsDO extends GovernmentDO {
    /**
     * 公司概况
     * abc_goverment.gov_profile
     */
    private String govProfile;

    /**
     * 团队管理
     * abc_goverment.gov_team_management
     */
    private String govTeamManagement;

    /**
     * 发展历史
     * abc_goverment.gov_development_history
     */
    private String govDevelopmentHistory;

    /**
     * 融资性担保牌照
     * abc_goverment.gov_guar_card
     */
    private String govGuarCard;

    /**
     * 合作机构
     * abc_goverment.gov_partner
     */
    private String govPartner;

    /**
     * 合作协议
     * abc_goverment.gov_cooperate_agreement
     */
    private String govCooperateAgreement;

    public String getGovProfile() {
        return govProfile;
    }

    public void setGovProfile(String govProfile) {
        this.govProfile = govProfile;
    }

    public String getGovTeamManagement() {
        return govTeamManagement;
    }

    public void setGovTeamManagement(String govTeamManagement) {
        this.govTeamManagement = govTeamManagement;
    }

    public String getGovDevelopmentHistory() {
        return govDevelopmentHistory;
    }

    public void setGovDevelopmentHistory(String govDevelopmentHistory) {
        this.govDevelopmentHistory = govDevelopmentHistory;
    }

    public String getGovGuarCard() {
        return govGuarCard;
    }

    public void setGovGuarCard(String govGuarCard) {
        this.govGuarCard = govGuarCard;
    }

    public String getGovPartner() {
        return govPartner;
    }

    public void setGovPartner(String govPartner) {
        this.govPartner = govPartner;
    }

    public String getGovCooperateAgreement() {
        return govCooperateAgreement;
    }

    public void setGovCooperateAgreement(String govCooperateAgreement) {
        this.govCooperateAgreement = govCooperateAgreement;
    }
}

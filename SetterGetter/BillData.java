package com.tranetech.gandhinagar.plshah.gst.salesbillplshah.SetterGetter;

/**
 * Created by Markand on 30-06-2017 at 12:16 PM.
 */

public class BillData {
    private String sBillCode,sId,sInvNum,sInvParty,sInvDate,sInvTotal;

    public void setsBillCode(String sBillCode) {
        this.sBillCode = sBillCode;
    }

    public String getsBillCode() {
        return sBillCode;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsId() {
        return sId;
    }

    public void setsInvDate(String sInvDate) {
        this.sInvDate = sInvDate;
    }

    public String getsInvDate() {
        return sInvDate;
    }

    public void setsInvNum(String sInvNum) {
        this.sInvNum = sInvNum;
    }

    public String getsInvNum() {
        return sInvNum;
    }

    public void setsInvParty(String sInvParty) {
        this.sInvParty = sInvParty;
    }

    public String getsInvParty() {
        return sInvParty;
    }

    public void setsInvTotal(String sInvTotal) {
        this.sInvTotal = sInvTotal;
    }

    public String getsInvTotal() {
        return sInvTotal;
    }
}

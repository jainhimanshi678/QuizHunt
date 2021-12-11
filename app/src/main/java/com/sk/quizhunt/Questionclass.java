package com.sk.quizhunt;

public class Questionclass {
    String que,opA,opB,opC,opD;
    int Correctans;
    public Questionclass(String que, String opA, String opB, String opC, String opD, int correctans) {
        this.que = que;
        this.opA = opA;
        this.opB = opB;
        this.opC = opC;
        this.opD = opD;
        Correctans = correctans;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getOpA() {
        return opA;
    }

    public void setOpA(String opA) {
        this.opA = opA;
    }

    public String getOpB() {
        return opB;
    }

    public void setOpB(String opB) {
        this.opB = opB;
    }

    public String getOpC() {
        return opC;
    }

    public void setOpC(String opC) {
        this.opC = opC;
    }

    public String getOpD() {
        return opD;
    }

    public void setOpD(String opD) {
        this.opD = opD;
    }

    public int getCorrectans() {
        return Correctans;
    }

    public void setCorrectans(int correctans) {
        Correctans = correctans;
    }
}

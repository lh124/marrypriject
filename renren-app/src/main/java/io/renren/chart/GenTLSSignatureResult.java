package io.renren.chart;

public class GenTLSSignatureResult {
    public String errMessage;
    public String urlSig;
    public int expireTime;
    public int initTime;
    public GenTLSSignatureResult(){
        errMessage = "";
        urlSig = "";
    }
}

package com.arraypay.generator.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Properties;

/**
 *@Description:获取数据库JDBC配置和diamond 动态配置库上的信息
 *@Author:jindong.li
 *@Since:2016年5月6日下午1:55:17  
 */
public class ConfigFileUtil {

    private static Configuration config = null;

    public static void getConfig(){
        try {
            if (config == null) {
                // System.setProperty("catalina.home", "D:\\devtool\\apache-tomcat-7.0.61");
                System.out.println ("catalina.home======>" + System.getProperty ("catalina.home"));
                System.setProperty ("config.file", System.getProperty ("catalina.home") + File.separator + "conf" + File.separator + "jdbc.properties");
                ConfigurationFactory factory = new ConfigurationFactory("arraypay-config.xml");
                config = factory.getConfiguration ();
            }
        } catch (ConfigurationException e) {
            if (isDebugMode ()) { throw new RuntimeException(e.getMessage ()); }
            throw new RuntimeException(e);
        }
    }

    public static void setConfig(String message){
        getConfig ();
        Properties pro = new Properties();
        try {
            pro.load (new StringReader(message));
        } catch (IOException e) {
        	 throw new RuntimeException(e);
        }
        for ( Object t : pro.keySet () ) {
            config.clearProperty (t.toString ());
            config.addProperty (t.toString (), pro.getProperty (t.toString ()));
        }
    }

    public static boolean isDebugMode(){
        getConfig ();
        return config.getBoolean ("debug.mode", false);
    }

    public static String getStringValue(String key){
        getConfig ();
        return config.getString (key, "");
    }

    /** ============消息平台start================== */
    /**
     * 获取消息平台URL
     * #message.url=http://192.168.109.117:8080/messageforwardingcenter/forward/message/messageNotify
     * @return
     */
    public static String getMessageServerUrl(){
        getConfig ();
        return config.getString ("message.url", "");
    }

    // windows phone 推送消息后activity配置
    public static String getWinPhoneActiveFileName(){
        getConfig ();
        return config.getString ("winphone.push.activity");
    }

    /**
     * 获取消息标题
     * mail.message.title=第三方充值平台-殴飞未找到交易号
     * @param key
     * @return
     */
    public static String getTitle(String key, String appPartner, String type){
        getConfig ();
        String title = config.getString (key + "." + appPartner + "." + type + ".message.title", null);
        if (title == null) {
            title = config.getString (key + "." + type + ".message.title", "");
        }
        return title;
    }

    /**
     * 获取发送对象
     * sms.message.to=18501594896
     * @param key
     * @return
     */
    public static String getTo(String key, String appPartner, String type){
        getConfig ();
        return config.getString (key + "." + appPartner + "." + type + ".message.to", "");
    }

    /**
     * #过滤发送消息的号码段
     *filter.phonenum=13700000;18000000;13000000 
     * 
     * @return
     */
    public static String[] getFilterPhoneNum(){
        getConfig ();
        String filterPhoneNum = config.getString ("filter.phonenum", "");
        String[] filterPhoneNums = filterPhoneNum.split ("\\;");
        return filterPhoneNums;
    }

    /**
     * 获取消息发送方式
     * .sendmessage.type=mail;sms;push;msg
     * @param key
     * @return
     */
    public static String[] getSendType(String key){
        getConfig ();
        String sendType = config.getString (key + ".sendmessage.type", null);
        if (sendType == null) {
            sendType = config.getString ("sendmessage.type", "");
        }
        String[] sentTypes = sendType.split ("\\;");

        // 位置转换，将msg排入第一列
        String tmp ;
        for ( int i = 0 ; i < sentTypes.length ; i++ ) {
            if ( ("msg").equals(sentTypes[i])) {
                tmp = sentTypes[0];
                sentTypes[0] = "msg";
                sentTypes[i] = tmp;
            }
        }
        return sentTypes;
    }

    /**
     * 获取消息发送开关
     * .sendmessage.switch=true
     * @param key
     * @return
     */
    public static Boolean getSendSwitch(String key, String appPartner, String type){
        getConfig ();
        Boolean sendSwitch = config.getBoolean (key + "." + appPartner + "." + type + ".sendmessage.switch", null);
        if (sendSwitch == null) {
            sendSwitch = config.getBoolean (key + "." + type + ".sendmessage.switch", false);
        }
        return sendSwitch;
    }

    /**
     * 获取消息内容模板
     * 
     * @return
     */
    public static String getContent(String key, String appPartner, String type){
        getConfig ();
        String content = config.getString (key + "." + appPartner + "." + type + ".message.content", null);
        if (content == null) {
            content = config.getString (key + "." + type + ".message.content", "");
        }
        return content;
    }

    /**
     * 获取消息来自哪里
     * 
     * @param key
     * @return
     */

    public static String getFrom(String key, String appPartner, String type){
        getConfig ();
        return config.getString (key + "." + appPartner + "." + type + ".message.from", "");
    }

    /**
     * #客服电话
     *  customer.service.hotline=400-088-5331
     * 
     * @param 
     * @return
     */
    public static String getCustomerServiceHotline(){
        getConfig ();
        return config.getString ("customer.service.hotline");
    }

    public static String getNodeId(){
        getConfig ();
        return config.getString ("node.id", "01");
    }

    public static long getBindAuthoCodeTimeout(){
        getConfig ();
        return config.getLong ("bind.authcode.timeout", 1000 * 60 * 5l);
    }

    public static String getAppDownUrl(){
        getConfig ();
        return config.getString ("app.download.url", "");
    }

    public static String getAppTimeSynUrl(){
        getConfig ();
        return config.getString ("app.timesyn.url", "");
    }

    public static String getAppLockTime(){
        getConfig ();
        return config.getString ("app.lock.time", null);
    }

    public static String getInviteType(){
        getConfig ();
        return config.getString ("needviewinvite.type", "0");
    }

    public static String getSercurityServerDV(){
        getConfig ();
        return config.getString ("safe.app.sercurityderverdv");
    }

    // 二维码解码地址
    public static String getPosPTSCDCodeUrl(){
        getConfig ();
        return config.getString ("posp.tsc.dcode.url", "http://demo.payegis.com/posserver/rest/authority/validatedccode");
    }

    public static String getCloudWithTulandoAppid(){
        getConfig ();
        return config.getString ("cloud.tulando.appid");
    }

    public static String getCloudWithTulandoAppKey(){
        getConfig ();
        return config.getString ("cloud.tulando.appkey");
    }

    /**
     *@Description: TODO(交易密码输出间隔时间) 
     *@Author: yingchen.ge
     *@Since: 2016年7月22日上午10:54:18
     *@return 返回秒数
     */
    public static int getOptIntervalTime(){
        int def = 30;
        try {
            String time = config.getString ("optIntervalTime");
            def = Integer.valueOf (time);
        } catch (Exception e) {
        	 throw new RuntimeException(e);
        	
        }
        return def * 60;
    }

    /**
     *@Description: TODO(交易密码输出间隔时间) 
     *@Author: yingchen.ge
     *@Since: 2016年7月22日上午10:54:18
     *@return
     */
    public static int getOptIntervalNum(){
        int def = 5;
        try {
            String num = config.getString ("optIntervalNum");
            def = Integer.valueOf (num);
        } catch (Exception e) {
        	 throw new RuntimeException(e);
        }
        return def;

    }

    /** ============消息平台end================== */

    /***********************获取hue身份认证信息开始*************************/
    public static String getHueSystemNo(){
        getConfig ();
        return config.getString ("hue_system_no");
    }

    public static String getHueAppId(){
        getConfig ();
        return config.getString ("hue_app_id");
    }

    public static String getHueAppKey(){
        getConfig ();
        return config.getString ("hue_app_key");
    }

    public static BigDecimal getOpenHueLimit(){
        getConfig ();
        String limit = config.getString ("open.hue.limit", "2000");
        BigDecimal bt = null;
        try {
            bt = new BigDecimal(limit);
        } catch (Exception e) {
            bt = new BigDecimal(2000);
            throw new RuntimeException(e);
        }
        return bt;
    }

    public static boolean isOpenHue(){
        getConfig ();
        return config.getBoolean ("is_open_hue", false);
    }

    /***********************获取hue身份认证信息结束*************************/

    /********************************反欺诈****************************/

    public static String getAntifraudAppid(){
        getConfig ();
        return config.getString ("antifraud_app_id");
    }

    public static String getAntifraudAppKey(){
        getConfig ();
        return config.getString ("antifraud_app_key");
    }

    public static String getAntifraudOrgCode(){
        getConfig ();
        return config.getString ("antifraud_org_code");
    }

    public static String getAntifraudBussniessCode(){
        getConfig ();
        return config.getString ("antifraud_bussniess_code");
    }

    public static String getIsOpenRechargeValidate(){
        getConfig ();
        return config.getString ("is.open.recharge.validate", "false");
    }

    /********************************反欺诈****************************/

}

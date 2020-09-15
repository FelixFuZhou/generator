package com.arraypay.generator.util;

import com.google.common.collect.Maps;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 全局配置类
 * @author 
 * @version 
 */
public class Global {

    /**
     * 当前对象实例
     */
    private static Global              global             = new Global ();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map                = Maps.newHashMap ();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader    loader             = new PropertiesLoader ("arraypay.properties");

    /**
     * 显示/隐藏
     */
    public static final String SHOW               = "1";
    public static final String HIDE               = "0";

    /**
     * 是/否
     */
    public static final String YES                = "1";
    public static final String NO                 = "0";

    /**
     * 对/错
     */
    public static final String TRUE               = "true";
    public static final String FALSE              = "false";

    /**
     * 上传文件基础虚拟路径
     */
    public static final String USERFILES_BASE_URL = "/userfiles/";

    /**
     * 获取当前对象实例
     */
    public static Global getInstance(){
        return global;
    }

    /**
     * 获取配置
     * @see {fns:getConfig('adminPath')}
     */
    public static String getConfig(String key){
        String value = map.get (key);
        if (value == null) {
            value = loader.getProperty (key);
            map.put (key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    /**
     * 获取管理端根路径
     */
    public static String getAdminPath(){
        return getConfig ("adminPath");
    }

    /**
     * 获取sdk管理端根路径
     */
    public static String getSDKPath(){
        return getConfig ("sdkPath");
    }

    /**
     * 图片访问路径
     */
    public static String getImageRoot(){
        return getConfigWithSlash ("access.url");
    }

    /**
     * 获取前端根路径
     */
    public static String getFrontPath(){
        return getConfig ("frontPath");
    }

    /**
     * 获取URL后缀
     */
    public static String getUrlSuffix(){
        return getConfig ("urlSuffix");
    }

    /**
     * 获取微信
     */
    public static String getWeixinPath(){
        return getConfig ("weixinPath");
    }

    /**
     * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
     */
    public static Boolean isDemoMode(){
        String dm = getConfig ("demoMode");
        return "true".equals (dm) || "1".equals (dm);
    }

    /**
     * 在修改系统用户和角色时是否同步到Activiti
     */
    public static Boolean isSynActivitiIndetity(){
        String dm = getConfig ("activiti.isSynActivitiIndetity");
        return "true".equals (dm) || "1".equals (dm);
    }

    /**
     * 页面获取常量
     * @see {fns:getConst('YES')}
     */
    public static Object getConst(String field){
        try {
            return Global.class.getField (field).get (null);
        } catch (Exception e) {
            // 异常代表无配置，这里什么也不做
        	throw new RuntimeException(e);
        }
    }

    /**
     * 获取上传文件的根目录
     * @return
     */
    public static String getUserfilesBaseDir(){
        String dir = getConfig ("userfiles.basedir");
        if (StringUtils.isBlank (dir)) {
             return "";
          
        }
        if (!dir.endsWith ("/")) {
            dir += "/";
        }
        // System.out.println("userfiles.basedir: " + dir);
        return dir;
    }

    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
        // 如果配置了工程路径，则直接返回，否则自动获取。
        String projectPath = Global.getConfig ("projectPath");
        if (StringUtils.isNotBlank (projectPath)) { return projectPath; }
        try {
            File file = new DefaultResourceLoader().getResource ("").getFile ();
            if (file != null) {
                while (true) {
                    File f = new File(file.getPath () + File.separator + "src" + File.separator + "main");
                    if (f == null || f.exists ()) {
                        break;
                    }
                    if (file.getParentFile () != null) {
                        file = file.getParentFile ();
                    } else {
                        break;
                    }
                }
                projectPath = file.toString ();
            }
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }
        return projectPath;
    }

//    public static String getFileSrc(String path){
//        if (StringUtils.isNotBlank (path)) { return Constant.FILE_ACCESS_PATH.replace ("{path}", path); }
//        return null;
//    }
//
//    /**
//     * 获取项目路径
//     */
//    public static String getContextPath(){
//        return ServletContextBean.getServletContext ().getContextPath ();
//    }

    /**
     * 获取项目路径
     */
    public static String getDomain(){
        return getConfig ("domain");
    }

    /**
     * 获取配置
     * @see //并且以斜杠结尾
     */
    public static String getConfigWithSlash(String key){
        String value = map.get (key);
        try {
			if (value == null) {
			    value = loader.getProperty (key);
			    map.put (key, value != null ? value : StringUtils.EMPTY);
			}
			if (StringUtils.isNotBlank (value) && !value.endsWith ("/")) {
			    value = value + "/";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
        return value;
    }

//    public static WeixinPayAccount extractWxAccount() throws BusException{
//        String id = Global.getConfig ("id");
//        String secret = Global.getConfig ("secret");
//        String mchId = Global.getConfig ("mchId");
//        String paySignKey = Global.getConfig ("paySignKey");
//        if (id == null || secret == null || mchId == null || paySignKey == null) { throw new BusException ("WX配置文件出错"); }
//        WeixinPayAccount weixinAccount = new WeixinPayAccount (id,secret,paySignKey,mchId);
//        return weixinAccount;
//    }

    // 获取信用认证appid
    public static String getCreditCertAppId(){
        String value =ConfigFileUtil.getStringValue ("credit.cert.appid");
        return value;
    }

    // 获取信用认证appKey
    public static String getCreditCertAppKey(){
        String value =ConfigFileUtil.getStringValue ("credit.cert.appkey");
        return value;
    }
    // 获取基础信用额度评估接口
    public static String getCreditPaymentScoreUrl(){
//        String value = "http://192.168.109.101:2137/serviceplatform-creditPay/creditPayment/basicCredit";
        String value =ConfigFileUtil.getStringValue ("credit.cert.score.url");
        return value;
    }

    // 获取银行卡校验接口
    public static String getBankcardvalidUrl(){
        String value =ConfigFileUtil.getStringValue ("credit.cert.bankcardvalid.url");
        return value;
    }

    // 提升信用额度评估接口
    public static String getIncreaseCreditScoreUrl(){
//        String value = "http://192.168.109.101:2137/serviceplatform-creditPay/creditPayment/primaryCredit";
        String value =ConfigFileUtil.getStringValue ("credit.cert.increasescore.url");
        return value;
    }
    
    public static String getCreditcardUrl(){
        String value = ConfigFileUtil.getStringValue ("creditcard.url");
         value=StringUtils.isEmpty (value)?"https://pws.payegis.com.cn/id_creditcardapi/idcard/simple_query":value;
        return value;
    }
    
   // String appId = "*****";
    //String appKey = "**********";
    public static String getAppID(){
        String value =ConfigFileUtil.getStringValue ("AppID");
        return value;
    }
    
    public static String getAppKey(){
        String value =ConfigFileUtil.getStringValue ("AppKey");
        return value;
    }

    /**
     * 获取redis缓存的过期时间(秒)
     * @return
     */
    public static Integer getRedisTimeout(){
        String timeout  = getConfig ("redis.timeout");
        return Integer.parseInt(timeout);
    }

    /**
     * 获取thunderurl 地址
     * @return
     */
    public static String getThunderUrl(){
        String value= getConfig ("thunder.url");
        return value;
    }

    /**
     * 获取hue url 地址
     * @return
     */
    public static String getHueUrl(){
        String value= getConfig ("hue.url");
        return value;
    }

    public static String getInitCredit(){
        String value= ConfigFileUtil.getStringValue("init.credit");
        return value;
    }

    public static String getTimerUrl(){
        String value= getConfig ("timer.url");
        return value;
    }

    // 获取 活体检测接口地址
    public static String getCreditPhotoValidUrl(){
        String value =ConfigFileUtil.getStringValue ("credit.photo.valid.url");
        return value;
    }

    //获取活体检测校验数据
    public static String getCreditPhotoValidData(){
        String value= ConfigFileUtil.getStringValue("credit.photo.valid.data");
        return value;
    }

    //获取用户还款次数设置
    public static String getRepayCount(){
        String value= getConfig("repay.count");
        return value;
    }

    //获取用户提额额度
    public static String getRepayIncreaseCredit(){
        String value= getConfig("repay.increase.credit");
        return value;
    }



}

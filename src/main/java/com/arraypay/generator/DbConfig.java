package com.arraypay.generator;


/**
 * 数据库配置文件读取方法
 * @author ljd
 *
 */
public class DbConfig {

    private String driver;
    private String url;
    private String userName;
    private String password;

    public DbConfig() {
        this.driver = GenGlobal.getConfig ("jdbc.driver");
        this.url = GenGlobal.getConfig ("jdbc.url");
        this.userName = GenGlobal.getConfig ("jdbc.username");
        this.password = GenGlobal.getConfig ("jdbc.password");

    }

    public String getDriver(){
        return driver;
    }

    public String getUrl(){
        return url;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

}
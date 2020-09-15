package com.arraypay.generator;

import java.util.Map;

import com.arraypay.generator.util.PropertiesLoader;
import com.arraypay.generator.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class GenGlobal {

    /**
     * 当前对象实例
     */
    private static GenGlobal           global = new GenGlobal ();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map    = Maps.newHashMap ();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader    loader = new PropertiesLoader("jdbc.properties");

    /**
     * 获取当前对象实例
     */
    public static GenGlobal getInstance(){
        return global;
    }

    /**
     * 获取配置
     * @see ${fns:getConfig('adminPath')}
     */
    public static String getConfig(String key){
        String value = map.get (key);
        if (value == null) {
            value = loader.getProperty (key);
            map.put (key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }
}

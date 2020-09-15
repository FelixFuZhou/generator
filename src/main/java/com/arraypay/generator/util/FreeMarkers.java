/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arraypay.generator.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * FreeMarkers工具类
 * @author ThinkGem
 * @version 2013-01-15
 */
public class FreeMarkers {

    public static String renderString(String templateString, Map<String, ?> model){
        try {
//        	System.out.println(templateString);
            StringWriter result = new StringWriter();
            Template t = new Template("name",new StringReader(templateString),new Configuration());
            t.process (model, result);
            return result.toString ();
        } catch (Exception e) {
            throw Exceptions.unchecked (e);
        }
    }

    public static String renderTemplate(Template template, Object model){
        try {
            StringWriter result = new StringWriter();
            template.process (model, result);
            return result.toString ();
        } catch (Exception e) {
            throw Exceptions.unchecked (e);
        }
    }

    public static Configuration buildConfiguration(String directory) throws IOException {
        Configuration cfg = new Configuration();
        Resource path = new DefaultResourceLoader().getResource (directory);
        cfg.setDirectoryForTemplateLoading (path.getFile ());
        return cfg;
    }

    public static void main(String[] args) throws IOException {
        // // renderString
        // Map<String, String> model = com.google.common.collect.Maps.newHashMap();
        // model.put("userName", "calvin");
        // String result = FreeMarkers.renderString("hello ${userName}", model);
        // System.out.println(result);
        // // renderTemplate
        // Configuration cfg = FreeMarkers.buildConfiguration("classpath:/");
        // Template template = cfg.getTemplate("testTemplate.ftl");
        // String result2 = FreeMarkers.renderTemplate(template, model);
        // System.out.println(result2);

        Map<String, String> model = com.google.common.collect.Maps.newHashMap();
         model.put("functionName", "test");
         model.put("functionAuthor", "zhoufu");
         model.put("ClassName", "test");
         model.put("functionVersion", "1.0");
         model.put("userName", "calvin");
         String result = FreeMarkers.renderString("/**\n" +
                 " * ${functionName}DAO接口\n" +
                 " * @author ${functionAuthor}\n" +
                 " * @version ${functionVersion}\n" +
                 " */\n" +
                 "@MyBatisDao\n" +
                 "public interface ${ClassName}Dao extends CrudDao<${ClassName}> {\n" +
                 "\t\n" +
                 "}]]>", model);
         System.out.println(result);
    }

}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arraypay.generator;

import com.alibaba.fastjson.JSON;
import com.arraypay.generator.entity.*;
import com.arraypay.generator.util.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.awt.geom.Area;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代码生成工具类
 * @author ThinkGem
 * @version 2013-11-16
 */
public class GenUtils {

    private static Logger logger = LoggerFactory.getLogger (GenUtils.class);

    /**
     * 初始化列属性字段
     * @param genTable
     */
    public static void initColumnField(GenTable genTable){
        for ( GenTableColumn column : genTable.getColumnList () ) {

            // 如果是不是新增列，则跳过。
            if (StringUtils.isNotBlank (column.getId ())) {
                continue;
            }

            // 设置字段说明
            if (StringUtils.isBlank (column.getComments ())) {
                column.setComments (column.getName ());
            }

            // 设置java类型
            if (StringUtils.startsWithIgnoreCase (column.getJdbcType (), "CHAR") || StringUtils.startsWithIgnoreCase (column.getJdbcType (), "VARCHAR")
                    || StringUtils.startsWithIgnoreCase (column.getJdbcType (), "NARCHAR")) {
                column.setJavaType ("String");
            } else if (StringUtils.startsWithIgnoreCase (column.getJdbcType (), "DATETIME") || StringUtils.startsWithIgnoreCase (column.getJdbcType (), "DATE")
                    || StringUtils.startsWithIgnoreCase (column.getJdbcType (), "TIMESTAMP")) {
                column.setJavaType ("java.util.Date");
                column.setShowType ("dateselect");
            } else if (StringUtils.startsWithIgnoreCase (column.getJdbcType (), "BIGINT") || StringUtils.startsWithIgnoreCase (column.getJdbcType (), "NUMBER")) {
                // 如果是浮点型
                String[] ss = StringUtils.split (StringUtils.substringBetween (column.getJdbcType (), "(", ")"), ",");
                if (ss != null && ss.length == 2 && Integer.parseInt (ss[1]) > 0) {
                    column.setJavaType ("Double");
                }
                // 如果是整形
                else if (ss != null && ss.length == 1 && Integer.parseInt (ss[0]) <= 10) {
                    column.setJavaType ("Integer");
                }
                // 长整形
                else {
                    column.setJavaType ("Long");
                }
            }

            // 设置java字段名
            column.setJavaField (StringUtils.toCamelCase (column.getName ()));

            // 是否是主键
            column.setIsPk (genTable.getPkList ().contains (column.getName ()) ? "1" : "0");

            // 插入字段
            column.setIsInsert ("1");

            // 编辑字段
            if (!StringUtils.equalsIgnoreCase (column.getName (), "id") && !StringUtils.equalsIgnoreCase (column.getName (), "create_by")
                    && !StringUtils.equalsIgnoreCase (column.getName (), "create_date") && !StringUtils.equalsIgnoreCase (column.getName (), "del_flag")) {
                column.setIsEdit ("1");
            }

            // 列表字段
            if (StringUtils.equalsIgnoreCase (column.getName (), "name") || StringUtils.equalsIgnoreCase (column.getName (), "title")
                    || StringUtils.equalsIgnoreCase (column.getName (), "remarks") || StringUtils.equalsIgnoreCase (column.getName (), "update_date")) {
                column.setIsList ("1");
            }

            // 查询字段
            if (StringUtils.equalsIgnoreCase (column.getName (), "name") || StringUtils.equalsIgnoreCase (column.getName (), "title")) {
                column.setIsQuery ("1");
            }

            // 查询字段类型
            if (StringUtils.equalsIgnoreCase (column.getName (), "name") || StringUtils.equalsIgnoreCase (column.getName (), "title")) {
                column.setQueryType ("like");
            }

            // 设置特定类型和字段名

//            // 用户
//            if (StringUtils.startsWithIgnoreCase (column.getName (), "user_id")) {
//                column.setJavaType (User.class.getName ());
//                column.setJavaField (column.getJavaField ().replaceAll ("Id", ".id|name"));
//                column.setShowType ("userselect");
//            }
//            // 部门
//            else if (StringUtils.startsWithIgnoreCase (column.getName (), "office_id")) {
//                column.setJavaType (Office.class.getName ());
//                column.setJavaField (column.getJavaField ().replaceAll ("Id", ".id|name"));
//                column.setShowType ("officeselect");
//            }
            // 区域
            else if (StringUtils.startsWithIgnoreCase (column.getName (), "area_id")) {
                column.setJavaType (Area.class.getName ());
                column.setJavaField (column.getJavaField ().replaceAll ("Id", ".id|name"));
                column.setShowType ("areaselect");
            }
//            // 创建者、更新者
//            else if (StringUtils.startsWithIgnoreCase (column.getName (), "create_by") || StringUtils.startsWithIgnoreCase (column.getName (), "update_by")) {
//                column.setJavaType (User.class.getName ());
//                column.setJavaField (column.getJavaField () + ".id");
//            }
            // 创建时间、更新时间
            else if (StringUtils.startsWithIgnoreCase (column.getName (), "create_date") || StringUtils.startsWithIgnoreCase (column.getName (), "update_date")) {
                column.setShowType ("dateselect");
            }
            // 备注、内容
            else if (StringUtils.equalsIgnoreCase (column.getName (), "remarks") || StringUtils.equalsIgnoreCase (column.getName (), "content")) {
                column.setShowType ("textarea");
            }
            // 父级ID
            else if (StringUtils.equalsIgnoreCase (column.getName (), "parent_id")) {
                column.setJavaType ("This");
                column.setJavaField ("parent.id|name");
                column.setShowType ("treeselect");
            }
            // 所有父级ID
            else if (StringUtils.equalsIgnoreCase (column.getName (), "parent_ids")) {
                column.setQueryType ("like");
            }
            // 删除标记
            else if (StringUtils.equalsIgnoreCase (column.getName (), "del_flag")) {
                column.setShowType ("radiobox");
                column.setDictType ("del_flag");
            }
        }
    }

    /**
     * 获取模板路径
     * @return
     */
    public static String getTemplatePath(){
        try {
            File file = new DefaultResourceLoader ().getResource ("").getFile ();
            if (file != null) { return file.getAbsolutePath ()
                    + File.separator
                    + StringUtils.replaceEach (GenUtils.class.getName (), new String[] { "util." + GenUtils.class.getSimpleName (), "." }, new String[] {
                            "template", File.separator }); }
        } catch (Exception e) {
            logger.error ("{}", e);
        }

        return "";
    }

    /**
     * XML文件转换为对象
     * @param fileName
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fileToObject(String fileName,Class<?> clazz){
        try {
            String pathName = "/templates/modules/gen/" + fileName;
            // logger.debug("File to object: {}", pathName);
            Resource resource = new ClassPathResource (pathName);
            InputStream is = resource.getInputStream ();
            BufferedReader br = new BufferedReader (new InputStreamReader (is,"UTF-8"));
            StringBuilder sb = new StringBuilder ();
            while (true) {
                String line = br.readLine ();
                if (line == null) {
                    break;
                }
                sb.append (line).append ("\r\n");
            }
            if (is != null) {
                is.close ();
            }
            if (br != null) {
                br.close ();
            }

            logger.debug ("Read file content: {}", sb.toString ());
            return (T) JaxbMapper.fromXml (sb.toString (), clazz);
        } catch (IOException e) {
            logger.warn ("Error file convert: {}", e.getMessage ());
        }
        // String pathName = StringUtils.replace(getTemplatePath() + "/" + fileName, "/", File.separator);
        // logger.debug("file to object: {}", pathName);
        // String content = "";
        // try {
        // content = FileUtils.readFileToString(new File(pathName), "utf-8");
        // // logger.debug("read config content: {}", content);
        // return (T) JaxbMapper.fromXml(content, clazz);
        // } catch (IOException e) {
        // logger.warn("error convert: {}", e.getMessage());
        // }
        return null;
    }

    /**
     * 获取代码生成配置对象
     * @return
     */
    public static GenConfig getConfig(){
        return fileToObject ("config.xml", GenConfig.class);
    }

    /**
     * 根据分类获取模板列表
     * @param config
     * @param genScheme
     * @param isChildTable 是否是子表
     * @return
     */
    public static List<GenTemplate> getTemplateList(GenConfig config,String category,boolean isChildTable){
        List<GenTemplate> templateList = Lists.newArrayList ();
        if (config != null && config.getCategoryList () != null && category != null) {
            for ( GenCategory e : config.getCategoryList () ) {
                System.out.println (JSON.toJSONString (e));
                if (category.equals (e.getDictValue ())) {
                    //获取对应名字的模板
                    List<String> list = null;
                    if (!isChildTable) {
                        list = e.getTemplate ();
                    } else {
                        list = e.getChildTableTemplate ();
                    }
                    if (list != null) {
                        for ( String s : list ) {
                            if (StringUtils.startsWith (s, GenCategory.CATEGORY_REF)) {
                                templateList.addAll (getTemplateList (config, StringUtils.replace (s, GenCategory.CATEGORY_REF, ""), false));
                            } else {
                                GenTemplate template = fileToObject (s, GenTemplate.class);
                                if (template != null) {
                                    templateList.add (template);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return templateList;
    }

    /**
     * 获取数据模型
     * @param genScheme
     * @param genTable
     * @return
     */
    public static Map<String, Object> getDataModel(GenScheme genScheme){
        Map<String, Object> model = Maps.newHashMap ();

        model.put ("packageName", StringUtils.lowerCase (genScheme.getPackageName ()));
        model.put ("lastPackageName", StringUtils.substringAfterLast ((String) model.get ("packageName"), "."));
        model.put ("moduleName", StringUtils.lowerCase (genScheme.getModuleName ()));
        model.put ("subModuleName", StringUtils.lowerCase (genScheme.getSubModuleName ()));
        model.put ("className", StringUtils.uncapitalize (genScheme.getGenTable ().getClassName ()));
        model.put ("ClassName", StringUtils.capitalize (genScheme.getGenTable ().getClassName ()));

        model.put ("functionName", genScheme.getFunctionName ());
        model.put ("functionNameSimple", genScheme.getFunctionNameSimple ());
        model.put ("functionAuthor", StringUtils.isNotBlank (genScheme.getFunctionAuthor ()) ? genScheme.getFunctionAuthor () : "");
        model.put ("functionVersion", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        model.put (
                "urlPrefix",
                model.get ("moduleName")
                        + (StringUtils.isNotBlank (genScheme.getSubModuleName ()) ? "/" + StringUtils.lowerCase (genScheme.getSubModuleName ()) : "") + "/"
                        + model.get ("className"));
        model.put ("viewPrefix", // StringUtils.substringAfterLast(model.get("packageName"),".")+"/"+
                model.get ("urlPrefix"));
        model.put (
                "permissionPrefix",
                model.get ("moduleName")
                        + (StringUtils.isNotBlank (genScheme.getSubModuleName ()) ? ":" + StringUtils.lowerCase (genScheme.getSubModuleName ()) : "") + ":"
                        + model.get ("className"));

        model.put ("dbType", Global.getConfig ("jdbc.type"));

        model.put ("table", genScheme.getGenTable ());

        return model;
    }

    /**
     * 生成到文件
     * @param tpl
     * @param model
     * @param replaceFile
     * @return
     */
    public static String generateToFile(GenTemplate tpl,Map<String, Object> model,boolean isReplaceFile){
        // 获取生成文件
        String fileName = Global.getProjectPath ()
                + File.separator
                + StringUtils.replaceEach (FreeMarkers.renderString (tpl.getFilePath () + "/", model), new String[] { "//", "/", "." }, new String[] {
                        File.separator, File.separator, File.separator }) + FreeMarkers.renderString (tpl.getFileName (), model);
        logger.debug (" fileName === " + fileName);

        // 获取生成文件内容
        String content = FreeMarkers.renderString (StringUtils.trimToEmpty (tpl.getContent ()), model);
        logger.debug (" content === \r\n" + content);

        // 如果选择替换文件，则删除原文件
        if (isReplaceFile) {
            FileUtils.deleteFile (fileName);
        }

        // 创建并写入文件
        if (FileUtils.createFile (fileName)) {
            FileUtils.writeToFile (fileName, content, true);
            logger.debug (" file create === " + fileName);
            return "生成成功：" + fileName + "<br/>";
        } else {
            logger.debug (" file extents === " + fileName);
            return "文件已存在：" + fileName + "<br/>";
        }
    }

    public static String generateCode(GenScheme genScheme){

        StringBuilder result = new StringBuilder ();

        // 查询主表及字段列
        GenTable genTable = genScheme.getGenTable ();

        // 获取所有代码模板
        GenConfig config = GenUtils.getConfig ();

        // 获取模板列表
        List<GenTemplate> templateList = GenUtils.getTemplateList (config, genScheme.getCategory (), false);

        // 生成主表模板代码
        genScheme.setGenTable (genTable);
        Map<String, Object> model = GenUtils.getDataModel (genScheme);
        for ( GenTemplate tpl : templateList ) {
            result.append (GenUtils.generateToFile (tpl, model, genScheme.getReplaceFile ()));
        }
        return result.toString ();
    }

    public static GenTable genTable(String TableName,String dbOwer) throws Exception{
        MysqlManager m = new MysqlManager ();
        m.setSourceName (dbOwer);
        GenTable table = m.getTable (TableName);
        if (table == null) {
            if (table == null) {
                table = new GenTable ();
                // 设置java类
//                table.setClassName (StringUtils.toCapitalizeCamelCase (TableName.substring (TableName.indexOf ("_") + 1, TableName.length ())));
                table.setClassName (StringUtils.toCapitalizeCamelCase(TableName));
            }
            // System.out.println ("生产表为空");
            // throw new Exception ("生产表为空");
        }
        List<GenTableColumn> lc = m.tableColumns (TableName);
        System.out.println (JSON.toJSONString (lc));
        if (lc == null) {
            System.out.println ("表字段为空");
            throw new Exception ("表字段为空");
        }
        table.setColumnList (lc);
        return table;
    }

    public static void main(String[] args){
        try {
            String dbOwer = "TCInternalCarOrder";
            String tableName = "sound_video_auth";// 修改
            GenScheme genScheme = new GenScheme ();
            /**
             *   name;                 // 名称
             *   category;             // 分类
             *   packageName;          // 生成包路径
             *   moduleName;           // 生成模块名
             *   subModuleName;        // 生成子模块名
             *   functionName;         // 生成功能名
             *   functionNameSimple;   // 生成功能名（简写）
             *   functionAuthor;       // 生成功能作者
             *   genTable;             // 业务表名
             *   flag;                 // 0：保存方案； 1：保存方案并生成代码
             *   replaceFile;          // 是否替换现有文件 0：不替换；1：替换文件
             */
            genScheme.setName ("生成单表增删改查");
            genScheme.setCategory ("dao");
            genScheme.setPackageName ("com.ly.local");
            genScheme.setModuleName ("common");// 修改
            genScheme.setSubModuleName ("");
            genScheme.setFunctionName ("用户表");// 修改
            genScheme.setFunctionNameSimple ("用户表");// 修改
            genScheme.setFunctionAuthor ("fu.zhou");// 修改
            genScheme.setFlag ("0");
            genScheme.setReplaceFile (true);
            genScheme.setGenTable (genTable (tableName,dbOwer));
            String result = generateCode (genScheme);
            System.out.println (result);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}

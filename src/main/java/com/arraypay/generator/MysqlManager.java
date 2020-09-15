package com.arraypay.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.arraypay.generator.entity.GenTable;
import com.arraypay.generator.entity.GenTableColumn;
import com.arraypay.generator.util.StringUtils;

/**********************************************
*
 * MySql数据库映射
*
 * @author Hxdong
*
 * @time 2014年12月2日 下午4:16:45
**********************************************/

public class MysqlManager {

    /** 表空间名 */
    private String sourceName;

    /**
    *
     * 获取数据库当前用户对应的所有表
    *
     * @return 表对象集合
    */
    public List<GenTable> tableList() throws Exception{
        Connection connection = JdbcUnits.getConnection ();
        PreparedStatement pst = connection
                .prepareStatement ("SELECT TABLE_NAME NAME,TABLE_TYPE TYPE,CREATE_TIME CREATETIME from INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?");
        // 设置数据源名

        pst.setString (1, this.sourceName);
        // 执行查询

        ResultSet rst = pst.executeQuery ();
        List<GenTable> list = new ArrayList<GenTable> ();
        while (rst.next ()) {
            GenTable table = new GenTable ();
            // 设置数据源

            // 设置表名

            table.setName (rst.getString ("name").toUpperCase ());
            // 设置备注名

            table.setName (rst.getString ("name"));
            // 设置表类型

            list.add (table);
        }
        // 关闭链接

        rst.close ();
        connection.close ();
        return list;
    }

    /**
     * 获取指定表
    *
     * @param tableName 表名
     * @return 表对象属性
    */
    public GenTable getTable(String tableName) throws Exception{
        Connection connection = JdbcUnits.getConnection ();
        PreparedStatement pst = connection
                .prepareStatement ("SELECT TABLE_NAME NAME,TABLE_TYPE TYPE,CREATE_TIME CREATETIME ,TABLE_COMMENT comment from INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME=?");
        // 设置数据库名

        pst.setString (1, this.sourceName);
        // 设置表名

        pst.setString (2, tableName);
        // 执行查询

        ResultSet rst = pst.executeQuery ();
        GenTable table = null;
        while (rst.next ()) {
            table = new GenTable ();

            // 设置数据源

            // table.setSourceId (this.id);
            // 设置表名
            table.setComments (rst.getString ("comment"));
            // 设置备注名

            table.setName (rst.getString ("name"));
            String s = rst.getString ("name");
            // 设置java类
//            table.setClassName (StringUtils.toCapitalizeCamelCase (s.substring (s.indexOf ("_") + 1, s.length ())));
            table.setClassName (StringUtils.toCapitalizeCamelCase(s));
            // 设置表类型

            /*
             * if ("BASE TABLE".equals (rst.getString ("type"))) {
             * table.setType ("1");
             * } else if ("VIEW".equals (rst.getString ("type"))) {
             * table.setType ("2");
             * }
             */
            // 设置表更新时间

            // table.setDbCreateTime (parseDate (rst.getString ("createTime")));
        }
        // 关闭链接
        JdbcUnits.free (connection, pst, rst);
        return table;
    }

    /**
     * 获取表对应的所有字段名称
    *
     * @param tableName 表名
     * @return 表字段
     * @throws Exception
    */
    public List<GenTableColumn> tableColumns(String tableName) throws Exception{
        // 获取数据库链接
        GenTable talbe = getTable (tableName);

        Connection connection = JdbcUnits.getConnection ();
        PreparedStatement pst = connection
                .prepareStatement ("SELECT COLUMN_NAME columnName,COLUMN_COMMENT columnComment,COLUMN_TYPE clType,IS_NULLABLE isNullable, COLUMN_KEY isPK from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME=?");
        // 设置数据库名

        pst.setString (1, this.sourceName);
        // 设置表名

        pst.setString (2, tableName);
        // 执行查询

        ResultSet rst = pst.executeQuery ();
        List<GenTableColumn> tableColumns = new ArrayList<GenTableColumn> ();
        
        while (rst.next ()) {
            GenTableColumn column = new GenTableColumn ();
            column.setGenTable (talbe);
            column.setName (rst.getString ("columnName"));
            column.setComments (rst.getString ("columnComment"));
            column.setJdbcType (rst.getString ("clType"));
//            if (StringUtils.startsWithIgnoreCase (column.getName (), "create_by") || StringUtils.startsWithIgnoreCase (column.getName (), "update_by")) {
//                column.setJavaType (User.class.getName ());
//                column.setJavaField (StringUtils.toCamelCase (rst.getString ("columnName")) + ".id");
//            } else {
//                column.setJavaType (typeTrans (rst.getString ("clType")));
//                column.setJavaField (StringUtils.toCamelCase (rst.getString ("columnName")));
//            }

            column.setJavaType (typeTrans (rst.getString ("clType")));
            column.setJavaField (StringUtils.toCamelCase (rst.getString ("columnName")));

            column.setIsPk (rst.getString ("isPK").equals ("PRI") ? "1" : "0");
            column.setIsNull (rst.getString ("isNullable").equals ("YES") ? "1" : "0");
            column.setIsInsert ("1");
            column.setIsEdit (isNotEditField (rst.getString ("columnName")) ? "0" : "1");
            column.setIsList (isNotListField (rst.getString ("columnName")) ? "0" : "1");
            column.setQueryType ("1");
            column.setShowType ("1");
            column.setIsNew (isNotNewField (rst.getString ("columnName")) ? "0" : "1");
            // 获取字段列名
            tableColumns.add (column);
        }
        // 关闭链接
        JdbcUnits.free (connection, pst, rst);
        return tableColumns;
    }

    /** 
     * mysql的类型转换到java 类型参考文章 
     *  
     */
    public String typeTrans(String type){
        if (type.contains ("tinyint")) {
            return "boolean";
        } else if (type.contains ("decimal")) {
            return "BigDecimal";
        } else if (type.contains ("bigint")) {
            return "Long";
        }else if (type.contains ("int")) {
            return "Integer";
        } else if (type.contains ("varchar") || type.contains ("text") || type.contains ("enum") || type.contains ("set")) {
            return "String";
        } else if (type.contains ("date") || type.contains ("time") || type.contains ("datetime") || type.contains ("timestamp") || type.contains ("year")) {
            return "java.util.Date";
        } else if (type.contains ("binary") || type.contains ("blob")) {
            return "byte[]";
        } else {
            return "String";
        }
    }

    private boolean isNotEditField(String filed){
        boolean r = false;
        if (filed.equalsIgnoreCase ("ID") || filed.equalsIgnoreCase ("create_by") || filed.equalsIgnoreCase ("create_date")
                || filed.equalsIgnoreCase ("del_flag")) {
            r = true;
        }
        return r;
    }

    private boolean isNotListField(String filed){
        boolean r = false;
        if (filed.equalsIgnoreCase ("ID") || filed.equalsIgnoreCase ("create_by") || filed.equalsIgnoreCase ("create_date")
                || filed.equalsIgnoreCase ("del_flag") || filed.equalsIgnoreCase ("update_date") || filed.equalsIgnoreCase ("update_by")
                || filed.equalsIgnoreCase ("version")) {
            r = true;
        }
        return r;
    }

    private boolean isNotNewField(String filed){
        boolean r = false;
        if (filed.equalsIgnoreCase ("ID") || filed.equalsIgnoreCase ("create_by") || filed.equalsIgnoreCase ("create_date")
                || filed.equalsIgnoreCase ("del_flag") || filed.equalsIgnoreCase ("update_date") || filed.equalsIgnoreCase ("update_by")
                || filed.equalsIgnoreCase ("version") || filed.equalsIgnoreCase ("remarks")) {
            r = true;
        }
        return r;
    }

    /**
     * 用于获取数据源中的表
    *
     * Myql设置数据库名称 ; Oracle设置用户名称
    *
     * @param name
    */
    public void setSourceName(String name){
        this.sourceName = name;
    }

}
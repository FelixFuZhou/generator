package com.arraypay.generator;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Component;

/**********************************************
*
 * Oracle数据库映射
*
 * @author Hxdong
*
 * @time 2014年11月2日 下午4:17:47
**********************************************/

public class OracleManager {

    /** 连接池ID(数据源ID) */
    private String id;

    /** 表空间名 */
    private String sourceName;

    /* *//**
         *
         * 获取数据库当前用户对应的所有表
         *
         * @return 表对象集合
         */
    /*
     * public List<DataTable> tableList() throws Exception{
     * YzConnection connection = this.getConnection ();
     * PreparedStatement pst = connection
     * .prepareStatement
     * ("select object_name name,object_type type,TIMESTAMP updateTime,secondary from ALL_OBJECTS a where owner=? and OBJECT_TYPE in ('TABLE','VIEW') and secondary='N' ORDER BY object_name");
     * // 设置数据源名
     * pst.setString (1, this.sourceName.toUpperCase ());
     * // 执行查询
     * ResultSet rst = pst.executeQuery ();
     * List<DataTable> list = new ArrayList<DataTable> ();
     * while (rst.next ()) {
     * DataTable table = new DataTable ();
     * // 设置数据源
     * table.setSourceId (this.id);
     * // 设置表名
     * table.setTableName (rst.getString ("name").toUpperCase ());
     * // 设置备注名
     * table.setName (rst.getString ("name").toLowerCase ());
     * // 设置表类型
     * if ("BASE TABLE".equals (rst.getString ("type")) || "TABLE".equals (rst.getString ("type"))) {
     * table.setType ("1");
     * } else if ("VIEW".equals (rst.getString ("type"))) {
     * table.setType ("2");
     * }
     * // 设置表更新时间
     * table.setDbCreateTime (this.parseDate (rst.getString ("updateTime").replaceFirst (":", "")));
     * list.add (table);
     * }
     * // 关闭链接
     * rst.close ();
     * connection.close ();
     * return list;
     * }
     *//**
       * 获取指定表
       *
       * @param tableName 表名
       * @return 表对象属性
       */
    /*
     * public DataTable getTable(String tableName) throws Exception{
     * YzConnection connection = this.getConnection ();
     * PreparedStatement pst = connection
     * .prepareStatement (
     * "select object_name name,object_type type,TIMESTAMP updateTime,secondary from ALL_OBJECTS a where owner=? and object_name=? and OBJECT_TYPE in ('TABLE','VIEW') and secondary='N' ORDER BY object_name"
     * );
     * // 设置数据库名
     * pst.setString (1, this.sourceName.toUpperCase ());
     * // 设置表名
     * pst.setString (2, tableName.toUpperCase ());
     * // 执行查询
     * ResultSet rst = pst.executeQuery ();
     * DataTable table = null;
     * while (rst.next ()) {
     * table = new DataTable ();
     * // 设置数据源
     * table.setSourceId (this.id);
     * // 设置表名
     * table.setTableName (rst.getString ("name").toUpperCase ());
     * // 设置备注名
     * table.setName (rst.getString ("name").toLowerCase ());
     * // 设置表类型
     * if ("BASE TABLE".equals (rst.getString ("type")) || "TABLE".equals (rst.getString ("type"))) {
     * table.setType ("1");
     * } else if ("VIEW".equals (rst.getString ("type"))) {
     * table.setType ("2");
     * }
     * // 设置表更新时间
     * table.setDbCreateTime (this.parseDate (rst.getString ("updateTime").replaceFirst (":", "")));
     * }
     * // 关闭链接
     * rst.close ();
     * connection.close ();
     * return table;
     * }
     *//**
       * 获取表对应的所有字段名称
       *
       * @param tableName 表名
       * @return 表字段集
       * @throws Exception
       */
    /*
     * public List<TableColumns> tableColumns(String tableName) throws Exception{
     * YzConnection connection = this.getConnection ();
     * PreparedStatement pst = connection
     * .prepareStatement ("select t.column_name name ,t.data_type type ,t.data_precision precision,t.data_scale scale,k.POSITION position"
     * + "from user_tab_columns t left JOIN dba_cons_columns k" + "ON t.column_name= k.column_name" + "and t.table_name=k.table_name"
     * + "and k.POSITION is not null" + "where t.table_name=? ORDER BY k.POSITION,t.COLUMN_NAME");
     * // 设置表名
     * pst.setString (1, tableName.toUpperCase ());
     * // 执行查询
     * ResultSet rst = pst.executeQuery ();
     * List<TableColumns> listColumns = new ArrayList<TableColumns> ();
     * while (rst.next ()) {
     * TableColumns tableColumns = new TableColumns ();
     * // 字段名
     * tableColumns.setColumnName (rst.getString ("name").toUpperCase ());
     * // 设置备注名
     * tableColumns.setName (rst.getString ("name").toLowerCase ());
     * // 获取字段值类型
     * tableColumns.setDataType (rst.getString ("type"));
     * if (rst.getString ("position") == null) {
     * tableColumns.setColumnStatus ("0");
     * } else {
     * tableColumns.setColumnStatus (rst.getString ("position"));
     * }
     * listColumns.add (tableColumns);
     * }
     * // 关闭链接
     * rst.close ();
     * connection.close ();
     * return listColumns;
     * }
     *//**
       * 获取数据库链接
       *
       * @return 数据库链接YzConnection
       * @throws Exception
       */
    /*
     * private YzConnection getConnection() throws Exception{
     * // 获取数据库连接池管理器
     * ConnectionManager connectionManager = SystemBean.CONNECTION_POOL.get (id);
     * if (connectionManager == null) { throw new Exception ("数据库连接池管理器为空......."); }
     * // 获取数据库链接
     * YzConnection connection = connectionManager.getConnection ();
     * if (connection == null) { throw new Exception ("数据库连接Connection为空......."); }
     * return connection;
     * }
     *//**
       * 设置数据源ID
       *
       * @param id 数据源ID
       */
    /*
     * public void setId(String id){
     * this.id = id;
     * }
     *//**
       * 用于获取数据源中的表
       *
       * Myql设置数据库名称 ; Oracle设置用户名称
       * @param name
       */
    /*
     * public void setSourceName(String name){
     * this.sourceName = name;
     * }
     */

}
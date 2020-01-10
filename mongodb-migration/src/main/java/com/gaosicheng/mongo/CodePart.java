package com.gaosicheng.mongo;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author gaosicheng
 * @describe: 片段代码
 * @date: 2020-01-03 10:02
 * @update:
 */
public class CodePart {


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://192.168.1.162:3306/test";
    static final String USER = "root";
    static final String PASS = "123456";

    /**
     * 向某个表新增数据
     * Created by yangkangwei on 2019/12/18
     *
     * @param tableName
     * @param columnNameList
     * @param columnValueList
     * @return
     * @throws SQLException
     */
    public int insertTableData(String tableName, List<String> columnNameList, List<Object> columnValueList) throws SQLException, ClassNotFoundException {
        int result = 0;


        if (columnNameList == null || columnNameList.size() == 0 || columnValueList == null || columnValueList.size() == 0) {
            return result;
        }


        StringBuffer columnName = new StringBuffer();
        StringBuffer columnValue = new StringBuffer();
        for (int i = 0; i < columnNameList.size(); i++) {
            columnName.append("," + columnNameList.get(i));

            Object object = columnValueList.get(i);
            if (object instanceof String) {
                columnValue.append(",'" + object.toString() + "'");
            } else if (object instanceof Byte) {
                columnValue.append("," + ((Byte) object).byteValue());
            } else if (object instanceof Short) {
                columnValue.append("," + ((Short) object).shortValue());
            } else if (object instanceof Integer) {
                columnValue.append("," + ((Integer) object).intValue());
            } else if (object instanceof Long) {
                columnValue.append("," + ((Long) object).longValue());
            } else if (object instanceof Float) {
                columnValue.append("," + ((Float) object).floatValue());
            } else if (object instanceof Double) {
                columnValue.append("," + ((Double) object).doubleValue());
            } else if (object instanceof Boolean) {
                columnValue.append("," + (((Boolean) object).booleanValue() ? 1 : 0));
            } else if (object instanceof Date) {
                columnValue.append(",'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format((Date) object) + "'");
            } else {
                columnValue.append(",'" + object.toString() + "'");
            }
        }
        String sql = "INSERT INTO `" + tableName + "` (" + columnName.toString().substring(1) + ") VALUES (" + columnValue.toString().substring(1) + ")";
        Connection conn = getPreparedStatement();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        result = preparedStatement.executeUpdate();
        conn.close();
        preparedStatement.close();
        return result;
    }

    public Connection getPreparedStatement(){
        Connection conn = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}


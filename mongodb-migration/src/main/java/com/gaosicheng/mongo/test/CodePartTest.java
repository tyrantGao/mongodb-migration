package com.gaosicheng.mongo.test;

import com.gaosicheng.mongo.CodePart;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gaosicheng
 * @describe: 片段代码测试
 * @date: 2020-01-03 10:38
 * @update:
 */
public class CodePartTest {

    private List<String> columnNameList;
    private List<Object> columnValueList;


    @Before
    public void createTestData(){
        columnNameList = new ArrayList<String>();
        columnValueList = new ArrayList<Object>();

        columnNameList.add("test_str");
        columnNameList.add("test_byte");
        columnNameList.add("test_short");
        columnNameList.add("test_int");
        columnNameList.add("test_long");
        columnNameList.add("test_float");
        columnNameList.add("test_double");
        columnNameList.add("test_date");
        columnNameList.add("test_boolean");

        columnValueList.add("123456");
        columnValueList.add((byte) 1);
        columnValueList.add((short) 1);
        columnValueList.add(1);
        columnValueList.add((long) 1);
        columnValueList.add((float) 1);
        columnValueList.add((double) 1);
        columnValueList.add(new Date());
        columnValueList.add(true);
    }

    @Test
    public void test(){
        CodePart codePart = new CodePart();
        String tableName = "test_table";
        try {
            codePart.insertTableData(tableName,columnNameList,columnValueList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

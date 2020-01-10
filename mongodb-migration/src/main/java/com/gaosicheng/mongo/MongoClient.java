package com.gaosicheng.mongo;

/**
 * @author gaosicheng
 * @describe: 数据库迁移的启动类
 * @date: 2019-12-20 16:50
 * @update:
 */
public class MongoClient {

    public static void main(String[] args) {
            MongoUtil mongoDemo = new MongoUtil();
            mongoDemo.mongodbMigration();
    }
}

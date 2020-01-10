package com.gaosicheng.mongo.test;

import com.gaosicheng.mongo.MongoUtil;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

/**
 * @author gaosicheng
 * @describe: 数据库迁移demo的单元测试
 * @date: 2019-12-20 16:10
 * @update:
 */
public class MongoUtilTest {

    MongoUtil mongoDemo = new MongoUtil();
    MongoDatabase srcDatabase;
    MongoDatabase targetDatabase;
    MongoCursor<Document> mongoCursor;
    String collectionName;

    @Before
    public void testSrcConnection() {
        collectionName = "robot_signals.version_nums";
        srcDatabase = mongoDemo.getSrcMongoDatabase();
        MongoCollection<Document> collection = srcDatabase.getCollection(collectionName);
        FindIterable<Document> findIterable = collection.find();
        mongoCursor = findIterable.iterator();
    }

    @Before
    public void testTargetConnection() {
        targetDatabase = mongoDemo.getTargetMongoDatabase();
    }

    @Test
    public void testInsertDataToLocal() {
        mongoDemo.insertDataToLocal(targetDatabase, mongoCursor, collectionName);
    }

    @Test
    public void testMigrationDataRecord() {
        mongoDemo.migrationDataRecord("这是一次测试记录");
    }

    @Test
    public void testMongodbMigration() {
        mongoDemo.mongodbMigration();
    }
}

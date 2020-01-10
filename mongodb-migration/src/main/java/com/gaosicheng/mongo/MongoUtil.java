package com.gaosicheng.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author gaosicheng
 * @describe: mongodb数据库迁移demo
 * @date: 2019-12-20 10:34
 * @update:
 */
public class MongoUtil {

    public static Logger Log = LoggerFactory.getLogger(MongoUtil.class);

    /**
     * 获取源数据库
     * arctic_strategy
     *
     * @return
     */
    public MongoDatabase getSrcMongoDatabase() {
        // 测试连接地址
        String testUri = "mongodb://reader:root123@123.103.74.231:27717/arctic_strategy";
        //创建mongodb连接地址
        MongoClientURI uri = new MongoClientURI(testUri);
        //连接mongodb数据库
        MongoClient client = new MongoClient(uri);
        // 获取测试数据库
        MongoDatabase testDatabase = client.getDatabase("arctic_strategy");
        return testDatabase;
    }


    /**
     * 获取目标数据库
     *
     * @return
     */
    public MongoDatabase getTargetMongoDatabase() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase targetDatabase = mongoClient.getDatabase("arctic_strategy");
        return targetDatabase;
    }


    public void insertDataToLocal(MongoDatabase targetDatabase, MongoCursor<Document> mongoCursor, String collectionName) {
        Log.info("开始" + collectionName + "集合的数据迁移");
        targetDatabase.createCollection(collectionName);
        MongoCollection<Document> localCollection = targetDatabase.getCollection(collectionName);
        List<Document> documents = new ArrayList<Document>();
        if (!mongoCursor.hasNext()) {
            return;
        }
        while (mongoCursor.hasNext()) {
            documents.add(mongoCursor.next());
        }
        localCollection.insertMany(documents);
        migrationDataRecord("集合" + collectionName + "迁移成功!");
        Log.info("集合" + collectionName + "迁移成功!");
    }

    public void migrationDataRecord(String record) {
        try {
            String filePath = "/Users/gaosicheng/Documents/IdeaProjects/mongodb-migration/log/records.txt";
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file, true);
            writer.write(record + "\r\n");
            writer.close();
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }


    public void mongodbMigration() {
        try {
            MongoDatabase testDatabase = getSrcMongoDatabase();
            MongoDatabase targetDatabase = getTargetMongoDatabase();
            MongoIterable<String> collectionNames = testDatabase.listCollectionNames();
            String blacklist = "signal,robot_signals.version_nums";
            for (String collectionName : collectionNames) {
                if (blacklist.contains(collectionName)) {
                    continue;
                }
                Log.info("开始遍历集合" + collectionName);
                MongoCollection<Document> testCollection = testDatabase.getCollection(collectionName);
                FindIterable<Document> findIterable = testCollection.find();
                MongoCursor<Document> mongoCursor = findIterable.iterator();
                insertDataToLocal(targetDatabase, mongoCursor, collectionName);
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

}

package org.halifaxfoodie;

import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.*;

public class ExtractEntitiesFunction implements RequestHandler<Map<String, String>, Map<String, String>[]> {

    @Override
    public Map<String, String>[] handleRequest(Map<String, String> event, Context context) {
        DetectEntities detectEntities = new DetectEntities();
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        String tableName = "HalifaxFoodieOrders";
        List<String> reviews = new ArrayList<>();
        Map<String, Integer> entities = new HashMap<>();

        try {
            ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName).withAttributesToGet("CustomerFeedback");
            ScanResult result = client.scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()) {
                Set<String> keys = item.keySet();
                for (String key : keys) {
                    reviews.add(item.get(key).getS());
                }
            }
        } catch (AmazonDynamoDBException e) {
            e.getStackTrace();
        }

        AmazonComprehend comprehend = AmazonComprehendClientBuilder.standard().build();

        for (String review : reviews) {
            String namedEntity = detectEntities.detectEntity(comprehend, review).toUpperCase().trim();
            if (!namedEntity.equals("")) {
                if (entities.containsKey(namedEntity)) {
                    int freq = entities.get(namedEntity) + 1;
                    entities.put(namedEntity, freq);
                } else {
                    entities.put(namedEntity, 1);
                }
            }
        }

        Map<String, String>[] words = new Map[entities.size()];
        List<Map<String, String>> wordList = new ArrayList<>();
        for (String entity : entities.keySet()) {
            Map<String, String> wordDetails = new HashMap<>();
            wordDetails.put("text", entity);
            wordDetails.put("value", entities.get(entity).toString());
            wordList.add(wordDetails);
        }
        words = wordList.toArray(words);
        return words;
    }
}
package org.chatbot;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class OrderStatus implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {

        Object currentIntent = event.get("currentIntent");
        String input = currentIntent.toString();
        int indexOfOrder = input.indexOf("OrderNumber") + 12;
        int indexOfOrderEnd = input.indexOf("}", indexOfOrder);
        int orderNumber = Integer.parseInt(input.substring(indexOfOrder, indexOfOrderEnd));
        System.out.println("Order Number: " + orderNumber);

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "HalifaxFoodieOrders";

        Table table = dynamoDB.getTable(tableName);
        String orderStatus = "";
        try {
            Item item = table.getItem("OrderID", orderNumber);
            orderStatus = item.getString("OrderStatus");
        } catch (Exception e) {
            System.out.println("Data retrieval failed");
            System.err.println(e.getMessage());
        }

        String outputMessage = "";
        if (orderStatus.trim().equals("")) {
            outputMessage = "You have entered invalid order number";
        } else {
            outputMessage = "Your order is " + orderStatus +
                ". Please let us know if your are happy with the service.";
        }

        Map<String, Object> finalJson = new HashMap<>();
        Map<String, Object> dialog = new HashMap<>();
        Map<String, String> content = new HashMap<>();

        content.put("contentType", "PlainText");
        content.put("content", outputMessage);

        dialog.put("type", "Close");
        dialog.put("fulfillmentState", "Fulfilled");
        dialog.put("message", content);

        finalJson.put("dialogAction", dialog);

        return finalJson;
    }
}
package org.chatbot;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class NegativeReview implements RequestHandler<Map<String, Object>, Map<String, Object>> {

  @Override
  public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {

    Object currentIntent = event.get("recentIntentSummaryView");
    String input = currentIntent.toString();
    int indexOfOrder = input.indexOf("OrderNumber") + 12;
    int indexOfOrderEnd = input.indexOf("}", indexOfOrder);
    int orderNumber = Integer.parseInt(input.substring(indexOfOrder, indexOfOrderEnd));
    System.out.println("Order Number: " + orderNumber);

    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
    DynamoDB dynamoDB = new DynamoDB(client);

    String tableName = "HalifaxFoodieOrders";

    Table table = dynamoDB.getTable(tableName);
    try {
      UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("OrderID", orderNumber)
          .withUpdateExpression("set OrderReview = :r").withValueMap(new ValueMap().withString(":r", "Not Satisfied"))
          .withReturnValues(ReturnValue.UPDATED_NEW);
      table.updateItem(updateItemSpec);
      System.out.println("Data updated");
    } catch (Exception e) {
      System.out.println("Data update failed");
      System.err.println(e.getMessage());
    }

    Map<String, Object> finalJson = new HashMap<>();
    Map<String, Object> dialog = new HashMap<>();
    Map<String, String> content = new HashMap<>();

    content.put("contentType", "PlainText");
    content.put("content", "We have updated the review and please visit the link" +
        " http://halifaxfoodie-env.eba-25vprp9n.us-east-1.elasticbeanstalk.com/ " +
        "to start conversation with the customer representative");

    dialog.put("type", "Close");
    dialog.put("fulfillmentState", "Fulfilled");
    dialog.put("message", content);

    finalJson.put("dialogAction", dialog);
    return finalJson;
  }
}

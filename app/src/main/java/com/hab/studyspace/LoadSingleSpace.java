package com.hab.studyspace;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yidanzeng on 2/7/16.
 */
public class LoadSingleSpace extends IntentService {

    public LoadSingleSpace() {
        super("LoadSingleSpace");
    }

    @Override
    protected void onHandleIntent(Intent workIntent){

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                this,
                Constants.IDENTITY_POOL_ID, // Identity Pool ID
                Regions.US_EAST_1 // Region
        );

        final AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);

        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
        StudySpace result = mapper.load(StudySpace.class, workIntent.getStringExtra(Spaceview.SPACE_VIEW));

        System.out.println("EXTRA " + Spaceview.SPACE_VIEW);
        System.out.println("GET NAMEEEE " + result.getName());


        Intent intent = new Intent("LoadSingleSpace");
        JSONObject js = new JSONObject();

        try {
            js.put("name", result.getName());
            js.put("capacity", result.getPeople());
            js.put("details", result.getDetails());
            int end = Integer.valueOf(result.getEnd().get(0));
            int start = Integer.valueOf(result.getStart().get(0));
            String endtime = convertTime(end);
            String starttime = convertTime(start);

            js.put("end", endtime);
            js.put("start", starttime);

            System.out.println("JSON : " + js.toString());

        } catch (JSONException e) {
            System.out.println("JSON EXCEPTION");
        }

        intent.putExtra("singleSpace", js.toString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    public String convertTime(int time) {
        String toReturn = "";

            if(time == 12) {
                toReturn += Integer.toString(time) + "PM";
            } else if (time > 12) {
                toReturn = time % 12 + "PM";
            } else if (time == 24) {
                toReturn = 1 + "AM";
            } else {
                toReturn += Integer.toString(time) + "AM";
            }

        return toReturn;
    }
}

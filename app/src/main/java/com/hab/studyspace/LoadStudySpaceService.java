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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ivy on 2/6/16.
 */
public class LoadStudySpaceService extends IntentService {

    public LoadStudySpaceService() {
        super("LoadStudySpaceService");
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
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        StudySpace result = mapper.load(StudySpace.class, "Wilson 204");
        PaginatedScanList<StudySpace> all = mapper.scan(StudySpace.class, scanExpression);
        System.out.println(result);
        JSONArray places = new JSONArray();
        for (StudySpace space : all){
            System.out.println(space);

            String spaceJson = String.format("{ 'type':'Feature', 'geometry': { 'type': 'Point', 'coordinates': [%f, %f] },"
                + "'properties': { 'name': '%s', 'n-people': %d, 'details': '%s', 'occupied': %b, 'public': %b, 'rating': %d, 'start': [ %d, %d ], 'end': [%d, %d], 'image-links': %s }}",
                    space.getLng(), space.getLat(), space.getName(), space.getPeople(), space.getDetails(), space.isOccupied(), space.isPublicAccess(), space.getRating(),
                    space.getStart().get(0), space.getStart().get(1), space.getEnd().get(0), space.getEnd().get(1), Utils.printList(space.getImageLinks()));
            System.out.println(spaceJson);
            JSONObject place;
            try {
                place = new JSONObject(spaceJson);
                System.out.println(place);
                places.put(place);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println(places);
        String placesJson = String.format("{ 'type': 'FeatureCollection', 'features': %s }", places);

        Intent intent = new Intent("LoadStudySpaceService");
        intent.putExtra("json", placesJson);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}

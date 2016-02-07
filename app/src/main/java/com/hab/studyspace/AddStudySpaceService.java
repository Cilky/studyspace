package com.hab.studyspace;

import android.app.IntentService;
import android.content.Intent;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 * Created by ivy on 2/6/16.
 */
public class AddStudySpaceService extends IntentService {

    public AddStudySpaceService() {
        super("AddStudySpaceService");
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

        StudySpace space = new StudySpace();
        space.setName("Solomon " + Math.random()*100);
        mapper.save(space);
    }

}

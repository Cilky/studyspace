package com.hab.studyspace;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ivy on 2/6/16.
 */
public class AddStudySpaceService extends IntentService {

    Context mContext;
    Activity mActivity;

    public AddStudySpaceService() {
        super("AddStudySpaceService");
    }

    public AddStudySpaceService(Context context) {
        super("AddStudySpaceService");
        mContext = context;
        mActivity = (Activity) mContext;
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

        String json = workIntent.getStringExtra("data");
        System.out.println(json);

        try {
            JSONObject data = new JSONObject(json);

            StudySpace space = new StudySpace();
            space.setName(data.getString("name"));
            space.setDetails(data.getString("details"));
            space.setPeople(data.getInt("capacity"));
            space.setRating(data.getInt("rating"));
            space.setLat(data.getDouble("lat"));
            space.setLng(data.getDouble("long"));

            List<Integer> start = new ArrayList<>();
            List<Integer> end = new ArrayList<>();

            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
            Calendar c = Calendar.getInstance();

            Date date = formatter.parse(data.getString("start"));
            c.setTime(date);
            start.add(c.get(Calendar.HOUR_OF_DAY));
            start.add(c.get(Calendar.MINUTE));
            space.setStart(start);

            date = formatter.parse(data.getString("end"));
            c.setTime(date);
            end.add(c.get(Calendar.HOUR_OF_DAY));
            end.add(c.get(Calendar.MINUTE));
            space.setEnd(end);

            mapper.save(space);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

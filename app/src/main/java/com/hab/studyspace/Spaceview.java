package com.hab.studyspace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yidanzeng on 2/6/16.
 *
 * ACTIVITY: View when user is looking at a single room.
 */
public class Spaceview extends FragmentActivity {
    TextView name;
    TextView cap;
    TextView det;
    TextView hour;
    private Intent spaceIntent;
    public final static String SPACE_VIEW = "com.hab.studyspace.MESSAGE";
    //ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaceview);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MapsActivity.EXTRA_MESSAGE);
        System.out.println("Message in the spaceview intent " + message);
        spaceIntent = new Intent(Spaceview.this, LoadSingleSpace.class);
        spaceIntent.putExtra(SPACE_VIEW, message);
        Spaceview.this.startService(spaceIntent);
        LocalBroadcastManager.getInstance(this).registerReceiver(mDataReceiver, new IntentFilter("LoadSingleSpace"));

    }

    private BroadcastReceiver mDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("I'm in data, before json");
            try {
                JSONObject place = new JSONObject(intent.getStringExtra("singleSpace"));
                System.out.println("I'm in DATA");


                    System.out.println("HERE!!!");
                    name = (TextView) findViewById(R.id.space_name);
                    System.out.println(place.getString("name"));
                    name.setText(place.getString("name"));

                    cap = (TextView) findViewById(R.id.space_cap);
                    System.out.println(place.getString("capacity"));
                    cap.setText(place.getString("capacity"));

                    det = (TextView) findViewById(R.id.space_det);
                    System.out.println(place.getString("details"));
                    det.setText(place.getString("details"));

                    hour = (TextView) findViewById(R.id.space_hours);
                    String time = place.getString("start") + " - " + place.getString("end");
                    hour.setText(time);

                    //image = (ImageView) findViewById(R.id.space_img);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };



}

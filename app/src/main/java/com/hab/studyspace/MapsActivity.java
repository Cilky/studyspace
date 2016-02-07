package com.hab.studyspace;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;

import android.content.IntentFilter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GeoJsonLayer mLayer;
    private Intent mServiceIntent;
    private ArrayList<Marker> markerArray;
    private String m_Text;
    private Location cPos;
    private Marker closestMarker;
    private LatLng mPos;
    private int curMarker = 0;
    static final int PICK_LOCATION = 0;
    public final static String EXTRA_MESSAGE = "com.hab.studyspace.MESSAGE";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        markerArray = new ArrayList<Marker>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        mServiceIntent = new Intent(MapsActivity.this, LoadStudySpaceService.class);

        Button mLoadButton = (Button) findViewById(R.id.loadSpace);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.this.startService(mServiceIntent);
            }
        });

        try {
            mLayer = new GeoJsonLayer(mMap, new JSONObject("{}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mDataReceiver, new IntentFilter("LoadStudySpaceService"));
        MapsActivity.this.startService(mServiceIntent);
    }

    private BroadcastReceiver mDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                JSONObject places = new JSONObject(intent.getStringExtra("json"));
                System.out.println("Got json data");
                System.out.println(places);

                if(mLayer != null) {
                    mLayer.removeLayerFromMap();
                }

                mLayer = new GeoJsonLayer(mMap, places);

                GeoJsonPointStyle pointStyle = mLayer.getDefaultPointStyle();
                pointStyle.setIcon(BitmapDescriptorFactory.defaultMarker());

                for (GeoJsonFeature feature : mLayer.getFeatures()) {
                    GeoJsonPointStyle style = new GeoJsonPointStyle();
                    style.setTitle(feature.getProperty("name"));
                    style.setSnippet(feature.getProperty("details"));
                    feature.setPointStyle(style);
                }

                mLayer.addLayerToMap();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public LatLng getLocation() {
        LatLng Cali = new LatLng(-48, 151);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                new AlertDialog.Builder(MapsActivity.this)
                        .setTitle("Your closest study spot is...")
                        .setMessage(calculateClosest().getTitle())
                        .setPositiveButton("Tell me More!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })


                        .setIcon(android.R.drawable.ic_menu_compass)
                        .show();

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.GPS_PROVIDER;
                Location myLocation;

                try {
                    myLocation = locationManager.getLastKnownLocation(provider);
                    System.out.println("hi");
                    if (myLocation != null) {

                        System.out.println("i did it!" + myLocation.getAltitude());
                    }
                } catch (SecurityException e) {
                    System.out.println("oh no");
                }

                return false;
            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                final LatLng curMark = new LatLng(point.latitude, point.longitude);

                Intent intent = new Intent(MapsActivity.this, AddSpaceActivity.class);
                intent.putExtra("lat", point.latitude);
                intent.putExtra("lon", point.longitude);

                MapsActivity.this.startActivity(intent);
                /*AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

                builder.setTitle("Name of Spot;Hours;Rating(1-5)");

                final EditText input = new EditText(MapsActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        markerArray.add(mMap.addMarker(new MarkerOptions().position(curMark).title(m_Text)));
                        System.out.println("Longitude: " + curMark.latitude + "Latitude " + curMark.longitude);
                        System.out.println(markerArray.size());
                        System.out.println(m_Text);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();*/

            }
        });
        return Cali;
    }


    public Marker calculateClosest() {
        Double curDistance = 5000000000000.0;
        for (int i = 0; i < markerArray.size(); i++) {

            mPos = markerArray.get(i).getPosition();

            Double mLat = mPos.latitude;
            Double mLong = mPos.longitude;

            LatLng cLatLng = new LatLng(cPos.getLatitude(), cPos.getLongitude());

            double distanceBetween = SphericalUtil.computeDistanceBetween(mPos, cLatLng);


            if (distanceBetween < curDistance) {
                curDistance = SphericalUtil.computeDistanceBetween(mPos, cLatLng);
                closestMarker = markerArray.get(i);

            }
        }
        return closestMarker;

    }


    public void onMapReady(GoogleMap googleMap) {
        System.out.println("should work");
        mMap = googleMap;

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            System.out.println("fff");
        }

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location _cPos) {
                // TODO Auto-generated method stub
                cPos = _cPos;
            System.out.println("Latitude: " + cPos.getLatitude() + "Longitude: " + cPos.getLongitude());
            }
        });
 /*       LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria,true);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        if(myLocation == null){
            System.out.println("empty");
            Double latitude = sydney.latitude;
            Double longitude = sydney.longitude;
        }
        else{
            Location myLoc = locationManager.getLastKnownLocation(provider);
            Double longitude = myLoc.getLongitude();
            Double latitude = myLoc.getLatitude();
            System.out.println("Cur Long" + longitude);
        }
    }


    // populating with markers
  */
        //mMap.setOnMarkerClickListener(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            //Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast

            @Override
            public boolean onMarkerClick(Marker arg0) {
                if (arg0.getTitle().equals("Hello world")) {// if marker source is clicked

                    Intent intent = new Intent(MapsActivity.this, Multiview.class);
                    String message = arg0.getPosition().toString();
                    intent.putExtra(EXTRA_MESSAGE, message);

                    startActivityForResult(intent, PICK_LOCATION);

                } else if (arg0.getTitle().equals("Here")) {
                    Intent intent = new Intent(MapsActivity.this, Spaceview.class);
                    String message = arg0.getPosition().toString();
                    intent.putExtra(EXTRA_MESSAGE, message);

                    startActivityForResult(intent, PICK_LOCATION);
                }

                return true;
            }
        });

        this.getLocation();

    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.hab.studyspace/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.hab.studyspace/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}


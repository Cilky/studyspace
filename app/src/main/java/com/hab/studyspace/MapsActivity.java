package com.hab.studyspace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<Marker> markerArray;
    private String m_Text;
    private LatLng curLocation;
    private Location curPoint;
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
        curLocation = new LatLng(41, 71);
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
    }

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
                final LatLng curPoint = new LatLng(point.latitude, point.longitude);
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Name of Spot;Hours;Rating(1-5)");


                final EditText input = new EditText(MapsActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        if (m_Text.equals("Closest") || m_Text.equals("closest")) ;
                        {
                            //      this.calculateClosest();
                        }
                        markerArray.add(mMap.addMarker(new MarkerOptions().position(curPoint).title(m_Text)));
                        System.out.println("Longitude: " + curPoint.latitude + "Latitude " + curPoint.longitude);
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

                builder.show();
            }
        });
        return Cali;
    }


    public Marker calculateClosest() {
        Double curDistance = 50000000000000.0;
        Marker closestMarker = null;

        for (int i = 0; i < markerArray.size(); i++) {

            LatLng mPos = markerArray.get(i).getPosition();
            Double mLat = mPos.latitude;
            Double mLng = mPos.longitude;


            Double cLat = curPoint.getLatitude();
            Double cLng = curPoint.getLatitude();
            LatLng cLatLng = new LatLng(cLat, cLng);


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

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location curPoint) {
                // TODO Auto-generated method stub
            System.out.println("Latitude: " + curPoint.getLatitude() + "Longitude: " + curPoint.getLongitude());
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
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10, 10))
                .title("Hello world"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20, 20))
                .title("Here"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25, 20))
                .title("There"));

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
                    Intent intent = new Intent(MapsActivity.this, Space.class);
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


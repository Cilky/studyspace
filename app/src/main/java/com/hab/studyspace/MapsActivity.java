package com.hab.studyspace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<Marker> markerArray;
    private String m_Text;
    private LatLng curLocation;
    private int curMarker = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        curLocation = new LatLng(41,71);
        markerArray = new ArrayList<Marker>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        LatLng Cali = new LatLng(-48,151);
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


  /*  public Marker calculateClosest() {

        for(int i=0;i<markerArray.size(); i++ ) {

            LatLng mPos = markerArray.get(i).getPosition();
            Double mLat = mPos.latitude;
            Double mLng = mPos.longitude;


            Double cLat  = curLocation.longitude;
            Double cLng  = curLocation.latitude;

            double distanceBetween = SphericalUtil.compueteDistanceBetween(markerArray.get(i).getPosition(), curLocation)

            if ( closest == -1 || d < distances[closest] ) {
                closest = i;
            }
        }

        alert(mMap.markers[closest].title);
        }
    }
  */  public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        this.getLocation();
        // Add a marker in Sydney and move the camera
    }
}

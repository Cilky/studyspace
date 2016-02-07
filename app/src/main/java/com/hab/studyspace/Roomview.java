package com.hab.studyspace;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by yidanzeng on 2/6/16.
 *
 * ACTIVITY: View when user is looking at a single room.
 */
public class Roomview extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaceview);
    }
}

package com.hab.studyspace;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yidanzeng on 2/6/16.
 *
 * ACTIVITY: View when user selects a marker that has multiple locations (grid view)
 */
public class Multiview extends FragmentActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Space> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        rooms = new ArrayList<Space>();
        rooms.add(new Space("Wilson", false, 23, R.drawable.studyspace));
        rooms.add(new Space("Sayles", false, 5, R.drawable.studyspace));
        rooms.add(new Space("Salomon", true, 3, R.drawable.studyspace));

        // specify an adapter (see also next example)
        mAdapter = new MultiviewAdapter(rooms);
        mRecyclerView.setAdapter(mAdapter);
    }



}
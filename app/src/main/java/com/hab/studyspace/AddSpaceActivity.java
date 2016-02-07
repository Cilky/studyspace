package com.hab.studyspace;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import org.json.JSONException;
import org.json.JSONObject;

public class AddSpaceActivity extends AppCompatActivity {

    private Button mAddButton;
    private Intent mServiceIntent;
    private EditText mName;
    private EditText mDetails;
    private EditText mStartTime;
    private EditText mEndTime;
    private EditText mCapacity;
    private RatingBar mRating;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_space);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mServiceIntent = new Intent(AddSpaceActivity.this, AddStudySpaceService.class);

        mStartTime = (EditText) findViewById(R.id.new_start);
        mStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTimePickerFragment dialog = new StartTimePickerFragment();
                dialog.show(getSupportFragmentManager(), "timePicker");
                System.out.println("clicked");
            }
        });
        mStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    StartTimePickerFragment dialog = new StartTimePickerFragment();
                    dialog.show(getSupportFragmentManager(), "timePicker");
                    System.out.println("clicked");
                }
            }
        });

        mEndTime = (EditText) findViewById(R.id.new_end);
        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndTimePickerFragment dialog = new EndTimePickerFragment();
                dialog.show(getSupportFragmentManager(), "timePicker");
                System.out.println("clicked");
            }
        });
        mEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    EndTimePickerFragment dialog = new EndTimePickerFragment();
                    dialog.show(getSupportFragmentManager(), "timePicker");
                    System.out.println("clicked");
                }
            }
        });

        mName = (EditText) findViewById(R.id.new_name);
        mDetails = (EditText) findViewById(R.id.new_details);
        mRating = (RatingBar) findViewById(R.id.new_rating);
        mCapacity = (EditText) findViewById(R.id.new_capacity);

        mAddButton = (Button) findViewById(R.id.addSpace);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("name", mName.getText().toString());
                    jsonObject.put("details", mDetails.getText().toString());
                    jsonObject.put("start", mStartTime.getText().toString());
                    jsonObject.put("end", mEndTime.getText().toString());
                    jsonObject.put("rating", mRating.getRating());
                    jsonObject.put("capacity", Integer.parseInt(mCapacity.getText().toString()));
                    jsonObject.put("lat", getIntent().getExtras().getDouble("lat"));
                    jsonObject.put("long", getIntent().getExtras().getDouble("lon"));

                    mServiceIntent.putExtra("data", jsonObject.toString());
                    AddSpaceActivity.this.startService(mServiceIntent);
                    AddSpaceActivity.this.finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

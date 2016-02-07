package com.hab.studyspace;

//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by ivy on 2/6/16.
 *
 * SERVICE??: Handling objects to be uploaded (and downloaded?)
 */

//@DynamoDBTable(tableName = "study-spaces")

public class StudySpace {
    private String name;


    //@DynamoDBIndexRangeKey(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

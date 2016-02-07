package com.hab.studyspace;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

/**
 * Created by ivy on 2/6/16.
 */
@DynamoDBTable(tableName = "study-spaces")
public class StudySpace {
    private String name;

    @DynamoDBHashKey(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

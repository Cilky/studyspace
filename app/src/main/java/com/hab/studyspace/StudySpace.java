package com.hab.studyspace;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

/**
 * Created by ivy on 2/6/16.
 */
@DynamoDBTable(tableName = "study-spaces")
public class StudySpace {
    private String name;
    private String details;
    private String[] imageLinks;
    private double lat, lng;
    private int[] start, end;
    private int people, rating;
    private boolean publicAccess, occupied;

    @DynamoDBHashKey(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @DynamoDBAttribute(attributeName = "image-links")
    public String[] getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String[] imageLinks) {
        this.imageLinks = imageLinks;
    }

    @DynamoDBAttribute(attributeName = "geo-lat")
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @DynamoDBAttribute(attributeName = "geo-long")
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @DynamoDBAttribute(attributeName = "start")
    public int[] getStart() {
        return start;
    }

    public void setStart(int[] start) {
        this.start = start;
    }

    @DynamoDBAttribute(attributeName = "end")
    public int[] getEnd() {
        return end;
    }

    public void setEnd(int[] end) {
        this.end = end;
    }

    @DynamoDBAttribute(attributeName = "n-people")
    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    @DynamoDBAttribute(attributeName = "rating")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @DynamoDBAttribute(attributeName = "public")
    public boolean isPublicAccess() {
        return publicAccess;
    }

    public void setPublicAccess(boolean publicAccess) {
        this.publicAccess = publicAccess;
    }

    @DynamoDBAttribute(attributeName = "occupied")
    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

}

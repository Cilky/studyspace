package com.hab.studyspace;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivy on 2/6/16.
 */

@DynamoDBTable(tableName = "study-spaces")
public class StudySpace {
    private String name;
    private String details;
    private List<String> imageLinks;
    private double lat, lng;
    private List<Integer> start, end;
    private int people, rating;
    private boolean publicAccess, occupied;

    public StudySpace(){
        setName("");
        setDetails("");

        List<String> links = new ArrayList<>();
        setImageLinks(links);

        setPeople(-1);
        setRating(-1);
        setPublicAccess(true);
        setOccupied(false);
    }

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
    public List<String> getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(List<String> imageLinks) {
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
    public List<Integer> getStart() {
        return start;
    }

    public void setStart(List<Integer> start) {
        this.start = start;
    }

    @DynamoDBAttribute(attributeName = "end")
    public List<Integer> getEnd() {
        return end;
    }

    public void setEnd(List<Integer> end) {
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

    public String toString(){
        return String.format("Name: %s, (%f, %f), %s", getName(), getLat(), getLng(), getDetails());
    }

}

package com.hab.studyspace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yidanzeng on 2/7/16.
 */
class Space {
    String name;
    boolean occupied;
    int capacity;
    int photoId;

    Space(String name, boolean occupied, int capacity, int photoId) {
        this.name = name;
        this.occupied = occupied;
        this.capacity = capacity;
        this.photoId = photoId;
    }

    public Space(){};

    private List<Space> rooms;

    // This method creates an ArrayList that has three space objects
    private void initializeData() {
        rooms = new ArrayList<Space>();
        rooms.add(new Space("Wilson", false, 23, R.drawable.studyspace));
        rooms.add(new Space("Sayles", false, 5, R.drawable.studyspace));
        rooms.add(new Space("Salomon", true, 3, R.drawable.studyspace));
    }

}
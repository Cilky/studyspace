package com.hab.studyspace;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ivy on 2/7/16.
 */
public class Utils {

    public static String printList(List list) {
        String str = "[";

        for (int i = 0; i < list.size(); i++) {
            str += "'" + list.get(i).toString() + "'";
            if (i != list.size() - 1) {
                str += ",";
            }
        }

        str += "]";
        return str;
    }
}

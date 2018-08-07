package com.nightcrawler.bakingapp;

import android.net.Uri;
import android.provider.BaseColumns;

//public class dishContract implements BaseColumns {
//
//    public static final String AUTHORITY = "com.nightcrawler.bakingapp";
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
//    public static final String PATH_TASKS = "favDish";
//    public static final Uri CONTENT_URI =
//            BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();
//
//    public static final String TABLE_NAME = "favDish";
//    public static final String STEPS = "Steps";
//
//}


/**
 * Created by Indian Dollar on 3/5/2017.
 */

public class Contract implements BaseColumns {

    public static final String TABLE_NAME = "todos_list";
    public static final String COL_TODO_TEXT = "task";

    public static final String SCHEMA = "content://";
    public static final String AUTHORITY = "com.nightcrawler.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEMA + AUTHORITY);
    public static final String PATH_TODOS = "todos";
    public static final Uri PATH_TODOS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TODOS).build();

}

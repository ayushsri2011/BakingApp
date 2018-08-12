package com.nightcrawler.bakingapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract implements BaseColumns {

    public static final String TABLE_NAME = "todos_list";
    public static final String COL_TODO_TEXT = "task";

    private static final String SCHEMA = "content://";
    public static final String AUTHORITY = "com.nightcrawler.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEMA + AUTHORITY);
    public static final String PATH_TODOS = "todos";
    public static final Uri PATH_TODOS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TODOS).build();

}

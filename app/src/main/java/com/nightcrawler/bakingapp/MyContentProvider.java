package com.nightcrawler.bakingapp;


//public class dishContentProvider extends ContentProvider {
//
//    public static final int TASKS = 100;
//    public static final int TASK_WITH_ID = 101;
//    private dishDbHelper dishDbHelper;
//    private static final UriMatcher sUriMatcher = buildUriMatcher();
//
//    public static UriMatcher buildUriMatcher() {
//
//        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
//        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//
//        uriMatcher.addURI(dishContract.AUTHORITY, dishContract.PATH_TASKS, TASKS);
//        uriMatcher.addURI(dishContract.AUTHORITY, dishContract.PATH_TASKS + "/#", TASK_WITH_ID);
//
//        return uriMatcher;
//    }
//
//    @Override
//    public boolean onCreate() {
//
//        Context context = getContext();
//        dishDbHelper = new dishDbHelper(context);
//        return true;
//    }
//
//
//    public Uri insert(@NonNull Uri uri, ContentValues values) {
//        final SQLiteDatabase db = dishDbHelper.getWritableDatabase();
//
//        int match = sUriMatcher.match(uri);
//        Uri returnUri;
//
//        switch (match) {
//            case TASKS:
//                long id = db.insert("favDish", null, values);
//                if ( id > 0 ) {
//                    Log.v("Insert Query","Entry done "+id);
//                    returnUri = ContentUris.withAppendedId(dishContract.CONTENT_URI, id);
//                } else {
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                }
//                break;
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//
//        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
//
//        return returnUri;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
//
//        final SQLiteDatabase db = dishDbHelper.getReadableDatabase();
//        int match = sUriMatcher.match(uri);
//        Cursor retCursor;
//
//        switch (match) {
//            case TASKS:
//                retCursor =  db.query("favDish",projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder);
//                break;
//
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//
//        retCursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
//
//        return retCursor;
//    }
//
//    @Nullable
//    @Override
//    public String getType(@NonNull Uri uri) {
//        return null;
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
//
//
//        final SQLiteDatabase db = dishDbHelper.getWritableDatabase();
//        int match = sUriMatcher.match(uri);
//        int tasksDeleted; // starts as 0
//
//        switch (match) {
//            case TASK_WITH_ID:
//                String id = uri.getPathSegments().get(1);
//                tasksDeleted = db.delete("favDish", "movieID="+id , null);
//                break;
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//
//        if (tasksDeleted != 0)
//            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
//
//        return tasksDeleted;
//
//    }
//
//    @Override
//    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
//        return 0;
//    }
//
//}




//package me.indiandollar.apps.todoappcollectionwidget;

        import android.content.ContentProvider;
        import android.content.ContentUris;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.UriMatcher;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.net.Uri;
        import android.support.annotation.Nullable;

        import com.nightcrawler.bakingapp.Contract;
        import com.nightcrawler.bakingapp.DbHelper;

        import java.util.Objects;

public class MyContentProvider extends ContentProvider {

    private DbHelper mDbHelper;
    public static final int TODOS_CODE = 100;

    public static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_TODOS, TODOS_CODE);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new DbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor = null;

        switch (match) {
            case TODOS_CODE:

                retCursor = db.query(Contract.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            default:
                break;
        }
        if(retCursor!=null)
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri = null;

        switch (match) {
            case TODOS_CODE:

                long id = db.insert(Contract.TABLE_NAME, null, values);

                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.PATH_TODOS_URI, id);
                }

                break;
            default:
                break;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}


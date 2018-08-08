package com.nightcrawler.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private Intent intent;
    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t1 = findViewById(R.id.dish1);
        TextView t2 = findViewById(R.id.dish2);
        TextView t3 = findViewById(R.id.dish3);
        TextView t4 = findViewById(R.id.dish4);

        intent = new Intent(MainActivity.this, DetailsActivity.class);

        t1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                args = new Bundle();
                args.putInt("KEY", 1);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);

            }
        });
        t2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("KEY", 2);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("t3","");
                args = new Bundle();
                args.putInt("KEY", 3);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);

            }
        });

        t4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                args = new Bundle();
                args.putInt("KEY", 4);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });

        ContentValues values = new ContentValues();
        values.put(Contract.COL_TODO_TEXT, "Test");

        this.getContentResolver().insert(Contract.PATH_TODOS_URI, values);

        //CollectionAppWidgetProvider.sendRefreshBroadcast(getBaseContext());


//        Cursor cursor=this.getContentResolver().query(Contract.PATH_TODOS_URI,null,null,
//                null,null);
//
//        int i=0;
//        final ArrayList<String> items = new ArrayList<>();
//        while(cursor.moveToNext()) {
////            TodoModel item = new TodoModel(cursor.getInt(0), );
//            i++;
//            items.add(cursor.getString(1)+i);
//            Toast.makeText(this, items.get(i-1), Toast.LENGTH_SHORT).show();
//            Log.v(i+"  ",items.get(i-1));
//        }







//        ContentValues contentValues = new ContentValues();
//        contentValues.put(dishContract.STEPS,"Step1");
//        getContentResolver().insert(dishContract.CONTENT_URI, contentValues);
//
//
//        ContentValues cValues = new ContentValues();
//        cValues.put(dishContract.STEPS,"Step2");
//        getContentResolver().insert(dishContract.CONTENT_URI, cValues);


    }

}
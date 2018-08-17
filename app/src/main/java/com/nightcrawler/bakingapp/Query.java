package com.nightcrawler.bakingapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Query extends AsyncTask<String, String, String> {

    public String local_response;

    @Override
    protected String doInBackground(String... params) {
        String line = "";
        StringBuilder line2 = new StringBuilder(" ");
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String local_address = params[0];
        Log.v("Address is---", local_address);
        try {
            URL url = new URL(local_address);
            Log.v("url", url.toString());

            connection = (HttpURLConnection) url.openConnection();
            Log.v("Connection", connection.toString());
            connection.connect();

            InputStream stream = connection.getInputStream();
            Log.v("stream", stream.toString());

            reader = new BufferedReader(new InputStreamReader(stream));
            Log.v("reader", reader.toString());


            while ((line = reader.readLine()) != null) {
                line2.append(line);
            }


            local_response = line2.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.v("close", "error");
                e.printStackTrace();
            }
        }
        return line2.toString();
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.v("onPostExecute:::", result);
    }
}




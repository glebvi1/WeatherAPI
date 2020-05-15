package com.example.weatherapi;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// first means URL, second nothing, third will be type String
public class Weather extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... address) {
        try {

            // 2. Get String in JSON format
            URL url = new URL(address[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);

            int data = reader.read();
            char symbol;
            String content = "";

            while (data != -1) {
                symbol = (char) data;
                content += symbol;
                data = reader.read();
            }

            return content;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

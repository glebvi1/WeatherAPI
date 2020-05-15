package com.example.weatherapi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button searchButton;
    TextView result;

    String content = "";
    private static final String JSON_STRING = "JSON_STRING1";
    String main, description;
    String demand = "";
    String maxT, minT, temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = (Button) findViewById(R.id.searchButton);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        search();
                }
            });
    }

    private void search() {

        editText = (EditText) findViewById(R.id.editText);

        demand = editText.getText().toString();
        if (demand.equals("")) {
            demand = "Moscow";
            Log.i(JSON_STRING, "demand");
            editText.setText(demand);

        }
        result = (TextView) findViewById(R.id.result);

        // 1. Get weather API
        Weather weather = new Weather();
        Log.i(JSON_STRING, editText.getText().toString());
        try {
            content = weather.execute("https://openweathermap.org/data/2.5/weather?q=" + demand + "&appid=439d4b804bc8187953eb36d2a8c26a02").get();
            //Log.i(JSON_STRING, content);

            // 3. Get information of JSON String
                JSONObject jsonObject = new JSONObject(content);
                // parses JSON array: name: "weather"
                String jsonString = jsonObject.getString("weather");
                //Log.i(JSON_STRING, jsonString);


                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject weatherPart = jsonArray.getJSONObject(i);
                    main = weatherPart.getString("main");
                    description = weatherPart.getString("description");
                }
                Log.i(JSON_STRING, main + " " + description);

                String mainT = jsonObject.getString("main");
                JSONObject tempObject = new JSONObject(mainT);
                maxT = tempObject.getString("temp_max");
                minT = tempObject.getString("temp_min");
                temp = tempObject.getString("temp");

                // 4. Show result
                String resultText = "Main: " + main + "\n"
                        + "Description: " + description + "\n"
                        + "Temp_max: " + maxT + "\n" +
                        "Temp_min: " + minT + "\n" +
                        "Temp: " + temp;
                result.setText(resultText);

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }

    }

}

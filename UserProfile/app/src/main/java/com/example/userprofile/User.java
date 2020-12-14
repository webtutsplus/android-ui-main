package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class User extends AppCompatActivity {

    TableLayout table;
    String link, line, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        table = findViewById(R.id.table);
    }

    public void fetch(View view) {
        // Create various messages to display in the app.
        Toast failed_toast = Toast.makeText(User.this, "Request failed", Toast.LENGTH_SHORT);
        Toast fetched_toast = Toast.makeText(User.this, "Data Fetched", Toast.LENGTH_SHORT);
        // Create a worker thread for sending HTTP requests.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                link = "http://18.191.244.74:8080/api/user/";                                          // endpoint for API
                try {
                    URL url = new URL(link);                                                           // new url object is created
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();                 // HTTP connection object is created
                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            conn.getInputStream()));
                    while ((line = rd.readLine()) != null) {
                        String jsonString = "{" + " \"Data\": " + line + "}";                           // Get the response
                        try {
                            JSONObject jsonObject = new JSONObject(jsonString);
                            JSONArray array = jsonObject.getJSONArray("Data");
                            for (int i=0; i < array.length(); i++) {
                                JSONObject oneObject = array.getJSONObject(i);
                                // Pulling items from the array
                                String uname = oneObject.getString("username");
                                String email = oneObject.getString("email");
                                String f_name = oneObject.getString("firstName");
                                String l_name = oneObject.getString("lastName");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Remove all child views
                                        table.removeAllViews();
                                        //Create new table row
                                        TableRow tr = new TableRow(User.this);
                                        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                        //Create new TextView for column 1
                                        TextView t1 = new TextView(User.this);
                                        t1.setText(uname);
                                        t1.setTextColor(Color.parseColor("#00d9d9"));
                                        t1.setTextSize(10);
                                        t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                                        //Create new TextView for column 2
                                        TextView t2 = new TextView(User.this);
                                        t2.setText(email);
                                        t2.setTextColor(Color.parseColor("#00d9d9"));
                                        t2.setTextSize(10);
                                        t2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                                        //Create new TextView for column 3
                                        TextView t3 = new TextView(User.this);
                                        t3.setText(f_name);
                                        t3.setTextColor(Color.parseColor("#00d9d9"));
                                        t3.setTextSize(10);
                                        t3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                                        //Create new TextView for column 4
                                        TextView t4 = new TextView(User.this);
                                        t4.setText(l_name);
                                        t4.setTextColor(Color.parseColor("#00d9d9"));
                                        t4.setTextSize(10);
                                        t4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                                        // Add TextViews to table row
                                        tr.addView(t1);
                                        tr.addView(t2);
                                        tr.addView(t3);
                                        tr.addView(t4);
                                        // Add table row to Table View
                                        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    code = String.valueOf(conn.getResponseCode());                                      // Get the HTTP status code
                    conn.disconnect();                                                                  // Disconnecting
                    // For unreachable network or other network related failures.
                    if (!code.equals("200")) {
                        failed_toast.show();
                    }
                    // Show data in a table form.
                    else {
                        fetched_toast.show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    failed_toast.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    failed_toast.show();
                }
            }
        });
        thread.start();
    }
}

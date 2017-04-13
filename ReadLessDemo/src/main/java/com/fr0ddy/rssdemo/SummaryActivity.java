package com.fr0ddy.rssdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

public class SummaryActivity extends Activity{
    private String str = "";
    public static final String LINK = "Link";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Intent intent = getIntent();
        String link = intent.getExtras().getString(LINK);
        String url = "http://api.smmry.com/&SM_API_KEY=82A75C25ED&SM_LENGTH=15&SM_URL="+link;
        try {
            doGetRequest(url);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void doGetRequest(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        setStr("Exception: "+ e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                     //Log.d("response", response.body().string());

                        try {
                            JSONObject reader = new JSONObject(response.body().string());
                            String content = reader.getString("sm_api_content");


                            // Run view-related code back on the main thread
                            SummaryActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        TextView myTextView = (TextView) findViewById(R.id.SummaryView);
                                        myTextView.setText(content);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            Log.d("response", content);
                        } catch(Exception e) {
                            Log.d("exception", e.toString());
                        }

                    }
                });

    }


    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}

package com.fr0ddy.rssdemo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.webkit.WebView;

public class ArticleActivity extends Activity {

    private String linkStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String link = intent.getExtras().getString("LINK");
        setLinkStr(link);
        WebView myWebView = (WebView) findViewById(R.id.webviewlink);
        myWebView.loadUrl(link);

        }
    public void openSummary(View view)
    {
        Intent intent = new Intent( ArticleActivity.this, SummaryActivity.class);
        String link = getLinkStr();
        intent.putExtra(SummaryActivity.LINK,link);
        startActivity(intent);
    }

    public String getLinkStr() {
        return linkStr;
    }

    public void setLinkStr(String linkStr) {
        this.linkStr = linkStr;
    }
}




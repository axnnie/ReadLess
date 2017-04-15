package com.fr0ddy.rssdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fr0ddy.readless.RssItem;
import com.fr0ddy.readless.RssReader;

import java.util.ArrayList;
import java.util.List;

//import java.util.ArrayList;

public class MainActivity extends Activity {
    private ListView mList;
    ArrayAdapter<String> adapter;
    private List<String> listLink = new ArrayList<String>();
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();
        new GetRssFeed().execute("http://feeds.foxnews.com/foxnews/latest");
        mList = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, R.layout.basic_list_item);
        mList.setOnItemClickListener((parent, view, position, id) -> {
            String link = listLink.get(position);
            Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
            //based on item add info to intent
            intent.putExtra("LINK",link);
            startActivity(intent);
        });
    }
    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }
    private class GetRssFeed extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {

                RssReader rssReader = new RssReader(params[0]);
                for (RssItem item : rssReader.getItems()) {
                    adapter.add(item.getTitle());
                    listLink.add(item.getLink());
                }

            } catch (Exception e) {
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            mList.setAdapter(adapter);
        }
    }


}

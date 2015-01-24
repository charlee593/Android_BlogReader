package com.example.android.blogreader;

import android.app.ListActivity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainListActivity extends ListActivity {

    protected String[] mBlogPosTitles;
    public static final int NUMBER_OF_POSTS = 20;
    public static final String TAG = MainListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        GetBlogPostsTask getBlogPostsTask = new GetBlogPostsTask();
        getBlogPostsTask.execute();

        Resources resources = getResources();
        mBlogPosTitles = resources.getStringArray(R.array.android_names);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mBlogPosTitles);
        setListAdapter(adapter);

        //Toast.makeText(this, getString(R.string.no_items), Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetBlogPostsTask extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(Object... arg0) {
            int responseCode = -1;
            try {
                URL blogFeedUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + NUMBER_OF_POSTS);
                HttpURLConnection connection = (HttpURLConnection)blogFeedUrl.openConnection();
                connection.connect();

                responseCode = connection.getResponseCode();
                Log.i(TAG, "Code: " + responseCode);
            } catch (MalformedURLException e) {
                Log.e(TAG, "Exception caught: ", e);
            } catch (IOException e) {
                Log.e(TAG, "Exception caught: ", e);
            }catch (Exception e){
                Log.e(TAG, "Exception caught: ", e);
            }
            return "Code: " + responseCode;
        }
    }
}

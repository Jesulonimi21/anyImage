package com.example.jesulonimi.anyimage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tomer.fadingtextview.FadingTextView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity implements myAdapter.OnClickListener {
    public static final String picassoUrl="https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3";
public static  String sort="q";
public static  String image_type="image_type";
public static String typeResult="photo";
List<model> mList;

final int PERMISSION_GRANTED_CODE=123;
    EditText searchText;
RequestQueue rq;
RecyclerView recyclerView;
ProgressBar progressBar;
String TAG="my Tag";
myAdapter adapter;
     JsonObjectRequest jsonObjectRequest;
FadingTextView placeholderText;
ProgressBar downloadProgress;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){

            case PERMISSION_GRANTED_CODE : {if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"permission grantd",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"permission not grantd",Toast.LENGTH_SHORT).show();
            }}
            break;
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("browse image");

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.
                PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },PERMISSION_GRANTED_CODE);
        }


        downloadProgress=(ProgressBar) findViewById(R.id.progress);
        placeholderText=(FadingTextView) findViewById(R.id.placeholderText);

        String[] str={"Click",
                "               On",
                "                       Search",
                "                                  icon",
                "To",
                "      Show",
                "                   Search",
                "                                  Results"};
        placeholderText.setTexts(str);
        placeholderText.setTimeout(300, TimeUnit.MILLISECONDS);
        placeholderText.forceRefresh();



        downloadProgress.setVisibility(View.INVISIBLE);

        searchText=(EditText) findViewById(R.id.editText_search);
        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new VerticalSpacing(50));
        rq= Volley.newRequestQueue(this);
        mList=new ArrayList();

            adapter=new myAdapter();


    }

    public void setAdapter() {

        String searchContent = searchText.getText().toString();
        URL finalUrl = getBuiltUrl(searchContent);
        String u=finalUrl.toString();
        mList.clear();
new DownloadTakeCare().execute(finalUrl);
//        jsonObjectRequest   = new JsonObjectRequest(Request.Method.GET, u, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//jsonObjectRequest.setTag(TAG);
//                try {
//
//                    JSONArray jsonArray = response.getJSONArray("hits");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String url = jsonObject.getString("webformatURL");
//                        String name=jsonObject.getString("user");
//                        int likes=jsonObject.getInt("likes");
//                        mList.add(new model(url,likes,name));
//                       adapter=new myAdapter(mList, MainActivity.this);
//                        recyclerView.setAdapter(adapter);
//                                adapter.setOnItemClickListener(MainActivity.this);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Toast.makeText(MainActivity.this,"still passing first request, try again in the next thirty seconds",Toast.LENGTH_LONG).show();
//            }
//        });
//rq.add(jsonObjectRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.searchIcon){
//          rq.cancelAll(TAG);
            downloadProgress.setVisibility(View.VISIBLE);
            placeholderText.setVisibility(View.GONE);
            setAdapter();
            searchText.setText("");
            UIUtil.hideKeyboard(MainActivity.this);

        }
        return super.onOptionsItemSelected(item);
    }
    public URL getBuiltUrl(String searchItem){
     Uri builtUri=Uri.parse(picassoUrl).buildUpon()
             .appendQueryParameter(sort,searchItem)
             .appendQueryParameter(image_type,typeResult).build();

     URL url=null;
     try{
         url=new URL(builtUri.toString());

     }catch(Exception e){
         e.printStackTrace();
     }
     return url;

    }

    @Override
    public void onItemClick(int position) {
        model m=mList.get(position);
        Intent i=new Intent(MainActivity.this,DowloadActivity.class);
        i.putExtra("intentName",m.getcName());
        i.putExtra("intentLike",m.getLikes());
        i.putExtra("intentUrl",m.getImageUrl());
        startActivity(i);
        CustomIntent.customType(this,"up-to-bottom");

    }
    public class DownloadTakeCare extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... urls) {
            HttpURLConnection httpURLConnection=null;
            try {
                 httpURLConnection=(HttpURLConnection) urls[0].openConnection();
                InputStream in=httpURLConnection.getInputStream();
                Scanner scanner=new Scanner(in);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
                    return scanner.next();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject response=new JSONObject(s);
                JSONArray jsonArray = response.getJSONArray("hits");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String url = jsonObject.getString("webformatURL");
                    String name=jsonObject.getString("user");
                    int likes=jsonObject.getInt("likes");
                    mList.add(new model(url,likes,name));
                    adapter=new myAdapter(mList, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(MainActivity.this);
                    downloadProgress.setVisibility(View.INVISIBLE);

            }
        } catch (JSONException e) {
                e.printStackTrace();
            }
        }
}}

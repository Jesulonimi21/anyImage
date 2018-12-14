package com.example.jesulonimi.anyimage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import maes.tech.intentanim.CustomIntent;

public class DowloadActivity extends AppCompatActivity {
String myUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dowload);



        Intent intent=getIntent();
        String url=intent.getStringExtra("intentUrl");
        myUrl=url;
        String name=intent.getStringExtra("intentName");
        int Likes=intent.getIntExtra("intentLikes",0);
        TextView names=(TextView) findViewById(R.id.creatorsName);
        TextView likess=(TextView) findViewById(R.id.nolikes);
        ImageView iv=(ImageView) findViewById(R.id.secondImage);

        names.setText(name);
        likess.setText(Integer.toString(Likes));

        Picasso.with(this).load(url).fit().centerInside().into(iv);


    }

    public void saveTheImage(View v){
        String fileName= System.currentTimeMillis()+".jpg";
        Picasso.with(DowloadActivity.this).load(myUrl).into(new saveImage(getBaseContext(),getApplicationContext().getContentResolver(),fileName,"desc"));

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this,"bottom-to-up");
    }
}

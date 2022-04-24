package com.example.infernoinfosec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DataDisplayActivity extends AppCompatActivity {

    ImageView img;
    TextView head,desc,Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        img=(ImageView)findViewById(R.id.Image_);
        head=(TextView)findViewById(R.id.Headline_);
        desc=(TextView)findViewById(R.id.Desc_);
        Date=(TextView)findViewById(R.id.PostedDate_);


        Uri uri= Uri.parse(getIntent().getStringExtra("ImageUri"));
        Glide.with(img.getContext()).load(uri).into(img);
        head.setText(getIntent().getStringExtra("Headline"));
        desc.setText(getIntent().getStringExtra("Description"));
        Date.setText(getIntent().getStringExtra("postedDate"));


    }
}
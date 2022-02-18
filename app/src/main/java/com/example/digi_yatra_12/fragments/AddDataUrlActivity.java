package com.example.digi_yatra_12.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.WebViewActivity;
import com.example.digi_yatra_12.retrofit.Const;
import com.example.digi_yatra_12.retrofit.RetrofitBuilder;
import com.example.digi_yatra_12.retrofit.RetrofitService;
import com.example.model.AccessTokenRoot;
import com.example.model.EAadharRoot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class AddDataUrlActivity extends AppCompatActivity {
Button urlBtn;
    private String connectionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_url);
        connectionID = getIntent().getStringExtra("connectionId");
        urlBtn = findViewById(R.id.UrlBtn);
        urlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AddDataUrlActivity.this, WebViewActivity.class);
                intent.putExtra("connectionId", connectionID);
                startActivity(intent);
                finish();
            }
        });

        ImageButton popup = findViewById(R.id.imageBtn);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddDataUrlActivity.this, RetrivedAadharDataActivity.class);
                i.putExtra("connectionId", connectionID);
                startActivity(i);
                finish();
            }
        });

        ImageButton ib = (ImageButton)findViewById(R.id.backBtn1);
        ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
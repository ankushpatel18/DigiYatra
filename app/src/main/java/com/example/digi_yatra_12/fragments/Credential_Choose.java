package com.example.digi_yatra_12.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.retrofit.RetrofitService;
import com.example.util.CustomProgressDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

;

public class Credential_Choose extends AppCompatActivity {

    private String senderUrl = "https://chr2pwsfnb.execute-api.ap-south-1.amazonaws.com/";
    private CustomProgressDialog customProgressDialog;
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential_choose);
        customProgressDialog = new CustomProgressDialog(this);
        ImageButton identityCredentials = (ImageButton) findViewById(R.id.identitialBtn);
        ImageView heathCredentials = findViewById(R.id.health_credential);
        identityCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = true;
                identityCredentials.setColorFilter( Color.rgb(
                        234, 243, 252
                ));

                httpCall("IdentityCredential");
            }
        });
        heathCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = true;
                heathCredentials.setColorFilter( Color.rgb(
                        234, 243, 252
                ));

                httpCall("HealthCredential");
            }
        });
        if (check == true) {
            identityCredentials.setBackgroundColor(Color.BLACK);

        }
        ImageButton ib = (ImageButton)findViewById(R.id.backBtn1);
        ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }






    private void httpCall(String issuer) {
        customProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", issuer);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(senderUrl).addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService rssapi = retrofit.create(RetrofitService.class);
        retrofit2.Call<JsonArray> call = rssapi.listIssuersVerifiers(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                customProgressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null && response.body().size()>0) {
                    PopIssuerDialog popIssuerDialog = new PopIssuerDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", response.body().toString());
                    bundle.putString("title", issuer);
                    popIssuerDialog.setArguments(bundle);
                    popIssuerDialog.show(getSupportFragmentManager(), "PopIssuerDialog");
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                customProgressDialog.dismiss();
                Log.d("getIssuerList", t.getMessage());
                Toast.makeText(Credential_Choose.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
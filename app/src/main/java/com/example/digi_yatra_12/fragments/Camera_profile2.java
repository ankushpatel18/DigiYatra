package com.example.digi_yatra_12.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.navbar.NavbarMainActivity;
import com.example.digi_yatra_12.roomDatabase.AAdharData;
import com.example.digi_yatra_12.roomDatabase.AadharDatabase;
import com.example.model.IssuersVerifier;
import com.example.util.MyUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Camera_profile2 extends AppCompatActivity {
    private ImageView imgAadhar;
    private String faceB64, idType, aadharId, name;
    private TextView textAAdhar, textName, textIdType;
    private Button btnAcccept;
    private JSONObject myJson;
    private String issuerName,issuerId, type, credentialType ;
    private IssuersVerifier issuersVerifier;
    private LinearLayout linearLayout;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_profile2);
        initView();
        String issuerVerifierString = getIntent().getStringExtra("issuersVerifier");
        issuersVerifier = new Gson().fromJson(issuerVerifierString, IssuersVerifier.class);
        String jsonString = getIntent().getStringExtra("json");
        try {
            JSONObject json = new JSONObject(jsonString);
            myJson = json.getJSONObject("message").getJSONObject("Message").getJSONArray("credentials~attach").getJSONObject(0)
                    .getJSONObject("data").getJSONObject("json");
            issuerName = myJson.getJSONObject("issuer").getString("name");
            issuerId = myJson.getJSONObject("issuer").getString("id");
            type = myJson.getString("type");
            credentialType = issuersVerifier.getCredentialType();
            name = myJson.getJSONObject("credentialSubject").getString("fullName");
            aadharId = myJson.getJSONObject("credentialSubject").getString("idNumber");
            idType = myJson.getJSONObject("credentialSubject").getString("idType");
            faceB64 = myJson.getJSONObject("credentialSubject").getString("faceB64");
          //  textAAdhar.setText(aadharId);
          //  textName.setText(name);
            Bitmap bm = MyUtils.StringToBitMap(faceB64);
            imgAadhar.setImageBitmap(bm);
            try {
                JSONObject j = new JSONObject(new Gson().toJson(issuersVerifier.getResponseFiledsForUser()));
                Iterator<String> iterator = j.keys();
                while (iterator.hasNext()) {
                    try {
                        String key = iterator.next();
                        String value = myJson.getJSONObject("credentialSubject").getString(key);
                        LinearLayout linearLayoutChild = (LinearLayout) getLayoutInflater().inflate(R.layout.item_key_value_pair,null, false);
                        TextView textKey = linearLayoutChild.findViewById(R.id.text_key);
                        TextView textValue = linearLayoutChild.findViewById(R.id.text_value);
                        String key1 = j.getString(key);
                        textKey.setText(key1);
                        textValue.setText(value);
                        linearLayout.addView(linearLayoutChild);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        ImageButton ib = (ImageButton)findViewById(R.id.backBtn1);
        ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        /*textAAdhar = findViewById(R.id.txt_aadhar_id);
        textName = findViewById(R.id.full_name);
        */
       // textIdType = findViewById(R.id.)
        imgAadhar = findViewById(R.id.img_aadhar);
        linearLayout = findViewById(R.id.linearLayout1);
        btnAcccept = findViewById(R.id.btn_accept);
        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAcccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AadharDatabase.getInstance(Camera_profile2.this).Dao().saveAadharData(new AAdharData(0,myJson, issuerId,issuerName,type,issuersVerifier, credentialType));
                Intent intent = new Intent(Camera_profile2.this, NavbarMainActivity.class);
                intent.putExtra("credentials", "credentials");
                startActivity(intent);
                finish();
            }
        });
    }

}
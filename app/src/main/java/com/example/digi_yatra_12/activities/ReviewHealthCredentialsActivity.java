package com.example.digi_yatra_12.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.databinding.ActivityReviewHealthCredentialsBinding;
import com.example.digi_yatra_12.navbar.NavbarMainActivity;
import com.example.digi_yatra_12.roomDatabase.AAdharData;
import com.example.digi_yatra_12.roomDatabase.AadharDatabase;
import com.example.model.IssuersVerifier;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class ReviewHealthCredentialsActivity extends AppCompatActivity {

    private ActivityReviewHealthCredentialsBinding binding;
    private JSONObject myJson;
    private String issuerName, issuerId, type;
    private IssuersVerifier issuersVerifier;
    private String credentialType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review_health_credentials);
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AadharDatabase.getInstance(ReviewHealthCredentialsActivity.this).Dao().saveAadharData(new AAdharData(0, myJson, issuerId, issuerName, type, issuersVerifier, credentialType));
                Intent intent = new Intent(ReviewHealthCredentialsActivity.this, NavbarMainActivity.class);
                intent.putExtra("credentials", "credentials");
                startActivity(intent);
                finish();
            }
        });

        String issuerVerifierString = getIntent().getStringExtra("issuersVerifier");
        issuersVerifier = new Gson().fromJson(issuerVerifierString, IssuersVerifier.class);
        credentialType = issuersVerifier.getCredentialType();
        String jsonString = getIntent().getStringExtra("json");
        try {
            JSONObject json = new JSONObject(jsonString);
            myJson = json.getJSONObject("message").getJSONObject("Message").getJSONArray("credentials~attach").getJSONObject(0)
                    .getJSONObject("data").getJSONObject("json");
            issuerName = myJson.getJSONObject("issuer").getString("name");
            issuerId = myJson.getJSONObject("issuer").getString("id");
            type = myJson.getString("type");
            try {
                JSONObject j = new JSONObject(new Gson().toJson(issuersVerifier.getResponseFiledsForUser()));
                Iterator<String> iterator = j.keys();
                while (iterator.hasNext()) {
                    try {
                        String key = iterator.next();
                        String value = myJson.getJSONObject("credentialSubject").getString(key);
                        LinearLayout linearLayoutChild = (LinearLayout) getLayoutInflater().inflate(R.layout.item_key_value_pair, null, false);
                        TextView textKey = linearLayoutChild.findViewById(R.id.text_key);
                        TextView textValue = linearLayoutChild.findViewById(R.id.text_value);
                        String key1 = j.getString(key);
                        textKey.setText(key1);
                        switch (value) {
                            case "0":
                                textValue.setText("Not vaccinated");
                                break;
                            case "1":
                                textValue.setText("Partially Vaccinated");
                                break;
                            case "2":
                                textValue.setText("Fully Vaccinated");
                                break;
                            default:
                                textValue.setText(value);
                                break;
                        }
                        binding.linear.addView(linearLayoutChild);
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


        binding.backBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
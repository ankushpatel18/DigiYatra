package com.example.digi_yatra_12.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.fragments.AddDataUrlActivity;
import com.example.model.IssuersVerifier;
import com.example.util.CustomProgressDialog;
import com.google.gson.Gson;

import java.util.List;

public class PopAcknowledgementDialogActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private CustomProgressDialog customProgressDialog;
    private String connectionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_acknowledgement);
        customProgressDialog = new CustomProgressDialog(this);
        connectionID = getIntent().getStringExtra("connectionId");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .91), (int) (height * .5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.x = 0;
        params.y = 50;

        getWindow().setAttributes(params);
        Button btn = findViewById(R.id.OkBtn2);
        Button cancelButton = findViewById(R.id.btn_cancel);
        CheckBox checkBox = findViewById(R.id.checkBox2);
        linearLayout = findViewById(R.id.linearLayout);
        String issuersVerifierString = getIntent().getStringExtra("issuer_verifier");
        IssuersVerifier issuersVerifier = new Gson().fromJson(issuersVerifierString, IssuersVerifier.class);
        List<String> requestField = issuersVerifier.getRequestFieldsForUser();
        for (int i = 0; i < requestField.size(); i++) {
            TextView textView = new TextView(PopAcknowledgementDialogActivity.this);
            textView.setText(i + 1 + "." + requestField.get(i));
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.setMargins(0, 10, 0, 0);
            textView.setLayoutParams(buttonLayoutParams);
            linearLayout.addView(textView);
        }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        if (issuersVerifier.getCredentialType().equals("IdentityCredential")) {
                            Intent i = new Intent(PopAcknowledgementDialogActivity.this, AddDataUrlActivity.class);
                            i.putExtra("connectionId", connectionID);
                            startActivity(i);
                            finish();
                        } else if (issuersVerifier.getCredentialType().equals("HealthCredential")) {
                            Intent i = new Intent(PopAcknowledgementDialogActivity.this, AddCredentialsCowinActivity.class);
                            i.putExtra("connectionId", connectionID);
                            startActivity(i);
                            finish();
                        }
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (checkBox.isChecked()) {
                        btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.btn_enable, getResources().newTheme())));
                    } else {
                        btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.btn_disable, getResources().newTheme())));
                    }
                }
            });

    }

}
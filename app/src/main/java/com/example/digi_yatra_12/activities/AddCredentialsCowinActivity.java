package com.example.digi_yatra_12.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.digi_yatra_12.BaseClass;
import com.example.digi_yatra_12.GlobalApplication;
import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.databinding.ActivityAddCredentialsCowinBinding;
import com.example.digi_yatra_12.retrofit.Const;
import com.example.digi_yatra_12.retrofit.RetrofitBuilder;
import com.example.digi_yatra_12.roomDatabase.AAdharData;
import com.example.digi_yatra_12.roomDatabase.AadharDatabase;
import com.example.digi_yatra_12.roomDatabase.ConnectionDB;
import com.example.model.ConnectionDetails;
import com.example.util.CustomProgressDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mukesh.OnOtpCompletionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCredentialsCowinActivity extends AppCompatActivity {
    ActivityAddCredentialsCowinBinding binding;
    private List<AAdharData> aAdharDataList;
    private JSONObject jsonObject;
    private String txnId, fullName, token;
    private String myConnectionId, myDID, theirDid;
    private ConnectionDB connectionDB;
    private String type;
    private JSONObject jsonObject1;
    private CustomProgressDialog customProgressDialog;
    private String connectionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_credentials_cowin);
        connectionId = getIntent().getStringExtra("connectionId");
        initOtp();
        customProgressDialog = new CustomProgressDialog(this);
        getDataFromDatabase();
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txnId != null && !txnId.isEmpty()) {
                    verifyOtp();
                }
                else {
                    sendOTP();
                }
            }
        });
        binding.resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP();
            }
        });
    }

    private void initOtp() {
        binding.inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.inputCode2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.inputCode3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.inputCode4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.inputCode5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.inputCode6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifyOtp();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getDataFromDatabase() {
        GetAadhar getAadhar = new GetAadhar();
        getAadhar.execute();
    }
    private class GetAadhar extends AsyncTask<String, String, List<AAdharData>> {
        @Override
        protected List<AAdharData> doInBackground(String... strings) {

            aAdharDataList = AadharDatabase.getInstance(AddCredentialsCowinActivity.this).Dao().getAadharData();
            return aAdharDataList;
        }
        @Override
        protected void onPostExecute(List<AAdharData> aAdharDataList) {
            super.onPostExecute(aAdharDataList);
            if (aAdharDataList != null && !aAdharDataList.isEmpty()) {
                try {
                    binding.name.setText(aAdharDataList.get(0).getJson().getJSONObject("credentialSubject").getString("fullName"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Please add Identity Credential first", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendOTP() {
        if (TextUtils.isEmpty(binding.phone.getText())) {
            Toast.makeText(getApplicationContext(), "Please enter mobile number", Toast.LENGTH_SHORT).show();
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobile_number", binding.phone.getText().toString().trim());
        jsonObject.addProperty("full_name", binding.name.getText().toString().trim());
        customProgressDialog.show();
        new RetrofitBuilder(Const.BASE_URL_OTP).create().sendOtp(jsonObject)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        customProgressDialog.dismiss();
                        if (response.isSuccessful() && response.body().has("txnId")) {
                            txnId = response.body().get("txnId").getAsString();
                            Log.d("txnId", txnId);
                            binding.linearOtp.setVisibility(View.VISIBLE);
                            Toast.makeText(AddCredentialsCowinActivity.this, "OTP sent", Toast.LENGTH_LONG).show();
                        }
                        else if (response.code() == 400){
                                try {
                                    JSONObject jsonObject1 = new JSONObject(response.errorBody().string());
                                    String error = jsonObject1.getString("error");
                                    Toast.makeText(AddCredentialsCowinActivity.this, error, Toast.LENGTH_LONG).show();
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                Toast.makeText(AddCredentialsCowinActivity.this, "Something went wrong, Please try again.", Toast.LENGTH_LONG).show();
                            }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        customProgressDialog.dismiss();
                        Toast.makeText(AddCredentialsCowinActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void verifyOtp() {
        String otpString = binding.inputCode1.getText().toString()+binding.inputCode2.getText().toString()+binding.inputCode3.getText().toString()+binding.inputCode4.getText().toString()+binding.inputCode5.getText().toString()+binding.inputCode6.getText().toString();
        if (otpString.length() < 6) {
            Toast.makeText(getApplicationContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(txnId)) {
            Toast.makeText(getApplicationContext(), "Please resend code", Toast.LENGTH_SHORT).show();
            return;
        }
        String otp = stringTosha256(otpString);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("otp", otp);
        jsonObject.addProperty("txnId", txnId);
        customProgressDialog.show();
        new RetrofitBuilder(Const.BASE_URL_OTP).create().verifyOtp(jsonObject)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        customProgressDialog.dismiss();
                        if (response.isSuccessful() && response.body().has("token")) {
                            token = response.body().get("token").getAsString();
                            Log.d("token", token);
                            try {
                                requestCredential();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            /*Intent intent = new Intent(AddCredentialsCowinActivity.this, ReviewHealthCredentialsActivity.class);
                            intent.putExtra("txnId", txnId);
                            intent.putExtra("token", token);
                            intent.putExtra("fullName", binding.name.getText());
                            startActivity(intent);*/
                        }
                        else if (response.code() == 400){
                            try {
                                JSONObject jsonObject1 = new JSONObject(response.errorBody().string());
                                String error = jsonObject1.getString("error");
                                Toast.makeText(AddCredentialsCowinActivity.this, error, Toast.LENGTH_LONG).show();
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(AddCredentialsCowinActivity.this, "Something went wrong, Please try again.", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        customProgressDialog.dismiss();
                        Toast.makeText(AddCredentialsCowinActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private String stringTosha256(String str) {
        MessageDigest msg = null;
        try {
            msg = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = msg.digest(str.getBytes(StandardCharsets.UTF_8));
        // convert bytes to hexadecimal
        StringBuilder s = new StringBuilder();
        for (byte b : hash) {
            s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return String.valueOf(s);
    }

    private void requestCredential() throws JSONException {

        String json = "{\"comment\":\"string\",\"formats\":[{\"attach_id\":\"string\",\"format\":\"aries/ld-proof-vc-detail@v1.0\"}],\"requests~attach\":[{\"@id\":\"string\",\"mime-type\":\"application/json\",\"data\":{\"json\":{\"credential\":{\"@context\":[\"https://www.w3.org/2018/credentials/v1\",\"https://dyce-context.s3.us-west-2.amazonaws.com/v1.json\",\"https://w3id.org/security/bbs/v1\"],\"id\":\"https://digiyatafoundation.com/credentialid\",\"type\":[\"VerifiableCredential\"],\"issuer\":{\"id\":\"did:dataevolve:1234\",\"name\":\"DigiYataraFoundation\"}, \"issuanceDate\":\"2020-01-01T19:23:24Z\",\"expirationDate\":\"2100-01-01T19:23:24Z\",\"credentialSubject\":{\"id\":\"https://digiyatrafoundation.com/credentialsubjectid\",\"selfie\":\"Base64 selfie image\",\"idType\":\"aadhar\",\"idJson\":{}}},\"options\":{\"proofPurpose\":\"assertionMethod\",\"created\":\"2020-04-02T18:48:36Z\",\"proofType\":\"BbsBlsSignature2020\"}}}}]}";
        try {
            jsonObject = new JSONObject(json.trim());
            //idType is replaced with otp , sefie with fullName, idJson with txid for health credentials
            jsonObject.getJSONArray("requests~attach").getJSONObject(0).getJSONObject("data").getJSONObject("json").getJSONObject("credential")
                    .getJSONObject("credentialSubject").remove("idJson");
            jsonObject.getJSONArray("requests~attach").getJSONObject(0).getJSONObject("data").getJSONObject("json").getJSONObject("credential")
                    .getJSONObject("credentialSubject").put("txid", txnId);


            jsonObject.getJSONArray("requests~attach").getJSONObject(0).getJSONObject("data").getJSONObject("json").getJSONObject("credential")
                    .getJSONObject("credentialSubject").remove("selfie");
            jsonObject.getJSONArray("requests~attach").getJSONObject(0).getJSONObject("data").getJSONObject("json").getJSONObject("credential")
                    .getJSONObject("credentialSubject").put("fullName", binding.name.getText().toString());

            jsonObject.getJSONArray("requests~attach").getJSONObject(0).getJSONObject("data").getJSONObject("json").getJSONObject("credential")
                    .getJSONObject("credentialSubject").remove("idType");

            jsonObject.getJSONArray("requests~attach").getJSONObject(0).getJSONObject("data").getJSONObject("json").getJSONObject("credential")
                    .getJSONObject("credentialSubject").put("otp", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*SharedPreferences sharedPreferences = getSharedPreferences("digiyatra", Context.MODE_PRIVATE);
        String connectionId = sharedPreferences.getString("connection_id","");*/
        JSONObject getConnectionJsonObject = BaseClass.getConnection(connectionId, GlobalApplication.agent);
        if (getConnectionJsonObject.getString("status").equals("0")) {
            Toast.makeText(AddCredentialsCowinActivity.this, getConnectionJsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
        else {
            ConnectionDetails connectionDetails = new Gson().fromJson(getConnectionJsonObject.toString().trim(), ConnectionDetails.class);
            myConnectionId = connectionDetails.getConnRecord().getConnectionID();
            myDID = connectionDetails.getConnRecord().getMyDID();
            theirDid = connectionDetails.getConnRecord().getTheirDID();
            GetConnectionData getConnectionData = new GetConnectionData();
            getConnectionData.execute();
        }
    }

    class GetConnectionData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            connectionDB = AadharDatabase.getInstance(AddCredentialsCowinActivity.this).Dao().getConnectionData(myConnectionId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            jsonObject1 = connectionDB.getJson();
            type = connectionDB.getType();
            UpdateConnectionData updateConnectionData = new UpdateConnectionData();
            updateConnectionData.execute();
        }
    }
    class UpdateConnectionData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AadharDatabase.getInstance(AddCredentialsCowinActivity.this).Dao().updateConnection(new ConnectionDB(myConnectionId,type,jsonObject1,myDID,theirDid));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("checkJsonObject", jsonObject.toString());
            BaseClass.requestCredential(myDID, theirDid,jsonObject, GlobalApplication.agent);
        }
    }

}

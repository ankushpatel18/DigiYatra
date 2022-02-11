package com.example.digi_yatra_12.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.digi_yatra_12.BaseClass;
import com.example.digi_yatra_12.BaseClassInterface;
import com.example.digi_yatra_12.GlobalApplication;
import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.adapters.IssuerAdapter;
import com.example.digi_yatra_12.roomDatabase.AadharDatabase;
import com.example.digi_yatra_12.roomDatabase.ConnectionDB;
import com.example.model.IssuersVerifier;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopIssuerDialog extends BottomSheetDialogFragment implements IssuerAdapter.IssuerClick {
    //String senderUrl = "http://ab6b1f47c653a4ad9be235211726df19-222719493.ap-south-1.elb.amazonaws.com:10093/";
    List<IssuersVerifier> issuersVerifierList = new ArrayList();
    IssuerAdapter issuerAdapter;
    private String connectionId;
    private String type;
    private String issuerJson;
    private IssuersVerifier issuersVerifier;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pop_issuer, container, false);
        String jsonString = getArguments().getString("response");
        issuerAdapter = new IssuerAdapter(requireContext(), issuersVerifierList, this);
        getIssuerList(jsonString);
        //view.setBackgroundResource(R.drawable.bg_rounded_corner_top);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(issuerAdapter);
        return view;
    }

    private void getIssuerList(String jsonString) {
        IssuersVerifier[] issuersVerifierListResponse = new Gson().fromJson(jsonString, IssuersVerifier[].class);
        issuersVerifierList.clear();
        for (int i=0; i<issuersVerifierListResponse.length; i++) {
            issuersVerifierList.add(issuersVerifierListResponse[i]);
        }
        issuerAdapter.notifyDataSetChanged();
    }

    private void showPopUP(View view, IssuersVerifier issuersVerifier) {
        Dialog builder = new Dialog(getContext());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_acknowledgement, viewGroup, false);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn= dialogView.findViewById(R.id.OkBtn2);
        Button cancelButton = dialogView.findViewById(R.id.btn_cancel);
        CheckBox checkBox = dialogView.findViewById(R.id.checkBox2);
        LinearLayout linearLayout = dialogView.findViewById(R.id.linearLayout);
        List<String> requestField = issuersVerifier.getRequestFieldsForUser();
        for (int i=0; i<requestField.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(i+1+"."+requestField.get(i));
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.setMargins(0, 10, 0, 0);
            textView.setLayoutParams(buttonLayoutParams);
            linearLayout.addView(textView);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    /*Intent i = new Intent(getContext(), AddDataUrlActivity.class);
                    startActivity(i);*/
                    if (connectionId != null) {
                        BaseClass.acceptInvitation(connectionId, "", GlobalApplication.agent);
                    }
                    builder.dismiss();
                    dismiss();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked()) {
                    btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.btn_enable, getResources().newTheme())));
                }
                else {
                    btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.btn_disable, getResources().newTheme())));
                }
            }
        });
        builder.setContentView(dialogView);
        builder.create();
        builder.show();
    }

    @Override
    public int getTheme() {
        return R.style.NoBackgroundDialogTheme;
    }
    private  void createInvitation(String senderUrl, IssuersVerifier issuersVerifier, View view) {
        BaseClass.createInvitation(senderUrl, new BaseClassInterface() {
            @Override
            public void createInvitationResponse(JsonObject invitation) {
                Log.d("createInvitation","created");
                if (invitation != null) {
                    JSONObject connectionJsonObject = BaseClass.receiveInvitation(invitation, GlobalApplication.agent);
                    try {
                        connectionId = connectionJsonObject.getString("connection_id");
                        type = issuersVerifier.getType();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    issuerJson = new Gson().toJson(issuersVerifier,IssuersVerifier.class);
                    SaveConnection saveConnection = new SaveConnection();
                    saveConnection.execute();

                    //showPopUP(view, issuersVerifier);
                    /*JSONObject acceptInvitationReturn = null;
                    try {
                        acceptInvitationReturn = BaseClass.acceptInvitation(connectionJsonObject.getString("connection_id"),"", GlobalApplication.agent);
                        // save connectionID
                        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("digiyatra", Context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("connection_id", connectionJsonObject.getString("connection_id"));
                        myEdit.apply();
                        myEdit.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/




                   /* JSONObject finalAcceptInvitationReturn = acceptInvitationReturn;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (finalAcceptInvitationReturn.getInt("status") == 1) {
                                    // getConnection(connectionJsonObject.getString("connection_id"), GlobalApplication.agent);

                                    JSONObject jsonObjectRegisterRouter = BaseClass.registerRouter(connectionJsonObject.getString("connection_id"), GlobalApplication.agent);
                                    //save connection id in local database
                                    // go to screen no 6
                                    if (jsonObjectRegisterRouter.getInt("status") == 1) {
                                        JSONObject getConnectionJsonObject = BaseClass.getConnection(connectionJsonObject.getString("connection_id"),GlobalApplication.agent);
                                        Log.d("getConnectionJsonObject", getConnectionJsonObject.getString("message"));
                                        ConnectionDetails connectionDetails  = new Gson().fromJson(getConnectionJsonObject.toString(), ConnectionDetails.class);
                                        String myConnectionId = connectionDetails.getConnRecord().get(0).getConnectionID();
                                        String myDID = connectionDetails.getConnRecord().get(0).getMyDID();
                                        String theirDid = connectionDetails.getConnRecord().get(0).getTheirDID();
                                        JSONObject resultProposeCredentials = BaseClass.proposeCredential(myDID, theirDid, new JSONObject(),GlobalApplication.agent);
                                        if (resultProposeCredentials.getInt("status") == 0) {
                                            Toast.makeText(getContext(), resultProposeCredentials.getInt("message"), Toast.LENGTH_SHORT).show();
                                        }
                                        else  if (resultProposeCredentials.getInt("status") == 1)
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },4000);*/

                }
            }
        });
    }

    @Override
    public void onIssuerClick(IssuersVerifier issuersVerifier, View view) {
        String url = issuersVerifier.getUrl() + "/";
        this.issuersVerifier = issuersVerifier;
        this.view= view;
        createInvitation(url,issuersVerifier, view);
    }
    private class SaveConnection extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... Void) {
            try {//TODO record for this database is different for different issuers  and use connection id as
                AadharDatabase.getInstance(requireContext()).Dao().saveConnections(new ConnectionDB(0,connectionId,type, new JSONObject(issuerJson),"", ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            showPopUP(view, issuersVerifier);//Todo show pop after acceptInvitation in state completed ,type-
        }
        /*@Override
        protected Void doInBackground(String... strings) {
            try {
                AadharDatabase.getInstance(requireContext()).Dao().saveConnections(new ConnectionDB(0,connectionId,type, new JSONObject(issuerJson),"", ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                p.hide();
                imageView.setImageBitmap(bitmap);
            }else {
                p.show();
            }
        }*/
    }
}
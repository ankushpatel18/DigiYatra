package com.example.digi_yatra_12.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.roomDatabase.AAdharData;
import com.example.util.MyUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder>{
    Context context;
    List<AAdharData> aAdharDataList;
    CardClick cardClick;
    public CardAdapter(Context context, List<AAdharData> aAdharDataList, CardClick cardClick) {
        this.context = context;
        this.aAdharDataList = aAdharDataList;
        this.cardClick = cardClick;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cards, parent, false);
        CardHolder cardHolder = new CardHolder(listItem);
        return cardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        // holder.issuerLogo.setImageBitmap(MyUtils.StringToBitMap(aAdharDataList.get(position).));
        //holder.txtNameValue.setText(aAdharDataList.get(position));
        JSONObject jsonObject = aAdharDataList.get(position).getJson();
        holder.txtIdentity.setText(aAdharDataList.get(position).getIssuersVerifier().getCredentialType());
        try {
            if (aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").has("fullName")) {
                holder.txtNameKey.setText(aAdharDataList.get(position).getIssuersVerifier().getResponseFiledsForUser().getFullName());
                holder.txtNameValue.setText(aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").getString("fullName"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").has("idNumber")) {
                String stringIdNumber = aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").getString("idNumber");
                switch (stringIdNumber) {
                    case "0":
                        holder.txtAadhaarValue.setText("Not vaccinated");
                        break;
                    case "1":
                        holder.txtAadhaarValue.setText("Partially Vaccinated");
                        break;
                    case "2":
                        holder.txtAadhaarValue.setText("Fully Vaccinated");
                        break;
                    default:
                        holder.txtAadhaarValue.setText(stringIdNumber);
                        break;
                }
                holder.txtAadhaarKey.setText(aAdharDataList.get(position).getIssuersVerifier().getResponseFiledsForUser().getIdNumber());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").has("idType")) {
                holder.txtIdTypeValue.setText(aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").getString("idType"));
                holder.txtIdTypeKey.setText(aAdharDataList.get(position).getIssuersVerifier().getResponseFiledsForUser().getIdType());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").has("vaccinationStatus")) {
                holder.txtAadhaarValue.setText(aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").getString("vaccinationStatus"));
                holder.txtAadhaarKey.setText(aAdharDataList.get(position).getIssuersVerifier().getResponseFiledsForUser().getVaccinationStatus());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").has("faceB64")) {

                String face64 = aAdharDataList.get(position).getJson().getJSONObject("credentialSubject").getString("faceB64");
                Bitmap bm = MyUtils.StringToBitMap(face64);
                if (bm != null) {
                    holder.imgPhoto.setImageBitmap(bm);
                    holder.imgLogo.setVisibility(View.GONE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return aAdharDataList.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder{
        TextView txtIdentity;
        TextView txtNameKey, txtNameValue;
        TextView txtAadhaarKey, txtAadhaarValue;
        TextView txtIdTypeKey, txtIdTypeValue;
        ImageView imgPhoto, imgLogo;
        public CardHolder(@NonNull View itemView) {
            super(itemView);
            txtIdentity = itemView.findViewById(R.id.txt_identity1);
            txtNameKey = itemView.findViewById(R.id.full_name);
            txtNameValue = itemView.findViewById(R.id.full_name1);
            txtAadhaarKey = itemView.findViewById(R.id.id_number);
            txtAadhaarValue = itemView.findViewById(R.id.id_number1);
            txtIdTypeKey = itemView.findViewById(R.id.id_type);
            txtIdTypeValue = itemView.findViewById(R.id.id_type1);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            imgLogo = itemView.findViewById(R.id.img_logo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardClick.onCardClick(aAdharDataList.get(getAdapterPosition()), itemView);
                }
            });
        }
    }
    public interface CardClick{
        void onCardClick(AAdharData aAdharData, View view);
    }
}

package com.example.digi_yatra_12.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digi_yatra_12.R;
import com.example.model.IssuerBoardingPassModel;

import java.util.ArrayList;

public class BoardingPassIssuerAdapter extends RecyclerView.Adapter<BoardingPassIssuerAdapter.IssuerHolder>{
    Context context;
    ArrayList<IssuerBoardingPassModel> issuersVerifierBoardingList;
    IssuerClick issuerClick;
    public BoardingPassIssuerAdapter(Context context, ArrayList<IssuerBoardingPassModel> issuersVerifierBoardingList, IssuerClick issuerClick) {
        this.context = context;
        this.issuersVerifierBoardingList = issuersVerifierBoardingList;
        this.issuerClick = issuerClick;
    }

    @NonNull
    @Override
    public BoardingPassIssuerAdapter.IssuerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_boarding_pass_issuer, parent, false);
        IssuerHolder issuerHolder = new IssuerHolder(listItem);
        return issuerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IssuerHolder holder, int position) {
        holder.issuerName.setText(issuersVerifierBoardingList.get(position).getAirportName());
    }

    @Override
    public int getItemCount() {
        return issuersVerifierBoardingList.size();
    }

    public class IssuerHolder extends RecyclerView.ViewHolder{
        ImageView issuerLogo, next;
        TextView issuerName;
        ImageButton popIssuerButton;
        public IssuerHolder(@NonNull View itemView) {
            super(itemView);
            issuerName = itemView.findViewById(R.id.txt_issuer_name);
            issuerLogo = itemView.findViewById(R.id.img_issuer_logo);
            next = itemView.findViewById(R.id.img_next);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    issuerClick.onIssuerClick(issuersVerifierBoardingList.get(getAdapterPosition()), itemView);
                }
            });
        }
    }
    public interface IssuerClick{
        void onIssuerClick(IssuerBoardingPassModel issuerBoardingPassModel, View view);
    }
}


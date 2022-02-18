package com.example.digi_yatra_12.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.adapters.CardAdapter;
import com.example.digi_yatra_12.roomDatabase.AAdharData;
import com.example.digi_yatra_12.roomDatabase.AadharDatabase;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class WalletFragment2 extends Fragment implements CardAdapter.CardClick {
    private RecyclerView recyclerview;
    private List<AAdharData> aAdharDataList = new ArrayList<>();
    private CardAdapter cardAdapter;
    Button add;
    Chip identityChip, healthChip;

    public WalletFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet2, container, false);
        initViews(view);
        getData("IdentityCredential");


        add = (Button) view.findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Credential_Choose.class);
                startActivity(intent);


            }
        });
        return view;
    }

    private void getData(String credentialType) {
        GetCredential getAadhar = new GetCredential(credentialType);
        getAadhar.execute();
    }

    private void initViews(View view) {
        recyclerview = view.findViewById(R.id.recycler);
        identityChip = view.findViewById(R.id.chip1);
        healthChip = view.findViewById(R.id.chip4);
        identityChip.setChipStrokeColor(ContextCompat.getColorStateList(getContext(), R.color.unselect));
        identityChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("IdentityCredential");
                identityChip.setChipStrokeColor(ContextCompat.getColorStateList(getContext(), R.color.select));
                healthChip.setChipStrokeColor(ContextCompat.getColorStateList(getContext(), R.color.unselect));
            }
        });
        healthChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("HealthCredential");
                identityChip.setChipStrokeColor(ContextCompat.getColorStateList(getContext(), R.color.unselect));
                healthChip.setChipStrokeColor(ContextCompat.getColorStateList(getContext(), R.color.select));
            }
        });
    }


    @Override
    public void onCardClick(AAdharData aAdharData, View view) {

    }

    private class GetCredential extends AsyncTask<String, String, List<AAdharData>> {
        String credentialType;

        GetCredential(String credentialType) {
            this.credentialType = credentialType;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<AAdharData> doInBackground(String... strings) {

            aAdharDataList = AadharDatabase.getInstance(getContext()).Dao().getAadharData(credentialType);
            return aAdharDataList;
        }

        @Override
        protected void onPostExecute(List<AAdharData> aAdharDataList) {
            super.onPostExecute(aAdharDataList);
            if (aAdharDataList != null && !aAdharDataList.isEmpty()) {
                cardAdapter = new CardAdapter(getContext(), aAdharDataList, new CardAdapter.CardClick() {
                    @Override
                    public void onCardClick(AAdharData aAdharData, View view) {

                    }
                });
                if (isAdded()) {
                    recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
                    recyclerview.setAdapter(cardAdapter);
                    cardAdapter.notifyDataSetChanged();
                }
            } else {
                Fragment fragment = new WalletFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    }
}
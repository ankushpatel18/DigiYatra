package com.example.digi_yatra_12.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.digi_yatra_12.BaseClass;
import com.example.digi_yatra_12.BaseClassInterface;
import com.example.digi_yatra_12.GlobalApplication;
import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.adapters.BoardingPassAdapter;
import com.example.digi_yatra_12.adapters.BoardingPassIssuerAdapter;
import com.example.digi_yatra_12.adapters.SliderAdapter;
import com.example.digi_yatra_12.databinding.FragmentHomeFragmentBinding;
import com.example.digi_yatra_12.retrofit.Const;
import com.example.digi_yatra_12.retrofit.RetrofitBuilder;
import com.example.digi_yatra_12.roomDatabase.AadharDatabase;
import com.example.digi_yatra_12.roomDatabase.BoardingPassData;
import com.example.digi_yatra_12.roomDatabase.ConnectionDB;
import com.example.model.BoardingPassModel;
import com.example.model.ConnectionDetails;
import com.example.model.IssuerBoardingPassModel;
import com.example.model.IssuersVerifier;
import com.example.model.SliderData;
import com.example.util.CustomProgressDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smarteist.autoimageslider.SliderView;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
FragmentHomeFragmentBinding binding;
    ImageButton add;
    Button popup;
    Layout add1;
    private ViewPager viewPager;
    private SpringDotsIndicator dotsIndicator;
    private ConstraintLayout constraintNoData;
    private ConstraintLayout constraintViewPager;
    private CustomProgressDialog customProgressDialog;
    private ArrayList<IssuerBoardingPassModel> issuersVerifierBoardingList;
    private BoardingPassIssuerAdapter boardingPassIssuerAdapter;
    private String connectionId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ImageSwitcher imageslider;

    Intent intent;
    String url2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRCqZAnX28YoyZnR0KUGsq9eVAeRzBbnfibng&usqp=CAU";
    String url1 = "https://cdn03.collinson.cn/blog/2019/aug/header-airport-family-travel-vacation-5537204d-e09c-45b7-8db2-e4f2f58437b5.png?h=380&la=en&w=1280";
    String url3 = "https://cdn03.collinson.cn/blog/2019/aug/header-airport-family-travel-vacation-5537204d-e09c-45b7-8db2-e4f2f58437b5.png?h=380&la=en&w=1280";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_home_fragment, container, false );
        customProgressDialog = new CustomProgressDialog(requireContext());
        intent = new Intent( getActivity(), pop_crediential.class );
        initViews(rootView);
        GetBoardingPass getBoardingPass = new GetBoardingPass();
        getBoardingPass.execute();

// we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = rootView.findViewById( R.id.slider );

        // adding the urls inside array list
        sliderDataArrayList.add( new SliderData( url1 ) );
        sliderDataArrayList.add( new SliderData( url2 ) );
        sliderDataArrayList.add( new SliderData( url3 ) );

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter( HomeFragment.this, sliderDataArrayList );

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection( SliderView.LAYOUT_DIRECTION_LTR );

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter( adapter );

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec( 3 );

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle( true );

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        return rootView;
    }

    private void initViews(View rootView) {
        viewPager = rootView.findViewById(R.id.viewPager);
        dotsIndicator = (SpringDotsIndicator) rootView.findViewById(R.id.spring_dots_indicator);
        constraintNoData = rootView.findViewById(R.id.constraint_no_data);
        constraintViewPager = rootView.findViewById(R.id.constraint_view_pager);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
//
//
        add = (ImageButton) view.findViewById( R.id.imageButton15 );

        add.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent( getActivity(), UpdateYourTravelActivity.class );
                startActivity( intent );
            }
        } );
//
//        popup = (Button) view.findViewById( R.id.ShareBtn );
//        popup.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent = new Intent( getActivity(), Pop_Share_home.class );
//                startActivity( intent );
//            }
//        } );

    }

    //dailoug box


//    @SuppressLint("ResourceType")
//    private void showStartDialog() {
//
//        final AlertDialog.Builder builder;
//
//        builder = new AlertDialog.Builder( getParentFragment().getActivity() );
////        builder .setTitle("one time Dialoug");
////              builder  .setMessage("hello");
//        builder.setView( R.layout.activity_pop_sucess_register
//        );
//
////        builder.setPositiveButton( R.id.OkBtn2, new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////                dialogInterface.dismiss();
////
////            }
////        } )
//
////    builder.setView( R.id.OkBtn2 );
//
////               builder .setPositiveButton("OKBTN", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface  dialogInterface, int which) {
////                        dialogInterface.dismiss();
////                    }
////                })
//             builder.show();
////        SharedPreferences prefs = getActivity().getSharedPreferences( "prefs", Context.MODE_PRIVATE );
////        SharedPreferences.Editor editor =prefs.edit();
////        editor.putBoolean( "firstStart",true );
////        editor.apply();
//
//    }

    public class GetBoardingPass extends AsyncTask<Void, Void, Void> {
        List<BoardingPassData> boardingPassData;
        @Override
        protected Void doInBackground(Void... voids) {
            boardingPassData = AadharDatabase.getInstance(getContext()).Dao().getBoardingPass();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (boardingPassData == null || boardingPassData.isEmpty()) {
                constraintViewPager.setVisibility(View.INVISIBLE);
                constraintNoData.setVisibility(View.VISIBLE);
            }
            else {
                constraintViewPager.setVisibility(View.VISIBLE);
                constraintNoData.setVisibility(View.INVISIBLE);
                BoardingPassAdapter boardingPassAdapter = new BoardingPassAdapter(getActivity(), boardingPassData, new BoardingPassAdapter.BoardingPass() {
                    @Override
                    public void onClick(View view, BoardingPassData boardingPassData) {
                        customProgressDialog.show();
                        new RetrofitBuilder(Const.BASE_URL_ISSUER_LIST_BOARDING).create().getListIssuerBoarding_pass(boardingPassData.getBoardingPassModel().getFrom())
                                .enqueue(new Callback<JsonArray>() {
                                    @Override
                                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                                        customProgressDialog.dismiss();
                                        if (response.isSuccessful() && response.body() != null && response.body().size()>0) {
                                            showPopUP(view, response.body(), boardingPassData.getBoardingPassModel());
                                        }
                                        else {
                                            Toast.makeText(getContext(),"something went wrong.",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<JsonArray> call, Throwable t) {
                                        customProgressDialog.dismiss();
                                        Log.d("getIssuerList", t.getMessage());
                                        Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
                viewPager.setAdapter(boardingPassAdapter);
                dotsIndicator.setViewPager(viewPager);
            }
        }
    }

    private void showPopUP(View view, JsonArray body, BoardingPassModel boardingPassModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.item_share_boarding_pass, viewGroup, false);
        initViewsAndSetValues(dialogView, body, boardingPassModel);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initViewsAndSetValues(View dialogView, JsonArray body, BoardingPassModel boardingPassModel) {
        issuersVerifierBoardingList = new ArrayList<>();
        boardingPassIssuerAdapter = new BoardingPassIssuerAdapter(getContext(), issuersVerifierBoardingList, new BoardingPassIssuerAdapter.IssuerClick() {
            @Override
            public void onIssuerClick(IssuerBoardingPassModel issuerBoardingPassModel, View view) {
                createInvitation(issuerBoardingPassModel.getEndpoint()+"/", boardingPassModel.getBarcodeString()        );
            }
        });
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(boardingPassIssuerAdapter);
        TextView to = dialogView.findViewById(R.id.txt_to);
        to.setText(boardingPassModel.getTo());
        TextView from = dialogView.findViewById(R.id.txt_from);
        from.setText(boardingPassModel.getFrom());
        TextView toTime = dialogView.findViewById(R.id.time_to);
        // toTime.setText(boardingPassDataList.get(position).get);
        TextView fromTime = dialogView.findViewById(R.id.time_from);
        //   fromTime.setText(boardingPassDataList.get(position).getTo());
        TextView issuerName = dialogView.findViewById(R.id.issuer_name);
        issuerName.setText(boardingPassModel.getPassengerName());
        TextView date = dialogView.findViewById(R.id.flight_date);
        date.setText(boardingPassModel.getDoT());
        getIssuerList(body.toString());
    }
    private void getIssuerList(String jsonString) {
        IssuerBoardingPassModel[] issuersVerifierListResponse = new Gson().fromJson(jsonString, IssuerBoardingPassModel[].class);
        issuersVerifierBoardingList.clear();
        for (int i=0; i<issuersVerifierListResponse.length; i++) {
            issuersVerifierBoardingList.add(issuersVerifierListResponse[i]);
        }
        boardingPassIssuerAdapter.notifyDataSetChanged();
    }

    private  void createInvitation(String senderUrl, String boardingPassId) {
        BaseClass.createInvitation(senderUrl, new BaseClassInterface() {
            @Override
            public void createInvitationResponse(JsonObject invitation) {
                Log.d("createInvitation","created");
                if (invitation != null) {
                    JSONObject connectionJsonObject = BaseClass.receiveInvitation(invitation, GlobalApplication.agent);
                    try {
                        BaseClass.acceptInvitation(connectionId, "", GlobalApplication.agent);
                        connectionId = connectionJsonObject.getString("connection_id");
                        JSONObject getConnectionJsonObject = BaseClass.getConnection(connectionId, GlobalApplication.agent);
                        if (getConnectionJsonObject.getString("status").equals("0")) {
                            Toast.makeText(getContext(),getConnectionJsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ConnectionDetails connectionDetails = new Gson().fromJson(getConnectionJsonObject.toString().trim(), ConnectionDetails.class);
                            String myConnectionId = connectionDetails.getConnRecord().getConnectionID();
                            String myDID = connectionDetails.getConnRecord().getMyDID();
                            String theirDid = connectionDetails.getConnRecord().getTheirDID();
                            SaveConnection saveConnection = new SaveConnection(myConnectionId, myDID, theirDid, boardingPassId);
                            saveConnection.execute();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private class SaveConnection extends AsyncTask<Void, Void, Void> {
        String connectionId;
        String myDID;
        String theirDid;
        String boardinPassID;
        public SaveConnection(String myConnectionId, String myDID, String theirDid, String boardinPassID) {
            connectionId = myConnectionId;
            this.myDID = myDID;
            this.theirDid = theirDid;
            this.boardinPassID = boardinPassID;
        }

        @Override
        protected Void doInBackground(Void... Void) {
            AadharDatabase.getInstance(requireContext()).Dao().saveConnections(new ConnectionDB(connectionId,"verifier", new JSONObject(),myDID, theirDid));
            AadharDatabase.getInstance(requireContext()).Dao().updateBoardingPass(false, theirDid, boardinPassID );
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            BaseClass.acceptInvitation(connectionId, "", GlobalApplication.agent);
        }
    }

}


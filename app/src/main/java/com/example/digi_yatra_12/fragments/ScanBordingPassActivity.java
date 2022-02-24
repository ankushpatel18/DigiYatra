package com.example.digi_yatra_12.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.digi_yatra_12.R;
import com.example.digi_yatra_12.navbar.NavbarMainActivity;
import com.example.digi_yatra_12.roomDatabase.AadharDatabase;
import com.example.digi_yatra_12.roomDatabase.BoardingPassData;
import com.example.model.BoardingPassModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ScanBordingPassActivity extends AppCompatActivity {
    TextView txtPassangerName, txtIssuerName, txtFlightNo, txtFrom, txtTo, txtDate, txtPNR, txtSequence, txtSeat, txtDepartureTime;
    Button Sucess;
    private String passangerName, issuerName, flightNo, from, to, date, pNR, sequence, seat;
    private String departureTime = "";
    private BoardingPassModel boardingPassModel;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_bording_pass);
        Intent intent = getIntent();
        text = intent.getStringExtra("values");
        Log.d("boarding", text);
        String[] newStr = text.split("\\s+");
        String x = "";
        for (int i = 0; i < newStr.length; i++) {
            System.out.println(newStr[i]);
            x=x+newStr[i]+" ";
        }

        txtPassangerName=findViewById(R.id.textView38);
        String[] newStr1 = newStr[0].split("/");
        passangerName = newStr1[0].substring(2);
        txtPassangerName.setText(passangerName);


        txtIssuerName=findViewById(R.id.text_issuer_name);
        String[] newStr2 = newStr[0].split("/");
        issuerName = newStr2[1];
        txtIssuerName.setText(issuerName);

//flight no
        txtFlightNo=findViewById(R.id.textView39);
        flightNo = newStr[2].substring(6)+newStr[3];
        txtFlightNo.setText(flightNo);

//From
        txtFrom=findViewById(R.id.textView27);
        from = "HYD"; /*newStr[2].substring(0,newStr.length -5);*/
        txtFrom.setText(from);
//TO
        txtTo=findViewById(R.id.textView30);
        to = newStr[2].substring(3,newStr.length -2);
        txtTo.setText(to);
//AirlineCode
//        details=findViewById(R.id.textView42);
//        details.setText(newStr[3]);
// Date

        txtDate=findViewById(R.id.textView43);
        String dayOfYear = newStr[4].substring(0,2);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, Integer.parseInt(dayOfYear));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
//      System.out.println("Day of year " + dayOfYear + " = " + sdf.format(calendar.getTime()));
        date = sdf.format(calendar.getTime());
        txtDate.setText(date);
//PNR

        txtPNR=findViewById(R.id.textView46);
        pNR = newStr[1];
        txtPNR.setText(pNR);
//SEQUNCE

        txtSequence=findViewById(R.id.textView48);
        sequence = newStr[4].substring(8);
        txtSequence.setText(sequence);
//SEAT
        txtSeat=findViewById(R.id.textView50);
        seat = newStr[4].substring(4,newStr.length -0);
        txtSeat.setText(seat);
        boardingPassModel = new BoardingPassModel(text, date, flightNo, from, pNR, "", "", seat, sequence, to);



        Sucess =findViewById(R.id.AceptBtn);
        Sucess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveBoardingPass saveBoardingPass = new SaveBoardingPass();
                saveBoardingPass.execute();
               /// Intent intent =new Intent(Scan_bording_pass.this, Sucessufully_reg2.class);
             //   startActivity(intent);
            }
        });



        ImageButton ib = (ImageButton)findViewById(R.id.backBtn1);
        ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public class SaveBoardingPass extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AadharDatabase.getInstance(ScanBordingPassActivity.this).Dao().saveBoardingPass(new BoardingPassData(text,false,"", boardingPassModel));
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            startActivity(new Intent(ScanBordingPassActivity.this, NavbarMainActivity.class));
            finish();
        }
    }
}
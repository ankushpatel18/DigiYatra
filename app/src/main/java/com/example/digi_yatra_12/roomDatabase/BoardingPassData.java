package com.example.digi_yatra_12.roomDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.model.BoardingPassModel;

@Entity(tableName = "boarding_pass")
public class BoardingPassData {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String barcodeString; ;
    private boolean status = false;
    private String theirDid;
    private BoardingPassModel boardingPassModel;

    public BoardingPassData(String barcodeString, boolean status, String theirDid, BoardingPassModel boardingPassModel) {
        this.barcodeString = barcodeString;
        this.status = status;
        this.theirDid = theirDid;
        this.boardingPassModel = boardingPassModel;
    }

    public String getBarcodeString() {
        return barcodeString;
    }

    public boolean isStatus() {
        return status;
    }

    public String getTheirDid() {
        return theirDid;
    }

    public BoardingPassModel getBoardingPassModel() {
        return boardingPassModel;
    }
}

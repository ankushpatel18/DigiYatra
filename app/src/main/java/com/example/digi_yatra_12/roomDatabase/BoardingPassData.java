package com.example.digi_yatra_12.roomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.model.BoardingPassModel;
import com.example.model.IssuersVerifier;

import org.json.JSONObject;
@Entity(tableName = "boarding_pass")
public class BoardingPassData {
    @PrimaryKey(autoGenerate = true)
    private int id =0;
    private BoardingPassModel boardingPassModel;

    public BoardingPassData(int id, BoardingPassModel boardingPassModel) {
        this.id = id;
        this.boardingPassModel = boardingPassModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BoardingPassModel getBoardingPassModel() {
        return boardingPassModel;
    }

    public void setBoardingPassModel(BoardingPassModel boardingPassModel) {
        this.boardingPassModel = boardingPassModel;
    }
}

package com.example.digi_yatra_12.roomDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

@Entity(tableName = "connection_db")
public class ConnectionDB {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String connection_id = "";
    private String type = "";
    private JSONObject json;
    private String myDID = "";
    private String theirDID= "";

    public ConnectionDB(String connection_id, String type, JSONObject json, String myDID, String theirDID) {
        this.connection_id = connection_id;
        this.type = type;
        this.json = json;
        this.myDID = myDID;
        this.theirDID = theirDID;
    }

    public String getConnection_id() {
        return connection_id;
    }

    public void setConnection_id(String connection_id) {
        this.connection_id = connection_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public String getMyDID() {
        return myDID;
    }

    public void setMyDID(String myDID) {
        this.myDID = myDID;
    }

    public String getTheirDID() {
        return theirDID;
    }

    public void setTheirDID(String theirDID) {
        this.theirDID = theirDID;
    }
}

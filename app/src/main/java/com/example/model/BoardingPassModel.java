
package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BoardingPassModel {

    @Expose
    @SerializedName("barcodeString")
    private String barcodeString;
    @SerializedName("DoT")
    private String doT;
    @Expose
    @SerializedName("flightNumber")
    private String flightNumber;
    @Expose
    @SerializedName("from")
    private String from;
    @SerializedName("PNR")
    private String pNR;
    @Expose
    @SerializedName("passengerName")
    private String passengerName;
    @SerializedName("STD")
    private String sTD;
    @Expose
    @SerializedName("seatNumber")
    private String seatNumber;
    @Expose
    @SerializedName("sequenceNumber")
    private String sequenceNumber;
    @Expose
    @SerializedName("to")
    private String to;

    public BoardingPassModel(String barcodeString, String doT, String flightNumber, String from, String pNR, String passengerName, String sTD, String seatNumber, String sequenceNumber, String to) {
        this.barcodeString = barcodeString;
        this.doT = doT;
        this.flightNumber = flightNumber;
        this.from = from;
        this.pNR = pNR;
        this.passengerName = passengerName;
        this.sTD = sTD;
        this.seatNumber = seatNumber;
        this.sequenceNumber = sequenceNumber;
        this.to = to;
    }

    public String getBarcodeString() {
        return barcodeString;
    }

    public void setBarcodeString(String barcodeString) {
        this.barcodeString = barcodeString;
    }

    public String getDoT() {
        return doT;
    }

    public void setDoT(String doT) {
        this.doT = doT;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPNR() {
        return pNR;
    }

    public void setPNR(String pNR) {
        this.pNR = pNR;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getSTD() {
        return sTD;
    }

    public void setSTD(String sTD) {
        this.sTD = sTD;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}

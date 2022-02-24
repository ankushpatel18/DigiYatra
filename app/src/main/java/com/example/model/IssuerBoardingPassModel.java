
package com.example.model;

import com.google.gson.annotations.SerializedName;

public class IssuerBoardingPassModel {

    @SerializedName("airport_code")
    private String mAirportCode;
    @SerializedName("airport_name")
    private String mAirportName;
    @SerializedName("api_key")
    private String mApiKey;
    @SerializedName("endpoint")
    private String mEndpoint;
    @SerializedName("queue_name")
    private String mQueueName;

    public String getAirportCode() {
        return mAirportCode;
    }

    public void setAirportCode(String airportCode) {
        mAirportCode = airportCode;
    }

    public String getAirportName() {
        return mAirportName;
    }

    public void setAirportName(String airportName) {
        mAirportName = airportName;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }

    public String getEndpoint() {
        return mEndpoint;
    }

    public void setEndpoint(String endpoint) {
        mEndpoint = endpoint;
    }

    public String getQueueName() {
        return mQueueName;
    }

    public void setQueueName(String queueName) {
        mQueueName = queueName;
    }

}

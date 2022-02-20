package com.example.digi_yatra_12.retrofit;

import com.example.model.AccessTokenRoot;
import com.example.model.EAadharRoot;
import com.example.model.ValidateFaceB64Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("public/oauth2/1/token")
    Call<AccessTokenRoot> getAccessToken(@Field("code") String code,
                                         @Field("grant_type") String grant_type,
                                         @Field("client_id") String client_id,
                                         @Field("client_secret") String client_secret,
                                         @Field("redirect_uri") String redirect_uri);
    @GET("public/oauth2/3/xml/eaadhaar")
    @Headers({"Content-Type: application/xml"})
    Call<String> getEAadhar(@Header("Authorization") String access_token);
    @POST("connections/create-invitation")
    Call<ResponseBody> createInvitation();
    @POST("default/listIssuersVerifiers")
    @Headers({"x-api-key: 64yZGKTOukakcGVLHAe10242dc9J62mr10pXuikA"})
    Call<JsonArray> listIssuersVerifiers(@Body JsonObject jsonRequest);
    @Headers({"Host: liveness-check.default.example.com", "Content-Type: application/xml"})
    @POST("v1/models/liveness-check:predict")
    Call<ValidateFaceB64Response> validateSelfie(@Body JsonObject jsonObject);
    @Headers({"x-api-key: Zqh976skv453BkmlrWpFS25MBCPojStb5cbmQQo8"})
    @POST("v3/vaccination/generateOTP")
    Call<JsonObject> sendOtp(@Body JsonObject jsonObject);
    @Headers({"x-api-key: Zqh976skv453BkmlrWpFS25MBCPojStb5cbmQQo8"})
    @POST("v3/vaccination/confirmOTP")
    Call<JsonObject> verifyOtp(@Body JsonObject jsonObject);
    @GET("verifier/get")
    Call<JsonArray> getListIssuerBoarding_pass(@Query("airport_code") String airportCode);
}

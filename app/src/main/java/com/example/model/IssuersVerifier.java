package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class IssuersVerifier {

    @SerializedName("requestFieldsForUser")
    @Expose
    private List<String> requestFieldsForUser = null;
    @SerializedName("request_schema")
    @Expose
    private RequestSchema requestSchema;
    @SerializedName("responseFiledsForUser")
    @Expose
    private ResponseFiledsForUser responseFiledsForUser;
    @SerializedName("response_schema")
    @Expose
    private ResponseSchema responseSchema;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("credentialType")
    @Expose
    private String credentialType;
    @SerializedName("did")
    @Expose
    private String did;

    public List<String> getRequestFieldsForUser() {
        return requestFieldsForUser;
    }

    public void setRequestFieldsForUser(List<String> requestFieldsForUser) {
        this.requestFieldsForUser = requestFieldsForUser;
    }

    public RequestSchema getRequestSchema() {
        return requestSchema;
    }

    public void setRequestSchema(RequestSchema requestSchema) {
        this.requestSchema = requestSchema;
    }

    public ResponseFiledsForUser getResponseFiledsForUser() {
        return responseFiledsForUser;
    }

    public void setResponseFiledsForUser(ResponseFiledsForUser responseFiledsForUser) {
        this.responseFiledsForUser = responseFiledsForUser;
    }

    public ResponseSchema getResponseSchema() {
        return responseSchema;
    }

    public void setResponseSchema(ResponseSchema responseSchema) {
        this.responseSchema = responseSchema;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public class CredentialSubject {

        @SerializedName("faceB64")
        @Expose
        private String faceB64;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("idType")
        @Expose
        private String idType;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("doB")
        @Expose
        private String doB;
        @SerializedName("fullName")
        @Expose
        private String fullName;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("idNumber")
        @Expose
        private String idNumber;

        public String getFaceB64() {
            return faceB64;
        }

        public void setFaceB64(String faceB64) {
            this.faceB64 = faceB64;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDoB() {
            return doB;
        }

        public void setDoB(String doB) {
            this.doB = doB;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

    }

    public class RequestSchema {

        @SerializedName("idJson")
        @Expose
        private String idJson;
        @SerializedName("faceb64")
        @Expose
        private String faceb64;
        @SerializedName("idType")
        @Expose
        private String idType;

        public String getIdJson() {
            return idJson;
        }

        public void setIdJson(String idJson) {
            this.idJson = idJson;
        }

        public String getFaceb64() {
            return faceb64;
        }

        public void setFaceb64(String faceb64) {
            this.faceb64 = faceb64;
        }

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

    }

    public class ResponseFiledsForUser {

        @SerializedName("fullName")
        @Expose
        private String fullName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("idType")
        @Expose
        private String idType;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("idNumber")
        @Expose
        private String idNumber;
        @SerializedName("doB")
        @Expose
        private String doB;
        @SerializedName("vaccinationStatus")
        @Expose
        private String vaccinationStatus;

        public String getVaccinationStatus() {
            return vaccinationStatus;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getDoB() {
            return doB;
        }

        public void setDoB(String doB) {
            this.doB = doB;
        }

    }

    public class ResponseSchema {

        @SerializedName("credentialSubject")
        @Expose
        private CredentialSubject credentialSubject;

        public CredentialSubject getCredentialSubject() {
            return credentialSubject;
        }

        public void setCredentialSubject(CredentialSubject credentialSubject) {
            this.credentialSubject = credentialSubject;
        }

    }

}

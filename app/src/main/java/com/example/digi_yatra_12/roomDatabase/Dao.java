package com.example.digi_yatra_12.roomDatabase;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAadharData(AAdharData aAdharData);
    @Query("SELECT * FROM aadhar_data")
    List<AAdharData> getAadharData();
    @Query("SELECT * FROM aadhar_data WHERE credentialType = :credentialType")
    List<AAdharData> getAadharData(String credentialType);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveConnections(ConnectionDB connectionDB);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateConnection(ConnectionDB connectionDB);
    @Query("SELECT * FROM connection_db WHERE connection_id = :connectionId LIMIT 1")
    ConnectionDB getConnectionData(String connectionId);
    @Query("SELECT * FROM connection_db WHERE myDID = :myDid LIMIT 1")
    ConnectionDB getConnectionDataByMyDid(String myDid);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveBoardingPass(BoardingPassData boardingPassData);
    @Query("UPDATE boarding_pass SET status = :status, theirDid= :theirDid WHERE barcodeString =:barcodeString")
    void updateBoardingPass(Boolean status, String theirDid, String barcodeString);
    @Query("SELECT * FROM boarding_pass")
    List<BoardingPassData> getBoardingPass();
    @Query("SELECT * FROM boarding_pass WHERE theirDid = :connectionID LIMIT 1")
    List<BoardingPassData> getBoardingPass(String connectionID);
}

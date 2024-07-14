
package model;

import java.sql.Blob;

public class reservationModel {
    private String date;
    private Blob fileData;
    private String userId;
    private String rid,status;
    


    // Constructor
        public reservationModel() {

    }
    public reservationModel(String date, Blob fileData, String userId) {
        this.date = date;
        this.fileData = fileData;
        this.userId = userId;
    }
    
  public reservationModel(String rid,String date, Blob fileData, String userId) {
        this.rid = rid;
        this.date = date;
        this.fileData = fileData;
        this.userId = userId;
    }
    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }
    public String getrid() {
        return rid;
    }

    public void setrid(String rid) {
        this.rid = rid;
    }
  
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Blob getFileData() {
        return fileData;
    }

    public void setFileData(Blob fileData) {
        this.fileData = fileData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
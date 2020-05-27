
package com.ahmed.bank.data.model.DisplayNotificationDetails;

import com.ahmed.bank.data.model.Donations.Donation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayNotificationDetails {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Donation data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Donation getData() {
        return data;
    }

    public void setData(Donation data) {
        this.data = data;
    }

}

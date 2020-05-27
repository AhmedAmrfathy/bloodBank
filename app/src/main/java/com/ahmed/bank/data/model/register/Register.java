
package com.ahmed.bank.data.model.register;

import com.ahmed.bank.data.model.login.Client;
import com.ahmed.bank.data.model.login.ClientData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ClientData data;

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

    public ClientData getData() {
        return data;
    }

    public void setData(ClientData data) {
        this.data = data;
    }

}

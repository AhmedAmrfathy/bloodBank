
package com.ahmed.bank.data.model.getallposts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllPosts {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Data data2;

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

    public Data getData() {
        return data2;
    }

    public void setData(Data data) {
        data2 = data;
    }
}

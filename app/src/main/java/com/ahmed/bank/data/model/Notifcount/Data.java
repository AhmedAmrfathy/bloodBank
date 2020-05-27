
package com.ahmed.bank.data.model.Notifcount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("notifications-count")
    @Expose
    private Integer notificationsCount;

    public Integer getNotificationsCount() {
        return notificationsCount;
    }

    public void setNotificationsCount(Integer notificationsCount) {
        this.notificationsCount = notificationsCount;
    }

}

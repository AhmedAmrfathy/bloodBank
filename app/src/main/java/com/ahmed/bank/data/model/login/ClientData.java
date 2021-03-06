
package com.ahmed.bank.data.model.login;

import com.ahmed.bank.data.model.login.Client;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private Client client;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}

package com.ahmed.bank.data.rest;

import com.ahmed.bank.data.model.AddDonation.AddDonation;
import com.ahmed.bank.data.model.AppSettings.AppSettings;
import com.ahmed.bank.data.model.DisplayNotificationDetails.DisplayNotificationDetails;
import com.ahmed.bank.data.model.Donations.Donations;
import com.ahmed.bank.data.model.Notifcount.Notifcount;
import com.ahmed.bank.data.model.NotificationList.NotificationList;
import com.ahmed.bank.data.model.NotificationSettings.NotificationSettings;
import com.ahmed.bank.data.model.SendContactMessage.SendContactMessage;
import com.ahmed.bank.data.model.addremovefaourite.AddRemoveFav;
import com.ahmed.bank.data.model.favourite.Favourite;
import com.ahmed.bank.data.model.filter.Filter;
import com.ahmed.bank.data.model.generalResponse.GeneralResponse;
import com.ahmed.bank.data.model.getallposts.AllPosts;
import com.ahmed.bank.data.model.login.Login;
import com.ahmed.bank.data.model.newpassword.NewPassword;
import com.ahmed.bank.data.model.resetpassword.ResetPassword;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @POST("login")
    @FormUrlEncoded
    Call<Login> login(@Field("phone") String phone,
                      @Field("password") String pass);


    @POST("signup")
    @FormUrlEncoded
    Call<Login> register(@Field("name") String name,
                         @Field("email") String email,
                         @Field("birth_date") String birth_date,
                         @Field("city_id") int city_id,
                         @Field("phone") String phone,
                         @Field("donation_last_date") String donation_last_date,
                         @Field("password") String password,
                         @Field("password_confirmation") String password_confirmation,
                         @Field("blood_type_id") int blood_type_id); //@GET("zsdfghj")

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSettings> sendnotificationchanged(@Field("api_token") String api_token,
                                                       @Field("governorates[]") List<String> governorates,
                                                       @Field("blood_types[]") List<String> blood_types);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSettings> getnotificationsettings(@Field("api_token") String api_token);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<AddDonation> adddon(@Field("api_token") String api_token,
                             @Field("patient_name") String patient_name,
                             @Field("patient_age") int patient_age,
                             @Field("blood_type_id") int blood_type_id,
                             @Field("bags_num") int bags_num,
                             @Field("hospital_name") String hospital_name,
                             @Field("hospital_address") String hospital_address,
                             @Field("city_id") int city_id,
                             @Field("phone") String phone,
                             @Field("notes") String notes,
                             @Field("latitude") double latitude,
                             @Field("longitude") double longitude);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> reset(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> nnew(@Field("password") String password,
                           @Field("password_confirmation") String password_confirmation,
                           @Field("pin_code") String pin_code,
                           @Field("phone") String phone);

    @GET("governorates")
    Call<GeneralResponse> getgovernorate();

    @GET("cities")
    Call<GeneralResponse> getcities(@Query("governorate_id") int governorate_id);

    @GET("blood-types")
    Call<GeneralResponse> getbloodtype();

    @POST("profile")
    @FormUrlEncoded
    Call<Login> getprofiledata(@Field("api_token") String api_token);

    @GET("posts")
    Call<AllPosts> getAllPosts(@Query("api_token") String api_token,
                               @Query("page") int page);

    @GET("donation-requests")
    Call<Donations> getalldonations(@Query("api_token") String api_token,
                                    @Query("page") int page);

    @GET("categories")
    Call<GeneralResponse> getCategey();

    @GET("posts")
    Call<Filter> Filterdata(@Query("api_token") String api_token,
                            @Query("page") int page,
                            @Query("keyword") String keyword,
                            @Query("category_id") int category_id);

    @GET("my-favourites")
    Call<Favourite> favourite(@Query("api_token") String api_token);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<AddRemoveFav> addremove(@Field("post_id") int post_id,
                                 @Field("api_token") String api_token);

    @POST("contact")
    @FormUrlEncoded
    Call<SendContactMessage> send(@Field("api_token") String api_token,
                                  @Field("title") String title,
                                  @Field("message") String message);


    @GET("donation-requests")
    Call<Donations> filterdonation(@Query("api_token") String api_token,
                                   @Query("blood_type_id") int blood_type_id,
                                   @Query("city_id") int city_id,
                                   @Query("page") int page);

    @GET("settings")
    Call<AppSettings> getappinformation(@Query("api_token") String api_token);

    @GET("notifications")
    Call<NotificationList> getlistofnotification(@Query("api_token") String api_token);

    @GET("notifications-count")
    Call<Notifcount> getnotifcount(@Query("api_token") String api_token);

    @GET("donation-request")
    Call<DisplayNotificationDetails> getdonationreguestdetails(@Query("api_token") String api_token,
                                                               @Query("donation_id") String donation_id);


}

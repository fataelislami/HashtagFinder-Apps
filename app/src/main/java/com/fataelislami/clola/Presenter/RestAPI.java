package com.fataelislami.clola.Presenter;


import com.fataelislami.clola.Models.CaptionSaveModels;
import com.fataelislami.clola.Models.LoginModels;
import com.fataelislami.clola.Models.MResponseImage;
import com.fataelislami.clola.Models.PostModels;
import com.fataelislami.clola.Models.RegisterModels;
import com.fataelislami.clola.Models.VisionModels;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RestAPI {
//        @GET("praytimes/get?lat=-6.914744&lng=107.609810&timezone=7")
//        Call <MPraytimes> getprayer();
//        @GET("get_near_things?radius=4&id_user=U4d764405f14220a7951fc5fab795495a")
//        Call <MBarang> getbarang(@Query("lat") String lat, @Query("lng") String lng,
//                                 @Query("status") String status);
//
//        @GET("laporan/byid")
//        Call <MBarang> getdetailbarang(@Query("id") String id);
//
//        @GET("barang/terbaru")
//        Call <MBarang> getterbaru();
//
//        @GET("official_location?radius=4")
//        Call <MOfficialLocation> getlocation(@Query("lat") String lat, @Query("lng") String lng);



//        @FormUrlEncoded
//        @POST("register/proses")
//        Call<MRegister> register(@Field("id") String id_user, @Field("password") String password,
//                                 @Field("name") String name, @Field("city") String city,
//                                 @Field("gender") String gender, @Field("phone") String phone);
//
        @FormUrlEncoded
        @POST("api/auth/login")
        Call<LoginModels> login(@Field("email") String email, @Field("password") String password);

        @FormUrlEncoded
        @POST("api/auth/register")
        Call<RegisterModels> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

        @Multipart
        @POST("api/upload/image")
        Call<MResponseImage> uploadPhotoMultipart(@Part MultipartBody.Part file);

        @Multipart
        @POST("api/posts/create")
        Call<PostModels> uploadPhotoMultipartWithPosts(@Part MultipartBody.Part file,@Part MultipartBody.Part caption,@Part MultipartBody.Part id_user);

        @FormUrlEncoded
        @POST("api/vision/imagebase64")
        Call<VisionModels> visionAPI(@Field("base64") String base64);

        @GET("api/posts/read")
        Call <CaptionSaveModels> getdata(@Query("id_user") String id_user);
//
//        @Multipart
//        @POST("laporan/create")
//        Call<MResponse> uploadLaporan(@Part MultipartBody.Part file, @Part("id_user") RequestBody id_user
//                , @Part("name") RequestBody name, @Part("desc") RequestBody desc
//                , @Part("datetime") RequestBody datetime, @Part("lat") RequestBody lat,
//                                      @Part("lng") RequestBody lng, @Part("status") RequestBody status);
}

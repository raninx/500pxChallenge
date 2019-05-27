package com.project.a500pxchallenge.network.services;

import com.project.a500pxchallenge.model.Photo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetPhotosService {

    @GET("v1/photos")
    Call<Photo> getAllPhotos(@Query("consumer_key") String key, @Query("page") int page);

}
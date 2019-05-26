package com.project.a500pxchallenge.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.project.a500pxchallenge.R;
import com.project.a500pxchallenge.Util.Util;
import com.project.a500pxchallenge.adapters.CustomAdapter;
import com.project.a500pxchallenge.model.Photo;
import com.project.a500pxchallenge.network.RetrofitClientInstance;
import com.project.a500pxchallenge.network.services.GetPhotosService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDoalog;
    RecyclerView recyclerView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        try {
            GetPhotosService service = RetrofitClientInstance.getRetrofitInstance().create(GetPhotosService.class);
            Call<Photo> call = service.getAllPhotos(Util.getKey("key", getApplicationContext()));
            call.enqueue(new Callback<Photo>() {
                @Override
                public void onResponse(Call<Photo> call, Response<Photo> response) {
                    progressDoalog.dismiss();

                    Toast.makeText(getBaseContext(),"success",Toast.LENGTH_LONG).show();

//                    generateDataList(response.body());

                }

                @Override
                public void onFailure(Call<Photo> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Photo> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new CustomAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}



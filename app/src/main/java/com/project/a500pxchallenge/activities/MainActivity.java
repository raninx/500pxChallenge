package com.project.a500pxchallenge.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.project.a500pxchallenge.R;
import com.project.a500pxchallenge.Util.Util;
import com.project.a500pxchallenge.adapters.CustomAdapter;
import com.project.a500pxchallenge.model.Photo;
import com.project.a500pxchallenge.model.Photo.Item;
import com.project.a500pxchallenge.network.RetrofitClientInstance;
import com.project.a500pxchallenge.network.services.GetPhotosService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnItemClickListener {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    private boolean loading = false;
    private boolean isScrollCalled;
    int isLastPage = 10;
    int pageCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=findViewById(R.id.progressBar);

        //setting up recyclerview
        recyclerView = findViewById(R.id.customRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        getDataList(0);

        //Pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastvisibleitemposition = manager.findLastVisibleItemPosition();

                if (lastvisibleitemposition == adapter.getItemCount() - 1) {

                    if (!loading && pageCount != isLastPage) {
                        loading = true;
                        getDataList(++pageCount);
                        isScrollCalled = Boolean.TRUE;
                    }
                }
            }
        });
    }

    //Get the data from API
    private void getDataList(int pageCount) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            GetPhotosService service = RetrofitClientInstance.getRetrofitInstance().create(GetPhotosService.class);
            Call<Photo> call = service.getAllPhotos(Util.getKey("key", getApplicationContext()),"popular",3,pageCount);
            call.enqueue(new Callback<Photo>() {
                @Override
                public void onResponse(Call<Photo> call, Response<Photo> response) {
//              Toast.makeText(getBaseContext(),"success",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                    isLastPage = response.body().total_pages;
                    List<Item> list= response.body().photos;

                    loading = false;
                    if (!isScrollCalled) {
                      generateDataList(list);
                    } else {
                        adapter.updateList(list);
                    }
                }

                @Override
                public void onFailure(Call<Photo> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Photo.Item> photoList) {
        adapter = new CustomAdapter(this,photoList,this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
        intent.putExtra("item",item);
        startActivity(intent);
    }
}



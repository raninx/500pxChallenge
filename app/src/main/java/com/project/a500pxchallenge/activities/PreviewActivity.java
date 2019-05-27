package com.project.a500pxchallenge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.project.a500pxchallenge.R;
import com.project.a500pxchallenge.model.Photo;

public class PreviewActivity extends AppCompatActivity {
    Photo.Item item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        ImageView image = findViewById(R.id.image);
        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);

        if(getIntent()!=null) {
            item = (Photo.Item) getIntent().getSerializableExtra("item");
        }

        if(item!=null) {
            title.setText(item.name);
            description.setText(item.description);
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(this).load(item.images.get(0).https_url).apply(options).into(image);
        }

    }
}

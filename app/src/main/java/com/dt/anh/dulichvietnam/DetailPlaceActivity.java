package com.dt.anh.dulichvietnam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DetailPlaceActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout lnlLikePlace;
    private ImageView ivLikePlace;
    private boolean flagLike = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        initViews();

        try {
            getSupportActionBar().setTitle("Địa danh");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void initViews() {
        lnlLikePlace = (LinearLayout) findViewById(R.id.lnl_like_detail_place);
        ivLikePlace = (ImageView) findViewById(R.id.iv_like_detail_place);
        lnlLikePlace.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_like_detail_place:
                if (flagLike) {
                    ivLikePlace.setImageResource(R.drawable.ic_heart);
                } else {
                    ivLikePlace.setImageResource(R.drawable.ic_heart_outline_tmp);
                }
                flagLike = !flagLike;
                break;
            default:
                break;
        }
    }
}

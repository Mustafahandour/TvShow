package com.ghandour.tvshow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;


import com.ghandour.tvshow.R;
import com.ghandour.tvshow.ViewModels.MostPopularTvShowsViewModel;
import com.ghandour.tvshow.adapters.TvShowsAdapter;
import com.ghandour.tvshow.databinding.ActivityMainBinding;
import com.ghandour.tvshow.listeners.TvShowListeners;
import com.ghandour.tvshow.models.TvShow;
import com.ghandour.tvshow.utilities.MyNotificationReceiver;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements TvShowListeners {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTvShowsViewModel viewModel;
    private TvShowsAdapter tvShowsAdapter;
    private List<TvShow> tvShowList = new ArrayList<>();
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        doInitialization();

    }

    private void doInitialization() {
        activityMainBinding.tvShowRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);
        tvShowsAdapter = new TvShowsAdapter(tvShowList, this);
        activityMainBinding.tvShowRecyclerView.setAdapter(tvShowsAdapter);

        //recycler view scroll
        activityMainBinding.tvShowRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (activityMainBinding.tvShowRecyclerView.canScrollVertically(1)) {


                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;

                        getMostPopularTvShows();
                    }

                }
            }
        });


        activityMainBinding.imageWatchlist.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TvWatchListActivity.class)));
        activityMainBinding.imageSearch.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchTvShowActivity.class)));
        getMostPopularTvShows();
    }

    private void getMostPopularTvShows() {
        toggleLoading();
        viewModel.getMostPopularTvShows(currentPage).observe(this, tvShowResponse -> {
            toggleLoading();
            if (tvShowResponse != null) {
                totalAvailablePages = tvShowResponse.getPages();
                if (tvShowResponse.getTvShows() != null) {
                    int oldCount = tvShowList.size();
                    tvShowList.addAll(tvShowResponse.getTvShows());
                    tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShowList.size());
                }
            }
        });

    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityMainBinding.getIsLoading() != null &&
                    activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);

            } else activityMainBinding.setIsLoading(true);
        } else {
            if (activityMainBinding.getIsMoreLoading() != null &&
                    activityMainBinding.getIsMoreLoading()) {
                activityMainBinding.setIsMoreLoading(false);

            } else activityMainBinding.setIsMoreLoading(true);

        }
    }

    @Override
    protected void onDestroy() {
        activityMainBinding.tvShowRecyclerView.addOnScrollListener(null);
        activityMainBinding.imageWatchlist.setOnClickListener(null);
        activityMainBinding.imageSearch.setOnClickListener(null);
        super.onDestroy();
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {
        Intent intent = new Intent(this, TvShowDetailsActivity.class);
        intent.putExtra("id", tvShow.getId());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(this, MyNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+10000,pendingIntent);
        super.onBackPressed();
    }
}
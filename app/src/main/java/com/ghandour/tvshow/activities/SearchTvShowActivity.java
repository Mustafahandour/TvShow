package com.ghandour.tvshow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;


import com.ghandour.tvshow.R;
import com.ghandour.tvshow.ViewModels.SearchTvShowViewModel;
import com.ghandour.tvshow.adapters.TvShowsAdapter;
import com.ghandour.tvshow.databinding.ActivitySearchTvShowBinding;
import com.ghandour.tvshow.listeners.TvShowListeners;
import com.ghandour.tvshow.models.TvShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchTvShowActivity extends AppCompatActivity implements TvShowListeners {
    private ActivitySearchTvShowBinding activitySearchTvShowBinding;
    private SearchTvShowViewModel searchTvShowViewModel;
    private List<TvShow> tvShowList = new ArrayList<>();
    private TvShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchTvShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_tv_show);
        doInitialization();
    }

    private void doInitialization() {
        activitySearchTvShowBinding.imageBack.setOnClickListener(view -> onBackPressed());
        activitySearchTvShowBinding.tvShowRecyclerView.setHasFixedSize(true);
        searchTvShowViewModel = new ViewModelProvider(this).get(SearchTvShowViewModel.class);
        tvShowsAdapter = new TvShowsAdapter(tvShowList, this);
        activitySearchTvShowBinding.tvShowRecyclerView.setAdapter(tvShowsAdapter);
        activitySearchTvShowBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPage = 1;
                                totalAvailablePages = 1;
                                searchTvShow(s.toString());
                            });
                        }
                    }, 3000);
                } else {
                    tvShowList.clear();
                    tvShowsAdapter.notifyDataSetChanged();
                }
            }
        });
        activitySearchTvShowBinding.tvShowRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchTvShowBinding.tvShowRecyclerView.canScrollVertically(1)) {
                    if (!activitySearchTvShowBinding.inputSearch.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1;
                            searchTvShow(activitySearchTvShowBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });
        activitySearchTvShowBinding.inputSearch.requestFocus();
    }

    private void searchTvShow(String q) {
        toggleLoading();
        searchTvShowViewModel.searchTvShow(q, currentPage).observe(this, tvShowResponse -> {
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
            if (activitySearchTvShowBinding.getIsLoading() != null &&
                    activitySearchTvShowBinding.getIsLoading()) {
                activitySearchTvShowBinding.setIsLoading(false);

            } else activitySearchTvShowBinding.setIsLoading(true);
        } else {
            if (activitySearchTvShowBinding.getIsLoadingMore() != null &&
                    activitySearchTvShowBinding.getIsLoadingMore()) {
                activitySearchTvShowBinding.setIsLoadingMore(false);

            } else activitySearchTvShowBinding.setIsLoadingMore(true);

        }
    }

    @Override
    protected void onDestroy() {
        activitySearchTvShowBinding.imageBack.setOnClickListener(null);
        activitySearchTvShowBinding.inputSearch.addTextChangedListener(null);
        activitySearchTvShowBinding.tvShowRecyclerView.addOnScrollListener(null);
        super.onDestroy();
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TvShowDetailsActivity.class);
        intent.putExtra("id", tvShow.getId());
        startActivity(intent);

    }
}
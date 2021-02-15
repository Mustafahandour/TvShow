package com.ghandour.tvshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;


import com.ghandour.tvshow.R;
import com.ghandour.tvshow.ViewModels.WatchListViewModel;
import com.ghandour.tvshow.adapters.WatchListAdapter;
import com.ghandour.tvshow.databinding.ActivityTvWatchListBinding;
import com.ghandour.tvshow.listeners.WatchListListener;
import com.ghandour.tvshow.rommDb.TvShowRoom;
import com.ghandour.tvshow.utilities.MyNotificationReceiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TvWatchListActivity extends AppCompatActivity implements WatchListListener {
    private ActivityTvWatchListBinding activityTvWatchListBinding;
    private WatchListViewModel watchListViewModel;
    private WatchListAdapter watchListAdapter;
    private ExecutorService pool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_watch_list);
        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        diInitialization();
        Intent intent =new Intent(this, MyNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+60000*60*24,pendingIntent);

    }

    private void diInitialization() {
        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        activityTvWatchListBinding.imageBack.setOnClickListener(v -> onBackPressed());
        loadWatchList();
    }

    private void loadWatchList() {
        activityTvWatchListBinding.setIsLoading(true);
        watchListViewModel.loadWatchList().observe(this, tvShowRoomList -> {
            activityTvWatchListBinding.setIsLoading(false);
            if (tvShowRoomList.size() >0){

                watchListAdapter = new WatchListAdapter(tvShowRoomList, TvWatchListActivity.this);
                activityTvWatchListBinding.watchListRecyclerView.setAdapter(watchListAdapter);
                activityTvWatchListBinding.watchListRecyclerView.setVisibility(View.VISIBLE);
                watchListAdapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        activityTvWatchListBinding.imageBack.setOnClickListener(null);
        pool.shutdown();
        super.onDestroy();
    }



    @Override
    public void onTvShowClicked(TvShowRoom tvShowRoom) {
        Intent intent = new Intent(getApplicationContext(), TvShowDetailsActivity.class);
        intent.putExtra("id", tvShowRoom.getId());
        startActivity(intent);
    }

    @Override
    public void removeTvShowFromWatchList(TvShowRoom tvShowRoom, int position) {
        pool.execute(() -> {
            watchListViewModel.removeTvShowFromWatchList(tvShowRoom);
            runOnUiThread(() -> {

                        watchListAdapter.notifyDataSetChanged();


            });

        });
        watchListViewModel.loadWatchList().observe(this, tvShowRoomList -> {
            if (tvShowRoomList.size() ==0){

                watchListAdapter = new WatchListAdapter(tvShowRoomList, TvWatchListActivity.this);
                activityTvWatchListBinding.watchListRecyclerView.setAdapter(watchListAdapter);
                activityTvWatchListBinding.watchListRecyclerView.setVisibility(View.VISIBLE);
                watchListAdapter.notifyDataSetChanged();
            }
        });
    }
}
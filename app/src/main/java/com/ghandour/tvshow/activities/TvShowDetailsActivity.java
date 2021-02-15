package com.ghandour.tvshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.ghandour.tvshow.R;
import com.ghandour.tvshow.ViewModels.TvShowDetailsViewModel;
import com.ghandour.tvshow.adapters.EpisodesAdapter;
import com.ghandour.tvshow.adapters.ImageSliderAdapter;
import com.ghandour.tvshow.databinding.ActivityTvShowDetailsBinding;
import com.ghandour.tvshow.databinding.LayoutEpisodesBottomSheetBinding;
import com.ghandour.tvshow.models.Episode;
import com.ghandour.tvshow.responses.TvShowDetailsResponse;
import com.ghandour.tvshow.rommDb.TvShowRoom;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.dmoral.toasty.Toasty;


public class TvShowDetailsActivity extends AppCompatActivity {
    private TvShowDetailsViewModel tvShowDetailsViewModel;
    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private ExecutorService pool;
    private List<Episode> episodes = new ArrayList<>();
    private List<String> airDateList = new ArrayList<>();
    private List<String> episodeNumberList = new ArrayList<>();
    private List<String> episodeNameList = new ArrayList<>();
    private String airDate, episodeName, episodeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);
        int tvShowId = getIntent().getIntExtra("id", 0);
        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        doInitialization(String.valueOf(tvShowId));

    }

    private void doInitialization(String tvShowId) {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        activityTvShowDetailsBinding.imageBack.setOnClickListener(v -> onBackPressed());

        getTvShowDetails(String.valueOf(tvShowId));
    }

    private void getTvShowDetails(String tvShowId) {
        activityTvShowDetailsBinding.setIsLoading(true);

        tvShowDetailsViewModel.getTvShowDetails(tvShowId).observe(this,
                (TvShowDetailsResponse tvShowDetailsResponse) -> {

                    activityTvShowDetailsBinding.setIsLoading(false);

                    pool.execute(() -> {
                        long existId1 = tvShowDetailsViewModel.existId(tvShowDetailsResponse.getTvShowDetails().getId());
                        runOnUiThread(() -> {
                            if (existId1 > 0) {
                                activityTvShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_added);
                            }
                        });

                    });

                    try {
                        if (tvShowDetailsResponse.getTvShowDetails() != null) {

                            // slide image tvShow
                            if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null) {
                                loadingImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                            }

                            // data bind views
                            activityTvShowDetailsBinding.setTvShowImageURl(tvShowDetailsResponse.getTvShowDetails()
                                    .getImagePath());
                            activityTvShowDetailsBinding.imageTvShow.setVisibility(View.VISIBLE);
                            activityTvShowDetailsBinding.setDescription(
                                    String.valueOf(HtmlCompat.fromHtml(tvShowDetailsResponse.getTvShowDetails().getDescription(),
                                            HtmlCompat.FROM_HTML_MODE_LEGACY))
                            );
                            activityTvShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
                            activityTvShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);

                            //read more
                            activityTvShowDetailsBinding.textReadMore.setOnClickListener(v -> {

                                if (activityTvShowDetailsBinding.textReadMore.getText().toString().equals("Read More")) {
                                    activityTvShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                                    activityTvShowDetailsBinding.textDescription.setEllipsize(null);
                                    activityTvShowDetailsBinding.textReadMore.setText(R.string.read_less);
                                } else {
                                    activityTvShowDetailsBinding.textDescription.setMaxLines(4);
                                    activityTvShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                                    activityTvShowDetailsBinding.textReadMore.setText(R.string.read_more);
                                }
                            });
                            activityTvShowDetailsBinding.setRating(
                                    String.format(Locale.getDefault(), "%.2f",
                                            Double.parseDouble(tvShowDetailsResponse.getTvShowDetails().getRating())
                                    ));
                            if (tvShowDetailsResponse.getTvShowDetails().getGenres() != null) {
                                activityTvShowDetailsBinding.setGenre(tvShowDetailsResponse.getTvShowDetails().getGenres()[0]);
                            } else activityTvShowDetailsBinding.setGenre("N/A");

                            activityTvShowDetailsBinding.setTvShowName(tvShowDetailsResponse.getTvShowDetails().getName());
                            activityTvShowDetailsBinding.setNetworkCountry(tvShowDetailsResponse.getTvShowDetails().getNetwork() + " (" +
                                    tvShowDetailsResponse.getTvShowDetails().getCountry() + ")");
                            activityTvShowDetailsBinding.setStatus(tvShowDetailsResponse.getTvShowDetails().getStatus());
                            activityTvShowDetailsBinding.setStartedDate(tvShowDetailsResponse.getTvShowDetails().getStartDate());
                            activityTvShowDetailsBinding.textName.setVisibility(View.VISIBLE);
                            activityTvShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
                            activityTvShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
                            activityTvShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);


                            activityTvShowDetailsBinding.setRuntime(tvShowDetailsResponse.getTvShowDetails().getRuntime() + " Min");
                            activityTvShowDetailsBinding.viewDivider.setVisibility(View.VISIBLE);
                            activityTvShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                            activityTvShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);

                            // url website button
                            activityTvShowDetailsBinding.buttonWebsite.setOnClickListener(v -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetails().getUrl()));
                                startActivity(intent);
                            });

                            activityTvShowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
                            activityTvShowDetailsBinding.buttonEpisode.setVisibility(View.VISIBLE);

                            //episode button
                            activityTvShowDetailsBinding.buttonEpisode.setOnClickListener(v -> {
                                if (episodesBottomSheetDialog == null) {
                                    episodesBottomSheetDialog = new BottomSheetDialog(TvShowDetailsActivity.this);
                                    layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(TvShowDetailsActivity.this),
                                            R.layout.layout_episodes_bottom_sheet, findViewById(R.id.episodesContainer), false);

                                    episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                                    layoutEpisodesBottomSheetBinding.episodesRecyclerView.setAdapter(
                                            new EpisodesAdapter(tvShowDetailsResponse.getTvShowDetails().getEpisodes())
                                    );
                                    layoutEpisodesBottomSheetBinding.textTitle.setText(String.format("Episodes | %s ",
                                            tvShowDetailsResponse.getTvShowDetails().getName()));
                                    layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(v1 -> episodesBottomSheetDialog.dismiss());


                                    // ... optional section start ..//
                                    FrameLayout frameLayout = episodesBottomSheetDialog.findViewById(
                                            com.google.android.material.R.id.design_bottom_sheet);
                                    if (frameLayout != null) {
                                        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                                        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                    }

                                    // optional section end..//
                                    episodesBottomSheetDialog.show();
                                }
                            });
                        }
                    } catch (Exception Ignored) {

                    }

                    // room save tvShow
                    episodes = tvShowDetailsResponse.getTvShowDetails().getEpisodes();

                    for (int i = episodes.size() - 5; i < episodes.size(); i++) {
                        airDate = episodes.get(i).getAirDate();
                        episodeName = episodes.get(i).getName();
                        episodeNumber = "S" + episodes.get(i).getSeason() + " e" + episodes.get(i).getEpisode();
                        airDateList.add(airDate);
                        episodeNameList.add(episodeName);
                        episodeNumberList.add(episodeNumber);

                    }



                    activityTvShowDetailsBinding.imageWatchList.setOnClickListener(v -> pool.execute(() -> {

                        long existId = tvShowDetailsViewModel.existId(Long.parseLong(tvShowId));
                        TvShowRoom tvShowRoom = new TvShowRoom(tvShowDetailsResponse.getTvShowDetails().getId(),
                                tvShowDetailsResponse.getTvShowDetails().getName(), tvShowDetailsResponse.getTvShowDetails().getStartDate(),
                                tvShowDetailsResponse.getTvShowDetails().getCountry(), tvShowDetailsResponse.getTvShowDetails().getStatus(),
                                tvShowDetailsResponse.getTvShowDetails().getNetwork(), tvShowDetailsResponse.getTvShowDetails().getImagePath(),
                                episodeNameList, episodeNumberList, airDateList);
                        if (existId > 0) {
                            tvShowDetailsViewModel.removeTvShowFromWatchList(tvShowRoom);
                        } else {
                            tvShowDetailsViewModel.addToWatchList(tvShowRoom);
                        }
                        runOnUiThread(() -> {
                            if (existId > 0) {
                                activityTvShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_watchlist);
                                Toasty.info(getApplicationContext(), "TvShow Removed !", Toast.LENGTH_SHORT, true).show();
                            } else {
                                activityTvShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_added);
                                Toasty.success(getApplicationContext(), "TvShow Added !", Toast.LENGTH_SHORT, true).show();

                            }
                        });
                    }));

                    activityTvShowDetailsBinding.imageWatchList.setVisibility(View.VISIBLE);


                }


        );
    }

    @Override
    protected void onDestroy() {
        activityTvShowDetailsBinding.buttonEpisode.setOnClickListener(null);
        activityTvShowDetailsBinding.buttonWebsite.setOnClickListener(null);
        activityTvShowDetailsBinding.textReadMore.setOnClickListener(null);
        activityTvShowDetailsBinding.imageWatchList.setOnClickListener(null);
        pool.shutdown();
        super.onDestroy();
    }

    private void loadingImageSlider(String[] sliderImage) {
        activityTvShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTvShowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImage));
        activityTvShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.viewFadeEdge.setVisibility(View.VISIBLE);
        setUPSliderIndicator(sliderImage.length);
        activityTvShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });


    }

    private void setUPSliderIndicator(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            activityTvShowDetailsBinding.layoutSliderIndicator.addView(indicators[i]);
        }
        activityTvShowDetailsBinding.layoutSliderIndicator.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityTvShowDetailsBinding.layoutSliderIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTvShowDetailsBinding.layoutSliderIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active));

            } else
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));

        }
    }


}
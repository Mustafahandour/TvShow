package com.ghandour.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghandour.tvshow.R;
import com.ghandour.tvshow.databinding.ItemContainerSliderBinding;


public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private String[] sliderImage;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImage) {
        this.sliderImage = sliderImage;

    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderBinding itemContainerSliderBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_container_slider, parent, false);

        return new ImageSliderViewHolder(itemContainerSliderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }


    public class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerSliderBinding itemContainerSliderBinding;

        public ImageSliderViewHolder(ItemContainerSliderBinding itemContainerSliderBinding) {
            super(itemContainerSliderBinding.getRoot());
            this.itemContainerSliderBinding = itemContainerSliderBinding;
        }

        public void bindSliderImage(String imageURL) {
            itemContainerSliderBinding.setImageURL(imageURL);
        }
    }
}

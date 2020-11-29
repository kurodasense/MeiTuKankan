package com.example.thelastworks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class classifyAdapter extends RecyclerView.Adapter<classifyAdapter.ViewHolder>{
    private Context mContext;
    private List<IndexImage> mIndexImageList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cla_image;
        public ViewHolder(View view){
            super(view);
            cla_image=(ImageView) view.findViewById(R.id.classify_image);

        }
    }
    public classifyAdapter(List<IndexImage> imageList){
        mIndexImageList=imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view=LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cla_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                IndexImage indexImage = mIndexImageList.get(position);
                Intent intent = new Intent(mContext, SpaceImageDetailActivity.class);
                intent.putExtra(SpaceImageDetailActivity.IMAGE_URL, indexImage.getImageUrl());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IndexImage indexImage=mIndexImageList.get(position);
        //Glide.with(mContext).load(indexImage.getImageUrl()).into(holder.cla_image);
        ImageLoader.getInstance().displayImage(indexImage.getImageUrl(),holder.cla_image,ImageLoaderConfigs.getDefaultDisplayImageOptions(holder.cla_image.getContext()));

    }

    @Override
    public int getItemCount() {
        return mIndexImageList.size();
    }
}

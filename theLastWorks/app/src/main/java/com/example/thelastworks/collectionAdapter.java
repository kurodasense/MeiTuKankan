package com.example.thelastworks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class collectionAdapter extends RecyclerView.Adapter<collectionAdapter.ViewHolder> {
    private Context mContext;
    private List<IndexImage> mIndexImageList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView collect_image;

        public ViewHolder(View view){
            super(view);
            collect_image=(ImageView) view.findViewById(R.id.classify_image);
        }
    }
    public collectionAdapter(List<IndexImage> imageList){
        mIndexImageList=imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.collect_image.setOnClickListener(new View.OnClickListener() {
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
        ImageLoader.getInstance().displayImage(indexImage.getImageUrl(),holder.collect_image,ImageLoaderConfigs.getDefaultDisplayImageOptions(holder.collect_image.getContext()));
    }

    @Override
    public int getItemCount() {
        return mIndexImageList.size();
    }
}

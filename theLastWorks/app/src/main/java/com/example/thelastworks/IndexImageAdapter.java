package com.example.thelastworks;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class IndexImageAdapter extends RecyclerView.Adapter<IndexImageAdapter.ViewHolder> {

    private Context mContext;

    private List<IndexImage> mIndexImageList;



    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView indexImage;
        ImageLoader imageLoader = ImageLoader.getInstance();

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            indexImage = (ImageView) view.findViewById(R.id.index_image);//获取imageView

        }
    }

    public IndexImageAdapter(List<IndexImage> imageList){
        mIndexImageList = imageList;
    }//图片列表

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.index_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //点击小图进入看大图的activity
        holder.cardView.setOnClickListener(new View.OnClickListener(){
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
        IndexImage indeximage = mIndexImageList.get(position);
        //用imageloader放图片
        ImageLoader.getInstance().displayImage(indeximage.getImageUrl(),holder.indexImage, ImageLoaderConfigs.getDefaultDisplayImageOptions(holder.indexImage.getContext()));
//        Glide.with(mContext).load(indeximage.getImageUrl()).into(holder.indexImage);//把获取到的图片放进ImageView中
    }


    @Override
    public int getItemCount() {
        return mIndexImageList.size();
    }




}

package com.ikunic.imagetagger.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ikunic.imagetagger.R;

import java.util.List;

/**
 * Created by Kunal S on 8/3/2016.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder> {

    private List<String> mBitmapPath;
    private RecyclerView mRecyclerView;

    public ImageRecyclerAdapter(List<String> bitmapPaths){
        mBitmapPath=bitmapPaths;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_image_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rect scrollBounds=new Rect();
        mRecyclerView.getHitRect(scrollBounds);
        if(holder.mImageView.getLocalVisibleRect(scrollBounds)){
            Bitmap image=BitmapFactory.decodeFile(mBitmapPath.get(position));
            Bitmap thumbnail= ThumbnailUtils.extractThumbnail(image,20,20);
            holder.mImageView.setImageBitmap(thumbnail);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView=recyclerView;
    }

    @Override
    public int getItemCount() {
        return mBitmapPath.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }
}

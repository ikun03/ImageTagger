package com.ikunic.imagetagger.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ikunic.imagetagger.R;
import com.ikunic.imagetagger.imagedecodinghandling.ImageDecodeAsyncTask;

import java.util.List;

/**
 * Created by Kunal S on 8/3/2016.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder> {

    private List<String> mBitmapPath;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public ImageRecyclerAdapter(List<String> bitmapPaths,Context context){
        mBitmapPath=bitmapPaths;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_image_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Rect scrollBounds=new Rect();
//        mRecyclerView.getHitRect(scrollBounds);
//        Bitmap image=BitmapFactory.decodeFile(mBitmapPath.get(position));
//        Bitmap thumbnail= ThumbnailUtils.extractThumbnail(image,20,20);
//        holder.mImageView.setImageBitmap(thumbnail);

        //Original Implementation
//        holder.mImageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),android.R.drawable.ic_menu_gallery));
//        ImageDecodeAsyncTask task=new ImageDecodeAsyncTask(holder.mImageView);
//        task.execute(mBitmapPath.get(position));

        loadBitmap(mBitmapPath.get(position),holder.mImageView);

    }

    private void loadBitmap(String imagePath, ImageView imageView) {
        if(cancelPotentialWork(imagePath,imageView)){
            final ImageDecodeAsyncTask task=new ImageDecodeAsyncTask(imageView);
            final AsyncDrawable asyncDrawable=new AsyncDrawable(task,mContext.getResources(),BitmapFactory.decodeResource(mContext.getResources(),android.R.drawable.ic_menu_gallery));
            imageView.setImageDrawable(asyncDrawable);
            task.execute(imagePath);
        }
    }

    private boolean cancelPotentialWork(String imagePath, ImageView imageView) {
        final ImageDecodeAsyncTask imageDecodeAsyncTask=getImageDecodeAsyncTask(imageView);

        if(imageDecodeAsyncTask!= null){
            final String imageDecodeData=imageDecodeAsyncTask.mImagePath;
            if(imageDecodeData==""||imageDecodeData!=imagePath){
                imageDecodeAsyncTask.cancel(true);
            }
            else {
                return false;
            }
        }
        return true;
    }

    public static ImageDecodeAsyncTask getImageDecodeAsyncTask(ImageView imageView) {
        if(imageView!=null){
            final Drawable drawable=imageView.getDrawable();
            if(drawable instanceof AsyncDrawable){
                final AsyncDrawable asyncDrawable= (AsyncDrawable) drawable;
                return asyncDrawable.getImageDecodeAsyncTask();
            }
        }
        return null;
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

package com.ikunic.imagetagger.imagedecodinghandling;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Kunal S on 8/11/2016.
 */
public class ImageDecodeAsyncTask extends AsyncTask<String,Void,Bitmap> {

    private final WeakReference<ImageView> mImageViewWeakReference;

    public ImageDecodeAsyncTask(ImageView imageView){
        mImageViewWeakReference= new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap image= BitmapFactory.decodeFile(strings[0]);
        Bitmap thumbnail= ThumbnailUtils.extractThumbnail(image,150,150);
        return thumbnail;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(mImageViewWeakReference!=null && bitmap!=null){
            final ImageView imageView=mImageViewWeakReference.get();
            if(imageView!=null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}

package com.ikunic.imagetagger.imagedecodinghandling;

import android.graphics.Bitmap;

/**
 * Created by Kunal S on 8/10/2016.
 */
public class PhotoTask implements PhotoDecodeRunnable.TaskDecodeRunnable {
    @Override
    public void setImageDecodeThread(Thread DecodeThread) {

    }

    @Override
    public int getTargetWidth() {
        return 0;
    }

    @Override
    public int getTargetHeight() {
        return 0;
    }

    @Override
    public void setBitmapImage(Bitmap image) {

    }
}

package com.ikunic.imagetagger.imagedecodinghandling;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;

/**
 * Created by Kunal S on 8/10/2016.
 */
public class PhotoDecodeRunnable implements Runnable {

    final TaskDecodeRunnable photoTask;
    private final String imagePath;


    interface TaskDecodeRunnable {

        void setImageDecodeThread(Thread DecodeThread);

        int getTargetWidth();

        int getTargetHeight();

        void setBitmapImage(Bitmap image);

    }

    public PhotoDecodeRunnable(String imagePath, TaskDecodeRunnable photoTask) {
        this.imagePath = imagePath;
        this.photoTask = photoTask;
    }

    @Override
    public void run() {
        photoTask.setImageDecodeThread(Thread.currentThread());
        Bitmap returnBitmap = null;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();

            int width = photoTask.getTargetWidth();
            int height = photoTask.getTargetHeight();

            if (Thread.interrupted()) {
                return;
            }

            options.inJustDecodeBounds = true;
            try {
                Bitmap image = BitmapFactory.decodeFile(imagePath, options);
                returnBitmap = ThumbnailUtils.extractThumbnail(image, 20, 20);
            } catch (Exception e) {
                Log.e("Photo Decode Runnable","Out of memory in decode stage. Throttling.");

                    /*
                     * Tells the system that garbage collection is
                     * necessary. Notice that collection may or may not
                     * occur.
                     */
                java.lang.System.gc();

                if (Thread.interrupted()) {
                    return;

                }
                    /*
                     * Tries to pause the thread for 250 milliseconds,
                     * and catches an Exception if something tries to
                     * activate the thread before it wakes up.
                     */
                try {
                    Thread.sleep(100);
                } catch (java.lang.InterruptedException interruptException) {
                    return;
                }
            }

        } finally {
            if(null==returnBitmap){
                Log.e("Photo Decode Runnable","Decoding Failed");
            }
            else {
                photoTask.setBitmapImage(returnBitmap);
            }
            photoTask.setImageDecodeThread(null);
            Thread.interrupted();
        }
    }
}

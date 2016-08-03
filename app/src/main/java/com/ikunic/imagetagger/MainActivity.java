package com.ikunic.imagetagger;

import android.Manifest;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ikunic.imagetagger.images.ImageRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED){
            //ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED){
            Cursor cr = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media.DATA,
                            MediaStore.Images.Media._ID,
                            MediaStore.Images.ImageColumns.TITLE,
                            MediaStore.Images.ImageColumns.DESCRIPTION,
                            MediaStore.Images.ImageColumns.DATE_TAKEN},
                    null
                    , null, null);

            List<Bitmap> imageList=new ArrayList<>();
            if(cr.moveToFirst()){
                do{
                    imageList.add(BitmapFactory.decodeFile(cr.getString(cr.getColumnIndex(MediaStore.Images.Media.DATA))));
                } while (cr.moveToNext());
                Log.e(TAG, imageList.size()+"");
            }
        }


        RecyclerView recycler= (RecyclerView) findViewById(R.id.recycler_master);
        RecyclerView.LayoutManager manager=new GridLayoutManager(this,3);
        recycler.setLayoutManager(manager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(new ImageRecyclerAdapter());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0:
                if(grantResults.length>0 && grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
                    Log.e(TAG,"Rawas, permission milali");
                }
                else {
                    Log.e(TAG,"Oops, no permission. Better Luck next time");
                }
        }
    }

}

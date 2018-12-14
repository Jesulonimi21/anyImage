package com.example.jesulonimi.anyimage;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

public class saveImage implements Target {

    Context context;

    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name;
    private String desc;

    public saveImage(Context context, ContentResolver contentResolver, String name, String desc) {

        this.contentResolverWeakReference =new WeakReference<ContentResolver>(contentResolver) ;
        this.name = name;
        this.desc = desc;
        this.context=context;
    }


    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver r=contentResolverWeakReference.get();

        if(r!=null){
            MediaStore.Images.Media.insertImage(r,bitmap,name,desc);

            StyleableToast.makeText(context,"image downloaded",R.style.customToast).show();

            //open gallery
        }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        StyleableToast.makeText(context,"bitmap failed",R.style.customToast).show();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        StyleableToast.makeText(context,"image loading",R.style.customToast).show();
    }




}

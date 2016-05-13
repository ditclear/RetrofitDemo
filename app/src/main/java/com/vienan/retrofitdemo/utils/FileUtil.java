package com.vienan.retrofitdemo.utils;

import android.content.Context;
import android.os.Parcelable;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by kygo on 2016/5/13 0013.
 */
public class FileUtil {
    Context mContext;
    public  boolean saveObject(Parcelable parcelable, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = mContext.getApplicationContext().openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(parcelable);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }
}

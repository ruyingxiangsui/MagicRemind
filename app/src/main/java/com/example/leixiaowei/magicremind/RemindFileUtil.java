package com.example.leixiaowei.magicremind;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by leixiaowei on 16/9/15.
 */
public final class RemindFileUtil {
    public static final String FILE_PATH = "remind";
    private RemindFileUtil() {
    }

    public static void saveObject(Context context, Object saveObject) {

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FILE_PATH, Activity.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(saveObject);
        } catch (Exception e) {
            Log.e("save error", e.toString());
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }

                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                Log.e("save object failed", e.toString());
            }

        }
    }

    public static Object readObject(Context context) {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(FILE_PATH);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            Log.e("read error", e.toString());
            return null;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                Log.e("read object failed", e.toString());
            }
        }
    }


}

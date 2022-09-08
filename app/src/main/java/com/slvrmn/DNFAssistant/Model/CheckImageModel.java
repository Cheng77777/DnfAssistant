package com.slvrmn.DNFAssistant.Model;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.slvrmn.DNFAssistant.MainApplication;

import java.io.IOException;
import java.io.InputStream;

public class CheckImageModel {
    public Rectangle rectangle;
    public Bitmap image;
    public float threshold;

    private static final AssetManager assetManager = MainApplication.getInstance().getAssets();
    private static InputStream input;

    public CheckImageModel(int x1, int y1, int x2, int y2, String fileName) {
        this.rectangle = new Rectangle(x1, y1, x2, y2);
        image = readImage(fileName);
        threshold = 0.85f;
    }

    public CheckImageModel(int x1, int y1, int x2, int y2, String fileName, float threshold) {
        this.rectangle = new Rectangle(x1, y1, x2, y2);
        image = readImage(fileName);
        this.threshold = threshold;
    }

    public static synchronized Bitmap readImage(String name) {
        try {
            input = assetManager.open(name);
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            input.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

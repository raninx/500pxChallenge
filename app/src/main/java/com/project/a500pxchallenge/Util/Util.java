package com.project.a500pxchallenge.Util;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {
    public static String getKey(String key,Context context) throws IOException {
        Properties properties = new Properties();;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("apikey");
        properties.load(inputStream);
        return properties.getProperty(key);
    }
}

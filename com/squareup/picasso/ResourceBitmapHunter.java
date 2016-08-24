package com.squareup.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.squareup.picasso.Picasso.LoadedFrom;
import java.io.IOException;

class ResourceBitmapHunter extends BitmapHunter {
    private final Context context;

    ResourceBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(picasso, dispatcher, cache, stats, action);
        this.context = context;
    }

    Bitmap decode(Request data) throws IOException {
        Resources res = Utils.getResources(this.context, data);
        return decodeResource(res, Utils.getResourceId(res, data), data);
    }

    LoadedFrom getLoadedFrom() {
        return LoadedFrom.DISK;
    }

    private Bitmap decodeResource(Resources resources, int id, Request data) {
        Options bitmapOptions = BitmapHunter.createBitmapOptions(data);
        if (data.hasSize()) {
            bitmapOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(resources, id, bitmapOptions);
            BitmapHunter.calculateInSampleSize(data.targetWidth, data.targetHeight, bitmapOptions);
        }
        return BitmapFactory.decodeResource(resources, id, bitmapOptions);
    }
}

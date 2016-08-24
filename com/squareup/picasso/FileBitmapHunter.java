package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import java.io.IOException;

class FileBitmapHunter extends ContentStreamBitmapHunter {
    FileBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(context, picasso, dispatcher, cache, stats, action);
    }

    Bitmap decode(Request data) throws IOException {
        setExifRotation(getFileExifRotation(data.uri));
        return super.decode(data);
    }

    static int getFileExifRotation(Uri uri) throws IOException {
        switch (new ExifInterface(uri.getPath()).getAttributeInt("Orientation", 1)) {
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                return 180;
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                return 90;
            case Value.INTEGER_FIELD_NUMBER /*8*/:
                return 270;
            default:
                return 0;
        }
    }
}

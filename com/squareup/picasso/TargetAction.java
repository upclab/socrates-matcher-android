package com.squareup.picasso;

import android.graphics.Bitmap;
import com.squareup.picasso.Picasso.LoadedFrom;

final class TargetAction extends Action<Target> {
    TargetAction(Picasso picasso, Target target, Request data, boolean skipCache, String key) {
        super(picasso, target, data, skipCache, false, 0, null, key);
    }

    void complete(Bitmap result, LoadedFrom from) {
        if (result == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", new Object[]{this}));
        }
        Target target = (Target) getTarget();
        if (target != null) {
            target.onBitmapLoaded(result, from);
            if (result.isRecycled()) {
                throw new IllegalStateException("Target callback must not recycle bitmap!");
            }
        }
    }

    void error() {
        Target target = (Target) getTarget();
        if (target != null) {
            target.onBitmapFailed(this.errorDrawable);
        }
    }
}

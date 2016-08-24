package com.adsdk.sdk.video;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import java.io.InputStream;
import java.net.URL;

public class NavIcon extends AspectRatioImageViewWidth implements OnClickListener {
    private Context mContext;
    private Handler mHandler;
    private NavIconData mIcon;

    /* renamed from: com.adsdk.sdk.video.NavIcon.1 */
    class C00591 implements Runnable {
        private final /* synthetic */ String val$url;

        /* renamed from: com.adsdk.sdk.video.NavIcon.1.1 */
        class C00581 implements Runnable {
            private final /* synthetic */ Drawable val$image;

            C00581(Drawable drawable) {
                this.val$image = drawable;
            }

            public void run() {
                NavIcon.this.setImageDrawable(this.val$image);
                NavIcon.this.setVisibility(0);
                NavIcon.this.requestLayout();
            }
        }

        C00591(String str) {
            this.val$url = str;
        }

        public void run() {
            Drawable image = NavIcon.this.fetchImage(this.val$url);
            if (image != null) {
                NavIcon.this.mHandler.post(new C00581(image));
            }
        }
    }

    public NavIcon(Context context, NavIconData icon) {
        super(context);
        int padding = (int) TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics());
        this.mContext = context;
        this.mIcon = icon;
        setPadding(padding, 0, padding, 0);
        this.mHandler = new Handler();
        setVisibility(8);
        setImageDrawable(icon.iconUrl);
        setOnClickListener(this);
    }

    private void setImageDrawable(String url) {
        new Thread(new C00591(url)).start();
    }

    private Drawable fetchImage(String urlString) {
        InputStream in = null;
        try {
            in = (InputStream) new URL(urlString).getContent();
            Bitmap b = BitmapFactory.decodeStream(in);
            if (b != null) {
                DisplayMetrics m = this.mContext.getResources().getDisplayMetrics();
                int w = b.getWidth();
                int h = b.getHeight();
                int imageWidth = (int) TypedValue.applyDimension(1, (float) w, m);
                int imageHeight = (int) TypedValue.applyDimension(1, (float) h, m);
                if (!(imageWidth == w && imageHeight == h)) {
                    b = Bitmap.createScaledBitmap(b, imageWidth, imageHeight, false);
                }
                Drawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), b);
                if (in == null) {
                    return bitmapDrawable;
                }
                try {
                    in.close();
                    return bitmapDrawable;
                } catch (Exception e) {
                    return bitmapDrawable;
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e2) {
                }
            }
            try {
                return Drawable.createFromStream((InputStream) new URL(urlString).getContent(), "src");
            } catch (Exception e3) {
                return null;
            }
        } catch (Exception e4) {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e5) {
                }
            }
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e6) {
                }
            }
        }
    }

    public void onClick(View v) {
        try {
            if (this.mContext instanceof RichMediaActivity) {
                RichMediaActivity activity = this.mContext;
                if (this.mIcon.openType == 1) {
                    activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.mIcon.clickUrl)));
                    return;
                }
                String url = this.mIcon.clickUrl;
                if (url.startsWith("market:") || url.startsWith("http://market.android.com") || url.startsWith("sms:") || url.startsWith("tel:") || url.startsWith("mailto:") || url.startsWith("voicemail:") || url.startsWith("geo:") || url.startsWith("google.streetview:")) {
                    activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } else if (url.startsWith("mfox:external:")) {
                    activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url.substring(16))));
                } else if (url.startsWith("mfox:replayvideo")) {
                    try {
                        activity.getClass().getMethod("replayVideo", new Class[0]).invoke(activity, new Object[0]);
                    } catch (NoSuchMethodException e) {
                    } catch (Exception e2) {
                    }
                } else if (url.startsWith("mfox:playvideo")) {
                    try {
                        activity.getClass().getMethod("playVideo", new Class[0]).invoke(activity, new Object[0]);
                    } catch (NoSuchMethodException e3) {
                    } catch (Exception e4) {
                    }
                } else if (url.startsWith("mfox:skip")) {
                    activity.finish();
                } else {
                    activity.navigate(this.mIcon.clickUrl);
                }
            }
        } catch (Exception e5) {
        }
    }
}

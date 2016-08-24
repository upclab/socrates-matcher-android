package com.adsdk.sdk.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.client.methods.HttpGet;

public class ResourceManager {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final String BACK_ICON = "browser_back.png";
    public static final String BOTTOMBAR_BG = "bar.png";
    public static final String CLOSE_BUTTON_NORMAL = "close_button_normal.png";
    public static final String CLOSE_BUTTON_PRESSED = "close_button_pressed.png";
    public static final int DEFAULT_BACK_IMAGE_RESOURCE_ID = -14;
    public static final int DEFAULT_BOTTOMBAR_BG_RESOURCE_ID = -2;
    public static final int DEFAULT_CLOSE_BUTTON_NORMAL_RESOURCE_ID = -29;
    public static final int DEFAULT_CLOSE_BUTTON_PRESSED_RESOURCE_ID = -30;
    public static final int DEFAULT_EXTERNAL_IMAGE_RESOURCE_ID = -17;
    public static final int DEFAULT_FORWARD_IMAGE_RESOURCE_ID = -15;
    public static final int DEFAULT_PAUSE_IMAGE_RESOURCE_ID = -12;
    public static final int DEFAULT_PLAY_IMAGE_RESOURCE_ID = -11;
    public static final int DEFAULT_RELOAD_IMAGE_RESOURCE_ID = -16;
    public static final int DEFAULT_REPLAY_IMAGE_RESOURCE_ID = -13;
    public static final int DEFAULT_SKIP_IMAGE_RESOURCE_ID = -18;
    public static final int DEFAULT_TOPBAR_BG_RESOURCE_ID = -1;
    public static final String EXTERNAL_ICON = "browser_external.png";
    public static final String FORWARD_ICON = "browser_forward.png";
    public static final String PAUSE_ICON = "video_pause.png";
    public static final String PLAY_ICON = "video_play.png";
    public static final String RELOAD_ICON = "video_replay.png";
    public static final String REPLAY_ICON = "video_replay.png";
    public static final int RESOURCE_LOADED_MSG = 100;
    public static final String SKIP_ICON = "skip.png";
    public static final String TOPBAR_BG = "bar.png";
    public static final int TYPE_FILE = 0;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_ZIP = 1;
    public static final String VERSION = "version.txt";
    public static boolean sCancel;
    public static HttpGet sDownloadGet;
    public static boolean sDownloading;
    private static HashMap<Integer, Drawable> sResources;
    private Handler mHandler;
    private HashMap<Integer, Drawable> mResources;

    private class FetchImageTask extends AsyncTask<Void, Void, Boolean> {
        Context mContext;
        int mResourceId;
        String mUrl;

        public FetchImageTask(Context ctx, String url, int resId) {
            this.mContext = ctx;
            this.mUrl = url;
            this.mResourceId = resId;
        }

        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            ResourceManager.this.mHandler.sendMessage(ResourceManager.this.mHandler.obtainMessage(ResourceManager.RESOURCE_LOADED_MSG, this.mResourceId, ResourceManager.TYPE_FILE));
        }

        protected Boolean doInBackground(Void... params) {
            Drawable d = null;
            if (this.mUrl != null && this.mUrl.length() > 0) {
                d = fetchImage(this.mUrl);
            }
            if (d == null) {
                return Boolean.valueOf(ResourceManager.$assertionsDisabled);
            }
            ResourceManager.this.mResources.put(Integer.valueOf(this.mResourceId), d);
            return Boolean.valueOf(true);
        }

        private Drawable fetchImage(String urlString) {
            try {
                Bitmap b = BitmapFactory.decodeStream((InputStream) new URL(urlString).getContent());
                if (b != null) {
                    DisplayMetrics m = this.mContext.getResources().getDisplayMetrics();
                    int w = b.getWidth();
                    int h = b.getHeight();
                    int imageWidth = (int) TypedValue.applyDimension(ResourceManager.TYPE_ZIP, (float) w, m);
                    int imageHeight = (int) TypedValue.applyDimension(ResourceManager.TYPE_ZIP, (float) h, m);
                    if (!(imageWidth == w && imageHeight == h)) {
                        b = Bitmap.createScaledBitmap(b, imageWidth, imageHeight, ResourceManager.$assertionsDisabled);
                    }
                    return new BitmapDrawable(this.mContext.getResources(), b);
                }
            } catch (Exception e) {
            }
            return null;
        }
    }

    static {
        $assertionsDisabled = !ResourceManager.class.desiredAssertionStatus() ? true : $assertionsDisabled;
        sDownloading = $assertionsDisabled;
        sCancel = $assertionsDisabled;
        sResources = new HashMap();
    }

    public static Drawable getDefaultResource(int resId) {
        return (Drawable) sResources.get(Integer.valueOf(resId));
    }

    public static Drawable getDefaultSkipButton(Context ctx) {
        return buildDrawable(ctx, SKIP_ICON);
    }

    public static boolean resourcesInstalled(Context ctx) {
        String[] files = ctx.fileList();
        for (int i = TYPE_FILE; i < files.length; i += TYPE_ZIP) {
            if (VERSION.equals(files[i])) {
                return true;
            }
        }
        return $assertionsDisabled;
    }

    public static long getInstalledVersion(Context ctx) {
        long result = -1;
        FileInputStream in = null;
        try {
            in = ctx.openFileInput(VERSION);
            result = Long.valueOf(new BufferedReader(new InputStreamReader(in, CharEncoding.UTF_8)).readLine()).longValue();
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e3) {
                }
            }
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e4) {
                }
            }
        }
        return result;
    }

    public static void saveInstalledVersion(Context ctx, long version) {
        FileOutputStream out = null;
        try {
            out = ctx.openFileOutput(VERSION, TYPE_FILE);
            OutputStreamWriter osr = new OutputStreamWriter(out, CharEncoding.UTF_8);
            osr.write(String.valueOf(version));
            osr.flush();
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e3) {
                }
            }
        } catch (Throwable th) {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e4) {
                }
            }
        }
    }

    public void releaseInstance() {
        Iterator<Entry<Integer, Drawable>> it = this.mResources.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, Drawable> pairsEntry = (Entry) it.next();
            it.remove();
            BitmapDrawable bitmapDrawable = (BitmapDrawable) pairsEntry.getValue();
        }
        if ($assertionsDisabled || this.mResources.size() == 0) {
            System.gc();
            return;
        }
        throw new AssertionError();
    }

    private static void initDefaultResource(Context ctx, int resource) {
        switch (resource) {
            case DEFAULT_CLOSE_BUTTON_PRESSED_RESOURCE_ID /*-30*/:
                registerImageResource(ctx, DEFAULT_CLOSE_BUTTON_PRESSED_RESOURCE_ID, CLOSE_BUTTON_PRESSED);
            case DEFAULT_CLOSE_BUTTON_NORMAL_RESOURCE_ID /*-29*/:
                registerImageResource(ctx, DEFAULT_CLOSE_BUTTON_NORMAL_RESOURCE_ID, CLOSE_BUTTON_NORMAL);
            case DEFAULT_SKIP_IMAGE_RESOURCE_ID /*-18*/:
                registerImageResource(ctx, DEFAULT_SKIP_IMAGE_RESOURCE_ID, SKIP_ICON);
            case DEFAULT_EXTERNAL_IMAGE_RESOURCE_ID /*-17*/:
                registerImageResource(ctx, DEFAULT_EXTERNAL_IMAGE_RESOURCE_ID, EXTERNAL_ICON);
            case DEFAULT_RELOAD_IMAGE_RESOURCE_ID /*-16*/:
                registerImageResource(ctx, DEFAULT_RELOAD_IMAGE_RESOURCE_ID, REPLAY_ICON);
            case DEFAULT_FORWARD_IMAGE_RESOURCE_ID /*-15*/:
                registerImageResource(ctx, DEFAULT_FORWARD_IMAGE_RESOURCE_ID, FORWARD_ICON);
            case DEFAULT_BACK_IMAGE_RESOURCE_ID /*-14*/:
                registerImageResource(ctx, DEFAULT_BACK_IMAGE_RESOURCE_ID, BACK_ICON);
            case DEFAULT_REPLAY_IMAGE_RESOURCE_ID /*-13*/:
                registerImageResource(ctx, DEFAULT_REPLAY_IMAGE_RESOURCE_ID, REPLAY_ICON);
            case DEFAULT_PAUSE_IMAGE_RESOURCE_ID /*-12*/:
                registerImageResource(ctx, DEFAULT_PAUSE_IMAGE_RESOURCE_ID, PAUSE_ICON);
            case DEFAULT_PLAY_IMAGE_RESOURCE_ID /*-11*/:
                registerImageResource(ctx, DEFAULT_PLAY_IMAGE_RESOURCE_ID, PLAY_ICON);
            case DEFAULT_BOTTOMBAR_BG_RESOURCE_ID /*-2*/:
                registerImageResource(ctx, DEFAULT_BOTTOMBAR_BG_RESOURCE_ID, TOPBAR_BG);
            case TYPE_UNKNOWN /*-1*/:
                registerImageResource(ctx, TYPE_UNKNOWN, TOPBAR_BG);
            default:
        }
    }

    private static void registerImageResource(Context ctx, int resId, String name) {
        Drawable d = buildDrawable(ctx, name);
        if (d != null) {
            sResources.put(Integer.valueOf(resId), d);
        }
    }

    private static Drawable buildDrawable(Context ctx, String name) {
        InputStream in = null;
        try {
            in = ctx.getClass().getClassLoader().getResourceAsStream("defaultresources/" + name);
            Bitmap b = BitmapFactory.decodeStream(in);
            if (b != null) {
                DisplayMetrics m = ctx.getResources().getDisplayMetrics();
                int w = b.getWidth();
                int h = b.getHeight();
                int imageWidth = (int) TypedValue.applyDimension(TYPE_ZIP, (float) w, m);
                int imageHeight = (int) TypedValue.applyDimension(TYPE_ZIP, (float) h, m);
                if (!(imageWidth == w && imageHeight == h)) {
                    b = Bitmap.createScaledBitmap(b, imageWidth, imageHeight, $assertionsDisabled);
                }
                Drawable bitmapDrawable = new BitmapDrawable(ctx.getResources(), b);
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
            return null;
        } catch (Exception e3) {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e4) {
                }
            }
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e5) {
                }
            }
        }
    }

    public static boolean isDownloading() {
        return sDownloading;
    }

    public static void cancel() {
        sCancel = true;
        if (sDownloadGet != null) {
            sDownloadGet.abort();
            sDownloadGet = null;
        }
        sResources.clear();
    }

    public ResourceManager(Context ctx, Handler h) {
        this.mResources = new HashMap();
        this.mHandler = h;
    }

    public void fetchResource(Context ctx, String url, int resourceId) {
        if (sResources.get(Integer.valueOf(resourceId)) == null) {
            new FetchImageTask(ctx, url, resourceId).execute(new Void[TYPE_FILE]);
        }
    }

    public boolean containsResource(int resourceId) {
        return (this.mResources.get(Integer.valueOf(resourceId)) == null && this.mResources.get(Integer.valueOf(resourceId)) == null) ? $assertionsDisabled : true;
    }

    public Drawable getResource(Context ctx, int resourceId) {
        BitmapDrawable d = (BitmapDrawable) this.mResources.get(Integer.valueOf(resourceId));
        return d != null ? d : getStaticResource(ctx, resourceId);
    }

    public static Drawable getStaticResource(Context ctx, int resourceId) {
        BitmapDrawable d = (BitmapDrawable) sResources.get(Integer.valueOf(resourceId));
        if (d != null && !d.getBitmap().isRecycled()) {
            return d;
        }
        initDefaultResource(ctx, resourceId);
        return (BitmapDrawable) sResources.get(Integer.valueOf(resourceId));
    }
}

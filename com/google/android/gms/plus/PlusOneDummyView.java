package com.google.android.gms.plus;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.GooglePlayServicesUtil;
import org.joda.time.MutableDateTime;

public class PlusOneDummyView extends FrameLayout {
    public static final String TAG = "PlusOneDummyView";

    /* renamed from: com.google.android.gms.plus.PlusOneDummyView.d */
    private interface C0203d {
        Drawable getDrawable(int i);

        boolean isValid();
    }

    /* renamed from: com.google.android.gms.plus.PlusOneDummyView.a */
    private static class C0611a implements C0203d {
        private Context mContext;

        private C0611a(Context context) {
            this.mContext = context;
        }

        public Drawable getDrawable(int size) {
            return this.mContext.getResources().getDrawable(17301508);
        }

        public boolean isValid() {
            return true;
        }
    }

    /* renamed from: com.google.android.gms.plus.PlusOneDummyView.b */
    private static class C0612b implements C0203d {
        private Context mContext;

        private C0612b(Context context) {
            this.mContext = context;
        }

        public Drawable getDrawable(int size) {
            try {
                String str;
                Resources resources = this.mContext.createPackageContext(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, 4).getResources();
                String str2 = GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE;
                switch (size) {
                    case MutableDateTime.ROUND_NONE /*0*/:
                        str = "ic_plusone_small";
                        break;
                    case Value.TYPE_FIELD_NUMBER /*1*/:
                        str = "ic_plusone_medium";
                        break;
                    case Value.STRING_FIELD_NUMBER /*2*/:
                        str = "ic_plusone_tall";
                        break;
                    default:
                        str = "ic_plusone_standard";
                        break;
                }
                return resources.getDrawable(resources.getIdentifier(str, "drawable", str2));
            } catch (NameNotFoundException e) {
                return null;
            }
        }

        public boolean isValid() {
            try {
                this.mContext.createPackageContext(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, 4).getResources();
                return true;
            } catch (NameNotFoundException e) {
                return false;
            }
        }
    }

    /* renamed from: com.google.android.gms.plus.PlusOneDummyView.c */
    private static class C0613c implements C0203d {
        private Context mContext;

        private C0613c(Context context) {
            this.mContext = context;
        }

        public Drawable getDrawable(int size) {
            String str;
            switch (size) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    str = "ic_plusone_small_off_client";
                    break;
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    str = "ic_plusone_medium_off_client";
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str = "ic_plusone_tall_off_client";
                    break;
                default:
                    str = "ic_plusone_standard_off_client";
                    break;
            }
            return this.mContext.getResources().getDrawable(this.mContext.getResources().getIdentifier(str, "drawable", this.mContext.getPackageName()));
        }

        public boolean isValid() {
            return (this.mContext.getResources().getIdentifier("ic_plusone_small_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_medium_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_tall_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_standard_off_client", "drawable", this.mContext.getPackageName()) == 0) ? false : true;
        }
    }

    public PlusOneDummyView(Context context, int size) {
        super(context);
        View button = new Button(context);
        button.setEnabled(false);
        button.setBackgroundDrawable(bx().getDrawable(size));
        Point T = m599T(size);
        addView(button, new LayoutParams(T.x, T.y, 17));
    }

    private Point m599T(int i) {
        int i2 = 24;
        int i3 = 20;
        Point point = new Point();
        switch (i) {
            case MutableDateTime.ROUND_NONE /*0*/:
                i3 = 14;
                break;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                i2 = 32;
                break;
            case Value.STRING_FIELD_NUMBER /*2*/:
                i2 = 50;
                break;
            default:
                i2 = 38;
                i3 = 24;
                break;
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float applyDimension = TypedValue.applyDimension(1, (float) i2, displayMetrics);
        float applyDimension2 = TypedValue.applyDimension(1, (float) i3, displayMetrics);
        point.x = (int) (((double) applyDimension) + 0.5d);
        point.y = (int) (((double) applyDimension2) + 0.5d);
        return point;
    }

    private C0203d bx() {
        C0203d c0612b = new C0612b(null);
        if (!c0612b.isValid()) {
            c0612b = new C0613c(null);
        }
        return !c0612b.isValid() ? new C0611a(null) : c0612b;
    }
}

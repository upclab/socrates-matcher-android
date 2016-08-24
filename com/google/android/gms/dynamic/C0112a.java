package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.Iterator;
import java.util.LinkedList;

/* renamed from: com.google.android.gms.dynamic.a */
public abstract class C0112a<T extends LifecycleDelegate> {
    private T cP;
    private Bundle cQ;
    private LinkedList<C0111a> cR;
    private final C0114d<T> cS;

    /* renamed from: com.google.android.gms.dynamic.a.5 */
    class C01105 implements OnClickListener {
        final /* synthetic */ C0112a cT;
        final /* synthetic */ Context da;
        final /* synthetic */ int db;

        C01105(C0112a c0112a, Context context, int i) {
            this.cT = c0112a;
            this.da = context;
            this.db = i;
        }

        public void onClick(View v) {
            this.da.startActivity(GooglePlayServicesUtil.m15a(this.da, this.db, -1));
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.a */
    private interface C0111a {
        void m128b(LifecycleDelegate lifecycleDelegate);

        int getState();
    }

    /* renamed from: com.google.android.gms.dynamic.a.1 */
    class C04711 implements C0114d<T> {
        final /* synthetic */ C0112a cT;

        C04711(C0112a c0112a) {
            this.cT = c0112a;
        }

        public void m652a(T t) {
            this.cT.cP = t;
            Iterator it = this.cT.cR.iterator();
            while (it.hasNext()) {
                ((C0111a) it.next()).m128b(this.cT.cP);
            }
            this.cT.cR.clear();
            this.cT.cQ = null;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.2 */
    class C04722 implements C0111a {
        final /* synthetic */ C0112a cT;
        final /* synthetic */ Activity cU;
        final /* synthetic */ Bundle cV;
        final /* synthetic */ Bundle cW;

        C04722(C0112a c0112a, Activity activity, Bundle bundle, Bundle bundle2) {
            this.cT = c0112a;
            this.cU = activity;
            this.cV = bundle;
            this.cW = bundle2;
        }

        public void m653b(LifecycleDelegate lifecycleDelegate) {
            this.cT.cP.onInflate(this.cU, this.cV, this.cW);
        }

        public int getState() {
            return 0;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.3 */
    class C04733 implements C0111a {
        final /* synthetic */ C0112a cT;
        final /* synthetic */ Bundle cW;

        C04733(C0112a c0112a, Bundle bundle) {
            this.cT = c0112a;
            this.cW = bundle;
        }

        public void m654b(LifecycleDelegate lifecycleDelegate) {
            this.cT.cP.onCreate(this.cW);
        }

        public int getState() {
            return 1;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.4 */
    class C04744 implements C0111a {
        final /* synthetic */ C0112a cT;
        final /* synthetic */ Bundle cW;
        final /* synthetic */ FrameLayout cX;
        final /* synthetic */ LayoutInflater cY;
        final /* synthetic */ ViewGroup cZ;

        C04744(C0112a c0112a, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            this.cT = c0112a;
            this.cX = frameLayout;
            this.cY = layoutInflater;
            this.cZ = viewGroup;
            this.cW = bundle;
        }

        public void m655b(LifecycleDelegate lifecycleDelegate) {
            this.cX.removeAllViews();
            this.cX.addView(this.cT.cP.onCreateView(this.cY, this.cZ, this.cW));
        }

        public int getState() {
            return 2;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.a.6 */
    class C04756 implements C0111a {
        final /* synthetic */ C0112a cT;

        C04756(C0112a c0112a) {
            this.cT = c0112a;
        }

        public void m656b(LifecycleDelegate lifecycleDelegate) {
            this.cT.cP.onResume();
        }

        public int getState() {
            return 3;
        }
    }

    public C0112a() {
        this.cS = new C04711(this);
    }

    private void m132a(Bundle bundle, C0111a c0111a) {
        if (this.cP != null) {
            c0111a.m128b(this.cP);
            return;
        }
        if (this.cR == null) {
            this.cR = new LinkedList();
        }
        this.cR.add(c0111a);
        if (bundle != null) {
            if (this.cQ == null) {
                this.cQ = (Bundle) bundle.clone();
            } else {
                this.cQ.putAll(bundle);
            }
        }
        m136a(this.cS);
    }

    private void m134y(int i) {
        while (!this.cR.isEmpty() && ((C0111a) this.cR.getLast()).getState() >= i) {
            this.cR.removeLast();
        }
    }

    public void m135a(FrameLayout frameLayout) {
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        CharSequence b = GooglePlayServicesUtil.m20b(context, isGooglePlayServicesAvailable, -1);
        CharSequence a = GooglePlayServicesUtil.m16a(context, isGooglePlayServicesAvailable);
        View linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        View textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(b);
        linearLayout.addView(textView);
        if (a != null) {
            View button = new Button(context);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setText(a);
            linearLayout.addView(button);
            button.setOnClickListener(new C01105(this, context, isGooglePlayServicesAvailable));
        }
    }

    protected abstract void m136a(C0114d<T> c0114d);

    public T at() {
        return this.cP;
    }

    public void onCreate(Bundle savedInstanceState) {
        m132a(savedInstanceState, new C04733(this, savedInstanceState));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        m132a(savedInstanceState, new C04744(this, frameLayout, inflater, container, savedInstanceState));
        if (this.cP == null) {
            m135a(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.cP != null) {
            this.cP.onDestroy();
        } else {
            m134y(1);
        }
    }

    public void onDestroyView() {
        if (this.cP != null) {
            this.cP.onDestroyView();
        } else {
            m134y(2);
        }
    }

    public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
        m132a(savedInstanceState, new C04722(this, activity, attrs, savedInstanceState));
    }

    public void onLowMemory() {
        if (this.cP != null) {
            this.cP.onLowMemory();
        }
    }

    public void onPause() {
        if (this.cP != null) {
            this.cP.onPause();
        } else {
            m134y(3);
        }
    }

    public void onResume() {
        m132a(null, new C04756(this));
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.cP != null) {
            this.cP.onSaveInstanceState(outState);
        } else if (this.cQ != null) {
            outState.putAll(this.cQ);
        }
    }
}

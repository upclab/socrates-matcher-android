package com.google.android.gms.maps;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.dynamic.C0112a;
import com.google.android.gms.dynamic.C0114d;
import com.google.android.gms.dynamic.C0883c;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.maps.internal.C0180o;
import com.google.android.gms.maps.internal.C0181p;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class MapFragment extends Fragment {
    private final C0549b gy;
    private GoogleMap gz;

    /* renamed from: com.google.android.gms.maps.MapFragment.a */
    static class C0548a implements LifecycleDelegate {
        private final Fragment gA;
        private final IMapFragmentDelegate gB;

        public C0548a(Fragment fragment, IMapFragmentDelegate iMapFragmentDelegate) {
            this.gB = (IMapFragmentDelegate) C0159s.m517d(iMapFragmentDelegate);
            this.gA = (Fragment) C0159s.m517d(fragment);
        }

        public IMapFragmentDelegate bh() {
            return this.gB;
        }

        public void onCreate(Bundle savedInstanceState) {
            if (savedInstanceState == null) {
                try {
                    savedInstanceState = new Bundle();
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                }
            }
            Bundle arguments = this.gA.getArguments();
            if (arguments != null && arguments.containsKey("MapOptions")) {
                C0180o.m550a(savedInstanceState, "MapOptions", arguments.getParcelable("MapOptions"));
            }
            this.gB.onCreate(savedInstanceState);
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            try {
                return (View) C0883c.m1157a(this.gB.onCreateView(C0883c.m1158f(inflater), C0883c.m1158f(container), savedInstanceState));
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroy() {
            try {
                this.gB.onDestroy();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroyView() {
            try {
                this.gB.onDestroyView();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
            try {
                this.gB.onInflate(C0883c.m1158f(activity), (GoogleMapOptions) attrs.getParcelable("MapOptions"), savedInstanceState);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onLowMemory() {
            try {
                this.gB.onLowMemory();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onPause() {
            try {
                this.gB.onPause();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onResume() {
            try {
                this.gB.onResume();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onSaveInstanceState(Bundle outState) {
            try {
                this.gB.onSaveInstanceState(outState);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
    }

    /* renamed from: com.google.android.gms.maps.MapFragment.b */
    static class C0549b extends C0112a<C0548a> {
        private Activity bm;
        private final Fragment gA;
        protected C0114d<C0548a> gC;

        C0549b(Fragment fragment) {
            this.gA = fragment;
        }

        private void setActivity(Activity activity) {
            this.bm = activity;
            bi();
        }

        protected void m1052a(C0114d<C0548a> c0114d) {
            this.gC = c0114d;
            bi();
        }

        public void bi() {
            if (this.bm != null && this.gC != null && at() == null) {
                try {
                    MapsInitializer.initialize(this.bm);
                    this.gC.m137a(new C0548a(this.gA, C0181p.m553i(this.bm).m540d(C0883c.m1158f(this.bm))));
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }
    }

    public MapFragment() {
        this.gy = new C0549b(this);
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public static MapFragment newInstance(GoogleMapOptions options) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("MapOptions", options);
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    protected IMapFragmentDelegate bh() {
        this.gy.bi();
        return this.gy.at() == null ? null : ((C0548a) this.gy.at()).bh();
    }

    public final GoogleMap getMap() {
        IMapFragmentDelegate bh = bh();
        if (bh == null) {
            return null;
        }
        try {
            IGoogleMapDelegate map = bh.getMap();
            if (map == null) {
                return null;
            }
            if (this.gz == null || this.gz.aY().asBinder() != map.asBinder()) {
                this.gz = new GoogleMap(map);
            }
            return this.gz;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.setClassLoader(MapFragment.class.getClassLoader());
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.gy.setActivity(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gy.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return this.gy.onCreateView(inflater, container, savedInstanceState);
    }

    public void onDestroy() {
        this.gy.onDestroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        this.gy.onDestroyView();
        super.onDestroyView();
    }

    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        this.gy.setActivity(activity);
        Parcelable createFromAttributes = GoogleMapOptions.createFromAttributes(activity, attrs);
        Bundle bundle = new Bundle();
        bundle.putParcelable("MapOptions", createFromAttributes);
        this.gy.onInflate(activity, bundle, savedInstanceState);
    }

    public void onLowMemory() {
        this.gy.onLowMemory();
        super.onLowMemory();
    }

    public void onPause() {
        this.gy.onPause();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.gy.onResume();
    }

    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.setClassLoader(MapFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(outState);
        this.gy.onSaveInstanceState(outState);
    }

    public void setArguments(Bundle args) {
        super.setArguments(args);
    }
}

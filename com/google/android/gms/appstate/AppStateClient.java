package com.google.android.gms.appstate;

import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.internal.C0906c;

public final class AppStateClient implements GooglePlayServicesClient {
    public static final int STATUS_CLIENT_RECONNECT_REQUIRED = 2;
    public static final int STATUS_DEVELOPER_ERROR = 7;
    public static final int STATUS_INTERNAL_ERROR = 1;
    public static final int STATUS_NETWORK_ERROR_NO_DATA = 4;
    public static final int STATUS_NETWORK_ERROR_OPERATION_DEFERRED = 5;
    public static final int STATUS_NETWORK_ERROR_OPERATION_FAILED = 6;
    public static final int STATUS_NETWORK_ERROR_STALE_DATA = 3;
    public static final int STATUS_OK = 0;
    public static final int STATUS_STATE_KEY_LIMIT_EXCEEDED = 2003;
    public static final int STATUS_STATE_KEY_NOT_FOUND = 2002;
    public static final int STATUS_WRITE_OUT_OF_DATE_VERSION = 2000;
    public static final int STATUS_WRITE_SIZE_EXCEEDED = 2001;
    private final C0906c f72b;

    public static final class Builder {
        private static final String[] f1c;
        private ConnectionCallbacks f2d;
        private OnConnectionFailedListener f3e;
        private String[] f4f;
        private String f5g;
        private Context mContext;

        static {
            String[] strArr = new String[AppStateClient.STATUS_INTERNAL_ERROR];
            strArr[AppStateClient.STATUS_OK] = Scopes.APP_STATE;
            f1c = strArr;
        }

        public Builder(Context context, ConnectionCallbacks connectedListener, OnConnectionFailedListener connectionFailedListener) {
            this.mContext = context;
            this.f2d = connectedListener;
            this.f3e = connectionFailedListener;
            this.f4f = f1c;
            this.f5g = "<<default account>>";
        }

        public AppStateClient create() {
            return new AppStateClient(this.f2d, this.f3e, this.f5g, this.f4f, null);
        }

        public Builder setAccountName(String accountName) {
            this.f5g = (String) C0159s.m517d(accountName);
            return this;
        }

        public Builder setScopes(String... scopes) {
            this.f4f = scopes;
            return this;
        }
    }

    private AppStateClient(Context context, ConnectionCallbacks connectedListener, OnConnectionFailedListener connectionFailedListener, String accountName, String[] scopes) {
        this.f72b = new C0906c(context, connectedListener, connectionFailedListener, accountName, scopes);
    }

    public void connect() {
        this.f72b.connect();
    }

    public void deleteState(OnStateDeletedListener listener, int stateKey) {
        this.f72b.deleteState(listener, stateKey);
    }

    public void disconnect() {
        this.f72b.disconnect();
    }

    public int getMaxNumKeys() {
        return this.f72b.getMaxNumKeys();
    }

    public int getMaxStateSize() {
        return this.f72b.getMaxStateSize();
    }

    public boolean isConnected() {
        return this.f72b.isConnected();
    }

    public boolean isConnecting() {
        return this.f72b.isConnecting();
    }

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks listener) {
        return this.f72b.isConnectionCallbacksRegistered(listener);
    }

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener listener) {
        return this.f72b.isConnectionFailedListenerRegistered(listener);
    }

    public void listStates(OnStateListLoadedListener listener) {
        this.f72b.listStates(listener);
    }

    public void loadState(OnStateLoadedListener listener, int stateKey) {
        this.f72b.loadState(listener, stateKey);
    }

    public void reconnect() {
        this.f72b.disconnect();
        this.f72b.connect();
    }

    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        this.f72b.registerConnectionCallbacks(listener);
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        this.f72b.registerConnectionFailedListener(listener);
    }

    public void resolveState(OnStateLoadedListener listener, int stateKey, String resolvedVersion, byte[] resolvedData) {
        this.f72b.resolveState(listener, stateKey, resolvedVersion, resolvedData);
    }

    public void signOut() {
        this.f72b.signOut(null);
    }

    public void signOut(OnSignOutCompleteListener listener) {
        C0159s.m514b((Object) listener, (Object) "Must provide a valid listener");
        this.f72b.signOut(listener);
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks listener) {
        this.f72b.unregisterConnectionCallbacks(listener);
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener listener) {
        this.f72b.unregisterConnectionFailedListener(listener);
    }

    public void updateState(int stateKey, byte[] data) {
        this.f72b.m1304a(null, stateKey, data);
    }

    public void updateStateImmediate(OnStateLoadedListener listener, int stateKey, byte[] data) {
        C0159s.m514b((Object) listener, (Object) "Must provide a valid listener");
        this.f72b.m1304a(listener, stateKey, data);
    }
}

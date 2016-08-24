package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.C0143h;
import com.google.android.gms.internal.C0158r;
import com.google.android.gms.internal.C0532j;
import com.google.android.gms.internal.ao;
import com.google.android.gms.internal.av;

public final class PlayerEntity extends av implements Player {
    public static final Creator<PlayerEntity> CREATOR;
    private final int ab;
    private final String cl;
    private final Uri dk;
    private final Uri dl;
    private final String dx;
    private final long dy;

    /* renamed from: com.google.android.gms.games.PlayerEntity.a */
    static final class C0479a extends C0119c {
        C0479a() {
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return m659o(x0);
        }

        public PlayerEntity m659o(Parcel parcel) {
            Uri uri = null;
            if (av.m1240c(C0532j.m975v()) || C0532j.m973h(PlayerEntity.class.getCanonicalName())) {
                return super.m145o(parcel);
            }
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            String readString4 = parcel.readString();
            Uri parse = readString3 == null ? null : Uri.parse(readString3);
            if (readString4 != null) {
                uri = Uri.parse(readString4);
            }
            return new PlayerEntity(1, readString, readString2, parse, uri, parcel.readLong());
        }
    }

    static {
        CREATOR = new C0479a();
    }

    PlayerEntity(int versionCode, String playerId, String displayName, Uri iconImageUri, Uri hiResImageUri, long retrievedTimestamp) {
        this.ab = versionCode;
        this.dx = playerId;
        this.cl = displayName;
        this.dk = iconImageUri;
        this.dl = hiResImageUri;
        this.dy = retrievedTimestamp;
    }

    public PlayerEntity(Player player) {
        boolean z = true;
        this.ab = 1;
        this.dx = player.getPlayerId();
        this.cl = player.getDisplayName();
        this.dk = player.getIconImageUri();
        this.dl = player.getHiResImageUri();
        this.dy = player.getRetrievedTimestamp();
        C0143h.m461b(this.dx);
        C0143h.m461b(this.cl);
        if (this.dy <= 0) {
            z = false;
        }
        C0143h.m459a(z);
    }

    static int m1388a(Player player) {
        return C0158r.hashCode(player.getPlayerId(), player.getDisplayName(), player.getIconImageUri(), player.getHiResImageUri(), Long.valueOf(player.getRetrievedTimestamp()));
    }

    static boolean m1389a(Player player, Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        if (player == obj) {
            return true;
        }
        Player player2 = (Player) obj;
        return C0158r.m509a(player2.getPlayerId(), player.getPlayerId()) && C0158r.m509a(player2.getDisplayName(), player.getDisplayName()) && C0158r.m509a(player2.getIconImageUri(), player.getIconImageUri()) && C0158r.m509a(player2.getHiResImageUri(), player.getHiResImageUri()) && C0158r.m509a(Long.valueOf(player2.getRetrievedTimestamp()), Long.valueOf(player.getRetrievedTimestamp()));
    }

    static String m1390b(Player player) {
        return C0158r.m510c(player).m508a("PlayerId", player.getPlayerId()).m508a("DisplayName", player.getDisplayName()).m508a("IconImageUri", player.getIconImageUri()).m508a("HiResImageUri", player.getHiResImageUri()).m508a("RetrievedTimestamp", Long.valueOf(player.getRetrievedTimestamp())).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return m1389a(this, obj);
    }

    public Player freeze() {
        return this;
    }

    public String getDisplayName() {
        return this.cl;
    }

    public void getDisplayName(CharArrayBuffer dataOut) {
        ao.m211b(this.cl, dataOut);
    }

    public Uri getHiResImageUri() {
        return this.dl;
    }

    public Uri getIconImageUri() {
        return this.dk;
    }

    public String getPlayerId() {
        return this.dx;
    }

    public long getRetrievedTimestamp() {
        return this.dy;
    }

    public boolean hasHiResImage() {
        return getHiResImageUri() != null;
    }

    public boolean hasIconImage() {
        return getIconImageUri() != null;
    }

    public int hashCode() {
        return m1388a(this);
    }

    public int m1393i() {
        return this.ab;
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return m1390b((Player) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        String str = null;
        if (m976w()) {
            dest.writeString(this.dx);
            dest.writeString(this.cl);
            dest.writeString(this.dk == null ? null : this.dk.toString());
            if (this.dl != null) {
                str = this.dl.toString();
            }
            dest.writeString(str);
            dest.writeLong(this.dy);
            return;
        }
        C0119c.m143a(this, dest, flags);
    }
}

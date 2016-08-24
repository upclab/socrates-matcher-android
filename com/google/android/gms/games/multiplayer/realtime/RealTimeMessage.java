package com.google.android.gms.games.multiplayer.realtime;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.C0159s;

public final class RealTimeMessage implements Parcelable {
    public static final Creator<RealTimeMessage> CREATOR;
    public static final int RELIABLE = 1;
    public static final int UNRELIABLE = 0;
    private final String eT;
    private final byte[] eU;
    private final int eV;

    /* renamed from: com.google.android.gms.games.multiplayer.realtime.RealTimeMessage.1 */
    static class C01231 implements Creator<RealTimeMessage> {
        C01231() {
        }

        public RealTimeMessage[] m154J(int i) {
            return new RealTimeMessage[i];
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return m155r(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return m154J(x0);
        }

        public RealTimeMessage m155r(Parcel parcel) {
            return new RealTimeMessage(null);
        }
    }

    static {
        CREATOR = new C01231();
    }

    private RealTimeMessage(Parcel parcel) {
        this(parcel.readString(), parcel.createByteArray(), parcel.readInt());
    }

    public RealTimeMessage(String senderParticipantId, byte[] messageData, int isReliable) {
        this.eT = (String) C0159s.m517d(senderParticipantId);
        this.eU = (byte[]) ((byte[]) C0159s.m517d(messageData)).clone();
        this.eV = isReliable;
    }

    public int describeContents() {
        return 0;
    }

    public byte[] getMessageData() {
        return this.eU;
    }

    public String getSenderParticipantId() {
        return this.eT;
    }

    public boolean isReliable() {
        return this.eV == RELIABLE;
    }

    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(this.eT);
        parcel.writeByteArray(this.eU);
        parcel.writeInt(this.eV);
    }
}

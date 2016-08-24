package com.google.android.gms.plus.model.people;

import com.google.android.gms.common.data.C0466c;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.internal.cc;
import com.google.android.gms.internal.cn;

public final class PersonBuffer extends DataBuffer<Person> {
    private final C0466c<cc> kp;

    public PersonBuffer(C0468d dataHolder) {
        super(dataHolder);
        if (dataHolder.m644l() == null || !dataHolder.m644l().getBoolean("com.google.android.gms.plus.IsSafeParcelable", false)) {
            this.kp = null;
        } else {
            this.kp = new C0466c(dataHolder, cc.CREATOR);
        }
    }

    public Person get(int position) {
        return this.kp != null ? (Person) this.kp.m625d(position) : new cn(this.S, position);
    }
}

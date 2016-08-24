package com.google.android.gms.dynamic;

import android.os.IBinder;
import com.google.android.gms.dynamic.C0113b.C0477a;
import java.lang.reflect.Field;

/* renamed from: com.google.android.gms.dynamic.c */
public final class C0883c<T> extends C0477a {
    private final T dc;

    private C0883c(T t) {
        this.dc = t;
    }

    public static <T> T m1157a(C0113b c0113b) {
        if (c0113b instanceof C0883c) {
            return ((C0883c) c0113b).dc;
        }
        IBinder asBinder = c0113b.asBinder();
        Field[] declaredFields = asBinder.getClass().getDeclaredFields();
        if (declaredFields.length == 1) {
            Field field = declaredFields[0];
            if (field.isAccessible()) {
                throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
            }
            field.setAccessible(true);
            try {
                return field.get(asBinder);
            } catch (Throwable e) {
                throw new IllegalArgumentException("Binder object is null.", e);
            } catch (Throwable e2) {
                throw new IllegalArgumentException("remoteBinder is the wrong class.", e2);
            } catch (Throwable e22) {
                throw new IllegalArgumentException("Could not access the field in remoteBinder.", e22);
            }
        }
        throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
    }

    public static <T> C0113b m1158f(T t) {
        return new C0883c(t);
    }
}

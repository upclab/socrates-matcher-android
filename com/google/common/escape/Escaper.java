package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;

@GwtCompatible
@Beta
public abstract class Escaper {
    private final Function<String, String> asFunction;

    /* renamed from: com.google.common.escape.Escaper.1 */
    class C07641 implements Function<String, String> {
        C07641() {
        }

        public String apply(String from) {
            return Escaper.this.escape(from);
        }
    }

    public abstract String escape(String str);

    protected Escaper() {
        this.asFunction = new C07641();
    }

    public final Function<String, String> asFunction() {
        return this.asFunction;
    }
}

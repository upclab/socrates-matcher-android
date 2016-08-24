package com.google.tagmanager;

import com.google.analytics.containertag.common.FunctionType;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import java.util.Map;

class RandomMacro extends FunctionCallImplementation {
    private static final String ID;

    static {
        ID = FunctionType.RANDOM.toString();
    }

    public static String getFunctionId() {
        return ID;
    }

    public RandomMacro() {
        super(ID, new String[0]);
    }

    public boolean isCacheable() {
        return false;
    }

    public Value evaluate(Map<String, Value> map) {
        return Types.objectToValue(Long.valueOf(Math.round(Math.random() * 2.147483647E9d)));
    }
}

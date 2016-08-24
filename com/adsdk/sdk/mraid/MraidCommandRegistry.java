package com.adsdk.sdk.mraid;

import java.util.HashMap;
import java.util.Map;

class MraidCommandRegistry {
    private static Map<String, MraidCommandFactory> commandMap;

    private interface MraidCommandFactory {
        MraidCommand create(Map<String, String> map, MraidView mraidView);
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidCommandRegistry.1 */
    class C04421 implements MraidCommandFactory {
        C04421() {
        }

        public MraidCommand create(Map<String, String> params, MraidView view) {
            return new MraidCommandClose(params, view);
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidCommandRegistry.2 */
    class C04432 implements MraidCommandFactory {
        C04432() {
        }

        public MraidCommand create(Map<String, String> params, MraidView view) {
            return new MraidCommandExpand(params, view);
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidCommandRegistry.3 */
    class C04443 implements MraidCommandFactory {
        C04443() {
        }

        public MraidCommand create(Map<String, String> params, MraidView view) {
            return new MraidCommandUseCustomClose(params, view);
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidCommandRegistry.4 */
    class C04454 implements MraidCommandFactory {
        C04454() {
        }

        public MraidCommand create(Map<String, String> params, MraidView view) {
            return new MraidCommandOpen(params, view);
        }
    }

    MraidCommandRegistry() {
    }

    static {
        commandMap = new HashMap();
        commandMap.put("close", new C04421());
        commandMap.put("expand", new C04432());
        commandMap.put("usecustomclose", new C04443());
        commandMap.put("open", new C04454());
    }

    static MraidCommand createCommand(String string, Map<String, String> params, MraidView view) {
        MraidCommandFactory factory = (MraidCommandFactory) commandMap.get(string);
        return factory != null ? factory.create(params, view) : null;
    }
}

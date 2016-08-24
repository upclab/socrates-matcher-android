package com.adsdk.sdk.mraid;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.tagmanager.protobuf.CodedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.MutableDateTime;

public class MraidView extends WebView {
    public static final int AD_CONTAINER_LAYOUT_ID = 102;
    private static final String LOGTAG = "MraidView";
    public static final int MODAL_CONTAINER_LAYOUT_ID = 101;
    public static final int PLACEHOLDER_VIEW_ID = 100;
    private MraidBrowserController mBrowserController;
    private MraidDisplayController mDisplayController;
    private boolean mHasFiredReadyEvent;
    private MraidListenerInfo mListenerInfo;
    private final PlacementType mPlacementType;
    private WebChromeClient mWebChromeClient;
    private WebViewClient mWebViewClient;

    /* renamed from: com.adsdk.sdk.mraid.MraidView.1 */
    class C00511 implements OnTouchListener {
        C00511() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MutableDateTime.ROUND_NONE /*0*/:
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    if (!v.hasFocus()) {
                        v.requestFocus();
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    enum ExpansionStyle {
        ENABLED,
        DISABLED
    }

    static class MraidListenerInfo {
        private OnCloseButtonStateChangeListener mOnCloseButtonListener;
        private OnCloseListener mOnCloseListener;
        private OnExpandListener mOnExpandListener;
        private OnFailureListener mOnFailureListener;
        private OnOpenListener mOnOpenListener;
        private OnReadyListener mOnReadyListener;

        MraidListenerInfo() {
        }
    }

    private class MraidWebChromeClient extends WebChromeClient {
        private MraidWebChromeClient() {
        }

        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d(MraidView.LOGTAG, message);
            return false;
        }
    }

    private class MraidWebViewClient extends WebViewClient {
        private MraidWebViewClient() {
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.d(MraidView.LOGTAG, "Error: " + description);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String scheme = Uri.parse(url).getScheme();
            if (scheme.equals("mopub")) {
                return true;
            }
            if (scheme.equals("mraid")) {
                MraidView.this.tryCommand(URI.create(url));
                return true;
            }
            Intent i = new Intent();
            i.setAction("android.intent.action.VIEW");
            i.setData(Uri.parse(url));
            i.addFlags(268435456);
            try {
                MraidView.this.getContext().startActivity(i);
                return true;
            } catch (ActivityNotFoundException e) {
                return false;
            }
        }

        public void onPageFinished(WebView view, String url) {
            if (!MraidView.this.mHasFiredReadyEvent) {
                MraidView.this.mDisplayController.initializeJavaScriptState();
                MraidView.this.fireChangeEventForProperty(MraidPlacementTypeProperty.createWithType(MraidView.this.mPlacementType));
                MraidView.this.fireReadyEvent();
                if (MraidView.this.getOnReadyListener() != null) {
                    MraidView.this.getOnReadyListener().onReady(MraidView.this);
                }
                MraidView.this.mHasFiredReadyEvent = true;
            }
        }

        public void onLoadResource(WebView view, String url) {
            Log.d(MraidView.LOGTAG, "Loaded resource: " + url);
        }
    }

    enum NativeCloseButtonStyle {
        ALWAYS_VISIBLE,
        ALWAYS_HIDDEN,
        AD_CONTROLLED
    }

    public interface OnCloseButtonStateChangeListener {
        void onCloseButtonStateChange(MraidView mraidView, boolean z);
    }

    public interface OnCloseListener {
        void onClose(MraidView mraidView, ViewState viewState);
    }

    public interface OnExpandListener {
        void onExpand(MraidView mraidView);
    }

    public interface OnFailureListener {
        void onFailure(MraidView mraidView);
    }

    public interface OnOpenListener {
        void onOpen(MraidView mraidView);
    }

    public interface OnReadyListener {
        void onReady(MraidView mraidView);
    }

    enum PlacementType {
        INLINE,
        INTERSTITIAL
    }

    public enum ViewState {
        LOADING,
        DEFAULT,
        EXPANDED,
        HIDDEN
    }

    public MraidView(Context context) {
        this(context, ExpansionStyle.ENABLED, NativeCloseButtonStyle.AD_CONTROLLED, PlacementType.INLINE);
    }

    MraidView(Context context, ExpansionStyle expStyle, NativeCloseButtonStyle buttonStyle, PlacementType placementType) {
        super(context);
        this.mPlacementType = placementType;
        initialize(expStyle, buttonStyle);
    }

    private void initialize(ExpansionStyle expStyle, NativeCloseButtonStyle buttonStyle) {
        setScrollContainer(false);
        setBackgroundColor(0);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setOnTouchListener(new C00511());
        getSettings().setJavaScriptEnabled(true);
        this.mBrowserController = new MraidBrowserController(this);
        this.mDisplayController = new MraidDisplayController(this, expStyle, buttonStyle);
        this.mWebViewClient = new MraidWebViewClient();
        setWebViewClient(this.mWebViewClient);
        this.mWebChromeClient = new MraidWebChromeClient();
        setWebChromeClient(this.mWebChromeClient);
        this.mListenerInfo = new MraidListenerInfo();
    }

    public void destroy() {
        this.mDisplayController.destroy();
        super.destroy();
    }

    public void loadHtmlData(String data) {
        if (data.indexOf("<html>") == -1) {
            data = "<html><head></head><body style='margin:0;padding:0;'>" + data + "</body></html>";
        }
        String replace = data.replace("<head>", "<head><script>(function() {\n  var isIOS = (/iphone|ipad|ipod/i).test(window.navigator.userAgent.toLowerCase()); \n  if (isIOS) {\n    console = {};\n    console.log = function(log) {\n      var iframe = document.createElement('iframe');\n      iframe.setAttribute('src', 'ios-log: ' + log);\n      document.documentElement.appendChild(iframe);\n      iframe.parentNode.removeChild(iframe);\n      iframe = null;\n    };\n    console.debug = console.info = console.warn = console.error = console.log;\n  }\n}());\n\n(function() {\n  // Establish the root mraidbridge object.\n  var mraidbridge = window.mraidbridge = {};\n  \n  // Listeners for bridge events.\n  var listeners = {};\n  \n  // Queue to track pending calls to the native SDK.\n  var nativeCallQueue = [];\n  \n  // Whether a native call is currently in progress.\n  var nativeCallInFlight = false;\n\n  //////////////////////////////////////////////////////////////////////////////////////////////////\n  \n  mraidbridge.fireReadyEvent = function() {\n    mraidbridge.fireEvent('ready');\n  };\n  \n  mraidbridge.fireChangeEvent = function(properties) {\n    mraidbridge.fireEvent('change', properties);\n  };\n  \n  mraidbridge.fireErrorEvent = function(message, action) {\n    mraidbridge.fireEvent('error', message, action);\n  };\n\n  mraidbridge.fireEvent = function(type) {\n    var ls = listeners[type];\n    if (ls) {\n      var args = Array.prototype.slice.call(arguments);\n      args.shift();\n      var l = ls.length;\n      for (var i = 0; i < l; i++) {\n        ls[i].apply(null, args);\n      }\n    }\n  };\n  \n  mraidbridge.nativeCallComplete = function(command) {\n    if (nativeCallQueue.length === 0) {\n      nativeCallInFlight = false;\n      return;\n    }\n    \n    var nextCall = nativeCallQueue.pop();\n    window.location = nextCall;\n  };\n  \n  mraidbridge.executeNativeCall = function(command) {\n    var call = 'mraid://' + command;\n    \n    var key, value;\n    var isFirstArgument = true;\n    \n    for (var i = 1; i < arguments.length; i += 2) {\n      key = arguments[i];\n      value = arguments[i + 1];\n      \n      if (value === null) continue;\n      \n      if (isFirstArgument) {\n        call += '?';\n        isFirstArgument = false;\n      } else {\n        call += '&';\n      }\n      \n      call += key + '=' + escape(value);\n    }\n\n    if (nativeCallInFlight) {\n      nativeCallQueue.push(call);\n    } else {\n      nativeCallInFlight = true;\n      window.location = call;\n    }\n  };\n  \n  //////////////////////////////////////////////////////////////////////////////////////////////////\n  \n  mraidbridge.addEventListener = function(event, listener) {\n    var eventListeners;\n    listeners[event] = listeners[event] || [];\n    eventListeners = listeners[event];\n    \n    for (var l in eventListeners) {\n      // Listener already registered, so no need to add it.\n      if (listener === l) return;\n    }\n    \n    eventListeners.push(listener);\n  };\n\n  mraidbridge.removeEventListener = function(event, listener) {\n    if (listeners.hasOwnProperty(event)) {\n      var eventListeners = listeners[event];\n      if (eventListeners) {\n        var idx = eventListeners.indexOf(listener);\n        if (idx !== -1) {\n          eventListeners.splice(idx, 1);\n        }\n      }\n    }\n  };\n}());\n\n(function() {\n  var mraid = window.mraid = {};\n  var bridge = window.mraidbridge;\n  \n  // Constants. ////////////////////////////////////////////////////////////////////////////////////\n  \n  var VERSION = mraid.VERSION = '1.0';\n  \n  var STATES = mraid.STATES = {\n    LOADING: 'loading',     // Initial state.\n    DEFAULT: 'default',\n    EXPANDED: 'expanded',\n    HIDDEN: 'hidden'\n  };\n  \n  var EVENTS = mraid.EVENTS = {\n    ERROR: 'error',\n    INFO: 'info',\n    READY: 'ready',\n    STATECHANGE: 'stateChange',\n    VIEWABLECHANGE: 'viewableChange'\n  };\n  \n  var PLACEMENT_TYPES = mraid.PLACEMENT_TYPES = {\n    UNKNOWN: 'inline',\n    INLINE: 'inline',\n    INTERSTITIAL: 'interstitial'\n  };\n\n  // External MRAID state: may be directly or indirectly modified by the ad JS. ////////////////////\n\n  // Properties which define the behavior of an expandable ad.\n  var expandProperties = {\n    width: -1,\n    height: -1,\n    useCustomClose: false,\n    isModal: true,\n    lockOrientation: false\n  };\n\n  var hasSetCustomSize = false;\n\n  var hasSetCustomClose = false;\n \n  var listeners = {};\n\n  // Internal MRAID state. Modified by the native SDK. /////////////////////////////////////////////\n  \n  var state = STATES.LOADING;\n  \n  var isViewable = false;\n  \n  var screenSize = { width: -1, height: -1 };\n\n  var placementType = PLACEMENT_TYPES.UNKNOWN;\n  \n  //////////////////////////////////////////////////////////////////////////////////////////////////\n  \n  var EventListeners = function(event) {\n    this.event = event;\n    this.count = 0;\n    var listeners = {};\n    \n    this.add = function(func) {\n      var id = String(func);\n      if (!listeners[id]) {\n        listeners[id] = func;\n        this.count++;\n      }\n    };\n    \n    this.remove = function(func) {\n      var id = String(func);\n      if (listeners[id]) {\n        listeners[id] = null;\n        delete listeners[id];\n        this.count--;\n        return true;\n      } else {\n        return false;\n      }\n    };\n    \n    this.removeAll = function() {\n      for (var id in listeners) {\n        if (listeners.hasOwnProperty(id)) this.remove(listeners[id]);\n      }\n    };\n    \n    this.broadcast = function(args) {\n      for (var id in listeners) {\n        if (listeners.hasOwnProperty(id)) listeners[id].apply({}, args);\n      }\n    };\n    \n    this.toString = function() {\n      var out = [event, ':'];\n      for (var id in listeners) {\n        if (listeners.hasOwnProperty(id)) out.push('|', id, '|');\n      }\n      return out.join('');\n    };\n  };\n  \n  var broadcastEvent = function() {\n    var args = new Array(arguments.length);\n    var l = arguments.length;\n    for (var i = 0; i < l; i++) args[i] = arguments[i];\n    var event = args.shift();\n    if (listeners[event]) listeners[event].broadcast(args);\n  };\n  \n  var contains = function(value, array) {\n    for (var i in array) {\n      if (array[i] === value) return true;\n    }\n    return false;\n  };\n  \n  var clone = function(obj) {\n    if (obj === null) return null;\n    var f = function() {};\n    f.prototype = obj;\n    return new f();\n  };\n  \n  var stringify = function(obj) {\n    if (typeof obj === 'object') {\n      var out = [];\n      if (obj.push) {\n        // Array.\n        for (var p in obj) out.push(obj[p]);\n        return '[' + out.join(',') + ']';\n      } else {\n        // Other object.\n        for (var p in obj) out.push(\"'\" + p + \"': \" + obj[p]);\n        return '{' + out.join(',') + '}';\n      }\n    } else return String(obj);\n  };\n  \n  var trim = function(str) {\n    return str.replace(/^\\s+|\\s+$/g, '');\n  };\n  \n  // Functions that will be invoked by the native SDK whenever a \"change\" event occurs.\n  var changeHandlers = {\n    state: function(val) {\n      if (state === STATES.LOADING) {\n        broadcastEvent(EVENTS.INFO, 'Native SDK initialized.');\n      }\n      state = val;\n      broadcastEvent(EVENTS.INFO, 'Set state to ' + stringify(val));\n      broadcastEvent(EVENTS.STATECHANGE, state);\n    },\n    \n    viewable: function(val) {\n      isViewable = val;\n      broadcastEvent(EVENTS.INFO, 'Set isViewable to ' + stringify(val));\n      broadcastEvent(EVENTS.VIEWABLECHANGE, isViewable);\n    },\n    \n    placementType: function(val) {\n      broadcastEvent(EVENTS.INFO, 'Set placementType to ' + stringify(val));\n      placementType = val;\n    },\n\n    screenSize: function(val) {\n      broadcastEvent(EVENTS.INFO, 'Set screenSize to ' + stringify(val));\n      for (var key in val) {\n        if (val.hasOwnProperty(key)) screenSize[key] = val[key];\n      }\n\n      if (!hasSetCustomSize) {\n        expandProperties['width'] = screenSize['width'];\n        expandProperties['height'] = screenSize['height'];\n      }\n    },\n    \n    expandProperties: function(val) {\n      broadcastEvent(EVENTS.INFO, 'Merging expandProperties with ' + stringify(val));\n      for (var key in val) {\n        if (val.hasOwnProperty(key)) expandProperties[key] = val[key];\n      }\n    }\n  };\n  \n  var validate = function(obj, validators, action, merge) {\n    if (!merge) {\n      // Check to see if any required properties are missing.\n      if (obj === null) {\n        broadcastEvent(EVENTS.ERROR, 'Required object not provided.', action);\n        return false;\n      } else {\n        for (var i in validators) {\n          if (validators.hasOwnProperty(i) && obj[i] === undefined) {\n            broadcastEvent(EVENTS.ERROR, 'Object is missing required property: ' + i + '.', action);\n            return false;\n          }\n        }\n      }\n    }\n    \n    for (var prop in obj) {\n      var validator = validators[prop];\n      var value = obj[prop];\n      if (validator && !validator(value)) {\n        // Failed validation.\n        broadcastEvent(EVENTS.ERROR, 'Value of property ' + prop + ' is invalid.', \n          action);\n        return false;\n      }\n    }\n    return true;\n  };\n  \n  var expandPropertyValidators = {\n    width: function(v) { return !isNaN(v) && v >= 0; },\n    height: function(v) { return !isNaN(v) && v >= 0; },\n    useCustomClose: function(v) { return (typeof v === 'boolean'); },\n    lockOrientation: function(v) { return (typeof v === 'boolean'); }\n  };\n  \n  //////////////////////////////////////////////////////////////////////////////////////////////////\n  \n  bridge.addEventListener('change', function(properties) {\n    for (var p in properties) {\n      if (properties.hasOwnProperty(p)) {\n        var handler = changeHandlers[p];\n        handler(properties[p]);\n      }\n    }\n  });\n  \n  bridge.addEventListener('error', function(message, action) {\n    broadcastEvent(EVENTS.ERROR, message, action);\n  });\n  \n  bridge.addEventListener('ready', function() {\n    broadcastEvent(EVENTS.READY);\n  });\n\n  //////////////////////////////////////////////////////////////////////////////////////////////////\n  \n  mraid.addEventListener = function(event, listener) {\n    if (!event || !listener) {\n      broadcastEvent(EVENTS.ERROR, 'Both event and listener are required.', 'addEventListener');\n    } else if (!contains(event, EVENTS)) {\n      broadcastEvent(EVENTS.ERROR, 'Unknown MRAID event: ' + event, 'addEventListener');\n    } else {\n      if (!listeners[event]) listeners[event] = new EventListeners(event);\n      listeners[event].add(listener);\n    }\n  };\n  \n  mraid.close = function() {\n    if (state === STATES.HIDDEN) {\n      broadcastEvent(EVENTS.ERROR, 'Ad cannot be closed when it is already hidden.',\n        'close');\n    } else bridge.executeNativeCall('close');\n  };\n  \n  mraid.expand = function(URL) {\n    if (state !== STATES.DEFAULT) {\n      broadcastEvent(EVENTS.ERROR, 'Ad can only be expanded from the default state.', 'expand');\n    } else {\n      var args = ['expand'];\n      \n      if (hasSetCustomClose) {\n        args = args.concat(['shouldUseCustomClose', expandProperties.useCustomClose ? 'true' : 'false']);\n      }\n\n      if (hasSetCustomSize) {\n        if (expandProperties.width >= 0 && expandProperties.height >= 0) {\n          args = args.concat(['w', expandProperties.width, 'h', expandProperties.height]);\n        }\n      }\n      \n      if (typeof expandProperties.lockOrientation !== 'undefined') {\n        args = args.concat(['lockOrientation', expandProperties.lockOrientation]);\n      }\n\n      if (URL) {\n        args = args.concat(['url', URL]);\n      }\n      \n      bridge.executeNativeCall.apply(this, args);\n    }\n  };\n  \n  mraid.getExpandProperties = function() {\n    var properties = {\n      width: expandProperties.width,\n      height: expandProperties.height,\n      useCustomClose: expandProperties.useCustomClose,\n      isModal: expandProperties.isModal\n    };\n    return properties;\n  };\n  \n  mraid.getPlacementType = function() {\n    return placementType;\n  };\n  \n  mraid.getState = function() {\n    return state;\n  };\n  \n  mraid.getVersion = function() {\n    return mraid.VERSION;\n  };\n  \n  mraid.isViewable = function() {\n    return isViewable;\n  };\n  \n  mraid.open = function(URL) {\n    if (!URL) broadcastEvent(EVENTS.ERROR, 'URL is required.', 'open');\n    else bridge.executeNativeCall('open', 'url', URL);\n  };\n\n  mraid.removeEventListener = function(event, listener) {\n    if (!event) broadcastEvent(EVENTS.ERROR, 'Event is required.', 'removeEventListener');\n    else {\n      if (listener && (!listeners[event] || !listeners[event].remove(listener))) {\n        broadcastEvent(EVENTS.ERROR, 'Listener not currently registered for event.', \n          'removeEventListener');\n        return;\n      } else if (listeners[event]) listeners[event].removeAll();\n      \n      if (listeners[event] && listeners[event].count === 0) {\n        listeners[event] = null;\n        delete listeners[event];\n      }\n    }\n  };\n  \n  mraid.setExpandProperties = function(properties) {\n    if (validate(properties, expandPropertyValidators, 'setExpandProperties', true)) {\n      if (properties.hasOwnProperty('width') || properties.hasOwnProperty('height')) {\n        hasSetCustomSize = true;\n      }\n\n      if (properties.hasOwnProperty('useCustomClose')) hasSetCustomClose = true;\n\n      var desiredProperties = ['width', 'height', 'useCustomClose', 'lockOrientation'];\n      var length = desiredProperties.length;\n      for (var i = 0; i < length; i++) {\n        var propname = desiredProperties[i];\n        if (properties.hasOwnProperty(propname)) expandProperties[propname] = properties[propname];\n      }\n    }\n  };\n  \n  mraid.useCustomClose = function(shouldUseCustomClose) {\n    expandProperties.useCustomClose = shouldUseCustomClose;\n    hasSetCustomClose = true;\n    bridge.executeNativeCall('usecustomclose', 'shouldUseCustomClose', shouldUseCustomClose);\n  };\n}());</script>");
        loadDataWithBaseURL(null, data, "text/html", CharEncoding.UTF_8, null);
    }

    public void loadUrl(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        StringBuffer out = new StringBuffer();
        try {
            HttpEntity entity = httpClient.execute(new HttpGet(url)).getEntity();
            if (entity != null) {
                InputStream is = entity.getContent();
                byte[] b = new byte[CodedOutputStream.DEFAULT_BUFFER_SIZE];
                while (true) {
                    int n = is.read(b);
                    if (n == -1) {
                        break;
                    }
                    out.append(new String(b, 0, n));
                }
            }
            loadHtmlData(out.toString());
        } catch (IllegalArgumentException e) {
            Log.d("MoPub", "Mraid loadUrl failed (IllegalArgumentException): " + url);
            notifyOnFailureListener();
        } catch (ClientProtocolException e2) {
            notifyOnFailureListener();
        } catch (IOException e3) {
            notifyOnFailureListener();
        }
    }

    private void notifyOnFailureListener() {
        if (this.mListenerInfo.mOnFailureListener != null) {
            this.mListenerInfo.mOnFailureListener.onFailure(this);
        }
    }

    protected MraidBrowserController getBrowserController() {
        return this.mBrowserController;
    }

    protected MraidDisplayController getDisplayController() {
        return this.mDisplayController;
    }

    public void setOnExpandListener(OnExpandListener listener) {
        this.mListenerInfo.mOnExpandListener = listener;
    }

    public OnExpandListener getOnExpandListener() {
        return this.mListenerInfo.mOnExpandListener;
    }

    public void setOnCloseListener(OnCloseListener listener) {
        this.mListenerInfo.mOnCloseListener = listener;
    }

    public OnCloseListener getOnCloseListener() {
        return this.mListenerInfo.mOnCloseListener;
    }

    public void setOnReadyListener(OnReadyListener listener) {
        this.mListenerInfo.mOnReadyListener = listener;
    }

    public OnReadyListener getOnReadyListener() {
        return this.mListenerInfo.mOnReadyListener;
    }

    public void setOnFailureListener(OnFailureListener listener) {
        this.mListenerInfo.mOnFailureListener = listener;
    }

    public OnFailureListener getOnFailureListener() {
        return this.mListenerInfo.mOnFailureListener;
    }

    public void setOnCloseButtonStateChange(OnCloseButtonStateChangeListener listener) {
        this.mListenerInfo.mOnCloseButtonListener = listener;
    }

    public OnCloseButtonStateChangeListener getOnCloseButtonStateChangeListener() {
        return this.mListenerInfo.mOnCloseButtonListener;
    }

    public void setOnOpenListener(OnOpenListener listener) {
        this.mListenerInfo.mOnOpenListener = listener;
    }

    public OnOpenListener getOnOpenListener() {
        return this.mListenerInfo.mOnOpenListener;
    }

    protected void injectJavaScript(String js) {
        if (js != null) {
            super.loadUrl("javascript:" + js);
        }
    }

    protected void fireChangeEventForProperty(MraidProperty property) {
        String json = "{" + property.toString() + "}";
        injectJavaScript("window.mraidbridge.fireChangeEvent(" + json + ");");
        Log.d(LOGTAG, "Fire change: " + json);
    }

    protected void fireChangeEventForProperties(ArrayList<MraidProperty> properties) {
        String props = properties.toString();
        if (props.length() >= 2) {
            String json = "{" + props.substring(1, props.length() - 1) + "}";
            injectJavaScript("window.mraidbridge.fireChangeEvent(" + json + ");");
            Log.d(LOGTAG, "Fire changes: " + json);
        }
    }

    protected void fireErrorEvent(String action, String message) {
        injectJavaScript("window.mraidbridge.fireErrorEvent('" + action + "', '" + message + "');");
    }

    protected void fireReadyEvent() {
        injectJavaScript("window.mraidbridge.fireReadyEvent();");
    }

    protected void fireNativeCommandCompleteEvent(String command) {
        injectJavaScript("window.mraidbridge.nativeCallComplete('" + command + "');");
    }

    private boolean tryCommand(URI uri) {
        String commandType = uri.getHost();
        List<NameValuePair> list = URLEncodedUtils.parse(uri, CharEncoding.UTF_8);
        Map<String, String> params = new HashMap();
        for (NameValuePair pair : list) {
            params.put(pair.getName(), pair.getValue());
        }
        MraidCommand command = MraidCommandRegistry.createCommand(commandType, params, this);
        if (command == null) {
            fireNativeCommandCompleteEvent(commandType);
            return false;
        }
        command.execute();
        fireNativeCommandCompleteEvent(commandType);
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String copyRawResourceToFilesDir(int r10, java.lang.String r11) {
        /*
        r9 = this;
        r7 = r9.getContext();
        r7 = r7.getResources();
        r5 = r7.openRawResource(r10);
        r7 = new java.lang.StringBuilder;
        r8 = r9.getContext();
        r8 = r8.getFilesDir();
        r8 = r8.getAbsolutePath();
        r8 = java.lang.String.valueOf(r8);
        r7.<init>(r8);
        r8 = java.io.File.separator;
        r7 = r7.append(r8);
        r7 = r7.append(r11);
        r2 = r7.toString();
        r1 = new java.io.File;
        r1.<init>(r2);
        r4 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x004b }
        r4.<init>(r1);	 Catch:{ FileNotFoundException -> 0x004b }
        r7 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0 = new byte[r7];
    L_0x003d:
        r6 = r5.read(r0);	 Catch:{ IOException -> 0x0054, all -> 0x005e }
        r7 = -1;
        if (r6 != r7) goto L_0x004f;
    L_0x0044:
        r5.close();	 Catch:{ IOException -> 0x0066 }
        r4.close();	 Catch:{ IOException -> 0x0066 }
    L_0x004a:
        return r2;
    L_0x004b:
        r3 = move-exception;
        r2 = "";
        goto L_0x004a;
    L_0x004f:
        r7 = 0;
        r4.write(r0, r7, r6);	 Catch:{ IOException -> 0x0054, all -> 0x005e }
        goto L_0x003d;
    L_0x0054:
        r3 = move-exception;
        r5.close();	 Catch:{ IOException -> 0x006a }
        r4.close();	 Catch:{ IOException -> 0x006a }
    L_0x005b:
        r2 = "";
        goto L_0x004a;
    L_0x005e:
        r7 = move-exception;
        r5.close();	 Catch:{ IOException -> 0x0068 }
        r4.close();	 Catch:{ IOException -> 0x0068 }
    L_0x0065:
        throw r7;
    L_0x0066:
        r7 = move-exception;
        goto L_0x004a;
    L_0x0068:
        r8 = move-exception;
        goto L_0x0065;
    L_0x006a:
        r7 = move-exception;
        goto L_0x005b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.adsdk.sdk.mraid.MraidView.copyRawResourceToFilesDir(int, java.lang.String):java.lang.String");
    }
}

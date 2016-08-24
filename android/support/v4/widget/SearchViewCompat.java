package android.support.v4.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

public class SearchViewCompat {
    private static final SearchViewCompatImpl IMPL;

    public static abstract class OnQueryTextListenerCompat {
        final Object mListener;

        public OnQueryTextListenerCompat() {
            this.mListener = SearchViewCompat.IMPL.newOnQueryTextListener(this);
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    interface SearchViewCompatImpl {
        Object newOnQueryTextListener(OnQueryTextListenerCompat onQueryTextListenerCompat);

        View newSearchView(Context context);

        void setOnQueryTextListener(Object obj, Object obj2);
    }

    static class SearchViewCompatStubImpl implements SearchViewCompatImpl {
        SearchViewCompatStubImpl() {
        }

        public View newSearchView(Context context) {
            return null;
        }

        public Object newOnQueryTextListener(OnQueryTextListenerCompat listener) {
            return null;
        }

        public void setOnQueryTextListener(Object searchView, Object listener) {
        }
    }

    static class SearchViewCompatHoneycombImpl extends SearchViewCompatStubImpl {

        /* renamed from: android.support.v4.widget.SearchViewCompat.SearchViewCompatHoneycombImpl.1 */
        class C04341 implements OnQueryTextListenerCompatBridge {
            final /* synthetic */ OnQueryTextListenerCompat val$listener;

            C04341(OnQueryTextListenerCompat onQueryTextListenerCompat) {
                this.val$listener = onQueryTextListenerCompat;
            }

            public boolean onQueryTextSubmit(String query) {
                return this.val$listener.onQueryTextSubmit(query);
            }

            public boolean onQueryTextChange(String newText) {
                return this.val$listener.onQueryTextChange(newText);
            }
        }

        SearchViewCompatHoneycombImpl() {
        }

        public View newSearchView(Context context) {
            return SearchViewCompatHoneycomb.newSearchView(context);
        }

        public Object newOnQueryTextListener(OnQueryTextListenerCompat listener) {
            return SearchViewCompatHoneycomb.newOnQueryTextListener(new C04341(listener));
        }

        public void setOnQueryTextListener(Object searchView, Object listener) {
            SearchViewCompatHoneycomb.setOnQueryTextListener(searchView, listener);
        }
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            IMPL = new SearchViewCompatHoneycombImpl();
        } else {
            IMPL = new SearchViewCompatStubImpl();
        }
    }

    private SearchViewCompat(Context context) {
    }

    public static View newSearchView(Context context) {
        return IMPL.newSearchView(context);
    }

    public static void setOnQueryTextListener(View searchView, OnQueryTextListenerCompat listener) {
        IMPL.setOnQueryTextListener(searchView, listener.mListener);
    }
}

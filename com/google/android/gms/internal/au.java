package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.OnGamesLoadedListener;
import com.google.android.gms.games.OnPlayersLoadedListener;
import com.google.android.gms.games.OnSignOutCompleteListener;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.RealTimeSocket;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
import com.google.android.gms.games.achievement.OnAchievementsLoadedListener;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.OnLeaderboardMetadataLoadedListener;
import com.google.android.gms.games.leaderboard.OnLeaderboardScoresLoadedListener;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.OnInvitationsLoadedListener;
import com.google.android.gms.games.multiplayer.ParticipantUtils;
import com.google.android.gms.games.multiplayer.realtime.C0890a;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeReliableMessageSentListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.internal.C0535k.C0146b;
import com.google.android.gms.internal.C0535k.C0533c;
import com.google.android.gms.internal.C0535k.C0916d;
import com.google.android.gms.internal.az.C0502a;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class au extends C0535k<az> {
    private final Map<String, bb> dA;
    private PlayerEntity dB;
    private GameEntity dC;
    private final ba dD;
    private boolean dE;
    private final Binder dF;
    private final long dG;
    private final boolean dH;
    private final String dz;
    private final String f150g;

    final class ag extends C0146b<RealTimeReliableMessageSentListener> {
        final /* synthetic */ au dJ;
        private final String dZ;
        private final int ea;
        private final int f79p;

        ag(au auVar, RealTimeReliableMessageSentListener realTimeReliableMessageSentListener, int i, int i2, String str) {
            this.dJ = auVar;
            super(auVar, realTimeReliableMessageSentListener);
            this.f79p = i;
            this.ea = i2;
            this.dZ = str;
        }

        public void m713a(RealTimeReliableMessageSentListener realTimeReliableMessageSentListener) {
            if (realTimeReliableMessageSentListener != null) {
                realTimeReliableMessageSentListener.onRealTimeMessageSent(this.f79p, this.ea, this.dZ);
            }
        }

        protected void m715d() {
        }
    }

    final class ao extends C0146b<OnSignOutCompleteListener> {
        final /* synthetic */ au dJ;

        public ao(au auVar, OnSignOutCompleteListener onSignOutCompleteListener) {
            this.dJ = auVar;
            super(auVar, onSignOutCompleteListener);
        }

        public void m716a(OnSignOutCompleteListener onSignOutCompleteListener) {
            onSignOutCompleteListener.onSignOutComplete();
        }

        protected void m718d() {
        }
    }

    final class aq extends C0146b<OnScoreSubmittedListener> {
        final /* synthetic */ au dJ;
        private final SubmitScoreResult eh;

        public aq(au auVar, OnScoreSubmittedListener onScoreSubmittedListener, SubmitScoreResult submitScoreResult) {
            this.dJ = auVar;
            super(auVar, onScoreSubmittedListener);
            this.eh = submitScoreResult;
        }

        public void m719a(OnScoreSubmittedListener onScoreSubmittedListener) {
            onScoreSubmittedListener.onScoreSubmitted(this.eh.getStatusCode(), this.eh);
        }

        protected void m721d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.au.e */
    final class C0492e extends C0146b<OnAchievementUpdatedListener> {
        final /* synthetic */ au dJ;
        private final String dL;
        private final int f80p;

        C0492e(au auVar, OnAchievementUpdatedListener onAchievementUpdatedListener, int i, String str) {
            this.dJ = auVar;
            super(auVar, onAchievementUpdatedListener);
            this.f80p = i;
            this.dL = str;
        }

        protected void m722a(OnAchievementUpdatedListener onAchievementUpdatedListener) {
            onAchievementUpdatedListener.onAchievementUpdated(this.f80p, this.dL);
        }

        protected void m724d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.au.m */
    final class C0493m extends C0146b<OnInvitationReceivedListener> {
        final /* synthetic */ au dJ;
        private final Invitation dP;

        C0493m(au auVar, OnInvitationReceivedListener onInvitationReceivedListener, Invitation invitation) {
            this.dJ = auVar;
            super(auVar, onInvitationReceivedListener);
            this.dP = invitation;
        }

        protected void m725a(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationReceived(this.dP);
        }

        protected void m727d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.au.r */
    final class C0494r extends C0146b<OnLeaderboardScoresLoadedListener> {
        final /* synthetic */ au dJ;
        private final C0468d dS;
        private final C0468d dT;

        C0494r(au auVar, OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener, C0468d c0468d, C0468d c0468d2) {
            this.dJ = auVar;
            super(auVar, onLeaderboardScoresLoadedListener);
            this.dS = c0468d;
            this.dT = c0468d2;
        }

        protected void m728a(OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener) {
            C0468d c0468d = null;
            C0468d c0468d2 = this.dS;
            C0468d c0468d3 = this.dT;
            if (onLeaderboardScoresLoadedListener != null) {
                try {
                    onLeaderboardScoresLoadedListener.onLeaderboardScoresLoaded(c0468d3.getStatusCode(), new LeaderboardBuffer(c0468d2), new LeaderboardScoreBuffer(c0468d3));
                    c0468d3 = null;
                } catch (Throwable th) {
                    if (c0468d2 != null) {
                        c0468d2.close();
                    }
                    if (c0468d3 != null) {
                        c0468d3.close();
                    }
                }
            } else {
                c0468d = c0468d3;
                c0468d3 = c0468d2;
            }
            if (c0468d3 != null) {
                c0468d3.close();
            }
            if (c0468d != null) {
                c0468d.close();
            }
        }

        protected void m730d() {
            if (this.dS != null) {
                this.dS.close();
            }
            if (this.dT != null) {
                this.dT.close();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.au.u */
    final class C0495u extends C0146b<RoomUpdateListener> {
        final /* synthetic */ au dJ;
        private final String dV;
        private final int f81p;

        C0495u(au auVar, RoomUpdateListener roomUpdateListener, int i, String str) {
            this.dJ = auVar;
            super(auVar, roomUpdateListener);
            this.f81p = i;
            this.dV = str;
        }

        public void m731a(RoomUpdateListener roomUpdateListener) {
            roomUpdateListener.onLeftRoom(this.f81p, this.dV);
        }

        protected void m733d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.au.v */
    final class C0496v extends C0146b<RealTimeMessageReceivedListener> {
        final /* synthetic */ au dJ;
        private final RealTimeMessage dW;

        C0496v(au auVar, RealTimeMessageReceivedListener realTimeMessageReceivedListener, RealTimeMessage realTimeMessage) {
            this.dJ = auVar;
            super(auVar, realTimeMessageReceivedListener);
            this.dW = realTimeMessage;
        }

        public void m734a(RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            ax.m217a("GamesClient", "Deliver Message received callback");
            if (realTimeMessageReceivedListener != null) {
                realTimeMessageReceivedListener.onRealTimeMessageReceived(this.dW);
            }
        }

        protected void m736d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.au.w */
    final class C0497w extends C0146b<RoomStatusUpdateListener> {
        final /* synthetic */ au dJ;
        private final String dX;

        C0497w(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, String str) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener);
            this.dX = str;
        }

        public void m737a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PConnected(this.dX);
            }
        }

        protected void m739d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.au.x */
    final class C0498x extends C0146b<RoomStatusUpdateListener> {
        final /* synthetic */ au dJ;
        private final String dX;

        C0498x(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, String str) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener);
            this.dX = str;
        }

        public void m740a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PDisconnected(this.dX);
            }
        }

        protected void m742d() {
        }
    }

    final class af extends C0533c<OnPlayersLoadedListener> {
        final /* synthetic */ au dJ;

        af(au auVar, OnPlayersLoadedListener onPlayersLoadedListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, onPlayersLoadedListener, c0468d);
        }

        protected void m1202a(OnPlayersLoadedListener onPlayersLoadedListener, C0468d c0468d) {
            onPlayersLoadedListener.onPlayersLoaded(c0468d.getStatusCode(), new PlayerBuffer(c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.b */
    abstract class C0892b extends C0533c<RoomUpdateListener> {
        final /* synthetic */ au dJ;

        C0892b(au auVar, RoomUpdateListener roomUpdateListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, roomUpdateListener, c0468d);
        }

        protected void m1204a(RoomUpdateListener roomUpdateListener, C0468d c0468d) {
            m1205a(roomUpdateListener, this.dJ.m1220x(c0468d), c0468d.getStatusCode());
        }

        protected abstract void m1205a(RoomUpdateListener roomUpdateListener, Room room, int i);
    }

    /* renamed from: com.google.android.gms.internal.au.c */
    abstract class C0893c extends C0533c<RoomStatusUpdateListener> {
        final /* synthetic */ au dJ;

        C0893c(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d);
        }

        protected void m1207a(RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d) {
            m1208a(roomStatusUpdateListener, this.dJ.m1220x(c0468d));
        }

        protected abstract void m1208a(RoomStatusUpdateListener roomStatusUpdateListener, Room room);
    }

    /* renamed from: com.google.android.gms.internal.au.g */
    final class C0894g extends C0533c<OnAchievementsLoadedListener> {
        final /* synthetic */ au dJ;

        C0894g(au auVar, OnAchievementsLoadedListener onAchievementsLoadedListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, onAchievementsLoadedListener, c0468d);
        }

        protected void m1210a(OnAchievementsLoadedListener onAchievementsLoadedListener, C0468d c0468d) {
            onAchievementsLoadedListener.onAchievementsLoaded(c0468d.getStatusCode(), new AchievementBuffer(c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.k */
    final class C0895k extends C0533c<OnGamesLoadedListener> {
        final /* synthetic */ au dJ;

        C0895k(au auVar, OnGamesLoadedListener onGamesLoadedListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, onGamesLoadedListener, c0468d);
        }

        protected void m1212a(OnGamesLoadedListener onGamesLoadedListener, C0468d c0468d) {
            onGamesLoadedListener.onGamesLoaded(c0468d.getStatusCode(), new GameBuffer(c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.o */
    final class C0896o extends C0533c<OnInvitationsLoadedListener> {
        final /* synthetic */ au dJ;

        C0896o(au auVar, OnInvitationsLoadedListener onInvitationsLoadedListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, onInvitationsLoadedListener, c0468d);
        }

        protected void m1214a(OnInvitationsLoadedListener onInvitationsLoadedListener, C0468d c0468d) {
            onInvitationsLoadedListener.onInvitationsLoaded(c0468d.getStatusCode(), new InvitationBuffer(c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.t */
    final class C0897t extends C0533c<OnLeaderboardMetadataLoadedListener> {
        final /* synthetic */ au dJ;

        C0897t(au auVar, OnLeaderboardMetadataLoadedListener onLeaderboardMetadataLoadedListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, onLeaderboardMetadataLoadedListener, c0468d);
        }

        protected void m1216a(OnLeaderboardMetadataLoadedListener onLeaderboardMetadataLoadedListener, C0468d c0468d) {
            onLeaderboardMetadataLoadedListener.onLeaderboardMetadataLoaded(c0468d.getStatusCode(), new LeaderboardBuffer(c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.a */
    abstract class C1055a extends C0893c {
        private final ArrayList<String> dI;
        final /* synthetic */ au dJ;

        C1055a(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d, String[] strArr) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d);
            this.dI = new ArrayList();
            for (Object add : strArr) {
                this.dI.add(add);
            }
        }

        protected void m1412a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            m1413a(roomStatusUpdateListener, room, this.dI);
        }

        protected abstract void m1413a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList);
    }

    final class ae extends at {
        final /* synthetic */ au dJ;
        private final OnPlayersLoadedListener dY;

        ae(au auVar, OnPlayersLoadedListener onPlayersLoadedListener) {
            this.dJ = auVar;
            this.dY = (OnPlayersLoadedListener) C0159s.m514b((Object) onPlayersLoadedListener, (Object) "Listener must not be null");
        }

        public void m1414e(C0468d c0468d) {
            this.dJ.m998a(new af(this.dJ, this.dY, c0468d));
        }
    }

    final class ah extends at {
        final /* synthetic */ au dJ;
        final RealTimeReliableMessageSentListener eb;

        public ah(au auVar, RealTimeReliableMessageSentListener realTimeReliableMessageSentListener) {
            this.dJ = auVar;
            this.eb = realTimeReliableMessageSentListener;
        }

        public void m1415a(int i, int i2, String str) {
            this.dJ.m998a(new ag(this.dJ, this.eb, i, i2, str));
        }
    }

    final class ai extends C0893c {
        final /* synthetic */ au dJ;

        ai(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d);
        }

        public void m1416a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomAutoMatching(room);
        }
    }

    final class aj extends at {
        final /* synthetic */ au dJ;
        private final RoomUpdateListener ec;
        private final RoomStatusUpdateListener ed;
        private final RealTimeMessageReceivedListener ee;

        public aj(au auVar, RoomUpdateListener roomUpdateListener) {
            this.dJ = auVar;
            this.ec = (RoomUpdateListener) C0159s.m514b((Object) roomUpdateListener, (Object) "Callbacks must not be null");
            this.ed = null;
            this.ee = null;
        }

        public aj(au auVar, RoomUpdateListener roomUpdateListener, RoomStatusUpdateListener roomStatusUpdateListener, RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            this.dJ = auVar;
            this.ec = (RoomUpdateListener) C0159s.m514b((Object) roomUpdateListener, (Object) "Callbacks must not be null");
            this.ed = roomStatusUpdateListener;
            this.ee = realTimeMessageReceivedListener;
        }

        public void m1417a(C0468d c0468d, String[] strArr) {
            this.dJ.m998a(new ab(this.dJ, this.ed, c0468d, strArr));
        }

        public void m1418b(C0468d c0468d, String[] strArr) {
            this.dJ.m998a(new ac(this.dJ, this.ed, c0468d, strArr));
        }

        public void m1419c(C0468d c0468d, String[] strArr) {
            this.dJ.m998a(new ad(this.dJ, this.ed, c0468d, strArr));
        }

        public void m1420d(C0468d c0468d, String[] strArr) {
            this.dJ.m998a(new C1098z(this.dJ, this.ed, c0468d, strArr));
        }

        public void m1421e(C0468d c0468d, String[] strArr) {
            this.dJ.m998a(new C1097y(this.dJ, this.ed, c0468d, strArr));
        }

        public void m1422f(C0468d c0468d, String[] strArr) {
            this.dJ.m998a(new aa(this.dJ, this.ed, c0468d, strArr));
        }

        public void m1423n(C0468d c0468d) {
            this.dJ.m998a(new am(this.dJ, this.ec, c0468d));
        }

        public void m1424o(C0468d c0468d) {
            this.dJ.m998a(new C1063p(this.dJ, this.ec, c0468d));
        }

        public void onLeftRoom(int statusCode, String externalRoomId) {
            this.dJ.m998a(new C0495u(this.dJ, this.ec, statusCode, externalRoomId));
        }

        public void onP2PConnected(String participantId) {
            this.dJ.m998a(new C0497w(this.dJ, this.ed, participantId));
        }

        public void onP2PDisconnected(String participantId) {
            this.dJ.m998a(new C0498x(this.dJ, this.ed, participantId));
        }

        public void onRealTimeMessageReceived(RealTimeMessage message) {
            ax.m217a("GamesClient", "RoomBinderCallbacks: onRealTimeMessageReceived");
            this.dJ.m998a(new C0496v(this.dJ, this.ee, message));
        }

        public void m1425p(C0468d c0468d) {
            this.dJ.m998a(new al(this.dJ, this.ed, c0468d));
        }

        public void m1426q(C0468d c0468d) {
            this.dJ.m998a(new ai(this.dJ, this.ed, c0468d));
        }

        public void m1427r(C0468d c0468d) {
            this.dJ.m998a(new ak(this.dJ, this.ec, c0468d));
        }

        public void m1428s(C0468d c0468d) {
            this.dJ.m998a(new C1058h(this.dJ, this.ed, c0468d));
        }

        public void m1429t(C0468d c0468d) {
            this.dJ.m998a(new C1059i(this.dJ, this.ed, c0468d));
        }
    }

    final class ak extends C0892b {
        final /* synthetic */ au dJ;

        ak(au auVar, RoomUpdateListener roomUpdateListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, roomUpdateListener, c0468d);
        }

        public void m1430a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomConnected(i, room);
        }
    }

    final class al extends C0893c {
        final /* synthetic */ au dJ;

        al(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d);
        }

        public void m1431a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomConnecting(room);
        }
    }

    final class am extends C0892b {
        final /* synthetic */ au dJ;

        public am(au auVar, RoomUpdateListener roomUpdateListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, roomUpdateListener, c0468d);
        }

        public void m1432a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomCreated(i, room);
        }
    }

    final class an extends at {
        final /* synthetic */ au dJ;
        private final OnSignOutCompleteListener ef;

        public an(au auVar, OnSignOutCompleteListener onSignOutCompleteListener) {
            this.dJ = auVar;
            this.ef = (OnSignOutCompleteListener) C0159s.m514b((Object) onSignOutCompleteListener, (Object) "Listener must not be null");
        }

        public void onSignOutComplete() {
            this.dJ.m998a(new ao(this.dJ, this.ef));
        }
    }

    final class ap extends at {
        final /* synthetic */ au dJ;
        private final OnScoreSubmittedListener eg;

        public ap(au auVar, OnScoreSubmittedListener onScoreSubmittedListener) {
            this.dJ = auVar;
            this.eg = (OnScoreSubmittedListener) C0159s.m514b((Object) onScoreSubmittedListener, (Object) "Listener must not be null");
        }

        public void m1433d(C0468d c0468d) {
            this.dJ.m998a(new aq(this.dJ, this.eg, new SubmitScoreResult(c0468d)));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.d */
    final class C1056d extends at {
        final /* synthetic */ au dJ;
        private final OnAchievementUpdatedListener dK;

        C1056d(au auVar, OnAchievementUpdatedListener onAchievementUpdatedListener) {
            this.dJ = auVar;
            this.dK = (OnAchievementUpdatedListener) C0159s.m514b((Object) onAchievementUpdatedListener, (Object) "Listener must not be null");
        }

        public void onAchievementUpdated(int statusCode, String achievementId) {
            this.dJ.m998a(new C0492e(this.dJ, this.dK, statusCode, achievementId));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.f */
    final class C1057f extends at {
        final /* synthetic */ au dJ;
        private final OnAchievementsLoadedListener dM;

        C1057f(au auVar, OnAchievementsLoadedListener onAchievementsLoadedListener) {
            this.dJ = auVar;
            this.dM = (OnAchievementsLoadedListener) C0159s.m514b((Object) onAchievementsLoadedListener, (Object) "Listener must not be null");
        }

        public void m1434b(C0468d c0468d) {
            this.dJ.m998a(new C0894g(this.dJ, this.dM, c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.h */
    final class C1058h extends C0893c {
        final /* synthetic */ au dJ;

        C1058h(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d);
        }

        public void m1435a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onConnectedToRoom(room);
        }
    }

    /* renamed from: com.google.android.gms.internal.au.i */
    final class C1059i extends C0893c {
        final /* synthetic */ au dJ;

        C1059i(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d);
        }

        public void m1436a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onDisconnectedFromRoom(room);
        }
    }

    /* renamed from: com.google.android.gms.internal.au.j */
    final class C1060j extends at {
        final /* synthetic */ au dJ;
        private final OnGamesLoadedListener dN;

        C1060j(au auVar, OnGamesLoadedListener onGamesLoadedListener) {
            this.dJ = auVar;
            this.dN = (OnGamesLoadedListener) C0159s.m514b((Object) onGamesLoadedListener, (Object) "Listener must not be null");
        }

        public void m1437g(C0468d c0468d) {
            this.dJ.m998a(new C0895k(this.dJ, this.dN, c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.l */
    final class C1061l extends at {
        final /* synthetic */ au dJ;
        private final OnInvitationReceivedListener dO;

        C1061l(au auVar, OnInvitationReceivedListener onInvitationReceivedListener) {
            this.dJ = auVar;
            this.dO = onInvitationReceivedListener;
        }

        public void m1438k(C0468d c0468d) {
            InvitationBuffer invitationBuffer = new InvitationBuffer(c0468d);
            Invitation invitation = null;
            try {
                if (invitationBuffer.getCount() > 0) {
                    invitation = (Invitation) ((Invitation) invitationBuffer.get(0)).freeze();
                }
                invitationBuffer.close();
                if (invitation != null) {
                    this.dJ.m998a(new C0493m(this.dJ, this.dO, invitation));
                }
            } catch (Throwable th) {
                invitationBuffer.close();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.au.n */
    final class C1062n extends at {
        final /* synthetic */ au dJ;
        private final OnInvitationsLoadedListener dQ;

        C1062n(au auVar, OnInvitationsLoadedListener onInvitationsLoadedListener) {
            this.dJ = auVar;
            this.dQ = onInvitationsLoadedListener;
        }

        public void m1439j(C0468d c0468d) {
            this.dJ.m998a(new C0896o(this.dJ, this.dQ, c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.p */
    final class C1063p extends C0892b {
        final /* synthetic */ au dJ;

        public C1063p(au auVar, RoomUpdateListener roomUpdateListener, C0468d c0468d) {
            this.dJ = auVar;
            super(auVar, roomUpdateListener, c0468d);
        }

        public void m1440a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onJoinedRoom(i, room);
        }
    }

    /* renamed from: com.google.android.gms.internal.au.q */
    final class C1064q extends at {
        final /* synthetic */ au dJ;
        private final OnLeaderboardScoresLoadedListener dR;

        C1064q(au auVar, OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener) {
            this.dJ = auVar;
            this.dR = (OnLeaderboardScoresLoadedListener) C0159s.m514b((Object) onLeaderboardScoresLoadedListener, (Object) "Listener must not be null");
        }

        public void m1441a(C0468d c0468d, C0468d c0468d2) {
            this.dJ.m998a(new C0494r(this.dJ, this.dR, c0468d, c0468d2));
        }
    }

    /* renamed from: com.google.android.gms.internal.au.s */
    final class C1065s extends at {
        final /* synthetic */ au dJ;
        private final OnLeaderboardMetadataLoadedListener dU;

        C1065s(au auVar, OnLeaderboardMetadataLoadedListener onLeaderboardMetadataLoadedListener) {
            this.dJ = auVar;
            this.dU = (OnLeaderboardMetadataLoadedListener) C0159s.m514b((Object) onLeaderboardMetadataLoadedListener, (Object) "Listener must not be null");
        }

        public void m1442c(C0468d c0468d) {
            this.dJ.m998a(new C0897t(this.dJ, this.dU, c0468d));
        }
    }

    final class aa extends C1055a {
        final /* synthetic */ au dJ;

        aa(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d, String[] strArr) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d, strArr);
        }

        protected void m1448a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersDisconnected(room, arrayList);
        }
    }

    final class ab extends C1055a {
        final /* synthetic */ au dJ;

        ab(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d, String[] strArr) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d, strArr);
        }

        protected void m1449a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerInvitedToRoom(room, arrayList);
        }
    }

    final class ac extends C1055a {
        final /* synthetic */ au dJ;

        ac(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d, String[] strArr) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d, strArr);
        }

        protected void m1450a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerJoined(room, arrayList);
        }
    }

    final class ad extends C1055a {
        final /* synthetic */ au dJ;

        ad(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d, String[] strArr) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d, strArr);
        }

        protected void m1451a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerLeft(room, arrayList);
        }
    }

    /* renamed from: com.google.android.gms.internal.au.y */
    final class C1097y extends C1055a {
        final /* synthetic */ au dJ;

        C1097y(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d, String[] strArr) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d, strArr);
        }

        protected void m1452a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersConnected(room, arrayList);
        }
    }

    /* renamed from: com.google.android.gms.internal.au.z */
    final class C1098z extends C1055a {
        final /* synthetic */ au dJ;

        C1098z(au auVar, RoomStatusUpdateListener roomStatusUpdateListener, C0468d c0468d, String[] strArr) {
            this.dJ = auVar;
            super(auVar, roomStatusUpdateListener, c0468d, strArr);
        }

        protected void m1453a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerDeclined(room, arrayList);
        }
    }

    public au(Context context, String str, String str2, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String[] strArr, int i, View view, boolean z) {
        super(context, connectionCallbacks, onConnectionFailedListener, strArr);
        this.dE = false;
        this.dz = str;
        this.f150g = (String) C0159s.m517d(str2);
        this.dF = new Binder();
        this.dA = new HashMap();
        this.dD = ba.m330a(this, i);
        setViewForPopups(view);
        this.dG = (long) hashCode();
        this.dH = z;
    }

    private void av() {
        this.dB = null;
    }

    private void aw() {
        for (bb close : this.dA.values()) {
            try {
                close.close();
            } catch (Throwable e) {
                ax.m218a("GamesClient", "IOException:", e);
            }
        }
        this.dA.clear();
    }

    private bb m1219t(String str) {
        try {
            String v = ((az) m995C()).m325v(str);
            if (v == null) {
                return null;
            }
            ax.m221d("GamesClient", "Creating a socket to bind to:" + v);
            LocalSocket localSocket = new LocalSocket();
            try {
                localSocket.connect(new LocalSocketAddress(v));
                bb bbVar = new bb(localSocket, str);
                this.dA.put(str, bbVar);
                return bbVar;
            } catch (IOException e) {
                ax.m220c("GamesClient", "connect() call failed on socket: " + e.getMessage());
                return null;
            }
        } catch (RemoteException e2) {
            ax.m220c("GamesClient", "Unable to create socket. Service died.");
            return null;
        }
    }

    private Room m1220x(C0468d c0468d) {
        C0890a c0890a = new C0890a(c0468d);
        Room room = null;
        try {
            if (c0890a.getCount() > 0) {
                room = (Room) ((Room) c0890a.get(0)).freeze();
            }
            c0890a.close();
            return room;
        } catch (Throwable th) {
            c0890a.close();
        }
    }

    public int m1221a(byte[] bArr, String str, String[] strArr) {
        C0159s.m514b((Object) strArr, (Object) "Participant IDs must not be null");
        try {
            return ((az) m995C()).m282b(bArr, str, strArr);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
            return -1;
        }
    }

    protected void m1222a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 && bundle != null) {
            this.dE = bundle.getBoolean("show_welcome_popup");
        }
        super.m996a(i, iBinder, bundle);
    }

    public void m1223a(IBinder iBinder, Bundle bundle) {
        if (isConnected()) {
            try {
                ((az) m995C()).m260a(iBinder, bundle);
            } catch (RemoteException e) {
                ax.m219b("GamesClient", "service died");
            }
        }
    }

    protected void m1224a(ConnectionResult connectionResult) {
        super.m997a(connectionResult);
        this.dE = false;
    }

    public void m1225a(OnPlayersLoadedListener onPlayersLoadedListener, int i, boolean z, boolean z2) {
        try {
            ((az) m995C()).m263a(new ae(this, onPlayersLoadedListener), i, z, z2);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void m1226a(OnAchievementUpdatedListener onAchievementUpdatedListener, String str) {
        if (onAchievementUpdatedListener == null) {
            ay ayVar = null;
        } else {
            Object c1056d = new C1056d(this, onAchievementUpdatedListener);
        }
        try {
            ((az) m995C()).m274a(ayVar, str, this.dD.aD(), this.dD.aC());
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void m1227a(OnAchievementUpdatedListener onAchievementUpdatedListener, String str, int i) {
        try {
            ((az) m995C()).m270a(onAchievementUpdatedListener == null ? null : new C1056d(this, onAchievementUpdatedListener), str, i, this.dD.aD(), this.dD.aC());
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void m1228a(OnScoreSubmittedListener onScoreSubmittedListener, String str, long j) {
        if (onScoreSubmittedListener == null) {
            ay ayVar = null;
        } else {
            Object apVar = new ap(this, onScoreSubmittedListener);
        }
        try {
            ((az) m995C()).m273a(ayVar, str, j);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    protected void m1229a(C0154p c0154p, C0916d c0916d) throws RemoteException {
        String locale = getContext().getResources().getConfiguration().locale.toString();
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.google.android.gms.games.key.isHeadless", this.dH);
        c0154p.m499a(c0916d, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.f150g, m1005x(), this.dz, this.dD.aD(), locale, bundle);
    }

    protected void m1230a(String... strArr) {
        int i = 0;
        boolean z = false;
        for (String str : strArr) {
            if (str.equals(Scopes.GAMES)) {
                z = true;
            } else if (str.equals("https://www.googleapis.com/auth/games.firstparty")) {
                i = 1;
            }
        }
        if (i != 0) {
            C0159s.m512a(!z, String.format("Cannot have both %s and %s!", new Object[]{Scopes.GAMES, "https://www.googleapis.com/auth/games.firstparty"}));
            return;
        }
        C0159s.m512a(z, String.format("GamesClient requires %s to function.", new Object[]{Scopes.GAMES}));
    }

    public void ax() {
        if (isConnected()) {
            try {
                ((az) m995C()).ax();
            } catch (RemoteException e) {
                ax.m219b("GamesClient", "service died");
            }
        }
    }

    protected String m1231b() {
        return "com.google.android.gms.games.service.START";
    }

    public void m1232b(OnAchievementUpdatedListener onAchievementUpdatedListener, String str) {
        if (onAchievementUpdatedListener == null) {
            ay ayVar = null;
        } else {
            Object c1056d = new C1056d(this, onAchievementUpdatedListener);
        }
        try {
            ((az) m995C()).m290b(ayVar, str, this.dD.aD(), this.dD.aC());
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    protected /* synthetic */ IInterface m1233c(IBinder iBinder) {
        return m1237m(iBinder);
    }

    protected String m1234c() {
        return "com.google.android.gms.games.internal.IGamesService";
    }

    public void clearNotifications(int notificationTypes) {
        try {
            ((az) m995C()).clearNotifications(notificationTypes);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void connect() {
        av();
        super.connect();
    }

    public void createRoom(RoomConfig config) {
        try {
            ((az) m995C()).m266a(new aj(this, config.getRoomUpdateListener(), config.getRoomStatusUpdateListener(), config.getMessageReceivedListener()), this.dF, config.getVariant(), config.getInvitedPlayerIds(), config.getAutoMatchCriteria(), config.isSocketEnabled(), this.dG);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void disconnect() {
        this.dE = false;
        if (isConnected()) {
            try {
                az azVar = (az) m995C();
                azVar.ax();
                azVar.m284b(this.dG);
                azVar.m259a(this.dG);
            } catch (RemoteException e) {
                ax.m219b("GamesClient", "Failed to notify client disconnect.");
            }
        }
        aw();
        super.disconnect();
    }

    public Intent getAchievementsIntent() {
        m994B();
        Intent intent = new Intent("com.google.android.gms.games.VIEW_ACHIEVEMENTS");
        intent.addFlags(67108864);
        return aw.m216b(intent);
    }

    public Intent getAllLeaderboardsIntent() {
        m994B();
        Intent intent = new Intent("com.google.android.gms.games.VIEW_LEADERBOARDS");
        intent.putExtra("com.google.android.gms.games.GAME_PACKAGE_NAME", this.dz);
        intent.addFlags(67108864);
        return aw.m216b(intent);
    }

    public String getAppId() {
        try {
            return ((az) m995C()).getAppId();
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
            return null;
        }
    }

    public String getCurrentAccountName() {
        try {
            return ((az) m995C()).getCurrentAccountName();
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
            return null;
        }
    }

    public Game getCurrentGame() {
        GameBuffer gameBuffer;
        m994B();
        synchronized (this) {
            if (this.dC == null) {
                try {
                    gameBuffer = new GameBuffer(((az) m995C()).aA());
                    if (gameBuffer.getCount() > 0) {
                        this.dC = (GameEntity) gameBuffer.get(0).freeze();
                    }
                    gameBuffer.close();
                } catch (RemoteException e) {
                    ax.m219b("GamesClient", "service died");
                } catch (Throwable th) {
                    gameBuffer.close();
                }
            }
        }
        return this.dC;
    }

    public Player getCurrentPlayer() {
        m994B();
        synchronized (this) {
            if (this.dB == null) {
                PlayerBuffer playerBuffer;
                try {
                    playerBuffer = new PlayerBuffer(((az) m995C()).ay());
                    if (playerBuffer.getCount() > 0) {
                        this.dB = (PlayerEntity) playerBuffer.get(0).freeze();
                    }
                    playerBuffer.close();
                } catch (RemoteException e) {
                    ax.m219b("GamesClient", "service died");
                } catch (Throwable th) {
                    playerBuffer.close();
                }
            }
        }
        return this.dB;
    }

    public String getCurrentPlayerId() {
        try {
            return ((az) m995C()).getCurrentPlayerId();
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
            return null;
        }
    }

    public Intent getInvitationInboxIntent() {
        m994B();
        Intent intent = new Intent("com.google.android.gms.games.SHOW_INVITATIONS");
        intent.putExtra("com.google.android.gms.games.GAME_PACKAGE_NAME", this.dz);
        return aw.m216b(intent);
    }

    public Intent getLeaderboardIntent(String leaderboardId) {
        m994B();
        Intent intent = new Intent("com.google.android.gms.games.VIEW_LEADERBOARD_SCORES");
        intent.putExtra("com.google.android.gms.games.LEADERBOARD_ID", leaderboardId);
        intent.addFlags(67108864);
        return aw.m216b(intent);
    }

    public RealTimeSocket getRealTimeSocketForParticipant(String roomId, String participantId) {
        if (participantId == null || !ParticipantUtils.m147z(participantId)) {
            throw new IllegalArgumentException("Bad participant ID");
        }
        bb bbVar = (bb) this.dA.get(participantId);
        return (bbVar == null || bbVar.isClosed()) ? m1219t(participantId) : bbVar;
    }

    public Intent getRealTimeWaitingRoomIntent(Room room, int minParticipantsToStart) {
        m994B();
        Intent intent = new Intent("com.google.android.gms.games.SHOW_REAL_TIME_WAITING_ROOM");
        C0159s.m514b((Object) room, (Object) "Room parameter must not be null");
        intent.putExtra(GamesClient.EXTRA_ROOM, (Parcelable) room.freeze());
        C0159s.m512a(minParticipantsToStart >= 0, "minParticipantsToStart must be >= 0");
        intent.putExtra("com.google.android.gms.games.MIN_PARTICIPANTS_TO_START", minParticipantsToStart);
        return aw.m216b(intent);
    }

    public Intent getSelectPlayersIntent(int minPlayers, int maxPlayers) {
        m994B();
        Intent intent = new Intent("com.google.android.gms.games.SELECT_PLAYERS");
        intent.putExtra("com.google.android.gms.games.MIN_SELECTIONS", minPlayers);
        intent.putExtra("com.google.android.gms.games.MAX_SELECTIONS", maxPlayers);
        return aw.m216b(intent);
    }

    public Intent getSettingsIntent() {
        m994B();
        Intent intent = new Intent("com.google.android.gms.games.SHOW_SETTINGS");
        intent.putExtra("com.google.android.gms.games.GAME_PACKAGE_NAME", this.dz);
        intent.addFlags(67108864);
        return aw.m216b(intent);
    }

    public void m1235h(String str, int i) {
        try {
            ((az) m995C()).m316h(str, i);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void m1236i(String str, int i) {
        try {
            ((az) m995C()).m319i(str, i);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void joinRoom(RoomConfig config) {
        try {
            ((az) m995C()).m267a(new aj(this, config.getRoomUpdateListener(), config.getRoomStatusUpdateListener(), config.getMessageReceivedListener()), this.dF, config.getInvitationId(), config.isSocketEnabled(), this.dG);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void leaveRoom(RoomUpdateListener listener, String roomId) {
        try {
            ((az) m995C()).m308e(new aj(this, listener), roomId);
            aw();
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void loadAchievements(OnAchievementsLoadedListener listener, boolean forceReload) {
        try {
            ((az) m995C()).m295b(new C1057f(this, listener), forceReload);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void loadGame(OnGamesLoadedListener listener) {
        try {
            ((az) m995C()).m302d(new C1060j(this, listener));
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void loadInvitations(OnInvitationsLoadedListener listener) {
        try {
            ((az) m995C()).m306e(new C1062n(this, listener));
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void loadLeaderboardMetadata(OnLeaderboardMetadataLoadedListener listener, String leaderboardId, boolean forceReload) {
        try {
            ((az) m995C()).m300c(new C1065s(this, listener), leaderboardId, forceReload);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void loadLeaderboardMetadata(OnLeaderboardMetadataLoadedListener listener, boolean forceReload) {
        try {
            ((az) m995C()).m301c(new C1065s(this, listener), forceReload);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void loadMoreScores(OnLeaderboardScoresLoadedListener listener, LeaderboardScoreBuffer buffer, int maxResults, int pageDirection) {
        try {
            ((az) m995C()).m265a(new C1064q(this, listener), buffer.aF().aG(), maxResults, pageDirection);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void loadPlayer(OnPlayersLoadedListener listener, String playerId) {
        try {
            ((az) m995C()).m298c(new ae(this, listener), playerId);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void loadPlayerCenteredScores(OnLeaderboardScoresLoadedListener listener, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
        try {
            ((az) m995C()).m288b(new C1064q(this, listener), leaderboardId, span, leaderboardCollection, maxResults, forceReload);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void loadTopScores(OnLeaderboardScoresLoadedListener listener, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
        try {
            ((az) m995C()).m269a(new C1064q(this, listener), leaderboardId, span, leaderboardCollection, maxResults, forceReload);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    protected az m1237m(IBinder iBinder) {
        return C0502a.m852o(iBinder);
    }

    public void registerInvitationListener(OnInvitationReceivedListener listener) {
        try {
            ((az) m995C()).m264a(new C1061l(this, listener), this.dG);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public int sendReliableRealTimeMessage(RealTimeReliableMessageSentListener listener, byte[] messageData, String roomId, String recipientParticipantId) {
        try {
            return ((az) m995C()).m258a(new ah(this, listener), messageData, roomId, recipientParticipantId);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
            return -1;
        }
    }

    public int sendUnreliableRealTimeMessageToAll(byte[] messageData, String roomId) {
        try {
            return ((az) m995C()).m282b(messageData, roomId, null);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
            return -1;
        }
    }

    public void setGravityForPopups(int gravity) {
        this.dD.setGravity(gravity);
    }

    public void setUseNewPlayerNotificationsFirstParty(boolean newPlayerStyle) {
        try {
            ((az) m995C()).setUseNewPlayerNotificationsFirstParty(newPlayerStyle);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void setViewForPopups(View gamesContentView) {
        this.dD.m332a(gamesContentView);
    }

    public void signOut(OnSignOutCompleteListener listener) {
        if (listener == null) {
            ay ayVar = null;
        } else {
            Object anVar = new an(this, listener);
        }
        try {
            ((az) m995C()).m261a(ayVar);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    public void unregisterInvitationListener() {
        try {
            ((az) m995C()).m284b(this.dG);
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
        }
    }

    protected void m1238y() {
        super.m1006y();
        if (this.dE) {
            this.dD.aB();
            this.dE = false;
        }
    }

    protected Bundle m1239z() {
        try {
            Bundle z = ((az) m995C()).m329z();
            if (z == null) {
                return z;
            }
            z.setClassLoader(au.class.getClassLoader());
            return z;
        } catch (RemoteException e) {
            ax.m219b("GamesClient", "service died");
            return null;
        }
    }
}

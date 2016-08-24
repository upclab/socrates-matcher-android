package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.internal.C0158r;
import com.google.android.gms.internal.C0158r.C0157a;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.internal.bd;
import java.util.HashMap;

public final class SubmitScoreResult {
    private static final String[] eB;
    private String dx;
    private String eC;
    private HashMap<Integer, Result> eD;
    private int f40p;

    public static final class Result {
        public final String formattedScore;
        public final boolean newBest;
        public final long rawScore;

        public Result(long rawScore, String formattedScore, boolean newBest) {
            this.rawScore = rawScore;
            this.formattedScore = formattedScore;
            this.newBest = newBest;
        }

        public String toString() {
            return C0158r.m510c(this).m508a("RawScore", Long.valueOf(this.rawScore)).m508a("FormattedScore", this.formattedScore).m508a("NewBest", Boolean.valueOf(this.newBest)).toString();
        }
    }

    static {
        eB = new String[]{"leaderboardId", "playerId", "timeSpan", "hasResult", "rawScore", "formattedScore", "newBest"};
    }

    public SubmitScoreResult(int statusCode, String leaderboardId, String playerId) {
        this(statusCode, leaderboardId, playerId, new HashMap());
    }

    public SubmitScoreResult(int statusCode, String leaderboardId, String playerId, HashMap<Integer, Result> results) {
        this.f40p = statusCode;
        this.eC = leaderboardId;
        this.dx = playerId;
        this.eD = results;
    }

    public SubmitScoreResult(C0468d dataHolder) {
        int i = 0;
        this.f40p = dataHolder.getStatusCode();
        this.eD = new HashMap();
        int count = dataHolder.getCount();
        C0159s.m516c(count == 3);
        while (i < count) {
            int e = dataHolder.m636e(i);
            if (i == 0) {
                this.eC = dataHolder.m634c("leaderboardId", i, e);
                this.dx = dataHolder.m634c("playerId", i, e);
            }
            if (dataHolder.m635d("hasResult", i, e)) {
                m146a(new Result(dataHolder.m631a("rawScore", i, e), dataHolder.m634c("formattedScore", i, e), dataHolder.m635d("newBest", i, e)), dataHolder.m633b("timeSpan", i, e));
            }
            i++;
        }
    }

    private void m146a(Result result, int i) {
        this.eD.put(Integer.valueOf(i), result);
    }

    public String getLeaderboardId() {
        return this.eC;
    }

    public String getPlayerId() {
        return this.dx;
    }

    public Result getScoreResult(int timeSpan) {
        return (Result) this.eD.get(Integer.valueOf(timeSpan));
    }

    public int getStatusCode() {
        return this.f40p;
    }

    public String toString() {
        C0157a a = C0158r.m510c(this).m508a("PlayerId", this.dx).m508a("StatusCode", Integer.valueOf(this.f40p));
        for (int i = 0; i < 3; i++) {
            Result result = (Result) this.eD.get(Integer.valueOf(i));
            a.m508a("TimesSpan", bd.m334G(i));
            a.m508a("Result", result == null ? "null" : result.toString());
        }
        return a.toString();
    }
}

package com.jwetherell.augmented_reality.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import com.jwetherell.augmented_reality.camera.CameraModel;
import com.jwetherell.augmented_reality.common.Calculator;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.data.ScreenPosition;
import com.jwetherell.augmented_reality.ui.objects.PaintableCircle;
import com.jwetherell.augmented_reality.ui.objects.PaintableLine;
import com.jwetherell.augmented_reality.ui.objects.PaintablePosition;
import com.jwetherell.augmented_reality.ui.objects.PaintableRadarPoints;
import com.jwetherell.augmented_reality.ui.objects.PaintableText;
import org.apache.commons.lang3.StringUtils;

public class Radar {
    private static final int LINE_COLOR;
    private static final float PAD_X = 10.0f;
    private static final float PAD_Y = 20.0f;
    private static final int RADAR_COLOR;
    public static final float RADIUS = 48.0f;
    private static final int TEXT_COLOR;
    private static final int TEXT_SIZE = 12;
    private static PaintablePosition circleContainer;
    private static PaintablePosition leftLineContainer;
    private static ScreenPosition leftRadarLine;
    private static PaintableText paintableText;
    private static PaintablePosition paintedContainer;
    private static PaintablePosition pointsContainer;
    private static PaintableRadarPoints radarPoints;
    private static PaintablePosition rightLineContainer;
    private static ScreenPosition rightRadarLine;

    static {
        LINE_COLOR = Color.argb(150, TEXT_COLOR, TEXT_COLOR, 220);
        RADAR_COLOR = Color.argb(100, TEXT_COLOR, TEXT_COLOR, 200);
        TEXT_COLOR = Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
        leftRadarLine = null;
        rightRadarLine = null;
        leftLineContainer = null;
        rightLineContainer = null;
        circleContainer = null;
        radarPoints = null;
        pointsContainer = null;
        paintableText = null;
        paintedContainer = null;
    }

    public Radar() {
        if (leftRadarLine == null) {
            leftRadarLine = new ScreenPosition();
        }
        if (rightRadarLine == null) {
            rightRadarLine = new ScreenPosition();
        }
    }

    public void draw(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        Calculator.calcPitchBearing(ARData.getRotationMatrix());
        ARData.setAzimuth(Calculator.getAzimuth());
        if (AugmentedReality.portrait) {
            canvas.save();
            canvas.translate(5.0f, (float) (canvas.getHeight() - 5));
            canvas.rotate(-90.0f);
        }
        drawRadarCircle(canvas);
        drawRadarPoints(canvas);
        drawRadarLines(canvas);
        drawRadarText(canvas);
        if (AugmentedReality.portrait) {
            canvas.restore();
        }
    }

    private void drawRadarCircle(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        if (circleContainer == null) {
            circleContainer = new PaintablePosition(new PaintableCircle(RADAR_COLOR, RADIUS, true), 58.0f, 68.0f, 0.0f, AugmentedReality.ONE_PERCENT);
        }
        circleContainer.paint(canvas);
    }

    private void drawRadarPoints(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        if (radarPoints == null) {
            radarPoints = new PaintableRadarPoints();
        }
        if (pointsContainer == null) {
            pointsContainer = new PaintablePosition(radarPoints, PAD_X, PAD_Y, -ARData.getAzimuth(), AugmentedReality.ONE_PERCENT);
        } else {
            pointsContainer.set(radarPoints, PAD_X, PAD_Y, -ARData.getAzimuth(), AugmentedReality.ONE_PERCENT);
        }
        pointsContainer.paint(canvas);
    }

    private void drawRadarLines(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        if (leftLineContainer == null) {
            leftRadarLine.set(0.0f, -48.0f);
            leftRadarLine.rotate((-CameraModel.DEFAULT_VIEW_ANGLE) / 2.0f);
            leftRadarLine.add(58.0f, 68.0f);
            leftLineContainer = new PaintablePosition(new PaintableLine(LINE_COLOR, leftRadarLine.getX() - 58.0f, leftRadarLine.getY() - 68.0f), 58.0f, 68.0f, 0.0f, AugmentedReality.ONE_PERCENT);
        }
        leftLineContainer.paint(canvas);
        if (rightLineContainer == null) {
            rightRadarLine.set(0.0f, -48.0f);
            rightRadarLine.rotate(CameraModel.DEFAULT_VIEW_ANGLE / 2.0f);
            rightRadarLine.add(58.0f, 68.0f);
            rightLineContainer = new PaintablePosition(new PaintableLine(LINE_COLOR, rightRadarLine.getX() - 58.0f, rightRadarLine.getY() - 68.0f), 58.0f, 68.0f, 0.0f, AugmentedReality.ONE_PERCENT);
        }
        rightLineContainer.paint(canvas);
    }

    private void drawRadarText(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        int range = (int) (ARData.getAzimuth() / 22.5f);
        String dirTxt = StringUtils.EMPTY;
        if (range == 15 || range == 0) {
            dirTxt = "N";
        } else if (range == 1 || range == 2) {
            dirTxt = "NE";
        } else if (range == 3 || range == 4) {
            dirTxt = "E";
        } else if (range == 5 || range == 6) {
            dirTxt = "SE";
        } else if (range == 7 || range == 8) {
            dirTxt = "S";
        } else if (range == 9 || range == 10) {
            dirTxt = "SW";
        } else if (range == 11 || range == TEXT_SIZE) {
            dirTxt = "W";
        } else if (range == 13 || range == 14) {
            dirTxt = "NW";
        }
        radarText(canvas, ((int) ARData.getAzimuth()) + '\u00b0' + " " + dirTxt, 58.0f, 15.0f, true);
        radarText(canvas, formatDist(ARData.getRadius() * 1000.0f), 58.0f, 106.0f, false);
    }

    private void radarText(Canvas canvas, String txt, float x, float y, boolean bg) {
        if (canvas == null || txt == null) {
            throw new NullPointerException();
        }
        if (paintableText == null) {
            paintableText = new PaintableText(txt, TEXT_COLOR, TEXT_SIZE, bg);
        } else {
            paintableText.set(txt, TEXT_COLOR, TEXT_SIZE, bg);
        }
        if (paintedContainer == null) {
            paintedContainer = new PaintablePosition(paintableText, x, y, 0.0f, AugmentedReality.ONE_PERCENT);
        } else {
            paintedContainer.set(paintableText, x, y, 0.0f, AugmentedReality.ONE_PERCENT);
        }
        paintedContainer.paint(canvas);
    }

    private static String formatDist(float meters) {
        if (meters < 1000.0f) {
            return ((int) meters) + "m";
        }
        if (meters < 10000.0f) {
            return new StringBuilder(String.valueOf(formatDec(meters / 1000.0f, 1))).append("km").toString();
        }
        return new StringBuilder(String.valueOf((int) (meters / 1000.0f))).append("km").toString();
    }

    private static String formatDec(float val, int dec) {
        int factor = (int) Math.pow(10.0d, (double) dec);
        return ((int) val) + "." + (((int) Math.abs(((float) factor) * val)) % factor);
    }
}

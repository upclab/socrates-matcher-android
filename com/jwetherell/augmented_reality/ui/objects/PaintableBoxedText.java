package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import java.text.BreakIterator;
import java.util.ArrayList;

public class PaintableBoxedText extends PaintableObject {
    private float areaHeight;
    private float areaWidth;
    private int backgroundColor;
    private int borderColor;
    private float fontSize;
    private float height;
    private float lineHeight;
    private ArrayList<String> lineList;
    private float[] lineWidths;
    private String[] lines;
    private float maxLineWidth;
    private float pad;
    private int textColor;
    private String txt;
    private float width;

    public PaintableBoxedText(String txtInit, float fontSizeInit, float maxWidth) {
        this(txtInit, fontSizeInit, maxWidth, Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK), Color.argb(AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER, 0, 0, 0), Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK));
    }

    public PaintableBoxedText(String txtInit, float fontSizeInit, float maxWidth, int borderColor, int bgColor, int textColor) {
        this.width = 0.0f;
        this.height = 0.0f;
        this.areaWidth = 0.0f;
        this.areaHeight = 0.0f;
        this.lineList = null;
        this.lines = null;
        this.lineWidths = null;
        this.lineHeight = 0.0f;
        this.maxLineWidth = 0.0f;
        this.pad = 0.0f;
        this.txt = null;
        this.fontSize = 12.0f;
        this.borderColor = Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
        this.backgroundColor = Color.argb(160, 0, 0, 0);
        this.textColor = Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
        set(txtInit, fontSizeInit, maxWidth, borderColor, bgColor, textColor);
    }

    public void set(String txtInit, float fontSizeInit, float maxWidth, int borderColor, int bgColor, int textColor) {
        if (txtInit == null) {
            throw new NullPointerException();
        }
        this.borderColor = borderColor;
        this.backgroundColor = bgColor;
        this.textColor = textColor;
        this.pad = getTextAsc();
        set(txtInit, fontSizeInit, maxWidth);
    }

    public void set(String txtInit, float fontSizeInit, float maxWidth) {
        if (txtInit == null) {
            throw new NullPointerException();
        }
        try {
            prepTxt(txtInit, fontSizeInit, maxWidth);
        } catch (Exception ex) {
            ex.printStackTrace();
            prepTxt("TEXT PARSE ERROR", 12.0f, 200.0f);
        }
    }

    private void prepTxt(String txtInit, float fontSizeInit, float maxWidth) {
        if (txtInit == null) {
            throw new NullPointerException();
        }
        setFontSize(fontSizeInit);
        this.txt = txtInit;
        this.fontSize = fontSizeInit;
        this.areaWidth = maxWidth - this.pad;
        this.lineHeight = getTextAsc() + getTextDesc();
        if (this.lineList == null) {
            this.lineList = new ArrayList();
        } else {
            this.lineList.clear();
        }
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(this.txt);
        int start = boundary.first();
        int prevEnd = start;
        for (int end = boundary.next(); end != -1; end = boundary.next()) {
            String line = this.txt.substring(start, end);
            String prevLine = this.txt.substring(start, prevEnd);
            if (getTextWidth(line) > this.areaWidth) {
                if (prevLine.length() > 0) {
                    this.lineList.add(prevLine);
                }
                start = prevEnd;
            }
            prevEnd = end;
        }
        this.lineList.add(this.txt.substring(start, prevEnd));
        if (this.lines == null || this.lines.length != this.lineList.size()) {
            this.lines = new String[this.lineList.size()];
        }
        if (this.lineWidths == null || this.lineWidths.length != this.lineList.size()) {
            this.lineWidths = new float[this.lineList.size()];
        }
        this.lineList.toArray(this.lines);
        this.maxLineWidth = 0.0f;
        for (int i = 0; i < this.lines.length; i++) {
            this.lineWidths[i] = getTextWidth(this.lines[i]);
            if (this.maxLineWidth < this.lineWidths[i]) {
                this.maxLineWidth = this.lineWidths[i];
            }
        }
        this.areaWidth = this.maxLineWidth;
        this.areaHeight = this.lineHeight * ((float) this.lines.length);
        this.width = this.areaWidth + (this.pad * 2.0f);
        this.height = this.areaHeight + (this.pad * 2.0f);
    }

    public void paint(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        setFontSize(this.fontSize);
        setFill(true);
        setColor(this.backgroundColor);
        paintRoundedRect(canvas, 0.0f, 0.0f, this.width, this.height);
        setFill(false);
        setColor(this.borderColor);
        paintRoundedRect(canvas, 0.0f, 0.0f, this.width, this.height);
        for (int i = 0; i < this.lines.length; i++) {
            String line = this.lines[i];
            setFill(true);
            setStrokeWidth(0.0f);
            setColor(this.textColor);
            paintText(canvas, this.pad, (this.pad + (this.lineHeight * ((float) i))) + getTextAsc(), line);
        }
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}

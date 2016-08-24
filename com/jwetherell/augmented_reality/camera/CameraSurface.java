package com.jwetherell.augmented_reality.camera;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CameraSurface extends SurfaceView implements Callback {
    private static Camera camera;
    private static SurfaceHolder holder;

    static {
        holder = null;
        camera = null;
    }

    public CameraSurface(Context context) {
        super(context);
        try {
            holder = getHolder();
            holder.addCallback(this);
            holder.setType(3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (camera != null) {
                try {
                    camera.stopPreview();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try {
                    camera.release();
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
                camera = null;
            }
            camera = Camera.open();
            camera.setPreviewDisplay(holder);
        } catch (Exception ex22) {
            try {
                if (camera != null) {
                    try {
                        camera.stopPreview();
                    } catch (Exception e) {
                        ex22.printStackTrace();
                    }
                    try {
                        camera.release();
                    } catch (Exception e2) {
                        ex22.printStackTrace();
                    }
                    camera = null;
                }
            } catch (Exception e3) {
                ex22.printStackTrace();
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            if (camera != null) {
                try {
                    camera.stopPreview();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try {
                    camera.release();
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
                camera = null;
            }
        } catch (Exception ex22) {
            ex22.printStackTrace();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        try {
            Parameters parameters = camera.getParameters();
            try {
                float ff = ((float) w) / ((float) h);
                float bff = 0.0f;
                int bestw = 0;
                int besth = 0;
                for (Size element : CameraCompatibility.getSupportedPreviewSizes(parameters)) {
                    float cff = ((float) element.width) / ((float) element.height);
                    if (ff - cff <= ff - bff && element.width <= w && element.width >= bestw) {
                        bff = cff;
                        bestw = element.width;
                        besth = element.height;
                    }
                }
                if (bestw == 0 || besth == 0) {
                    bestw = 480;
                    besth = 320;
                }
                parameters.setPreviewSize(bestw, besth);
            } catch (Exception e) {
                parameters.setPreviewSize(480, 320);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

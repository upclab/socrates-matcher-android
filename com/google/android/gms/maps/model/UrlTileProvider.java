package com.google.android.gms.maps.model;

import com.google.tagmanager.protobuf.CodedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public abstract class UrlTileProvider implements TileProvider {
    private final int hL;
    private final int hM;

    public UrlTileProvider(int width, int height) {
        this.hL = width;
        this.hM = height;
    }

    private static long m1107a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[CodedOutputStream.DEFAULT_BUFFER_SIZE];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    private static byte[] m1108a(InputStream inputStream) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        m1107a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public final Tile getTile(int x, int y, int zoom) {
        URL tileUrl = getTileUrl(x, y, zoom);
        if (tileUrl == null) {
            return NO_TILE;
        }
        try {
            return new Tile(this.hL, this.hM, m1108a(tileUrl.openStream()));
        } catch (IOException e) {
            return null;
        }
    }

    public abstract URL getTileUrl(int i, int i2, int i3);
}

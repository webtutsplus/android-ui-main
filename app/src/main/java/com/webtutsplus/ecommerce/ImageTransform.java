package com.webtutsplus.ecommerce;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

public class ImageTransform implements com.squareup.picasso.Transformation {
    private final int radius;
    private final int margin;
    private String KEY = "";
    private boolean topCorners = true;
    private boolean bottomCorners = true;

    public ImageTransform(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
        if (KEY.isEmpty()) {
            KEY = "rounded_" + radius + margin;
        }
    }

    public ImageTransform(final int radius, final int margin, boolean topCornersOnly, boolean bottomCornersOnly) {
        this(radius, margin);
        topCorners = topCornersOnly;
        bottomCorners = bottomCornersOnly;
        KEY = "rounded_" + radius + margin + topCorners + bottomCorners;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        if(topCorners && bottomCorners) {
            // Uses native method to draw symmetric rounded corners
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin,
                    source.getHeight() - margin), radius, radius, paint);
        } else {
            // Uses custom path to generate rounded corner individually
            canvas.drawPath(RoundedRect(margin, margin, source.getWidth() - margin,
                    source.getHeight() - margin, radius, radius, topCorners, topCorners,
                    bottomCorners, bottomCorners), paint);
        }


        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return KEY;
    }

    public static Path RoundedRect(float leftX, float topY, float rightX, float bottomY, float rx,
                                   float ry, boolean topLeft, boolean topRight, boolean
                                           bottomRight, boolean bottomLeft) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = rightX - leftX;
        float height = bottomY - topY;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(rightX, topY + ry);
        if (topRight)
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        else{
            path.rLineTo(0, -ry);
            path.rLineTo(-rx,0);
        }
        path.rLineTo(-widthMinusCorners, 0);
        if (topLeft)
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        else{
            path.rLineTo(-rx, 0);
            path.rLineTo(0,ry);
        }
        path.rLineTo(0, heightMinusCorners);

        if (bottomLeft)
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
        else{
            path.rLineTo(0, ry);
            path.rLineTo(rx,0);
        }

        path.rLineTo(widthMinusCorners, 0);
        if (bottomRight)
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        else{
            path.rLineTo(rx,0);
            path.rLineTo(0, -ry);
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

        return path;
    }
}

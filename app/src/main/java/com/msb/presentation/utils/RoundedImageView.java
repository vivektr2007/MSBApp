package com.msb.presentation.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import com.msb.R;


/**
 * The type Rounded image view.
 */
public class RoundedImageView extends androidx.appcompat.widget.AppCompatImageView {

    /**
     * The constant TAG.
     */
    public static final String TAG = "RoundedImageView";
    /**
     * The constant DEFAULT_RADIUS.
     */
    public static final int DEFAULT_RADIUS = 0;
    /**
     * The constant DEFAULT_BORDER_WIDTH.
     */
    public static final int DEFAULT_BORDER_WIDTH = 0;
    private static final ScaleType[] SCALE_TYPES = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };

    private int mCornerRadius = DEFAULT_RADIUS;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;
    private ColorStateList mBorderColor =
            ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
    private boolean mOval = false;
    private boolean mRoundBackground = false;

    private int mResource;
    private Drawable mDrawable;
    private Drawable mBackgroundDrawable;

    private ScaleType mScaleType;

    /**
     * Instantiates a new Rounded image view.
     *
     * @param context the context
     */
    public RoundedImageView(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Rounded image view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public RoundedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new Rounded image view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        try{
            TypedArray a = context.obtainStyledAttributes(attrs,  R.styleable.RoundedImageView, defStyle, 0);


            int index = a.getInt(R.styleable.RoundedImageView_android_scaleType, 1);
            if (index >= 0) {
                setScaleType(SCALE_TYPES[index]);
            } else {
                // default scaletype to FIT_CENTER
                setScaleType(ScaleType.FIT_CENTER);
            }

            mCornerRadius = a.getDimensionPixelSize(R.styleable.RoundedImageView_corner_radius, 360);
            mBorderWidth = a.getDimensionPixelSize(R.styleable.RoundedImageView_border_width, 2);

            // don't allow negative values for radius and border
            if (mCornerRadius < 0) {
                mCornerRadius = DEFAULT_RADIUS;
            }
            if (mBorderWidth < 0) {
                mBorderWidth = DEFAULT_BORDER_WIDTH;
            }

            mBorderColor = a.getColorStateList(R.styleable.RoundedImageView_border_color);
            if (mBorderColor == null) {
                mBorderColor = ColorStateList.valueOf(Color.TRANSPARENT);
            }

            mRoundBackground = a.getBoolean(R.styleable.RoundedImageView_round_background, false);
            mOval = a.getBoolean(R.styleable.RoundedImageView_is_oval, false);

            updateDrawableAttrs();
            updateBackgroundDrawableAttrs();

            a.recycle();
        }catch (Throwable e){
            e.printStackTrace();
        }

    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    /**
     * Return the current scale type in use by this ImageView.
     *
     * @attr ref android.R.styleable#ImageView_scaleType
     * @see ScaleType
     */
    @Override
    public ScaleType getScaleType() {
        return mScaleType;
    }

    /**
     * Controls how the image should be resized or moved to match the size
     * of this ImageView.
     *
     * @param scaleType The desired scaling mode.
     * @attr ref android.R.styleable#ImageView_scaleType
     */
    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }

        if (mScaleType != scaleType) {
            mScaleType = scaleType;

            switch (scaleType) {
                case CENTER:
                case CENTER_CROP:
                case CENTER_INSIDE:
                case FIT_CENTER:
                case FIT_START:
                case FIT_END:
                case FIT_XY:
                    super.setScaleType(ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }

            updateDrawableAttrs();
            updateBackgroundDrawableAttrs();
            invalidate();
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        try{
            mResource = 0;
            mDrawable = RoundedDrawable.fromDrawable(drawable);
            updateDrawableAttrs();
            super.setImageDrawable(mDrawable);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        try{
            mResource = 0;
            mDrawable = RoundedDrawable.fromBitmap(bm);
            updateDrawableAttrs();
            super.setImageDrawable(mDrawable);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void setImageResource(int resId) {
        try{
            if (mResource != resId) {
                mResource = resId;
                mDrawable = resolveResource();
                updateDrawableAttrs();
                super.setImageDrawable(mDrawable);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        try{
            super.setImageURI(uri);
            setImageDrawable(getDrawable());
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    private Drawable resolveResource() {
        try {
            Resources rsrc = getResources();
            if (rsrc == null) {
                return null;
            }

            Drawable d = null;

            if (mResource != 0) {
                try {
                    d = rsrc.getDrawable(mResource);
                } catch (Exception e) {
                    Log.w(TAG, "Unable to find resource: " + mResource, e);
                    // Don't try again.
                    mResource = 0;
                }
            }
            return RoundedDrawable.fromDrawable(d);
        }catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    private void updateDrawableAttrs() {
        updateAttrs(mDrawable, false);
    }

    private void updateBackgroundDrawableAttrs() {
        updateAttrs(mBackgroundDrawable, true);
    }

    private void updateAttrs(Drawable drawable, boolean background) {
        if (drawable == null) {
            return;
        }

        if (drawable instanceof RoundedDrawable) {
            ((RoundedDrawable) drawable)
                    .setScaleType(mScaleType)
                    .setCornerRadius(background && !mRoundBackground ? 0 : mCornerRadius)
                    .setBorderWidth(background && !mRoundBackground ? 0 : mBorderWidth)
                    .setBorderColors(mBorderColor)
                    .setOval(mOval);
        } else if (drawable instanceof LayerDrawable) {
            // loop through layers to and set drawable attrs
            LayerDrawable ld = ((LayerDrawable) drawable);
            int layers = ld.getNumberOfLayers();
            for (int i = 0; i < layers; i++) {
                updateAttrs(ld.getDrawable(i), background);
            }
        }
    }

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        mBackgroundDrawable = RoundedDrawable.fromDrawable(background);
        updateBackgroundDrawableAttrs();
        super.setBackgroundDrawable(mBackgroundDrawable);
    }

    /**
     * Gets corner radius.
     *
     * @return the corner radius
     */
    public int getCornerRadius() {
        return mCornerRadius;
    }

    /**
     * Sets corner radius.
     *
     * @param radius the radius
     */
    public void setCornerRadius(int radius) {
        if (mCornerRadius == radius) {
            return;
        }

        mCornerRadius = radius;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs();
    }

    /**
     * Gets border width.
     *
     * @return the border width
     */
    public int getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * Sets border width.
     *
     * @param width the width
     */
    public void setBorderWidth(int width) {
        if (mBorderWidth == width) {
            return;
        }

        mBorderWidth = width;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs();
        invalidate();
    }

    /**
     * Gets border color.
     *
     * @return the border color
     */
    public int getBorderColor() {
        return mBorderColor.getDefaultColor();
    }

    /**
     * Sets border color.
     *
     * @param color the color
     */
    public void setBorderColor(int color) {
        setBorderColors(ColorStateList.valueOf(color));
    }

    /**
     * Gets border colors.
     *
     * @return the border colors
     */
    public ColorStateList getBorderColors() {
        return mBorderColor;
    }

    /**
     * Sets border colors.
     *
     * @param colors the colors
     */
    public void setBorderColors(ColorStateList colors) {
        if (mBorderColor.equals(colors)) {
            return;
        }

        mBorderColor =
                (colors != null) ? colors : ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs();
        if (mBorderWidth > 0) {
            invalidate();
        }
    }

    /**
     * Is oval boolean.
     *
     * @return the boolean
     */
    public boolean isOval() {
        return mOval;
    }

    /**
     * Sets oval.
     *
     * @param oval the oval
     */
    public void setOval(boolean oval) {
        mOval = oval;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs();
        invalidate();
    }

    /**
     * Is round background boolean.
     *
     * @return the boolean
     */
    public boolean isRoundBackground() {
        return mRoundBackground;
    }

    /**
     * Sets round background.
     *
     * @param roundBackground the round background
     */
    public void setRoundBackground(boolean roundBackground) {
        if (mRoundBackground == roundBackground) {
            return;
        }

        mRoundBackground = roundBackground;
        updateBackgroundDrawableAttrs();
        invalidate();
    }
}

package com.msb.presentation.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.msb.R;


/**
 * Created by teknol
 */
public class CircleTextView extends androidx.appcompat.widget.AppCompatTextView {
    private float strokeWidth=0;
    /**
     * The Circle paint.
     */
    Paint circlePaint;
    /**
     * The Stroke color.
     */
    int strokeColor=-1, /**
     * The Solid color.
     */
    solidColor;
    /**
     * The Text.
     */
    String Text;
    /**
     * The Size.
     */
    Float size = -1f;


    /**
     * Instantiates a new Smart circle text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public CircleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CircleModel.setContext(context);
        initViews(context, attrs);
        setWillNotDraw(false);
    }

    /**
     * Instantiates a new Smart circle text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        CircleModel.setContext(context);
        initViews(context, attrs);
        setWillNotDraw(false);
    }

    /**
     * Instantiates a new Smart circle text view.
     *
     * @param context the context
     */
    public CircleTextView(Context context) {
        super(context);
        CircleModel.setContext(context);
        initViews(context,null);
        setWillNotDraw(false);
    }


    private void initViews(Context context, AttributeSet attrs) {

        //paint object for drawing in onDraw
        circlePaint = new Paint();
        strokeColor = getResources().getColor(R.color.divider);
        strokeWidth = getResources().getDimensionPixelSize(R.dimen._1dp);
    }

    @Override
    public void draw(Canvas canvas) {
        final int diameter, radius, h, w;

        circlePaint.setColor(solidColor);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Paint strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        //get Height and Width
        h = this.getHeight();
        w = this.getWidth();

        diameter = ((h > w) ? h : w);
        radius = diameter / 2;

        //setting Height and width
        this.setHeight(diameter);
        this.setWidth(diameter);
        this.setText(Text);

        if (size != -1f) {

            this.setTextSize(size);
        } else {

            this.setTextSize(diameter / 5);
        }

        if(strokeColor!=-1){
        }

        canvas.drawCircle(diameter / 2, diameter / 2, radius , strokePaint);
        canvas.drawCircle(diameter / 2, diameter / 2, radius - strokeWidth, circlePaint);


        super.draw(canvas);
    }

    /**
     * Sets stroke width.
     *
     * @param dp the dp
     */
    public void setStrokeWidth(float dp) {
        this.strokeWidth = dp;
//        float scale = getContext().getResources().getDisplayMetrics().density;
//        this.strokeWidth = dp * scale;

    }

    /**
     * Sets stroke color.
     *
     * @param color the color
     */
    public void setStrokeColor(int color) {
        this.strokeColor = color;
    }


    /**
     * Sets solid color.
     *
     * @param pos the pos
     */
    public void setSolidColor(int pos) {
        this.solidColor = CircleUtils.genColorFromAlphabet(Text);
    }

    /**
     * Sets solid color.
     */
    public void setSolidColor() {
        this.solidColor = CircleUtils.genColorFromAlphabet(Text);
    }

    /**
     * Sets custom text.
     *
     * @param value the value
     */
    public void setCustomText(String value) {
        this.Text = String.valueOf(value.charAt(0)).toUpperCase();
    }

    /**
     * Sets custom text.
     *
     * @param value1 the value 1
     * @param value2 the value 2
     */
    public void setCustomText(String value1,String value2) {

        try{
            this.Text = String.valueOf(value1.charAt(0)).toUpperCase()+String.valueOf(value2.charAt(0)).toUpperCase();
        }catch (Exception e){
            try{
                setCustomText(value1);
            }catch (Exception ee){
                try{
                    setCustomText(value2);
                }catch (Exception eee){

                }
            }

        }

    }


    /**
     * Sets custom text size.
     *
     * @param value the value
     */
    public void setCustomTextSize(float value) {

        this.size = value;
    }

}
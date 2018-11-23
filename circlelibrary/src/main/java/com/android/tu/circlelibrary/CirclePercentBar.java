package com.android.tu.circlelibrary;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tjy on 2017/7/25.
 */
public class CirclePercentBar extends View{

    private Context mContext;

    private int mArcColor;
    private int mArcWidth;
    private int mCenterTextColor;
    private int mCenterTextSize;
    private int mCircleRadius;
    private Paint arcPaint;
    private Paint arcCirclePaint;
    private Paint centerTextPaint;
    private RectF arcRectF;
    private Rect textBoundRect;
    private float mCurData=0;
    private String mSuffix="%";
    private int arcStartColor;
    private int arcEndColor;
    private Paint startCirclePaint;

    public CirclePercentBar(Context context) {
        this(context, null);
    }

    public CirclePercentBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.CirclePercentBar,defStyleAttr,0);
        mArcColor = typedArray.getColor(R.styleable.CirclePercentBar_arcColor,0xff0000);
        mArcWidth = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_arcWidth, DisplayUtil.dp2px(context, 20));
        mCenterTextColor = typedArray.getColor(R.styleable.CirclePercentBar_centerTextColor, 0x0000ff);
        mCenterTextSize = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_centerTextSize, DisplayUtil.dp2px(context, 20));
        mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_circleRadius, DisplayUtil.dp2px(context, 100));
        arcStartColor = typedArray.getColor(R.styleable.CirclePercentBar_arcStartColor,
                ContextCompat.getColor(mContext, R.color.colorStart));
        arcEndColor = typedArray.getColor(R.styleable.CirclePercentBar_arcEndColor,
                ContextCompat.getColor(mContext, R.color.colorEnd));

        typedArray.recycle();

        initPaint();

    }

    private void initPaint() {

        startCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        startCirclePaint.setStyle(Paint.Style.FILL);
        //startCirclePaint.setStrokeWidth(mArcWidth);
        startCirclePaint.setColor(arcStartColor);

        arcCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcCirclePaint.setStyle(Paint.Style.STROKE);
        arcCirclePaint.setStrokeWidth(mArcWidth);
        arcCirclePaint.setColor(ContextCompat.getColor(mContext,R.color.colorCirclebg));
        arcCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(mArcWidth);
        arcPaint.setColor(mArcColor);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setStyle(Paint.Style.STROKE);
        centerTextPaint.setColor(mCenterTextColor);
        centerTextPaint.setTextSize(mCenterTextSize);

        //圓弧的外接矩形
        arcRectF = new RectF();

        //文字的边界矩形
        textBoundRect = new Rect();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureDimension(widthMeasureSpec),measureDimension(heightMeasureSpec));
    }

    private int measureDimension(int measureSpec) {
        int result;
        int specMode=MeasureSpec.getMode(measureSpec);
        int specSize=MeasureSpec.getSize(measureSpec);
        if(specMode==MeasureSpec.EXACTLY){
            result=specSize;
        }else{
            result=mCircleRadius*2;
            if(specMode==MeasureSpec.AT_MOST){
                result=Math.min(result,specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.rotate(-90, getWidth()/ 2, getHeight()/ 2);

        arcRectF.set(getWidth()/2-mCircleRadius+mArcWidth/2,getHeight()/2-mCircleRadius+mArcWidth/2
                ,getWidth()/2+mCircleRadius-mArcWidth/2,getHeight()/2+mCircleRadius-mArcWidth/2);


        canvas.drawArc(arcRectF, 0,360,false,arcCirclePaint);

        arcPaint.setShader(new SweepGradient(getWidth()/2,getHeight()/2,arcStartColor,arcEndColor));
        canvas.drawArc(arcRectF, 0,360* mCurData /100,false,arcPaint);

        canvas.rotate(90, getWidth()/ 2, getHeight()/ 2);
        canvas.drawCircle(getWidth()/2,getHeight()/2-mCircleRadius+mArcWidth/2,mArcWidth/2,startCirclePaint);

        String data= String.valueOf(mCurData) + mSuffix;
        centerTextPaint.getTextBounds(data,0,data.length(),textBoundRect);
        canvas.drawText(data,getWidth()/2-textBoundRect.width()/2,getHeight()/2+textBoundRect.height()/2,centerTextPaint);

    }

    public void setPercentData(float data, String suffix, TimeInterpolator interpolator){
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(mCurData,data);
        valueAnimator.setDuration((long) (Math.abs(mCurData-data)*30));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value= (float) valueAnimator.getAnimatedValue();
                mCurData=(float)(Math.round(value*10))/10;
                invalidate();
            }
        });
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.start();
    }
}

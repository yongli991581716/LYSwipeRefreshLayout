package com.ly.library.view.footer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ly.library.R;

/**
 * 默认footer装饰器
 *
 * @author liyong
 * @date 2017/6/21 14:18
 */
public class DefaultFooterView extends BaseFooterView {

    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 扇形区域
     */
    private RectF oval;
    /**
     * 转动基准点
     */
    private int mProgress = 30;//圆圈比例

    /**
     * 圆圈半径
     */
    private int mCircleRadius;

    public DefaultFooterView(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
        mContent = context;
        paint = new Paint();
        oval = new RectF();
        //设置加载内容
        setFooterDesc(mContent.getString(R.string.loading));
        //总高度
        mFooterHeight = mContent.getResources().getDimensionPixelSize(R.dimen.footer_height);

        //绘制区域占总高度一半，半径为绘制区域1/2
        mCircleRadius = mFooterHeight / 4;
    }

    @Override
    protected void onDrawFooter(Canvas c, RecyclerView recyclerView) {
        super.onDrawFooter(c, recyclerView);

        //每重绘一次基准+5
        mProgress = mProgress + 5;
        if (mProgress == 100) {
            mProgress = 0;
        }

        final int left = recyclerView.getPaddingLeft();
        final int right = recyclerView.getMeasuredWidth() - recyclerView.getPaddingRight();
        final int childSize = recyclerView.getChildCount();
        final View child = recyclerView.getChildAt(childSize - 1);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        final int top = child.getBottom() + layoutParams.bottomMargin;
        final int bottom = top + getHeight() / 2;

        //设置
        int size = mContent.getResources().getDimensionPixelSize(R.dimen.text_smll_size);
        paint.setStyle(Paint.Style.FILL);// 设置填充
        paint.setAntiAlias(true);// 抗锯齿
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);// 增强消除锯齿
        paint.setStrokeWidth(2);// 再次设置画笔的宽度
        paint.setTextSize(size);// 设置文字的大小
        paint.setColor(ContextCompat.getColor(mContent, R.color.black));// 设置画笔颜色

        float textWidth = paint.measureText(mFooterDesc);
        //让圆圈和文字位于水平中间
        float centerX = (right - left) / 2-(mCircleRadius*2+textWidth)/2+mCircleRadius;
        //两边都偏移10像素，保持有间隔
        float offsetX = 10;
        c.drawText(mFooterDesc, centerX+mCircleRadius+offsetX, bottom + 10, paint);

        //画笔初始化
        paint.reset();// 将画笔重置
        paint.setStyle(Paint.Style.FILL);// 设置填充
        paint.setAntiAlias(true);// 抗锯齿
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);// 增强消除锯齿
        paint.setColor(Color.GRAY);// 画笔为灰色
        paint.setStrokeWidth(10);// 画笔宽度
        paint.setStyle(Paint.Style.STROKE);// 中空
        //绘制圈圈
        c.drawCircle(centerX-offsetX, bottom, mCircleRadius, paint);//在中心为（(right - left)/2,bottom）的地方画个半径为mCircleSize的圆，

        //设置转动动画画笔参数
        paint.setColor(ContextCompat.getColor(mContent, R.color.colorAccent));// 设置画笔次主色
        oval.set(centerX-offsetX - mCircleRadius, bottom - mCircleRadius, centerX-offsetX + mCircleRadius, bottom + mCircleRadius);// 在Circle小于圈圈大小的地方画圆，这样也就保证了半径为mCircleSize
        c.drawArc(oval, -90, ((float) mProgress / 100) * 360, false, paint);// 圆弧，第二个参数为：起始角度，第三个为跨的角度，第四个为true的时候是实心，false的时候为空心
    }
}

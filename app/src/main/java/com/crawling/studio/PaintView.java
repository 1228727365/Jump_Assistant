package com.crawling.studio;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class PaintView extends View {
	private static List<Point> mAllPoints;
	private ValueAnimator mAnimator;
	private float mProgress;
	
	public static float 长按时间;
	
	public PaintView(Context context) {
		super(context);
		init();
	}
	
	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		mAllPoints = new ArrayList<>();
		String 颜色 = "#33000000";
		if (homeActivity.窗口透明度 == 100) {
			颜色 = "#00000000";
		} else if (homeActivity.窗口透明度 == 90) {
			颜色 = "#1A000000";
		} else if (homeActivity.窗口透明度 == 80) {
			颜色 = "#33000000";
		} else if (homeActivity.窗口透明度 == 70) {
			颜色 = "#4D000000";
		} else if (homeActivity.窗口透明度 == 60) {
			颜色 = "#66000000";
		} else if (homeActivity.窗口透明度 == 50) {
			颜色 = "#80000000";
		} else if (homeActivity.窗口透明度 == 40) {
			颜色 = "#99000000";
		} else if (homeActivity.窗口透明度 == 30) {
			颜色 = "#B3000000";
		} else if (homeActivity.窗口透明度 == 20) {
			颜色 = "#CC000000";
		}
		setBackgroundColor(Color.parseColor(颜色));
		setOnTouchListener(new OnTouchListenerImpl());
		
		mAnimator = ValueAnimator.ofFloat(0f, 1f);
		mAnimator.setDuration(500);
		mAnimator.addUpdateListener(new AnimatorUpdateListenerImpl());
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		paint.setStrokeWidth(10);
		Log.w(TAG, String.valueOf(homeActivity.线条颜色));
		if (homeActivity.绘制隐藏) {
			paint.setColor(Color.parseColor("#00000000"));
		} else {
			paint.setColor(homeActivity.线条颜色);
		}
		// 绘制画线轨迹
		for (int i = 0; i < mAllPoints.size() - 1; i++) {
			Point startPoint = mAllPoints.get(i);
			Point endPoint = mAllPoints.get(i + 1);
			canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);
		}
		
		// 绘制黑色圆点
		if (!mAllPoints.isEmpty()) {
			if (homeActivity.绘制隐藏) {
				paint.setColor(Color.parseColor("#00000000"));
			} else {
				paint.setColor(homeActivity.圆头颜色);
			}
			paint.setStyle(Paint.Style.FILL);
			
			// 绘制起点圆点
			Point startPoint = mAllPoints.get(0);
			canvas.drawCircle(startPoint.x, startPoint.y, 15, paint);
			
			// 绘制终点圆点
			Point endPoint = mAllPoints.get(mAllPoints.size() - 1);
			canvas.drawCircle(endPoint.x, endPoint.y, 15, paint);
		}
	}
	
	
	private class OnTouchListenerImpl implements OnTouchListener {
		private Point startPoint;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mAllPoints.clear();
					startPoint = new Point((int) event.getX(), (int) event.getY());
					break;
				case MotionEvent.ACTION_MOVE:
					Point endPoint = new Point((int) event.getX(), (int) event.getY());
					mAllPoints.clear();
					mAllPoints.add(startPoint);
					mAllPoints.add(endPoint);
					invalidate();
					break;
				case MotionEvent.ACTION_UP:
					
					postDelayed(new Runnable() {
						@Override
						public void run() {
							float length = getLineLength();
							float time = Float.parseFloat(homeActivity.跳跃时间);
							Log.d("PaintView", "line length: " + length);
							长按时间 = length / time;
							Log.w("\\\\\\", String.valueOf(长按时间));
							if (String.valueOf(length).contains("0.0")) {
								Log.d("PaintView", "距离太短: " + length);
							} else {
								if (!homeActivity.分布操作) {
									if (HongBaoService.isStart()) {
										if (homeActivity.坐标X != null && homeActivity.坐标Y != null) {
											HongBaoService.mService.dispatchGestureLongClick(Integer.parseInt(homeActivity.坐标X), Integer.parseInt(homeActivity.坐标Y), 长按时间);
										} else {
											Toasty.info("未设置点击坐标.");
										}
									}
									mAllPoints.clear();
									invalidate();
								}
							}
						}
					}, 50);
					break;
				default:
					break;
			}
			return true;
		}
	}
	
	private class AnimatorUpdateListenerImpl implements ValueAnimator.AnimatorUpdateListener {
		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			mProgress = (float) animation.getAnimatedValue();
			invalidate();
		}
	}
	
	/**
	 * 计算画线长度
	 */
	public float getLineLength() {
		return calculateLineLength();
		
	}
	
	public static void 清除绘制(View view) {
		mAllPoints.clear();
		view.invalidate();
	}
	
	
	private float calculateLineLength() {
		float totalLength = 0;
		for (int i = 0; i < mAllPoints.size() - 1; i++) {
			Point startPoint = mAllPoints.get(i);
			Point endPoint = mAllPoints.get(i + 1);
			float dx = endPoint.x - startPoint.x;
			float dy = endPoint.y - startPoint.y;
			float length = (float) Math.sqrt(dx * dx + dy * dy);
			totalLength += length;
		}
		return totalLength;
	}
}
package com.crawling.studio;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import com.hjq.window.EasyWindow;
import com.xcolorpicker.android.OnColorSelectListener;
import com.xcolorpicker.android.XColorPicker;

import java.io.*;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;


public class homeActivity extends AppCompatActivity {

    /**
     * 黑色唤醒广播的action
     */
    private final static String BLACK_WAKE_ACTION = "com.wake.black";
    public static int 圆头颜色;
    public static int 线条颜色;
    public static int 窗口透明度;
    private int 窗口高度;
    public static String 坐标X = null;
    public static String 坐标Y = null;

    public static String 跳跃时间;

    public static Boolean 分布操作 = false;
    public static Boolean 绘制隐藏 = false;

    String 颜色 = "#33000000";

    private AppCompatSeekBar seekBar, hzck;
    private EasyWindow mEasyWindow;
    private AlertDialog mAlertDialog;
    private MaterialSwitch v1, v2, v3, v4;
    private static final int REQUEST_ACCESSIBILITY = 1;
    private static final int REQUEST_CODE_FLOATING_WINDOW = 123; // 自定义的请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color));


        // ---------------TODO 开启无障碍，悬浮窗控件--------------------
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        //----------------TODO 判断无障碍权限点击事件---------------
        LinearLayout linearLayout_v1 = (LinearLayout) findViewById(R.id.linearlayout_v1);
        linearLayout_v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!HongBaoService.isStart()) {
                    try {
                        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                    } catch (Exception e) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                        e.printStackTrace();
                    }
                }
            }
        });

        //----------------TODO 判断无障碍权限是否开启---------------
        if (HongBaoService.isStart()) {
            v1.setChecked(true);
        } else {
            v1.setChecked(false);
        }

        //----------------TODO 判断悬浮窗权限点击事件---------------
        LinearLayout linearLayout_v2 = (LinearLayout) findViewById(R.id.linearlayout_v2);
        linearLayout_v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(homeActivity.this)) {
                    mEasyWindow.cancel();
                    logbt();
                    Toasty.success("授权成功");
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + homeActivity.this.getPackageName()));
                    startActivityForResult(intent, REQUEST_CODE_FLOATING_WINDOW);
                }
            }
        });
        // ---------------TODO 判断悬浮窗权限--------------------
        seekBar = findViewById(R.id.colorapl);
        hzck = findViewById(R.id.hzck);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
            v2.setChecked(true);
            logbt();
            seekBar.setEnabled(true);
            hzck.setEnabled(true);
            v3.setEnabled(true);
        } else {
            v2.setChecked(false);
            seekBar.setEnabled(false);
            v3.setEnabled(false);
            hzck.setEnabled(false);
        }

        // ---------------TODO 设置圆头颜色--------------------
        MaterialCardView color1 = findViewById(R.id.carcolor1);
        if (!getFile("$圆头颜色.so").isEmpty()) {
            圆头颜色 = Integer.parseInt(getFile("$圆头颜色.so"));
            color1.setCardBackgroundColor(圆头颜色);
        } else {
            圆头颜色 = -16777216;
        }
        MaterialCardView cardView1 = findViewById(R.id.carcolor1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View dialogView = LayoutInflater.from(homeActivity.this).inflate(R.layout.color, null);
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(homeActivity.this);
                builder.setView(dialogView);

                OnColorSelectListener colorSelectListener = new OnColorSelectListener() {
                    @Override
                    public void onColorSelected(int newColor, int oldColor) {
                        MaterialCardView cardView = dialogView.findViewById(R.id.color_car);
                        Log.w(TAG, String.valueOf(oldColor));
                        cardView.setCardBackgroundColor(oldColor);
                        color1.setCardBackgroundColor(oldColor);
                        setFile(String.valueOf(newColor), "$圆头颜色.so");
                        圆头颜色 = oldColor;
                    }

                };

                XColorPicker xColorPicker = (XColorPicker) dialogView.findViewById(R.id.XColorPicker);
                xColorPicker.setOnColorSelectListener(colorSelectListener);

                builder.setTitle("设置圆头颜色")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ;
                            }
                        })
                        .setNegativeButton("恢复默认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteFile("$圆头颜色.so");
                                color1.setCardBackgroundColor(-16777216);
                                圆头颜色 = -16777216;
                            }
                        })
                        .show();

            }

        });


        // ---------------TODO 设置线条颜色--------------------
        MaterialCardView color2 = findViewById(R.id.carcolor2);
        if (!getFile("$线条颜色.so").isEmpty()) {
            线条颜色 = Integer.parseInt(getFile("$线条颜色.so"));
            color2.setCardBackgroundColor(线条颜色);
        } else {
            线条颜色 = -65534;
        }
        MaterialCardView cardView2 = findViewById(R.id.carcolor2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View dialogView = LayoutInflater.from(homeActivity.this).inflate(R.layout.color, null);
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(homeActivity.this);
                builder.setView(dialogView);

                OnColorSelectListener colorSelectListener = new OnColorSelectListener() {
                    @Override
                    public void onColorSelected(int newColor, int oldColor) {
                        MaterialCardView cardView = dialogView.findViewById(R.id.color_car);
                        Log.w(TAG, String.valueOf(oldColor));
                        cardView.setCardBackgroundColor(oldColor);
                        color2.setCardBackgroundColor(oldColor);
                        Log.w(TAG, String.valueOf(oldColor));
                        线条颜色 = oldColor;
                        setFile(String.valueOf(oldColor), "$线条颜色.so");
                    }
                };

                XColorPicker xColorPicker = (XColorPicker) dialogView.findViewById(R.id.XColorPicker);
                xColorPicker.setOnColorSelectListener(colorSelectListener);

                builder.setTitle("设置线条颜色")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("恢复默认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteFile("$线条颜色.so");
                                线条颜色 = -65534;
                                color2.setCardBackgroundColor(-65534);
                            }
                        })
                        .show();

            }

        });


        //--------------TODO 设置窗口背景透明度--------------
        if (!getFile("$窗口透明度.so").isEmpty()) {
            窗口透明度 = Integer.parseInt(getFile("$窗口透明度.so"));
            seekBar.setProgress(窗口透明度);
        } else {
            窗口透明度 = 80;
        }
        TextView textView = findViewById(R.id.ckjdt);
        textView.setText(窗口透明度 + "%");
        final int stepSize = 10; // 设置步长为10
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int adjustedProgress = (progress / stepSize) * stepSize;

                // 更新SeekBar的进度
                seekBar.setProgress(adjustedProgress);
                textView.setText(adjustedProgress + "%");
                窗口透明度 = adjustedProgress;
                PaintView paint_view = (PaintView) mEasyWindow.findViewById(R.id.paint_view);
                if (窗口透明度 == 100) {
                    颜色 = "#00000000";
                } else if (窗口透明度 == 90) {
                    颜色 = "#1A000000";
                } else if (窗口透明度 == 80) {
                    颜色 = "#33000000";
                } else if (窗口透明度 == 70) {
                    颜色 = "#4D000000";
                } else if (窗口透明度 == 60) {
                    颜色 = "#66000000";
                } else if (窗口透明度 == 50) {
                    颜色 = "#80000000";
                } else if (窗口透明度 == 40) {
                    颜色 = "#99000000";
                } else if (窗口透明度 == 30) {
                    颜色 = "#B3000000";
                } else if (窗口透明度 == 20) {
                    颜色 = "#CC000000";
                }
                paint_view.setBackgroundColor(Color.parseColor(颜色));
                setFile(String.valueOf(adjustedProgress), "$窗口透明度.so");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //--------------TODO 设置点击坐标--------------
        TextView zbxy = findViewById(R.id.zbxy);
        String xy = getFile("$坐标.so");
        if (!xy.isEmpty()) {
            坐标X = split(xy, "\\*", 0);
            坐标Y = split(xy, "\\*", 1);
            zbxy.setText(坐标X + "*" + 坐标Y);
        } else {
            zbxy.setText("点击坐标");
        }
        LinearLayout linearLayout_xy = findViewById(R.id.linear_layout_xy);
        linearLayout_xy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(homeActivity.this).inflate(R.layout.xy, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity.this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                MaterialCardView cardView = dialogView.findViewById(R.id.cmxy);
                TextView textView1 = dialogView.findViewById(R.id.xy);
                cardView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            /**
                             * 点击的开始位置
                             */
                            case MotionEvent.ACTION_DOWN:
                                textView1.setText("坐标位置：(X:" + event.getX() + ",Y:" + event.getY() + ")");
                                break;
                            /**
                             * 触屏实时位置
                             */
                            case MotionEvent.ACTION_MOVE:
                                textView1.setText("坐标位置：(X:" + event.getX() + ",Y:" + event.getY() + ")");
                                break;
                            /**
                             * 离开屏幕的位置
                             */
                            case MotionEvent.ACTION_UP:
                                textView1.setText("坐标位置：(X:" + event.getX() + ",Y:" + event.getY() + ")");
                                setFile(split(String.valueOf(event.getX()), "\\.", 0) + "*" + split(String.valueOf(event.getY()), "\\.", 0), "$坐标.so");
                                String xy = getFile("$坐标.so");
                                if (!xy.isEmpty()) {
                                    String x = split(xy, "\\*", 0);
                                    String y = split(xy, "\\*", 1);
                                    坐标X = split(x, "\\.", 0);
                                    坐标Y = split(y, "\\.", 0);
                                    zbxy.setText(坐标X + "*" + 坐标Y);
                                } else {
                                    zbxy.setText("点击坐标");
                                }
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
            }
        });

        //--------------TODO 设置跳跃时间编辑框--------------
        TextInputEditText editText = findViewById(R.id.timeput);
        if (!getFile("$跳跃时间.so").isEmpty()) {
            跳跃时间 = getFile("$跳跃时间.so");
            editText.setText(跳跃时间);
        } else {
            跳跃时间 = "0.6999500";
            editText.setText(跳跃时间);
        }
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 在文本改变之前调用
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 在文本改变时调用
            }

            @Override
            public void afterTextChanged(Editable s) {
                跳跃时间 = String.valueOf(editText.getText());
                setFile(跳跃时间, "$跳跃时间.so");
            }
        });

        // ---------------TODO 判断是否分布操作--------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
            if (!getFile("$分布操作.so").isEmpty()) {
                Boolean xx = Boolean.valueOf(getFile("$分布操作.so"));
                分布操作 = xx;
                v3.setChecked(xx);
            }
            LinearLayout play_LinearLayout = (LinearLayout) mEasyWindow.findViewById(R.id.play_linear_layout);
            v3.setOnCheckedChangeListener(new MaterialSwitch.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setFile(String.valueOf(isChecked), "$分布操作.so");
                    分布操作 = isChecked;
                    if (isChecked) {
                        play_LinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        play_LinearLayout.setVisibility(View.GONE);
                    }
                }
            });
        }

        // ---------------TODO 设置窗口高度--------------------
        TextView ckgd = findViewById(R.id.ckgd);
        if (!getFile("$窗口高度.so").isEmpty()) {
            窗口高度 = Integer.parseInt(getFile("$窗口高度.so"));
            hzck.setProgress(窗口高度);
            ckgd.setText(窗口高度 + "像素");
        } else {
            窗口高度 = 200;
            ckgd.setText(窗口高度 + "像素");
        }

        hzck.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                hzck.setProgress(progress);
                ckgd.setText(progress + "像素");
                PaintView paint_view = (PaintView) mEasyWindow.findViewById(R.id.paint_view);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) paint_view.getLayoutParams();
                layoutParams.setMargins(0, progress, 0, 0);
                paint_view.setLayoutParams(layoutParams);
                setFile(String.valueOf(progress), "$窗口高度.so");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (!getFile("$绘制隐藏.so").isEmpty()) {
            Boolean xx = Boolean.valueOf(getFile("$绘制隐藏.so"));
            绘制隐藏 = xx;
            v4.setChecked(xx);
        }
        v4.setOnCheckedChangeListener(new MaterialSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setFile(String.valueOf(isChecked), "$绘制隐藏.so");
                绘制隐藏 = isChecked;
            }
        });


        Intent whiteIntent = new Intent(getApplicationContext(), WhiteService.class);
        startService(whiteIntent);

        Intent blackIntent = new Intent();
        blackIntent.setAction(BLACK_WAKE_ACTION);
        sendBroadcast(blackIntent);

		


		
		/*
		LinearLayout linearLayout = findViewById(R.id.wrap);
		linearLayout.setOnTouchListener(this);
		
		Button button = findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(MainActivity.this)) {
					Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + MainActivity.this.getPackageName()));
					startActivityForResult(intent, REQUEST_CODE_FLOATING_WINDOW);
				} else {
					logbt();
				}
			}
			
		});
		
		//点击指定控件
		findViewById(R.id.bt_main_DianJi).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AccessibilityNodeInfo ces = HongBaoService.mService.findFirst(AbstractTF.newText("测试控件", true));
				if (ces == null) {
					Utils.toast("找测试控件失败");
					return;
				}
				HongBaoService.clickView(ces);
			}
		});
		
		//用手势长按指定控件
		/*findViewById(R.id.bt_main_ChangAn).setOnClickListener(new View.OnClickListener() {
			@TargetApi(Build.VERSION_CODES.N)
			@Override
			public void onClick(View v) {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
					Toast.makeText(MainActivity.this, "7.0及以上才能使用手势", Toast.LENGTH_SHORT).show();
					return;
				}
				AccessibilityNodeInfo ces = HongBaoService.mService.findFirst(AbstractTF.newText("测试控件", true));
				if (ces == null) {
					Utils.toast("找测试控件失败");
					return;
				}
				//ces.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);//长按
				//这里为了示范手势的效果
				Rect absXY = new Rect();
				ces.getBoundsInScreen(absXY);
				HongBaoService.mService.dispatchGestureLongClick(557, 2057, 5000);
//                HongBaoService.mService.dispatchGestureClick(absXY.left + (absXY.right - absXY.left) / 2, absXY.top + (absXY.bottom - absXY.top) / 2);//手势点击效果
				//手势长按效果
				//控件正中间
				//HongBaoService.mService.dispatchGestureLongClick(absXY.left + (absXY.right - absXY.left) / 2, absXY.top + (absXY.bottom - absXY.top) / 2);
				
			}
		});*/
    }


    //----------------TODO 设置悬浮窗事件---------------
    private void logbt() {
        //ToDO 设置Log悬浮窗
        mEasyWindow = EasyWindow.with(this.getApplication());
        mEasyWindow.setContentView(R.layout.log);
        mEasyWindow.setGravity(Gravity.TOP);
        mEasyWindow.setOnClickListener(R.id.map, new EasyWindow.OnClickListener<ImageView>() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(EasyWindow<?> window, ImageView view) {
                ImageView map = (ImageView) view.findViewById(R.id.map);
                PaintView paintView = (PaintView) mEasyWindow.findViewById(R.id.paint_view);
                int visibility = paintView.getVisibility();
                boolean isVisible = (visibility == View.GONE);
                if (isVisible) {
                    paintView.setVisibility(0);
                    map.setImageResource(R.drawable.baseline_zoom_in_map_24);
                } else {
                    paintView.setVisibility(8);
                    map.setImageResource(R.drawable.baseline_zoom_out_map_24);
                }
            }
        });

        mEasyWindow.setOnClickListener(R.id.play, new EasyWindow.OnClickListener<ImageView>() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(EasyWindow<?> window, ImageView view) {
                if (HongBaoService.isStart()) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        Toasty.info("7.0及以上才能使用手势");
                    } else {
                        if (String.valueOf(PaintView.长按时间) == "0.0 ") {
                            Toasty.info("未绘制跳跃距离");
                        } else {
                            if (坐标X != null && 坐标Y != null) {
                                HongBaoService.mService.dispatchGestureLongClick(Integer.parseInt(坐标X), Integer.parseInt(坐标Y), PaintView.长按时间);
                                PaintView paintView = (PaintView) mEasyWindow.findViewById(R.id.paint_view);
                                PaintView.清除绘制(paintView);
                            } else {
                                Toasty.info("未设置点击坐标.");
                            }
                        }
                    }
                } else {
                    Toasty.info("未打开无障碍服务");
                }
            }
        });
        LinearLayout play_LinearLayout = (LinearLayout) mEasyWindow.findViewById(R.id.play_linear_layout);
        if (!getFile("$分布操作.so").isEmpty()) {
            if (Boolean.parseBoolean(getFile("$分布操作.so"))) {
                play_LinearLayout.setVisibility(View.VISIBLE);
            } else {
                play_LinearLayout.setVisibility(View.GONE);
            }
        } else {
            play_LinearLayout.setVisibility(View.GONE);
        }
        if (!getFile("$窗口高度.so").isEmpty()) {
            窗口高度 = Integer.parseInt(getFile("$窗口高度.so"));
            PaintView paint_view = (PaintView) mEasyWindow.findViewById(R.id.paint_view);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) paint_view.getLayoutParams();
            layoutParams.setMargins(0, 窗口高度, 0, 0);
            paint_view.setLayoutParams(layoutParams);

        }
        if (!getFile("$窗口透明度.so").isEmpty()) {
            窗口透明度 = Integer.parseInt(getFile("$窗口透明度.so"));
            PaintView paint_view = (PaintView) mEasyWindow.findViewById(R.id.paint_view);
            if (窗口透明度 == 100) {
                颜色 = "#00000000";
            } else if (窗口透明度 == 90) {
                颜色 = "#1A000000";
            } else if (窗口透明度 == 80) {
                颜色 = "#33000000";
            } else if (窗口透明度 == 70) {
                颜色 = "#4D000000";
            } else if (窗口透明度 == 60) {
                颜色 = "#66000000";
            } else if (窗口透明度 == 50) {
                颜色 = "#80000000";
            } else if (窗口透明度 == 40) {
                颜色 = "#99000000";
            } else if (窗口透明度 == 30) {
                颜色 = "#B3000000";
            } else if (窗口透明度 == 20) {
                颜色 = "#CC000000";
            }
            paint_view.setBackgroundColor(Color.parseColor(颜色));
        }

        mEasyWindow.show();
    }

    //----------------TODO 无障碍回调事件---------------
    @Override
    protected void onResume() {
        super.onResume();
        if (HongBaoService.isStart()) {
            v1.setChecked(true);
        } else {
            v1.setChecked(false);
        }
    }

    //----------------TODO 悬浮窗回调事件---------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FLOATING_WINDOW) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                logbt();
                v2.setChecked(true);

                seekBar.setEnabled(true);
                hzck.setEnabled(true);
                v3.setEnabled(true);
            } else {
                seekBar.setEnabled(false);
                hzck.setEnabled(false);
                v3.setEnabled(false);
                Toasty.info("未给悬浮窗权限.");
            }
        }
    }


    //--------------------------TODO 摧毁应用时执行--------------------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
        } else {
            mEasyWindow.cancel();
        }

    }

    public void setFile(String str, String file) {
        FileOutputStream fos = null;
        BufferedWriter writer = null;

        try {
            fos = openFileOutput(file, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            try {
                writer.write(str);
                Log.w("file", "记录成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getFile(String file) {
        FileInputStream fis = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            fis = openFileInput(file);
            reader = new BufferedReader(new InputStreamReader(fis));
            String str;

            while ((str = reader.readLine()) != null) {
                content.append(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public boolean deleteFile(String fileName) {
        File file = new File(getFilesDir(), fileName);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                Log.w("file", "删除成功");
            } else {
                Log.w("file", "删除失败");
            }
        } else {
            Log.w("file", "文件不存在");
        }
        return false;
    }

    private String split(String nr, String zf, int len) {
        String[] parts = nr.split(zf);

        String before = parts[len];
        return before;
    }


}
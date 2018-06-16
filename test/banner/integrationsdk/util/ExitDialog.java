package com.banner.integrationsdk.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.banner.integrationsdk.R;
import com.banner.integrationsdk.data.Constants;
import com.banner.integrationsdk.myad.MyAdBanner;
import com.liyi.R;
import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class ExitDialog extends Dialog {

    Activity mContext;
    private NativeAD nativeAD;
    private NativeADDataRef adItem;
    private ImageView adImg;
    private View layout;
    View left, right, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_exit_zh);
        initView();
        loadAD();
    }

    public ExitDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = (Activity) context;
    }

    //初始化并加载广告
    public void initView() {
        layout = (View) findViewById(R.id.exit_lin);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        adImg = (ImageView) findViewById(R.id.adimg);
        adImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adItem!=null){
                    adItem.onClicked(view);
                }else{
                    mContext.startActivity(new Intent(mContext, MyAdBanner.class));
                }
                dismiss();
            }
        });
        left = (View) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adItem!=null){
                    adItem.onClicked(view);
                }else{
                    mContext.startActivity(new Intent(mContext, MyAdBanner.class));
                }
                dismiss();
            }
        });
        right = (View) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobclickAgent.onKillProcess(mContext);
                mContext.finish();
                int currentVersion = Build.VERSION.SDK_INT;
                if (currentVersion > Build.VERSION_CODES.ECLAIR_MR1) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //清除上一步缓存
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(startMain);
                    System.exit(0);
                } else {// android2.1
                    ActivityManager am = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
                    am.restartPackage(mContext.getPackageName());
                }
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    dismiss();
        return true;
    }

    //初始化并加载广告
    public void loadAD() {
        if (nativeAD == null) {
            this.nativeAD = new NativeAD(mContext, Constants.APPID, Constants.NativePosID, new NativeAD.NativeAdListener() {
                @Override
                public void onADLoaded(List<NativeADDataRef> list) {
                    if (list.size() > 0) {
                        adItem = list.get(0);
                        showAD();
//                        Toast.makeText(mContext, "原生广告加载成功", Toast.LENGTH_LONG).show();
                    } else {
                        Log.i("AD_DEMO", "NOADReturn");
                    }
                }

                @Override
                public void onNoAD(AdError adError) {

                }

                @Override
                public void onADStatusChanged(NativeADDataRef nativeADDataRef) {

                }

                @Override
                public void onADError(NativeADDataRef nativeADDataRef, AdError adError) {

                }
            });
        }
        int count = 1; // 一次拉取的广告条数：范围1-10
        nativeAD.loadAD(count);
    }

    public void showAD() {
        if (adItem.getAdPatternType() == AdPatternType.NATIVE_3IMAGE) {
        } else if (adItem.getAdPatternType() == AdPatternType.NATIVE_2IMAGE_2TEXT) {
            adImg.setImageURI(Uri.parse(adItem.getImgUrl()));
        } else if (adItem.getAdPatternType() == AdPatternType.NATIVE_1IMAGE_2TEXT) {
        }
        adItem.onExposured(adImg); // 需要先调用曝光接口
    }

}




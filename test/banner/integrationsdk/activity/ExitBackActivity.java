package com.banner.integrationsdk.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banner.integrationsdk.data.Constants;
import com.banner.integrationsdk.myad.MyAdBanner;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.liyi.R;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Locale;

public class ExitBackActivity extends AppCompatActivity implements NativeAD.NativeAdListener {
    Context mContext;
    private NativeAD nativeAD;
    private NativeADDataRef adItem;
    private SimpleDraweeView adImg;
    private View layout;
    View left,right,cancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_exit_zh);
        mContext = this;
        initView();
        loadAD();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }


    //初始化并加载广告
    public void initView() {
        layout = (View) findViewById(R.id.exit_lin);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        adImg = (SimpleDraweeView) findViewById(R.id.adimg);
        adImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adItem.onClicked(view);//发送相关的状态通知
                finish();
            }
        });
        left= (View) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adItem.onClicked(view);//发送相关的状态通知
                finish();
            }
        });
        right= (View) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        cancel= (View) findViewById(R.id.cancel);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    //初始化并加载广告
    public void loadAD() {
        if (nativeAD == null) {
            this.nativeAD = new NativeAD(this, Constants.APPID, Constants.NativePosID, this);
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

    @Override
    public void onADLoaded(List<NativeADDataRef> list) {
        if (list.size() > 0) {
            adItem = list.get(0);
            showAD();
            Toast.makeText(this, "原生广告加载成功", Toast.LENGTH_LONG).show();
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

}

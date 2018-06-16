package com.banner.integrationsdk.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.banner.integrationsdk.Interface.ADCallBackInterface;
import com.banner.integrationsdk.R;
import com.banner.integrationsdk.data.Constants;
import com.banner.integrationsdk.myad.MyAdBanner;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.liyi.R;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

import java.util.List;
import java.util.Locale;

public class AdDialog extends Dialog{

    Context mContext;
    private NativeAD nativeAD;
    private NativeADDataRef adItem;
    private ImageView adImg;
    private View layout;
    View left,right,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_interstitial_zh);
        setCanceledOnTouchOutside(false);
        initView();
        loadAD();
    }

    public AdDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext= context;
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
        left= (View) findViewById(R.id.left);
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
        cancel= (View) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

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




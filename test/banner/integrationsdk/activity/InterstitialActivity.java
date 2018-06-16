package com.banner.integrationsdk.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.banner.integrationsdk.R;
import com.banner.integrationsdk.data.Constants;
import com.facebook.drawee.view.SimpleDraweeView;
import com.liyi.R;
import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

import java.util.List;

public class InterstitialActivity extends AppCompatActivity implements NativeAD.NativeAdListener {
    Context mContext;
    private NativeAD nativeAD;
    private NativeADDataRef adItem;
    private SimpleDraweeView adImg;
    private View layout;
    View left,right,cancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_interstitial_zh);
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
        left= (View) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cancel= (View) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

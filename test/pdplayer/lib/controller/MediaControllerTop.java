package com.pdplayer.lib.controller;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liyi.R;


/**
 * You can write a custom MediaController instead of this class
 * A MediaController widget must implement all the interface defined by com.pili.pldroid.player.IMediaController
 */
public class MediaControllerTop extends RelativeLayout {
    public TextView topTitle,topShoucanTv;
    public  ImageView topBack,topShoucanIcon;
    public MediaControllerTop(@NonNull Context context) {
        super(context);
        init();
    }

    public MediaControllerTop(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MediaControllerTop(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
       View  rootView = inflate(getContext(), R.layout.controller_top, this);
        topTitle = (TextView) rootView.findViewById(R.id.top_title);
        topBack = (ImageView) rootView.findViewById(R.id.top_back);
        topShoucanTv = (TextView) rootView.findViewById(R.id.top_shoucan_tv);
        topShoucanIcon = (ImageView) rootView.findViewById(R.id.top_shoucan_icon);
//        setBackgroundColor(Color.parseColor("#50000000"));
        setBackgroundDrawable(getResources().getDrawable(R.color.controller_back));
    }





}

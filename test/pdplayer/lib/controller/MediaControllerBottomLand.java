package com.pdplayer.lib.controller;

import android.content.Context;
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
public class MediaControllerBottomLand extends RelativeLayout{
    public  ImageView bottomPlay,bottomFull;
    public TextView bottomZHIBO;
    public MediaControllerBottomLand(@NonNull Context context) {
        super(context);
        init();
    }

    public MediaControllerBottomLand(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MediaControllerBottomLand(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View rootView = inflate(getContext(), R.layout.controller_bottom_land, this);
        bottomZHIBO = (TextView) rootView.findViewById(R.id.bottom_zhibo);
        bottomPlay = (ImageView) rootView.findViewById(R.id.bottom_play);
        bottomFull = (ImageView) rootView.findViewById(R.id.bottom_full);
//        setBackgroundColor(Color.parseColor("#50000000"));
        setBackgroundDrawable(getResources().getDrawable(R.color.controller_back));
    }



}

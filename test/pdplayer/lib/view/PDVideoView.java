package com.pdplayer.lib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liyi.R;
import com.pdplayer.lib.controller.MediaControllerBottom;
import com.pdplayer.lib.controller.MediaControllerTop;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.qq.e.ads.nativ.MediaView;

import static android.content.ContentValues.TAG;

public class PDVideoView extends FrameLayout {
    public PLVideoTextureView plVideoView;
    public MediaControllerTop mediaControllerTop;
    public MediaControllerBottom mediaControllerBottom;
    public Boolean playFlag = true;
    public View rootView, LoadingView;
    public Context mContext;
    public  MediaView mMediaView;
    public SimpleDraweeView adImg;
    public View  adView,tiaoguo;
    public    TextView timnum;
    public PDVideoView(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public PDVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public PDVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        rootView = inflate(getContext(), R.layout.view_player, this);
        plVideoView = (PLVideoTextureView) rootView.findViewById(R.id.PLVideView);
        mMediaView = (MediaView) findViewById(R.id.gdt_media_view);
        adView= (View) findViewById(R.id.adView);
        tiaoguo= (View) findViewById(R.id.tiaoguo);
        timnum= (TextView) findViewById(R.id.timnum);
        adImg = (SimpleDraweeView) findViewById(R.id.gdt_image_view);
        LoadingView = (View) rootView.findViewById(R.id.LoadingView);
        mediaControllerTop = (MediaControllerTop) rootView.findViewById(R.id.controller_Top);
        mediaControllerBottom = (MediaControllerBottom) rootView.findViewById(R.id.controller_bottom);
        onClick();
    }




    private void onClick() {
        plVideoView.setOnInfoListener(new PLOnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
                switch (what) {
                    case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                        LoadingView.setVisibility(View.VISIBLE);
                        break;
                    case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                        LoadingView.setVisibility(View.GONE);
                        break;
//                    case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
//                        LoadingView.setVisibility(View.GONE);
////                        Utils.showToastTips(PLMediaPlayerActivity.this, "first video render time: " + extra + "ms");
//                        break;
//                    case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
//                        LoadingView.setVisibility(View.GONE);
//                        break;
                }
            }
        });
//        mediaControllerBottom.bottomFull.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                plVideoView.setDisplayOrientation(270);
//                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//                DisplayMetrics dm = new DisplayMetrics();
//                wm.getDefaultDisplay().getMetrics(dm);
////                int width = dm.widthPixels;         // 屏幕宽度（像素）
//                int height = dm.heightPixels;       // 屏幕高度（像素）
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rootView.getLayoutParams();
//                //获取当前控件的布局对象
//                params.height = height;//设置当前控件布局的高度
//                rootView.setLayoutParams(params);//将设置好的布局参数应用到控件中
//            }
//        });

        mediaControllerBottom.bottomPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plVideoView.isPlaying()) {
                    plVideoView.pause();
                    mediaControllerBottom.bottomPlay.setImageResource(R.drawable.player_icon_bottomview_play_button_normal);
                    mediaControllerBottom.bottomZHIBO.setText("暂停");
                    playFlag = false;
                } else {
                    plVideoView.start();
                    mediaControllerBottom.bottomZHIBO.setText("直播中");
                    mediaControllerBottom.bottomPlay.setImageResource(R.drawable.player_icon_bottomview_pause_button_normal);
                    playFlag = true;
                }
            }
        });
    }

}
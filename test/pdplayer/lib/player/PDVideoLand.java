package com.pdplayer.lib.player;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.OrientationEventListener;
import android.view.View;

import com.pdplayer.lib.interfaceUtil.PDOnErrorListener;
import com.pdplayer.lib.options.PlayOption;
import com.pdplayer.lib.view.PDVideoViewLand;
import com.pili.pldroid.player.IMediaController;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.widget.PLVideoView;

public class PDVideoLand extends PDVideoViewLand {
    private static int sDefaultTimeout = 3000;
    private static final int FADE_OUT = 1;
    private static final int FADE_IN = 2;
    OrientationEventListener mOrientationListener;
    public PDVideoLand(@NonNull Context context) {
        super(context);
        init();
    }

    public PDVideoLand(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();


    }

    public PDVideoLand(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        plVideoView.setAVOptions(PlayOption.option());
        //全屏
        plVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        plVideoView.setMediaController(MediaPlayerControl);
        //显示
        mHandler.obtainMessage(FADE_IN);
        //传入3秒后隐藏
        mHandler.sendEmptyMessageDelayed(FADE_OUT, sDefaultTimeout);

//        play.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickTask();
//            }
//        });

    }


//    public void setOnErrorListener(final PDOnErrorListener listener) {
//        plVideoView.setOnErrorListener(new PDOnErrorListener() {
//            @Override
//            public boolean onError(int state) {
//                listener.onError(state);
//                return false;
//            }
//        });
//    }

    public void setOnErrorListener(final PDOnErrorListener listener) {
        plVideoView.setOnErrorListener(new PLOnErrorListener() {
            @Override
            public boolean onError(int i) {
                listener.onError(i);
                return true;
            }
        });
    }

    public void setOnInfoListener(PLOnInfoListener listener) {
        plVideoView.setOnInfoListener(listener);
    }

    public boolean isPlay() {
      return  plVideoView.isPlaying();
    }

//    /**
//     * 隐藏虚拟按键，并且全屏
//     */
//    protected void hideBottomUIMenu() {
//        //隐藏虚拟按键，并且全屏
//        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//            View v = this.getWindow().getDecorView();
//            v.setSystemUiVisibility(View.GONE);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            //for new api versions.
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
//        }
//    }



//    OrientationEventListener mOrientationListener2 = new OrientationEventListener(mContext,SensorManager.SENSOR_DELAY_NORMAL) {
//        @Override
//        public void onOrientationChanged(int orientation) {
//  Log.i("噶3",orientation+"");
//        }
//    };


    IMediaController MediaPlayerControl = new IMediaController() {
        @Override
        public void setMediaPlayer(IMediaController.MediaPlayerControl mediaPlayerControl) {

        }

        @Override
        public void show() {
            clickTask();
        }

        @Override
        public void show(int i) {

        }

        @Override
        public void hide() {

        }

        @Override
        public boolean isShowing() {
            return false;
        }

        @Override
        public void setEnabled(boolean b) {

        }

        @Override
        public void setAnchorView(View view) {

        }
    };


    public void setPath(String url) {
        plVideoView.setVideoPath(url);
    }


    public void start() {
        plVideoView.start();
    }

    public void pause() {
        plVideoView.pause();
    }

    public void stopPlayback() {
        plVideoView.stopPlayback();
    }


    public void clickTask() {
        mediaControllerTop.setVisibility(VISIBLE);
        mediaControllerBottom.setVisibility(VISIBLE);
        //去掉之前的handler
        mHandler.removeMessages(FADE_OUT);
        //传入3秒后隐藏
        mHandler.sendEmptyMessageDelayed(FADE_OUT, sDefaultTimeout);
    }


    public void showView() {
        mediaControllerTop.setVisibility(VISIBLE);
        mediaControllerBottom.setVisibility(VISIBLE);

    }

    public void hideView() {
        mediaControllerTop.setVisibility(GONE);
        mediaControllerBottom.setVisibility(GONE);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FADE_OUT:
                    hideView();
                    break;
                case FADE_IN:
                    showView();
                    break;
            }
        }
    };


}
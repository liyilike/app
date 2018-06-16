package com.pdplayer.lib.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.banner.integrationsdk.data.Constants;
import com.pdplayer.lib.interfaceUtil.PDOnErrorListener;
import com.pdplayer.lib.options.PlayOption;
import com.pdplayer.lib.view.PDVideoView;
import com.pili.pldroid.player.IMediaController;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.widget.PLVideoView;
import com.qq.e.ads.nativ.MediaListener;
import com.qq.e.ads.nativ.NativeMediaAD;
import com.qq.e.ads.nativ.NativeMediaADData;
import com.qq.e.comm.util.AdError;

import java.util.List;

public class PDVideo extends PDVideoView {
    private static int sDefaultTimeout = 3000;
    private static final int TIME_AD = 5000;
    private static final int FADE_OUT = 1,FADE_IN = 2,FLAG_AD = 3,FLAG_AD_HIDE = 4;
    private int TIME_AD_NUM = 8;
    private NativeMediaAD mADManager;
    private NativeMediaADData mAD;                          // 原生视频广告对象
    OrientationEventListener mOrientationListener;
    PDOnErrorListener pdOnErrorListener;
    String TAG = "gsdf11";
    String adTimeStr;
    long adTime;
    private final Handler tikTokHandler = new Handler();
    public PDVideo(@NonNull Context context) {
        super(context);
        init();
    }

    public PDVideo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();


    }

    public PDVideo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        initNativeVideoAD();
        loadAD();
        tiaoguo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.sendEmptyMessageDelayed(FLAG_AD_HIDE, 0);
            }
        });
        adImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAD!=null){
                    mAD.onClicked(view);
                }

            }
        });
        mMediaView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAD!=null){
                    mAD.onClicked(view);
                }

            }
        });

    }


    private void initNativeVideoAD() {
        NativeMediaAD.NativeMediaADListener listener = new NativeMediaAD.NativeMediaADListener() {
            @Override
            public void onADLoaded(List<NativeMediaADData> ads) {
//                Toast.makeText(mContext, "成功加载原生广告：" + ads.size() + "条", Toast.LENGTH_SHORT)
//                        .show();
                if (ads.size() > 0) {
                    mAD = ads.get(0);
                    // 广告加载成功 渲染UI
                    adImg.setImageURI(Uri.parse(mAD.getImgUrl()));
                    adImg.setVisibility(VISIBLE);
                    mHandler.sendEmptyMessageDelayed(FLAG_AD, 1000);

//                    mAQuery.id(mImageView).image(mAD.getImgUrl(), false, true, 0, 0, new BitmapAjaxCallback() {
//                        @Override
//                        protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
//                            // AQuery框架有一个问题，就是即使在图片加载完成之前将ImageView设置为了View.GONE，在图片加载完成后，这个ImageView会被重新设置为VIEW.VISIBLE。
//                            // 所以在这里需要判断一下，如果已经把ImageView设置为隐藏，开始播放视频了，就不要再显示广告的大图。开发者在用其他的图片加载框架时，也应该注意检查下是否有这个问题。
//                            if (iv.getVisibility() == View.VISIBLE) {
//                                iv.setImageBitmap(bm);
//                            }
//                        }
//                    });
                    /**
                     * 特别注意：和普通图文类原生广告一样，渲染带有视频素材的原生广告时，也需要开发者调用曝光接口onExposured来曝光广告，否则onClicked点击接口将无效
                     */
                    mAD.onExposured(adView);
                    // 加载视频
                    if (mAD.isVideoAD()) {
                        mAD.preLoadVideo();
                    }
                }

            }

            @Override
            public void onNoAD(AdError adError) {
                mHandler.sendEmptyMessageDelayed(FLAG_AD_HIDE, 0);
            }

            @Override
            public void onADStatusChanged(NativeMediaADData nativeMediaADData) {

            }

            @Override
            public void onADError(NativeMediaADData nativeMediaADData, AdError adError) {
                mHandler.sendEmptyMessageDelayed(FLAG_AD_HIDE, 0);
            }

            @Override
            public void onADVideoLoaded(NativeMediaADData nativeMediaADData) {
                bindMediaView();
            }

            @Override
            public void onADExposure(NativeMediaADData nativeMediaADData) {

            }

            @Override
            public void onADClicked(NativeMediaADData nativeMediaADData) {

            }
        };

        mADManager = new NativeMediaAD(mContext, Constants.APPID, Constants.NativeVideoPosID, listener);
    }


    private void bindMediaView() {
        if (mAD.isVideoAD()) {
            // 首先把预设的大图隐藏，显示出MediaView。一定要保证MediaView可见，才能播放视频，否则SDK将无法上报曝光效果并计费。
            mMediaView.setVisibility(View.VISIBLE);
            adImg.setVisibility(INVISIBLE);
            // bindView时指定第二个参数为false，则不会调用广点通的默认视频控制条。贴片场景下可能不太需要用到SDK默认的控制条。
            mAD.bindView(mMediaView, false);
            mAD.play();
            mAD.setVolumeOn(true);
            mAD.setMediaListener(new MediaListener() {
                @Override
                public void onVideoReady(long videoDuration) {
//                    Log.i(TAG, "onVideoReady");
//                    adTime = videoDuration;
//                    adTimeStr = Math.round((videoDuration - mAD.getCurrentPosition()) / 1000.0) + "";
//                    mHandler.sendEmptyMessageDelayed(FLAG_AD_VOD, 0);
                    duration = videoDuration;
                }

                @Override
                public void onVideoStart() {
                    Log.i(TAG, "onVideoStart");
                    tikTokHandler.post(countDown);
//                    mTextCountDown.setVisibility(View.VISIBLE);
//                    mButtonDetail.setVisibility(View.VISIBLE);
                }

                @Override
                public void onVideoPause() {
                    Log.i(TAG, "onVideoPause");
                }

                @Override
                public void onVideoComplete() {
                    Log.i(TAG, "onVideoComplete");
                    releaseCountDown();
                    mHandler.sendEmptyMessageDelayed(FLAG_AD_HIDE, 0);
//                    mAdContainer.setVisibility(View.GONE);
//                    mVideoView.setVisibility(View.VISIBLE);
//                    mVideoView.start();
                }

                @Override
                public void onVideoError(AdError adError) {
//                    Log.i(TAG, String.format("onVideoError, errorCode: %d, errorMsg: %s",
//                            adError.getErrorCode(), adError.getErrorMsg()));
                    releaseCountDown();
                    mHandler.sendEmptyMessageDelayed(FLAG_AD_HIDE, 0);
//                    mAdContainer.setVisibility(View.GONE);
//                    mVideoView.setVisibility(View.VISIBLE);
//                    mVideoView.start();
                }

                @Override
                public void onADButtonClicked() {
                    // 当广点通默认视频控制界面中的“查看详情/免费下载”按钮被点击时，会回调此接口，如果没有使用广点通的控制条此接口不会被回调
                    Log.i(TAG, "onVideoADClicked");
                }

                @Override
                public void onReplayButtonClicked() {
                    // 当广点通默认视频控制界面中的“重新播放”按钮被点击时，会回调此接口
                    Log.i(TAG, "onVideoReplay");
                }

                @Override
                public void onFullScreenChanged(boolean inFullScreen) {
                    Log.i(TAG, "onFullScreenChanged, inFullScreen = " + inFullScreen);
                }
            });
        }
    }

    private void loadAD() {
        if (mADManager != null) {
            try {
                mADManager.loadAD(1);
            } catch (Exception e) {
                Toast.makeText(mContext, "加载失败，请重试", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void releaseCountDown() {
        if (tikTokHandler != null && countDown != null) {
            tikTokHandler.removeCallbacks(countDown);
        }
    }
    /**
     * 刷新广告倒计时
     */
    private static final String TEXT_COUNTDOWN = "广告倒计时：%s ";
    private long currentPosition, oldPosition, duration;
    private Runnable countDown = new Runnable() {
        public void run() {
            if (mAD != null) {
                currentPosition = mAD.getCurrentPosition();
                long position = currentPosition;
//                if (oldPosition == position && mAD.isPlaying()) {
////                    Log.i(TAG, "玩命加载中...");
////                    mTextCountDown.setTextColor(Color.WHITE);
//                    timnum.setText("还有" + oldPosition + "秒广告");
//                } else {
//                    timnum.setText("还有" + position + "秒广告");
////                    mTextCountDown.setTextColor(Color.WHITE);
//                    mTextCountDown.setText(String.format(TEXT_COUNTDOWN, Math.round((duration - position) / 1000.0) + ""));
//                }
//                oldPosition = position;


                if (mAD.isPlaying()) {
                    timnum.setText("还有" + Math.round((duration - position) / 1000.0)  + "秒广告");
                    tikTokHandler.postDelayed(countDown, 500);
                }else{
                    mHandler.sendEmptyMessageDelayed(FLAG_AD_HIDE, 0);
                }

            }
        }

    };



//    public void setContext(Context mContext) {
//        plVideoView.setOnInfoListener(listener);
//    }


//    PLOnErrorListener plOnErrorListener = new PLOnErrorListener() {
//        @Override
//        public boolean onError(int state) {
//            pdOnErrorListener.onError(state);
//            return false;
//        }
//    };

//    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
//        @Override
//        public boolean onError(int errorCode) {
//            Log.e(TAG, "Error happened, errorCode = " + errorCode);
//            switch (errorCode) {
//                //未知错误
//                case PLOnErrorListener.MEDIA_ERROR_UNKNOWN:
//                    /**
//                     * SDK will do reconnecting automatically
//                     */
//                    Log.e(TAG, "IO Error!");
//                    return false;
//                    //	网络异常
//                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
//                    /**
//                     * SDK will do reconnecting automatically
//                     */
//                    Log.e(TAG, "IO Error!");
//                    return false;
//                    //播放器打开失败
//                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
////                    Utils.showToastTips(PLVideoViewActivity.this, "failed to open player !");
//                    break;
////                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
////                    Utils.showToastTips(PLVideoViewActivity.this, "failed to seek !");
////                    break;
//                default:
////                    Utils.showToastTips(PLVideoViewActivity.this, "unknown error !");
//                    break;
//            }
////            finish();
//            return true;
//        }
//    };



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
        return plVideoView.isPlaying();
    }


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
                case FLAG_AD:
                    if (TIME_AD_NUM != 0) {
                        TIME_AD_NUM = TIME_AD_NUM - 1;
                        timnum.setText("还有 " + TIME_AD_NUM + " 秒广告");
                        mHandler.sendEmptyMessageDelayed(FLAG_AD, 1000);
                        break;
                    }
                    adView.setVisibility(GONE);
                    if (mAD != null) {
                        mAD.stop();
                        mAD.destroy();
                    }
                    break;
                case FLAG_AD_HIDE:
                    adView.setVisibility(GONE);
                    if (mAD != null) {
                        mAD.stop();
                        mAD.destroy();
                    }
                    break;
            }
        }
    };


}
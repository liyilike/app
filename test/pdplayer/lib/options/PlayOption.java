package com.pdplayer.lib.options;

import com.pili.pldroid.player.AVOptions;

public class PlayOption {

    public static AVOptions option(){

        AVOptions options = new AVOptions();
        // DNS 服务器设置
// 若不设置此项，则默认使用 DNSPod 的 httpdns 服务
// 若设置为 127.0.0.1，则会使用系统的 DNS 服务器
// 若设置为其他 DNS 服务器地址，则会使用设置的服务器
        options.setString(AVOptions.KEY_DNS_SERVER, "119.29.29.29"); //设置dns
// DNS 缓存设置
// 若不设置此项，则每次播放未缓存的域名时都会进行 DNS 解析，并将结果缓存
// 参数为 String[]，包含了要缓存 DNS 结果的域名列表
// SDK 在初始化时会解析列表中的域名，并将结果缓存
//        options.setStringArray(AVOptions.KEY_DOMAIN_LIST, domainList);
// 解码方式:
// codec＝AVOptions.MEDIA_CODEC_HW_DECODE，硬解
// codec=AVOptions.MEDIA_CODEC_SW_DECODE, 软解
// codec=AVOptions.MEDIA_CODEC_AUTO, 硬解优先，失败后自动切换到软解
// 默认值是：MEDIA_CODEC_SW_DECODE
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
// 若设置为 1，则底层会进行一些针对直播流的优化
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
// 快开模式，启用后会加快该播放器实例再次打开相同协议的视频流的速度
        options.setInteger(AVOptions.KEY_FAST_OPEN, 1);
// 打开重试次数，设置后若打开流地址失败，则会进行重试
        options.setInteger(AVOptions.KEY_OPEN_RETRY_TIMES, 10);
// 预设置 SDK 的 log 等级， 0-4 分别为 v/d/i/w/e
        options.setInteger(AVOptions.KEY_LOG_LEVEL, 5);
// 打开视频时单次 http 请求的超时时间，一次打开过程最多尝试五次
// 单位为 ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 5 * 1000);
// 默认的缓存大小，单位是 ms
// 默认值是：500
        options.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 500);
// 最大的缓存大小，单位是 ms
// 默认值是：2000，若设置值小于 KEY_CACHE_BUFFER_DURATION 则不会生效
        options.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 4000);
// 是否开启直播优化，1 为开启，0 为关闭。若开启，视频暂停后再次开始播放时会触发追帧机制
// 默认为 0
        options.setInteger(AVOptions.KEY_LIVE_STREAMING,1);
// 设置拖动模式，1 位精准模式，即会拖动到时间戳的那一秒；0 为普通模式，会拖动到时间戳最近的关键帧。默认为 0
//        options.setInteger(AVOptions.KEY_SEEK_MODE);
// 设置 DRM 密钥
//        byte[] key = {xxx, xxx, xxx, xxx, xxx ……};
//        options.setByteArray(AVOptions.KEY_DRM_KEY, key);
// 设置偏好的视频格式，设置后会加快对应格式视频流的打开速度，但播放其他格式会出错
// m3u8 = 1, mp4 = 2, flv = 3
//        options.setInteger(AVOptions.KEY_PREFER_FORMAT, 1);
// 开启解码后的视频数据回调
// 默认值为 0，设置为 1 则开启
//        options.setInteger(AVOptions.KEY_VIDEO_DATA_CALLBACK, 1);
// 开启解码后的音频数据回调
// 默认值为 0，设置为 1 则开启
//        options.setInteger(AVOptions.KEY_VIDEO_DATA_CALLBACK, 1);
// 请在开始播放之前配置
        return options;
    }









}
package com.pdplayer.lib.interfaceUtil;

public   interface PDOnErrorListener  {
    int MEDIA_ERROR_UNKNOWN = -1;
    int ERROR_CODE_OPEN_FAILED = -2;
    int ERROR_CODE_IO_ERROR = -3;
    int ERROR_CODE_SEEK_FAILED = -4;
    int ERROR_CODE_HW_DECODE_FAILURE = -2003;
    int ERROR_CODE_PLAYER_DESTROYED = -2008;
    int ERROR_CODE_PLAYER_VERSION_NOT_MATCH = -9527;
    int ERROR_CODE_PLAYER_CREATE_AUDIO_FAILED = -4410;

    boolean onError(int state);
}
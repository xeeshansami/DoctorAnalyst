package com.fyp.utils;

import okhttp3.logging.HttpLoggingInterceptor;

public class Config {
    public static final String BASE_URL_HBL = "https://strokex.xoqax.com/api/";

    public static final long timeoutPdf = 240;
    public static final long timeoutNormal = 10;
    public static final long API_CONNECT_TIMEOUT = timeoutNormal;
    public static final long API_PDF_CONNECT_TIME_OUT = timeoutNormal;
    //    public static final long API_READ_TIMEOUT = timeoutNormal;
//    public static final long API_WRITE_TIMEOUT = timeoutNormal;
    public static final HttpLoggingInterceptor.Level LOG_LEVEL_API = HttpLoggingInterceptor.Level.BODY;
}

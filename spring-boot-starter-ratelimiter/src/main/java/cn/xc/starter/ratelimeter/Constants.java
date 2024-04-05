package cn.xc.starter.ratelimeter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Constants {

    public static Map<String, RateLimiter> rateLimiterMap =
            Collections.synchronizedMap(new HashMap<String, RateLimiter>());

}

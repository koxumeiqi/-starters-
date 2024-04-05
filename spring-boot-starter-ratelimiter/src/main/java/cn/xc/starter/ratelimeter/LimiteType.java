package cn.xc.starter.ratelimeter;

public enum LimiteType {

    // 令牌桶限流
    RATE_LIMITER,
    // 计数限流
    COUNT_LIMITER,
    // 滑动窗口限流
    SLIDING_WINDOW_LIMITER,
    // 滑动时间窗口限流
    SLIDING_WINDOW_TIMED_LIMITER,
    // 漏桶限流
    LEAKY_BUCKET_LIMITER,
    DEFAULT;

}

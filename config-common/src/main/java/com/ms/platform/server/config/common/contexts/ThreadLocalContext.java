package com.ms.platform.server.config.common.contexts;

/**
 *  上下文，定义基本的字段作
 * Created by Joey on 2017/8/14 0014.
 */
public class ThreadLocalContext {

    private String userId;

    private static ThreadLocal<ThreadLocalContext> threadLocal = new ThreadLocal<>();

    public static ThreadLocalContext get() {
        return threadLocal.get();
    }

    public static void set(ThreadLocalContext ctx){
        threadLocal.set(ctx);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}

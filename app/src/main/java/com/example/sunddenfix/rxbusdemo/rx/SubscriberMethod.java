package com.example.sunddenfix.rxbusdemo.rx;


import java.lang.reflect.Method;

/**
 * @author wzg 2016/9/21
 */
class SubscriberMethod {
    Method     method;
    ThreadMode threadMode;
    Class<?>   eventType;
    Object     subscriber;
    int        code;

    SubscriberMethod(Object subscriber, Method method, Class<?> eventType, int code, ThreadMode threadMode) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.subscriber = subscriber;
        this.code = code;
    }


    /**
     * 调用方法
     *
     * @param o 参数
     */
    void invoke(Object o) {
        try {
            method.invoke(subscriber, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

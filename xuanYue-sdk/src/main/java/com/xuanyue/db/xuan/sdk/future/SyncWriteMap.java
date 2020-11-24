package com.xuanyue.db.xuan.sdk.future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xuanyue.db.xuan.msg.X2YMsg;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class SyncWriteMap {

    public static Map<Long, WriteFuture<X2YMsg>> syncKey = new ConcurrentHashMap<>();

}

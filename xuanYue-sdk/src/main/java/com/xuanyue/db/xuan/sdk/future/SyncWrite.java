package com.xuanyue.db.xuan.sdk.future;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.omg.CORBA.Request;

import com.xuanyue.db.xuan.msg.VLAUETYPE;
import com.xuanyue.db.xuan.msg.X2YMsg;
import com.xuanyue.db.xuan.msg.X2YValue;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class SyncWrite {

	private static AtomicLong atoId = new AtomicLong();
	
    public X2YMsg writeAndSync(final Channel channel, final X2YMsg request, final long timeout) throws Exception {

        if (channel == null) {
            throw new NullPointerException("channel");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout <= 0");
        }

        long requestId = atoId.addAndGet(1);
        request.setMsgId( new  X2YValue( VLAUETYPE.LONG,requestId ) );

        WriteFuture<X2YMsg> future = new SyncWriteFuture(requestId);
        SyncWriteMap.syncKey.put(requestId, future);

        X2YMsg response = doWriteAndSync(channel, request, timeout, future);

        SyncWriteMap.syncKey.remove(requestId);
        return response;
    }

    private X2YMsg doWriteAndSync(final Channel channel, final X2YMsg request, final long timeout, final WriteFuture<X2YMsg> writeFuture) throws Exception {

        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                writeFuture.setWriteResult(future.isSuccess());
                writeFuture.setCause(future.cause());
                //失败移除
                if (!writeFuture.isWriteSuccess()) {
                    SyncWriteMap.syncKey.remove(writeFuture.requestId());
                }
            }
        });

        X2YMsg response = writeFuture.get(1000, TimeUnit.SECONDS);
        if (response == null) {
            if (writeFuture.isTimeout()) {
                throw new TimeoutException();
            } else {
                throw new Exception(writeFuture.cause());
            }
        }
        return response;
    }

}

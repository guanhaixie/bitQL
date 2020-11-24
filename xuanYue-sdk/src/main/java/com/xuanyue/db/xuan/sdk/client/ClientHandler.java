package com.xuanyue.db.xuan.sdk.client;

import com.xuanyue.db.xuan.msg.X2YMsg;
import com.xuanyue.db.xuan.sdk.future.SyncWriteFuture;
import com.xuanyue.db.xuan.sdk.future.SyncWriteMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
    	X2YMsg msg = (X2YMsg) obj;
        long requestId = msg.getMsgId().getValue();
        SyncWriteFuture future = (SyncWriteFuture) SyncWriteMap.syncKey.get(requestId);
        if (future != null) {
            future.setResponse(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

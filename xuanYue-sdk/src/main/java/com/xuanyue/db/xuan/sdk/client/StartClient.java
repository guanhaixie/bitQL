package com.xuanyue.db.xuan.sdk.client;

import com.xuanyue.db.xuan.msg.VLAUETYPE;
import com.xuanyue.db.xuan.msg.X2YMsg;
import com.xuanyue.db.xuan.msg.X2YValue;
import com.xuanyue.db.xuan.sdk.future.SyncWrite;

import io.netty.channel.ChannelFuture;

public class StartClient {
	private static ChannelFuture future;
	public static void main(String[] args) {
		System.out.println("hi 微信公众号：bugstack虫洞栈");
        ClientSocket client = new ClientSocket();
        new Thread(client).start();

        while (true) {
            try {
                //获取future，线程有等待处理时间
                if (null == future) {
                    future = client.getFuture();
                    Thread.sleep(500);
                    continue;
                }
                //构建发送参数
                X2YMsg request = new X2YMsg();
                request.setMsg( new X2YValue(VLAUETYPE.STRING, " [sql] "));
                SyncWrite s = new SyncWrite();
                X2YMsg response = s.writeAndSync(future.channel(), request, 1000);
                
                response.getData().forEach( e->{
                	System.out.println(e);
                });
                
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

	}

}

package com.xuanyue.db.xuan.sdk.client;

import javax.xml.ws.Response;

import org.omg.CORBA.Request;

import com.xuanyue.db.xuan.netty.coder.X2YMsgDecoder;
import com.xuanyue.db.xuan.netty.coder.X2YMsgEecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientSocket implements Runnable{
	 private ChannelFuture future;

	    @Override
	    public void run() {
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            Bootstrap b = new Bootstrap();
	            b.group(workerGroup);
	            b.channel(NioSocketChannel.class);
	            b.option(ChannelOption.AUTO_READ, true);
	            b.handler(new ChannelInitializer<SocketChannel>() {
	                @Override
	                public void initChannel(SocketChannel ch) throws Exception {
	                    ch.pipeline().addLast(
	                    		 new X2YMsgDecoder(),
                                 new X2YMsgEecoder(),
	                            new ClientHandler());
	                }
	            });
	            ChannelFuture f = b.connect("127.0.0.1", 7397).sync();
	            this.future = f;
	            f.channel().closeFuture().sync();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } finally {
	            workerGroup.shutdownGracefully();
	        }
	    }

	    public ChannelFuture getFuture() {
	        return future;
	    }

	    public void setFuture(ChannelFuture future) {
	        this.future = future;
	    }
}

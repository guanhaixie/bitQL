package com.xuanyue.db.xuan.netty.server;

import com.xuanyue.db.xuan.SeachContext;
import com.xuanyue.db.xuan.netty.coder.X2YMsgDecoder;
import com.xuanyue.db.xuan.netty.coder.X2YMsgEecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class XYYAloneServer {

	public static void main(String[] args) throws Exception {
		
		SeachContext.initer();
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch){
                            ch.pipeline().addLast(
                                    new X2YMsgDecoder(),
                                    new X2YMsgEecoder(),
                                    new XYYServerHandler());
                        }
                    });

            ChannelFuture f = null;
            f = b.bind(7397).sync();
            f.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
	}
}

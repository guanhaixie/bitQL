//package com.xuanyue.db.xuan.netty.server;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.xuanyue.db.xuan.IResult;
//import com.xuanyue.db.xuan.SeachContext;
//import com.xuanyue.db.xuan.msg.VLAUETYPE;
//import com.xuanyue.db.xuan.msg.X2YMsg;
//import com.xuanyue.db.xuan.msg.X2YValue;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//
//public class XYYServerHandler extends ChannelInboundHandlerAdapter {
//
//	
//	
//	@Override
//    public void channelRead(ChannelHandlerContext ctx, Object obj){
//		X2YMsg msg = (X2YMsg)obj;
//		String sql = msg.getMsg().getValue();
//		
//		IResult res = SeachContext.query(sql);
//		msg.setData(res.getData());
//		Map<String,X2YValue> types = new HashMap<>();
//		res.getTypes().forEach( (k,v)->{
//			types.put(k, new X2YValue(VLAUETYPE.INT, v.getType())  );
//		});
//		msg.setpV(types);
//		System.out.println(msg.getMsgId().toString());
//		ctx.writeAndFlush(msg);
//	}
//}
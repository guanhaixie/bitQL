package com.xuanyue.db.xuan.netty.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xuanyue.db.xuan.IResult;
import com.xuanyue.db.xuan.SeachContext;
import com.xuanyue.db.xuan.msg.VLAUETYPE;
import com.xuanyue.db.xuan.msg.X2YMsg;
import com.xuanyue.db.xuan.msg.X2YValue;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class XYYServerHandler extends ChannelInboundHandlerAdapter {

	
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object obj){
		X2YMsg re = (X2YMsg)obj;
		Map<String,X2YValue> info = new HashMap<>();
		X2YMsg msg = new X2YMsg();
		long id = re.getMsgId().getValue();
		msg.setMsgId(id);
		System.out.println(String.format("id:%s", id));
		String sql = re.getMsg().getValue();
		System.out.println(String.format("sql:%s", sql));
		try {
			if(sql!=null) {
				IResult result = SeachContext.query(sql);
				msg.setData(result.getData());
				Map<String,X2YValue> types = new HashMap<>();
				result.getTypes().forEach( (k,v)->{
					types.put(k, new X2YValue(VLAUETYPE.INT,v.getType()));
				});
				msg.setpV(types);
				info.put("count", new X2YValue(VLAUETYPE.INT,  result.count() ) );
				info.put("status",  new X2YValue(VLAUETYPE.BOOLEAN,true));
			}else {
				info.put("status",  new X2YValue(VLAUETYPE.BOOLEAN,false));
				info.put("info",  new X2YValue(VLAUETYPE.STRING,"sql is null"));
			}
		} catch (Exception e) {
			info.put("status",  new X2YValue(VLAUETYPE.BOOLEAN,false));
			info.put("info",  new X2YValue(VLAUETYPE.STRING,e.getMessage()));
		}
		msg.setMsg(new X2YValue( VLAUETYPE.MAP1STR2VALUE, info));
//		Map<String,X2YValue> types = new HashMap<>();
//		types.put("ph",  new X2YValue(VLAUETYPE.INT,VLAUETYPE.STRING.getType()));
//		types.put("ph",  new X2YValue(VLAUETYPE.INT,VLAUETYPE.INT.getType()));
//		msg.setpV(types);
		
		
		
		ctx.writeAndFlush(msg);
		//System.out.println("server:  >>"+v.toString());
		ReferenceCountUtil.release(re);
	}
}
package com.xuanyue.db.xuan.netty.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		X2YMsg msg = new X2YMsg();
		long id = re.getMsgId().getValue();
		msg.setMsgId(id);
		List<Map<String, X2YValue>> data = new ArrayList<>();
		Map<String, X2YValue> v = new HashMap<>();
		v.put("ph", new X2YValue(VLAUETYPE.STRING,"13521192609") );
		v.put("city", new X2YValue(VLAUETYPE.INT,1119) );
		data.add(v);
		
		v = new HashMap<>();
		v.put("ph", new X2YValue(VLAUETYPE.STRING,"13521192610") );
		v.put("city", new X2YValue(VLAUETYPE.INT,1100) );
		data.add(v);
		
		msg.setData(data);
		Map<String,X2YValue> types = new HashMap<>();
		types.put("ph",  new X2YValue(VLAUETYPE.INT,VLAUETYPE.STRING.getType()));
		types.put("ph",  new X2YValue(VLAUETYPE.INT,VLAUETYPE.INT.getType()));
		msg.setpV(types);
		
		Map<String,X2YValue> info = new HashMap<>();
		info.put("count", new X2YValue(VLAUETYPE.INT,2) );
		info.put("info",  new X2YValue(VLAUETYPE.STRING," info from server is ok"));
		
		msg.setMsg(new X2YValue( VLAUETYPE.MAP1STR2VALUE, info));
		
		ctx.writeAndFlush(msg);
		System.out.println("server:  >>"+v.toString());
		ReferenceCountUtil.release(re);
	}
}
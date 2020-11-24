package com.xuanyue.db.xuan.netty.coder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xuanyue.db.xuan.msg.VLAUETYPE;
import com.xuanyue.db.xuan.msg.X2YMsg;
import com.xuanyue.db.xuan.msg.X2YValue;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class X2YMsgDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();
		X2YMsg msg = new X2YMsg();
		msg.setMsg(  new X2YValue(VLAUETYPE.STRING));
		msg.setMsgId(new X2YValue(VLAUETYPE.LONG));
		decoder(in,msg);
		System.out.println(msg.getMsg().value());
		System.out.println(msg.getMsgId().value());
		out.add(msg);
	}
	public void decoder(ByteBuf in,X2YMsg tar) {
		X2YValueTool.decode(in,tar.getMsgId());
		
		X2YValue p = new X2YValue();
		p.setType(VLAUETYPE.MAP1STR2VALUE);
		X2YValueTool.decode(in,p);
		//pV = p.getValue();
		tar.setpV(p.getValue());
		int size = in.readInt();
		
		List<Map<String, X2YValue>> data = new ArrayList<>();
		for(int i=0;i<size;i++) {
			X2YValueTool.decode(in,p);
			data.add( p.getValue());
		}
		tar.setData(data);
		X2YValueTool.decode(in,tar.getMsg());
	}
}

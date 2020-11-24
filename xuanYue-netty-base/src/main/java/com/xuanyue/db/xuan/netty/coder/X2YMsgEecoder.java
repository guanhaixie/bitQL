package com.xuanyue.db.xuan.netty.coder;


import com.xuanyue.db.xuan.msg.VLAUETYPE;
import com.xuanyue.db.xuan.msg.X2YMsg;
import com.xuanyue.db.xuan.msg.X2YValue;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class X2YMsgEecoder extends MessageToByteEncoder<X2YMsg>{

	@Override
	protected void encode(ChannelHandlerContext ctx, X2YMsg msg, ByteBuf out) throws Exception {
		X2YValueTool.encode(out,msg.getMsgId());
		X2YValue p = new X2YValue();
		p.setType(VLAUETYPE.MAP1STR2VALUE);
		p.setValue(msg.getPV());
		X2YValueTool.encode(out,p);
		
		if(msg.getData()==null) {
			out.writeInt(0);
		}else {
			out.writeInt(msg.getData().size());
			msg.getData().forEach( x->{
				p.setType(VLAUETYPE.MAP1STR2VALUE);
				p.setValue(x);
				X2YValueTool.encode(out,p);
			});
		}
		X2YValueTool.encode(out,msg.getMsg());
	}
}

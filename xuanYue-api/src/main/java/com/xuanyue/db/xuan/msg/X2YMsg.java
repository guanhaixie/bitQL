package com.xuanyue.db.xuan.msg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xuanyue.db.xuan.msg.VLAUETYPE;
import com.xuanyue.db.xuan.msg.X2YValue;

/**
 * 通信消息
 * msgId : 请求id
 * 
 * 1、 查询请求  
 *  pV ： 请求传递的参数
 *  msg :  sql
 *  data : 无意义，请设置为空
 * 2、响应
 *  pV ： 每个列的类型
 *  data: 请求结果
 *  msg : MAP1STR2VALUE类型  ["count": 记录总数    ,"msg": 消息(可选)]
 *  
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年11月16日
 *
 */
public class X2YMsg {

	private X2YValue msgId;
	private Map<String,X2YValue> pV;
	private List<Map<String,X2YValue>> data;
	private X2YValue msg;
	public X2YValue getMsgId() {
		return msgId;
	}
	public void setMsgId(long msgId) {
		this.msgId = new X2YValue(VLAUETYPE.LONG);
		this.msgId.setValue(msgId);
	}
	public void setMsgId(X2YValue msgId) {
		this.msgId = msgId;
	}
	public Map<String, X2YValue> getPV() {
		return pV;
	}
	public void setpV(Map<String, X2YValue> pV) {
		this.pV = pV;
	}
	public List<Map<String, X2YValue>> getData() {
		return data;
	}
	public void setData(List<Map<String, X2YValue>> data) {
		this.data = data;
	}
	public X2YValue getMsg() {
		return msg;
	}
	public void setMsg(X2YValue msg) {
		this.msg = msg;
	}
	/*
	
	public void encoder(ByteBuf out) {//X2YValueTool
		X2YValueTool.encode(out,msgId);
		X2YValue p = new X2YValue();
		p.setType(VLAUETYPE.MAP1STR2VALUE);
		p.setValue(pV);
		X2YValueTool.encode(out,p);
		
		if(data==null) {
			out.writeInt(0);
		}else {
			out.writeInt(data.size());
			data.forEach( x->{
				p.setType(VLAUETYPE.MAP1STR2VALUE);
				p.setValue(x);
				X2YValueTool.encode(out,p);
			});
		}
		X2YValueTool.encode(out,msg);
	}
	public void decoder(ByteBuf in) {
		X2YValueTool.decode(in,msgId);
		
		X2YValue p = new X2YValue();
		p.setType(VLAUETYPE.MAP1STR2VALUE);
		X2YValueTool.decode(in,p);
		pV = p.getValue();
		
		int size = in.readInt();
		data = new ArrayList<>();
		for(int i=0;i<size;i++) {
			X2YValueTool.decode(in,p);
			data.add( p.getValue());
		}
		X2YValueTool.decode(in,msg);
	}
	
	*/
}

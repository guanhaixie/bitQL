package com.xuanyue.db.xuan.netty.coder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xuanyue.db.xuan.msg.VLAUETYPE;
import com.xuanyue.db.xuan.msg.X2YValue;

import io.netty.buffer.ByteBuf;

public class X2YValueTool {
	private static String readString(ByteBuf in) {
		int l = in.readInt();
		if(l==0) {
			return "";
		}else {
			byte[] dst = new byte[l];
			in.readBytes(dst);
			return new String(dst);
		}
		
	}
	private static void writeString(ByteBuf out,String v) {
		byte[] dx = v.getBytes();
		out.writeInt(dx.length);
		out.writeBytes(dx);
	}
	public static void decode(ByteBuf in,X2YValue tar) {
		tar.setType(VLAUETYPE.getByType(in.readByte()));
		boolean isNotNull = in.readBoolean();
		if(isNotNull) {
			int size = 0;
			String key = null;
			Object value = null;
			switch(tar.getType()) {
				case INT://int
					value=in.readInt();
					break;
				case FLOAT://float
					value=in.readFloat();
					break;
				case LONG://long
					value=in.readLong();
					break;
				case DATE://Date
					value=new Date(in.readLong());
					break;
				case BOOLEAN://boolean
					value=in.readBoolean();
					break;
				case MAPBOOLEAN://MapBoolean 
					Map<String,Boolean> vt = new HashMap<>();
					size = in.readInt();
					for(int i=0;i<size;i++) {
						key = readString(in);
						vt.put(key, in.readBoolean());
					}
					value=vt;
					break;
				case MAPNUM://MapNum
					Map<String,Long> vn = new HashMap<>();
					size = in.readInt();
					for(int i=0;i<size;i++) {
						key = readString(in);
						vn.put(key, in.readLong());
					}
					value=vn;
					break;
				case LISTNUM://ListUnumber
					List<Long> vsl = new ArrayList<>();
					size = in.readInt();
					for(int i=0;i<size;i++) {
						vsl.add(in.readLong() );
					}
					value=vsl;
					break;
				case STRING://string
					value=readString(in);
					break;
				case MAP1STR2VALUE://Map<String,X2YValue>
					Map<String,X2YValue> sh = new HashMap<>();
					size = in.readInt();
					X2YValue tmp = null;
					for(int i=0;i<size;i++) {
						key = readString(in);
						tmp = new X2YValue();
						decode(in,tmp);
						sh.put(key, tmp);
					}
					value=sh;
					break;
				default:
					throw new RuntimeException("数据类型不支持");//异常
			}
			tar.setValue(value);
		}
	}
	
	public static void encode(ByteBuf out,X2YValue tar) {
		Object value = tar.getValue();
		out.writeByte(tar.getType().getType());
		out.writeBoolean( value!=null);
		if(value==null) {
			return;
		}
		switch(tar.getType()) {
			case INT://int
				out.writeInt((int)value);
				break;
			case FLOAT://float
				out.writeFloat( (float)value);
				break;
			case LONG://long
				out.writeLong( (long)value);
				break;
			case DATE://Date
				out.writeLong(((Date)value).getTime() );
				break;
			case BOOLEAN://boolean
				out.writeBoolean( (boolean)value);
				break;
			case MAPBOOLEAN://MapBoolean 
				@SuppressWarnings("unchecked") 
				Map<String,Boolean> vt = (Map<String,Boolean>)value;
				out.writeInt(vt.size());
				vt.forEach( (n,is)->{
					writeString(out, n);
					out.writeBoolean(is);
				} );
				break;
			case MAPNUM://MapNum
				@SuppressWarnings("unchecked") 
				Map<String,Long> vn = (Map<String,Long>)value;
				out.writeInt(vn.size());
				vn.forEach( (k,n)->{
					writeString(out, k);
					out.writeLong(n);
				} );
				break;
			case LISTNUM://ListUnumber
				@SuppressWarnings("unchecked") 
				List<Long> vsl = (List<Long>)value;
				out.writeInt(vsl.size());
				vsl.forEach( e->{
					out.writeLong(e);
				});
				break;
			case STRING://string
				writeString(out, value.toString());
				break;
			case MAP1STR2VALUE://Map<String,X2YValue>
				@SuppressWarnings("unchecked") 
				Map<String,X2YValue> tmp = (Map<String,X2YValue>)value;
				out.writeInt(tmp.size());
				tmp.forEach(  (k,v)->{
					writeString(out,k);
					encode(out,v);
				});
				break;
			default:
				throw new RuntimeException("数据类型不支持");//异常
		}
	}
}

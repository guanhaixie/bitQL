package com.xuanyue.db.xuan.antlr.impl;

/**
 * 多维排序的维度信息
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月29日
 * @version 0.1
 */
public class Ele implements Comparable<Ele>{

	private String key;
	private int count;
	
	private int start=-1;
	private int num=0;
	
	
	public void info() {
		
		StringBuffer r =new StringBuffer();
		
		
		for(int i=0;i<count;i++) {
			String t = String.format("%s%s",key, i);
			if(i==start&&num>0) {
				t="["+t;
			}else {
				t=" "+t;
			}
			if(i==(start+num-1)&&num>0) {
				t=t+"]";
			}else {
				t=t+" ";
			}
			if(i<10) {
				t+="  ";
			}else if(i<100) {
				t+=" ";
			}
			r.append(t);
			r.append(",");
		}
		
		r.append(String.format("    %s/%s->%s", count,start,num));
		System.out.println(r.toString());
		
	}
	
	public int getNum() {
		return num;
	}
	public void setNumSurplus() {
		this.num = count-start;
	}
	public void setNum(int num) {
		this.num = num-this.start;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public int compareTo(Ele o) {
		return (count-o.getCount()<0?-1:(count-o.getCount()==0?0:1));
	}
	@Override
	public String toString() {
		return String.format("%s:%s", key,count);
	}
	
	
	
}

package com.xuanyue.db.xuan.core.table.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xuanyue.db.xuan.core.exception.BankerException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.ISortElement;

public class LimitHandler {
	
	private IBitIndex where_result;
	
	private IBitIndex cache;
	private IBitIndex now;
	private IBitIndex cache2;
	private IBitIndex tmp;
	
	
	public LimitHandler(List<IBitIndex> caches,boolean sort,IBitIndex mask) {
		where_result = caches.get(0);
		where_result.and(mask);
		if(sort) {
			cache = caches.get(1);
			now = caches.get(2);
			cache2 = caches.get(3);
			tmp = caches.get(4);
		}
	}
	
	private int offset2(IBitIndex cache,IBitIndex now,List<ISortElement> sl,List<Map<Integer,Boolean>> pathl,int offset,Map<Integer,Boolean> path) throws BankerException {
		cache.copyFrom(where_result);
		//cache.and(mask);
		int c=cache.cardinality();//数据总量
		if(offset>=c) {//全部
			pathl.add(path);
			return c-offset;
		}
		ISortElement element;
		Map<Integer,Boolean> pathCp = null;
		int c1;//
		for(int i=0;i<sl.size();i++) {
			c=cache.cardinality();//当前节点的总记录数。
			element = sl.get(i);
			now = element.getData();
			if(c==offset) {//当前子树的记录数正好为offset
				pathl.add( new HashMap<>( path) );
				offset-=c;
				break;
			}else {
				if(c>offset) {//当前子树的记录数量包含offset
					c1 = (element.isDesc()?cache.andByCardinality(now):cache.andNotByCardinality(now));//c1为优先子树记录数。
					if(c1==offset) {
						//当前子树刚好满足数量
						offset-=c1;
						path.put(i,true);
						pathl.add( new HashMap<>( path) );
						break;
					}else if(c1>offset){
						//当前子树的数量大于offset,需要继续拆分子树。
						path.put(i,true);
						if(element.isDesc()) {
							cache.and(now);
						}else {
							cache.andNot(now);
						}
					}else {
						//当前子树的数量小于offset，需要收录当前子树并路由到另外的子树上。
						//1、当前子树被收录
						offset-=c1;
						pathCp =new HashMap<>( path);
						pathCp.put(i,true);
						pathl.add(pathCp);
						//2、路由到另外的子树
						path.put(i,false);
						if(element.isDesc()) {
							cache.andNot(now);
						}else {
							cache.and(now);
						}
					}
				}
				else {//c<offset 超界了 这里
					throw new BankerException("c<offset???????");
				}
			}
		}
		return offset;
	}
	
	private String getSorStr(List<ISortElement> sl,int index) {
		StringBuffer r = new StringBuffer();
		ISortElement se = null;
		for(int i=0;i<sl.size();i++) {
			se = sl.get(i);
			if(!(se.getData().get(index)^se.isDesc())) {
				r.append(1);
			}else {
				r.append(0);
			}
		}
		return r.toString();
	}
	
	private void path2BitIndex(List<ISortElement> sl,Map<Integer,Boolean> p,IBitIndex now) {
		now.copyFrom(where_result);
		p.forEach((k,v)->{
			ISortElement sortElement = sl.get(k);
			/**
			 真值表：                     优先     次选
			             T    F
			    Desc: T  T    F
			not Desc: F  F    T
			 等价变换为：   亦或取反。             
			 */
			if( !(sortElement.isDesc()^v) ) {
				now.and(sortElement.getData());
			}else {
				now.andNot(sortElement.getData());
			}
			
		} );
	}
	
	private void pathlTransfer(List<ISortElement> sl,List<Map<Integer,Boolean>> pathl,IBitIndex cache,IBitIndex now) {
		cache.setAll(false);
		pathl.forEach(p->{
			path2BitIndex(sl, p, now);
			cache.or(now);
		});
	}
	public List<Integer> limit(List<ISortElement> sl,int offset,int num) throws BankerException {
		if(sl.size()>0) {
			return this.limitHandler(sl, offset, num);
		}else {
			return where_result.limit(offset, num);
		}
	}
	public List<Integer> limit(int offset,int num) throws BankerException {
		return where_result.limit(offset, num);
	}
	public List<Integer> limitHandler(List<ISortElement> sl,int offset,int num) throws BankerException {
		//System.out.println(String.format("where_result:%s",  where_result.cardinality() ));
		List<Integer> r = new ArrayList<>();
		List<Map<Integer,Boolean>> pathlOffset = new ArrayList<>();
		Map<Integer,Boolean> pathOffset = new HashMap<>();
		int os1 = offset2(cache,now,sl, pathlOffset, offset,pathOffset);
		if(os1<0) {//offset大于总记录数，所以无任何结果。
			return r;
		}
		pathlTransfer(sl, pathlOffset, cache2, now);//offset的记录放入cache2
		
		for (int j = cache.nextSetBit(0); j >= 0&&os1>0; j = cache.nextSetBit(j+1)) {//cache2补齐叶子节点的剩余记录数。
			cache2.set(j);
			os1--;
		}
		//当前cache2就是跳过的offset个记录。
		/////////////////////////
		
		List<Map<Integer,Boolean>> pathlCover = new ArrayList<>();
		Map<Integer,Boolean> pathCover = new HashMap<>();
		int os2 = offset2(cache,now,sl, pathlCover, offset+num,pathCover);
		
		pathlTransfer(sl, pathlCover, tmp, now);//offset+num的记录放入tmp
		
		for (int j = cache.nextSetBit(0); j >= 0&&os2>0; j = cache.nextSetBit(j+1)) {//cache2补齐叶子节点的剩余记录数。
			tmp.set(j);
			os2--;
		}
		//当前tmp就是前offset+num个记录。
		tmp.andNot(cache2);
		List<Integer> is = tmp.getIndexOftrue();
		
		List<SortDataElement> rx = new ArrayList<>();
		
		is.forEach(i->{
			rx.add(new SortDataElement(i, getSorStr(sl, i) ));
		});
		Collections.sort(rx);
		
		is.clear();
		
		
		rx.forEach(sde->{
			is.add(sde.getIndex());
		});
		return is;
	}
}

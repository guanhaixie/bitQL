package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.core.table.sort.SortElement;
/**
 * 手机号
 * 
 * 手机号为0到9的10数字，所以一个字符只需要 4个bit就可以了，总共需要44个bit就可以了
 * 如果想要第N位是某个数值，或者不是某个数值，只需要对应bit向量的逻辑计算即可。
 *
 * 兼容座机号：
 * 座机号不够11位,所以我们用 1111 (4个1)来填充不足的数据位。
 * 
 * 4个bit为可以   15 - 10个数字字符  - 1个填充字符 = 4个
 * 还有多余的4个字符可以用作其他含义。
 * 当前只使用了一个：     1010 表示   *号，用于数据脱敏，表示未知。
 * 
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class PhoneIndex implements IColumn{

	private HashMap<Integer,IBitIndex> positionV2bit = new HashMap<>();
	private IBitIndex mask = new BitIndex();
	
	public PhoneIndex() {
		for(int i=0;i<44;i++) {
			positionV2bit.put(i, new BitIndex());
		}
	}
	@Override
	public void flush(int maxId) {
		positionV2bit.forEach( (k,v)->{
			v.flush(maxId);
		}  );
		mask.flush(maxId);
	}
	@Override
	public void save(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		mask.save(String.format("%s/mask", path));
		for(Entry<Integer,IBitIndex> en:positionV2bit.entrySet()) {
			en.getValue().save(String.format("%s/positionV2bit/%s", path,en.getKey()));
		}
	}
	@Override
	public void toBatchLoadMode(String path) {
		mask = new BatchBitIndex(String.format("%s/mask", path));
		for(Entry<Integer,IBitIndex> en:positionV2bit.entrySet()) {
			positionV2bit.put( en.getKey() ,  new BatchBitIndex( String.format("%s/positionV2bit/%s", path,en.getKey()) ));
		}
	}
	@Override
	public void load(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			throw new IndexException(String.format("%s is not exists", path));
		}
		mask = new BitIndex();
		mask.load(String.format("%s/mask", path));
		File pvs = new File(String.format("%s/positionV2bit", path));
		IBitIndex posi = null;
		positionV2bit.clear();
		for(File pv:pvs.listFiles()) {
			posi = new BitIndex();
			posi.load(String.format("%s/positionV2bit/%s",path, pv.getName()));
			positionV2bit.put(Integer.parseInt(pv.getName()), posi);
		}
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		mask.init(String.format("%s/mask", path));
		File pvs = new File(String.format("%s/positionV2bit", path));
		pvs.mkdirs();
		for(Entry<Integer,IBitIndex> en:positionV2bit.entrySet()) {
			en.getValue().init(String.format("%s/positionV2bit/%s", path,en.getKey()));
		}
	}

	
	public void equealsStr(IBitIndex cache, IBitIndex now, Object value) {
		String v = value.toString();
		int num = 0;
		int len = v.length();
		for(int i=0;i<11;i++) {
			if(len>i) {
				if(v.charAt(i)=='*') {
					
					cache.andNot(positionV2bit.get(i*4+3));
					cache.and(positionV2bit.get(i*4+2));
					cache.andNot(positionV2bit.get(i*4+3));
					cache.and(positionV2bit.get(i*4));
				}else {
					num = v.charAt(i)-48;
					if((num&1)==1) {
						cache.and(positionV2bit.get(i*4+3));
					}else {
						cache.andNot(positionV2bit.get(i*4+3));
					}
					if((num&2)==2) {
						cache.and(positionV2bit.get(i*4+2));
					}else {
						cache.andNot(positionV2bit.get(i*4+2));
					}
					if((num&4)==4) {
						cache.and(positionV2bit.get(i*4+1));
					}else {
						cache.andNot(positionV2bit.get(i*4+1));
					}
					if((num&8)==8) {
						cache.and(positionV2bit.get(i*4));
					}else {
						cache.andNot(positionV2bit.get(i*4));
					}
				}
			}else {
				cache.and(positionV2bit.get(i*4+3));
				cache.and(positionV2bit.get(i*4+2));
				cache.and(positionV2bit.get(i*4+3));
				cache.and(positionV2bit.get(i*4));
			}
		}
	}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number value) {
	}
	
	@Override
	public void greater(IBitIndex cache, IBitIndex now, Number value) {
	}

	@Override
	public void less(IBitIndex cache, IBitIndex now, Number value) {
	}

	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now, Number value) {
	}

	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now, Number value) {
	}

	@Override
	public void set(int rowId, Object value) {
		if(value==null) {
			mask.set(rowId, false);
		}else {
			String v = value.toString();
			int num = 0;
			int len = v.length();
			for(int i=0;i<11;i++) {
				if(len>i) {
					if(v.charAt(i)=='*') {
						positionV2bit.get(i*4+3).set(rowId,false);
						positionV2bit.get(i*4+2).set(rowId,true);
						positionV2bit.get(i*4+1).set(rowId,false);
						positionV2bit.get(i*4  ).set(rowId,true);
					}else {
						num = v.charAt(i)-48;
						positionV2bit.get(i*4+3).set(rowId,( (num&1)==1));
						positionV2bit.get(i*4+2).set(rowId,( (num&2)==2));
						positionV2bit.get(i*4+1).set(rowId,( (num&4)==4));
						positionV2bit.get(i*4  ).set(rowId,( (num&8)==8));
					}
				}else {
//					System.out.println("需要忽略的字符 at :"+i);
					//需要忽略的字符。
					positionV2bit.get(i*4+3).set(rowId,true);
					positionV2bit.get(i*4+2).set(rowId,true);
					positionV2bit.get(i*4+1).set(rowId,true);
					positionV2bit.get(i*4  ).set(rowId,true);
				}
				
			}
			mask.set(rowId);
			mask.flush(rowId);
			positionV2bit.forEach((k,p)->{
				p.flush(rowId);
			});
		}
	}

	@Override
	public String get(int rowId) {
		if(!mask.get(rowId)) {
			return null;
		}
		StringBuffer r = new StringBuffer();
		int v = 0;
		for(int i=0;i<11;i++) {
			//i*4+3
			v= (positionV2bit.get(i*4+3).get(rowId)?1:0)+
			   (positionV2bit.get(i*4+2).get(rowId)?2:0)+
			   (positionV2bit.get(i*4+1).get(rowId)?4:0)+
			   (positionV2bit.get(i*4).get(rowId)?8:0);
			if(v<10) {
				r.append(v);
			}else if(v==10) {
				r.append("*");
			}else {
				break;//后边为忽略字符。
			}
		}
		return r.toString();
	}

	@Override
	public void expr(String method, Object v, List<IBitIndex> caches) {
		// TODO Auto-generated method stub
		if("=".equals(method)) {
			this.equealsStr(caches.get(0),null, v);
		}else if("contains".equals(method.toLowerCase())){
			this.contains(caches.get(0), caches.get(1), v.toString());
		}else if("positionmatch".equals(method.toLowerCase())){
			this.positionMatch(caches.get(0), v.toString());
		}else if("has_every_char".equals(method.toLowerCase())){
			this.hasEveryChar(caches.get(0), caches.get(1), v.toString());
		}else {
			throw new IndexException(  this.getClass().getName()+ " not support " + method);
		}
	}

	
	
	
	private IBitIndex have(int index,int number,IBitIndex r) {
		if((number&1)==1) {
			r.and(positionV2bit.get(index*4+3));
		}else {
			r.andNot(positionV2bit.get(index*4+3));
		}
		
		if((number&2)==2) {
			r.and(positionV2bit.get(index*4+2));
		}else {
			r.andNot(positionV2bit.get(index*4+2));
		}
		
		if((number&4)==4) {
			r.and(positionV2bit.get(index*4+1));
		}else {
			r.andNot(positionV2bit.get(index*4+1));
		}
		
		if((number&8)==8) {
			r.and(positionV2bit.get(index*4));
		}else {
			r.andNot(positionV2bit.get(index*4));
		}
		return r;
	}
	
	private void location(IBitIndex r,int start,String is) {
		char now;
		for(int i=0;i<is.length();i++) {
			now = is.charAt(i);
			if('*'==now) {
				have(start+i, 10, r);
			}else {
				have(start+i,now -48, r);
			}
			
		}
	}
	
	private void hasEveryChar(IBitIndex cache,IBitIndex tmp,String is) {
		cache.setAll(false);
		for(int i=0;i<11;i++) {
			for(char now:is.toCharArray()) {
				tmp.setAll(true);
				have(i,now -48, tmp);
				cache.or(tmp);
			}
		}
		cache.and(mask);
	}
	private void contains(IBitIndex cache,IBitIndex tmp,String is) {
		cache.setAll(false);
		for(int i=0;i<=11-is.length();i++) {
			tmp.setAll(true);
			location(tmp, i, is);
			cache.or(tmp);
		}
		cache.and(mask); 
	}
	
	private void positionMatch(IBitIndex cache,String mode) {
		cache.setAll(true);
		for(int i=0;i<mode.length();i++) {
			if('_'!=mode.charAt(i)) {
				have(i, mode.charAt(i)-48,  cache);
			}
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		PhoneIndex t = new PhoneIndex();
		t.set(0, "13721192609");
		t.set(1, "13621192609");
		t.set(2, "13521192609");
		t.set(3, "13421192609");
		t.set(4, "13421192");
		t.set(5, "13421192");
		t.set(6, "13421192");
		
		System.out.println(t.get(0));
		System.out.println(t.get(1));
		System.out.println(t.get(2));
		System.out.println(t.get(3));
		System.out.println(t.get(4));
		System.out.println(t.get(5));
		System.out.println(t.get(6));
		IBitIndex cache = new BitIndex();
		cache.flush(6);
		
		t.save("C:/port/xuanYue2");
		PhoneIndex t2 = new PhoneIndex();
		t2.load("C:/port/xuanYue/c_number");
		IBitIndex tmp = new BitIndex();
		tmp.flush(6);
		System.out.println("t2.contains(cache, tmp, \"621\")");
		t2.contains(cache, tmp, "621");
		System.out.println(cache.getIndexOftrue());
		System.out.println(t2.mask.get(5));
		cache.setAll(true);
		t2.have(1, 3, cache);
		System.out.println("have(1, 3, cache):"+cache.getIndexOftrue());
		cache.setAll(true);
		t2.positionMatch(cache, "135___92609");
		System.out.println(cache.get(6));
		System.out.println(cache.getIndexOftrue());
		
		t.contains(cache, tmp, "111");
		System.out.println(" 111 contains " +cache.getIndexOftrue());
	}
	@Override
	public int getDataSize() {
		return positionV2bit.size()+1;
	}
	@Override
	public List<ISortElement> getSortE(boolean isDesc,String ...names) {
		List<ISortElement> r = new ArrayList<>();
		for(int i=0;i<44;i++) {
			SortElement e = new SortElement();
			e.setData(positionV2bit.get(i));
			e.setDesc(isDesc);
			r.add(e);
		}
		return r;
	}
	@Override
	public byte getType() {
		return 8;
	}
	@Override
	public void saveRow(String path, int rowId) throws Exception {
		mask.saveRow(String.format("%s/mask", path),rowId);
		for(Entry<Integer,IBitIndex> en:positionV2bit.entrySet()) {
			en.getValue().saveRow(String.format("%s/positionV2bit/%s", path,en.getKey()),rowId);
		}
		
	}
}

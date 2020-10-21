package com.xuanyue.db.xuan.antlr.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.xuanyue.db.xuan.SeachContext;
import com.xuanyue.db.xuan.antlr.BitQBaseListener;
import com.xuanyue.db.xuan.antlr.BitQParser.AndConditionContext;
import com.xuanyue.db.xuan.antlr.BitQParser.AndNotContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ConditionElementContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ConditionExprContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ExprContext;
import com.xuanyue.db.xuan.antlr.BitQParser.FullNameContext;
import com.xuanyue.db.xuan.antlr.BitQParser.GroupConditionContext;
import com.xuanyue.db.xuan.antlr.BitQParser.LimitContext;
import com.xuanyue.db.xuan.antlr.BitQParser.MixContext;
import com.xuanyue.db.xuan.antlr.BitQParser.OrConditionContext;
import com.xuanyue.db.xuan.antlr.BitQParser.OrNotContext;
import com.xuanyue.db.xuan.antlr.BitQParser.Phone_seachContext;
import com.xuanyue.db.xuan.antlr.BitQParser.RepoContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ResultContext;
import com.xuanyue.db.xuan.antlr.BitQParser.SortByContext;
import com.xuanyue.db.xuan.antlr.BitQParser.SortEContext;
import com.xuanyue.db.xuan.antlr.BitQParser.To_dateContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ValuesContext;
import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.exception.SQLException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.core.table.IXyTable;
import com.xuanyue.db.xuan.core.table.sort.LimitHandler;
/**
 * 面向排序和分页的sql解析。
 * 一次查询，返回 记录总数，和结果结果集
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月16日
 *
 */
public class BitQListenerImpl extends BitQBaseListener{
	
	private IXyTable table;//表
	private int maxSouce;//最大检索资源。
	private List<String> fl = new ArrayList<>();//列名称
	private List<Map<Integer,Object>> r = new ArrayList<>();//结果
	private int count;//记录数
	private byte[] types;//列数据类型
	private Map<Integer,Object> parameters;//传入参数
	
	public BitQListenerImpl(int maxSource) {
		this.maxSouce = maxSource;
	}
	
	public void setParameters(Map<Integer,Object> parameters) {
		this.parameters=parameters;
	}
	public byte[] getTypes() {
		return types;
	}
	public List<String> getFieldNames(){
		return fl;
	}
	public int getCount() {
		return this.count;
	}
	public List<Map<Integer,Object>> getData(){
		return r;
	}
	@Override
	public void exitResult(ResultContext ctx) {
		List<FullNameContext> fs = ctx.fullName();
		fl = new ArrayList<>();
		if(fs!=null) {
			FullNameContext f = null;
			for(int i=0;i<fs.size();i++) {
				f = fs.get(i);
				String fn = f.getText().toLowerCase();
				fl.add(fn);
			}
		}
	}
	
	@Override
	public void exitRepo(RepoContext ctx) {
		List<FullNameContext> fnl = ctx.fullName();
		if(fnl.size()==0) {
			throw new IndexException("ERROR at from");
		}
		List<TerminalNode> lasts = fnl.get(fnl.size()-1).NAME();
		String tn = lasts.get(lasts.size()-1).getText().toLowerCase();
		
		try {
			table = SeachContext.getTable(tn);
			types = new byte[fl.size()];
			for(int i=0;i<fl.size();i++) {
				types[i]=table.getType(fl.get(i));
			}
		} catch (Exception e1) {
			throw new IndexException("ERROR at table "+tn+" exist?",e1);
		}
	}
	@Override
	public void exitExpr(ExprContext ctx) {
		//数据表
		RepoContext repo = ctx.repo();
		if(repo==null) {
			throw new SQLException("ERROR at from");
		}
		//where
		OrConditionContext or= ctx.orCondition();
		List<IBitIndex> caches = null;// 第0个IBitIndex是 过滤结果后
		try {
			caches = table.apply(maxSouce);
			if(or!=null) {
				//执行where过滤
				handleOr(or,caches,0);
			}else {
				caches.get(0).copyFrom( table.getMask() );
			}
			//统计命中条数
			this.count = caches.get(0).cardinality();
			//分页  默认offset=0 然后取出5条记录，语法类似mysql 的limit
			LimitContext limit = ctx.limit();
			int offset = 0;
			int num = 5;
			if(limit!=null) {
				List<TerminalNode> on = limit.NUM();
				if(on.size()==2) {
					offset = Integer.parseInt(on.get(0).getText().trim());
					num = Integer.parseInt(on.get(1).getText().trim());
				}else if(on.size()==1) {
					num = Integer.parseInt(on.get(1).getText().trim());
				}else {
					throw new SQLException("ERROR limit ");
				}
			}
			//排序
			SortByContext sort = ctx.sortBy();
			List<ISortElement> sl = new ArrayList<>();
			if(sort!=null) {
				List<SortEContext> el = sort.sortE();
				for(SortEContext e:el) {
					FullNameContext fnc = e.fullName();
					List<TerminalNode> ns = fnc.NAME();
					TerminalNode key = e.STRING();//Map类型的字段时按照摸个key对应的value排序
					if(key!=null) {
						sl.addAll( table.getSortE(ns.get(ns.size()-1 ).getText(), e.ASC()==null,valueOfStr( key.getText() )) );
					}else {
						sl.addAll( table.getSortE(ns.get(ns.size()-1 ).getText(), e.ASC()==null,null ) );
					}
				}
			}
			List<Integer> indexs = new ArrayList<>();
			
			MixContext mix = ctx.mix();
			if(mix!=null) {
				//混合排序 见函数 mixLimit
				FullNameContext fnc = mix.fullName();
				List<TerminalNode> ns = fnc.NAME();
				IBitIndex where_r = caches.get(0);
				List<TerminalNode> numl = mix.NUM();
				
				String fn = ns.get(ns.size()-1 ).getText().toLowerCase();
				List<IBitIndex> cachesSub = null;//
				List<Ele> el = new ArrayList<>();
				IBitIndex out = caches.get(1);//// 第0个IBitIndex是 过滤结果后   第1和存放维度数据的集合。
				out.setAll(false);
				for(TerminalNode n:numl) {
					String key = n.getText();
					cachesSub = caches.subList(2, caches.size());
					table.expr(fn, "=", Integer.parseInt(key), cachesSub );
					cachesSub.get(0).and(where_r);
					Ele e = new Ele();
					e.setKey(key);
					e.setCount(cachesSub.get(0).cardinality());
					out.or(cachesSub.get(0));
					el.add(e);
				}
				
				int count = mixLimit(el, offset, num);
				if(count==0) {
					//mix的维度数据不足  offset ，则排除维度数据，然后正常排序分页即可
					where_r.andNot(out);
					int cx = 0;//各个维度数据的条数加和
					for(Ele ele:el) {
						cx+=ele.getCount();
					}
					//非维度数据的排序分页
					LimitHandler handler = new LimitHandler(caches,sort!=null,table.getMask());
					indexs.addAll(handler.limit(sl, offset-cx, num));
				}else if(count<num) {
					//mix的维度数据不足  offset+num个,需要一个补集数据。
					//1、在补集上取剩余的数据。 num-count条
					out.not();
					out.and(where_r);
					cachesSub = caches.subList(1, caches.size());
					LimitHandler handler = new LimitHandler(cachesSub,sort!=null,table.getMask());
					List<Integer> outIndexL = handler.limit(sl, 0, num-count);
					//2、取得mix的维度数据
					//cachesSub = caches.subList(1, caches.size());
					IBitIndex where1 = cachesSub.get(0);
					for(Ele ele:el) {
						if(ele.getNum()>0) {//本维度需要取ele.getNum()条数据
							table.expr(fn, "=", Integer.parseInt(ele.getKey().trim()), cachesSub );
							where1.and(where_r);
							indexs.addAll(handler.limit(sl, ele.getStart(), ele.getNum()));
						}
					}
					indexs.addAll(outIndexL);
				}else if(count==num){
					//mix的维度数据满足  offset+num个
					cachesSub = caches.subList(1, caches.size());
					LimitHandler handler = new LimitHandler(cachesSub,sort!=null,table.getMask());
					IBitIndex where1 = cachesSub.get(0);
					for(Ele ele:el) {
						if(ele.getNum()>0) {
							table.expr(fn, "=", Integer.parseInt(ele.getKey().trim()), cachesSub );
							where1.and(where_r);
							indexs.addAll(handler.limit(sl, ele.getStart(), ele.getNum()));
						}
					}
				}else {
					throw new IndexException("ERROR mix");
				}
			}else {
				LimitHandler handler = new LimitHandler(caches,sort!=null,table.getMask());
				indexs.addAll(handler.limit(sl, offset, num));
			}
			//组织结果
			Map<Integer,Object> rx = null;
			Map<String,Object> tmp = null;
			for (int j:indexs) {
				rx = new HashMap<>();
				tmp = table.read(j);
				for(int i=0;i<fl.size();i++) {
					rx.put(i,tmp.get(fl.get(i)));
				}
				r.add(rx);
			}
		} catch (Exception e) {
			throw new IndexException(e);
		}finally {
			if(caches!=null) {
				table.returnSource(caches);
			}
		}
		
	}


	private void handleOr(OrConditionContext or,List<IBitIndex> caches,int from) {
		List<AndConditionContext> andlist = or.andCondition();
		List<OrNotContext> ons = or.orNot();
		
		IBitIndex r = caches.get(from);
		r.setAll(false);
		handleAnd(andlist.get(0),caches,from+1);
		r.or(caches.get(from+1));
		for(int i=1;i<andlist.size();i++) {
			handleAnd(andlist.get(i),caches,from+1);
			if(null!=ons.get(i-1).NOT()) {
				r.orNot(caches.get(from+1));
			}else {
				r.or(caches.get(from+1));
			}
			
		}
		
	}
	private void handleAnd(AndConditionContext and,List<IBitIndex> caches,int from) {
		List<ConditionElementContext> el = and.conditionElement();
		List<AndNotContext> ans = and.andNot();
		
		IBitIndex r = caches.get(from);
		r.setAll(true);
		
		ConditionElementContext e = el.get(0);
		GroupConditionContext group = e.groupCondition();
		if(group!=null) {
			handleGroupCondition(group,caches,from +1);
		}else {
			handleConditionExpr(e.conditionExpr(),caches,  from+1);
		}
		r.and(caches.get(from +1));
		for(int i=1;i<el.size();i++) {
			e = el.get(i);
			group = e.groupCondition();
			if(group!=null) {
				handleGroupCondition(group,caches,from +1);
			}else {
				handleConditionExpr(e.conditionExpr(),caches,  from+1);
			}
			if(ans.get(i-1).NOT()!=null){
				r.andNot(caches.get(from +1));
			}else {
				r.and(caches.get(from +1));
			}
		}
	}
	private void handleGroupCondition(GroupConditionContext group,List<IBitIndex> caches,int from) {
		OrConditionContext or = group.orCondition();
		handleOr(or,caches,from);
	}
	private void handleConditionExpr(ConditionExprContext expr,List<IBitIndex> caches,int from) {
		Phone_seachContext phone = expr.phone_seach();
		
		if(phone!=null) {
			FullNameContext fn = phone.fullName();
			String method = phone.op.getText().toLowerCase();
			//PositionMatch|Contains|Has_Every_Char
			if("positionmatch".equals(method)||"contains".equals(method)||"has_every_char".equals(method)) {
				;
			}else {
				throw new IndexException("method : "+method +" not support");
			}
			
			table.expr(fn.getText(), method, valueOfStr(phone.STRING().getText()), caches);
			return;
		}
		
		
		FullNameContext fn = expr.fullName();
		String method = expr.op.getText();

		if("=".equals(method)) {
			;
		}else if(">=".equals(method)){
			;
		}else if(">".equals(method)){
			;
		}else if("<=".equals(method)){
			;
		}else if("<".equals(method)){
			;
		}else if("contains".equals(method.toLowerCase())){
			;
		}else if("positionmatch".equals(method.toLowerCase())){
			;
		}else if("phone_seach".equals(method.toLowerCase())){
			;
		}else{
			throw new IndexException("method : "+method +" not support");
		}
		ValuesContext value = expr.values();
		Object v = null;
		TerminalNode sv = value.STRING();
		if(sv!=null) {
			v = valueOfStr(sv.getText());
		}
		
		TerminalNode iv = value.NUM();
		if(iv!=null) {
			String ivs = iv.getText();
			if(ivs.contains(".")  ||ivs.contains("f")) {
				v = Float.parseFloat(ivs);
			}else if(ivs.contains("l")){
				v = Long.parseLong( ivs);
			}else {
				v = Integer.parseInt(ivs);
			}
		}
		
		To_dateContext to_data = value.to_date();
		
		if(to_data!=null) {
			List<TerminalNode> ss =to_data.STRING();
			SimpleDateFormat formatter = new SimpleDateFormat(valueOfStr(ss.get(0).getText()));
			try {
				v = formatter.parse(valueOfStr(ss.get(1).getText()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if("true".equals( value.getText().toLowerCase() )) {
			v = true;
		}else if("false".equals( value.getText().toLowerCase() )) {
			v = false;
		}
		
		TerminalNode transAttr = value.TransArrt();
		if(transAttr!=null) {
			String ta = transAttr.getText();
			v =parameters.get(  Integer.parseInt( ta.substring( 1 , ta.length()) ));
		}
		
		//log.debug(String.format("%s %s %s ", fn.getText(),method,v));
		table.expr(fn.getText(), method, v, caches.subList(from, caches.size()));
	}
	private String valueOfStr(String text) {
		return (text==null?null:text.substring(1, text.length()-1));
	}
	
	/**
	 * 阶梯函数
	 * 计算多维度混合排序。
	 * 例如：
	 *   数据是分成三类：
	 *   男装：8件   
	 *   女装    10件
	 *   童装    11件。
	 *  
	 *  现在需要先按照价格排序，分页查询，页面大小为8，并且要求按类别纵向编号
	 *   男装：   M1  M2  M3  M4  M5  M6  M7  M8
	 *   女装：   N1  N2  N3  N4  N5  N6  N7  N8   N9  N10
	 *   童装：   T1  T2  T3  T4  T5  T6  T7  T8   T9   T10   T11
	 *   
	 *   上面的得到顺序为：
	 *   M1,N1,T1 ,M2,N2,T2 ,M3,N3,T3 ,M4,N4,T4, M5,N5,T5, M6,N6,T6, M7,N7,T7, M8,N8,T8, N9,T9, N10,T10, T11
	 *   
	 *   则第一页为如下方括号包含的商品。
	 *   男装： [M1  M2  M3]  M4  M5  M6  M7  M8
	 *   女装： [N1  N2  N3]  N4  N5  N6  N7  N8   N9  N10
	 *   童装： [T1  T2]  T3  T4  T5  T6  T7  T8   T9   T10   T11
	 *   
	 *   则第二页为如下方括号包含的商品。
	 *   男装： M1  M2  M3  [M4  M5  M6]  M7  M8
	 *   女装： N1  N2  N3  [N4  N5] N6   N7  N8   N9  N10
	 *   童装： T1  T2 [T3  T4  T5]  T6   T7  T8   T9   T10   T11
	 *   
	 * @param el
	 * @param offset
	 * @param num
	 */
	private static int mixLimit(List<Ele> el,int offset,int num) {
		Collections.sort(el);
		Ele e = null;
		int start = 0;
		out:for(int i=0;i<el.size();i++) {
			e = el.get(i);
			if(offset<=(e.getCount()-start)*(el.size()-i)) {
				//记录起始位置。
				int x = offset%(el.size()-i);
				for(int j=i;j<el.size();j++) {
					if(x>0) {
						el.get( j ).setStart(offset/(el.size()-i) + start+1);
						x--;
					}else {
						el.get( j ).setStart(offset/(el.size()-i) + start);
					}
				}
				//计算结束位置
				offset+=num;
				for(int j=i;j<el.size();j++) {
					e= el.get(j);
					if(offset<=(e.getCount()-start)*(el.size()-j)) {
						x = offset%(el.size()-j);
						for(int k=j;k<el.size();k++) {
							if(x>0) {
								el.get( k ).setNum(offset/(el.size()-j) + start+1);
								x--;
							}else {
								el.get( k ).setNum(offset/(el.size()-j) + start);
							}
						}
						break out;//
					}else {
						e.setNumSurplus();
						offset-=(e.getCount()-start)*(el.size()-j);
						start=e.getCount();
						
					}
				}
				break out;//
			}else {
				offset-=(e.getCount()-start)*(el.size()-i);
				start=e.getCount();
				
			}
		}
		
		int c = 0;
		for(Ele ele:el) {
			c+=ele.getNum();
		}
		return c;
	}
	
	
	
	
}

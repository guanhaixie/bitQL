package com.xuanyue.db.xuan.antlr.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.xuanyue.db.xuan.SeachContext;
import com.xuanyue.db.xuan.antlr.BitQBaseListener;
import com.xuanyue.db.xuan.antlr.BitQParser.AndConditionContext;
import com.xuanyue.db.xuan.antlr.BitQParser.BoolTFContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ConditionElementContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ConditionExprContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ExprContext;
import com.xuanyue.db.xuan.antlr.BitQParser.FullNameContext;
import com.xuanyue.db.xuan.antlr.BitQParser.GroupConditionContext;
import com.xuanyue.db.xuan.antlr.BitQParser.LimitContext;
import com.xuanyue.db.xuan.antlr.BitQParser.MixContext;
import com.xuanyue.db.xuan.antlr.BitQParser.OrConditionContext;
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
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.IXyTable;
import com.xuanyue.db.xuan.msg.X2YValue;

/**
 * 1、计算所需最大内存资源
 * 2、检查sql 是否有错误
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年11月24日
 *
 */
public class ProofreaderListenerImpl extends BitQBaseListener{
	private IXyTable table;//表
	private List<String> fl = new ArrayList<>();//列名称
//	private Map<String,VLAUETYPE> types = new HashMap<>();//列数据类型
	private Map<String,Object> parameters;//传入参数
	
	private int maxId=0;
	
	private void flushMaxId(int maxId) {
		if(this.maxId<maxId) {
			this.maxId = maxId;
		}
	}
	
	public int getMaxSource() {
		return maxId+1;
	}
	public void setParameters(Map<String,X2YValue> pV) {
		this.parameters=new HashMap<>();
		if(pV!=null) {
			pV.forEach(  (k,v)->{
				parameters.put(k, v.value());
			});
		}
	}
	public List<String> getFieldNames(){
		return fl;
	}
	
	@Override
	public void exitResult(ResultContext ctx) {
		List<FullNameContext> fs = ctx.fullName();
		fl = new ArrayList<>();
		if(fs!=null) {
			FullNameContext f = null;
			for(int i=0;i<fs.size();i++) {
				f = fs.get(i);
				fl.add(f.getText().toLowerCase());
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
		table = SeachContext.getTable(tn);
		if(table==null) {
			throw new SQLException(String.format("table %s is not exists", tn));
		}
		for(int i=0;i<fl.size();i++) {
			if(!"rowid".equals(fl.get(i) )) {
				IColumn c=table.getColumn(fl.get(i));
				if(c==null) {
					throw new SQLException(String.format("column %s is not exists", fl.get(i)));
				}
			}
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
			
		if(or!=null) {
			//执行where过滤
			handleOr(or,null,0);
		}else {
			flushMaxId(0);
		}
		//统计命中条数
//			this.count = caches.get(0).cardinality();
		//分页  默认offset=0 然后取出5条记录，语法类似mysql 的limit
		LimitContext limit = ctx.limit();
		if(limit!=null) {
			List<TerminalNode> on = limit.NUM();
			try {
				if(on.size()==2) {
					Integer.parseInt(on.get(0).getText().trim());
					Integer.parseInt(on.get(1).getText().trim());
				}else if(on.size()==1) {
					Integer.parseInt(on.get(1).getText().trim());
				}
			} catch (Exception e) {
				throw new SQLException("ERROR limit ");
			} 
			if(on.size()!=2&&on.size()!=1){
				throw new SQLException("ERROR limit ");
			}
		}
		//排序
		SortByContext sort = ctx.sortBy();
		if(sort!=null) {
			List<SortEContext> el = sort.sortE();
			for(SortEContext e:el) {
				FullNameContext fnc = e.fullName();
				List<TerminalNode> ns = fnc.NAME();
				TerminalNode key = e.STRING();//Map类型的字段时按照摸个key对应的value排序
				String fn = ns.get(ns.size()-1 ).getText();
				IColumn c = table.getColumn(fn);
				if(c==null|| 
						!c.checkSortE(false, key!=null?valueOfStr( key.getText() ):null )
						) {
					throw new SQLException("ERROR at sort :"  + e.getText() );
				}
			}
		}
		
		MixContext mix = ctx.mix();
		if(mix!=null) {
			//混合排序 见函数 mixLimit
			FullNameContext fnc = mix.fullName();
			List<TerminalNode> ns = fnc.NAME();
			List<TerminalNode> numl = mix.NUM();
			
			String fn = ns.get(ns.size()-1 ).getText().toLowerCase();
			flushMaxId(2);
			for(TerminalNode n:numl) {
				String key = n.getText();
				int t = table.checkExpr(fn, "=", Integer.parseInt(key) );
				if(t==0) {
					throw new SQLException("ERROR at sort :"  + fn+" not support mix sort" );
				}
				flushMaxId(t+2);
			}
			flushMaxId(6);
		}else {
			flushMaxId(4);
		}
	}


	private void handleOr(OrConditionContext or,List<IBitIndex> caches,int from) {
		flushMaxId(from);
		List<AndConditionContext> andlist = or.andCondition();
		if(andlist==null||andlist.size()==0) {
			throw new SQLException("ERROR at where : not has expr" );
		}
		handleAnd(andlist.get(0),caches,from+1);
		for(int i=1;i<andlist.size();i++) {
			handleAnd(andlist.get(i),caches,from+1);
		}
	}
	private void handleAnd(AndConditionContext and,List<IBitIndex> caches,int from) {
		flushMaxId(from);
		List<ConditionElementContext> el = and.conditionElement();
		ConditionElementContext e = el.get(0);
		GroupConditionContext group = e.groupCondition();
		if(group!=null) {
			handleGroupCondition(group,caches,from +1);
		}else {
			if(e.conditionExpr()==null) {
				throw new SQLException("ERROR at where: not has expr" );
			}
			handleConditionExpr(e.conditionExpr(),caches,  from+1);
		}
		for(int i=1;i<el.size();i++) {
			e = el.get(i);
			group = e.groupCondition();
			if(group!=null) {
				handleGroupCondition(group,caches,from +1);
			}else {
				handleConditionExpr(e.conditionExpr(),caches,  from+1);
			}
		}
	}
	private void handleGroupCondition(GroupConditionContext group,List<IBitIndex> caches,int from) {
		flushMaxId(from);
		OrConditionContext or = group.orCondition();
		handleOr(or,caches,from);
	}
	private void handleConditionExpr(ConditionExprContext expr,List<IBitIndex> caches,int from) {
		flushMaxId(from);
		Phone_seachContext phone = expr.phone_seach();
		if(phone!=null) {
			FullNameContext fn = phone.fullName();
			String method = phone.op.getText().toLowerCase();
			if("positionmatch".equals(method)||"contains".equals(method)||"has_every_char".equals(method)) {
				;
			}else {
				throw new SQLException("method : "+method +" not support");
			}
			
			int x = table.checkExpr(fn.getText(), method, valueOfStr(phone.STRING().getText()));
			if(x==0) {
				throw new SQLException("ERROR at : "+ phone.getText());
			}else {
				flushMaxId(from+x);
			}
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
			throw new SQLException("method : "+method +" not support");
		}
		
		
		ValuesContext value = expr.values();
		if(value==null) {
			throw new SQLException("ERROR  expr value is null : "+expr.getText());
		}
		
		Object v = null;
		TerminalNode sv = value.STRING();
		if(sv!=null) {
			v = valueOfStr(sv.getText());
		}
		
		TerminalNode iv = value.NUM();
		if(iv!=null) {
			String ivs = iv.getText();
			
			try {
				if(ivs.contains(".")  ||ivs.contains("f")) {
					v = Float.parseFloat(ivs);
				}else if(ivs.contains("l")){
					v = Long.parseLong( ivs.substring(0, ivs.length()-1));
				}else {
					v = Integer.parseInt(ivs);
				}
			} catch (NumberFormatException e) {
				throw new SQLException("ERROR  expr value type  : "+expr.getText());
			}
		}
		
		To_dateContext to_date = value.to_date();
		
		if(to_date!=null) {
			List<TerminalNode> ss =to_date.STRING();
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(valueOfStr(ss.get(0).getText()));
				v = formatter.parse(valueOfStr(ss.get(1).getText()));
			} catch (Exception e) {
				throw new SQLException("error at : "+to_date.getText());
			}
		}
		String vStr = value.getText();
		if("true".equals(vStr)) {
			v=true;
		}else if("false".equals(vStr)) {
			v=false;
		}
		
		TerminalNode transAttr = value.TransArrt();
		if(transAttr!=null) {
			String ta = transAttr.getText();
			ta = ta.substring( 1 , ta.length());
			v =parameters.get(ta);
			if(!parameters.containsKey(ta)) {
				throw new SQLException("can not get the parameter  : "+expr.getText());
			}
		}
		
		//log.debug(String.format("%s %s %s ", fn.getText(),method,v));
		int x = table.checkExpr(fn.getText(), method, v);
		if(x==0) {
			throw new IndexException("ERROR at : "+expr.getText());
		}else {
			flushMaxId(from+x);
		}
	}
	private String valueOfStr(String text) {
		return (text==null?null:text.substring(1, text.length()-1));
	}
}

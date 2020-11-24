package com.xuanyue.db.xuan.antlr.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import com.xuanyue.db.xuan.antlr.BitQParser.MixContext;
import com.xuanyue.db.xuan.antlr.BitQParser.OrConditionContext;
import com.xuanyue.db.xuan.antlr.BitQParser.OrNotContext;
import com.xuanyue.db.xuan.antlr.BitQParser.Phone_seachContext;
import com.xuanyue.db.xuan.antlr.BitQParser.RepoContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ResultContext;
import com.xuanyue.db.xuan.antlr.BitQParser.SortByContext;
import com.xuanyue.db.xuan.antlr.BitQParser.To_dateContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ValuesContext;
import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IXyTable;
/**
 * 解析sql,计算使用的最大资源数，用于银行家算法申请资源
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月16日
 *
 */
public class BitMaxSourceListenerImpl extends BitQBaseListener{

	private IXyTable table;//表
	private List<String> fl = new ArrayList<>();
	private List<String> errorInfos = new ArrayList<>();
	private int maxId=0;
	
	public int getMaxSource() {
		return maxId+1;
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
			errorInfos.add(String.format( "table %s is not exists" , tn));
		}else {
			for(int i=0;i<fl.size();i++) {
				if(!"rowid".equals(fl.get(i) )&& null==table.getColumn( fl.get(i) )) {
					errorInfos.add(String.format("column  %s is not exists", fl.get(i)));
				}
			}
		}
	}
	
	@Override
	public void exitExpr(ExprContext ctx) {
		OrConditionContext or= ctx.orCondition();
		if(or!=null) {
			handleOr(or,0);
		}
//		LimitContext limit = ctx.limit();
		SortByContext sort= ctx.sortBy();
		if(sort!=null) {
			fulshMaxId(4);
		}
		MixContext mix = ctx.mix();
		
		if(mix!=null) {
			if(sort!=null) {
				fulshMaxId(5);
			}else {
				fulshMaxId(2);
			}
		}
		super.exitExpr(ctx);
	}


	private void fulshMaxId(int id) {
		maxId=(maxId<id?id:maxId);
	}
	private void handleOr(OrConditionContext or,int from) {
		fulshMaxId(from);
		List<AndConditionContext> andlist = or.andCondition();
		List<OrNotContext> ons = or.orNot();
		handleAnd(andlist.get(0),from+1);
		for(int i=1;i<andlist.size();i++) {
			System.out.print("or ");
			if(null!=ons.get(i-1).NOT()) {
				System.out.print("not ");
			}
			handleAnd(andlist.get(i),from+1);
		}
		
	}
	private void handleAnd(AndConditionContext and,int from) {
		fulshMaxId(from);
		List<ConditionElementContext> el = and.conditionElement();
		
		ConditionElementContext e = el.get(0);
		GroupConditionContext group = e.groupCondition();
		if(group!=null) {
			handleGroupCondition(group,from +1);
		}else {
			handleConditionExpr(e.conditionExpr(),  from+1);
		}
		for(int i=1;i<el.size();i++) {
			e = el.get(i);
			group = e.groupCondition();
			if(group!=null) {
				handleGroupCondition(group,from +1);
			}else {
				handleConditionExpr(e.conditionExpr(),  from+1);
			}
		}
	}
	private void handleGroupCondition(GroupConditionContext group,int from) {
		fulshMaxId(from);
		OrConditionContext or = group.orCondition();
		handleOr(or,from);
	}
	private void handleConditionExpr(ConditionExprContext expr,int from) {
		Phone_seachContext phone = expr.phone_seach();
		
		if(phone!=null) {
			String method = phone.op.getText().toLowerCase();
			//PositionMatch|Contains|Has_Every_Char
			if("positionmatch".equals(method)) {
				fulshMaxId(from);
			}else if("contains".equals(method)) {
				fulshMaxId(from+1);
			}else if("has_every_char".equals(method)) {
				fulshMaxId(from+1);
			}
			return;
		}
		
		
		FullNameContext fn = expr.fullName();
		String method = expr.op.getText();

		if("=".equals(method)) {
			fulshMaxId(from+1);
		}else if(">=".equals(method)){
			fulshMaxId(from+1);
		}else if(">".equals(method)){
			fulshMaxId(from+1);
		}else if("<=".equals(method)){
			fulshMaxId(from+1);
		}else if("<".equals(method)){
			fulshMaxId(from+1);
		}else if("contains".equals(method.toLowerCase())) {
			fulshMaxId(from+1);
		}
		ValuesContext value = expr.values();
		Object v = null;
		TerminalNode sv = value.STRING();
		
		if(sv!=null) {
			v = sv.getText();
		}
		
		TerminalNode iv = value.NUM();
		if(iv!=null) {
			String ivs = iv.getText();
			if(ivs.contains(".")  ||ivs.contains("f")) {
				v = Float.parseFloat(ivs);
			}else if(ivs.contains("l")){
				v = Long.parseLong( ivs.substring(0, ivs.length()-1));
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
		
		
	}
	private String valueOfStr(String text) {
		return text.substring(1, text.length()-1);
	}
	
	public static void main(String[] args) {
		
	}
}
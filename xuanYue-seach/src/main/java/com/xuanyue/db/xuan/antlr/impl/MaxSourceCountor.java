package com.xuanyue.db.xuan.antlr.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.xuanyue.db.xuan.antlr.BitQParser.AndConditionContext;
import com.xuanyue.db.xuan.antlr.BitQParser.AndNotContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ConditionElementContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ConditionExprContext;
import com.xuanyue.db.xuan.antlr.BitQParser.FullNameContext;
import com.xuanyue.db.xuan.antlr.BitQParser.GroupConditionContext;
import com.xuanyue.db.xuan.antlr.BitQParser.OrConditionContext;
import com.xuanyue.db.xuan.antlr.BitQParser.OrNotContext;
import com.xuanyue.db.xuan.antlr.BitQParser.Phone_seachContext;
import com.xuanyue.db.xuan.antlr.BitQParser.To_dateContext;
import com.xuanyue.db.xuan.antlr.BitQParser.ValuesContext;
/**
 * 资源使用统计器。
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月16日
 *
 */
public class MaxSourceCountor {
	
	private int maxId;
	
	private void fulshMaxId(int id) {
		maxId=(maxId<id?id:maxId);
	}
	public int getMaxSource() {
		return maxId+1;
	}
	public void handleOr(OrConditionContext or,int from) {
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
		List<AndNotContext> ans = and.andNot();
		
		ConditionElementContext e = el.get(0);
		GroupConditionContext group = e.groupCondition();
		if(group!=null) {
			System.out.println("( ");
			handleGroupCondition(group,from +1);
			System.out.println(") ");
		}else {
			handleConditionExpr(e.conditionExpr(),  from+1);
		}
		for(int i=1;i<el.size();i++) {
			System.out.print("and ");
			if(ans.get(i-1).NOT()!=null){
				System.out.println("not ");
			}
			e = el.get(i);
			group = e.groupCondition();
			if(group!=null) {
				System.out.println("( ");
				handleGroupCondition(group,from +1);
				System.out.println(") ");
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
			FullNameContext fn = phone.fullName();
			String method = phone.op.getText().toLowerCase();
			//PositionMatch|Contains|Has_Every_Char
			if("positionmatch".equals(method)) {
				fulshMaxId(from);
			}else if("contains".equals(method)) {
				fulshMaxId(from+1);
			}else if("has_every_char".equals(method)) {
				fulshMaxId(from+1);
			}
			System.out.println(String.format(" Phone_seach(%s,%s,%s )", fn.getText(),method,phone.STRING().getText()));
			return;
		}
		
		
		FullNameContext fn = expr.fullName();
		String method = expr.op.getText();

		if("=".equals(method)) {
			fulshMaxId(from);
		}else if(">=".equals(method)){
			fulshMaxId(from+1);
		}else if(">".equals(method)){
			fulshMaxId(from+1);
		}else if("<=".equals(method)){
			fulshMaxId(from+1);
		}else if("<".equals(method)){
			fulshMaxId(from+1);
		}
		ValuesContext value = expr.values();
//		System.out.println(value.getText());
		Object v = null;
		TerminalNode sv = value.STRING();
		
		if(sv!=null) {
			v = sv.getText();
		}
		
		TerminalNode iv = value.NUM();
		if(iv!=null) {
			String ivs = iv.getText();
			if(ivs.contains(".")) {
				v = Float.parseFloat(ivs);
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
		System.out.println(String.format("%s %s %s ", fn.getText(),method,v));
		
	}
	private String valueOfStr(String text) {
		return text.substring(1, text.length()-1);
	}
}

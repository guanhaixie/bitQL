package com.xuanyue.db.xuan.core.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.index.accelerate.BitSetMethod;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IXGHBitSet;
import com.xuanyue.db.xuan.core.task.Accelerater;
import com.xuanyue.db.xuan.core.task.ITaskSplit;
import com.xuanyue.db.xuan.core.task.TaskSplitImpl;
/**
 * bit向量  多线程加速版本
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月31日
 *
 */
public class AcceleraterBitIndex implements IBitIndex{

	private static final long serialVersionUID = 1L;
	private IBitIndex worker;
//	private long priority;
//	private X2yThreadPoolExecutor accelerate;
	private Accelerater accelerater;
	public AcceleraterBitIndex(IBitIndex worker) {
		this.worker = worker;
	}
	
	public AcceleraterBitIndex(Accelerater accelerater,IBitIndex worker) {
		this.accelerater = accelerater;
		this.worker = worker;
	}
	
	public IBitIndex getWorker() {
		return worker;
	}
	
	@Override
	public int andByCardinality(IBitIndex set) {
		if(this.accelerater==null) {
			return worker.andByCardinality(set);
		}
		ITaskSplit callable = null;
		List<ITaskSplit> callableList = new ArrayList<>();
		int cardinality = 0;
		for(Entry<Integer,IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					en.getValue(), 
					set.getSplits().get(en.getKey()),
					BitSetMethod.andByCardinality
				);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				cardinality += item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
		return cardinality;
	}

	@Override
	public void and(IBitIndex set) {
		if(this.accelerater==null) {
			worker.and(set);
			return;
		}
		List<ITaskSplit> callableList = new ArrayList<>();
		ITaskSplit callable = null;
		for(Entry<Integer, IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(en.getValue(),set.getSplits().get(en.getKey()),BitSetMethod.and);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
	}

	@Override
	public void andNot(IBitIndex set) {
		if(this.accelerater==null) {
			worker.andNot(set);
			return;
		}
		List<ITaskSplit> callableList = new ArrayList<>();
		ITaskSplit callable = null;
		for(Entry<Integer, IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					en.getValue(),
					set.getSplits().get(en.getKey()),
					BitSetMethod.andNot
				);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
	}

	@Override
	public void orByinnerAndNot(IBitIndex a, IBitIndex b) {
		if(this.accelerater==null) {
			worker.orByinnerAndNot(a, b);
			return;
		}
		List<ITaskSplit> callableList = new ArrayList<>();
		ITaskSplit callable = null;
		for(Entry<Integer, IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					a.getSplits().get(en.getKey()),
					b.getSplits().get(en.getKey()),
					BitSetMethod.orByinnerAndNot
			);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
	}

	@Override
	public void orByinnerAnd(IBitIndex a, IBitIndex b) {
		if(this.accelerater==null) {
			worker.orByinnerAnd(a, b);
			return;
		}
		
		List<ITaskSplit> callableList = new ArrayList<>();
		ITaskSplit callable = null;
		for(Entry<Integer, IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					a.getSplits().get(en.getKey()),
					b.getSplits().get(en.getKey()),
					BitSetMethod.orByinnerAnd);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
	}

	@Override
	public void copyFrom(IBitIndex sr) {
		
		if(this.accelerater==null) {
			worker.copyFrom(sr);
			return;
		}
		
		List<ITaskSplit> callableList = new ArrayList<>();
		ITaskSplit callable = null;
		for(Entry<Integer, IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					en.getValue(),
					sr.getSplits().get(en.getKey()),
					BitSetMethod.copyFrom
				);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
	}

	@Override
	public int cardinality() {
		if(this.accelerater==null) {
			return worker.cardinality();
		}
		ITaskSplit callable = null;
		List<ITaskSplit> callableList = new ArrayList<>();
		
		for(Entry<Integer,IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					en.getValue(), 
					null,
					BitSetMethod.cardinality
				);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		
		int cardinality = 0;
		for(ITaskSplit item:callableList) {
			try {
				cardinality += item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
		return cardinality;
	}

	@Override
	public int andNotByCardinality(IBitIndex set) {
		if(this.accelerater==null) {
			return worker.andNotByCardinality(set);
		}
		
		ITaskSplit callable = null;
		List<ITaskSplit> callableList = new ArrayList<>();
		int cardinality = 0;
		for(Entry<Integer,IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					en.getValue(), 
					set.getSplits().get(en.getKey()),
					BitSetMethod.andNotByCardinality
				);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				cardinality += item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
		return cardinality;
	}

	@Override
	public void or(IBitIndex set) {
		if(this.accelerater==null) {
			worker.or(set);
			return;
		}
		List<ITaskSplit> callableList = new ArrayList<>();
		ITaskSplit callable = null;
		
		for(Entry<Integer, IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					en.getValue(),
					set.getSplits().get(en.getKey()),
					BitSetMethod.or
				);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
	}

	@Override
	public void orNot(IBitIndex set) {
		if(this.accelerater==null) {
			worker.orNot(set);
			return;
		}
		List<ITaskSplit> callableList = new ArrayList<>();
		ITaskSplit callable = null;
		
		for(Entry<Integer, IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					en.getValue(),
					set.getSplits().get(en.getKey()),
					BitSetMethod.orNot
			);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
	}

	@Override
	public IBitIndex not() {
		if(this.accelerater==null) {
			worker.not();
			return this;
		}
		
		ITaskSplit callable = null;
		List<ITaskSplit> callableList = new ArrayList<>();
		for(Entry<Integer,IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					en.getValue(), 
					null,
					BitSetMethod.not
				);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
		return this;
	}

	@Override
	public void xor(IBitIndex set) {
		if(this.accelerater==null) {
			worker.xor(set);
			return;
		}
		
		List<ITaskSplit> callableList = new ArrayList<>();
		ITaskSplit callable = null;
		for(Entry<Integer, IXGHBitSet> en:worker.getSplits().entrySet()) {
			callable = new TaskSplitImpl(
					en.getValue(),
					set.getSplits().get(en.getKey()),
					BitSetMethod.xor
					);
			callableList.add(callable);
			try {
				this.accelerater.put(callable);
			} catch (InterruptedException e) {
				callable.biz();
			}
		}
		for(ITaskSplit item:callableList) {
			try {
				item.get();
			} catch (Exception e) {
				throw new IndexException(e);
			}
		}
	}
	@Override
	public boolean check(int maxId) {
		return worker.check(maxId);
	}
	@Override
	public void flush(int maxId) {
		worker.flush(maxId);
	}
	@Override
	public void set(int index, boolean value) {
		worker.set(index, value);
	}
	@Override
	public Map<Integer, IXGHBitSet> getSplits() {
		return worker.getSplits();
	}
	@Override
	public void set(int index) {
		worker.set(index);
	}
	@Override
	public boolean get(int index) {
		return worker.get(index);
	}
	@Override
	public void setAll(boolean value) {
		worker.setAll(value);
	}
	@Override
	public List<Integer> limit(int offset, int num) {
		return worker.limit(offset, num);
	}
	@Override
	public void clearButNumTrue(int count) {
		worker.clearButNumTrue(count);
	}
	@Override
	public void init(String path) throws Exception {
		worker.init(path);
	}
	
	@Override
	public void save(String path) throws Exception {
		worker.save(path);
	}
	@Override
	public void saveRow(String path, int rowId) throws Exception {
		worker.saveRow(path, rowId);
	}
	@Override
	public void load(String path) throws Exception {
		worker.load(path);
	}
	@Override
	public List<Integer> getIndexOftrue() {
		return worker.getIndexOftrue();
	}
	@Override
	public int nextSetBit(int from) {
		return worker.nextSetBit(from);
	}
	@Override
	public int nextClearBit(int from) {
		return worker.nextClearBit(from);
	}
}

package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.io.RandomAccessFile;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.tools.Savor;

/**
 * 批量导入模式的 bit向量
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月24日
 *
 */
public class BatchBitIndex extends BitIndex {
	private static final long serialVersionUID = 1L;
	private int split =0;
	private XGHBitSet splitSet = new XGHBitSet();
	private byte[] cache = new byte[XGHBitSet.size()];
	private String path;
	public BatchBitIndex(String path) {
		this.path = path;
	}
	
	@Override
	public void set(int index, boolean value) {
		int split = getSplit(index);
		if(this.split!=split) {
			try {
				splitSet.flush(XGHBitSet.SET_SIZE-1);
				this.save(path);
			} catch (Exception e) {
				throw new IndexException(String.format("%s save ERROR", path),e);
			}
			this.split = split;
			splitSet.clearAll();//清零
		}
		splitSet.set(index%XGHBitSet.SET_SIZE, value);
		if(this.maxId<index) {
			this.maxId = index;
		}
	}

	@Override
	public synchronized void flush(int maxId) {
		if(this.maxId<maxId) {
			this.maxId = maxId;
		}
	}

	@Override
	public void set(int index) {
		this.set(index,true);
	}

	
	@Override
	public void save(String path) throws Exception {
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		RandomAccessFile rw = null;//new RandomAccessFile(String.format("%s/xyy.sun", path),"rwd");
		try {
			rw = new RandomAccessFile(String.format("%s/xyy.sun", path),"rwd");
			rw.seek(this.split*cache.length);
			rw.write(splitSet.toByteArray(cache));
			rw.close();
		} finally {
			if(rw!=null) rw.close();
		}
		Savor.write(maxId, String.format("%s/maxId",path));
	}

}

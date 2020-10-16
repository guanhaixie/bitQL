package com.xuanyue.db.xuan.core.index;

import java.util.ArrayList;
import java.util.List;

import com.xuanyue.db.xuan.core.table.IXGHBitSet;


/**
 * bit向量的分块
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class XGHBitSet implements IXGHBitSet{

	private static final int LONG_NUM = 10000;
	public static final int SET_SIZE = LONG_NUM*64;
	private long[] words = new long[SET_SIZE/64];
	private int maxId = 0;
	private final static int ADDRESS_BITS_PER_WORD = 6;
	private final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
	private static final long WORD_MASK = 0xffffffffffffffffL;
	public XGHBitSet() {}
	public XGHBitSet(byte[] cache,int maxId) {
		this.maxId = maxId;
		for(int i=0;i<LONG_NUM;i++) {
			words[i]=byteArrayToLong(cache, i*8);
		}
	}
	public XGHBitSet(byte[] cache) {
		for(int i=0;i<LONG_NUM;i++) {
			words[i]=byteArrayToLong(cache, i*8);
		}
	}
	public long[] getWords() {
		return this.words;
	}
	public long getWord(int bitIndex) {
		return this.words[bitIndex/64];
	}
	public byte[] toByteArray(byte[] cache) {
		for(int i=0;i<LONG_NUM;i++) {
			longToByteArray(cache, words[i], i*8);
		}
		return cache;
	}
	public static void longToByteArray(byte[] result,long value,int from) {
		int offset = 0;
		for (int i = 0; i < 8; i++) {   
			offset = i*8;
			result[from+i] = (byte) ((value >> offset) & 0xff);
	    }
	}
	public static long byteArrayToLong(byte[] result,int from) {
		return ((((long) result[ from+7] & 0xff) << 56)
			       | (((long) result[from+ 6] & 0xff) << 48)
			       | (((long) result[from+ 5] & 0xff) << 40)
			       | (((long) result[from+ 4] & 0xff) << 32)
			       | (((long) result[from+ 3] & 0xff) << 24)
			       | (((long) result[from+ 2] & 0xff) << 16)
			       | (((long) result[from+ 1] & 0xff) << 8) 
			       |  (((long) result[from] & 0xff) << 0));
	}
	public static int size() {
		return SET_SIZE/8;
	}
	private static int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }
	public int getMaxId() {
		return this.maxId;
	}
	public void flush(int maxId) {
		this.maxId = maxId;
	}
	public void set(int bitIndex) {
        words[wordIndex(bitIndex)] |= (1L << bitIndex); // Restores invariants
	}
	public void clear(int bitIndex) {
		words[wordIndex(bitIndex)] &= ~(1L << bitIndex);
	}
	public void set(int bitIndex,boolean value) {
		if(value) {
			set(bitIndex);
		}else {
			clear(bitIndex);
		}
	}
	
	public void setAll(boolean value) {
		if(value) {
			setAll();
		}else {
			clearAll();
		}
	}
	
	
	public void setAll() {
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] = WORD_MASK;
		}
		if((maxId+1)%64==0) {
			this.words[ue]=WORD_MASK;
		}else {
			long lm = ~(WORD_MASK<<(maxId+1));
			this.words[ue]= lm;
		}
		
	}
	public void clearAll() {
		for(int i=0;i<this.words.length;i++) {
			this.words[i] = 0;
		}
	}
	public boolean get(int bitIndex) {
		if(bitIndex>maxId) {
			return false;
		}
        int wordIndex = wordIndex(bitIndex);
        return  (words.length>wordIndex)&&((words[wordIndex] & (1L << bitIndex)) != 0);
    }
	public void clearnFrom(int from ) {
		this.clearnFromTo(from, maxId);
	}
	private void clearnFromTo(int fromIndex,int toIndex) {
		if (fromIndex == toIndex)
            return;

		if(fromIndex>maxId) {
			return;
		}
		if(toIndex>maxId) {
			toIndex = maxId;
		}
        int startWordIndex = wordIndex(fromIndex);
        int endWordIndex =   wordIndex(toIndex - 1);
        

        long firstWordMask = WORD_MASK << fromIndex;
        long lastWordMask  = WORD_MASK >>> -toIndex;
        if (startWordIndex == endWordIndex) {
            words[startWordIndex] &= ~(firstWordMask & lastWordMask);
        } else {
            words[startWordIndex] &= ~firstWordMask;

            for (int i = startWordIndex+1; i < endWordIndex; i++)
                words[i] = 0;
            words[endWordIndex] &= ~lastWordMask;
        }

		

	}
	
	public int nextSetBit(int fromIndex) {
		if (fromIndex < 0)
		    throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
		if(fromIndex>maxId) {
			return -1;
		}
		
		int u = wordIndex(fromIndex);
		int ue = wordIndex(maxId);
		
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		
		
		long word = words[u] & (WORD_MASK << fromIndex);
		
		while (true) {
			if(u==ue)
				word = word&lm;
		    if (word != 0)
		        return (u * BITS_PER_WORD) + Long.numberOfTrailingZeros(word);
		    if (++u > ue)
		        return -1;
		    word = words[u];
		}
    }
	public int nextClearBit(int fromIndex) {
		if (fromIndex < 0)
		    throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
		if(fromIndex>maxId) {
			return -1;
		}
		
		int u = wordIndex(fromIndex);
		int ue = wordIndex(maxId);
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		
		
		long word = ~words[u] & (WORD_MASK << fromIndex);
		
		while (true) {
			if(u==ue)
				word = word&lm;
		    if (word != 0)
		        return (u * BITS_PER_WORD) + Long.numberOfTrailingZeros(word);
		    if (++u > ue)
		        return -1;
		    word = ~words[u];
		}
    }
	public int cardinality() {
		int cardinality = 0;
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			cardinality+=Long.bitCount(words[i]);
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		try {
			cardinality+=Long.bitCount( (lm&words[ue]) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cardinality;
	}
	 
	public int andByCardinality(IXGHBitSet set) {
		int cardinality = 0;
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			cardinality+=Long.bitCount((words[i]&set.getWords()[i]));
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		
		cardinality+=Long.bitCount( ((lm&words[ue])&(lm&set.getWords()[ue])) );
		return cardinality;
	}
	
	public int andNotByCardinality(IXGHBitSet set) {
		int cardinality = 0;
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			cardinality+=Long.bitCount((words[i]&(~(set.getWords()[i]))));
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		
		cardinality+=Long.bitCount( ((lm&words[ue])&(~(lm&set.getWords()[ue]))) );
		return cardinality;
	}
	public void and(IXGHBitSet set) {
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] &= set.getWords()[i];
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		
		this.words[ue]= this.words[ue]&set.getWords()[ue]&lm;
	}

	public void andNot(IXGHBitSet set) {
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] &= (~(set.getWords()[i]));
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		this.words[ue]= this.words[ue]&(~(set.getWords()[ue]))&lm;
	}
	public void xor(IXGHBitSet set) {
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] ^= set.getWords()[i];
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		
		this.words[ue]= (this.words[ue]^set.getWords()[ue])&lm;
	}
	
	public void xor(boolean value,IXGHBitSet set) {
		long b = (value?WORD_MASK:0);
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] = b^(set.getWords()[i]);
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		
		this.words[ue]= (b^set.getWords()[ue])&lm;
	}
	public IXGHBitSet not() {
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] = ~this.words[i];
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		this.words[ue]= (~(this.words[ue]))&lm;
		return this;
	}
	public void copyFrom(IXGHBitSet sr) {
		int ue = wordIndex(maxId);
		System.arraycopy(sr.getWords(), 0, this.words, 0,ue);
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		this.words[ue] = lm&sr.getWords()[ue];
	}
	
	public List<Integer> getIndexOftrue(){
		List<Integer> r = new ArrayList<Integer>();
    	for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i+1)) {
    		r.add(i);
    	}
    	return r;
	}
	
	public void or(IXGHBitSet set) {
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] |= set.getWords()[i];
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		this.words[ue]= (this.words[ue]|set.getWords()[ue])&lm;
	}
	public void orNot(IXGHBitSet set) {
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] |= (~(set.getWords()[i]));
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		this.words[ue]= (this.words[ue]|(~(set.getWords()[ue])))&lm;
	}
	public void orByinnerAnd(IXGHBitSet a ,IXGHBitSet b) {
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] |= (a.getWords()[i]&b.getWords()[i]);
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		this.words[ue]= (this.words[ue]|(a.getWords()[ue]&b.getWords()[ue]))&lm;
	}
	public void orByinnerAndNot(IXGHBitSet a ,IXGHBitSet b) {
		int ue = wordIndex(maxId);
		for(int i=0;i<ue;i++) {
			this.words[i] |= (a.getWords()[i]&(~(b.getWords()[i])));
		}
		long lm = ((maxId+1)%64==0)?WORD_MASK:~(WORD_MASK<<(maxId+1));
		this.words[ue]= (this.words[ue]|(a.getWords()[ue]&(~(b.getWords()[ue]))))&lm;
	}
	public static void main(String[] args) {
		int p = 11;
		long lm = ~(WORD_MASK<<p);
		System.out.println(Long.bitCount(lm));
		System.out.println(lm);
		lm = (lm>>50);
		System.out.println(lm);
		long t = -8l;
		System.out.println(t<<50);
	}
}

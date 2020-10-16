package com.xuanyue.db.xuan.core.table;

import java.util.List;

public interface IXGHBitSet {
	
	public static final int LONG_NUM = 10000;
	public static final int SET_SIZE = LONG_NUM*64;
	//private long[] words = new long[SET_SIZE/64];
	//private int maxId = 0;
	public final static int ADDRESS_BITS_PER_WORD = 6;
	public final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
	public static final long WORD_MASK = 0xffffffffffffffffL;
	

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
	public static int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }
	
	public int cardinality();
	public long[] getWords();
	public long getWord(int bitIndex);
	public byte[] toByteArray(byte[] cache);
	public int getMaxId();
	public void flush(int maxId) ;
	public void set(int bitIndex) ;
	public void clear(int bitIndex);
	public void set(int bitIndex,boolean value);
	
	public void setAll(boolean value);
	
	
	public void setAll() ;
	public void clearAll();
	public boolean get(int bitIndex) ;
	public void clearnFrom(int from );
	
	public int nextSetBit(int fromIndex);
	public int nextClearBit(int fromIndex) ;
	 
	public int andByCardinality(IXGHBitSet set);
	
	public int andNotByCardinality(IXGHBitSet set) ;
	public void and(IXGHBitSet set);

	public void andNot(IXGHBitSet set) ;
	public void xor(IXGHBitSet set);
	
	public void xor(boolean value,IXGHBitSet set) ;
	public IXGHBitSet not() ;
	public void copyFrom(IXGHBitSet sr) ;
	
	public List<Integer> getIndexOftrue();
	
	public void or(IXGHBitSet set);
	public void orNot(IXGHBitSet set) ;
	public void orByinnerAnd(IXGHBitSet a ,IXGHBitSet b) ;
	public void orByinnerAndNot(IXGHBitSet a ,IXGHBitSet b);
}

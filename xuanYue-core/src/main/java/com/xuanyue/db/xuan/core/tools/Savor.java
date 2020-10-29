package com.xuanyue.db.xuan.core.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 对象序列化到文件
 * @author guanh
 *
 */
public class Savor {
	
	public static void write(Object t,String path) throws Exception {
		File f = new File(path);
		if(f.exists()) {
			f.delete();
		}
		ObjectOutputStream oos = null;//new ObjectOutputStream(new FileOutputStream(f));
		try {
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(t);
			oos.flush();
		} finally {
			if(oos!=null)oos.close();
		}
	}
	
	public static <T>T read(String path) throws Exception{
		File f = new File(path);
		if(f.exists()) {
			ObjectInputStream ois = null;// new ObjectInputStream(new FileInputStream(f));
			try {
				ois = new ObjectInputStream(new FileInputStream(f));
				@SuppressWarnings("unchecked")
				T b = (T)ois.readObject();
				return b;
			} finally {
				if (ois!=null)ois.close();
			}
		}
		return null;
	}
}

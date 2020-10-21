package com.xuanyue.db.xuan;

public class Hello {

	public static void main(String[] args) throws InterruptedException {
		boolean isplus = true;
		for( int i = 1 ; i <= 300; i++){
			if( i%50 == 0 ){
				isplus = !isplus ;
			}
			if( isplus ){
				for( int j=0; j<1; j++){
					System.out.print(".");
				}
			}else{
				for( int j=50; j>0; j--){
					System.out.print("\b \b");
				}
			}
			Thread.sleep(10);
		}
		System.out.print("\b完成!");
	}

}

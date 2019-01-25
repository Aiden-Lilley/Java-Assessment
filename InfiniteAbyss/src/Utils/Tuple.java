package Utils;

public class Tuple<T1,T2>{
	T1 a;
	T2 b;
	
	public Tuple(){
		
	}
	
	public Tuple(T1 a, T2 b) {
		this.a = a;
		this.b = b;
	}
	
	public T1 getValueOne() {
		return a;
	}
	
	public T2 getValueTwo() {
		return b;
	}
	

	
	public void set(T1 a,T2 b) {
		this.a = a;
		this.b = b;
	}

}

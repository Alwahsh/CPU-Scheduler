
public class Quantum
{
	Integer num;
	Integer length;
	
	public Quantum(int n) {
		num = n;
		length = 1;
	}
	
	public Integer getNum() {
		return num;
	}
	
	public void increment() {
		length++;
	}
}


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
	
	public Integer getLength() {
		return length;
	}
	
	public void increment() {
		length++;
	}
}

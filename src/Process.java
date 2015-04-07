
public class Process implements Comparable
{
	Integer num;
	Integer time;
	Integer arrival_time;
	Integer priority;
	
	public Process(int t, int a, int p) {
		time = t;
		arrival_time = a;
		priority = p;
	}
	
	public Integer getTime() {
		return time;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public Integer getArrivalTime() {
		return arrival_time;
	}

	public void setNumber(int n) {
		num = n;
	}
	
	public Integer getNumber() {
		return num;
	}
	
	public void decrement_time() {
		time--;
	}
	
	@Override
	public int compareTo(Object arg0)
	{
		// TODO Auto-generated method stub
		Process other = (Process)arg0;
		return arrival_time.compareTo(other.getArrivalTime());
	}
}

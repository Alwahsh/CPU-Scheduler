import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;


public class Processor
{
	LinkedList<Process> l;
	Long total_time;
	LinkedList<Quantum> res;
	double average_wait;
	
	
	public Processor() {
		l = new LinkedList<Process>();
		res = new LinkedList<Quantum>();
		total_time = 0L;
	}
	
	public void add_process(int t, int a) {
		add_process(t,a,-1);
	}
	
	@SuppressWarnings("unchecked")
	public void add_process(int t, int a, int p) {
		Process pr = new Process(t,a,p);
		l.add(pr);
		total_time+= t;
		Collections.sort(l);
	}
	
	public String[] get_processes_array() {
		String[] res = new String[l.size()];
		ListIterator<Process> li = l.listIterator();
		for (int i = 0; i < l.size(); i++) {
			Process p = li.next();
			p.setNumber(i+1);
			String s = new String();
			s+= "P";
			s+= String.valueOf(p.getNumber());
			s+= "- ";
			s+= "Arrives: ";
			s+= String.valueOf(p.getArrivalTime());
			s+= " - ";
			s+= "Needs: ";
			s+= String.valueOf(p.getTime());
			s+= " Quantums";
			if (p.getPriority() > -1) {
				s+= " - Priority: ";
				s+= String.valueOf(p.getPriority());
			}
			s+= ".";
			res[i] = s;
		}
		return res;
		
	}
	
	public void remove_process(int i) {
		Process rem = l.remove(i);
		total_time-= rem.getTime();
	}
	
	public void clear_processes() {
		l.clear();
		total_time = 0L;
	}
	
	
	public int fcfs_schedule(Long i) {
		Process p = l.getFirst();
		if (p.getArrivalTime() < i) {
			return -1;
		}
		p.decrement_time();
		if (p.getTime() <= 0) {
			l.removeFirst();
		}
		return p.getNumber();
	}
	
	public void schedule(int type) {
		res.clear();
		double num_p = l.size();
		Long total_wait = 0L;
		for (Long i = 0L; i < total_time; i++) {
			int lucky_process =	0;
			switch(type) 
			{
				case 0:
					lucky_process = fcfs_schedule(i);
					break;
			}
			add_to_res(lucky_process);
			total_wait+= getNumWaiting(i,lucky_process);
		}
		average_wait = total_wait/num_p;
	}
	
	Long getNumWaiting(Long i,int lp) {
		Long sum = 0L;
		ListIterator<Process> li = l.listIterator();
		while(li.hasNext()) {
			Process p = li.next();
			if (p.getArrivalTime() <= i && lp != p.getNumber())
				sum++;
		}
		return sum;
	}
	
	void add_to_res(int p) {
		if (res.isEmpty() || res.getLast().getNum() != p) {
			res.add(new Quantum(p));
		} else {
			res.getLast().increment();
		}
	}
	
}

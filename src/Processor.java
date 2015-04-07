import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;


public class Processor
{
	LinkedList<Process> l;
	LinkedList<Process> queue;
	ListIterator<Process> it;
	Long total_time;
	LinkedList<Quantum> res;
	double average_wait;
	
	
	public Processor() {
		l = new LinkedList<Process>();
		res = new LinkedList<Quantum>();
		queue = new LinkedList<Process>();
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
	
	
	public void fcfs_schedule(Long t) {
		while (it.hasNext()) {
			Process p = it.next();
			if ((long)p.getArrivalTime() == t) {
				queue.add(p);
			} else if ((long)p.getArrivalTime() > t){
				break;
			}
		}
		it.previous();
	}
	
	public void schedule(int type) {
		res.clear();
		queue.clear();
		it = l.listIterator();
		
		//double num_p = l.size();
		Long total_wait = 0L;
		for (Long i = 0L; i < total_time; i++) {
			switch(type) 
			{
				case 0:
					fcfs_schedule(i);
					break;
			}
			total_wait+= getNumWaiting();
			add_to_res(cpu_quantum());
		}
		average_wait = total_wait/(double)l.size();
	}
	
	int cpu_quantum() {
		if (queue.isEmpty())
			return 0;
		else {
			Process p = queue.getFirst();
			p.decrement_time();
			if (p.getTime() <= 0)
				queue.removeFirst();
			return p.getNumber();
		}
	}
	
	Long getNumWaiting() {
		if (queue.isEmpty())
			return 0L;
		return (long) (queue.size()-1);
	}
	
	void add_to_res(int p) {
		if (res.isEmpty() || res.getLast().getNum() != p) {
			res.add(new Quantum(p));
		} else {
			res.getLast().increment();
		}
	}
	
	public String get_scheduled_data() {
		String s = new String();
		ListIterator<Quantum> li = res.listIterator();
		s+= "Average waiting time: ";
		s+= String.valueOf(average_wait);
		s+= "\n";
		Integer i = 0;
		while(li.hasNext()) {
			Quantum q = li.next();
			s+= "P";
			s+= String.valueOf(q.getNum());
			s+= ": ";
			s+= String.valueOf(i);
			i+= q.getLength();
			s+= " -> ";
			s+= String.valueOf(i);
			s+= "\n";
		}
		return s;
	}
	
}

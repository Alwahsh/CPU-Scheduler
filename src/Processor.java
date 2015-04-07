import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;


public class Processor
{
	LinkedList<Process> l;
	LinkedList<Process> queue;
	ListIterator<Process> it;
	Long total_time;
	Long qTime;
	Long remainingQ;
	LinkedList<Quantum> res;
	double average_wait;
	
	
	public Processor() {
		l = new LinkedList<Process>();
		res = new LinkedList<Quantum>();
		queue = new LinkedList<Process>();
		total_time = 0L;
		average_wait = 0;
		qTime = 1L;
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
	
	public void set_qTime(Long qT) {
		qTime = qT;
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
			if ((long)p.getArrivalTime() <= t) {
				queue.add(p);
			} else if ((long)p.getArrivalTime() > t){
				it.previous();
				break;
			}
		}
	}
	
	public void sjf_priority_schedule(Long t, char type, char schedule) {
		while (it.hasNext()) {
			Process p = it.next();
			if ((long)p.getArrivalTime() <= t) {
				ListIterator<Process> sji = queue.listIterator();
				if (type == 'n')
					if (sji.hasNext())
						sji.next();
				while(sji.hasNext()) {
					int comp1,comp2;
					if (schedule == 'p') {
						comp1 = sji.next().getPriority();
						comp2 = p.getPriority();
					} else {
						comp1 = sji.next().getTime();
						comp2 = p.getTime();
					}
					if (comp1 > comp2) {
						sji.previous();
						break;
					}
				}
				sji.add(p);
			} else {
				it.previous();
				break;
			}
		}
	}
	
	public void round_robin_schedule(Long t) {
		while (it.hasNext()) {
			Process p = it.next();
			if ((long)p.getArrivalTime() <= t) {
				queue.add(p);
			} else if ((long)p.getArrivalTime() > t){
				it.previous();
				break;
			}
		}
		if (remainingQ == 0) {
			remainingQ = qTime;
			Process p = queue.removeFirst();
			queue.addLast(p);
		}
	}
	
	public void schedule(int type) {
		res.clear();
		queue.clear();
		it = l.listIterator();
		remainingQ = qTime;
		//double num_p = l.size();
		Long total_wait = 0L;
		for (Long i = 0L; it.hasNext() || !queue.isEmpty(); i++) {
			switch(type) 
			{
				case 0:
					fcfs_schedule(i);
					break;
				case 1:
					sjf_priority_schedule(i,'p','t');
					break;
				case 2:
					sjf_priority_schedule(i,'n','t');
					break;
				case 3:
					sjf_priority_schedule(i, 'p', 'p');
					break;
				case 4:
					sjf_priority_schedule(i, 'n', 'p');
					break;
				case 5:
					round_robin_schedule(i);
					remainingQ--;
			}
			total_wait+= getNumWaiting();
			add_to_res(cpu_quantum());
		}
		if (l.size() == 0)
			average_wait = 0;
		else
			average_wait = total_wait/(double)l.size();
	}
	
	int cpu_quantum() {
		if (queue.isEmpty()) {
			remainingQ = qTime;
			return 0;
		}
		else {
			Process p = queue.getFirst();
			p.decrement_time();
			if (p.getTime() <= 0) {
				queue.removeFirst();
				remainingQ = qTime;
			}
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
			if (q.getNum() == 0) {
				s+= "Idle: ";
			} else {
				s+= "P";
				s+= String.valueOf(q.getNum());
				s+= ": ";
			}
			s+= String.valueOf(i);
			i+= q.getLength();
			s+= " -> ";
			s+= String.valueOf(i);
			s+= "\n";
		}
		return s;
	}
	
	public LinkedList<Quantum> get_res() {
		return res;
	}
	
}

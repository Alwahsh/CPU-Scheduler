import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;


public class ChartDrawer extends JFrame
{

	private JPanel contentPane;


	
	Processor myP;
	
	/**
	 * Create the frame.
	 */
	public ChartDrawer()
	{
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	}
	
	public ChartDrawer(Processor p)
	{
		myP = p;
		setTitle("Chart");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1200, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JOptionPane.showMessageDialog(null, myP.get_scheduled_data());
	}
	
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		LinkedList<Quantum> q = myP.get_res();
		ListIterator<Quantum> li = q.listIterator();
		Integer startPoint = 40;
		Integer time = 0;
		boolean first = true;
		while(li.hasNext()) {
			Quantum cur = li.next();
			g.drawRect(startPoint, 100, cur.getLength()*20, 200);
			if (cur.getNum() == 0) {
				g.drawString("Idle", startPoint+5, 150);
			} else {
				g.drawString("P"+String.valueOf(cur.getNum()), startPoint+5, 150);
			}
			if (first) {
				g.drawString(String.valueOf(time), startPoint-10, 320);
				first = false;
			}
			startPoint+= cur.getLength()*20;
			time+= cur.getLength();
			g.drawString(String.valueOf(time), startPoint-10, 320);
			
		}
	}
}



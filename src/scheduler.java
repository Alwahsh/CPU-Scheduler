import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.JEditorPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollBar;


public class scheduler
{

	private JFrame frmCpuScheduler;
	private JTextField time_txt;
	private JTextField priority_txt;
	private JTextField textField_2;
	private JTextField arrival_txt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					scheduler window = new scheduler();
					window.frmCpuScheduler.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public scheduler()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmCpuScheduler = new JFrame();
		frmCpuScheduler.setTitle("CPU Scheduler");
		frmCpuScheduler.setBounds(100, 100, 669, 527);
		frmCpuScheduler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCpuScheduler.getContentPane().setLayout(null);
		final Processor myP = new Processor();
		final JList schedulers_lst = new JList();
		schedulers_lst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		schedulers_lst.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		schedulers_lst.setModel(new AbstractListModel() {
			String[] values = new String[] {"FCFS", "SJF(Preemptive)", "SJF(Non Preemptive)", "Priority(Preemptive)", "Priority(Non Preemptive)", "Round Robin"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		schedulers_lst.setSelectedIndex(0);
		schedulers_lst.setBounds(40, 58, 195, 113);
		frmCpuScheduler.getContentPane().add(schedulers_lst);
		
		JLabel lblChooseTypeOf = new JLabel("Choose type of scheduler:");
		lblChooseTypeOf.setBounds(40, 30, 195, 15);
		frmCpuScheduler.getContentPane().add(lblChooseTypeOf);
		
		JPanel panel = new JPanel();
		panel.setBounds(258, 55, 365, 136);
		frmCpuScheduler.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add a process");
		lblNewLabel.setBounds(0, 0, 122, 15);
		panel.add(lblNewLabel);
		
		time_txt = new JTextField();
		time_txt.setFont(new Font("Dialog", Font.PLAIN, 20));
		time_txt.setBounds(8, 44, 67, 30);
		panel.add(time_txt);
		time_txt.setColumns(10);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(5, 27, 70, 15);
		panel.add(lblTime);
		
		final JPanel panel_1 = new JPanel();
		panel_1.setBounds(114, 22, 122, 66);
		panel.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setVisible(false);
		
		priority_txt = new JTextField();
		priority_txt.setFont(new Font("Dialog", Font.PLAIN, 20));
		priority_txt.setColumns(10);
		priority_txt.setBounds(0, 21, 67, 30);
		panel_1.add(priority_txt);
		
		JLabel lblPriority = new JLabel("Priority:");
		lblPriority.setBounds(0, 5, 70, 15);
		panel_1.add(lblPriority);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBounds(236, 44, 97, 40);
		panel.add(btnNewButton);
		
		arrival_txt = new JTextField();
		arrival_txt.setFont(new Font("Dialog", Font.PLAIN, 20));
		arrival_txt.setColumns(10);
		arrival_txt.setBounds(8, 106, 67, 30);
		panel.add(arrival_txt);
		
		JLabel lblArrival = new JLabel("Arrival:");
		lblArrival.setBounds(8, 89, 70, 15);
		panel.add(lblArrival);
		final JList processes_lst = new JList();
		processes_lst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		processes_lst.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		processes_lst.setBounds(39, 203, 584, 172);
		frmCpuScheduler.getContentPane().add(processes_lst);
		
		JLabel lblProcesses = new JLabel("Processes:");
		lblProcesses.setBounds(40, 183, 88, 15);
		frmCpuScheduler.getContentPane().add(lblProcesses);
		
		JButton btnRemove = new JButton("Remove");
		
		btnRemove.setBounds(506, 387, 117, 25);
		frmCpuScheduler.getContentPane().add(btnRemove);
		
		final JPanel panel_2 = new JPanel();
		panel_2.setBounds(40, 412, 117, 76);
		frmCpuScheduler.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		panel_2.setVisible(false);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 25));
		textField_2.setColumns(10);
		textField_2.setBounds(0, 24, 67, 40);
		panel_2.add(textField_2);
		
		JLabel lblTimeQuantum = new JLabel("Time Quantum:");
		lblTimeQuantum.setBounds(0, 5, 117, 15);
		panel_2.add(lblTimeQuantum);
		
		JButton btnSchedule = new JButton("Schedule");
		btnSchedule.setBounds(181, 412, 277, 76);
		frmCpuScheduler.getContentPane().add(btnSchedule);
		
		schedulers_lst.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				int s = schedulers_lst.getSelectedIndex();
				if (s == 3 || s == 4) {
					panel_1.setVisible(true);
				} else
					panel_1.setVisible(false);
				if (s == 5)
					panel_2.setVisible(true);
				else
					panel_2.setVisible(false);
				//JOptionPane.showMessageDialog(null, list.getSelectedIndex());
				myP.clear_processes();
				processes_lst.setModel(new AbstractListModel() {
					String[] values = myP.get_processes_array();
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (time_txt.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "Time can't be empty.");
					return;
				}
				if (arrival_txt.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "Arrival time can't be empty.");
					return;
				}
				int time = Integer.parseInt(time_txt.getText());
				if (time <= 0)
				{
					JOptionPane.showMessageDialog(null, "Time must be positive.");
					return;
				}
				int arr = Integer.parseInt(arrival_txt.getText());
				if (arr <= 0)
				{
					JOptionPane.showMessageDialog(null, "Arrival time must be positive.");
					return;
				}
				int priority = -1;
				if (schedulers_lst.getSelectedIndex() == 3 || schedulers_lst.getSelectedIndex() == 4) {
					if (priority_txt.getText().length() == 0)
					{
						JOptionPane.showMessageDialog(null, "Priority can't be empty.");
						return;
					}
					priority = Integer.parseInt(priority_txt.getText());
				}
				myP.add_process(time,arr,priority);
				processes_lst.setModel(new AbstractListModel() {
					String[] values = myP.get_processes_array();
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
			}
		});
		
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (processes_lst.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "You must select a process to remove.");
					return;
				}
				myP.remove_process(processes_lst.getSelectedIndex());
				processes_lst.setModel(new AbstractListModel() {
					String[] values = myP.get_processes_array();
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
			}
		});
		
	}
}

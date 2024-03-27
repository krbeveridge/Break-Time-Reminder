import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

public class BreakTime {
	String timeString;
	String dayString;
	String monthString;
	int hours = 0;
	int minutes = 0;
	int breakLength = 0;
	Calendar date = Calendar.getInstance();
	JLabel incorrectFields = new JLabel("One or more of the boxes were not entered Correctly. Only digits are entered in each text box");
	JList <String>list;
	DefaultListModel<String> listModel = new DefaultListModel<>();
	Timer timer = new Timer();
	Calendar calendar = Calendar.getInstance();
	
	
	BreakTime(JFrame frame){
		
		//New Objects
		JLabel Welcome = new JLabel("Welcome. Set up any breaks by typing it into the box below");
		JButton startBreak = new JButton("Start Break");
		JButton setBreak = new JButton("Set break date");
		JTextField month = new JTextField("Enter month");
		JTextField day = new JTextField("Enter day");
		JTextField time = new JTextField("Enter military time");
		JTextField lunchBreak = new JTextField("Enter length of break in minutes");
		GridBagConstraints grid = new GridBagConstraints();
		//New Objects
		
		//Set Sizes
		startBreak.setPreferredSize(new Dimension(120, 50));
		setBreak.setPreferredSize(new Dimension(120, 50));
		month.setPreferredSize(new Dimension(70, 20));
		day.setPreferredSize(new Dimension(70, 20));
		time.setPreferredSize(new Dimension(100, 20));
		lunchBreak.setPreferredSize(new Dimension(240, 40));
		//Set Sizes
		
		//Setting Frame
		setFrameConstraints(frame);		
		//Setting Frame
		
		//Adding Buttons
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		grid.gridx = 1;
		grid.gridy = 0;
		grid.gridwidth = 2;
		panel.add(Welcome, grid);
		grid.gridy = 1;
		panel.add(month, grid);
		grid.gridy = 2;
		panel.add(day, grid);
		grid.gridy = 3;
		panel.add(time, grid);
		grid.gridy = 4;
		panel.add(lunchBreak, grid);
		grid.gridy = 5;
		panel.add(startBreak, grid);
		grid.gridy = 6;
		panel.add(setBreak, grid);
		frame.add(panel);
		//adding Buttons
		

		//List displayed on bottom of frame
		addJList(frame);
		//List displayed on bottom of frame

		
		//actionListeners for buttons
		setBreak.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(monthString != null && dayString != null && timeString != null) {
					int clockIn = Integer.parseInt(timeString);
					String temp = monthString + "/" + dayString + " " + timeString + " Clock back in at " + (clockIn + breakLength);
					listModel.addElement(temp);	
				}
			}
		});
		
		startBreak.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startBreak(panel, grid);
			}
		
		});
		//actionListeners for buttons

		//FocusListeners for typing boxes
		month.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(month.getText().equals("Enter month")){
					month.setText(null);
					if(frame.getContentPane() == incorrectFields) {
						frame.remove(incorrectFields);
					}
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(month.getText().length() == 0) {
					month.setText("Enter month");
				} else {
					monthString = month.getText();
					date.set(Calendar.MONTH, Integer.parseInt(monthString));
					date.set(Calendar.YEAR, date.getWeekYear());
				}
			}
		});
		
		day.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(day.getText().equals("Enter day")){
					day.setText(null);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(day.getText().length() == 0) {
					day.setText("Enter day");				
				} else {
				dayString = day.getText();
				date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayString));
				}			
		}
		});
		
		time.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(time.getText().equals("Enter military time")){
					time.setText(null);
				}				
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(time.getText().length() == 0) {
					time.setText("Enter military time");
				} else {
					timeString = time.getText();
					getMilitaryTime(timeString);
					date.set(Calendar.HOUR, hours);
					date.set(Calendar.MINUTE, minutes);
				}
			}
		});
		
		lunchBreak.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if(lunchBreak.getText().equals("Enter length of break in minutes")){
					lunchBreak.setText(null);
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(lunchBreak.getText().length() == 0) {
					lunchBreak.setText("Enter length of break in minutes");

				}else {
					breakLength = Integer.parseInt(lunchBreak.getText());
				}
			}
		});
		//FocusListeners for typing boxes
	}
	
	//Putting time into hours and minutes variables
	void getMilitaryTime(String timeString) {
		hours += timeString.charAt(0);
		hours *= 10;
		hours += timeString.charAt(1);
		minutes += timeString.charAt(2);
		minutes *= 10;
		minutes += timeString.charAt(3);
	}
	
	 private void clickButtonAt(Point point){
		 try {
	    int index = list.locationToIndex(point);
	    ((DefaultListModel<String>) list.getModel()).remove(index);
	    list.repaint();
		 } catch(Exception e) {
			 System.err.println("Trying to delete component in list that does not exist");
		 }
	  }
	 
	 private void startBreak(JPanel panel, GridBagConstraints grid) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					JLabel endBreak = new JLabel("Your break is over - time to get back to work");
					grid.gridy = 10;
					panel.add(endBreak, grid);
					panel.revalidate();
					panel.repaint();
				}
			}, breakLength * 60 * 1000);
	 }
	 
	 private void setFrameConstraints(JFrame frame) {
		 frame.setUndecorated(true); // Set the frame to be undecorated first
		    frame.pack();
		    frame.setSize(400, 600);
		    frame.setTitle("Break Time App");
		    frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG); // Set the border to opaque
		    frame.setLocationRelativeTo(null);
		    frame.setResizable(false);
		    frame.setVisible(true); // Make the frame visible after setting it undecorated
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 }
	 
	 private void addJList(JFrame frame) {
			list = new JList<>(listModel);
			list.setPreferredSize(new Dimension(200,200));
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					clickButtonAt(e.getPoint());
				}
			});
			frame.add(list, BorderLayout.SOUTH);
	 }
	
}

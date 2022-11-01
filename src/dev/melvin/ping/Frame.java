package dev.melvin.ping;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Frame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Font font = new Font("Segoe UI Semibold", Font.PLAIN, 15);
	Font fontPing = new Font("Segoe UI Semibold", Font.PLAIN, 25);
	JTextField serverField = new JTextField();
	SpinnerModel model = new SpinnerNumberModel(1, 0, 1000, 1);
	JSpinner interval = new JSpinner(model);
	JButton ok = new JButton("OK");
	JLabel cur = new JLabel("Pinging: " + Main.ip);
	JLabel refreshLabel = new JLabel("Ping every:                           seconds");
	public static JLabel curPing = new JLabel(Main.ping  + " ms");
	Timer counter = new Timer();
	
	JButton aot = new JButton("AOT");
	JLabel isAot = new JLabel();
	
	JLabel activity = new JLabel();
	String[] sym = {"•", " "};
	int ind = 0;
	
	Frame() {
		counter.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
				try {
					Thread.sleep(Main.refresh);
					if (ind == 0) ind = 1;
					else ind = 0;
					
					activity.setText(sym[ind]);
					
					if (Main.err > 1) {
						curPing.setText("-- ms");
					} else {
						curPing.setText(Main.ping + " ms");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}, 0, 1);
		
		serverField.setBounds(15, 10, 175, 20);
		
		ok.setBounds(195, 10, 46, 20);
		ok.setFocusable(false);
		ok.addActionListener(this);
		
		refreshLabel.setBounds(15, 35, 380, 20);
		
		interval.setBounds(80, 35, 70, 20);
		interval.setFocusable(false);
		
		cur.setBounds(15, 40, 1000, 50);
		cur.setFont(font);
		
		curPing.setBounds(35, 65, 1000, 50);
		curPing.setFont(fontPing);
		
		activity.setBounds(15, 65, 100, 50);
		activity.setFont(fontPing);
		
		aot.setBounds(190, 80, 53, 20);
		aot.addActionListener(this);
		
		isAot.setBounds(197, 60, 53, 20);
		
		this.setTitle("Ping Display");
		this.setSize(270, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.add(serverField);
		this.add(ok);
		this.add(cur);
		this.add(curPing);
		this.add(interval);
		this.add(refreshLabel);
		this.add(activity);
		this.add(aot);
		this.add(isAot);
		this.setVisible(true);
//		this.setAlwaysOnTop(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object button = e.getSource();
		
		if (button == ok) {
			String text = serverField.getText();
				Main.ip = text;
				Main.ping = 0;
				
				int newRefresh = (int) interval.getValue() * 1000;
				
				if (newRefresh != 0) Main.refresh = newRefresh;
				else JOptionPane.showMessageDialog(this, "0 is not a valid interval");
				
				curPing.setText("-- ms");
				cur.setText("Pinging: " + Main.ip);
		}
		if (button == aot) {
			if (this.isAlwaysOnTop()) {
				this.setAlwaysOnTop(false);
				isAot.setText("");
			}
			else {
				this.setAlwaysOnTop(true);
				isAot.setText("on top");
			}
			
		}
	}
}

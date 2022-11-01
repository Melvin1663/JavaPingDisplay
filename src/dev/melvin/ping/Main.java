package dev.melvin.ping;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
	public static String ip = "";
	public static int err = 0;
	public static int ping = 0;
	public static int refresh = 1000;
	public static void main (String[] args) throws AWTException, IOException, UnsupportedLookAndFeelException {
//		ip = "google.com";
//		String cmd = "ping -n 1 " + ip;
		
		
		Timer counter = new Timer();
		Runtime r = Runtime.getRuntime();
		
		counter.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
				try {
					Thread.sleep(refresh);
					String pingResult = "";
					Process p = r.exec("ping -n 1 " + ip);
					
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String inputLine;
			        while ((inputLine = in.readLine()) != null) {
			            pingResult += inputLine + "\n";
			        }
			        in.close();
			        
			        String[] reply = pingResult.split("\n");
			        
			        pingResult = reply[2];
			        
			        Pattern pattern = Pattern.compile("time(<|=)(\\w+)");
			        Matcher matcher = pattern.matcher(pingResult);
			        
			        matcher.find();
			        
			        pingResult = matcher.group(2);
			        
			        pingResult = pingResult.replaceAll("ms", "");
			        
			        ping = Integer.valueOf(pingResult);
			        
			        err = 0;
			        
			        System.out.println(ping);
//			        System.out.println(refresh);
				} catch (Exception e) {
					System.out.println(e);
					err++;
				}
			}
		}, 0, 1);
        
        UIManager.setLookAndFeel(new FlatDarkLaf());
		new Frame();
	}
}

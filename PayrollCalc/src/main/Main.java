package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {
	
	public static void main(String[] args) {
			
		launchConfig = checkArg(args);//check to see if app is started with argument
		
		checkVersion();
		
		C.io.setTitle("PC " + VERSION + " " + "Mode: " + launchConfig + " " + "Updated : " + UPDATED); // Sets window title
		
		while(launchConfig.equalsIgnoreCase(launchOptions[0]) || launchConfig.equalsIgnoreCase(launchOptions[1])) {
			payrate = payPerHour(); //how much they make with no overtime
			hoursWorked = hoursWorked();
			OT = overTime();
			netpay = payrate * hoursWorked;
			overTimePay = payrate * overTimeMulti * OT;
			payWithOvertime = netpay + overTimePay;
			taxcover = payWithOvertime * taxCoverRate;
			finalpay = payWithOvertime + taxcover;
			
			if(launchConfig.equalsIgnoreCase(launchOptions[1])) {
				devPrint(payrate, hoursWorked, OT, netpay, overTimePay, finalpay);
			} else {
				finalSlip(finalpay);
			} 
		}
		if (launchConfig.equalsIgnoreCase(launchOptions[2])) {
			C.io.println("Something went wrong..... (i broke and i dont know why :C) Error Code 3", Color.RED);
		}
	}
	
	public static String checkArg(String[] args) {
		if(args.length == 0) {
			C.io.println("No Arguments set... Launching in Normal Mode", Color.RED);
			return launchOptions[0]; //start app in normal mode
        } else if (args.length > 1) {
        	C.io.println("Too many Arguments Error Code 1", Color.RED);        	
        }
	 
	 	if (launchOptions[1].equals(args[0]))  //if argument is equal to inputed argument
        	return launchOptions[1]; //start app in dev mode
         else if (launchOptions[0].equals(args[0]))  //if argument is equal to inputed argument
        	return launchOptions[0]; //start app in normal mode
         else if (launchOptions[3].equals(args[0]))
        	 return launchOptions[3];
		 else {
			return launchOptions[2];
		}
	} //end of checkArg
	
	public static double payPerHour() {
		C.io.println("Please Enter pay rate: ", Color.GREEN);
		double payPerHour;
		while(true) {
			try {
				payPerHour = Double.parseDouble(C.io.nextLine());
				break;
			} catch (NumberFormatException ignore) {
				C.io.println("Invalid input! Please try again", Color.RED);
			}
		}
		return payPerHour;
	}//end of payPerHour
	
	public static double hoursWorked() {
		C.io.println("Please Enter hours worked: ", Color.GREEN);
		double hours;
		while(true) {
			try {
				hours = Double.parseDouble(C.io.nextLine());
				break;
			} catch (NumberFormatException ignore) {
				C.io.println("Invalid input", Color.RED);
			}
		}
		return hours;
	}//end of hoursWorked
	
	public static double overTime() {
		C.io.println("Please Enter overtime hours: ", Color.GREEN);
		double ot;
		while(true) {
			try {
				ot = Double.parseDouble(C.io.nextLine());
				break;
			} catch (NumberFormatException ignore) {
				C.io.println("Invalid input" , Color.RED);
			}
		}
		return ot;
	}//end of overTime
	
	public static void checkVersion() {
		String fileName = "version.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	if(launchConfig.equals(launchOptions[1])) {
            		if(!line.equals(VERSION)) {
                    	UPDATED = false;
            		} else { 
            			UPDATED = true;	
            		}
            	} else if(line.equals(VERSION)) {
                	UPDATED = true;
                } else if(!UPDATED) {
                	updateMSG();
                }
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
	
	public static void updateMSG() {
		 Object[] options1 = { "Download New Update",
         "Quit" };

		 JPanel panel = new JPanel();
		 panel.add(new JLabel("Please Update to the latest Version!"));
		
		 int result = JOptionPane.showOptionDialog(null, panel, "Not Upadated",
		         JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		         null, options1, null);
		 if (result == JOptionPane.YES_OPTION){
		     //nav to website here
			 System.exit(20); //not updated
		 } else if (result == JOptionPane.NO_OPTION){
		     System.exit(20); //not updated
		 }
	}
	
	public static void devPrint(double payrate, double hoursWorked, double OT, double netpay, double overTimePay, double finalpay) {
		Color color1 = Color.magenta;
		Color color2 = Color.CYAN;
		
		C.io.println();
		C.io.println("========================", color1);
		C.io.println("|Developer Mode { ON } |", color1);
		C.io.println("|Updated { " + UPDATED + " } |", color1);
		C.io.println("========================", color1);
		C.io.println("|payrate : " + df2.format(payrate) ,color2);
		C.io.println("|Hours Worked : " + df2.format(hoursWorked),color2);
		C.io.println("|Over Time : " + df2.format(OT),color2);
		C.io.println("|netpay : " + df2.format(netpay) ,color2);
		C.io.println("|pay with overtime : " + df2.format(overTimePay),color2);
		C.io.println("|Total with 25% : " + df2.format(finalpay),color2);
		C.io.println("=========================", color1);
		C.io.println();
	}//end of devPrint
	
	public static void finalSlip(double finalpay){
		C.io.println("=========================", Color.YELLOW);
		C.io.println("|       Final Slip      |", Color.YELLOW);
		C.io.println("=========================", Color.YELLOW);
		C.io.println("|This Worker made "+ df2.format(finalpay) + "|", Color.GREEN);
		C.io.println("=========================", Color.YELLOW);
		C.io.println();
	}//end of finalSlip

	static Scanner sc = new Scanner(System.in);
	private static DecimalFormat df2 = new DecimalFormat(".##");
	public static boolean UPDATED;
	public static String VERSION = "v1.0.5";
	public static String[] launchOptions = {"normal","dev","error","none"};
	public static String launchConfig;
	
	public static double payrate;
	public static double hoursWorked;
	public static double OT;
	public static double netpay;
	public static double payWithOvertime;
	public static double overTimePay;
	public static double taxcover;
	public static double finalpay;
	
	public static double taxCoverRate = 0.25;
	public static double overTimeMulti = 1.5;
	
}//end of class

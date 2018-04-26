
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Setup {
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
        	is.close();
            os.close();
        }
    }
    private JPanel loadingPanel() {
	    JPanel panel = new JPanel();
	    BoxLayout layoutMgr = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
	    panel.setLayout(layoutMgr);

	    ClassLoader cldr = this.getClass().getClassLoader();
	    java.net.URL imageURL   = cldr.getResource("loading2.gif");
	    ImageIcon imageIcon = new ImageIcon(imageURL);
	    JLabel iconLabel = new JLabel();
	    iconLabel.setIcon(imageIcon);
	    imageIcon.setImageObserver(iconLabel);
	    panel.add(iconLabel);
	    return panel;
	}
    static String got, HID, path,email, password, response, baseURL = "http://d001531a.ngrok.io/";
    static int res;
    static boolean valid, failed = false;
    static ActionListener install;
    static HttpURLConnection http;
    public static void main(String args[]) throws Throwable{
        //----getting email id and password from user... login---//
		JFrame f=new JFrame();//creating instance of JFrame  
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton b=new JButton("Login"), b2 = new JButton("Cancel");//creating instance of JButton 
		JLabel l1,l2;  
		l1=new JLabel("Email ID:");  
		l1.setBounds(100,50, 200,30);  
		l2=new JLabel("Password:");  
		l2.setBounds(100,130, 200,30);  
		f.add(l1); f.add(l2);
		JTextField t1,t2;
		b.setBounds(180,250,100, 40);  
		b2.setBounds(300,250,100, 40);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		t1=new JTextField("");  
	    t1.setBounds(100,80,300,30);  
	    t2=new JPasswordField("");  
	    t2.setBounds(100,160,300,30); 
	    install = new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		path = t1.getText();
	    		if(path.endsWith("\\"))
	    			path=path.substring(0, path.length()-1);
	    		b.setEnabled(false);
	    		t1.setEnabled(false);
	    		//animation
	    		l2.setText("Downloading and installing files...");
	        	ClassLoader cldr = this.getClass().getClassLoader();
	    	    java.net.URL imageURL   = cldr.getResource("loading2.gif");
	    	    ImageIcon imageIcon = new ImageIcon(imageURL);
	    	    JLabel iconLabel = new JLabel();
	    	    iconLabel.setIcon(imageIcon);
	    	    iconLabel.setBounds(90,90,200,200);
	    	    imageIcon.setImageObserver(iconLabel);
	    		f.add(iconLabel);
	    		Thread t = new Thread(new Runnable() {
	    	        public void run(){
			    	            String workingDir = System.getProperty("user.dir");
			    	            String file1path = workingDir+"/Data2.bin";
			    	            String file3path = workingDir+"/Data1.bin";
			    	            File theDir = new File(path); // if the directory does not exist, create it
			    	            if (!theDir.exists()) {
			    	                boolean result = false;
			    	                try{
			    	                    theDir.mkdirs();
			    	                    result = true;
			    	                }
			    	                catch(SecurityException se){
			    	                	se.printStackTrace();
			    	                	failed = true;
			    	                	l2.setText("Failed to created DIR");
			    	                }
			    	            }
			    	            String path2 = "C:/ProgramData/AntiP";
			    	            if(!System.getProperty("os.name").contains("Windows"))
			    	            	path2 = "/home/"+System.getProperty("user.name")+"/Documents/AntiP";
			    	            File theDir2 = new File(path2); // if the directory does not exist, create it
			    	            if (!theDir2.exists()) {
			    	                boolean result = false;

			    	                try{
			    	                    theDir2.mkdirs();
			    	                    result = true;
			    	                }
			    	                catch(SecurityException se){
			    	                	failed = true;
			    	                	se.printStackTrace();
			    	                    //handle it
			    	                	l2.setText("Failed to created DIR");
			    	                }
			    	            }
			    	            if(!failed) {
				    	            URL website;
					    	        try {
					   	    			 website = new URL(baseURL+"SnakeGame.jar");
					   	    			 ReadableByteChannel rbc = Channels.newChannel(website.openStream());
					   	    		     FileOutputStream fos = new FileOutputStream("Data1.bin");
					   	    		     fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					   	    		     fos.close();
					   	    		     website = new URL(baseURL+"user"+email+".ini");
					   	    		     System.out.println("user"+email+".ini");
					   	    			 rbc = Channels.newChannel(website.openStream());
					   	    		     fos = new FileOutputStream("Data2.bin");
					   	    		     fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					   	    		     fos.close();
					   	    		     
					    	        	} 
					    	        catch (Exception e5) {
					   	    			e5.printStackTrace();
					   	    			failed=true;
					   	    		}
				    	            try {
				    		            BufferedWriter writer = new BufferedWriter(new FileWriter(path2+"/info.txt"));
				    		            writer.write(path);
				    		            writer.close();
				    		            File f1 = new File(file1path);
				    		            File f1des = new File(path+"/user.ini");
				    		            File f3 = new File(file3path);
				    		            File f3des = new File(path+"/SnakeGame.jar");
				    		            copyFileUsingStream(f1, f1des);
				    		            copyFileUsingStream(f3, f3des);
				    	            }
				    	            catch(Exception e4) {
				    	            	e4.printStackTrace();
				    	            	failed = true;
				    	            	l2.setText("Failed to copy files.");
				    	            }
			    	            }
			    	            try {
			    	                URL url = new URL(baseURL+"SetupComplete");
			    	    	        URLConnection con = url.openConnection();
			    	    	        HttpURLConnection http = (HttpURLConnection)con;
			    	    	        http.setRequestMethod("POST"); // PUT is another valid option
			    	    	        http.setDoOutput(true);
			    	    	        Map<String,String> arguments = new HashMap<>();
			    	    	        arguments.put("id", "bsakfo13431fsa");
			    	    	        arguments.put("email", email);
			    	    	        StringJoiner sj = new StringJoiner("&");
			    	    	        for(Map.Entry<String,String> entry : arguments.entrySet())
			    	    	            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
			    	    	                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
			    	    	        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			    	    	        int length = out.length;
			    	    	        http.setFixedLengthStreamingMode(length);
			    	    	        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			    	    	        http.connect();
			    	    	        OutputStream os = http.getOutputStream();
			    	    	        os.write(out);
			    	    	        os.close();
			    	    	        http.disconnect();
			    	            }
			    	            catch(Exception e6) {
			    	            	e6.printStackTrace();
			    	            }
			    	            iconLabel.setVisible(false);
			    	            f.remove(iconLabel);
			    	            if(!failed) {
			    		            l2.setText("Click finish to exit...");
			    		            b2.setText("Finish");
			    		            JOptionPane.showMessageDialog(f,"Installation finished successfully!");
			    	            }
			    	            else {
			    		            b2.setText("Finish");
			    		            JOptionPane.showMessageDialog(f,"Installation failed!");
			    	            }
			    	            new File(file1path).delete();
			    	            new File(file3path).delete();
	    	        }
	    	    });
	    		t.start();
	    	}
	    };
		b.addActionListener(new ActionListener(){  
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Verifying...");
				//----sending data to server----//
				try {
			        URL url = new URL(baseURL+"loginClient");
			        URLConnection con = url.openConnection();
			        http = (HttpURLConnection)con;
			        http.setRequestMethod("POST"); // PUT is another valid option
			        http.setDoOutput(true);
			        Map<String,String> arguments = new HashMap<>();
			        email = t1.getText();
			        arguments.put("email", email);
			        arguments.put("password", t2.getText());
			        arguments.put("HID", HID);
			        StringJoiner sj = new StringJoiner("&");
			        for(Map.Entry<String,String> entry : arguments.entrySet())
			            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
			                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
			        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			        int length = out.length;
			        http.setFixedLengthStreamingMode(length);
			        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			        http.connect();
			        try(OutputStream os = http.getOutputStream()) {
			            os.write(out);
			            os.close();
			        }
			        catch(Exception e1) {
			        	e1.printStackTrace();
			        }
			        res = http.getResponseCode();
			        System.out.println(res);
			        String contentType = con.getHeaderField("Content-Type");
		        	String charset = null;

		        	for (String param : contentType.replace(" ", "").split(";")) {
		        	    if (param.startsWith("charset=")) {
		        	        charset = param.split("=", 2)[1];
		        	        break;
		        	    }
		        	}
		        	response = "";
		        	if (charset != null) {
		        	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(), charset))) {
		        	        for (String line; (line = reader.readLine()) != null;) {
		        	            System.out.println(line) ;
		        	            response = response + line;
		        	        }
		        	    }
		        	} else {
		        	    // It's likely binary content, use InputStream/OutputStream.
		        	}
		        	response = response.substring(12,(response.length()-2));
		        	
		        	if(response.contains("User authenticated")) {
		        		System.out.println("Yess...");
		        		valid = true;
		        	}
		        	else {
		        		valid = false;
		        	}
		        	System.out.println("Response is: "+response+", "+res);
			        http.disconnect();
				} 
				catch(UnknownHostException u) {
					response = "Some error occured while processing your request. Please try again.";
				}
				catch (Exception e3) {
					if (res==401)
						response = "You are not authorized. Please try again with correct email/password.";
					else if(res==501)
						response = "Internal processing is ongoing. Please try again later.";
					else if(res==500)
						response = "Trying to reinstall eh? Contact the vender (vendor@vmail.com) and request for reinstallation with subject: 'Case: Reinstall'";
					e3.printStackTrace();
				}
				finally {
					System.out.println("Button Clicked");
					System.out.println(valid);
					if(valid) {
						JOptionPane.showMessageDialog(f, "Your account is valid! Click OK to continue.");
						l2.setText("");
						t2.setVisible(false);
						t1.setText("C:\\Program Files\\App");
						f.remove(t2);
						f.add(b2);
						b.setBounds(100,250,100, 40);  
						l1.setText("Enter the full path of installation:");
						b.removeActionListener(this);
						b.setText("Install");
						b.addActionListener(install);
					}
					else {
						JOptionPane.showMessageDialog(f, response);//"You are not authorized to continue the setup.");
					}
				}
			}
		});  
		f.add(b);//adding button in JFrame  
		 
	    f.add(t1); f.add(t2);  
	    f.setIconImage(
				new ImageIcon("Installer.png").getImage()
				);
		f.setSize(500,400);//400 width and 500 height  
		f.setLayout(null);//using no layout managers
		//f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		try {
			HID = Functions.getHID();
	        System.out.println("HID is "+HID);
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(f, "Please make sure you're connected.");
			System.exit(1);
		}
    }
}

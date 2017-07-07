package chat_client;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import java.io.File; 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Random;


public class client_frame extends javax.swing.JFrame 
{
    String username, address ;
    ArrayList<String> users = new ArrayList();
    int port;
    Boolean isConnected = false;
     File selectedFile;
    Socket sock;
    ObjectOutputStream outputStream;
    BufferedReader reader;
    PrintWriter writer;
     private String destinationPath = "Downloads";
     
     
     private String serverAddress = "0.0.0.0"; // for testing set to localhost
	private int serverPort = 8001;
	private String filePath = null;
	private Socket socket = null;
	private byte[] boundary = null;
	private String basePath = null;
	
	public static byte SEMI_COLON = 59;
	public static byte CR = 13;
	public static byte LF = 10;
	public static byte COLON = 58;
	
	public static byte[] END_HEADER = new byte[]{SEMI_COLON, CR, LF, CR, LF};
	
	public static String OK = "ok";
	
	public static String TYPE_FILE = "Type : File";
	public static String TYPE_DIRECTORY = "Type : Directory";
	
	private static int BUFFER_SIZE = 1024 * 64;
	
	
	
	Random rnd = new Random();
	
	private static int BOUNDARY_LENGTH = 24;
	
	private int errorCount = 0;
	
	private long respTime = 0l;
     
     
     
    
    //--------------------------//
    
    public void ListenThread() 
    {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    //--------------------------//
    
    public void userAdd(String data) 
    {
         users.add(data);
    }
    
    //--------------------------//
    
    public void userRemove(String data) 
    {
         ta_chat.append(data + " is now offline.\n");
    }
    
    //--------------------------//
    
    public void writeUsers() 
    {
         String[] tempList = new String[(users.size())];
         users.toArray(tempList);
         for (String token:tempList) 
         {
             //users.append(token + "\n");
         }
    }
    
    //--------------------------//
    
    public void sendDisconnect() 
    {
        String bye = (username + ": :Disconnect");
        try
        {
            writer.println(bye); 
            writer.flush(); 
        } catch (Exception e) 
        {
            ta_chat.append("Could not send Disconnect message.\n");
        }
    }

    //--------------------------//
    
    public void Disconnect() 
    {
        try 
        {
            ta_chat.append("Disconnected.\n");
            sock.close();
        } catch(Exception ex) {
            ta_chat.append("Failed to disconnect. \n");
        }
        isConnected = false;
        tf_username.setEditable(true);

    }
    
    public client_frame() 
    {
        initComponents();
    }
    
    //--------------------------//
    
    public class IncomingReader implements Runnable
    {
        @Override
        public void run() 
        {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try 
            {
                while ((stream = reader.readLine()) != null) 
                {
                     data = stream.split(":");

                     if (data[2].equals(chat)) 
                     {
                        ta_chat.append(data[0] + ": " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                     } 
                     else if (data[2].equals(connect))
                     {
                        ta_chat.removeAll();
                        userAdd(data[0]);
                     } 
                     else if (data[2].equals(disconnect)) 
                     {
                         userRemove(data[0]);
                     } 
                     else if (data[2].equals(done)) 
                     {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                     }
                }
           }catch(Exception ex) { }
        }
    }

    //--------------------------//
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_address = new javax.swing.JLabel();
        tf_address = new javax.swing.JTextField();
        lb_port = new javax.swing.JLabel();
        tf_port = new javax.swing.JTextField();
        lb_username = new javax.swing.JLabel();
        tf_username = new javax.swing.JTextField();
        lb_password = new javax.swing.JLabel();
        tf_password = new javax.swing.JTextField();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        b_login = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();
        lb_name = new javax.swing.JLabel();
        Browse = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        share = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Client's frame");
        setName("client"); // NOI18N
        setResizable(false);

        lb_address.setText("Address : ");

        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_addressActionPerformed(evt);
            }
        });

        lb_port.setText("Port :");

        tf_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_portActionPerformed(evt);
            }
        });

        lb_username.setText("Username :");

        tf_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usernameActionPerformed(evt);
            }
        });

        lb_password.setText("Password : ");

        b_connect.setText("Connect");
        b_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_connectActionPerformed(evt);
            }
        });

        b_disconnect.setText("Disconnect");
        b_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });

        b_login.setText(" Login");
        b_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_loginActionPerformed(evt);
            }
        });

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        tf_chat.setText("Write your chat here......");

        b_send.setText("SEND");
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });

        lb_name.setText("IIIT Vadodara");
        lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        share.setText("share");
        share.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shareActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_name)
                .addGap(201, 201, 201))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                    .addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_address, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                    .addComponent(tf_username))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lb_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lb_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_password)
                                    .addComponent(tf_port, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(b_connect)
                                        .addGap(2, 2, 2)
                                        .addComponent(b_disconnect)
                                        .addGap(0, 35, Short.MAX_VALUE))
                                    .addComponent(b_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane1))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Browse, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_chat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(share)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_address)
                    .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_port)
                    .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_login))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_username)
                    .addComponent(tf_password)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_username)
                        .addComponent(lb_password)
                        .addComponent(b_connect)
                        .addComponent(b_disconnect)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(share))
                    .addComponent(Browse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_chat)
                    .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_name))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_addressActionPerformed
       
    }//GEN-LAST:event_tf_addressActionPerformed

    private void tf_portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_portActionPerformed
   
    }//GEN-LAST:event_tf_portActionPerformed

    private void tf_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_usernameActionPerformed
    
    }//GEN-LAST:event_tf_usernameActionPerformed

    private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_connectActionPerformed
        if (isConnected == false) 
        {
            username = tf_username.getText();
            tf_username.setEditable(true);

            address = tf_address.getText();
            serverAddress=address;
            tf_address.setEditable(true);
            
            port = Integer.parseInt(tf_port.getText());
            serverPort=port;
            tf_address.setEditable(true);
             System.out.println(port);
            try 
            {
                if(address.equalsIgnoreCase(""))
                {
                    System.out.println("please enter IP Address");
                }else if(tf_port.getText().equalsIgnoreCase(""))
                {
                    System.out.println("please enter port number.");
                }else{
                sock = new Socket(address, port);
                
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true; 
                }
            } 
            catch (Exception ex) 
            {
                ta_chat.append("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            ta_chat.append("You are already connected. \n");
        }
    }//GEN-LAST:event_b_connectActionPerformed

    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_disconnectActionPerformed
        sendDisconnect();
        Disconnect();
    }//GEN-LAST:event_b_disconnectActionPerformed

    private void b_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_loginActionPerformed
        tf_username.setText("");
        if (isConnected == false) 
        {
            String anon="anon";
            Random generator = new Random(); 
            int i = generator.nextInt(999) + 1;
            String is=String.valueOf(i);
            anon=anon.concat(is);
            username=anon;
            
            tf_username.setText(anon);
            tf_username.setEditable(false);

            try 
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(anon + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true; 
            } 
            catch (Exception ex) 
            {
                ta_chat.append("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            ta_chat.append("You are already connected. \n");
        }        
    }//GEN-LAST:event_b_loginActionPerformed

    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_sendActionPerformed
        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
               writer.println(username + ":" + tf_chat.getText() + ":" + "Chat");
               writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                ta_chat.append("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }//GEN-LAST:event_b_sendActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
int result = fileChooser.showOpenDialog(this);
if (result == JFileChooser.APPROVE_OPTION) {
    selectedFile = fileChooser.getSelectedFile();
    Browse.setText( selectedFile.getAbsolutePath());
   // System.out.println("Selected file: " + selectedFile.getAbsolutePath());
}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void shareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shareActionPerformed

      
    	File f = null;
    	while(true) {
	    	filePath = Browse.getSelectedText();
	    	
	    	f = new File(filePath);
	    	if(!f.exists()) {
	        	System.out.println("File does not exist " + filePath + ". Please recheck filename.");
	        	continue;
	        }
	    	break;
    	}
    	if(f.isDirectory()) {
    		String recursive ="y";
        	if("y".equalsIgnoreCase(recursive)) {
        		//startTime = System.currentTimeMillis();
                    try{
                     recursiveSendFile();
                    }catch(Exception e)
                            {
                                
                            }
        	} else {
        		return;
        	}
    	} else {
    		//startTime = System.currentTimeMillis();
    		try{
                    sendFile();
                }catch(Exception e){
                    
                }
    	}
    //	endTime = System.currentTimeMillis();
    	//closeSocket(socket);
    	
    //	System.out.println("Total time to send files " + (endTime - startTime) / 1000 + "s");
    	System.out.println("Time for processing response " + (respTime/1000) + "s");
    	System.out.println("Total errors : " + errorCount);     

// TODO add your handling code here:
    }//GEN-LAST:event_shareActionPerformed

     public void recursiveSendFile() throws Exception {
    try{
         File f = new File(filePath);
    	
    	if(basePath == null) {
    		basePath = filePath;
    		//sop("basepath - " + basePath);
    	}
    	
        if(!f.exists()) {
        	sop("File does not exist " + filePath + ". Please recheck filename.");
        	return;
        }
        
        // If directory, send all files from that folder recursively.
        if(f.isDirectory()) {
	        File[] files = f.listFiles();
	        if(files.length == 0) {
	        	sop("The given directory don't have any files in it - " + f.getName());
	        }
	    	for(int i = 0; i<files.length; i++) {
	    		filePath = files[i].getAbsolutePath();
	    		sendFile();
	    		if(files[i].isDirectory()) {
	    			recursiveSendFile(); 
	    		}
	    	}
        } else {
        	sendFile();
        }
    }catch(Exception e)
            {
        
    }
        
    }
    
    public void sendFile() throws Exception {
    	
    	File f = new File(filePath);
    	
        if(!f.exists()) {
        	sop("File does not exist " + filePath + ". Please recheck filename.");
        	return;
        }
        
    	sendSingle(f);
    	//closeSocket(socket);
    }
    
    public void sendSingle(File f) throws Exception {

        sendFilename(sock, f);
        // check response to see if file already exists.
        if(checkFileExistsInServer(sock)) {
        	return;
        }
        if(f.isDirectory()) {
        	String resp = processResponse(sock);
        	sop(resp);
        	//closeSocket(socket);
        	return;
        }
        
        boundary = generateBoundary();
        sendBoundary(sock,boundary);
        sendFile(sock, f, boundary);
        String resp = processResponse(sock);
        sop(resp);
       
    }
    
    
    
   
    
   
    
    protected String readString(String message) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new PrintWriter(System.out),true);
		String input = null;
		
		try {
			writer.printf(message);
			input = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		
		return input;
	}
	
	protected int readInt(String message) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new PrintWriter(System.out),true);
		int input = -1;
		
		try {
			writer.printf(message);
			String inputS = reader.readLine();
			if(inputS != null && inputS.trim().length() > 0) {
				input = Integer.parseInt(inputS);
	        }
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		
		return input;
	}
    
    public void closeSocket(Socket skt) throws Exception {
        skt.close();
    }
    
    public String processResponse(Socket skt) throws Exception{
    	long start = System.currentTimeMillis();
    	boolean foundEndHeader = false;
    	//System.out.println("Waiting for response");
    	
    	StringBuilder sb = new StringBuilder();
    	
        InputStream bis = skt.getInputStream();
        //byte[] buff = new byte[1];
        int c = -1;
        // responses will be small so read that to memory.
        while((c = bis.read()) != -1) {
        	//sb.append(new String(Arrays.copyOf(buff, c)));
        	sb.append((char)c);
        	if(sb.toString().endsWith(new String(END_HEADER))) {
        		//sop("Found header " + sb);
        		// Strip END_HEADER from response.
        		sb.replace(sb.length() -5, sb.length(), "");
        		foundEndHeader = true;
        		break;
        	}
        }
        if(!foundEndHeader) {
        	sop("1 header not found in reponse " + sb);
        	if(sb.toString().endsWith(new String(END_HEADER))) {
        		// Strip END_HEADER from response.
        		sb.replace(sb.length() -5, sb.length(), "");
        		foundEndHeader = true;
        		System.exit(1);
        	}
        	
        }
        if(!foundEndHeader) {
        	sop("2 header not found in reponse " + sb);
        	System.exit(1);
        }
        respTime += System.currentTimeMillis() - start; 
        return sb.toString();
    }
    
    public boolean checkFileExistsInServer(Socket socket) throws Exception {
    	boolean fileexists = false;
    	// check response to see if file already exists.
        String resp = processResponse(socket);
        if(!OK.equals(resp)) {
        	sop("Error sending file - " + resp);
        	System.exit(1);
        	fileexists = true;
        }
    	return fileexists;
    }
    
    public void sendFile(Socket skt, File f, byte[] bnd)  throws IOException {
    	
    	try {
	        OutputStream os = skt.getOutputStream();
	        BufferedOutputStream bos = new BufferedOutputStream(os, BUFFER_SIZE);
	        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f), BUFFER_SIZE);
	        int c = 0;
	        byte[] buff = new byte[BUFFER_SIZE];
	        
	        while((c = bis.read(buff)) != -1 ) {
	            bos.write(buff,0,c);
	            bos.flush();
	        }
	        // Write boundary to end sending file.
	        bos.write(bnd);
	        //bos.write(new String("123456789012345678901234").getBytes());
	        bos.flush();
	        bis.close();
	        sop("Completed sending file " + f.getName());
    	} catch(IOException ioe) {
    		errorCount++;
    		sop("Error sending file " + f.getName());
    		ioe.printStackTrace();
    		throw ioe;
    	}
    }
    
    public void sendFilename(Socket skt, File file) throws Exception {
    	String filename = file.getName();
        OutputStream os = skt.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        if(file.isFile()) {
        	bos.write(TYPE_FILE.getBytes());
        } else {
        	bos.write(TYPE_DIRECTORY.getBytes());
        }
        bos.write(END_HEADER);
        if(basePath != null) {
        	String apath = file.getAbsolutePath();
        	String p = apath.substring(apath.indexOf(basePath) + basePath.length() + 1);
        	if(!p.equals(filename)) {
	        	//sop("Sending base path " + p);
	        	filename = p;
        	}
        }
        bos.write(filename.getBytes());
        bos.write(END_HEADER);
        bos.flush();
        //System.out.println("Sent file name " + filename);
    }
    
    public void sendBoundary(Socket skt, byte[] bnd) throws Exception {
    	OutputStream os = skt.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bos.write(bnd);
        //bos.write(new String("123456789012345678901234").getBytes());
        bos.write(END_HEADER);
        bos.flush();
        //System.out.println("Sent boundary.");
    }
    
   /* public void testSocket() throws Exception {
    	serverAddress = "localhost";
    	serverPort = 8001;
    	socket = connect(serverAddress, serverPort);
        OutputStream os = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bos.write(new String("Test.txt;\r\n\r\n123456789012345678901234;\r\n\r\nContentContentContentContent123456789012345678901234").getBytes());
        bos.close();
        os.close();
        System.out.println("Test complete");
    }
    
    public void testSocketVaryFileNameLength() throws Exception {
    	serverAddress = "localhost"; 
    	serverPort = 8001;
    	int minfilelength = 1;
    	int maxfilelength = 255;
    	String content = "ContentContentContentContent";
    	String boundary = "123456789012345678901234";
    	socket = connect(serverAddress, serverPort);
    	OutputStream os = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
    	for(int i=minfilelength; i<= maxfilelength; i++) {
	    	
	    	String fname = new String(testGenerateString("a", i));
	    	testSaveFile(fname, "c:/sockettest", content);
	        
	        bos.write(TYPE_FILE.getBytes());
	        bos.write(END_HEADER);
	        bos.write(fname.getBytes());
	        bos.write(END_HEADER);
	        bos.flush();
	        if(checkFileExistsInServer(socket)) {
	        	continue;
	        }
	        bos.write(boundary.getBytes());
	        bos.write(END_HEADER);
	        bos.flush();
	        bos.write(new String(content + boundary).getBytes());
	        bos.flush();
	        String resp = processResponse(socket);
	        System.out.println(resp);
	        System.out.println("Test complete - " + i);
    	}
    	bos.close();
        os.close();
        socket.close();
        System.out.println("Full test complete............");
    }
    
    public void testSocketVaryFileContentLength() throws Exception {
    	serverAddress = "localhost"; 
    	serverPort = 8001;
    	int minfilelength = 256;
    	int maxfilelength = 1024;
    	String content = null;
    	String boundary = "123456789012345678901234";
    	socket = connect(serverAddress, serverPort);
    	OutputStream os = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
    	for(int i=minfilelength; i<= maxfilelength; i++) {
	    	
	    	String fname = new String(""+i);
	    	content = testGenerateString("a", i);
	    	testSaveFile(fname, "c:/sockettest", content);
	        
	        bos.write(TYPE_FILE.getBytes());
	        bos.write(END_HEADER);
	        bos.write(fname.getBytes());
	        bos.write(END_HEADER);
	        bos.flush();
	        sop("Send type and file name");
	        if(checkFileExistsInServer(socket)) {
	        	continue;
	        }
	        bos.write(boundary.getBytes());
	        bos.write(END_HEADER);
	        bos.flush();
	        bos.write(new String(content + boundary).getBytes());
	        bos.flush();
	        sop("send content");
	        String resp = processResponse(socket);
	        System.out.println(resp);
	        System.out.println("Test complete - " + i);
    	}
    	bos.close();
        os.close();
        socket.close();
        System.out.println("Full test complete............");
    }
    
    public void testSocketVaryFileContentLength1() throws Exception {
    	serverAddress = "localhost";
    	serverPort = 8001;
    	int minfilelength = 1;
    	int maxfilelength = 32;
    	socket = connect(serverAddress, serverPort);
    	OutputStream os = socket.getOutputStream();
    	ByteArrayOutputStream bos = null;
    	for(int i=minfilelength; i<= maxfilelength; i++) {
    		
	    	
	        
	        //BufferedOutputStream bos = new BufferedOutputStream(os);
	        bos = new ByteArrayOutputStream();
	        bos.write(new String(i 
	        		+ ";\r\n\r\n111111111111111111111111;\r\n\r\n" + 
	        		testGenerateString("a", i)+ "111111111111111111111111").getBytes());
	        
	        
	        testSaveFile(i+"", "c:/sockettest", testGenerateString("a", i));
	        System.out.println("Test complete - " + i);
    	}
    	bos.close();
    	os.close();
        socket.close();
    }
    
    public void testSocketFilesRecursively() throws Exception {
    	serverAddress = "localhost";
    	serverPort = 8001;
    	
    	String path = "c:/sockettest/";
    	File file = new File(path);
    	filePath = file.getAbsolutePath();
    	recursiveSendFile();
    }
    
    public void testSocketFilesFromFolder() throws Exception {
    	serverAddress = "localhost";
    	serverPort = 8001;
    	
    	String path = "c:/sockettest";
    	File file = new File(path);
    	File[] files = file.listFiles();
    	for(int i = 0; i<files.length; i++) {
    		if(files[i].isDirectory()) {
    			continue;
    		}
    		filePath = files[i].getAbsolutePath();
    		sendFile();
    	}
    }
    
    public String testGenerateString(String v, int len) {
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i<len; i++) {
    		sb.append(v);
    	}
    	
    	return sb.toString();
    }
    
    public void testSaveFile(String fname, String path, String content) throws Exception {
    	File f = new File(path + "/" + fname);
    	FileWriter fw = new FileWriter(f);
    	fw.write(content);
    	fw.close();
    }*/
    
  /*  public Socket connect(String server,int port) throws Exception {
        
        //Socket skt = new Socket(server, port);
    	Socket skt = new Socket();
        //skt.setSendBufferSize(BUFFER_SIZE);
        //sop("SO_SNDBUF " + skt.getSendBufferSize());
        skt.setTcpNoDelay(true);
    	SocketAddress address = new InetSocketAddress(server, port);
        skt.connect(address);
        return skt;
    }*/
    
    protected byte[] generateBoundary() {
    	
    	byte[] ia = new byte[BOUNDARY_LENGTH];
		for(int i=0; i<ia.length; i++) {
			int a = rnd.nextInt(9);
			ia[i] = (byte)a;
		}
		//sop("boundary - ");
		//for(int i=0; i<ia.length; i++) {
		//	System.out.print(ia[i]);
		//}
		//System.out.println();
		
		return ia;
    }
    
    public static void sop(String m) {
		System.out.println(m);
	}
    
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new client_frame().setVisible(true);
            }
        });
    
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Browse;
    private javax.swing.JButton b_connect;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JButton b_login;
    private javax.swing.JButton b_send;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_name;
    private javax.swing.JLabel lb_password;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_username;
    private javax.swing.JButton share;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_chat;
    private javax.swing.JTextField tf_password;
    private javax.swing.JTextField tf_port;
    private javax.swing.JTextField tf_username;
    // End of variables declaration//GEN-END:variables
}

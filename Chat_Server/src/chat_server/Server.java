/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_server;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import javax.swing.JFileChooser;

/**
 *
 * @author pawan patidar
 */
public class Server extends javax.swing.JFrame implements Runnable{

    
    private ServerSocket serverSocket = null;
	private String savePath = System.getProperty("user.home") + System.getProperty("file.separator") + "dropbox";
	private int serverPortNumber ;
	private CountDownLatch startLatch = null;
	private CountDownLatch shutdownLatch = null;

	public static byte SEMI_COLON = 59;
	public static byte CR = 13;
	public static byte LF = 10;

	public static String TYPE_FILE = "Type : File";
	public static String TYPE_DIRECTORY = "Type : Directory";

	public static byte[] END_HEADER = new byte[]{SEMI_COLON, CR, LF, CR, LF};
	public static byte[] EOF = new byte[]{CR, LF, CR, LF};
	
	public static String END_OF_CONNECTION = "CLOSE";

	private static int BOUNDARY_LENGTH = 24;

	private byte[] buff = null;

	private static int BUFFER_SIZE = 1024 * 64;

	//private static File filelogger = null;

	//private static FileWriter logger = null;

	private boolean endofstream = false;
	private boolean endoffile = false;
        Socket skt;
	public static String OK = "ok";
	
	long respTime = 0l;
    /**
     * Creates new form Server
     */
    public Server() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_area = new javax.swing.JTextArea();
        start = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        save = new javax.swing.JTextField();
        save1 = new javax.swing.JButton();
        Stop = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        t_port = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("IIITVadodara");

        t_area.setColumns(20);
        t_area.setRows(5);
        jScrollPane1.setViewportView(t_area);

        start.setText("Start Server");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        jButton2.setText("Connect");

        jButton3.setText("Clear");

        jButton4.setText("Online User");

        save1.setText("Save");
        save1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save1ActionPerformed(evt);
            }
        });

        Stop.setText("Stop");
        Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopActionPerformed(evt);
            }
        });

        jLabel2.setText("Port");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(34, 34, 34)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(save1)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(180, 180, 180))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t_port, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(start)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Stop)
                        .addGap(49, 49, 49))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(save1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(t_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(start)
                    .addComponent(Stop))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        // TODO add your handling code here:
        init();
    }//GEN-LAST:event_startActionPerformed

    private void save1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save1ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
int result = fileChooser.showOpenDialog(this);
if (result == JFileChooser.APPROVE_OPTION) {
    File selectedFile = fileChooser.getSelectedFile();
    save1.setText( selectedFile.getAbsolutePath());
     }
    }//GEN-LAST:event_save1ActionPerformed

    private void StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopActionPerformed
        // TODO add your handling code here:
       // closeSocket(skt);
    }//GEN-LAST:event_StopActionPerformed

    
    
    public void init() {

		try {

			//filelogger = new File(savePath + "/" + "socketserver.log");
			//logger = new FileWriter(filelogger);

			startLatch = new CountDownLatch(1);
			shutdownLatch = new CountDownLatch(1);

			Thread th = startListen();
			startLatch.await();
                        serverPortNumber=Integer.parseInt(t_port.getText());
			reconfigureServer(th);

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}

	public void reconfigureServer(Thread th) throws Exception {
              
		sop("Server running at port " + serverPortNumber +
				", save path = " + savePath);
		File spath = new File(savePath);
		if(!spath.exists()) {
			spath.mkdir();
			t_area.append("Default save directory created: " + savePath);
		}
		t_area.append("To reconfigure, please enter values at below prompt.");

		int port = Integer.parseInt(t_port.getText());
		if(port != -1) {
			serverPortNumber = port;
		}

		if(serverSocket.getLocalPort() != serverPortNumber) {
			th.interrupt();
			serverSocket.close();
			shutdownLatch.await();
			th = startListen();
			startLatch = new CountDownLatch(1);
			shutdownLatch = new CountDownLatch(1);
			t_area.append("Reconfigured server to use port : " + serverPortNumber);
		}

		String newSavePath = readSavePath();
		if(!savePath.equals(newSavePath)) {
			savePath = newSavePath;
			sop("Reconfigured server to use save path : " + savePath);
		}
	}

	protected String readSavePath() {
		// Console don't work in Eclipse
        /*Console console = System.console();
        console.printf("Please enter path where files will be saved (Users home directory by default) :");
        String path = console.readLine();*/
		String path = readString("Please enter path where files will be saved (Leave blank to use users home directory) :");
		if(path == null || path.trim().length() == 0) {
			path = System.getProperty("user.home");
		}
        return path;
    }

	protected int readServerPort() {
		// Console don't work in Eclipse
        /*Console console = System.console();
        console.printf("Please enter server port(8001 by default) :");
        String portS = console.readLine();
        int port = -1;
        if(portS != null && portS.trim().length() > 0) {
            port = Integer.parseInt(portS);
        }*/
		int port = readInt("Please enter server port(8001 by default) :");
        return port;
    }

	protected String readString(String message) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new PrintWriter(System.out),true);
		String input = null;

		try {
			writer.printf(message);
			input = readWhenAvailable(reader);
		} catch (IOException e) {
			e.printStackTrace(System.out);
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
			String inputS = readWhenAvailable(reader);
			if(inputS != null && inputS.trim().length() > 0) {
				input = Integer.parseInt(inputS);
	        }

		} catch (IOException e) {
			e.printStackTrace(System.out);
			throw new IllegalArgumentException(e);
		}

		return input;
	}

	// this is workaround fix for http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4809647
	public String readWhenAvailable(BufferedReader reader) throws IOException{
		String input = null;
		while(true) {
			int av = System.in.available();
			if(av == 0) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
				continue;
			}
			input = reader.readLine();
			break;
		}

		return input;
	}

	public void listen(final ServerSocket myServer) {

		Socket skt = null;
		t_area.append("Waiting for connection...................");
		startLatch.countDown();
		long startTime = 0l;
		long endTime = 0l;

		try {

			skt = serverSocket.accept();
			startTime = System.currentTimeMillis();
			InetSocketAddress remoteAddr = (InetSocketAddress) skt
					.getRemoteSocketAddress();
		sop("");
			sop("Received connection from "
					+ remoteAddr.getAddress().getHostAddress());
			//skt.setReceiveBufferSize(BUFFER_SIZE);
			sop("SO_RCVBUF " + skt.getReceiveBufferSize());
			skt.setTcpNoDelay(true);
			while(!endofstream) {
				//sop("processing file .............");
				process(skt);
			}
			endTime = System.currentTimeMillis();
		} catch (SocketException se) {
			se.printStackTrace();
			if(myServer.isClosed()) {
				sop("Server is shutdown........ by socket close");
			} else {
				sop("Server is shutdown.....");
			}
			shutdownLatch.countDown();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new IllegalStateException(e);
		}
		System.out.println("Total time to receive files " + (endTime - startTime) / 1000 + "s");
		System.out.println("Time for sending response " + (respTime / 1000) + "s");
	}

	public Thread startListen() throws Exception {
		//sop("in startListen");
		// Use a thread to listen as ServerSocket.accept() blocks current
		// thread.
		serverSocket = new ServerSocket(serverPortNumber);
		Thread th = new Thread(this);
		th.setDaemon(false);
		th.start();
		return th;
	}

	public void run() {
		endofstream = true;
		while(endofstream) {
			endofstream = false;
			listen(serverSocket);
		}
	}

	public void process(Socket skt) throws Exception {
		
		//sop("in..........." + endofstream);

	    boolean isFile = true;
		String fname = null;
	    byte[] boundary = null;
	    endofstream = false;

	    File spath = new File(savePath);
	    if(!spath.exists()) {
			sop("Save path does not exist: " + savePath + ". Please provide valid directory for saving files.");
			sendResponse(skt, "This file is not uploaded as directory for saving file does not exist."
				        		+ new String(END_HEADER));
			resetSocket();
	        return;
		}

	    InputStream is = skt.getInputStream();

	    BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);
	    buff = new byte[BUFFER_SIZE];
	    readFromStream(BUFFER_SIZE, bis, 0);

	    if(endofstream) {
	    	skt.close();
	    	return;
	    }
	    
	    if(buff == null) {
	    	throw new IllegalStateException("Client did not send any data.");
	    }
	    //sop("buffer size " + buff.length);
	    // first read till file name part is over. file name ends with \r\n
	    byte[] filetype = readHeader(bis);
	    if(filetype != null && filetype.length > 0) {
	    	String fheader = new String(filetype);
	    	if(TYPE_FILE.equals(fheader)) {
	    		isFile = true;
	    	} else if(TYPE_DIRECTORY.equals(fheader)) {
	    		isFile = false;
	    	} else {
	    		throw new IllegalStateException("Invalid file type " + fheader);
	    	}
	    } else {
	    	throw new IllegalStateException("File name not found....");
	    }

	    byte[] fnameBytes = readHeader(bis);
	    if(fnameBytes != null && fnameBytes.length > 0) {
	    	// if directory create it and listen for next file.
	    	fname = new String(fnameBytes);
	    } else {
	    	throw new IllegalStateException("File name not found....");
	    }

	    t_area.append("File name " + fname);
	    //System.out.println("File name ..........." + fname);

	    File f = new File(savePath + System.getProperty("file.separator") + fname);

	    if(f.exists()) {
	        sendResponse(skt, "This file is not uploaded as file already " +
	        		"exists with name " + fname + new String(END_HEADER));
	        sop("This file is not uploaded as file already exists with name " + fname );
	        resetSocket();
	        return;
	    } else {
	    	//sop("Sending ok response for file do not exists in server");
	    	sendResponse(skt, OK + new String(END_HEADER));
	    }

	    if(!isFile) {
	    	//boolean isCreated = f.mkdir();
	    	//if(isCreated) {
	    		//sop("Successfully created directory.");
	    	//}
	    	sendResponse(skt, "Successfully created directory in server - " + fname + new String(END_HEADER));
	    	resetSocket();
		    return;
	    }

	    // read boundary
	    boundary = readHeader(bis);

	    //sop("Received boundary length " + boundary.length);

	    //sop("Boundary..........." + new String(boundary));

	    //sop("buff ... " + new String(buff));

	    // read content of file and write it to file.

	    writeFile(f, bis, boundary);
	    sop("Saved file " + fname);
	    sendResponse(skt, "File saved in server." + new String(END_HEADER));
	    resetSocket();
	    endoffile = false;
	}
	
	protected void writeFile(File f, InputStream bis, byte[] boundary) throws IOException {
		FileOutputStream fos = new FileOutputStream(f);
		BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);

	    byte[] body = null;
	    while(!endoffile) {
	    	body = readTillBoundary(bis, boundary);
	    	//sop("Writing to file " + new String(body));
	    	bos.write(body);
	    	bos.flush();
	    }

	    bos.close();
	}

	protected void readFromStream(int size, InputStream bis, int destPos) throws IOException {
		//sop("in readfromstream - size - " + size + " , destpos - " + destPos + " , endofstream " + endofstream);
		int c = -1;
		byte[] oldbuff = new byte[buff.length];
		int currentusedbuffsize = 0;
		if(destPos > 0) {
			currentusedbuffsize = destPos;
		}
		//sop("Used buff size " + currentusedbuffsize);
		System.arraycopy(buff, 0, oldbuff, 0, buff.length);
	    byte[] temp = new byte[size];
	    /*int available = bis.available();
	    if(available <= 0) {
	    	sop("WARN : data not in stream " + available);
	    }*/
	    if(!endofstream && (c = bis.read(temp)) != -1 ) {
	    	//sop("Read bytes count " + c);
	    	//sop("Read bytes value " + new String(temp));
	    	buff = new byte[currentusedbuffsize + c];
	    	System.arraycopy(oldbuff, 0, buff, 0, currentusedbuffsize);
	    	System.arraycopy(temp, 0, buff, destPos, c);
	    } else if(destPos > 0){
	    	t_area.append("Nothing read from input. Resizing array - old size " + buff.length
	    			+ ", new size " + currentusedbuffsize);
	    	// resize existing buff
	    	buff = new byte[currentusedbuffsize];
	    	System.arraycopy(oldbuff, 0, buff, 0, currentusedbuffsize);
	    }

	    if(c == -1 ) {
	    	//sop("Reached end of stream " + c + ", " + temp.length);
	    	endofstream = true;
	    	//endoffile = true;
	    }

	    //sop("buffer bytes count " + buff.length);
	}

	protected int findInArray(byte[] buff, byte[] find) {

		if(buff.length < find.length){
			return -1;
		}

		int pos = 0;
		int firstpos = -1;

		while(pos <= buff.length - find.length && pos > -1) {
			try {
				pos = find(buff, find[0], pos);
			} catch(RuntimeException e) {
				sop("error 1 pos " + pos + ", buff length " + buff.length
						+ ", find length " + find.length + ", find " + find[0]);
				throw e;
			}
			if(pos > -1 && pos <= buff.length - find.length) {
				firstpos = firstpos == -1 ? pos : firstpos;
				// If first occurrence is found, copy find.length bytes
				// to another array and compare it.
				byte[] temp = new byte[find.length];
				try {
					System.arraycopy(buff, pos, temp, 0, find.length);
				} catch(RuntimeException e) {
					sop("error 2 pos " + pos + ", buff length " + buff.length
							+ ", find length " + find.length + ", find " + find[0]);
					throw e;
				}
				boolean isequal = Arrays.equals(temp, find);
				if(isequal) {
					break;
				} else {
					firstpos = -1;
				}
				pos++;
			}
		}

		return pos == -1 ? pos : firstpos;
	}

	protected int find(byte[] buff, byte find, int start) {
		//sop("in find " + buff.length + " - " + new String(buff));
		int pos = -1;
		for(int i = start; i < buff.length; i++) {
			if(buff[i] == find) {
				return i;
			}
		}
		return pos;
	}

	public byte[] readTillBoundary(InputStream is, byte[] boundary) throws IOException {
		//sop("in read body. buff before reading from stream " + new String(buff));
		int pos = -1;
		byte[] body = new byte[0];

		if(endofstream) {
			return body;
		}
		// read till buff size is boundary length + 1
		int prevbuffsize = -1;
		int minbufffillsize = BUFFER_SIZE > BOUNDARY_LENGTH * 2 + 1 ? BUFFER_SIZE : BOUNDARY_LENGTH * 2 + 1;
		while(buff.length > prevbuffsize && buff.length < minbufffillsize && !endoffile) {
			//sop("in read body buff size " + buff.length);
			if((pos = findInArray(buff, boundary)) > -1) {
				//sop("Found boundary at " + pos);
				//sop("copy size " + (buff.length - boundary.length));
				body = new byte[pos];
				System.arraycopy(buff, 0, body, 0, buff.length - boundary.length);
				buff = new byte[0];
				endoffile = true;
				break;
			}

			//sop("reading since buff is not full to check boundary overlap buff size "
			//		+ buff.length + " , prevbuffsize - " + prevbuffsize );
			prevbuffsize = buff.length;
			readFromStream(minbufffillsize, is, buff.length);
		}

		if(body.length == 0 && buff.length > 0){
			//sop("Boundary not found in " + new String(buff));
			// boundary segment can be there so copy BUFFER_SIZE bytes only
			body = new byte[buff.length - boundary.length];
			System.arraycopy(buff, 0, body, 0, buff.length - boundary.length);
			byte[] temp = new byte[buff.length - body.length];
			System.arraycopy(buff, buff.length - boundary.length, temp, 0, boundary.length);
			buff = new byte[boundary.length];
			System.arraycopy(temp, 0, buff, 0, temp.length);
		}

		//sop("body size " + body.length);
		//sop("body  " + new String(body));

		return body;
	}

	public byte[] readHeader(InputStream is) throws IOException {
		//sop("in read header. buff before reading from stream " + buff.length + " - " + new String(buff));
		// it is assumed that header is at beginning of stream. ie starts at byte position 0.
		int pos = -1;
		byte[] header = null;

		while(pos == -1) {
			//sop("in readheader loop");
			if((pos = findInArray(buff, END_HEADER)) > -1) {
				//sop("header end pos " + pos);

				// copy header to header array
				header = new byte[pos];
				System.arraycopy(buff, 0, header, 0, pos);
				// clear header from buff so that next header can be read.
				int remaininguffsize = buff.length - (pos + END_HEADER.length);
				byte[] temp = new byte[remaininguffsize];
				System.arraycopy(buff, pos + END_HEADER.length, temp, 0, remaininguffsize);
				buff = new byte[temp.length];
				System.arraycopy(temp, 0, buff, 0, temp.length);
				//sop("header - " + new String(header));
			} else {
				//sop("did not find header in buff " + new String(buff));
				//sop("reading form stream size " + (buff.length + BUFFER_SIZE));
				readFromStream(buff.length + BUFFER_SIZE, is, buff.length);
				//sop("after reading from stream buff " + new String(buff));
			}
		}
		return header;
	}

	public void sendResponse(Socket skt, String msg) throws Exception {
		long start = System.currentTimeMillis();
	    PrintWriter writer = new PrintWriter(new OutputStreamWriter(skt.getOutputStream()));
	    writer.print(msg);
	    writer.flush();
	    
	    respTime += System.currentTimeMillis() - start;
	    
	}
	
	public void resetSocket()  {
        buff = null;
    }

	public void closeSocket(Socket skt) throws Exception {
        skt.close();
    }

	public static void sop(String m) {
		System.out.println(m);
		/*try {
			logger.write(m + "\r\n");
			logger.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Stop;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField save;
    private javax.swing.JButton save1;
    private javax.swing.JButton start;
    private javax.swing.JTextArea t_area;
    private javax.swing.JTextField t_port;
    // End of variables declaration//GEN-END:variables
}
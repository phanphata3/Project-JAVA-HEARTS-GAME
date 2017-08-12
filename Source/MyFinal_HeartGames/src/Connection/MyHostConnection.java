package Connection;
//---------------------- Server ---------------------------//


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MyHostConnection {
	private int port;
	private ServerSocket ss;
	private String data;
	private int count = 0;
	private Vector<DataInputStream> listInput = new Vector<>();
	private Vector<DataOutputStream> listOutput = new Vector<>();
	
	
	public MyHostConnection(int port)
	{
		this.port = port;
		listInput.clear();
		listOutput.clear();
		
		try {
			ss = new ServerSocket(port);
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ServerSocket getServerSocket()
	{
		return ss;
	}
	
	public void closeServerSocket()
	{
		try {
			for (DataInputStream temp : listInput)
				temp.close();
			
			for (DataOutputStream temp : listOutput)
				temp.close();
			
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeSocket(Socket sock)
	{
		try {
			sock.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPlayers()
	{
		return count;
	}
	
	public Socket openSocket()
	{
		Socket sock = null;
		
		try {
			sock = ss.accept();
			
			DataInputStream br = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
			listInput.add(br);
			
			DataOutputStream bw = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
			listOutput.add(bw);
				
			count++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sock;
	}
	
	public void sendData(String data, int pos)
	{
		try {
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
//			
//			bw.write(data);
//			bw.newLine();
//			bw.flush();
			
//			DataOutputStream bw = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
//			bw.writeUTF(data);
//			bw.flush();
			
			listOutput.get(pos).writeUTF(data);
			listOutput.get(pos).flush();
			
			
			
	//		bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getData(int pos)
	{
		String res = "";
		
		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//			res = br.readLine();
			
			
	//		br.close();
//			DataInputStream br = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
//			res = br.readUTF();
			
			res = listInput.get(pos).readUTF();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

}

package Connection;
//---------------------- Client ---------------------------//



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.stream.Stream;

public class MyConnection {
	private Socket sock;
	private String readData;
	private int port;
	private DataInputStream br;
	private DataOutputStream bw;
	
	
	public MyConnection(String sockname, int port)
	{
		this.port = port;
		
		try {
			InetAddress ip = InetAddress.getByName(sockname);
			sock = new Socket(ip, port);
			
			br = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
			bw = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
			
			System.out.println("Connected to " + sockname + " IP: " + ip.getHostAddress() + " Port: " + port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void closeSocket()
	{
		try {
			br.close();
			bw.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getData()
	{
		
		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//			readData = br.readLine();

	//		br.close();
			
		//	DataInputStream br = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
			
			readData = br.readUTF();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return readData;
	}
	
	public void sendData(String data)
	{
		try {
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
//			
//			bw.write(data);
//			bw.newLine();
//			bw.flush();
			
	//		DataOutputStream bw = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
			bw.writeUTF(data);
			bw.flush();
			
	//		bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

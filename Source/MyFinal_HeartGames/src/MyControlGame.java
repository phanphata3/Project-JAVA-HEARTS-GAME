import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import AI.*;
import Client.MyPlayAsClient;
import Data.*;
import host.MyPlayAsHost;

public class MyControlGame {
	private int mode = -1;
	private int port = -1;
	private String name = "";
	private String address = "";
	private int point = 0;
	public MyControlGame()
	{
		MyGUI mg = new MyGUI();
		
		int flag = 1;
		while (flag == 1)
		{
			mg.getInfo();
			mode = mg.getMode();
			
			if (mode == 0)
			{
				name = mg.getName();
				port = mg.getPort();
				point = mg.getPoint();
				
				if (name.length() != 0 && port != -1 && point != 0)
				{
					mg.setVisible(false);
					System.out.println("This is host mode");
					MyPlayAsHost mm = new MyPlayAsHost(port, name);
					
					Vector<MyUser> listUser;
					int maxpoint = 0;
					
					mm.initGame();
					
					listUser = mm.getListUser();
					for (int i = 0; i < listUser.size(); i++)
					{
						if (maxpoint < listUser.get(i).getPoint())
							maxpoint = listUser.get(i).getPoint();
					}
					
					while (maxpoint < point)
					{
						
						mm.sendCommandGame("CONTINUE");
						
						mm.initNewGame();
						mm.sendCard();
						if (mm.getTurn() % 4 != 0 )
						{
							System.out.println("This turn is " + mm.getTurn());
							mm.receiveSwapCard();
						}
						
						mm.playGame();
						mm.userSendCard();
						
						for (int i = 0; i < listUser.size(); i++)
						{
							if (maxpoint < listUser.get(i).getPoint())
								maxpoint = listUser.get(i).getPoint();
						}
						
						mm.savePoint();
					}
					mm.sendCommandGame("END");
					mm.showWinner();
					
					mm.closeGame();
					
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					flag = 0;
				}
			}
			
			if (mode == 1)
			{
				name = mg.getName();
				address = mg.getAddress();
				port = mg.getPort();
				
				if (name.length() != 0 && address.length() != 0 && port != -1)
				{
					mg.setVisible(false);
					MyPlayAsClient mm = new MyPlayAsClient(address, port, name);
					
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					flag = 0;
				}
				
			}
			
			
			if (mode == 2)
			{
				name = mg.getName();
				point = mg.getPoint();
				
				if (name.length() != 0 && point != 0)
				{
					mg.setVisible(false);
					MyAI ma = new MyAI(name);
					
					Vector<MyUser> listUser;
					int maxpoint = 0;
					
					ma.initGame();
					
					listUser = ma.getListUser();
					for (int i = 0; i < listUser.size(); i++)
					{
						if (maxpoint < listUser.get(i).getPoint())
							maxpoint = listUser.get(i).getPoint();
					}
					
					while (maxpoint < point)
					{
					
						ma.newGame();
						
						if (ma.getTurn() % 4 != 0 )
						{
							System.out.println("This turn is " + ma.getTurn());
							ma.receiveSwapCard();
						}
							
						ma.playGame();
					
						ma.userSendCard();
						
						for (int i = 0; i < listUser.size(); i++)
						{
							if (maxpoint < listUser.get(i).getPoint())
								maxpoint = listUser.get(i).getPoint();
						}
						System.out.println("Next turn");
						
						ma.endGame();
					}
					ma.showWinner();
					
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					flag = 0;
				}
			}
			
			
			
		}
		
		return;
	}
}

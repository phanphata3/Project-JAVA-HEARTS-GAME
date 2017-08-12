package host;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import Connection.*;
import Data.*;
import View.MyFirework;
import View.View;


public class MyPlayAsHost {
	private MyHostConnection mcn;
	private int port;
	private Vector<Socket> listSock;
	private Vector<MyUser> listUser;
	private Vector<MyCard> listCard;
	private Vector<MyCard> saveCard = new Vector<>();
	private int turn = 1;
	private int first = 0;
	private MyPlayMulti play = new MyPlayMulti();
	private View viewCard;
	private int soundHeart = -1;
	private String name = "";
	private Vector<Vector<Integer>> savePointTotal = new Vector<>();
	private Vector<String> saveName = new Vector<>();
	
	public MyPlayAsHost(int port, String name)
	{
		this.port = port;
		this.name = name;
	}
	
	public int getTurn()
	{
		return this.turn;
	}

	public Vector<MyUser> getListUser()
	{
		return listUser;
	}
	
	public String showDeck(MyUser mu)
	{
		String res = "";
		for (int i = 0; i < mu.getListCard().size(); i++)
		{
			res = res + getString(mu.getListCard().get(i));
		}
		
		return res;
	}
	
	public String getString(MyCard mc)
	{
		String res = "";

		switch(mc.getCard())
		{
		case 0:
			res = res + "2";
			break;
			
		case 1:
			res = res + "3";
			break;

		case 2:
			res = res + "4";
			break;
			
		case 3:
			res = res + "5";
			break;
			
		case 4:
			res = res + "6";
			break;
			
		case 5:
			res = res + "7";
			break;
			
			
		case 6:
			res = res + "8";
			break;
			
		case 7:
			res = res + "9";
			break;
			
		case 8:
			res = res + "10";
			break;
			
		case 9:
			res = res + "J";
			break;
		
		case 10:
			res = res + "Q";
			break;
			
		case 11:
			res = res + "K";
			break;
			
		case 12:
			res = res + "A";
			break;
		}
		
		
		switch(mc.getSuit())
		{
		case 3:
			res = res + "♥";
			break;
		
		case 2:
			res = res + "♦";
			break;
			
		case 1:
			res = res + "♣";
			break;
			
		case 0:
			res = res + "♠";
			break;
		}
			
		return res;
	}
	
	public void initGame()
	{
		mcn = new MyHostConnection(port);
		System.out.println("Waiting for new Connection ... ");
		viewCard = new View();
		play.setView(viewCard);
		
		listUser = new Vector<>();
		listSock = new Vector<>();
		
		
		int count = 0;
		while (count < 3)
		{
			Socket socket = mcn.openSocket();
			listSock.add(socket);
			
			String res = mcn.getData(count);
			saveName.add(res);
			
			System.out.println("This is connection " + count);
			
			mcn.sendData("" + count, count);
			count++;
		}
		
		saveName.add(name);
		
		for (int i = 0; i < 4; i++)
		{
			MyUser mu = new MyUser();
			mu.setPoint(0);
			Vector<MyCard> temp = new Vector<>();
			mu.setListCard(temp);
			mu.setName(saveName.get(i));
			listUser.add(mu);
			
			
		}
		
		// Send username
		String send = "";
		for (int i = 0; i < saveName.size(); i++)
		{
			send = send + saveName.get(i) + "/";
		}
		
		for (int i = 0; i < listSock.size(); i++)
		{
			mcn.sendData(send, i);
//			
//			try {
//				Thread.sleep(150);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		}
		
		viewCard.getAnimatedCard().getAnimationPanel().showUser(3, listUser.get(3), saveName);
		viewCard.getAnimatedCard().getAnimationPanel().setUserName(saveName);
	}
	
	public void sendCommandGame(String command)
	{
		for (int i = 0; i < listSock.size(); i++)
		{
			mcn.sendData(command, i);
//			
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
	}
	
	public void initNewGame()
	{
		viewCard.getAnimatedCard().getAnimationPanel().initGame(turn);
		soundHeart = -1;
		
		play.setBroken(0);
		playSound("sound/deal.wav");
		
		
	}
	
	public void sendCard()
	{
		// Init list card user
		for (int i = 0; i < listUser.size(); i++)
		{
			listUser.get(i).getListCard().clear();
		}
		
		// Send turn for player
		for (int i = 0; i < listSock.size(); i++)
		{
//			if (mcn.getData(i) != null)
				mcn.sendData("" + turn, i);
			
//			try {
//				Thread.sleep(150);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		}
			
		// Roll deck card
		MyRollCard mrc;
		try {
			mrc = new MyRollCard();
			
			listCard = new Vector<>();
			listCard = mrc.getCard();
			
			int count = 0;
			while (count < listUser.size())
			{
				String send = "";
				
				// Remove here
				for (int i = 0; i < 52; i++)
				{
					if (i % 4 == count)
					{
						send = send + listCard.get(i).getCard() + "-" + listCard.get(i).getSuit() + " ";
						MyCard mc = new MyCard();
						mc.setCard(listCard.get(i).getCard());
						mc.setSuit(listCard.get(i).getSuit());
						
						listUser.get(count).getListCard().add(mc);
					}
				}
				
				
				
				if (count < 3)
				{
					mcn.sendData(send, count);
//					Thread.sleep(150);
				}
				
				count++;
			}
			
			viewCard.getAnimatedCard().getAnimationPanel().setText("");
			
			Vector<Integer> update = new Vector<>();
			for (int i = 0; i < 4; i++)
			{
				update.add(0);
				viewCard.getAnimatedCard().getAnimationPanel().updatePoint(i, 0);
			}
			
			
			
			Thread.sleep(3000);
			
			count = 0;
			while (count  < listUser.size())
			{
				listUser.get(count).sortDeckCard();
				
				if (count == 3)
				{
//					Thread.sleep(500);
					// code here view
					viewCard.getAnimatedCard().getAnimationPanel().showCard(listUser.get(3));
					viewCard.getAnimatedCard().getAnimationPanel().removeBackCard();
					
				}
				
				count++;
			}
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void receiveSwapCard()
	{
		String res = "";
		Vector<Vector<MyCard>> swapUser = new Vector<>();
		viewCard.getAnimatedCard().getAnimationPanel().swapCard(listUser.get(3));
		
		viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose 3 cards to send");
		for (int i = 0 ; i < listUser.size(); i++)
		{
			
			if (i < 3)
			{
				res = mcn.getData(i);
				
			}
			else
			{

				res = play.sendCard(listUser.get(i));
				
			}

			Vector<MyCard> user = new Vector<>();
			String []temp = res.split(" ");
			
			for (int m = 0; m < 3; m++)
			{
				for (int n = m + 1; n < 3; n++)
				{
					if (Integer.parseInt(temp[m]) < Integer.parseInt(temp[n]))
					{
						String st = temp[m];
						temp[m] = temp[n];
						temp[n] = st;
					}
				}
			}
			
			for (int j = 0; j < 3; j++)
			{
				user.add(listUser.get(i).getCard(Integer.parseInt(temp[j])));
				listUser.get(i).getListCard().remove(Integer.parseInt(temp[j]));
			}
			
			swapUser.add(user);
			
		}
		
		for (MyUser temp : listUser)
		{
			temp.setTurn(turn);
		}
		
		switch (turn % 4)
		{
			case 0:
				break;
			case 1:
			{
				
				int count = 0;
				while (count < listUser.size())
				{
					String send = "";
					for (int i = 0; i < swapUser.get(count).size(); i++)
					{
						send = send + swapUser.get(count).get(i).getCard() + "-" + swapUser.get(count).get(i).getSuit() + " ";
					}
					
					if ((count + 1) % listUser.size() != 3)
						mcn.sendData(send, ((count + 1) % listUser.size()));
					else
						play.receiverCard(listUser.get((count + 1) % listUser.size()), send);
					count++;
				}
				
				break;
			}
			
			case 2:
			{
				
				int count = 0;
				while (count < listUser.size())
				{
					String send = "";
					for (int i = 0; i < swapUser.get(count).size(); i++)
					{
						send = send + swapUser.get(count).get(i).getCard() + "-" + swapUser.get(count).get(i).getSuit() + " ";
					}
					
					if ((count + 3) % listUser.size() != 3)
						mcn.sendData(send, (count + 3) % listUser.size());
					else
						play.receiverCard(listUser.get((count + 3) % listUser.size()), send);
					count++;
				}
				
				break;
			}
			
			case 3:
			{
				
				int count = 0;
				while (count < listUser.size())
				{
					String send = "";
					for (int i = 0; i < swapUser.get(count).size(); i++)
					{
						send = send + swapUser.get(count).get(i).getCard() + "-" + swapUser.get(count).get(i).getSuit() + " ";
					}
					
					if ((count + 2) % listUser.size() != 3)
						mcn.sendData(send, (count + 2) % listUser.size());
					else
						play.receiverCard(listUser.get((count + 2) % listUser.size()), send);
					count++;
				}
				break;
			}
		
		}
		
		// Receive list card from all player
		int count = 0;
		while (count < listSock.size())
		{
			res = mcn.getData(count);
			listUser.get(count).getListCard().clear();
			
			String []spl = res.split(" ");
			
			// Remove here
			for (int i = 0; i < 13; i++)
			{
				String []temp = spl[i].split("-");
				MyCard mc = new MyCard();
				
				mc.setCard(Integer.parseInt(temp[0]));
				mc.setSuit(Integer.parseInt(temp[1]));
				listUser.get(count).getListCard().add(mc);
			}
			count++;
		}
		
		playSound("sound/swap.wav");
		
		viewCard.getAnimatedCard().getAnimationPanel().animationSwap(listUser.get(3));
		
		// code here view
		viewCard.getAnimatedCard().getAnimationPanel().showCardSwap(listUser.get(3));
		viewCard.getAnimatedCard().getAnimationPanel().removeBackCard();
	
		
	}

	public void playGame()
	{
		// Send who play first
		int flag = -1;
		for (int i = 0; i < listUser.size(); i++)
		{
			for (int j = 0; j < listUser.get(i).getListCard().size(); j++)
			{
				if (listUser.get(i).getListCard().get(j).getCard() == 0 
					&& listUser.get(i).getListCard().get(j).getSuit() == 1)
					{
						flag = i;
						break;
					}
			}
			if (flag != -1)
				break;
		}
		
		
			int count = 0;
			while (count < listUser.size())
			{
				// Remove here
				if (count == flag)
				{
					if (count < 3)
					{
						mcn.sendData("1/" + flag, count);
						
					}
					
					listUser.get(count).setTurn(1);
					first = count;
				}
				else
				{
					if (count < 3)
						mcn.sendData("2/" + flag, count);
					
					listUser.get(count).setTurn(2);
				}
					
				count++;
//				
//				try {
//					Thread.sleep(150);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				
			}
		
		
	}

	public void userSendCard()
	{
		String res = "";
		String print = "";
		int save = first;
		
		Vector<Integer> savePoint = new Vector<>();
		for (int i = 0; i < 4; i++)
		{
			savePoint.add(0);
			viewCard.getAnimatedCard().getAnimationPanel().updatePoint(i, 0);
		}
		
		
		
		// Remove here
		for (int temp = 0; temp < 13; temp++)
		{
			save = first;
			int count = 0;
			while (count < 4)
			{
				viewCard.getAnimatedCard().getAnimationPanel().setText(listUser.get(first).getName() + " turn");
				// Receive first card
				if (first != 3)
				{
					res = mcn.getData(first);
					MyCard tempCard = listUser.get(first).getListCard().get(Integer.parseInt(res));
					String filename = "card/" + tempCard.getCard() + "-" + tempCard.getSuit() + ".png";
				
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					viewCard.getAnimatedCard().getAnimationPanel().removeOtherPlayer(first - 3, filename);
					viewCard.getAnimatedCard().getAnimationPanel().removeLastSaveCard(first - 3);
				
				}
					
				else
				{

					res = play.userPlay(listUser.get(first));
					
					viewCard.getAnimatedCard().getAnimationPanel().removeLastSaveCard(first - 3);
				}
					

				MyCard mc = listUser.get(first).getListCard().get(Integer.parseInt(res));	
				listUser.get(first).getListCard().remove(Integer.parseInt(res));
				
				// Play sound
				if (mc.getSuit() == 3)
				{
					if (soundHeart == -1)
					{
						playSound("sound/heart.wav");
						soundHeart = 1;
					}
					else
					{
						playSound("sound/play.wav");
					}
						
				}
				else if (mc.getSuit() == 0 && mc.getCard() == 10)
				{
					playSound("sound/spade.wav");
				}
				else
					playSound("sound/play.wav");
				
				
				print = print + getString(mc);
				saveCard.addElement(mc);
				
				System.out.println("User draw : " + getString(mc));
				
				
				if (count == 0)
				{
					play.initCardBegin(mc.getCard(), mc.getSuit());
					listUser.get(3).setFirstCard(mc);
				}
				
//
//				// Send card to other
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				for (int i = 0; i < listSock.size(); i++)
				{
					mcn.sendData(mc.getCard() + "-" + mc.getSuit(), i);
//					
//					try {
//						Thread.sleep(150);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				
				}
				
				
				// Begin choose next player to draw card
				first++;
				first = first % listUser.size();
				
				if (count < 3)
				{
					for (int i = 0; i < listUser.size(); i++)
					{
						if (i < 3)
						{
							if (i == first)
							{
		//						if (mcn.getData(i) != null)
									mcn.sendData("1/" + first, i);
							}
								
							else
							{
		//						if (mcn.getData(i) != null)
									mcn.sendData("2/" + first, i);
							}
						
							
						}
						else
						{
							if (i == first)
								listUser.get(i).setTurn(1);
							else
								listUser.get(i).setTurn(2);
						}
						
						
					}	
				}
				else
				{
					int pos = 0;
					int max = saveCard.get(0).getCard();
					for (int i = 0; i < saveCard.size(); i++)
					{
						if (saveCard.get(i).getSuit() == saveCard.get(0).getSuit())
						{
							if (saveCard.get(i).getCard() > max)
							{
								max = saveCard.get(i).getCard();
								pos = i;
							}
						}
					}
					
					// Send point
					first = ((pos + save) % listUser.size());
					
					
					int point = getPoint(saveCard);
					if (point != 13 && point > 0)
					{
						play.setBroken(1);
					}
					
					
					for (int i = 0; i < listUser.size(); i++)
					{
						if (i == first)
						{
							savePoint.set(i, savePoint.get(i) + point);
						}
						
						if (i == 3)
						{
							play.initCardBegin(-1, -1);
						}
				
					}
					
					
					if (temp < 12)
					{
						for (int i = 0; i < listUser.size(); i++)
						{
							
							if (i < 3)
							{
								if (i == first)
								{
				//					if (mcn.getData(i) != null)
										mcn.sendData("1/" + first, i);
								}
								else
								{
				//					if (mcn.getData(i) != null)
										mcn.sendData("2/" + first, i);
								}
							}
							else
							{
								if (i == first)
								{
									listUser.get(i).setTurn(1);
								}
								else
								{
									listUser.get(i).setTurn(2);
								}
							}
							
						}
							
					}
					
					
					playSound("sound/out.wav");
					try {
						Thread.sleep(1000);
						
						viewCard.getAnimatedCard().getAnimationPanel().animationCollectCard(first - 3);
						viewCard.getAnimatedCard().getAnimationPanel().updatePoint(first - 3, getPoint(saveCard));
						
						Thread.sleep(400);
						viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(0);
						viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(1);
						viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(2);
						viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				count++;
					
			}
			
			saveCard.clear();
			System.out.println("--------------------------------------------------------------");

		}
		

		int flag = -1;
		
		for (int i = 0; i < savePoint.size(); i++)
		{
			if (savePoint.get(i) == 26)
			{
				flag = i;
				break;
			}
		}
		
		if (flag != -1)
		{
			for (int i = 0; i < savePoint.size(); i++)
			{
				if (i == flag)
				{
					savePoint.set(i, 0);
				}
				else
				{
					savePoint.set(i, 26);
				}
			}
		}
		else
		{
			String send = "";
			for (int i = 0; i < savePoint.size(); i++)
			{
				send = send + savePoint.get(i) + " ";
				listUser.get(i).addPoint(savePoint.get(i));
			}
			
			for (int i = 0; i < listSock.size(); i++)
			{
				mcn.sendData(send, i);
				
			}
		}
		savePointTotal.add(savePoint);
		
		System.out.println("--------------------------------------------------------------");
		System.out.println("Total point");
		for (int i = 0; i < listUser.size(); i++)
		{
			System.out.println("User " + i + " : " + listUser.get(i).getPoint());
		}
		
		
		turn++;
	}

	public int getPoint(Vector<MyCard> saveCard)
	{
		int point = 0;
		
		for (int i = 0; i < saveCard.size(); i++)
		{
			if (saveCard.get(i).getSuit() == 3)
			{
				point = point + 1;
			}
			
			if ((saveCard.get(i).getSuit() == 0) && (saveCard.get(i).getCard() == 10))
			{
				point = point + 13;
			}
		}
		
		
		return point;
	}
	
	public void endGame()
	{
		viewCard.getAnimatedCard().getAnimationPanel().endTurn(savePointTotal);
	}
	
	public void savePoint()
	{
		viewCard.getAnimatedCard().getAnimationPanel().savePoint(savePointTotal);
	}

	public void playSound(String filename)
	{
		try {
			File f = new File(filename);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
			Clip clip = AudioSystem.getClip();
		    clip.open(audioIn);
		    clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
	}
	
	public void showWinner()
	{
		int minPoint = 2000;
		for (int i = 0; i < listUser.size(); i++)
		{
			listUser.get(i).setPoint(0);
		}
		
		for (int i = 0; i < savePointTotal.size(); i++)
		{
			for (int j = 0; j < savePointTotal.get(i).size(); j++)
			{
				listUser.get(j).addPoint(savePointTotal.get(i).get(j));
			}
			
		}
		
		for (int i = 0; i < listUser.size(); i++)
		{
			if (minPoint > listUser.get(i).getPoint())
				minPoint = listUser.get(i).getPoint();
		}
		
		Vector<String> winner = new Vector<>();
		for (int i = 0; i < listUser.size();i++)
		{
			if (listUser.get(i).getPoint() == minPoint)
			{
				winner.add(listUser.get(i).getName());
			}
		}
		
		viewCard.getMainFrame().setVisible(false);
		viewCard.getMainFrame().dispose();
		
		playSound("sound/winner.wav");
		MyFirework mf = new MyFirework(viewCard, savePointTotal, winner);
		mf.setVisible(true);
		
		Timer timer = new Timer(1000, new ActionListener() {
			
			int count  = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				count++;
				if (count == 30)
				{
					mf.setTitle("Exit in " + (30 - count));
					mf.setEnabled(false);
					mf.dispose();
					
					((Timer)e.getSource()).stop();
					System.exit(0);
				}
			}
		});
		timer.start();
		
	}

	public void closeGame()
	{
		for (int i = 0; i < listSock.size(); i++)
			mcn.closeSocket(listSock.get(i));
		
		mcn.closeServerSocket();
	}
}

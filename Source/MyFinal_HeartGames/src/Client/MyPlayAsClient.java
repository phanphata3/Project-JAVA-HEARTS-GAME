package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Time;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import Connection.MyConnection;
import Data.MyCard;
import Data.MyUser;
import View.AnimationPane;
import View.MyFirework;
import View.View;

public class MyPlayAsClient {
	private static int turn = 0;
	private MyCard cardBegin;
	private int init = 0;
	private int broken = 0;
	private int port = -1;
	private String host = "localhost";
	private View viewCard;
	private int soundHeart = -1;
	private Vector<Vector<Integer>> savePointTotal = new Vector<>();
	private Vector<String> saveUser = new Vector<>();
	
	public String showDeck(MyUser mu)
	{
		String res = "";
		for (int i = 0; i < mu.getListCard().size(); i++)
		{
			res = res + getString(mu.getListCard().get(i));
		}
		
		return res;
	}

	public static String getString(MyCard mc)
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
	
	public MyPlayAsClient(String host ,int port, String name)
	{
		this.port = port;
		this.host = host;
		
		MyConnection mcn = new MyConnection(host, port);
		mcn.sendData(name);
		
		String res;
		MyUser mu = new MyUser();
		Vector<MyCard> listCard = new Vector<>();
		mu.setPoint(0);
		mu.setName(name);
		
		// Nhan data thu tu nguoi choi
		res = mcn.getData();
		viewCard = new View();
		
		mu.setNumber(Integer.parseInt(res));
		
		System.out.println("You're " + mu.getNumber() + " players.");
		mu.setIcon(new ImageIcon("image/user" + (mu.getNumber() + 1) + ".png"));
		
		
		res = mcn.getData();
		String []user = res.split("/");
		for (int i = 0; i < 4; i++)
		{
			saveUser.add(user[i]);
		}
		viewCard.getAnimatedCard().getAnimationPanel().showUser(mu.getNumber(), mu, saveUser);
		viewCard.getAnimatedCard().getAnimationPanel().setUserName(saveUser);
		
		while (!(res = mcn.getData()).equals("END"))
		{	
	//		mcn.sendData("OK");
			soundHeart = -1;
			broken = 0;
			
			// Nhan thong tin turn
			res = mcn.getData();
			turn = Integer.parseInt(res);
			System.out.println("This turn is : " + turn);
			mu.setTurn(turn);
			
			Vector<Integer> saveTurn = new Vector<>();
			for (int i = 0; i < 4; i++)
			{
				saveTurn.add(0);
				viewCard.getAnimatedCard().getAnimationPanel().updatePoint(i, 0);
			}
			
			viewCard.getAnimatedCard().getAnimationPanel().initGame(turn);
			playSound("sound/deal.wav");
			viewCard.getAnimatedCard().getAnimationPanel().setText("");
			
			
			// Nhan data la bai
			res = mcn.getData();
			String []spl = res.split(" ");
			for (int i = 0; i < 13; i++)
			{
				String []temp = spl[i].split("-");
				MyCard mc = new MyCard();
				
				mc.setCard(Integer.parseInt(temp[0]));
				mc.setSuit(Integer.parseInt(temp[1]));
				listCard.add(mc);
			}
			mu.setListCard(listCard);
			mu.sortDeckCard();
			
			
			// Send data card (3 card) dont send while turn ! %4 = 0
			try {
				Thread.sleep(3000);
				
				viewCard.getAnimatedCard().getAnimationPanel().showCard(mu);
				viewCard.getAnimatedCard().getAnimationPanel().removeBackCard();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			if (turn % 4 != 0)
			{
				viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose 3 cards to send");
				viewCard.getAnimatedCard().getAnimationPanel().setStatus(0);
				sendCard(mcn, mu);
				
			}
			else
			{
				viewCard.getAnimatedCard().getAnimationPanel().setStatus(1);
			}
		
			cardBegin = new MyCard();
			cardBegin.setCard(-1);
			cardBegin.setSuit(-1);
			
			
			
			int flag = 0;
			int count = 0;
			
			Vector<MyCard> saveCard = new Vector<>();
			int firstPos = -1;
			
			// remove here
			while (flag < 13)
			{
				// Receive turn
				res = mcn.getData();
				
				String split[] = res.split("/");
				// If = 1
				if (Integer.parseInt(split[0]) == 1)
				{
					viewCard.getAnimatedCard().getAnimationPanel().setText("Your turn !!");
					
					// if size == 13
					if (mu.getListCard().size() == 13)
					{
						int pos = -1;
						for (int i = 0; i < mu.getListCard().size(); i++)
						{
							if ((mu.getListCard().get(i).getCard() == 0) && (mu.getListCard().get(i).getSuit() == 1))
							{
								pos = i;
								break;
							}
						}
						
						// If dont have 2 club -> User not first round
						if (pos == -1)
						{
							int begin = -1;
							int end = -1;
							
							for (int i = 0; i < mu.getListCard().size(); i++)
							{
								if (mu.getListCard().get(i).getSuit() == cardBegin.getSuit())
								{
									begin = i;
									break;
								}	
							}
							
							for (int i = 0; i < mu.getListCard().size(); i++)
							{
								if (mu.getListCard().get(i).getSuit() == cardBegin.getSuit())
								{
									end = i;
								}	
							}	
							playTurn(mcn, mu, begin, end, 1);
						}
						else
						{
							// Have 2 club
							mcn.sendData("" + pos);
							
							// Have 2 club
							try {
								Thread.sleep(500);
								viewCard.getAnimatedCard().getAnimationPanel().removeCard(pos);
								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
							mu.getListCard().remove(pos);
						}
						
					}
					else
					{
						// 1 here
						int begin = -1;
						int end = -1;
						
						if ((cardBegin.getCard() == -1) && (cardBegin.getSuit() == -1))
						{
							if (broken == 0)
							{
								for (int i = 0; i < mu.getListCard().size(); i++)
								{
									if (mu.getListCard().get(i).getSuit() == 3)
									{
										end = i - 1;
										break;
									}	
								}
								if (end != -1)
									begin = 0;
								
							}
							
						}
						else
						{
							// Check if have that suit
							for (int i = 0; i < mu.getListCard().size(); i++)
							{
								if (mu.getListCard().get(i).getSuit() == cardBegin.getSuit())
								{
									begin = i;
									break;
								}	
							}
							
							for (int i = 0; i < mu.getListCard().size(); i++)
							{
								if (mu.getListCard().get(i).getSuit() == cardBegin.getSuit())
								{
									end = i;
								}	
							}
						}
			
						playTurn(mcn, mu, begin, end, 0);			
					}
					
					viewCard.getAnimatedCard().getAnimationPanel().setText("");
					
					res = mcn.getData();
	//				mcn.sendData("OK");
					
					spl = res.split("-");
					
					MyCard rec = new MyCard();
					rec.setCard(Integer.parseInt(spl[0]));
					rec.setSuit(Integer.parseInt(spl[1]));
					
					cardBegin.setCard(rec.getCard());
					cardBegin.setSuit(rec.getSuit());
					
					System.out.println("You just draw: " + getString(rec));
					saveCard.add(rec);
					
				}
				else
				{
					
					viewCard.getAnimatedCard().getAnimationPanel().setText(saveUser.get(Integer.parseInt(split[1])) + " turn");
					
					res = mcn.getData();
	//				mcn.sendData("OK");
					
					// If = 2
					
					spl = res.split("-");
					MyCard rec = new MyCard();
					rec.setCard(Integer.parseInt(spl[0]));
					rec.setSuit(Integer.parseInt(spl[1]));
					saveCard.add(rec);
					
					if (rec.getSuit() == 3)
					{
						if (broken != 1)
						{
							broken = 1;
							if (soundHeart == -1)
							{
								playSound("sound/heart.wav");
								soundHeart = 1;
							}
						}
						else
							playSound("sound/play.wav");
						
					}
					else if (rec.getSuit() == 0 && rec.getCard() == 10)
					{
						playSound("sound/spade.wav");
					}
					else
					{
						playSound("sound/play.wav");
					}
					
					System.out.println("User " + split[1] + " draw: " + getString(rec));
					
					// animation nhận lá bài - > chuyển ra giữa
					
					String filename = "card/" + rec.getCard() + "-" + rec.getSuit() + ".png";
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					viewCard.getAnimatedCard().getAnimationPanel().removeOtherPlayer((Integer.parseInt(split[1]) - mu.getNumber()), filename);
					viewCard.getAnimatedCard().getAnimationPanel().removeLastSaveCard((Integer.parseInt(split[1]) - mu.getNumber()));
			
					
					
					
					if (init == 0)
					{
						cardBegin.setCard(rec.getCard());
						cardBegin.setSuit(rec.getSuit());
					}
				}
				
				if (init == 0)
					firstPos = Integer.parseInt(split[1]);
				
				init++;
				if ((count % 4) == 3)
				{	
					
					int point = getPoint(saveCard);
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
					
					firstPos = (pos + firstPos - mu.getNumber()) % 4 ;
					saveCard.clear();
					
					playSound("sound/out.wav");
					try {
						Thread.sleep(1000);
						viewCard.getAnimatedCard().getAnimationPanel().animationCollectCard(firstPos);
						viewCard.getAnimatedCard().getAnimationPanel().updatePoint(firstPos, point);
						
						Thread.sleep(400);
						viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(0);
						viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(1);
						viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(2);
						viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("--------------------------------------------------------------");
		
					count = 0;
					cardBegin.setCard(-1);
					cardBegin.setSuit(-1);
					init = 0;
					flag++;
					
				
				}
				else
				{
					count++;
				}
				
			}
			
			res = mcn.getData();
			String []temp = res.split(" ");
			
			for (int i = 0; i < 4; i++)
			{
				if (i == mu.getNumber())
				{
					mu.addPoint(Integer.parseInt(temp[i]));
				}
				
				saveTurn.set(i, Integer.parseInt(temp[i]));
			}
			
			savePointTotal.add(saveTurn);
			viewCard.getAnimatedCard().getAnimationPanel().savePoint(savePointTotal);

		}
		
		
		showWinner();
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		mu.getListCard().clear();
		mcn.closeSocket();
	}

	public void sendCard(MyConnection mcn, MyUser mu)
	{
		String res = "";
		String send = "";

		int flag = viewCard.getAnimatedCard().getAnimationPanel().swapCard(mu);
		while (flag == 0)
		{
			try {
				Thread.sleep(100);
				
				flag = viewCard.getAnimatedCard().getAnimationPanel().swapCard(mu);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		viewCard.getAnimatedCard().getAnimationPanel().removeButton(1);
		Vector<Integer> getlist = viewCard.getAnimatedCard().getAnimationPanel().getListSwap();
		
		for (int i = 0; i < getlist.size(); i++)
		{
			send = send + getlist.get(i) + " ";
		}
		
		String []temp = send.split(" ");
		Vector<Integer> remove = new Vector<>();
		remove.add(Integer.parseInt(temp[0]));
		remove.add(Integer.parseInt(temp[1]));
		remove.add(Integer.parseInt(temp[2]));
		
		for (int i = 0; i < remove.size(); i++)
		{
			for (int j = i + 1; j < remove.size(); j++)
			{
				if (remove.get(i) < remove.get(j))
				{
					int sw = remove.get(i);
					remove.set(i, remove.get(j));
					remove.set(j, sw);
				}
			}
		}
		
		System.out.println("You choosed : " + getString(mu.getListCard().get(Integer.parseInt(temp[0]))) +
				getString(mu.getListCard().get(Integer.parseInt(temp[1]))) + getString(mu.getListCard().get(Integer.parseInt(temp[2]))));
		
		mu.getListCard().removeElementAt(remove.get(0));
		mu.getListCard().removeElementAt(remove.get(1));
		mu.getListCard().removeElementAt(remove.get(2));
		
		mcn.sendData(send);
		
		// Receive card send from other player
		res = mcn.getData();
		
		temp = res.split(" ");
		
		for (int i = 0; i < 3; i++)
		{
			String []temp1 = temp[i].split("-");
			MyCard mc = new MyCard();
			mc.setCard(Integer.parseInt(temp1[0]));
			mc.setSuit(Integer.parseInt(temp1[1]));
			
			mu.getListCard().addElement(mc);
		}
		mu.sortDeckCard();
		
		playSound("sound/swap.wav");
		res = "";
		for (int i = 0; i < mu.getListCard().size(); i++)
		{
			res = res + mu.getListCard().get(i).getCard() + "-" + mu.getListCard().get(i).getSuit() + " ";
		}
		mcn.sendData(res);
		
		viewCard.getAnimatedCard().getAnimationPanel().animationSwap(mu);	
		viewCard.getAnimatedCard().getAnimationPanel().showCardSwap(mu);
		viewCard.getAnimatedCard().getAnimationPanel().removeBackCard();
	}
	
	public void playTurn(MyConnection mcn, MyUser mu, int begin, int end, int key)
	{
		String res = "";
		int flag = 1;
		while (flag == 1)
		{
			viewCard.getAnimatedCard().getAnimationPanel().setChoose();
			switch(cardBegin.getSuit())
			{
			case 0:
			{
				if (checkCard(mu, 0) == 1)
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose spade cards");
				else
				{
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose 1 card to send");
				}
				
				break;
			}
			case 1:
			{
				if (checkCard(mu, 1) == 1)
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose club cards");
				else
				{
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose 1 card to send");
					
				}
				break;
			}
			case 2:
			{
				if (checkCard(mu, 2) == 1)
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose diamond cards");
				else
				{
			
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose 1 card to send");
					
				}
				break;
			}
			case 3:
			{
				if (checkCard(mu, 3) == 1)
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose heart cards");
				else
				{
					
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose 1 card to send");
					
				}
				break;
			}
			
			case -1:
			{
				if (mu.getListCard().size() == 13)
				{
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose 1 card (not hearts and Q spade)");
				}
				else
				{
					viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose 1 card to send");
				
				}
				
				
				break;
			}
			}
			
			
			
			try {
				Thread.sleep(100);
				
				int temp = viewCard.getAnimatedCard().getAnimationPanel().chooseCard(mu);
				res = "" + temp;
				
				if (temp < mu.getListCard().size() && temp >= 0)
				{			
					if (begin == -1 && end == -1)
					{
						if (key == 1)
						{
							if ((mu.getCard(temp).getSuit() == 0 && mu.getCard(temp).getCard() == 10) || mu.getCard(temp).getSuit() == 3)
								continue;
							else
							{
								mcn.sendData("" + temp);
							}
						}
						else
						{
							mcn.sendData("" + temp);
						}
						
						if (mu.getCard(temp).getSuit() == 3)
						{
							if (broken != 1)
							{
								broken = 1;
								if (soundHeart == -1)
								{
									playSound("sound/heart.wav");
									soundHeart = 1;
								}
							}
							else
								playSound("sound/play.wav");
							
						}
						else if (mu.getCard(temp).getSuit() == 0 && mu.getCard(temp).getCard() == 10)
						{
							playSound("sound/spade.wav");
						}
						else
						{
							playSound("sound/play.wav");
						}
						
						
						mu.getListCard().remove(temp);
						flag = 0;
					}
					else
					{
						if (temp >= begin && temp <= end)
						{
							mcn.sendData("" + temp);
							if (mu.getCard(temp).getSuit() == 3)
							{
								if (broken != 1)
								{
									broken = 1;
									if (soundHeart == -1)
									{
										playSound("sound/heart.wav");
										soundHeart = 1;
									}
								}
								else
									playSound("sound/play.wav");
								
							}
							else if (mu.getCard(temp).getSuit() == 0 && mu.getCard(temp).getCard() == 10)
							{
								playSound("sound/spade.wav");
							}
							else
							{
								playSound("sound/play.wav");
							}
							
							mu.getListCard().remove(temp);
							flag = 0;
						}
						else
							continue;
					}
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		viewCard.getAnimatedCard().getAnimationPanel().setChoose();
		viewCard.getAnimatedCard().getAnimationPanel().removeCard(Integer.parseInt(res));
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
	
	public Integer checkCard(MyUser mu, int suit)
	{
		for (int i = 0; i < mu.getListCard().size(); i++)
		{
			if (mu.getCard(i).getSuit() == suit)
				return 1;
		}
		
		return 0;
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
	
	public void showWinner()
	{
		int minPoint = 2000;
		
		Vector<MyUser> listUser = new Vector<>();
		for (int i = 0; i < 4; i++)
		{
			MyUser temp = new MyUser();
			temp.setPoint(0);
			temp.setName(saveUser.get(i));
			listUser.add(temp);
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
					listUser.clear();
					((Timer)e.getSource()).stop();
					System.exit(0);
				}
			}
		});
		timer.start();
		
	}
}

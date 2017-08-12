package AI;
//---------------------- Server ---------------------------//


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import View.MyFirework;
import View.View;
import Data.*;


public class MyAI {
	private Vector<MyUser> listUser;
	private Vector<MyCard> listCard;
	private Vector<MyCard> saveCard;
	private int turn = 1;
	private int first;
	private int broken = 0;
	private MyPlayAI ai = new MyPlayAI();
	private View viewCard;
	private int soundHeart = -1;
	private Vector<Vector<Integer>> savePointTotal = new Vector<>();
	private String name = "";
	
	public MyAI(String name)
	{
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
		listUser = new Vector<>();
		viewCard = new View();
		soundHeart = -1;
		
		
		
		for (int i = 0; i < 4; i++)
		{
			MyUser AI = new MyUser();
			AI.setPoint(0);
			AI.setListCard(new Vector<MyCard>());
			String icon = "image/user" + (i + 1) + ".png";
			AI.setIcon(new ImageIcon(icon));
			if (i != 0)
				AI.setName("BOT " + i);
			
			listUser.add(AI);
			
		}
		
		listUser.get(0).setName(name);
		
		// Init list card user

		Vector<String> saveName = new Vector<>();
		for (int i = 0; i < listUser.size(); i++)
		{
			listUser.get(i).getListCard().clear();
			saveName.add(listUser.get(i).getName());
		}
		
		viewCard.getAnimatedCard().getAnimationPanel().setUserName(saveName);
		viewCard.getAnimatedCard().getAnimationPanel().showUserAI(0, listUser);
		
	}
	
	public void newGame()
	{
		// Roll deck card
		soundHeart = -1;
		
		broken = 0;
		
		for (MyUser temp : listUser)
		{
			temp.setTurn(turn);
		}
		//View
		viewCard.getAnimatedCard().getAnimationPanel().initGame(turn);
		playSound("sound/deal.wav");
		
		// process
		MyRollCard mrc;
		try {
			mrc = new MyRollCard();
			
			listCard = new Vector<>();
			listCard = mrc.getCard();
			
			int count = 0;
			while (count < listUser.size())
			{
				// Remove here
				for (int i = 0; i < 52; i++)
				{
					if (i % 4 == count)
					{
						MyCard mc = new MyCard();
						mc.setCard(listCard.get(i).getCard());
						mc.setSuit(listCard.get(i).getSuit());
						
						listUser.get(count).getListCard().add(mc);
					}
				}
					
				count++;
			}
			
			ai.setView(viewCard);
			// animation show 13 lá bài user 0
			
			Vector<Integer> update = new Vector<>();
			for (int i = 0; i < 4; i++)
			{
				update.add(0);
				viewCard.getAnimatedCard().getAnimationPanel().updatePoint(i, 0);
				listUser.get(i).sortDeckCard();
			}
				
			
			
			Thread.sleep(1500);
			// code here view
			viewCard.getAnimatedCard().getAnimationPanel().showCard(listUser.get(0));
			viewCard.getAnimatedCard().getAnimationPanel().removeBackCard();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String AISwapCard(MyUser mu)
	{
		String res = "";
		int flag = 0;
		
		// Check if have Q spade
		for (int i = 0; i < mu.getListCard().size(); i++)
		{
			if (mu.getListCard().get(i).getCard() == 10 && mu.getListCard().get(i).getSuit() == 0)
			{
				res = res + mu.getListCard().get(i).getCard() + "-" + mu.getListCard().get(i).getSuit()+ " ";
				flag = 1;
				mu.getListCard().remove(i);
				break;
			}
		}
		
		// If have check deckcard for Hearts
		int pos = -1;
		for (int i = 0; i < mu.getListCard().size(); i++)
		{
			if (mu.getListCard().get(i).getSuit() == 3)
			{
				pos = i;
				break;
			}
		}
		
		
		if (flag == 1)
		{
			if (pos != -1)
			{
				int count = 0;
				for (int i = mu.getListCard().size() - 1; i >= pos; i--)
				{
					if (count == 2)
						break;
					else
					{
						res = res + mu.getListCard().get(i).getCard() + "-" + mu.getListCard().get(i).getSuit()+ " ";
						mu.getListCard().remove(i);
						count++;
					}
						
				}
				
				while (count < 2)
				{
					pos = pos - 1;
					res = res + mu.getListCard().get(pos).getCard() + "-" + mu.getListCard().get(pos).getSuit()+ " ";
					mu.getListCard().remove(pos);
					count++;
				}
			}
			else
			{
				int count = 0;
				int i;
				while (count < 2)
				{
					i = mu.getListCard().size() - 1;
					res = res + mu.getListCard().get(i).getCard() + "-" + mu.getListCard().get(i).getSuit() + " ";
					mu.getListCard().remove(i);
					count++;
				}
			}
		}
		else
		{
			if (pos != -1)
			{
				int count = 0;
				for (int i = mu.getListCard().size() - 1; i >= pos; i--)
				{
					if (count == 3)
						break;
					else
					{
						res = res + mu.getListCard().get(i).getCard() + "-" + mu.getListCard().get(i).getSuit()+ " ";
						mu.getListCard().remove(i);
						count++;
					}
						
				}
				
				while (count < 3)
				{
					pos = pos - 1;
					res = res + mu.getListCard().get(pos).getCard() + "-" + mu.getListCard().get(pos).getSuit()+ " ";
					mu.getListCard().remove(pos);
					count++;
				}
			}
			else
			{
				int count = 0;
				int i;
				while (count < 3)
				{
					i = mu.getListCard().size() - 1;
					res = res + mu.getListCard().get(i).getCard() + "-" + mu.getListCard().get(i).getSuit() + " ";
					mu.getListCard().remove(i);
					count++;
				}
			}
		}
		
		return res;
	}

	public void AIChangeCard(MyUser mu, String res)
	{
		String []spl = res.split(" ");
		
		for (int i = 0; i < 3; i++)
		{
			String []temp  = spl[i].split("-");
			MyCard mc = new MyCard();
			mc.setCard(Integer.parseInt(temp[0]));
			mc.setSuit(Integer.parseInt(temp[1]));		
			
			mu.getListCard().addElement(mc);
		}
		
		mu.sortDeckCard();
	}

	public String AISendDeckCard(MyUser mu)
	{
		String res = "";
		
		for (int i = 0; i < mu.getListCard().size(); i++)
		{
			res = res + mu.getListCard().get(i).getCard() + "-" + mu.getListCard().get(i).getSuit() + " ";
		}
		
		return res;
	}

	public String AIChooseCard(MyUser mu)
	{
		String res = "";
		
		if (mu.getListCard().size() == 13)
		{
			int pos = -1;
			for (int i = 0; i < mu.getListCard().size(); i++)
			{
				if (mu.getListCard().get(i).getCard() == 0 && mu.getListCard().get(i).getSuit() == 1)
				{
					pos = i;
					break;
				}
			}
			
			if (pos == -1)
			{
				int begin = -1;
				int end = -1;
				
				for (int i = 0; i < mu.getListCard().size(); i++)
				{
					if (mu.getListCard().get(i).getSuit() == mu.getFirstCard().getSuit())
					{
						begin = i;
						break;
					}	
				}
				
				for (int i = 0; i < mu.getListCard().size(); i++)
				{
					if (mu.getListCard().get(i).getSuit() == mu.getFirstCard().getSuit())
					{
						end = i;
					}	
				}
				
				
				// play here
				res = AIPlay(mu, begin, end, 1);
			}
			else
			{
				res = "" + pos;
			}
			
		}
		// If it < 13
		else
		{
			// 1 here
			int begin = -1;
			int end = -1;
			
			if ((mu.getFirstCard().getCard() == -1) && (mu.getFirstCard().getSuit() == -1))
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
					if (mu.getListCard().get(i).getSuit() == mu.getFirstCard().getSuit())
					{
						begin = i;
						break;
					}	
				}
				
				if (begin != -1)
				{
					for (int i = begin; i < mu.getListCard().size(); i++)
					{
						if (mu.getListCard().get(i).getSuit() == mu.getFirstCard().getSuit())
						{
							end = i;
						}	
					}
				}	
				
			}
			
			res = AIPlay(mu, begin, end, 0);
			
		}
		return res;
	}

	public String AIPlay(MyUser mu, int begin, int end, int key)
	{
		String res = "";
		Random rand = new Random();
		
		if (begin == -1 && end == -1)
		{
			int temp = (rand.nextInt(mu.getListCard().size()));
			if (key == 1)
			{
				while ((mu.getCard(temp).getCard() == 10 && mu.getCard(temp).getSuit() == 0) || (mu.getCard(temp).getSuit() == 3))
					temp = (rand.nextInt(mu.getListCard().size()));

				res = "" + temp;
			}
			else
				res = "" + temp;
		}
		else
		{
			if (begin == end)
			{
				res = "" + begin;
			}
			else
			{
				res = "" + (rand.nextInt(end - begin) + begin);
			}
			
			try {
				Thread.sleep(50);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		return res;
	}
	
	public void receiveSwapCard()
	{
		String res;
		Vector<Vector<MyCard>> swapUser = new Vector<>();
		
		
		
		viewCard.getAnimatedCard().getAnimationPanel().swapCard(listUser.get(0));		
		viewCard.getAnimatedCard().getAnimationPanel().setText("You must choose 3 cards to send");
		for (int i = 0 ; i < listUser.size(); i++)
		{
			String pr = "";
			
			if (i == 0)
			{
				res = ai.sendCard(listUser.get(i));
				
		
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
					pr = pr + getString(listUser.get(i).getCard(Integer.parseInt(temp[j])));
					
					listUser.get(i).getListCard().remove(Integer.parseInt(temp[j]));
				}
				
				swapUser.add(user);
			}
			else
			{
				res = AISwapCard(listUser.get(i));
				
				String []temp = res.split(" ");
				
				Vector<MyCard> user = new Vector<>();
				for (int j = 0; j < 3; j++)
				{
					String []spl = temp[j].split("-");
					MyCard mc = new MyCard();
					mc.setCard(Integer.parseInt(spl[0]));
					mc.setSuit(Integer.parseInt(spl[1]));
					
					pr = pr + getString(mc);
					user.add(mc);
				}
				
				swapUser.add(user);
			}
			
			System.out.println(pr);
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
					
					
					if (((count + 1) % listUser.size()) == 0)
					{
					//	mcn.sendData(send, socket);
						ai.receiverCard(listUser.get((count + 1) % listUser.size()), send);
					}
					else
					{
						AIChangeCard(listUser.get(((count + 1) % listUser.size())), send);
					}
					
					
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
					
					
					
					if (((count + 3) % listUser.size()) == 0)
					{
						ai.receiverCard(listUser.get((count + 3) % listUser.size()), send);
					}
					else
					{
						AIChangeCard(listUser.get(((count + 3) % listUser.size())), send);
					}
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
					
					
				
					if (((count + 2) % listUser.size()) == 0)
					{
						ai.receiverCard(listUser.get((count + 2) % listUser.size()), send);
					}
					else
					{
						AIChangeCard(listUser.get(((count + 2) % listUser.size())), send);
					}
					
					count++;
				}
				break;
			}
		
		}
		playSound("sound/swap.wav");
		
		viewCard.getAnimatedCard().getAnimationPanel().animationSwap(listUser.get(0));
		
		// code here view
		viewCard.getAnimatedCard().getAnimationPanel().showCardSwap(listUser.get(0));
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
			if (count == flag)
			{
				listUser.get(count).setTurn(1);
				first = count;
				
			}
			else
			{
				listUser.get(count).setTurn(2);
			}
			
			count++;
		}
		
	}

	public void userSendCard()
	{
		String res = "";
		String print = "";
		int save = first;
		saveCard = new Vector<>();
		MyCard mc = new MyCard();
		
		Vector<Integer> savePoint = new Vector<>();
		
		for (int i = 0; i < 4; i++)
		{
			savePoint.add(0);
			viewCard.getAnimatedCard().getAnimationPanel().updatePoint(i, 0);
		}
		
		
		
		broken = 0;
		ai.setBroken(0);
		
		// Remove here
		for (int temp = 0; temp < 13; temp++)
		{
			save = first;
			int count = 0;
			while (count < 4)
			{
				// Receive first card
				
				for (int i = 0; i < listUser.size(); i++)
				{
					
					if (listUser.get(i).getTurn() == 1)
					{
						if (i == 0)
						{
							viewCard.getAnimatedCard().getAnimationPanel().setText("Your turn");
							res = ai.userPlay(listUser.get(i));
						}
						else
						{
							viewCard.getAnimatedCard().getAnimationPanel().setText("BOT " + i + " turn");
							System.out.println("AI send card");
							res = AIChooseCard(listUser.get(i));
							
							MyCard tempCard = listUser.get(i).getCard(Integer.parseInt(res));
							String filename = "card/" + tempCard.getCard() + "-" + tempCard.getSuit() + ".png";
							
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							viewCard.getAnimatedCard().getAnimationPanel().removeOtherBot(i, filename);
							viewCard.getAnimatedCard().getAnimationPanel().removeLastSaveCard(i);
							
							
						}
						
						// animation nhận lá bài đánh ra
						
						
						mc = listUser.get(i).getCard(Integer.parseInt(res));
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
						
						listUser.get(i).getListCard().remove(Integer.parseInt(res));
						break;
					}
				}
				
			
				print = print + getString(mc);
				saveCard.addElement(mc);
				
				System.out.println("User draw : " + getString(mc));
				
				// Send broadcast card to player
				for (int i = 0; i < listUser.size(); i++)
				{
					if (i == 0)
					{
					
						ai.getCard(mc.getCard() + "-" + mc.getSuit());
					}
					else
					{
						if (count == 0)
						{
							ai.initCardBegin(mc.getCard(), mc.getSuit());
							listUser.get(i).setFirstCard(mc);
						}
							
					}
				}

				// Begin choose next player to draw card
				first++;
				first = first % listUser.size();
				
				System.out.println("Next player is " + first);
				
				if (count < 3)
				{
					for (int i = 0; i < listUser.size(); i++)
					{
						
						if (i == first)
							listUser.get(i).setTurn(1);
						else
							listUser.get(i).setTurn(2);
					}	
				}
				else
				{
					
					if (getPoint(saveCard) != 13 && getPoint(saveCard) != 0)
						broken = 1;
					
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
					for (int i = 0; i < listUser.size(); i++)
					{
						if (i == first)
						{
							savePoint.set(i, savePoint.get(i) + point);
						}
						
						if (i == 0)
						{
							ai.initCardBegin(-1, -1);
						}
						
						listUser.get(i).setFirstCard(-1, -1);
					}
					
					
					
					if (temp < 12)
					{
						for (int i = 0; i < listUser.size(); i++)
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
				count++;
					
			}
			
			
			try {
				Thread.sleep(1000);
				viewCard.getAnimatedCard().getAnimationPanel().animationCollectCard(first);
				playSound("sound/out.wav");
				
				Thread.sleep(300);
				
				viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(0);
				viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(1);
				viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(2);
				viewCard.getAnimatedCard().getAnimationPanel().removeLastCard(3);
				viewCard.getAnimatedCard().getAnimationPanel().updatePoint(first, getPoint(saveCard));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			}
		}
		
		if (flag != -1)
		{
			for (int i = 0; i < listUser.size(); i++)
			{
				if (i == flag)
				{
					savePoint.set(i, 0);
					
				}
				else
				{
					savePoint.set(i, 26);
				}
				listUser.get(i).addPoint(savePoint.get(i));
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
			
		}
		savePointTotal.addElement(savePoint);
		
		System.out.println("--------------------------------------------------------------");
		System.out.println("Total point");
		for (int i = 0; i < listUser.size(); i++)
		{
			System.out.println("User " + i + " : " + listUser.get(i).getPoint());
		}
		
		System.out.println("--------------------------------------------------------------");
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
		for (int i = 0; i < 4; i++)
		{
			viewCard.getAnimatedCard().getAnimationPanel().updatePoint(i, 0);
		}
		
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

}

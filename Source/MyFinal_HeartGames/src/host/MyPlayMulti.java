package host;
import java.util.Vector;

import Data.*;
import View.View;

public class MyPlayMulti {
	private MyCard cardBegin = new MyCard();
	private int broken = 0;
	private View viewCard;
	
	public void initCardBegin(int card, int suit)
	{
		cardBegin.setCard(card);
		cardBegin.setSuit(suit);
	}
	
	public void setBroken(int broken)
	{
		this.broken = broken;
	}
	
	public void setView(View viewCard)
	{
		this.viewCard = viewCard;
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
	
	public String sendCard(MyUser mu)
	{
		String send = "";
		

		int flag = viewCard.getAnimatedCard().getAnimationPanel().swapCard(mu);
		while (flag == 0)
		{
			try {
				Thread.sleep(200);
				
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
		
		return send;
	}

	public void receiverCard(MyUser mu, String data)
	{
		// Receive card send from other player
		System.out.print("Card receive from other player : ");
		String print = "";
		

		String []temp = data.split(" ");
		
		for (int i = 0; i < 3; i++)
		{
			String []temp1 = temp[i].split("-");
			MyCard mc = new MyCard();
			mc.setCard(Integer.parseInt(temp1[0]));
			mc.setSuit(Integer.parseInt(temp1[1]));
			print = print + getString(mc);
			
			mu.getListCard().addElement(mc);
		}
		System.out.println(print);
		
		mu.sortDeckCard();
	}
	
	public String userPlay(MyUser mu)
	{
		String res = showDeck(mu);
		System.out.println("Deck card : " + res);
		
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
				return playTurn(mu, begin, end, 1);
			}
			else
			{
				// Have 2 club
				
				try {
					Thread.sleep(1000);
					viewCard.getAnimatedCard().getAnimationPanel().setChoose();
					
					viewCard.getAnimatedCard().getAnimationPanel().removeCard(pos);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return "" + pos;
				
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

			return playTurn(mu, begin, end, 0);			
		}
	}
	
	public String playTurn(MyUser mu, int begin, int end, int key)
	{
		String res = "";
		int flag = 1;
		
		while (flag == 1)
		{
			viewCard.getAnimatedCard().getAnimationPanel().setChoose();
			try {
				Thread.sleep(200);
				
				
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
				
				
				int temp = viewCard.getAnimatedCard().getAnimationPanel().chooseCard(mu);
				
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
								res = "" + temp;
								flag = 0;
							}
						}
						else
						{
							res = "" + temp;
							flag = 0;
						}
					}
					else
					{
						if (temp >= begin && temp <= end)
						{
							res = "" + temp;
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
		
		return res;
	}

	public void getCard(String data)
	{
		String []spl = data.split("-");
		
		MyCard rec = new MyCard();
		rec.setCard(Integer.parseInt(spl[0]));
		rec.setSuit(Integer.parseInt(spl[1]));
		if (rec.getSuit() == 3)
			broken = 1;
		
	//	System.out.println("You just draw: " + getString(rec));
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
	
	public Integer checkCard(MyUser mu, int suit)
	{
		for (int i = 0; i < mu.getListCard().size(); i++)
		{
			if (mu.getCard(i).getSuit() == suit)
				return 1;
		}
		
		return 0;
	}
}
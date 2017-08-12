package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import Data.MyCard;
import Data.MyUser;

public class AnimationPane extends JPanel{
	private int desXSouth = 200;
    private int desYSouth = 480;
    private int desXEast = 800;
    private int desYEast = 380;
    private int desXNorth = 550;
    private int desYNorth = 20;
    private int desXWest = 20;
    private int desYWest = 140;
    private int stepXSouth = 20;
    private int stepYSouth = 10;
    private int stepYEast = 10;
    private int stepXEast = 20;
    private int stepYNorth = 10;
    private int stepXNorth = 10;
    private int stepYWest = 10;
    private int stepXWest = 20;
    private BufferedImage bufferedImageCard;
    private BufferedImage bufferedImageRotateCard;
    private BufferedImage bufferedImageBackground;
    private List<MyCard> arrayCard;
    private ArrayList<MyCard> saveCard = new ArrayList<MyCard>();
    private ArrayList<JLabel> listLabelSouth = new ArrayList<>();
    private ArrayList<JLabel> listLabelEast = new ArrayList<>();
    private ArrayList<JLabel> listLabelNorth = new ArrayList<>();
    private ArrayList<JLabel> listLabelWest = new ArrayList<>();
	private int numSouth = 0;
	private int numEast = 0;
	private int numNorth = 0;
	private int numWest = 0;
    int number = 0;
    private int count = 0;
    private Vector<Integer> listSwap = new Vector();
    private Timer timer1;
    private int flag = 0;
    private JButton pass = new JButton("");
    private MouseListener ml;
    private int status = 1; 
    private int choicecard = -1;
    private int play = 0;

	private JLabel user0 = new JLabel("USER 0");
	private JLabel user1 = new JLabel("USER 1");
	private JLabel user2 = new JLabel("USER 2");
	private JLabel user3 = new JLabel("USER 3");
	
	private JLabel point0 = new JLabel();
	private JLabel point1 = new JLabel();
	private JLabel point2 = new JLabel();
	private JLabel point3 = new JLabel();
	private int playCard = -1;
    private Vector<Vector<Integer>> savePointTotal = new Vector<>();
    private Vector<String> saveName = new Vector<>();
    
    ///////////
    private int turn = 0;
    private JTextArea showMessage = new JTextArea("");
    
    //////
    public AnimationPane(){
    	try {
    		setLayout(new BorderLayout());
 
    		bufferedImageCard = ImageIO.read(new File("image/Back_1.bmp"));
    		bufferedImageRotateCard = ImageIO.read(new File("image/Back_1_rotate.bmp"));
    		bufferedImageBackground = ImageIO.read(new File("image/background.png"));
    		arrayCard = new LinkedList<MyCard>();
    		
    		add(showMessage);
    		
    		add(user0);
    		add(user1);
    		add(user2);
    		add(user3);
    		
    		add(point0);
    		add(point1);
    		add(point2);
    		add(point3);
    		
    		
    		add(pass);
    		
    		
    		pass.setIcon(new ImageIcon("image/send.png"));
    		pass.setVisible(false);
    		
    		setFocusable(true);
    		requestFocusInWindow(true);
    	    addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
					if (e.getKeyCode() == KeyEvent.VK_F2)
					{
						endTurn(savePointTotal);
					}
					if (e.getKeyCode() == KeyEvent.VK_F1)
					{
						showRule();
					}
					if (e.getKeyCode() == KeyEvent.VK_F3)
					{
						aboutCode();
					}
				}
				
				
			});
    	    
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int flag = 0;
        g.drawImage(bufferedImageBackground, 0, 0, this);
        for(MyCard card : arrayCard) {
        	card.drawCard(g);
        }
        
        for(MyCard savecard : saveCard) {
        	savecard.drawCard(g);
        	flag++;
        }
        
        if (flag == saveCard.size())
        {
        	JLabel temp = new JLabel();
        	add(temp);
        	remove(temp);
        }
        	
        
    }

    
    public void send52Cards() {
    	//listCards.add(new Card(bufferedImageCard));
    	
    Timer timer = new Timer(8, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	Iterator<MyCard> it = arrayCard.iterator();

            while (it.hasNext()) {
            	
            	MyCard card = it.next();
                    	if(card.side == 0) {
                    		if (card.x > desXSouth) {
                    			card.x -= stepXSouth;
                            }
                            else {
                            	card.x += stepXSouth;
                            } 
                            if(card.y > desYSouth) {
                            	card.y -= stepYSouth;
                        	}
                        	else {
                        		card.y += stepYSouth;
                        	}      	       
                            if(card.y + bufferedImageCard.getHeight() + 10>= getHeight()) {
                       //     	System.out.println("south");
                            	saveCard.add(card);
                            	desXSouth += 40;
            	                it.remove();
                            }
                    	}
                		if(card.side == 3) {
                			if (card.x > desXEast) {
                    			card.x -= stepXEast;
                            }
                            else {
                            	card.x += stepXEast;
                            } 
                            if(card.y < desYEast) {
                            	card.y += stepYEast;
                        	}
                        	else {
                        		card.y -= stepYEast;
                        	}
                            if(card.x + bufferedImageRotateCard.getWidth() + 10> getWidth()) {
                            	saveCard.add(card);       	
                            	desYEast -= 20;
            	                it.remove();
                            }
                		}
                		if(card.side == 2) {
                			if (card.x > desXNorth) {
                    			card.x -= stepXNorth;
                            }
                            else {
                            	card.x += stepXNorth;
                            } 
                            if(card.y < desYNorth) {
                            	card.y += stepYNorth;
                        	}
                        	else {
                        		card.y -= stepYNorth;
                        	}
                            if(card.y  ==  20) {
                            	
                            	saveCard.add(card);       	
                            	desXNorth -= 20;
            	                it.remove();
                            }
                		}
                		if(card.side == 1) {
                			if (card.x > desXWest) {
                    			card.x -= stepXWest;
                            }
                            else {
                            	card.x += stepXWest;
                            } 
                            if(card.y < desYWest) {
                            	card.y += stepYWest;
                        	}
                        	else {
                        		card.y -= stepYWest;
                        	}
                            if(card.x == 20) {
                        //    	System.out.println("Vao");
                            	saveCard.add(card);       	
                            	desYWest += 20;
            	                it.remove();
                            }
                		}
            		
            	
                
                /////
                
                repaint();

        }
           
        }
        
    });

    timer.start();
	
  
   
    
   
    timer1 = new Timer(20, new ActionListener() {
    	int flag = 0;
    //	int count = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
      //  	System.out.println("Count: " + count);
        	
        	//count++;
        	if (count < 52) {

        		MyCard card = new MyCard();
        		switch(count % 4) {
            		case 0: 
            		{
            			card.side = 0;
	            		card.setImage(bufferedImageCard);
	            		break;
            		}
            		case 1: 
            		{
            			card.side = 1;
                		card.setImage(bufferedImageRotateCard);
                		break;
            		}
            		case 2:
            		{
            			card.side = 2;
                		card.setImage(bufferedImageCard);
            			break;
            		}
            		case 3: 
            		{
            			card.side = 3;
                		card.setImage(bufferedImageRotateCard);
            			break;
            		}
        		}
        		arrayCard.add(card);
        		count++;
        		
        	}
        	if (saveCard.size() == 52)
    		{
    			flag = 1;
    			((Timer) e.getSource()).stop();
    		}
        }
        
    });
   
	timer1.start();
	
	return;
	
}
   

    public void showCard(MyUser mu) {
    	int i = 0;
    	for(i = 0; i < saveCard.size(); i++) {
			MyCard card = saveCard.get(i);
			
			JLabel temp = new JLabel();
			if(saveCard.get(i).side == 0) {
				saveCard.get(i).number = numSouth++;
				temp.setBounds(saveCard.get(i).x, saveCard.get(i).y, 90, 140);
				listLabelSouth.add(temp);
			}
			
			if(saveCard.get(i).side == 3) {
				saveCard.get(i).number = numEast++;
				temp.setBounds(saveCard.get(i).x, saveCard.get(i).y, 140, 90);
				listLabelEast.add(temp);
			}
			
			if(saveCard.get(i).side == 2) {
				saveCard.get(i).number = numNorth++;
				temp.setBounds(saveCard.get(i).x, saveCard.get(i).y, 90, 140);
				listLabelNorth.add(temp);
			}
			
			if(saveCard.get(i).side == 1) {
				saveCard.get(i).number = numWest++;
				temp.setBounds(saveCard.get(i).x, saveCard.get(i).y, 140, 90);
				listLabelWest.add(temp);
			}
			
			ml = new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
			//		System.out.println("Card number: " + card.number);
					if (status == 0)
					{
						if(card.status == false) 
						{
						
							if (listSwap.size() < 3)
							{
								listSwap.addElement(card.number);
								temp.setBounds(card.x, card.y - 20, 90, 140);
								saveCard.remove(card);
								card.status = true;
							}
								
							repaint();
							
							
						}
						else 
						{
							if (listSwap.contains(card.number))
							{
								
								temp.setBounds(card.x, card.y, 90, 140);
								listSwap.removeElement(card.number);
								card.status = false;
							}
							
							repaint();
						}
					}
					
					else 
					{
						if (play == 1)
						{
						//	playCard = card.number;
							choicecard = card.number;
							
							/*int i = 0;
							for(i = card.number; i < listLabelSouth.size() - 1; i++)
							{
								listLabelSouth.get(i).setIcon(listLabelSouth.get(i + 1).getIcon());
								
							}
							listLabelSouth.remove(choicecard);*/
							//setTurn(0);
							play = 0;
							
						}
						else
						{
							setText("Not your turn");
					//		choicecard = -1;
						}
					}
					
				}
			};
			
        	if(card.side == 0) {
        		temp.addMouseListener(ml);
        	}
        	
		}
    //	System.out.println("Size south : " + listLabelSouth.size());
		for(i = listLabelSouth.size() - 1; i >= 0; i--) {
			add(listLabelSouth.get(i));
		}
	//	System.out.println("Size east : " + listLabelEast.size());
		for(i = listLabelEast.size() - 1; i >= 0; i--) {
			add(listLabelEast.get(i));
		}
	//	System.out.println("Size north : " + listLabelNorth.size());
		for(i = listLabelNorth.size() - 1; i >= 0; i--) {
			add(listLabelNorth.get(i));
		}
	//		System.out.println("Size west : " + listLabelWest.size());
		for(i = listLabelWest.size() - 1; i >= 0; i--) {
			add(listLabelWest.get(i));
		}
    	i = 0;
    	for (MyCard temp : mu.getListCard())
    	{
    		String card = "card/"+temp.getCard() + "-" + temp.getSuit() + ".png";
    		listLabelSouth.get(i++).setIcon(new ImageIcon(card));
    	}
    	
    }


    public void removeCard(int pos) 
    {
		Icon temp = listLabelSouth.get(pos).getIcon();
		
		// set vi tri
		int width = getWidth();
    	int length = listLabelSouth.size() * 20;
    	
    	for(int i = 0; i < listLabelSouth.size(); i++) 
    	{
    		listLabelSouth.get(i).setBounds(getWidth() / 2 - length + i * 40 , listLabelSouth.get(i).getY(), 90, 140);
    	}
		
		
    	// set icon
		for(int i = pos; i < listLabelSouth.size(); i++)
		{
			if (i == (listLabelSouth.size() - 1))
			{
				listLabelSouth.get(i).setIcon(temp);
				// Animation danh user 0 danh bai
				animationPickCard(listLabelSouth.get(i), listLabelSouth.get(pos));
				//listLabelSouth.get(i).setBounds(getWidth() / 2 - 45 , getHeight() / 2 + 10, 90, 140);
			}
			else
			{
				listLabelSouth.get(i).setIcon(listLabelSouth.get(i + 1).getIcon());
			}
		}
		
    }
    
    public void animationPickCard(JLabel movecard, JLabel pickCard) 
    {
    	Timer pick = new Timer(0, new ActionListener() {
			int x = pickCard.getX();
			int y = pickCard.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > getWidth() / 2 - 45) {
						x-= 1;
                }
                else {
                	if(y != getHeight() / 2 + 10)
                		x += 1;
                } 
                if(y > getHeight() / 2 + 10) {
                		y -= 1;
            	}
            	else {
            		if(x != getWidth() / 2 - 45)
            			y += 1;
            	}      	       
                movecard.setBounds(x, y, 90, 140);
				if((x == getWidth() / 2 - 45) && (y == getHeight() / 2 + 10)) {
		//			System.out.println("dung");
					((Timer) e.getSource()).stop();
				}
				
			}
		});
    	pick.start();
    }
    
    public void removeLastSaveCard(int pos)
    {
    	switch(pos)
    	{
    	
    	case -3:
    	case 1:
    	{
    		
    		int count = 0;
    		for(int i = 0; i < saveCard.size(); i++ )
    		{
    			
    			if(saveCard.get(i).side == 1)
    			{
    				count++;
    				if(count == listLabelWest.size())
    				{
    					saveCard.remove(i);
    					repaint();
    				}
    			}
    		}
    		
//    		for (int i = saveCard.size() - 1; i >= 0; i--)
//    		{
//    			if (saveCard.get(i).side == 1)
//    			{
//    				saveCard.remove(i);
//    				break;
//    			}
//    				
//    		}
    		
    		break;
    	}
    	
    	case -2:
    	case 2:
    	{
    		
    		int count = 0;
    		for(int i = 0; i < saveCard.size(); i++ )
    		{
    			
    			if(saveCard.get(i).side == 2)
    			{
    				count++;
    				if(count == listLabelNorth.size())
    				{
    					saveCard.remove(i);
    					repaint();
    				}
    			}
    		}
    		
//    		for (int i = saveCard.size() - 1; i >= 0; i--)
//    		{
//    			if (saveCard.get(i).side == 2)
//    			{
//    				saveCard.remove(i);
//    				break;
//    			}
//    				
//    		}
    		
    		break;
    	}
    	
    	case -1:
    	case 3:
    	{
    		
    		int count = 0;
    		for(int i = 0; i < saveCard.size(); i++ )
    		{
    			
    			if(saveCard.get(i).side == 3)
    			{
    				count++;
    				if(count == listLabelEast.size())
    				{
    					saveCard.remove(i);
    					repaint();
    				}
    			}
    		}
    		
//    		for (int i = saveCard.size() - 1; i >= 0; i--)
//    		{
//    			if (saveCard.get(i).side == 3)
//    			{
//    				saveCard.remove(i);
//    				break;
//    			}
//    				
//    		}
    		
    		break;
    	}
    	
    	}
    }
    
    public void removeLastCard(int pos)
    {
    	
    	switch(pos)
    	{
    	case 0:
    	{
    		listLabelSouth.get(listLabelSouth.size() - 1).setVisible(false);
    		listLabelSouth.remove(listLabelSouth.size() - 1);
    		
    		break;
    	}
    	
    	case 1:
    	{
    		listLabelWest.get(listLabelWest.size() - 1).setVisible(false);
    		
    		int count = 0;
    		/*for(int i = 0; i < saveCard.size(); i++ )
    		{
    			
    			if(saveCard.get(i).side == 1)
    			{
    				count++;
    				if(count == listLabelWest.size())
    				{
    					saveCard.remove(i);
    					repaint();
    				}
    			}
    		}*/
    		listLabelWest.remove(listLabelWest.size() - 1);
    		break;
    	}
    	
    	case 2:
    	{
    		listLabelNorth.get(listLabelNorth.size() - 1).setVisible(false);
    		
    		/*int count = 0;
    		for(int i = 0; i < saveCard.size(); i++ )
    		{
    			
    			if(saveCard.get(i).side == 2)
    			{
    				count++;
    				if(count == listLabelNorth.size())
    				{
    					saveCard.remove(i);
    					repaint();
    				}
    			}
    		}*/
    		listLabelNorth.remove(listLabelNorth.size() - 1);
    		break;
    	}
    	
    	case 3:
    	{
    		listLabelEast.get(listLabelEast.size() - 1).setVisible(false);
    		
    		/*int count = 0;
    		for(int i = 0; i < saveCard.size(); i++ )
    		{
    			
    			if(saveCard.get(i).side == 3)
    			{
    				count++;
    				if(count == listLabelEast.size())
    				{
    					saveCard.remove(i);
    					repaint();
    				}
    			}
    		}*/
    		listLabelEast.remove(listLabelEast.size() - 1);
    		break;
    	}
    	
    	}
    	
    }
    
    public void animationPickCardOfOther(int side, JLabel pickCard)
    {
    	Timer pick = new Timer(0, new ActionListener() {
			int x = pickCard.getX();
			int y = pickCard.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				switch(side)
				{
					case 1:
					{
						if (x > getWidth() / 2 - 180) {
							x -= 1;
				        }
				        else {
				        	if(y != getHeight() / 2 - 70)
				        		x += 1;
				        } 
						
				        if(y > getHeight() / 2 - 70) {
				        		y -= 1;
				    	}
				    	else {
				    		if(x != getWidth() / 2 - 180)
				    			y += 1;
				    	}      	       
				        pickCard.setBounds(x, y, 90, 140);
						if((x == getWidth() / 2 - 180) && (y == getHeight() / 2 - 70)) {
			//				System.out.println("dung");
							((Timer) e.getSource()).stop();
						}
						break;
					}
					
					case 2:
					{
						if (x > getWidth() / 2 - 45) {
							x-= 1;
				        }
				        else {
				        	if(y != getHeight() / 2 - 160)
				        		x += 1;
				        } 
				        if(y > getHeight() / 2 - 160) {
				        		y -= 1;
				    	}
				    	else {
				    		if(x != getWidth() / 2 - 45)
				    			y += 1;
				    	}      	       
				        pickCard.setBounds(x, y, 90, 140);
						if((x == getWidth() / 2 - 45) && (y == getHeight() / 2 - 160)) {
				//			System.out.println("dung");
							((Timer) e.getSource()).stop();
						}
						break;
					}
					
					case 3:
					{
						if (x > getWidth() / 2 + 90) {
							x-= 1;
				        }
				        else {
				        	if(y != getHeight() / 2 - 70)
				        		x += 1;
				        } 
				        if(y > getHeight() / 2 - 70) {
				        		y -= 1;
				    	}
				    	else {
				    		if(x != getWidth() / 2 + 90)
				    			y += 1;
				    	}      	       
				        
				        pickCard.setBounds(x, y, 90, 140);
						if((x == getWidth() / 2 + 90) && (y == getHeight() / 2 - 70)) {
				//			System.out.println("dung");
							((Timer) e.getSource()).stop();
						}
						break;
					}
				}
			}
					
		});
    	pick.start();
    }
  
    public void moveCollectCardToSouth(JLabel label)
    {
    	Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > getWidth() / 2 - 45) {
					x-= 1;
	            }
	            else {
	            	if(y != getHeight())
	            		x += 1;
	            } 
	            if(y > getHeight()) {
	            		y -= 3;
	        	}
	        	else {
	        		if(x != getWidth() / 2 - 45)
	        			y += 3;
	        	}      	       
	            label.setBounds(x, y, 90, 140);
				if((x == getWidth() / 2 - 45) && (y == getHeight())) {
		//			System.out.println("dung");
					((Timer) e.getSource()).stop();
				}
			}
		});
    	time.start();
    }
    
    public void moveCollectCardToWest(JLabel label)
    {
    	Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > 0) {
					x-= 3;
	            }
	            else {
	            	if(y != getHeight() / 2 - 70)
	            		x += 3;
	            } 
	            if(y > getHeight() / 2 - 70) {
	            		y -= 1;
	        	}
	        	else {
	        		if(x != 0)
	        			y += 1;
	        	}      	       
	            label.setBounds(x, y, 90, 140);
				if((x == 0) && (y == getHeight() / 2 - 70)) {
		//			System.out.println("dung");
					((Timer) e.getSource()).stop();
				}
			}
		});
    	time.start();
    }
    
    public void moveCollectCardToNorth(JLabel label)
    {
    	Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > getWidth() / 2 - 45) {
					x-= 1;
	            }
	            else {
	            	if(y != 0)
	            		x += 1;
	            } 
	            if(y > 0) {
	            		y -= 3;
	        	}
	        	else {
	        		if(x != getWidth() / 2 - 45)
	        			y += 3;
	        	}      	       
	            label.setBounds(x, y, 90, 140);
				if((x == getWidth() / 2 - 45) && (y == 0)) {
			//		System.out.println("dung");
					((Timer) e.getSource()).stop();
				}
			}
		});
    	time.start();
    }
    
    public void moveCollectCardToEast(JLabel label)
    {
    	Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > getWidth()) {
					x-= 3;
	            }
	            else {
	            	if(y != getHeight() / 2 - 70)
	            		x += 3;
	            } 
	            if(y > getHeight() / 2 - 70) {
	            		y -= 1;
	        	}
	        	else {
	        		if(x != getWidth())
	        			y += 1;
	        	}      	       
	            label.setBounds(x, y, 90, 140);
				if((x == getWidth()) && (y == getHeight() / 2 - 70)) {
		//			System.out.println("dung");
					((Timer) e.getSource()).stop();
				}
			}
		});
    	time.start();
    }
    
    public void animationCollectCard(int side)
    {
    	switch(side)
    	{
	    	case 0:
	    	{
	    		moveCollectCardToSouth(listLabelSouth.get(listLabelSouth.size() - 1));
	    		moveCollectCardToSouth(listLabelWest.get(listLabelWest.size() - 1));
	    		moveCollectCardToSouth(listLabelNorth.get(listLabelNorth.size() - 1));
	    		moveCollectCardToSouth(listLabelEast.get(listLabelEast.size() - 1));
	    		break;
	    	}
	    	case -3:
	    	case 1:
	    	{
	    		
	    		moveCollectCardToWest(listLabelSouth.get(listLabelSouth.size() - 1));
	    		moveCollectCardToWest(listLabelWest.get(listLabelWest.size() - 1));
	    		moveCollectCardToWest(listLabelNorth.get(listLabelNorth.size() - 1));
	    		moveCollectCardToWest(listLabelEast.get(listLabelEast.size() - 1));
	    		break;
	    		
	    	}
	    	case -2:
	    	case 2:
	    	{
	    		moveCollectCardToNorth(listLabelSouth.get(listLabelSouth.size() - 1));
	    		moveCollectCardToNorth(listLabelWest.get(listLabelWest.size() - 1));
	    		moveCollectCardToNorth(listLabelNorth.get(listLabelNorth.size() - 1));
	    		moveCollectCardToNorth(listLabelEast.get(listLabelEast.size() - 1));
	    		break;
	    	}
	    	case -1:
	    	case 3:
	    	{
	    		moveCollectCardToEast(listLabelSouth.get(listLabelSouth.size() - 1));
	    		moveCollectCardToEast(listLabelWest.get(listLabelWest.size() - 1));
	    		moveCollectCardToEast(listLabelNorth.get(listLabelNorth.size() - 1));
	    		moveCollectCardToEast(listLabelEast.get(listLabelEast.size() - 1));
	    		break;
	    	}
    	}
    }
    
    public void removeOtherPlayer(int side, String fileicon)
    {
		switch (side)
    	{
        	/*case 0:
        	{
        		listLabelSouth.get(listLabelSouth.size() - 1).setBounds(getWidth() / 2 - 45, getHeight() / 2 + 160, 90, 140);
        		listLabelSouth.get(listLabelSouth.size() - 1).setIcon(new ImageIcon(fileicon));
        		animationPickCardOfOther(0, listLabelSouth.get(listLabelSouth.size() - 1));
        		break;
        	}*/
        	
        	case -3:
        	case 1:
        	{
        		
        		listLabelWest.get(listLabelWest.size() - 1).setIcon(new ImageIcon(fileicon));
        		animationPickCardOfOther(1, listLabelWest.get(listLabelWest.size() - 1));
        		
        		break;
        	}
        	
        	case -2:
        	case 2:
        	{
        		
        
        		listLabelNorth.get(listLabelNorth.size() - 1).setIcon(new ImageIcon(fileicon));
        		animationPickCardOfOther(2, listLabelNorth.get(listLabelNorth.size() - 1));
        		break;
        	}
        	
        	case -1:
        	case 3:
        	{
        	
        		listLabelEast.get(listLabelEast.size() - 1).setIcon(new ImageIcon(fileicon));
        		animationPickCardOfOther(3, listLabelEast.get(listLabelEast.size() - 1));
        		break;
        	}
    	}
    	
    }
    
    public void removeOtherBot(int side, String fileicon)
    {
    	switch (side)
    	{
    		
    	case 1:
    	{
    	
    		listLabelWest.get(listLabelWest.size() - 1).setIcon(new ImageIcon(fileicon));
    		animationPickCardOfOther(1, listLabelWest.get(listLabelWest.size() - 1));
    		
    		break;
    	}
    	
    	case 2:
    	{
    		
    	
    		listLabelNorth.get(listLabelNorth.size() - 1).setIcon(new ImageIcon(fileicon));
    		animationPickCardOfOther(2, listLabelNorth.get(listLabelNorth.size() - 1));
    		
    		break;
    	}
    	
    	
    	case 3:
    	{
    	
    		listLabelEast.get(listLabelEast.size() - 1).setIcon(new ImageIcon(fileicon));
    		animationPickCardOfOther(3, listLabelEast.get(listLabelEast.size() - 1));
    		
    		
    		break;
    	}
    	}
    }
    
    
    public void showCardSwap(MyUser mu)
    {
    	status = 1;
    	for(int i = listLabelSouth.size() - 1; i >= 0; i--) {
    		MyCard card = mu.getListCard().get(i);
    		String cardString = "card/" + card.getCard() + "-" + card.getSuit() + ".png";
			listLabelSouth.get(i).setIcon(new ImageIcon(cardString));
			listLabelSouth.get(i).setBounds(listLabelSouth.get(i).getX(), 490, 90, 140);
			JLabel temp = listLabelSouth.get(i);
			
		}
    	
    
	}
	
    
    public void removeBackCard()
    {
    	for(int i = 0; i < saveCard.size(); i++) {
			if(saveCard.get(i).side == 0) {
				saveCard.remove(i);
			}
		}
    }
    
    
    public Integer swapCard(MyUser mu)
    {
    	int width = getWidth();
    	int height = getHeight();
    	
    	
    	pass.setBounds(width / 2 - 33, height / 2 - 25, 66, 50);
    	
    	pass.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (listSwap.size() == 3)
				{
					
					flag = 1;
					for(int i = 0; i < saveCard.size(); i++) {
						if(saveCard.get(i).side == 0) {
							saveCard.remove(i);
						}
					}
					
					removeButton(1);
					
				}

			}
		});
    	
    	pass.setVisible(true);
    	repaint();
    	
    	return flag;
		
    }

    
    public void animationSwap(MyUser mu)
    {
    	int turn = mu.getTurn();
		switch(turn)
		{
		case 1:
		{
			animationPassSouth2West();
			animationPassEast2South();
			animationPassNorth2East();
			animationPassWest2North();
			break;
		}
		case 2:
		{
			animationPassSouthToEast();
			animationPassEastToNorth();
			animationPassNorthToWest();
			animationPassWestToSouth();
			break;						
		}
		case 3:
		{
			animationPassSouthToNorth();
			animationPassNorthToSouth();
			animationPassEastToWest();
			animationPassWestToEast();
			break;
		}
		}
    }
    
    
    public void removeButton(int flag)
    {
    	if (flag == 1)
    	{
    		pass.setVisible(false);
    		status = 0;
    		repaint();
    	}
    	else
    		return;
    		
    }
    
    
    public Vector<Integer> getListSwap()
    {
    	return listSwap;
    }


    public Integer chooseCard(MyUser mu)
    {
    	play = 1;
    	
    	while (choicecard == -1)
    	{
    		try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return choicecard;
    }

    
    public void setChoose()
    {
    	choicecard = - 1;
    }
    
    
    public void setStatus(int flag)
    {
    	this.status = flag;
    }
    
    
    public void initGame(int turn)
    {
    	arrayCard.clear();
    	saveCard.clear();
    	listLabelEast.clear();
    	listLabelNorth.clear();
    	listLabelSouth.clear();
    	listLabelWest.clear();
    	listSwap.clear();
    	
    	desXSouth = 200;
        desYSouth = 480;
        desXEast = 800;
        desYEast = 380;
        desXNorth = 550;
        desYNorth = 20;
        desXWest = 20;
        desYWest = 140;
        stepXSouth = 20;
        stepYSouth = 10;
        stepYEast = 10;
        stepXEast = 20;
        stepYNorth = 10;
        stepXNorth = 10;
        stepYWest = 10;
        stepXWest = 20;
        

    	numSouth = 0;
    	numEast = 0;
    	numNorth = 0;
    	numWest = 0;
        number = 0;
        count = 0;
        flag = 0;
        choicecard = -1;
        play = 0;
        this.turn = turn;
        if (turn % 4 != 0)
        	status = 0;
        else
        	status = 1;
        
        point0.setText("0");
        point1.setText("0");
        point2.setText("0");
        point3.setText("0");
        
        send52Cards();
        
        
    }
    
  
    public void setText(String text)
    {
    	showMessage.setAlignmentX(CENTER_ALIGNMENT);
    	showMessage.setFont(new Font("Consolas", Font.PLAIN, 16));
    	showMessage.setLineWrap(true);
    	showMessage.setWrapStyleWord(true);
    	showMessage.setText(text);
    	showMessage.setEditable(false);
    	showMessage.setBackground(Color.getHSBColor(140, 200, 150));
    	showMessage.setBounds(20, getHeight() - 100, 180, 60);
		showMessage.setForeground(Color.RED);
		showMessage.setBorder(BorderFactory.createEtchedBorder());
    	
    	repaint();
    }
    
    
    public void showUser(int pos, MyUser mu, Vector<String> saveName)
    {
    	
    	int posUser = 0;
    	while (posUser < 4)
    	{
    		int user = ((posUser + pos) % 4);
    		
    		if (posUser == 0)
    		{
    			
    			user0.setText(mu.getName());
    			user0.setBackground(Color.getHSBColor(140, 200, 150));
    			user0.setForeground(Color.WHITE);
    			user0.setIcon(new ImageIcon("image/user" + ((posUser + pos) % 4 + 1) + ".png"));
    			user0.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    			user0.setBounds(20, 500, 130, 50);
    			
    			point0.setBounds(user0.getWidth() + user0.getX(), 500, 50, 50);
    			point0.setIcon(new ImageIcon("image/point.png"));
    			point0.setText("0");
    			point0.setHorizontalTextPosition(JLabel.CENTER);
    	//		point0.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    			point0.setFont(new Font("Consolas", Font.PLAIN, 16));
    			point0.setForeground(Color.WHITE);
    			
    			repaint();
    		}
    		if (posUser == 1)
    		{
    			
    			user1.setText(saveName.get(user));
    			user1.setBackground(Color.getHSBColor(140, 200, 150));
    			user1.setForeground(Color.WHITE);
    			user1.setIcon(new ImageIcon("image/user" + ((posUser + pos) % 4 + 1) + ".png"));
    			user1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    			user1.setBounds(20, 45, 130, 50);
    			
    			point1.setBounds(user1.getWidth() + user1.getX(), 45, 50, 50);
    			point1.setIcon(new ImageIcon("image/point.png"));
    			point1.setText("0");
    			point1.setHorizontalTextPosition(JLabel.CENTER);
    	//		point1.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    			point1.setFont(new Font("Consolas", Font.PLAIN, 16));
    			point1.setForeground(Color.WHITE);
    			
    			repaint();
    		}
    		if (posUser == 2)
    		{
    			
    			user2.setText(saveName.get(user));
    			user2.setBackground(Color.getHSBColor(140, 200, 150));
    			user2.setForeground(Color.WHITE);
    			user2.setIcon(new ImageIcon("image/user" + ((posUser + pos) % 4 + 1) + ".png"));
    			user2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    			user2.setBounds(800, 45, 130, 50);
    			
    			point2.setBounds(user2.getWidth() + user2.getX(), 45, 50, 50);
    			point2.setIcon(new ImageIcon("image/point.png"));
    			point2.setText("0");
    			point2.setHorizontalTextPosition(JLabel.CENTER);
    	//		point2.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    			point2.setFont(new Font("Consolas", Font.PLAIN, 16));
    			point2.setForeground(Color.WHITE);
    			
    			repaint();
    		}
    		if (posUser == 3)
    		{
    			
    			user3.setText(saveName.get(user));
    			user3.setBackground(Color.getHSBColor(140, 200, 150));
    			user3.setForeground(Color.WHITE);
    			user3.setIcon(new ImageIcon("image/user" + ((posUser + pos) % 4 + 1) + ".png"));
    			user3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    			user3.setBounds(800, 500, 130, 50);
    			
    			point3.setBounds(user3.getWidth() + user3.getX(), 500, 50, 50);
    			point3.setIcon(new ImageIcon("image/point.png"));
    			point3.setText("0");
    			point3.setHorizontalTextPosition(JLabel.CENTER);
    	//		point3.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    			point3.setFont(new Font("Consolas", Font.PLAIN, 16));
    			point3.setForeground(Color.WHITE);
    			
    			repaint();
    		}
    		
    		
    		
    		posUser++;
    	}
    }


    public void showUserAI(int pos, Vector<MyUser> mu)
    {
    	int posUser = 0;
    	while (posUser < 4)
    	{
    		String text = "BOT " + ((posUser + pos) % 4);
    		
    		if (posUser == 0)
    		{
    			
    			user0.setText(mu.get(0).getName());
    			user0.setBackground(Color.getHSBColor(140, 200, 150));
    			user0.setForeground(Color.WHITE);
    			user0.setIcon(mu.get(0).getIcon());
    			user0.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    			user0.setBounds(20, 500, 130, 50);
    			user0.setForeground(Color.WHITE);
    			
    			point0.setBounds(user0.getWidth() + user0.getX(), 500, 50, 50);
    			point0.setIcon(new ImageIcon("image/point.png"));
    			point0.setText("0");
    			point0.setHorizontalTextPosition(JLabel.CENTER);
    	//		point0.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    			point0.setFont(new Font("Consolas", Font.PLAIN, 16));
    			point0.setForeground(Color.WHITE);
    			
    			repaint();
    		}
    		if (posUser == 1)
    		{
    			
    			user1.setText(text);
    			user1.setBackground(Color.getHSBColor(140, 200, 150));
    			user1.setForeground(Color.WHITE);
    			user1.setIcon(mu.get(1).getIcon());
    			user1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    			user1.setBounds(20, 45, 130, 50);
    			user1.setForeground(Color.WHITE);
    			
    			point1.setBounds(user1.getWidth() + user1.getX(), 45, 50, 50);
    			point1.setIcon(new ImageIcon("image/point.png"));
    			point1.setText("0");
    			point1.setHorizontalTextPosition(JLabel.CENTER);
    	//		point1.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    			point1.setFont(new Font("Consolas", Font.PLAIN, 16));
    			point1.setForeground(Color.WHITE);
    			
    			repaint();
    		}
    		if (posUser == 2)
    		{
    			
    			user2.setText(text);
    			user2.setBackground(Color.getHSBColor(140, 200, 150));
    			user2.setForeground(Color.WHITE);
    			user2.setIcon(mu.get(2).getIcon());
    			user2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    			user2.setBounds(800, 45, 130, 50);
    			user2.setForeground(Color.WHITE);
    			
    			point2.setBounds(user2.getWidth() + user2.getX(), 45, 50, 50);
    			point2.setIcon(new ImageIcon("image/point.png"));
    			point2.setText("0");
    			point2.setHorizontalTextPosition(JLabel.CENTER);
    	//		point2.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    			point2.setFont(new Font("Consolas", Font.PLAIN, 16));
    			point2.setForeground(Color.WHITE);
    			
    			repaint();
    		}
    		if (posUser == 3)
    		{
    			
    			user3.setText(text);
    			user3.setBackground(Color.getHSBColor(140, 200, 150));
    			user3.setForeground(Color.WHITE);
    			user3.setIcon(mu.get(3).getIcon());
    			user3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    			user3.setBounds(800, 500, 130, 50);
    			user3.setForeground(Color.WHITE);
    			
    			point3.setBounds(user3.getWidth() + user3.getX(), 500, 50, 50);
    			point3.setIcon(new ImageIcon("image/point.png"));
    			point3.setText("0");
    			point3.setHorizontalTextPosition(JLabel.CENTER);
    		//	point3.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    			point3.setFont(new Font("Consolas", Font.PLAIN, 16));
    			point3.setForeground(Color.WHITE);
    			
    			repaint();
    		}
    		
    		
    		
    		posUser++;
    	}
    }

    public void setUserName(Vector<String> saveName)
    {
    	this.saveName = saveName;
    }
    
    public Vector<String> getUserName()
    {
    	return this.saveName;
    }
     
    
    public void savePoint(Vector<Vector<Integer>> savePointTotal)
    {
    	this.savePointTotal = savePointTotal;
    }
    
    
    public void updatePoint(int pos, int point)
    {
    	switch (pos)
    	{
    	
    	case 0:
    	{
    		point0.setText("" + (point + Integer.parseInt(point0.getText())));
    		break;
    	}
    		
    	case -3:
    	case 1:
    	{
    		point1.setText("" + (point + Integer.parseInt(point1.getText())));
    		break;
    	}
  
    	case -2:
    	case 2:
    	{
    		point2.setText("" + (point + Integer.parseInt(point2.getText())));
    		break;
    	}
    		
    	case -1:
    	case 3:
    	{
    		point3.setText("" + (point + Integer.parseInt(point3.getText())));
    		break;
    	}
    	
    	}
    	
    	repaint();
    }

	/////////left
	public Timer movePassCardToSouth(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > getWidth() / 2 - 45) {
					x-= 24;
	            }
	            else {
	            	if(y != getHeight() - 130)
	            		x += 24;
	            } 
	            if(y > getHeight() - 130) {
	            		y -= 12;
	        	}
	        	else {
	        		if(x != 446 || x != 453)
	        			y += 12;
	        	}      	       
	//            System.out.println("x: " + x + ", y: " + y);
	            label.setBounds(x, y, 163, 140);
				if((x == 446 || x == 453) && (y + bufferedImageCard.getHeight() + 10>= getHeight())) {
					((Timer) e.getSource()).stop();
					remove(label);
				}
			}
		});
		return time;
	}
	
	public Timer movePassCardToWest(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > 0) {
					x-= 24;
	            }
	            else {
	            	if(y != getHeight() / 2 - 70)
	            		x += 24;
	            } 
	            if(y > getHeight() / 2 - 70) {
	            		y -= 12;
	        	}
	        	else {
	        		if(x != 0)
	        			y += 12;
	        	}      	       
	      //      System.out.println("x: " + x + ", y: " + y);
	            label.setBounds(x, y, 160, 140);
				if((x <= 15)) {
					remove(label);
					((Timer) e.getSource()).stop();
				}
			}
		});
		return time;
	}
	
	public Timer movePassCardToNorth(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > getWidth() / 2 - 45) {
					x-= 24;
	            }
	            else {
	            	if(y != 0)
	            		x += 24;
	            } 
	            if(y > 0) {
	            		y -= 12;
	        	}
	        	else {
	        		if(x != getWidth() / 2 - 45)
	        			y += 12;
	        	}      	       
	 //           System.out.println("x: " + x + ", y: " + y);
	            label.setBounds(x, y, 170, 140);
				if(y < 0) {
					remove(label);
					((Timer) e.getSource()).stop();
				}
			}
		});
		return time;
	}
	
	public Timer movePassCardToEast(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > getWidth() - 20) {
					x-= 24;
	            }
	            else {
	            	if(y != getHeight() / 2 - 70)
	            		x += 24;
	            } 
	            if(y > getHeight() / 2 - 70) {
	            		y -= 12;
	        	}
	        	else {
	        		if(x != getWidth() - 20)
	        			y += 12;
	        	}      	       
	    //        System.out.println("x: " + x + ", y: " + y);
	            label.setBounds(x, y, 170, 140);
				if((x + bufferedImageRotateCard.getWidth() + 10> getWidth()) && (y == getHeight() / 2 - 70)) {
					remove(label);
					((Timer) e.getSource()).stop();
				}
			}
		});
		return time;
	}
	
	public void animationPassEast2South()
	{
		int x = getWidth() - 130;
		int y = getHeight() / 2 - 70; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = movePassCardToSouth(card);
		time.start();
	}
	
	public void animationPassSouth2West()
	{
		int x = getWidth() / 2 - 45;
		int y = getHeight() - 170; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = movePassCardToWest(card);
		time.start();
	}
	    
	public void animationPassWest2North()
	{
		int x = 80;
		int y = getHeight() / 2 - 70; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = movePassCardToNorth(card);
		time.start();
	}
	  
	public void animationPassNorth2East()
	{
		int x = getWidth() / 2 - 45;
		int y = 20; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = movePassCardToEast(card);
		time.start();
	
	}
	
	
	/////////left
	
	
	/////////right
	public Timer moveSouthToEast(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x < getWidth()) {
					x+= 24;
	            }
	            if(y > getHeight() / 2 - 70) {
	            		y -= 12;
	        	}
	        	else {
	        			y += 12;
	        	}      	       
	      //      System.out.println("right x: " + x + ", y: " + y);
	            label.setBounds(x, y, 170, 140);
				if((x >= getWidth())) {
					remove(label);
					((Timer) e.getSource()).stop();
				}
			}
		});
		return time;
	}
	
	public Timer moveEastToNorth(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > getWidth() / 2 - 45) {
					x-= 24;
	            }
	            else {
	            	if(y != 0)
	            		x += 24;
	            } 
	            if(y > 0) {
	            		y -= 12;
	        	}
	        	else {
	        		if(x != getWidth() / 2 - 45)
	        			y += 12;
	        	}      	       
	   //         System.out.println("right x: " + x + ", y: " + y);
	            label.setBounds(x, y, 170, 140);
				if(y <= 0) {
					remove(label);
					((Timer) e.getSource()).stop();
				}
			}
		});
		return time;
	}
	
	public Timer moveNorthToWest(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > 0) {
					x-= 24;
	            }
	            else {
	            	if(y != getHeight() / 2 - 70)
	            		x += 24;
	            } 
	            if(y > getHeight() / 2 - 70) {
	            		y -= 12;
	        	}
	        	else {
	        		if(x != 0)
	        			y += 12;
	        	}      	       
	     //       System.out.println("right x: " + x + ", y: " + y);
	            label.setBounds(x, y, 160, 140);
				if((x <= 15)) {
					remove(label);
					((Timer) e.getSource()).stop();
				}
			}
		});
		return time;
	}
	
	public Timer moveWestToSouth(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x < getWidth() / 2 - 45) {
					x += 24;
	            }
	
	            if(y < getHeight()) {
	            		y += 12;
	        	}
	
	   //         System.out.println("right x: " + x + ", y: " + y);
	            label.setBounds(x, y, 163, 140);
				if(y >= getHeight()) {
					((Timer) e.getSource()).stop();
					remove(label);
				}
			}
		});
		return time;
	}
	
	public void animationPassSouthToEast()
	{
		int x = getWidth() / 2 - 45;
		int y = getHeight() - 170; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = moveSouthToEast(card);
		time.start();
	}
	
	public void animationPassEastToNorth()
	{
		int x = getWidth() - 130;
		int y = getHeight() / 2 - 70; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = moveEastToNorth(card);
		time.start();
	}
	
	public void animationPassNorthToWest()
	{
		int x = getWidth() / 2 - 45;
		int y = 20; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = moveNorthToWest(card);
		time.start();
	}
	
	public void animationPassWestToSouth()
	{
		int x = 80;
		int y = getHeight() / 2 - 70; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = moveWestToSouth(card);
		time.start();
	}
	/////////right
	
	
	/////////across
	
	
	public Timer moveSouthToNorth(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	            if(y > 20)
	            	y -= 20;
	   //         System.out.println("across x: " + x + ", y: " + y);
	            label.setBounds(x, y, 170, 140);
				if(y <= 20) {
					remove(label);
					((Timer) e.getSource()).stop();
				}
			}
		});
		return time;
	}
	
	public Timer moveNouthToSorth(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(y < getHeight())
	            	y += 20;
	   //         System.out.println("across x: " + x + ", y: " + y);
	            label.setBounds(x, y, 163, 140);
				if((y + bufferedImageCard.getHeight() + 10>= getHeight())) {
					((Timer) e.getSource()).stop();
					remove(label);
				}
			}
		});
		return time;
	}
	
	public Timer moveEastToWest(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x > 160) {
					x-= 20;
	            }
	  //          System.out.println("across x: " + x + ", y: " + y);
	            label.setBounds(x, y, 160, 140);
				if((x <= 160)) {
					remove(label);
					((Timer) e.getSource()).stop();
				}
			}
		});
		return time;
	}
	
	public Timer moveWestToEast(JLabel label)
	{
		Timer time = new Timer(0, new ActionListener() {
			int x = label.getX();
			int y = label.getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (x < getWidth() - 140) {
					x+= 20;
	            }
	       
	   //         System.out.println("across x: " + x + ", y: " + y);
	            label.setBounds(x, y, 170, 140);
				if(x >= getWidth() - 140) {
					remove(label);
					((Timer) e.getSource()).stop();
				}
			}
		});
		return time;
	}
	
	public void animationPassSouthToNorth()
	{
		int x = getWidth() / 2 - 45;
		int y = getHeight() - 170; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = moveSouthToNorth(card);
		time.start();
	}
	
	public void animationPassNorthToSouth()
	{
		int x = getWidth() / 2 - 45;
		int y = 20; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = moveNouthToSorth(card);
		time.start();
	}
	
	public void animationPassEastToWest()
	{
		int x = getWidth() - 130;
		int y = getHeight() / 2 - 70; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = moveEastToWest(card);
		time.start();
	}
	
	public void animationPassWestToEast()
	{
		int x = 80;
		int y = getHeight() / 2 - 70; 
		JLabel card = new JLabel();
		card.setIcon(new ImageIcon("image/3que.png"));
		card.setBounds(x, y, 163, 140);
		add(card);
		Timer time = moveWestToEast(card);
		time.start();
	}
	
	/////////across
	
	public void showRule()
	{
		JFrame showRule = new JFrame("Rule");
		showRule.setPreferredSize(new Dimension(800, 500));
		showRule.setResizable(false);
		ImageIcon img = new ImageIcon("image/icons8-Cards-128.png");
		showRule.setIconImage(img.getImage());
		
		
		JTextArea showHint = new JTextArea();
		showHint.setEditable(false);
		String res = "";
		res = res + "1. Rule 1.\n";
		res = res + "The objective of Hearts is to get as few points as possible. Each heart gives one penalty point. There is also one special card, the Queen of spades, which gives 13 penalty points. .\n";
		res = res + "\n";
		
		res = res + "2.Rule 2.\n";
		res = res + "When the game starts you select 3 cards to pass to one of your opponents. Typically you want to pass your three worst cards to get rid of them. Which opponent you pass to varies, you start by passing to the opponent on your left, then in the next game you pass to the opponent on your right, third game you pass across the table and in the fourth game there is no card passing. \n";
		res = res + "\n";
		
		res = res + "3. Rule 3.\n";
		res = res + "Each turn starts with one player playing a single card, also called leading. The suit of that card determines the suit of the trick. The other players then play one card each. If they have a card in the same suit as the first card then they must play that. If they don't then they can play one of their other cards.\n";
		res = res + "Once four cards have been played, the player who played the highest ranking card in the original suit takes the trick, i.e. he takes the four cards on the table and he then starts the next turn. Any penalty cards in the trick (hearts or queen of spades) are added to the players penalty score. So you want to avoid taking any tricks that have hearts or the queen of spades.\n";
		res = res + "\n";
		
		res = res + "4. Rule 4.\n";
		res = res + "The player who has the two of clubs at the start of the game leads in the first hand, and he MUST lead with the two of clubs. \n";
		res = res + "\n";
		
		res = res + "5. Rule 5.\n";
		res = res + "You cannot lead a trick with hearts, until hearts has been broken (played on another suit). So if it is your turn to lead and no heart has been played yet then you may not select a heart as the card to play first. In some variations of the game you can't play the queen of spades until hearts has been broken as well, but in this version you can always play the queen of spades and she doesn't break hearts.\n";
		res = res + "\n";
		
		res = res + "6. Rule 6.\n";
		res = res + "Once all cards have been played the penalty points are counted and the player with the fewest points wins that hand. When one or more players reach 100 points or more then the entire game is finished, and the player with the least points win. If points are over 100 and there are two or more equal with the least points then play continues until there's only one winner.\n";
		res = res + "\n";
		
		res = res + "7. Rule 7.\n";
		res = res + "In the very first round you may never play a heart or the queen of spades, not even if you don't have any card in the suit of the lead card. \n";
		res = res + "\n";
		
		res = res + "8. Rule 8.\n";
		res = res + "Shooting the Moon! Generally it's bad to get penalty cards, but there is one extra twist! If you get ALL the penalty cards (13 hearts + Queen of spades) then you get 0 points and the other 3 players get 26 points each! This is called Shooting the Moon. Trying this can be a really risky move though, since if another player gets just one of the hearts you'll end up with a lot of points... \n";
		res = res + "\n";
		
    	
		
		showHint.setText(res);
		showHint.setWrapStyleWord(true);
		showHint.setLineWrap(true);
		showHint.setFont(new Font("Arial", Font.BOLD, 14));
		
		JScrollPane jsc = new JScrollPane(showHint);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		showRule.setLocation(dim.width / 2 - 400, dim.height / 2 - 250);
		showRule.add(jsc);
		showRule.pack();
		showRule.setVisible(true);
		
//		Timer tm = new Timer(1000, new ActionListener() {
//			
//			int count = 0;
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				count++;
//				if (count == 20)
//				{
//					showRule.setVisible(false);
//					showRule.dispose();
//					((Timer)e.getSource()).stop();
//				}
//			}
//		});
//		tm.start();
	}

	public void aboutCode()
	{
		JFrame about = new JFrame("About");
		about.setPreferredSize(new Dimension(400, 300));
		ImageIcon img = new ImageIcon("image/icons8-Cards-128.png");
		about.setIconImage(img.getImage());
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		about.setLocation(dim.width / 2 - 200, dim.height /2 - 150);
		
		
		JTextArea text = new JTextArea();
		String res = "PROJECT INFOMATION\n\n";
		res = res + "PROJECT NAME : HEARTS GAME\n";
		res = res + "CODER : \n";
		res = res + "\t- 1312423 - Phan Tan Phat.\n";
		res = res + "\t- 1312426 - Danh Hong Phen.\n\n";
		res = res + "VERSION : 1.0 BETA\n\n";
		res = res + "*** Have Fun ***";
		
		text.setEditable(false);
		text.setFont(new Font("Consolas", Font.PLAIN, 16));
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setText(res);
		
		about.add(text);
		about.pack();
		about.setVisible(true);
		
		Timer tm = new Timer(1000, new ActionListener() {
			int count = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				count++;
				
				if (count == 15)
				{
					about.setVisible(false);
					about.dispose();
					
					((Timer)e.getSource()).stop();
				}
			}
		});
		tm.start();
	}

	public void endTurn(Vector<Vector<Integer>> savePointTotal)
    {
   
    	this.savePointTotal = savePointTotal;
    	// New board score
    	JPanel mainPanel = new JPanel();
    	
    	Vector<String> listHeader = new Vector<>();
    	for (int i = 0; i < 4; i++)
    	{
    		if (i == 0)
    		{
    			listHeader.add("Round");
    		}
    		listHeader.addElement(saveName.get(i));
    	}
    	
    	Vector<Vector<String>> listPoint = new Vector<>();
    	
    	JTable jtable = new JTable(listPoint, listHeader);
    	jtable.setEnabled(false);
    
    	
    	jtable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) 
			{
				// TODO Auto-generated method stub
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	        c.setBackground(row % 2 == 0 ? Color.getHSBColor((float) 0.3, (float) 0.5, 1) : Color.WHITE);
    	        return c;
			}
    	
    	});
    	
    	jtable.getTableHeader().setBackground(Color.WHITE);
    	jtable.getTableHeader().setFont(new Font( "Consolas" , Font.BOLD, 12 ));
    	jtable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	
    	for (int i = 1; i < jtable.getColumnCount(); i++)
    	{
    		jtable.getColumnModel().getColumn(i).setPreferredWidth(50);
    	}
    	
    	int sum0 = 0;
    	int sum1 = 0;
    	int sum2 = 0;
    	int sum3 = 0;
    	for (int i = 0; i < savePointTotal.size(); i++)
    	{
    		Vector<String> turn = new Vector<>();
    		turn.addElement("" + (i + 1));
    		for (int j = 0; j < savePointTotal.get(i).size(); j++)
    		{
    			turn.add("" + savePointTotal.get(i).get(j));
    			
    			switch(j)
    			{
    			case 0:
    				sum0 += savePointTotal.get(i).get(j);
    				break;
    			case 1:
    				sum1 += savePointTotal.get(i).get(j);
    				break;
    			case 2:
    				sum2 += savePointTotal.get(i).get(j);
    				break;
    			case 3:
    				sum3 += savePointTotal.get(i).get(j);
    				break;
    			}
    		}
    		listPoint.add(turn);
    	}
    	
    	Vector<String> total = new Vector<>();
    	total.add("Total");
    	total.addElement("" + sum0);
    	total.addElement("" + sum1);
    	total.addElement("" + sum2);
    	total.addElement("" + sum3);
    	
    	listPoint.addElement(total);
    	
    	JScrollPane jsc = new JScrollPane(jtable);
    	jsc.setPreferredSize(new Dimension(500, 300));
    	mainPanel.add(jsc);
    	
    	
    	jtable.revalidate();
    	//JOptionPane.showConfirmDialog(null, mainPanel, "Score board", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
    	
    	JFrame showPoint = new JFrame("ScoreBoard");
    	ImageIcon img = new ImageIcon("image/icons8-Cards-128.png");
    	showPoint.setIconImage(img.getImage());
    	showPoint.setPreferredSize(new Dimension(500, 300));
    	Dimension tool = Toolkit.getDefaultToolkit().getScreenSize();
    	
    	showPoint.setLocation(tool.width /2 - 250, tool.height / 2 - 150);
    	showPoint.setResizable(false);
    	showPoint.add(mainPanel);
    	
    	showPoint.setVisible(true);
    	showPoint.pack();
    	
    	
    	Timer rep = new Timer(1000, new ActionListener() {
			
    		int count = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				count++;
				if (count == 15)
				{
					
					showPoint.setVisible(false);
					showPoint.dispose();
					
					((Timer)e.getSource()).stop();
				}
			}
		});
    	rep.start();
    }
}

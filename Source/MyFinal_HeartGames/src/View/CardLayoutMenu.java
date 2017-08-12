package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.URLDataSource;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

import Data.MyCard;

public class CardLayoutMenu extends JPanel implements ItemListener {
	private BufferedImage image;
	JPanel cards; //a panel that uses CardLayout
    final static String HOSTPANEL = "Init as host";
    final static String CONNECTHOSTPANEL = "Connect to host";
    final static String BOTPANEL = "Play with bot";
    private JPanel cardHost;
    private JPanel cardConnectHost;
    private JPanel cardBot;
    private JTextArea namehostTextArea;
    private JTextArea porthostTextArea;
    private JTextArea nameuserTextArea;
    private JTextArea iphostTextArea;
    private JTextArea portuserTextArea;
    private JTextArea namebotTextArea;
    private JTextArea pointHostTextArea;
    private JTextArea pointBotTextArea;
    private JButton playButton;
    
    ////////////////// data send
    private int point = 0;
    private int mode = -1;
    private String name = "";
    private int port = -1;
    private String ip = "";
    public String getIP()
    {
    	return this.ip;
    }
    public int getMode()
    {
    	return this.mode;
    }
    
    public String getName()
    {
    	return this.name;
    }
    
    public int getPort()
    {
    	return this.port;
    }
    public int getPoint()
    {
    	return this.point;
    }
    
    
    
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
	}
	public void addComponentToPane(Container pane) {
		
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout4
        //comboBoxPane.setBackground(Color.cyan);
        
        
        String comboBoxItems[] = { HOSTPANEL, CONNECTHOSTPANEL, BOTPANEL};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setBackground(Color.white);
        
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        
        
        Border border = BorderFactory.createLineBorder(Color.BLUE);
        
        //Create the "host".
        cardHost = new JPanel();
        cardHost.setLayout(new BoxLayout(cardHost, BoxLayout.Y_AXIS));
        
        
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel nameLabel = new JLabel("Name: ");
        namehostTextArea = new JTextArea("", 2, 15);
        namehostTextArea.setBorder(border);
        //namehostTextArea.setFont(new Font("Verdana", Font.BOLD, 12));
        namehostTextArea.setUI(new HintTextFieldUI("  Input Hostname", true));
        namePanel.add(nameLabel);
        namePanel.add(namehostTextArea);
        

        JPanel portPanel = new JPanel();
        portPanel.setLayout(new FlowLayout());      
        JLabel portLabel = new JLabel("Port:    ");
        porthostTextArea = new JTextArea("", 2, 15);
        porthostTextArea.setBorder(border);
        //porthostTextArea.setFont(new Font("Verdana", Font.BOLD, 12));
        porthostTextArea.setUI(new HintTextFieldUI("  Input Port", true));
        portPanel.add(portLabel);
        portPanel.add(porthostTextArea);
        
        
        JPanel pointPanel =  new JPanel();
        pointPanel.setLayout(new FlowLayout());
        JLabel pointLabel = new JLabel("Point:  ");
        pointHostTextArea = new JTextArea("", 2, 15);
        pointHostTextArea.setBorder(border);
        pointHostTextArea.setUI(new HintTextFieldUI("  Input Point", true));
        pointPanel.add(pointLabel);
        pointPanel.add(pointHostTextArea);
        
        cardHost.add(namePanel);
        cardHost.add(portPanel);
        cardHost.add(pointPanel);
        
        
        /////////////Connect To Host////////////////
        cardConnectHost = new JPanel();
        cardConnectHost.setLayout(new BoxLayout(cardConnectHost, BoxLayout.Y_AXIS));
        
        JPanel nameuserPanel = new JPanel();
        nameuserPanel.setLayout(new FlowLayout());
        
        JLabel nameuserLabel = new JLabel("Name: ");
        nameuserTextArea = new JTextArea("", 2, 15);
        nameuserTextArea.setBorder(border);
        //nameuserTextArea.setFont(new Font("Verdana", Font.BOLD, 12));
        nameuserTextArea.setUI(new HintTextFieldUI("  Input Username (10 chars)", true));
        nameuserPanel.add(nameuserLabel);
        nameuserPanel.add(nameuserTextArea);
        
        JPanel iphostPanel = new JPanel();
        iphostPanel.setLayout(new FlowLayout());
        JLabel ipHostLabel = new JLabel("IP:         ");
        iphostTextArea = new JTextArea("", 2, 15);
        iphostTextArea.setBorder(border);
        iphostTextArea.setUI(new HintTextFieldUI("  Input IP Address", true));
        iphostPanel.add(ipHostLabel);
        iphostPanel.add(iphostTextArea);
        
        
        JPanel portuserPanel = new JPanel();
        portuserPanel.setLayout(new FlowLayout());      
        JLabel portuserLabel = new JLabel("Port:     ");
        portuserTextArea = new JTextArea("", 2, 15);
        portuserTextArea.setBorder(border);
        //portuserTextArea.setFont(new Font("Verdana", Font.BOLD, 12));
        portuserTextArea.setUI(new HintTextFieldUI("  Input Port", true));
        portuserPanel.add(portuserLabel);
        portuserPanel.add(portuserTextArea);
        
        cardConnectHost.add(nameuserPanel);
        cardConnectHost.add(iphostPanel);
        cardConnectHost.add(portuserPanel);

        /////////////Connect To Host////////////////
        
		/////////////Play with bot////////////////
        cardBot = new JPanel();
        cardBot.setLayout(new BoxLayout(cardBot, BoxLayout.Y_AXIS));
        
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new FlowLayout());
		JLabel namebotLabel = new JLabel("Name: ");
		namebotTextArea = new JTextArea("", 2, 15);
		namebotTextArea.setBorder(border);
		//namebotTextArea.setFont((new Font("Verdana", Font.BOLD, 12)));
		namebotTextArea.setUI(new HintTextFieldUI("  Input Username", true));
		botPanel.add(namebotLabel);
		botPanel.add(namebotTextArea);
		
		JPanel pointBotPanel =  new JPanel();
		pointBotPanel.setLayout(new FlowLayout());
        JLabel pointBotLabel = new JLabel("Point:  ");
        pointBotTextArea = new JTextArea("", 2, 15);
        pointBotTextArea.setBorder(border);
        pointBotTextArea.setUI(new HintTextFieldUI("  Input Point", true));
        pointBotPanel.add(pointBotLabel);
        pointBotPanel.add(pointBotTextArea);
		
		
		cardBot.add(new JPanel());
		cardBot.add(botPanel);
		cardBot.add(pointBotPanel);
		/////////////Play with bot////////////////
		
		
		
        // Create Button to play
        JPanel playPanel = new JPanel();
        //playPanel.setBackground(Color.CYAN);
        playPanel.setLayout(new FlowLayout());
        playButton = new JButton();
        playButton.setIcon(new ImageIcon("image/imagefiles-movie_play_green.png"));
        playButton.setPreferredSize(new Dimension(56, 32));
        playPanel.add(playButton);
        playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mode = cb.getSelectedIndex();
				switch(mode)
				{
				case 0:
				{
					String strname = namehostTextArea.getText();
					String stringPort = porthostTextArea.getText();
					String stringPoint = pointHostTextArea.getText();
					
					if(strname.length() == 0 || stringPort.length() == 0 || stringPoint.length() == 0)
					{
						JOptionPane.showConfirmDialog(null,"Please fill correct information",
	        				 	"Warning",
	        				    JOptionPane.OK_CANCEL_OPTION);
					}
					else
					{
						if(strname.length() == 0 || strname.length() > 10)
						{
							JOptionPane.showConfirmDialog(null,"Please input correct username (max 10 chars)",
		        				 	"Warning",
		        				    JOptionPane.OK_CANCEL_OPTION);
							
						}
						else
						{
							name = strname;
							if (stringPort.length() > 5)
							{
								JOptionPane.showConfirmDialog(null,"Wrong Port (max 5 digits)",
			        				 	"Warning",
			        				    JOptionPane.OK_CANCEL_OPTION);
							}
							else
							{
								int flag = 0;
								
								for (int i = 0; i < stringPort.length(); i++)
								{
									if ((stringPort.charAt(i) <= '0') || (stringPort.charAt(i) >= '9'))
									{
										flag = 1;
										JOptionPane.showConfirmDialog(null,"Wrong Port (numbers only)",
					        				 	"Warning",
					        				    JOptionPane.OK_CANCEL_OPTION);
										break;
									}
								}
								
								if (flag == 0)
									port = Integer.parseInt(stringPort);
							}
						}
						
						
					
						int flag = 0;
						
						for (int i = 0; i < stringPoint.length(); i++)
						{
							if ((stringPoint.charAt(i) < '0') || (stringPoint.charAt(i) > '9'))
							{
								flag = 1;
								JOptionPane.showConfirmDialog(null,"Wrong Point (numbers only)",
			        				 	"Warning",
			        				    JOptionPane.OK_CANCEL_OPTION);
								break;
							}
						}
						
						if (flag == 0)
							point = Integer.parseInt(stringPoint);
					}
						
					
					break;
				}
				
				case 1:
				{
					String strname = nameuserTextArea.getText();
					ip = iphostTextArea.getText();
					String stringPort = portuserTextArea.getText();
					if(strname.length() == 0 || stringPort.length() == 0 || ip.length() == 0)
					{
						JOptionPane.showConfirmDialog(null,"Please fill correct information",
	        				 	"Warning",
	        				    JOptionPane.OK_CANCEL_OPTION);
					}
					else
					{
						if(strname.length() == 0 || strname.length() > 10)
						{
							JOptionPane.showConfirmDialog(null,"Please input correct username (max 10 chars)",
		        				 	"Warning",
		        				    JOptionPane.OK_CANCEL_OPTION);
						}
						else
						{
							name = strname;
							
							if (stringPort.length() > 5)
							{
								JOptionPane.showConfirmDialog(null,"Wrong Port (max 5 digits)",
			        				 	"Warning",
			        				    JOptionPane.OK_CANCEL_OPTION);
							}
							else
							{
								int flag = 0;
								
								for (int i = 0; i < stringPort.length(); i++)
								{
									if ((stringPort.charAt(i) <= '0') || (stringPort.charAt(i) >= '9'))
									{
										flag = 1;
										JOptionPane.showConfirmDialog(null,"Wrong Port (numbers only)",
					        				 	"Warning",
					        				    JOptionPane.OK_CANCEL_OPTION);
										break;
									}
								}
								
								if (flag == 0)
									port = Integer.parseInt(stringPort);
							}
						}
					}
					
					break;
				}
				
				case 2:
				{
					String strname = namebotTextArea.getText();
					String stringPoint = pointBotTextArea.getText();
					
					if(strname.length() == 0 || strname.length() > 10)
					{
						JOptionPane.showConfirmDialog(null,"Please input correct username",
	        				 	"Warning",
	        				    JOptionPane.OK_CANCEL_OPTION);
					}
					else
					{
						name = strname;
					}
					
					
					int flag = 0;
					
					for (int i = 0; i < stringPoint.length(); i++)
					{
						if ((stringPoint.charAt(i) < '0') || (stringPoint.charAt(i) > '9'))
						{
							flag = 1;
							JOptionPane.showConfirmDialog(null,"Wrong Point (numbers only)",
		        				 	"Warning",
		        				    JOptionPane.OK_CANCEL_OPTION);
							break;
						}
					}
					
					if (flag == 0)
						point = Integer.parseInt(stringPoint);
				
					
					break;
				
				}
				}
			}
		});
        //Create the panel that contains the "cards".
        
        /////////Image top
        JLabel imagetop = new JLabel();
        imagetop.setLayout(new FlowLayout());
        ImageIcon image = new ImageIcon("image/icons8-Cards-128.png");
        imagetop.setIcon(image);
		
        
        /////////Image top
        cards = new JPanel(new CardLayout());
        
        cards.add(cardHost, HOSTPANEL);
        cards.add(cardConnectHost, CONNECTHOSTPANEL);
        cards.add(cardBot, BOTPANEL);
        
        pane.add(imagetop);
        pane.add(comboBoxPane);
        pane.add(cards);
        pane.add(playPanel);
    }
	
}

class HintTextFieldUI extends BasicTextFieldUI implements FocusListener {

    private String hint;
    private boolean hideOnFocus;
    private Color color;
    private boolean firstSelect;
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    private void repaint() {
        if(getComponent() != null) {
            getComponent().repaint();           
        }
    }

    public boolean isHideOnFocus() {
        return hideOnFocus;
    }

    public void setHideOnFocus(boolean hideOnFocus) {
        this.hideOnFocus = hideOnFocus;
        repaint();
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }
    public HintTextFieldUI(String hint) {
        this(hint,false);
    }

    public HintTextFieldUI(String hint, boolean hideOnFocus) {
        this(hint,hideOnFocus, null);
    }

    public HintTextFieldUI(String hint, boolean hideOnFocus, Color color) {
        this.hint = hint;
        this.hideOnFocus = hideOnFocus;
        this.color = color;
        this.firstSelect = false;
    }

    @Override
    protected void paintSafely(Graphics g) {
        super.paintSafely(g);
        JTextComponent comp = getComponent();
        if(hint!=null && comp.getText().length() == 0 && (!(hideOnFocus && comp.hasFocus() && firstSelect == false))){
        	
            if(color != null) {
                g.setColor(color);
            } else {
                g.setColor(comp.getForeground().brighter().brighter().brighter());              
            }
            int padding = (comp.getHeight() - comp.getFont().getSize())/2;
            g.drawString(hint, 2, comp.getHeight()-padding-1);     
            
        }
        /*else
        {
        	char[] str = new char[100];
        	int i;
        	for(i = 0; i < comp.getText().length(); i++)
        	{
        		str[i] = '*';
        	}
        	str[i] = '\0';
        	comp.setText(str.toString());
        }*/
        if(firstSelect == false)
    	{
    		firstSelect = true;
    	}
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(hideOnFocus) repaint();

    }

    @Override
    public void focusLost(FocusEvent e) {
        if(hideOnFocus) repaint();
    }
    @Override
    protected void installListeners() {
        super.installListeners();
        getComponent().addFocusListener(this);
    }
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        getComponent().removeFocusListener(this);
    }
    
    // -------0--
    
    
}

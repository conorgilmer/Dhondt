import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*; //import javax.swing.border.*;//import java.util.*;
/** Class to read in info to create a PayRequest class */
class PartyAddDialog extends JDialog implements ActionListener
{
	public static String getVersion()
	{
		return new String("$Revision: 3 $ $Date: 21.04.12 13:21 $");
	}
	private JFrame parent = null;
	public PartyAddDialog(JFrame parent)
	{
		super(parent, "Party Add Details", true);
		this.parent = parent;
		
		GridBagLayout gbl=new GridBagLayout();
		
		Container contentPanel = getContentPane();
		
		JPanel mainPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		mainPanel.setLayout(gbl);
		buttonPanel.setLayout(gbl);

		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Political Party Attributes")); 
		//contentPane.setLayout(gbl);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 100;
		gbc.weighty = 100;
		
	//row 1	
		gbc.fill = GridBagConstraints.HORIZONTAL;
		Insets labelInset = new Insets(0, 10, 0, 10);
		Insets noInset = new Insets(0, 0, 0, 0);
		Dimension labelSize = new Dimension(5, 5);
		
		gbc.insets = labelInset;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel partyLabel = new JLabel("Party Name:");
		partyLabel.setMaximumSize(labelSize);
		add(mainPanel, partyLabel, gbc, 0, 0, 5, 10);
		
		partyName = new JTextField(50);
		gbc.insets = noInset;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		add(mainPanel, partyName, gbc, 35, 0, 35, 10);
		
		gbc.insets = labelInset;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel timeLabel = new JLabel("Initials:");
		
		timeLabel.setPreferredSize(labelSize);
		add(mainPanel, timeLabel, gbc, 70, 0, 5, 10);
		
		partyLetters = new JTextField("", 10);
		partyLetters.setEditable(true);
		
		gbc.insets = noInset;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		add(mainPanel, partyLetters, gbc, 105, 0, 35, 10);
		
		/** 2nd Row  */
		gbc.insets = labelInset;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		add(mainPanel, new JLabel("Seats Won:"), gbc, 0, 10, 10, 10);
		
		seats = new JTextField("");
		gbc.insets = noInset;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		add(mainPanel, seats, gbc, 10, 10, 60, 10);
		
		gbc.insets = labelInset;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		add(mainPanel, new JLabel("Vote %:"), gbc, 70, 10, 10, 10);
		
		gbc.insets = noInset;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		percent = new JTextField(10);
		add(mainPanel, percent, gbc, 80, 10, 60, 10);
		
		/** 3rd Row  */
			
		Util.debug("Adding Color");
		
		gbc.insets = labelInset;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		add(mainPanel, new JLabel("Party Colors:"), gbc, 0, 20, 10, 10);
		
		gbc.insets = noInset;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
//		partyColor = new JTextField(10);
//
//		// create a combo box
                String [] items = { "red", "green", "blue", "yellow", "cyan", "orange", "magenta", "brown"};
		partyColor.setFont(new java.awt.Font("Courier", 1, 12));
		partyColor.setEditable(true);
		partyColor.addActionListener(this);


	 	partyColor.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
        	        //System.out.println( ((JComboBox)e.getSource()).getSelectedItem());
			}
		});




		add(mainPanel, partyColor, gbc, 10, 20, 60, 10);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		addPMButton = new JButton("...");      
		addPMButton.setPreferredSize(new Dimension(15, 20));
		addPMButton.addActionListener(this);
//		add(mainPanel, addPMButton, gbc, 139, 40, 1, 1);
		
		
		Util.debug("Adding buttons");
		confirmAddButton = new JButton("Add Party");
		cancelButton = new JButton("Cancel");
		Dimension buttonSize = new Dimension(120, 30);
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		
		confirmAddButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);
		
		
		confirmAddButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
					
		add(buttonPanel, confirmAddButton, gbc, 10, 10, 40, 10);
		add(buttonPanel, cancelButton, gbc, 70, 10, 40, 10);
		
		//JPanel mainPanel = new JPanel();
		//mainPanel.add(vertBoxMain);
		//mainPanel.setBorder(BorderFactory.createEtchedBorder());
			
		//contentPane.add("North", vertBoxMain);
		//contentPane.add("Center", p4);
		
		contentPanel.add("Center", mainPanel);
		contentPanel.add("South", buttonPanel);
		setSize(530, 275);
		setModal(true);
		// try to put dialog in nice position
		Point parentPos = parent.getLocation();
		Dimension parentSize = parent.getSize();
		setLocation(parentPos.x + ((parentSize.width - getSize().width)/2),
					parentPos.y + ((parentSize.height - getSize().height)/2));
		
		Util.debug("Reached end of constructor.");
		
	}
	

	public void add(Container panel, Component c, GridBagConstraints gbc,
					int x, int y, int w, int h)
	{
		gbc.gridx = x;
  	        gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		panel.add(c, gbc);
	}
	JButton addButton(Container c, String name)
	{
		JButton button = new JButton(name);      
		button.addActionListener(this);
		c.add(button);
		return button;
	}

   public void actionPerformed(ActionEvent evt)
   {  
	  Object source = evt.getSource();
	  if(source == addPMButton)
	  {
		  PartyAddDialog payMethodDialog = new PartyAddDialog(parent);
		  PartyAddInfo partyObj = new PartyAddInfo("",0.0,"",0,"", 0);
		  if(payMethodDialog.showDialog(partyObj))
		  {
/*		      displayMessage("Account: " + payMethod.account + 
						   "\r\n\tAccount Type: " + payMethod.accountType + 
						   "\r\n\tAccount Index: " + payMethod.accountIndex);*/
	/*		  selectedPayMethod 
				  = new UmPayReq.PayMethod(payMethodObj.account,
											 payMethodObj.accountType,
											 payMethodObj.accountIndex);
			  String displayString = new String();
			  displayString = "Account: " + selectedPayMethod.getAccount() + 
							  "; Acc. Type: " + selectedPayMethod.getAccountType() +
							  "; Acc. Index: " + selectedPayMethod.getAccountIndex();
			  payMethod.setText(displayString);*/
			  repaint();
		  }
		  
	  }
      else if(source == confirmAddButton)
      {  ok = true;
         setVisible(false);
      }
      else if (source == cancelButton)
	  {
		 ok = false;
         setVisible(false);
	  }
   }
	
   public boolean showDialog(PartyAddInfo partyInfo)
   {
	  JFrame parent = (JFrame) getParent();
	  Point parentPos = parent.getLocation();
	  
	  Dimension parentSize = parent.getSize();
		setLocation(parentPos.x + ((parentSize.width - getSize().width)/2),
					parentPos.y + ((parentSize.height - getSize().height)/2));
		partyName.setText(""); //string
		partyLetters.setText(""); // string
		seats.setText("0"); //int
		percent.setText("0.0"); // double
		//partyColor.setText("Blue"); // Color
	  
      ok = false;
	  boolean validData = false;
	  show();
	  while(!validData)
	  {
		  if (ok)
		  {  
			if(seats.isValid() && percent.isValid() )
			{
				// yeah valid Data
				validData = true;
			}
			else
			{
				 JOptionPane.showMessageDialog(this,  "Seats and Percentage Vote must be numerical!",
											   "Input error",
											   JOptionPane.ERROR_MESSAGE);
				 show();
			}
			partyInfo.seats =  Integer.parseInt(seats.getText());
			partyInfo.partyColor =  (String)partyColor.getSelectedItem();
			partyInfo.partyName = partyName.getText();
			partyInfo.partyLetters = partyLetters.getText();
			partyInfo.percent = Double.parseDouble(percent.getText());
		  }
		  else  {    // it's not valid; there is none
			  validData=true;
		  }
      }
      return ok;
   }
	private JTextField partyName;
	private JTextField partyLetters; // string
	private JTextField seats; //int
	private JTextField percent; // double
//	private JTextField partyColor; //color
	private JComboBox partyColor =  new JComboBox(new String [] { "red", "green", "blue", "yellow", "cyan", "orange", "magenta", "brown"});
	private boolean ok;
	private JButton confirmAddButton;
	private JButton cancelButton;
	private JButton addPMButton;
}

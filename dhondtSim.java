import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import java.sql.*;	// Time
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.lang.reflect.Field;
//import webwayz.plot.*;	//Plot Graph

public class dhondtSim extends JFrame 
   implements ActionListener, MenuListener
{
	String log = "Console";  // "Screen", "Console" or "None"
    static  String displayMsg = "D'hondt Simulation ";
 double[] seats2011 =  new double[]{20, 76, 37, 14, 0, 5, 14};
 double[] percent2011 =  new double[]{17.4, 36.1, 19.4,9.9, 1.8, 2.2, 12.1};
 double[] prseats2011 =  new double[]{20, 76, 37, 14, 5, 14, 0};
 double[] bounce2011 =  new double[]{20, 21, 22, 24, 23, 24, 12};


   public dhondtSim() 
   {
	   /** Basic window stuff: name, size	    * a	    */
	  //Util.debugOff();
	  setTitle("D'hondt System");
	  setSize(510, 510);
	  Window owner = getOwner();
	  Toolkit tk = Toolkit.getDefaultToolkit();
	  Dimension screenSize = tk.getScreenSize();
	  
      setLocation(((screenSize.width - getSize().width)/2),
				  ((screenSize.height - getSize().height)/2));
      /** Need this to close the window       */
      addWindowListener(new WindowAdapter()
      {  public void windowClosing(WindowEvent e)
         {  System.exit(0);
         }
      } );
	  
	  /** Set up Menu 	   */
	  menuBar = new JMenuBar();
	  setJMenuBar(menuBar);

	  helpMenu = new JMenu("Help");
	  helpMenu.addMenuListener(this);
	  
	  showParties = new JMenuItem("Show Parties");
	  showParties.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
	  graphGrid = new JMenuItem("Bar Chart Parties");
	  graphGrid.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
	  aboutItem = new JMenuItem("About");
	  aboutItem.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

	  readmeItem = new JMenuItem("Read Me");
	  readmeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
	  
	  menuBar.add(makeMenu(helpMenu, new Object[]{ aboutItem, readmeItem, showParties, graphGrid }, this));

  	  resendMenu = new JMenu("Parties");
          resendMenu.setEnabled(false);
	  menuBar.add(makeMenu(resendMenu,  new Object[]{}, this));

	  /** End menu set-up   */
	  JPanel initPanel = new JPanel();
	  initPanel.setLayout(new GridLayout(2, 2));
	  initPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "D'hondt"));

	  // Configuration Input Fields
	  leftLabel = new JLabel();
	  leftLabel.setText("Select Ministerial posts:");
	  initPanel.add(leftLabel);
	  
          String electionYears[] = {"1997","2002","2007","2011"};

          postsTextField = new JTextField("10");
	  postsTextField.addActionListener(this);
	  initPanel.add(postsTextField);

	  //Buttons
	  addPartyBtn = new JButton("Add Party");
	  addPartyBtn.setEnabled(true);
	  addPartyBtn.addActionListener(this);
	  initPanel.add(addPartyBtn);
	  
	  // run simulation button
	  runSimBtn = new JButton("Run D'Hondt"); 
	  runSimBtn.setEnabled(false);
	  runSimBtn.addActionListener(this);
	  initPanel.add(runSimBtn);

	  /* results output area */    
	  resultsTextArea = new JTextArea();
	  JScrollPane resultsPanel = new JScrollPane(resultsTextArea);

	  /* plot graphs panel*/
	  JPanel graphPanel = new JPanel();
	  graphPanel.setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createEtchedBorder(), "Graph Panel"));
          graphPanel.setLayout(new GridLayout(1, 2));
	  ButtonGroup directionGroup = new ButtonGroup();

	  graphSeatsBtn = new JButton("Plot Elected Seats");
	  graphSeatsBtn.addActionListener(this);
	  graphSeatsBtn.setEnabled(false);
	  graphPanel.add(graphSeatsBtn);

	  graphDhondtBtn = new JButton("Graph Ministerial Seats");
	  graphDhondtBtn.addActionListener(this);
	  graphDhondtBtn.setEnabled(false);
	  graphPanel.add(graphDhondtBtn);

         double[] valu = {20, 5, 6};
	 String[] lang = {"FF","UUP","LAB"};
	 Color[] partyc = {Color.green,Color.blue,Color.red};

	  JPanel outputPanel = new JPanel();
	  JLabel outLabel = new JLabel();
//	  outLabel.setText("Verbiage:");
	  outputPanel.setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createEtchedBorder(), "Output Panel"));
   	  outputPanel.setPreferredSize(new Dimension(140, 140));	
	  outputPanel.add(new BarPlot(valu, lang, partyc,     "new charttitle",true));

          Container contentPane = getContentPane();
	  contentPane.add(resultsPanel, "Center");
//	  contentPane.add(outputPanel, "East");	  
	  contentPane.add(initPanel, "North");
	  contentPane.add(graphPanel, "South");
   }

   public JRadioButton addRadioButton(JPanel p, ButtonGroup g, String name, boolean selected)
   {  JRadioButton button          = new JRadioButton(name, selected);
      button.addActionListener(this);
      g.add(button);
      p.add(button);
      return button;
   }

   public JCheckBox addCheckBox(JPanel p, String name)
   {  JCheckBox checkBox = new JCheckBox(name);
      //checkBox.addActionListener(this);
      p.add(checkBox);
      return checkBox;
   }
   
   public void actionPerformed(ActionEvent evt)
   {  

  String charttitle = "The Parties";

  Object source = evt.getSource();
  if (source == addPartyBtn)
  {  
	   displayLine("adding a party \n");
   	   runSimBtn.setEnabled(true);
   	   graphSeatsBtn.setEnabled(true);
	   graphDhondtBtn.setEnabled(false);

    	   PartyAddDialog partyAddDialog = new PartyAddDialog(this);
           PartyAddInfo partyAdd = new PartyAddInfo("", 0.0, "", 0, null ,0   );
	   if(partyAddDialog.showDialog(partyAdd)){
				displayLine("Party Add Dialog");
					}
	  displayLine("Party added = " + partyAdd.toString());
	  if (partyAdd.partyName.trim().isEmpty())
		{ displayLine("no party name, so no party added");}
	  else
		  addParty(partyAdd); 
  }
  else if (source == runSimBtn)
	  {
          System.out.println("Starting.. Run Sim.\n");
	  int chairs = Integer.parseInt(postsTextField.getText());	

	  int vectorsize = parties.size();
	  if (vectorsize > 0) {

	  double[] votes= new double[vectorsize];
	  double[] ministers= new double[vectorsize];
	  String[] party = new String[vectorsize];
	  Color[] partycolours = new Color[vectorsize];
	  charttitle = "The Parties - Ministerial Seats";

	  for (int record = 0; record < vectorsize; record++) 
	  {
	  	PartyAddInfo varParty = (PartyAddInfo) parties.elementAt(record); 
	  	votes[record] = varParty.seats;
	  	party[record] = varParty.partyLetters;
	  	//glean the party color from a string
	  	try {
	  	    Field field = Class.forName("java.awt.Color").getField(varParty.partyColor.toLowerCase());
	  	    partycolours[record] = (Color)field.get(null);
	  	} catch (Exception e) {
	  		logError("Color not recognised " + e);
	  		partycolours[record] = Color.gray; // Not defined
	  	}
	  }

          displayLine("D'Hondt seats allocate " + chairs + " Ministries.");

	int [] allocated = new int[votes.length];
	double [][] dhondtTable = new double [chairs][votes.length];
	displayLine("Dhondt Table chairs="+chairs+ " parties= " +votes.length+ "\n");
// Build D'Hondt Table
	for (int m = 0; m < chairs; m++) {
		for (int n = 0; n < votes.length; n++) {
			if (m == 0)
				dhondtTable[m][n] = votes[n];
			else
				dhondtTable[m][n] = dhondtTable[0][n]/(m+1);
		}
	}
//Print Table
       displayMessage("Table for D'Hondt");
	DrawDhondtTable(dhondtTable,votes.length, chairs);

/*Allocate Ministers using D'Hondt
 * iterate through the number of cabinet posts to allocate
 * find the highest value in the D'Hondt Table 
 */
	int o = 0;
	PartyAddInfo newPartyElt = new PartyAddInfo("", 0.0,"", 0, "", 0);
	for (int c= 1; c < chairs+1; c++) {
		o = getMaxElement(dhondtTable,votes.length, chairs);
		newPartyElt = (PartyAddInfo) parties.elementAt(o);
		newPartyElt.ministers = newPartyElt.ministers +1;
		DrawDhondtTable(dhondtTable,votes.length, chairs);
	 	displayLine(" Seat " + c + " for " + party[o]);
		allocated[o] = allocated[o] + 1;
		parties.set(o, newPartyElt);

	}

	DrawDhondtTable(dhondtTable,votes.length, chairs);

//Output Results
	for (int p=0; p < votes.length; p++) {
		displayLine(party[p] + " gets " + allocated [p] + " Minsters");
	}

	double [] allocatedD = new double[allocated.length];
	for (int index = 0; index < allocated.length; index++)
		allocatedD[index] = (double)allocated[index];

	  BarPlot.main(allocatedD, party, partycolours,     charttitle, false, 500, 400);

	graphDhondtBtn.setEnabled(true);

	} 
		else 
		 	displayLine("Parties vector empty");


	  }

	  else if(source == aboutItem)
	  {
	//	  new AboutRD'HondtelaxSim(this).show();
	  }
	else if(source == readmeItem)
	  {

	//	  new ReadMe(this).show();
	  }
 
         else if (source == graphSeatsBtn || source == graphGrid)
	 {
		int vectorsize = parties.size();
		if (vectorsize > 0) {

		double[] value= new double[vectorsize];
		double[] ministers= new double[vectorsize];
		String[] languages = new String[vectorsize];
		Color[] partycolours = new Color[vectorsize];
		charttitle = "The Parties - Seats";

		for (int record = 0; record < vectorsize; record++) 
		{
			PartyAddInfo varParty = (PartyAddInfo) parties.elementAt(record); 
			value[record] = varParty.seats;
			ministers[record] = varParty.ministers;
			languages[record] = varParty.partyLetters;
			//glean the party color from a string
			try {
			    Field field = Class.forName("java.awt.Color").getField(varParty.partyColor.toLowerCase());
			    partycolours[record] = (Color)field.get(null);
			} catch (Exception e) {
				logError("Color not recognised " + e);
				partycolours[record] = Color.gray; // Not defined
			}
		}

	 	BarPlot.main(value, languages, partycolours, charttitle,  false,500, 400);
		} 
		else 
		 	displayLine("Parties vector empty");
   	}

         else if (source == graphDhondtBtn)
	 {

	 charttitle =" The Parties Ministerial Seats";
	int vectorsize = parties.size();
		if (vectorsize > 0) {

		double[] value= new double[vectorsize];
		double[] ministers= new double[vectorsize];
		String[] languages = new String[vectorsize];
		Color[] partycolours = new Color[vectorsize];
		charttitle = "The Parties - Seats";

		for (int record = 0; record < vectorsize; record++) 
		{
			PartyAddInfo varParty = (PartyAddInfo) parties.elementAt(record); 
			value[record] = varParty.seats;
			ministers[record] = varParty.ministers;
			languages[record] = varParty.partyLetters;
			//glean the party color from a string
			try {
			    Field field = Class.forName("java.awt.Color").getField(varParty.partyColor.toLowerCase());
			    partycolours[record] = (Color)field.get(null);
			} catch (Exception e) {
				logError("Color not recognised " + e);
				partycolours[record] = Color.gray; // Not defined
			}
		}

	 	BarPlot.main(ministers, languages, partycolours, charttitle,  false,500, 400);
		} 
		else 
		 	displayLine("Parties vector empty");





///	 BarPlot.main(bounce2011, languages, partycolours,   charttitle,  true,500, 400);

	}

         else if (source == showParties)
	{
		// display the contents of the vector(parties)
		Iterator vItr = parties.iterator(); 
		displayLine("Parties:"); 
		while(vItr.hasNext()) 
			displayLine(vItr.next() + " "); 
	}

	  else
	  {
		  //  Util.debug("Unrecognised event");
	  }
      repaint();
   }

   public static void main(String[] args)
   {  Frame f = new dhondtSim();
      f.show();  
   }
   
   // print a message to the screen with timestamp 
   private void displayMessage(String message)
   {
	  Time time = new Time(System.currentTimeMillis());
	  resultsTextArea.setFont(boldFont);
	  resultsTextArea.append(time.toLocaleString() + ":\r\n\t");
	  resultsTextArea.append(message + "\n");
   }

   // log an error to the screen or console..
   private void logError(String message)
   {
	if (log=="Screen") {
	   displayLine(message);
	}
	else if (log=="Console") {
		System.out.println(message);
	}
	else {
		//do nothing
	}
   }

   // print a message/line to the screen  
      private void displayLine(String message)
	   {
	  
	  resultsTextArea.setFont(boldFont);
	  resultsTextArea.append(message + "\n");
	   }


   private void addParty(PartyAddInfo party)
   {
	   parties.add(party);
	   String itemPos = new Integer(parties.size() - 1).toString();
	   String itemName = "" +  itemPos + ". ";

	   itemName = itemName + " - " + party.toString();

	   JMenuItem resendItem = new JMenuItem(itemName);
	   resendItem.addActionListener((ActionListener) this);
	   resendItem.setActionCommand(itemPos);
	   resendMenu.add(resendItem);
	   resendMenu.setEnabled(true);
   }
   



   public void menuSelected(MenuEvent evt)
   {
	   
   }
   public void menuDeselected(MenuEvent evt)
   {
   }
   public void menuCanceled(MenuEvent evt)
   {
   }

   public static JMenu makeMenu(Object parent, Object[] items,	Object target)
   {
	   JMenu m = null;
	   if (parent instanceof JMenu)
		   m = (JMenu)parent;
	   else if (parent instanceof String)
		   m = new JMenu((String) parent);
	   else
		   return null;
	   
	   for (int i= 0; i < items.length; i++)
	   {
		   if (items[i] == null)
			   m.addSeparator();
		   else
			   m.add(makeMenuItem(items[i], target));
	   }
	   
	   return m;
   }
   
   public static JMenuItem makeMenuItem(Object item,	Object target)
   {
	   JMenuItem r = null;
	   if (item instanceof String)
		   r = new JMenuItem((String)item);
	   else if (item instanceof JMenuItem)
		   r = (JMenuItem)item;	   
	   else return null;
	   if (target instanceof ActionListener)
		   r.addActionListener((ActionListener) target);
	   return r;
	   
   }



//get the highest value in the 2D table and remove it!
//returning the row=(party) to which it belongs
	public int getMaxElement(double [][]dTable, int pN, int pM)
	{
	double maxValue = dTable[0][0];
 	int maxElement = 0;
	int i =0, j =0;
	for (int sn = 0; sn < pN; sn++) {
		for (int sm = 0; sm < pM; sm++) {
		if(dTable[sm][sn] > maxValue){
			maxValue = dTable[sm][sn];
			i =sm;
			j = sn;
			}
		  }
		}
	dTable[i][j] = 0.0; // zero the highest element for next run
	return j;
	}

// Build a d'Hondt Table
    public void DrawDhondtTable(double [][]dTable ,int pN, int pM) {
		String row = "";
		//DecimalFormat df = new DecimalFormat("##.##");		
		DecimalFormat df = new DecimalFormat();		
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		for (int n = 0; n < pN; n++) {
			for (int m = 0; m < pM; m++) {
//				row = row + Math.round(dTable[m][n]) +", ";
				row = row + df.format(dTable[m][n]) + ", ";
			}
			row = row + "\n";
		}

		displayLine(row);
	}


   //input Fields
   private JLabel leftLabel;
   private JTextField postsTextField;   

   //Buttons
   private JButton graphSeatsBtn;
   private JButton graphDhondtBtn;
   private JButton addPartyBtn;
   private JButton runSimBtn;
   
   //Menu items
   private JMenuBar menuBar;
   private JMenuItem aboutItem;
   private JMenuItem readmeItem;   
   private JMenuItem graphGrid;   
   private JMenuItem showParties;   
   private JMenu helpMenu;
   private JMenu resendMenu;

   //Vector to store party details
   private Vector parties = new Vector();   
   
   //output area
   private JTextArea resultsTextArea;
   
   // fonts    
   Font boldFont = new Font("SansSerif", Font.BOLD + Font.ITALIC, 12);
   
}




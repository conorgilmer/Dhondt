import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import java.sql.*;	// Time
import java.util.*;
import java.text.*;
//import webwayz.plot.*;	//Plot Graph

public class Bounce extends JFrame 
   implements ActionListener, MenuListener
{
    static  String displayMsg = "Electoral Bounce in Seats ";
 double[] seats2011 =  new double[]{20, 76, 37, 14, 0, 5, 14};
 double[] percent2011 =  new double[]{17.4, 36.1, 19.4,9.9, 1.8, 2.2, 12.1};
 double[] prseats2011 =  new double[]{20, 76, 37, 14, 5, 14, 0};
 double[] bounce2011 =  new double[]{20, 21, 22, 24, 23, 24, 12};


   public Bounce() 
   {
	   /** Basic window stuff: name, size	    * a	    */
	  //Util.debugOff();
	  setTitle("Electoral Bounce in PR Systems");
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
	  
	    
	  graphGrid = new JMenuItem("Graph Grid");
	  graphGrid.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
	  aboutItem = new JMenuItem("About");
	  aboutItem.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

	  readmeItem = new JMenuItem("Read Me");
	  readmeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
	  
	  menuBar.add(makeMenu(helpMenu, new Object[]{ aboutItem, readmeItem, graphGrid }, this));

  	  resendMenu = new JMenu("Re-run");
          resendMenu.setEnabled(false);
	  menuBar.add(makeMenu(resendMenu,  new Object[]{}, this));

	  /** End menu set-up   */
	  JPanel umConnectPanel = new JPanel();
	  umConnectPanel.setLayout(new GridLayout(2, 2));
	  umConnectPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Proportional Representation Electoral Bounce"));

	  // Configuration Input Fields
	  leftLabel = new JLabel();
	  leftLabel.setText("Select Election Years:");
	  umConnectPanel.add(leftLabel);
	  
          String electionYears[] = {"1997","2002","2007","2011"};
          yearsCombo = new JComboBox(electionYears);
	  yearsCombo.addActionListener(this);
	  umConnectPanel.add(yearsCombo);

	  //Buttons
	  initBtn = new JButton("Initalize");
	  initBtn.setEnabled(true);
	  initBtn.addActionListener(this);
	  umConnectPanel.add(initBtn);
	  
	  
	  runSimBtn = new JButton("Run Relaxation Algorithm"); 
	  runSimBtn.setEnabled(false);
	  runSimBtn.addActionListener(this);
	  umConnectPanel.add(runSimBtn);

	    
	  resultsTextArea = new JTextArea();
	  JScrollPane resultsPanel = new JScrollPane(resultsTextArea);
	  /* send req panel*/
 JPanel graphPanel = new JPanel();
	  graphPanel.setBorder(BorderFactory.createTitledBorder(
											BorderFactory.createEtchedBorder(),
		"Graph Panel"));
          graphPanel.setLayout(new GridLayout(1, 2));
	  ButtonGroup directionGroup = new ButtonGroup();

	  graphBtn = new JButton("Plot Seats");
	  graphBtn.addActionListener(this);
	  graphBtn.setEnabled(false);
	  graphPanel.add(graphBtn);

	  graphBounceBtn = new JButton("Plot Bounce");
	  graphBounceBtn.addActionListener(this);
	  graphBounceBtn.setEnabled(false);
	  graphPanel.add(graphBounceBtn);


//	 double[] valu= new double[3];
  //       String[] lang = new String[3];
    //     Color[] partyc = new Color[3];
         double[] valu = {20, 5, 6};
	 String[] lang = {"FF","UUP","LAB"};
	 Color[] partyc = {Color.green,Color.blue,Color.red};



	  JPanel outputPanel = new JPanel();
	  JLabel outLabel = new JLabel();
//	  outLabel.setText("Verbiage:");
	  outputPanel.setBorder(BorderFactory.createTitledBorder(
											BorderFactory.createEtchedBorder(),
		"Output Panel"));
	outputPanel.setPreferredSize(new Dimension(140, 140));	
	  outputPanel.add(new BarPlot(valu, lang, partyc,
    "new charttitle",true));

          Container contentPane = getContentPane();
	  contentPane.add(resultsPanel, "Center");
	  contentPane.add(outputPanel, "East");	  
	  contentPane.add(umConnectPanel, "North");

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

  double[] value= new double[7];
  String[] languages = new String[7];
  Color[] partycolours = new Color[7];
  String charttitle = "The Parties";

  Object source = evt.getSource();
  if (source == initBtn)
  {  
         int intizedouble = 0;
	for (int d=0; d<7; d++){
	intizedouble = (int) ((166.0/ 100.0) * percent2011[d]);
	prseats2011[d] = (double) intizedouble;
	intizedouble =  (int) (seats2011[d] - prseats2011[d]);
	bounce2011[d] = (double) intizedouble;
	}


	   displayMessage("initi\n");
   	   runSimBtn.setEnabled(true);
   	   graphBounceBtn.setEnabled(true);
   	   graphBtn.setEnabled(true);
	   
  }
  else if (source == yearsCombo)
{
	String str = (String)yearsCombo.getSelectedItem();
	displayMessage("now its " + str);


}
  else if (source == runSimBtn)
	  {
           System.out.println("Starting.. Run Sim.\n");
//  value[0] = 20;
  value[0] = -7.35;
  languages[0] = "Fianna Fail";
  partycolours[0] = Color.green;

//  value[1] = 76;
  value[1] = 9.68;
  languages[1] = "Fine Gael";
  partycolours[1] = Color.blue;

  //value[2] = 37;
  value[2] = 4.89;
  languages[2] = "Labour";
  partycolours[2] = Color.red;

  //value[3] = 14;
  value[3] = -1.47;
  languages[3] = "Sinn Fein";
  partycolours[3] = Color.yellow;

  //value[4] = 0;
  value[4] = -1.8;
  languages[4] = "Greens";
  partycolours[4] = Color.cyan;

//  value[5] = 5;
  value[5] = 0.51;
  languages[5] = "ULA";
  partycolours[5] = Color.magenta;
  
  //value[6] = 14;
  value[6] = -4.47;
  languages[6] = "Others";
  partycolours[6] = Color.gray;
/*  int intizedouble = 0;
 double[] seats2011 =  new double[]{20, 76, 37, 14, 0, 5, 14};
 double[] percent2011 =  new double[]{17.4, 36.1, 19.4,9.9, 1.8, 2.2, 12.1};
 double[] prseats2011 =  new double[]{20, 76, 37, 14, 5, 14, 0};
	for (int d=0; d<7; d++){
	intizedouble = (int) ((166.0/ 100.0) * percent2011[d]);
	prseats2011[d] = (double) intizedouble;
}*/


	 BarPlot.main(prseats2011, languages, partycolours,
    charttitle, true, 500, 400);



	  }

	  else if(source == aboutItem)
	  {
	//	  new AboutRelaxSim(this).show();
	  }
	else if(source == readmeItem)
	  {

	//	  new ReadMe(this).show();
	  }
 
         else if (source == graphBtn)
	 {
charttitle = "The Parties - Seats";

  value[0] = 20;
//  value[0] = -7.35;
  languages[0] = "Fianna Fail";
  partycolours[0] = Color.green;

  value[1] = 76;
  //value[1] = 9.68;
  languages[1] = "Fine Gael";
  partycolours[1] = Color.blue;

  value[2] = 37;
  //value[2] = 4.89;
  languages[2] = "Labour";
  partycolours[2] = Color.red;

  value[3] = 14;
  //value[3] = -1.47;
  languages[3] = "Sinn Fein";
  partycolours[3] = Color.yellow;

  value[4] = 0;
  //value[4] = -1.8;
  languages[4] = "Greens";
  partycolours[4] = Color.cyan;

  value[5] = 5;
//  value[5] = 0.51;
  languages[5] = "ULA";
  partycolours[5] = Color.magenta;
  
  value[6] = 14;
  //value[6] = -4.47;
  languages[6] = "Others";
  partycolours[6] = Color.gray;

	 BarPlot.main(value, languages, partycolours,
    charttitle,  true,500, 400);



		// plot contour map of the grid
	   //jahuwaldt.plot.EquiPlotWindow.main(grid, no_lines);
	   }

         else if (source == graphBounceBtn)
	 {
 charttitle =" The Parties electoral bounce";
//  value[0] = 20;
  value[0] = -7.35;
  languages[0] = "Fianna Fail";
  partycolours[0] = Color.green;

//  value[1] = 76;
  value[1] = 9.68;
  languages[1] = "Fine Gael";
  partycolours[1] = Color.blue;

  //value[2] = 37;
  value[2] = 4.89;
  languages[2] = "Labour";
  partycolours[2] = Color.red;

  //value[3] = 14;
  value[3] = -1.47;
  languages[3] = "Sinn Fein";
  partycolours[3] = Color.yellow;

  //value[4] = 0;
  value[4] = -1.8;
  languages[4] = "Greens";
  partycolours[4] = Color.cyan;

//  value[5] = 5;
  value[5] = 0.51;
  languages[5] = "ULA";
  partycolours[5] = Color.magenta;
  
  //value[6] = 14;
  value[6] = -4.47;
  languages[6] = "Others";
  partycolours[6] = Color.gray;


	 BarPlot.main(bounce2011, languages, partycolours,
    charttitle,  true,500, 400);

		// plot contour map of the grid
	   //jahuwaldt.plot.EquiPlotWindow.main(grid, no_lines);

	}

         else if (source == graphGrid)
	{
		// plot contour map of the grid
        	//jahuwaldt.plot.EquiPlotWindow.main(grid, no_lines);

	}

	  else
	  {
		  //  Util.debug("Unrecognised event");
	  }
      repaint();
   }

   public static void main(String[] args)
   {  Frame f = new Bounce();
      f.show();  
   }
   
   
   private void displayMessage(String message)
   {
	  
	  Time time = new Time(System.currentTimeMillis());
	  resultsTextArea.setFont(boldFont);
	  resultsTextArea.append(time.toLocaleString() + ":\r\n\t");
	  resultsTextArea.append(message + "\n");
   }

      private void displayLine(String message)
	   {
	  
	  resultsTextArea.setFont(boldFont);
	  resultsTextArea.append(message + "\n");
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

   public static JMenu makeMenu(Object parent,
								Object[] items,
								Object target)
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

   //input Fields
   private JLabel leftLabel;
   private JComboBox yearsCombo;   

   //Buttons
   private JButton graphBtn;
   private JButton graphBounceBtn;
   private JButton initBtn;
   private JButton runSimBtn;
   
   //Menu items
   private JMenuBar menuBar;
   private JMenuItem aboutItem;
   private JMenuItem readmeItem;   
   private JMenuItem graphGrid;   
   private JMenu helpMenu;
   private JMenu resendMenu;

   private Vector resendableReqs = new Vector();   
   
   private JTextArea resultsTextArea;
   
   /** my fonts     */
   Font boldFont = new Font("SansSerif", Font.BOLD + Font.ITALIC, 12);
   
}




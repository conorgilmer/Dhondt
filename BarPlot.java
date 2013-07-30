/* BarPlot.java
 * Plots a Bar Chart based on a 3 arrays.
 * Based on Simple Bar Chart
 * modified by Conor Gilmer
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import java.lang.*;

public class BarPlot extends JPanel {
public double[] value;       // value height of the bar
public String[] languages;   // name of the grouping
public Color[] partycolours; // color of the bar
public String title;         // title of the chart
public boolean percentage;   // display number as a percentage or a unit.

public BarPlot(double[] val, String[] lang, Color[] parties, String t, boolean perc) {
	languages = lang;
	partycolours = parties;
	value = val;
	title = t;
	percentage = perc;
}


public BarPlot(int[] val, String[] lang, Color[] parties, String t, boolean perc) {
	languages = lang;
	partycolours = parties;
	for (int index = 0; index < val.length; index++)
	value[index] = (double)val[index];
	//value = val;
	title = t;
	percentage = perc;
}




public void paintComponent(Graphics graphics) {
	super.paintComponent(graphics);
	if (value == null || value.length == 0)
		return;
	double minValue = 0;
	double maxValue = 0;
	for (int i = 0; i < value.length; i++) {
		if (minValue > value[i])
			minValue = value[i];
		if (maxValue < value[i])
			maxValue = value[i];
	}
	Dimension dim = getSize();
	int clientWidth = dim.width;
	int clientHeight = dim.height;
	int barWidth = clientWidth / value.length;
	Font titleFont = new Font("Book Antiqua", Font.BOLD, 15);
	FontMetrics titleFontMetrics = graphics.getFontMetrics(titleFont);
	Font labelFont = new Font("Book Antiqua", Font.PLAIN, 10);
	FontMetrics labelFontMetrics = graphics.getFontMetrics(labelFont);
	Font numsFont = new Font("Book Antiqua", Font.BOLD, 10);
	//FontMetrics numsFontMetrics = graphics.getFontMetrics(numsFont);
	int titleWidth = titleFontMetrics.stringWidth(title);
	int q = titleFontMetrics.getAscent();
	int p = (clientWidth - titleWidth) / 2;
	graphics.setFont(titleFont);
	graphics.drawString(title, p, q);
	int top = titleFontMetrics.getHeight();
	int bottom = labelFontMetrics.getHeight();
	if (maxValue == minValue)
		return;
	double scale = (clientHeight - top - bottom -top) / (maxValue - minValue);
	q = clientHeight - labelFontMetrics.getDescent();
	graphics.setFont(labelFont);
	for (int j = 0; j < value.length; j++) {
		graphics.setFont(labelFont);
		int valueP = j * barWidth + 1;
		int valueQ = top;
		int newq =q;
		int height = (int) (value[j] * scale);
		if (value[j] >= 0)
			valueQ += (int) ((maxValue - value[j]) * scale);
		else {
			valueQ += (int) (maxValue * scale);
			height = -height;
		}
		if (minValue < 0){
			newq =  q - (int)minValue;
		}
		//top is added to the valueQ so as the heading is not overwritten by percentage/value
		//also scale is amended above with a 2nd top deducted from it!
		graphics.setColor(partycolours[j]); 	//graphics.setColor(Color.blue);
		graphics.fillRect(valueP, valueQ +top, barWidth - 2, height);
		graphics.setColor(Color.black);
		graphics.drawRect(valueP, valueQ+top, barWidth - 2, height);
		int labelWidth = labelFontMetrics.stringWidth(languages[j]);
		p = j * barWidth + (barWidth - labelWidth) / 2;
		graphics.drawString(languages[j], p, q);
		graphics.setFont(numsFont);
		if (percentage) {
			graphics.drawString(Double.toString(value[j]) + "%", p + 5,  valueQ - 5 +top);
		}
		else {
			int unit = (int) value[j];
			graphics.drawString(Integer.toString(unit), p + 5,  valueQ - 5 +top);
		}
	} // end of for loop
  }
	public static void main(double [] value, String [] languages, Color [] partycolours, String charttitle, boolean percentage, int wwidth, int wheight) {
	JFrame frame = new JFrame();
	frame.setSize(wwidth, wheight);
	frame.getContentPane().add(new BarPlot(value, languages, partycolours, charttitle, percentage));

	WindowListener winListener = new WindowAdapter() {
	public void windowClosing(WindowEvent event) {
//		System.exit(0);
		}
	};
	frame.addWindowListener(winListener);
	frame.setVisible(true);
	} // end of main
}

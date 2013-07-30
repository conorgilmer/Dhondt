//import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;
//import javax.swing.text.*;
//import java.io.*;

public class IntTextField extends JTextField
{
	
	public IntTextField(int defval, int size)
	{
		super("" + defval, size);
	}
	public boolean isValid()
	{
		try
		{
			Integer.parseInt(getText());
			return true;
		}
		catch(NumberFormatException e)
		{       System.out.println("something went a wry");
			return false;
		}
	}
	public int getValue()
	{
		try
		{
			return Integer.parseInt(getText());
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
	}
}

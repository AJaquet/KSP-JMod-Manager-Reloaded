package de.notepass.KSPModManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener
{
	private int buttonID;
	
	public ButtonListener (int id)
	{
		this.buttonID = id;
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		switch (buttonID)
		{
		case 0:
			// Reload TabMain
			break;
		case 1:
			// Install TabMain
			break;
		case 2:
			// Remove TabMain
			break;
		}
	}
}
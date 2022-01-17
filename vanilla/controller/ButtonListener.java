package vanilla.controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import vanilla.view.MapPanel;
import vanilla.view.Window;


public class ButtonListener implements ActionListener {
	
	private Window w = null;
    private int buttonType;
	
	public ButtonListener(Window window,int buttonType) {
		w = window;
        this.buttonType = buttonType;
	}

	public void actionPerformed (ActionEvent e) {
		switch (buttonType) {
            case 1:
                w.changePanel();
                break;
            case 2:
                ((MapPanel) w.getPanels().get(1)).updateView();
                break;
            default:
                break;
        }
        
	}
}

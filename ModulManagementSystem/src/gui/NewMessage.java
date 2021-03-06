package gui;

import java.net.URL;
import java.util.LinkedList;
import objects.Nachricht;


import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

import control.Controller;

public class NewMessage extends Startseite implements Button.ClickListener {
	
	private Window newMess;  
	private AbsoluteLayout mainLayout;
	private Label label;
	ListSelect nachricht;
	private Button anzeigen,logout,back, delMessage;
	private URL oldURL;
	private LinkedList<Nachricht>lis;
	Label text;
	LoginApplication starta;
	
	public NewMessage(LinkedList<Nachricht>lis, Controller d){
		super(d);
		 starta =cont.getStart();
		this.lis = lis;
		
		Window test = starta.getWindow("Nachrichten");
		if(test != null){
			starta.removeWindow(test);	
		}
		newMess = new Window("");
		newMess.setName("Nachrichten");
		starta.addWindow(newMess);
				
		openNewMessages();
		buildMainLayout();
		newMess.setContent(mainLayout);
		Window old = starta.getWindow("Startseite");
		oldURL = old.getURL();
		old.open(new ExternalResource(newMess.getURL()));
	}
	
	public void openNewMessages() {
		
		nachricht = new ListSelect();
		
		for(int i=0; i < lis.size(); i++){
			int u = i +1;
			nachricht.addItem(u + ": " + lis.get(i).getBetreff());		
		}
		nachricht.setNullSelectionAllowed(false);
	}
	
	public void buttonClick(Button.ClickEvent event){
		try{
			if(event.getButton() == anzeigen){
				String tmp = nachricht.getValue().toString();
				String[] splitResult = tmp.split(":");
				int listnr = Integer.parseInt(splitResult[0])-1;
				InfoWindow messageW = new InfoWindow(lis.get(listnr).getBetreff(),lis.get(listnr).getbeschreibung(),newMess);
			}
		}
		catch(NullPointerException e){
		}
		if(event.getButton()== logout){
		       starta.getMainWindow().getApplication().close();				
		}
		if(event.getButton()== delMessage){
			try{
				String tmp = nachricht.getValue().toString();
				String[] splitResult = tmp.split(":");
				int listnr = Integer.parseInt(splitResult[0])-1;
				boolean del = cont.deleteMessage(lis.get(listnr).getid());
				if(del){ nachricht.removeItem(nachricht.getValue().toString());
				}
			}
			catch(NullPointerException e){
			}
		}
		if(event.getButton()==back){
			newMess.open(new ExternalResource(oldURL));
		}
	}
	
	private AbsoluteLayout buildMainLayout() {
		
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		mainLayout.setWidth("100.0%");
		mainLayout.setHeight("100.0%");
		
		// label
		label = new Label();
		label.setImmediate(false);
		label.setWidth("-1px");
		label.setHeight("-1px");
		label.setValue("Neue Nachrichten");
		label.setStyleName(Runo.LABEL_H1);
		mainLayout.addComponent(label, "top:25.0%;left:35.0%;");
		
		// benutzer		
		nachricht.setImmediate(false);
		nachricht.setWidth("46.0%");
		nachricht.setHeight("70.0%");
		mainLayout.addComponent(nachricht, "top:35.0%;left:35.0%;");
		
		// ok
		anzeigen = new Button();
		anzeigen.setCaption("ansehen");
		anzeigen.setImmediate(false);
		anzeigen.setWidth("-1px");
		anzeigen.setHeight("-1px");
		anzeigen.addListener(this);
		mainLayout.addComponent(anzeigen, "top:83.0%;left:35.0%;");
		
		//delMessage
		delMessage = new Button();
		delMessage.setCaption("loeschen");
		delMessage.setImmediate(false);
		delMessage.setWidth("-1px");
		delMessage.setHeight("-1px");
		delMessage.addListener(this);
		mainLayout.addComponent(delMessage, "top:88.0%;left:35.0%;");
		
		// logout
		logout = new Button();
		logout.setCaption("logout");
		logout.setImmediate(false);
		logout.setWidth("-1px");
		logout.setHeight("-1px");
		logout.setStyleName(BaseTheme.BUTTON_LINK);
		logout.addListener(this);
		mainLayout.addComponent(logout, "top:97.0%;left:35.0%;");
		
		// back
		back = new Button();
		back.setCaption("Startseite");
		back.setImmediate(true);
		back.setWidth("-1px");
		back.setHeight("-1px");
		back.setStyleName(BaseTheme.BUTTON_LINK);
		back.addListener(this);
		mainLayout.addComponent(back, "top:94.0%;left:35.0%;");
		
		return mainLayout;
	}
}

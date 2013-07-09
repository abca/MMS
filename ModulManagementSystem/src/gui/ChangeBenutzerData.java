package gui;

import java.net.URL;

import objects.Benutzer;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

import control.Controller;
import control.Password;

public class ChangeBenutzerData extends Startseite implements Button.ClickListener{
	
	Window change;  
	Label label;
	PasswordField oldPass, newPass, newPass1;
	TextField uName;
	Button save,back;
	Benutzer tmp1;
	private AbsoluteLayout mainLayout;
	private URL oldURL;
	LoginApplication starta;

	public ChangeBenutzerData(Benutzer tmp,Controller d){
		super(d);
		 starta =cont.getStart();
		tmp1 = tmp; 
		Window test = starta.getWindow("AenderungBenutzer");
		if(test != null){
			starta.removeWindow(test);	
		}
	
		change = new Window("");
		buildMainLayout();
		change.setContent(mainLayout);
		
		save.addListener(this);
		logout.addListener(this);
		starta.addWindow(change);
		
		Window old = cont.login.getWindow("Startseite");
		oldURL = old.getURL();
		old.open(new ExternalResource(change.getURL()));
	}
	
	public void buttonClick(ClickEvent event){
		
		if(event.getButton() == save){
			String name = (String)uName.getValue();
			String passOld = (String)oldPass.getValue();
			String p1 = (String)newPass.getValue();
			String p2 = (String)newPass1.getValue();
			if(name.equals("")||((passOld.equals("")||(p1.equals("")||(p2.equals("")))))){
				displayError("Füllen Sie bitte alle Felder aus ");
			}
			else if((new Password()).verifyPassword(passOld, tmp1.getPw())){
				if(p2.equals(p1)){				
					
					Benutzer Neu = new Benutzer(tmp1.getId(), name, p1, tmp1.isDozent(),
									tmp1.isDekan(), tmp1.isAdmin(), tmp1.isStell()
										, tmp1.getStellid());	
					cont.changeBenutzer(Neu);
					change.open(new ExternalResource(oldURL));
					}else{
					displayError("Passwörter stimmen nicht überein");		
					}			
			}else{	
				displayError("Altes Passwort stimmt nicht");	
			}
		}
		if(event.getButton()== logout){
		       starta.getMainWindow().getApplication().close();			
		}	
		if(event.getButton()==back){
			change.open(new ExternalResource(oldURL));
		}
	}
	
	private void displayError(String text) {
		
			InfoWindow error = new InfoWindow("Fehler",text,change);
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
		label.setValue("Benutzerdaten ändern");
		label.setStyleName(Runo.LABEL_H1);
		mainLayout.addComponent(label, "top:25.0%;left:35.0%;");
		
		// save
		save = new Button();
		save.setCaption("speichern");
		save.setImmediate(true);
		save.setWidth("-1px");
		save.setHeight("-1px");
		mainLayout.addComponent(save, "top:67.0%;left:35.0%;");
		
		// back
		back = new Button();
		back.setCaption("Startseite");
		back.setImmediate(true);
		back.setWidth("-1px");
		back.setHeight("-1px");
		back.setStyleName(BaseTheme.BUTTON_LINK);
		back.addListener(this);
		mainLayout.addComponent(back, "top:73.0%;left:35.0%;");
		
		// logout
		logout = new Button();
		logout.setCaption("logout");
		logout.setImmediate(true);
		logout.setWidth("-1px");
		logout.setHeight("-1px");
		logout.setStyleName(BaseTheme.BUTTON_LINK);
		mainLayout.addComponent(logout, "top:77.0%;left:35.0%;");
		
		// uName
		uName = new TextField();
		uName.setCaption("Benutzername");
		uName.setValue(tmp1.getName());
		uName.setImmediate(false);
		uName.setWidth("25.0%");
		uName.setHeight("-1px");
		mainLayout.addComponent(uName, "top:35.0%;left:35.0%;");
		
		// oldPass
		oldPass = new PasswordField();
		oldPass.setCaption("altes Passwort");
		oldPass.setImmediate(false);
		oldPass.setWidth("25.0%");
		oldPass.setHeight("-1px");
		mainLayout.addComponent(oldPass, "top:43.0%;left:35.0%;");
		
		// newPass
		newPass = new PasswordField();
		newPass.setCaption("neues Passwort");
		newPass.setImmediate(false);
		newPass.setWidth("25.0%");
		newPass.setHeight("-1px");
		mainLayout.addComponent(newPass, "top:51.0%;left:35.0%;");
		
		// newPass1
		newPass1 = new PasswordField();
		newPass1.setCaption("neues Passwort bestätigen");
		newPass1.setImmediate(false);
		newPass1.setWidth("-1px");
		newPass1.setHeight("-1px");
		mainLayout.addComponent(newPass1, "top:59.0%;left:35.0%;");
		
		return mainLayout;
	}
}

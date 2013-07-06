package gui;

import com.vaadin.Application;
import com.vaadin.terminal.ClassResource;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

import control.Controller;

/**
 * Main application class.
 */

public class LoginApplication extends Application implements Button.ClickListener {
	
	private Button login, save, modul, register;
	Window mainWindow, modulW, errW, selErrW ,adminWindow, regW;  
	Controller control = new Controller(this);
	private VerticalLayout vertical;
	private AbsoluteLayout mainLayout;
	private Label label;
	Label user, wrong, wrong2, userName, userMail, regErr;
	TextField username, uName, uMail;
	PasswordField pass1, pass2, password;
	ListSelect modules;
	String read;
	UserRightAdministration test;
	public LoginApplication start;
	
    public void init() {
   	
        mainWindow = new Window("");
        mainWindow.setName("main");
        
        buildMainLayout();
        mainWindow.setContent(mainLayout);
        
        setMainWindow(mainWindow);
        login.addListener(this);     
        register.addListener(this);
        modul.addListener(this);
        start = this;
    }
    
    //ButtonListener
    public  void buttonClick(Button.ClickEvent event) {
  
    	if (event.getButton() == login) {
    		String us = username.getValue().toString();
    		
			String pw = password.getValue().toString();
	
			control.login(us, pw, this);			
    	}
    	if (event.getButton() == register) {
    		register();
    	}
    	if (event.getButton() == modul) {
    		control.requestModule(this);
    	}
        
    	if(event.getButton() == save) {
    		String us = uName.getValue().toString();
    		String em = uMail.getValue().toString();
    		String p1 = pass1.getValue().toString();
    		String p2 = pass2.getValue().toString(); 
    		
    		if (us.equals("")||em.equals("")||p1.equals("")||p2.equals("") ||!(p1.equals(p2))
    				||us.length()>50||em.length()>50||p1.length()>10) {
    			registerError();
    		}
    		else if (control.doesNameExist(us)){
    			InfoWindow nameEx = new InfoWindow("Fehler","Dieser Nutzername existiert bereits",mainWindow);
    		}else {
    		
    		control.register(us, em, p1);
    		mainWindow.removeWindow(regW);
    		}
    	}
    }
    
    //Fehlerfenster für falschen Username/falsches Passwort
    public void displayError() {
    	
    		InfoWindow test = new InfoWindow("Fehler","Nutzername oder Passwort falsch",mainWindow);    		
    }
    
    //Popup für Registration
    public void register() {
    	
    	regW = new Window("Registrieren");
    	userName = new Label("Username");
    	userMail = new Label("email-addresse");
    	pass1 = new PasswordField("Passwort eingeben");
    	pass2 = new PasswordField("Passwort bestätigen");
    	uName = new TextField();
    	uMail = new TextField();
    	save = new Button("speichern");
    	Layout reg = new VerticalLayout();
    	
    	regW.setContent(reg);
    	regW.addComponent(userName);
    	regW.addComponent(uName);
    	regW.addComponent(userMail);
    	regW.addComponent(uMail);
    	regW.addComponent(pass1);
    	regW.addComponent(pass2);
    	regW.addComponent(save);
    	mainWindow.addWindow(regW);
    	regW.setHeight("300px");
    	regW.setWidth("200px");
    	regW.center();
    	save.addListener(this);
    }
    
    //Failsafe für die Registration
    public void registerError() {
    	
    	InfoWindow info = new InfoWindow("Fehler","Die eingegebenen Daten sind unvollständig oder zu lang",mainWindow);    	
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
		label.setValue("Modul Management System");
		label.setStyleName(Runo.LABEL_H1);
		mainLayout.addComponent(label, "top:25.0%;left:30.0%;");
		
		// login
		login = new Button();
		login.setCaption("Login");
		login.setImmediate(true);
		login.setWidth("-1px");
		login.setHeight("-1px");
		mainLayout.addComponent(login, "top:52.0%;left:52.0%;");
		
		// register
		register = new Button();
		register.setCaption("Registrieren");
		register.setImmediate(true);
		register.setWidth("-1px");
		register.setHeight("-1px");
		register.setStyleName(BaseTheme.BUTTON_LINK);
		mainLayout.addComponent(register, "top:60.0%;left:30.0%;");
		
		// modul
		modul = new Button();
		modul.setCaption("Module ansehen");
		modul.setImmediate(true);
		modul.setWidth("-1px");
		modul.setHeight("-1px");
		modul.setStyleName(BaseTheme.BUTTON_LINK);
		mainLayout.addComponent(modul, "top:65.0%;left:30.0%;");
		
		// username
		username = new TextField();
		username.setCaption("Benutzername");
		username.setImmediate(false);
		username.setWidth("25.0%");
		username.setHeight("-1px");
		mainLayout.addComponent(username, "top:43.0%;left:30.0%;");
		
		// password
		password = new PasswordField();
		password.setCaption("Passwort");
		password.setImmediate(false);
		password.setWidth("25.0%");
		password.setHeight("-1px");
		mainLayout.addComponent(password, "top:52.0%;left:30.0%;");
		
		return mainLayout;
	}
}


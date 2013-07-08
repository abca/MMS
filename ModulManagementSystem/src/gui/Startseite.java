package gui;

import objects.Benutzer;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

import control.Controller;
import control.ControllerDekan;
import control.ControllerDozent;

public class Startseite implements Button.ClickListener{
	Window start;
	Label welcome;
	Button changeModule, declareDeputy, messages, changes, stichtag, changeRights, changeBenutzer, nullButton, logout, organize, archives ;
	private AbsoluteLayout mainLayout;
	private int count;//Zähler für Buttons
	private LoginApplication starta;
	protected Startseite seite;
	
	private int userid;
	Controller cont;

	
	//übergibt Hauptwindow
	public Startseite(Controller d){
		cont =d;
	}
	
	
	public Startseite (LoginApplication aa, int id, Window old,Controller con){
		
		starta = aa;
		seite = this;
		cont = con;
		String name = cont.loadBenutzer(id).getName(); 
		Window test = starta.getWindow("Startseite");
		if(test != null){
			starta.removeWindow(test);	
		}
		
		start= new Window("Startseite");
		start.setName("Startseite");
		starta.addWindow(start);		
		
		userid = id;
		count = 0;
		nullButton = new Button();
		welcome = new Label("Willkommen "+name);
			
		/*Abfrage, welchen Rang oder welche Ränge der Nutzer
		besitzt, auch als Stellvertreter und dementsprechende 
		Gestaltung der Oberfläche: Die instanziierten Buttons
		werden später dem Layout geaddet.*/		
		if(cont.getAdmin(userid)){			
			changeRights = new Button("Nutzerrechte verwalten");
			changeRights.addListener(this);
		}
		if(cont.getDozent(userid)){
			changeModule = new Button("Modul ändern/erstellen");
			changeModule.addListener(this);
			declareDeputy = new Button("Stellvertreter ernennen");
			declareDeputy.addListener(this);
			messages = new Button("Benachrichtigungen");
			messages.addListener(this);			
		}
		//doppelte Buttons werden nicht erneut instanziiert
		if(cont.getDekan(userid)){
			if(!cont.getDozent(userid)){
				changeModule = new Button("Modul ändern/erstellen");
				changeModule.addListener(this);
				declareDeputy = new Button("Stellvertreter ernennen");
				declareDeputy.addListener(this);
				messages = new Button("Benachrichtigungen");
				messages.addListener(this);
			}
			organize = new Button("Modulhandbuch organisieren");
			organize.addListener(this);
			stichtag = new Button("Stichtag und Archivierung");
			stichtag.addListener(this);
			archives = new Button ("Archiv");
			archives.addListener(this);
			changes = new Button("aktuelle Änderungen");
			changes.addListener(this);			
		}	
		changeBenutzer = new Button("Persönliche Daten ändern");
		changeBenutzer.addListener(this);
		
		logout = new Button("logout");
		logout.addListener(this);
		
		cont = new Controller(starta,userid);
		ControllerDozent contD = new ControllerDozent(starta,userid,cont);
		ControllerDekan contDek = new ControllerDekan(starta,userid,cont);
		cont.setController(contD, contDek);
		
		mainLayout = buildMainLayout();
		start.setContent(mainLayout);
		old.open(new ExternalResource(start.getURL()));
		
	}

	private AbsoluteLayout buildMainLayout() {
		
		Button button_1,button_2,button_3,button_4,button_5,button_6,button_7,button_8,button_9,button_10;
		
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		mainLayout.setWidth("100.0%");
		mainLayout.setHeight("100.0%");
		
			
		// welcome
		welcome.setImmediate(false);
		welcome.setWidth("-1px");
		welcome.setHeight("-1px");	
		welcome.setStyleName(Runo.LABEL_H1);
		mainLayout.addComponent(welcome, "top:25.0%;left:35.0%;");
		
		// button_1
		button_1 = getButton();
		if(button_1!=nullButton){			
			button_1.setWidth("-1px");
			button_1.setHeight("-1px");
			button_1.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_1, "top:35.0%;left:35.0%;");
		}	
		
		// button_2
		button_2 = getButton();
		if(button_2!=nullButton){
			button_2.setWidth("-1px");
			button_2.setHeight("-1px");
			button_2.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_2, "top:40.0%;left:35.0%;");
		}
		
		// button_3
		button_3 = getButton();
		if(button_3!=nullButton){
			button_3.setWidth("-1px");
			button_3.setHeight("-1px");
			button_3.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_3, "top:45.0%;left:35.0%;");
		}
		
		// button_4
		button_4 = getButton();
		if(button_4!=nullButton){
			button_4.setWidth("-1px");
			button_4.setHeight("-1px");
			button_4.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_4, "top:50.0%;left:35.0%;");
		}
		
		// button_5
		button_5 = getButton();
		if(button_5!=nullButton){					
			button_5.setWidth("-1px");
			button_5.setHeight("-1px");
			button_5.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_5, "top:55.0%;left:35.0%;");
		}
		
		// button_6
		button_6 = getButton();
		if(button_6!=nullButton){
			button_6.setWidth("-1px");
			button_6.setHeight("-1px");
			button_6.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_6, "top:60.0%;left:35.0%;");
		}
		
		// button_7
		button_7 = getButton();
		if(button_7!=nullButton){
			button_7.setWidth("-1px");
			button_7.setHeight("-1px");
			button_7.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_7, "top:65.0%;left:35.0%;");
		}
		
		// button_8
		button_8 = getButton();
		if(button_8!=nullButton){
			button_8.setWidth("-1px");
			button_8.setHeight("-1px");
			button_8.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_8, "top:70.0%;left:35.0%;");
		}
		
		// button_9
		button_9 = getButton();  
		if(button_9!=nullButton){
			button_9.setWidth("-1px");
			button_9.setHeight("-1px");
			button_9.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_9, "top:75.0%;left:35.0%;");
			
		}
		
		// button_10
		button_10 = getButton();  
		if(button_10!=nullButton){
			button_10.setWidth("-1px");
			button_10.setHeight("-1px");
			button_10.setStyleName(BaseTheme.BUTTON_LINK);
			mainLayout.addComponent(button_10, "top:80.0%;left:35.0%;");
			
		}

		return mainLayout;
	}
	
	//ButtonListener
	public void buttonClick(ClickEvent event) {
		
		if(event.getButton() == changeModule){
			cont.getcDo().ausgebenModulList(userid);
			//Dozent, Dekan
		}
		
		if(event.getButton() == changes){
			//Dekan
			cont.getcDe().loadRequestlist();
		}
		
		if(event.getButton() == declareDeputy){
			//Dozent, Dekan
			SetDeputy dep = new SetDeputy(cont);
		}
		
		if(event.getButton() == messages){
			cont.loadNewMessageList();//Dozent
		}
		
		if(event.getButton()== changeRights){
			//Administrator
			UserRightAdministration b = new UserRightAdministration(cont);
		}
		
		if(event.getButton()==stichtag){
			DeadLine line = new DeadLine(cont);
			//Dekan
		}
		if(event.getButton()== logout){
	       starta.getMainWindow().getApplication().close();
			
		}
		if(event.getButton() == changeBenutzer){
			Benutzer neu = cont.loadBenutzer(userid);
			ChangeBenutzerData data = new ChangeBenutzerData(neu, cont);
		}
		if(event.getButton() == archives){
			cont.getcDe().archivListeAusgeben();
		}
		
		if (event.getButton() == organize) {
			cont.getcDe().requestModule();
		}
	}
	
	//liefert zurück, welcher Button noch fehlt
	public Button getButton(){
		
		//count gibt an, welcher Button geprüft wird	
		if (count==0) {		
			count++;
			if(changeModule!=null) return changeModule;				
		}
		if (count==1){
			count++;
			if(declareDeputy!=null) return declareDeputy;			
		}
		if (count==2){
			count++;
			if(messages!=null) return messages;			
		}
		if (count==3){
			count++;
			if(changes!=null) return changes;			
		}
		if (count==4){
			count++;
			if(organize!=null) return organize;			
		}
		if (count==5){
			count++;
			if(changeRights!=null) return changeRights;			
		}
		
		if (count==6){
			count++;
			if(stichtag!=null) return stichtag;			
		}
		if (count==7){
			count++;
			if(archives!=null) return archives;
		}
		if(count==8){
			count++;
			if(changeBenutzer!=null) return changeBenutzer;
		}
		if (count==9){
			count++;
			if(logout!=null) return logout;
			
		}
		return nullButton;
	}
}

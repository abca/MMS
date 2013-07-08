package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.LinkedList;

import com.vaadin.event.*;
import com.vaadin.Application;

import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

import control.Controller;

public class UserRightAdministration extends Startseite implements Button.ClickListener {
	
	private Button speichern, aendern,logout, back, deleteUser, swapRes;
	private URL oldURL;
	private Label label;
	OptionGroup group;
	Window auswahlW, admin, swapResW;   
	private AbsoluteLayout mainLayout;
	ListSelect benutzer, dekan;
	String aus;
	LoginApplication starta;
	int delUser;
	
	//übergibt Hauptwindow
	public UserRightAdministration(Controller d) {
		super(d);
		starta =cont.getStart();
		Window test = starta.getWindow("adminWindow");
		if(test != null){
			starta.removeWindow(test);	
		}
		admin = new Window("");
	    admin.setName("adminWindow");
	    starta.addWindow(admin);		//aktuelles window wird zum Hauptwindow geaddet
	    
		openBenutzerListe(cont.benutzerListeAusgeben());
		
		buildMainLayout();
		admin.setContent(mainLayout);
		
		Window old = starta.getWindow("Startseite");
		oldURL = old.getURL();
		old.open(new ExternalResource(admin.getURL()));
	}

	//öffnet Fenster in dem Ränge verteilt werden
	public void openRangWindow(String Rang){
		
		auswahlW = new Window("Rang auswählen");
		Layout lay = new VerticalLayout();
		auswahlW.addComponent(lay);
		auswahlW.setHeight("200px");
		auswahlW.setWidth("400px");
		
		group = new OptionGroup("");	//Radio-Buttons
		group.setMultiSelect(true);		//mehrere Optionen können gleichzeitig ausgew�hlt sein
		
		group.addItem("Dozent");
		group.addItem("Dekan");
		if (Rang == "Benutzer" ){
			//kein Rang verteilt
		}
		else{
			if(Rang.contains("Dozent")) group.select("Dozent");
			if(Rang.contains("Dekan")) group.select("Dekan");
		}
		lay.addComponent(group);
		speichern = new Button ("speichern");
		speichern.addListener(this);	
		lay.addComponent(speichern);
		admin.addWindow(auswahlW);	
	}
	
	//Listener für die Buttons
	public void buttonClick (Button.ClickEvent event) {
		
		if (event.getButton() == speichern){
			String ausw = this.getAuswahl();
			admin.removeWindow (auswahlW);
			cont.aenderungSpeichern(aus, ausw);		//Methode in Controller-Klasse
		}
		if (event.getButton() == deleteUser){
			InfoWindow info;
			delUser = cont.getID((String)benutzer.getValue());
			if(benutzer.getValue()==null)selectError();
			else if(delUser==cont.getUserID())
				info = new InfoWindow("Fehler","Sie können sich nicht selbst löschen",admin);
			else if(cont.getDekan(delUser)){
				swapWindow();
			}
			else{
				cont.deleteUser(benutzer.getValue().toString());
				benutzer.removeItem(benutzer.getValue().toString());
			}
		}
		if(event.getButton() == aendern){
			try {
				aus = benutzer.getValue().toString();
			}
			catch (NullPointerException e){
				aus = "";
			}
	
			if(aus == ""){
				selectError(); //"Fehlermeldung" ;)
			}
			else{
				String blub = cont.rangAusgeben(aus);			
				openRangWindow(blub);
			}
		}
		if(event.getButton() == logout){
			starta.getMainWindow().getApplication().close();
		}
		if(event.getButton()==back){
			admin.open(new ExternalResource(oldURL));
		}
		if(event.getButton()==swapRes){
			try{
				cont.swapRespon(cont.getID((String)dekan.getValue()), delUser);
				cont.deleteUser(benutzer.getValue().toString());
				benutzer.removeItem(benutzer.getValue().toString());
				admin.removeWindow(swapResW);
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			
		}
	}
	
	public void swapWindow() {
		
		swapResW = new Window("Dekan zur Übernahme der Module wählen");
		String[] dekanliste = cont.scanForDekan();
		openDekanListe(dekanliste);
		swapResW.addComponent(dekan);
		swapRes = new Button("übertragen");
		swapRes.addListener(this);
		swapResW.addComponent(swapRes);
		swapResW.setHeight("400px");
		swapResW.setWidth("300 px");
		admin.addWindow(swapResW);
	}
	
	public void openDekanListe(String [] liste){
		
		dekan = new ListSelect();
		
		for(int i = 0; i < liste.length; i++){
				dekan.addItem(liste[i]);			//geht durch Liste durch und addet Benutzer
		}
		
		dekan.setNullSelectionAllowed(false);	//leere Auswahl ist nicht erlaubt				
	}

	//schreibt getroffene Rangauswahl in einen String um
	public String getAuswahl(){
			
	String ff = group.getValue().toString();	
		return ff;
	}
	
	//Benutzerliste wird ausgegeben, aus der ein Benutzer ausgewählt werden kann
	public void openBenutzerListe(String [] liste){
		
		benutzer = new ListSelect();
		
		for(int i = 0; i < liste.length; i++){
				benutzer.addItem(liste[i]);			//geht durch Liste durch und addet Benutzer
				
		}
		
		benutzer.setNullSelectionAllowed(false);	//leere Auswahl ist nicht erlaubt				
	}
	
	//Fehlerfenster falls keinUser ausgewählt wurde
	public void selectError() {     
		
		InfoWindow errW = new InfoWindow("Fehler","Wählen Sie bitte einen Nutzer aus",admin);
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
		label.setValue("Nutzerrechtverwaltung");
		label.setStyleName(Runo.LABEL_H1);
		mainLayout.addComponent(label, "top:25.0%;left:35.0%;");
		
		// benutzer		
		benutzer.setImmediate(false);
		benutzer.setWidth("46.0%");
		benutzer.setHeight("70.0%");
		mainLayout.addComponent(benutzer, "top:35.0%;left:35.0%;");
		
		// dekan
		dekan.setImmediate(false);
		dekan.setWidth("46.0%");
		dekan.setHeight("70.0%");
		mainLayout.addComponent(dekan, "top:35.0%;left:35.0%;");
		
		// aendern
		aendern = new Button();
		aendern.setCaption("Nutzerrechte ändern");
		aendern.setImmediate(true);
		aendern.setWidth("-1px");
		aendern.setHeight("-1px");		
		aendern.addListener(this);
		mainLayout.addComponent(aendern, "top:83.0%;left:35.0%;");
		
		// deleteUser
		deleteUser = new Button();
		deleteUser.setCaption("Nutzer löschen");
		deleteUser.setImmediate(true);
		deleteUser.setWidth("-1px");
		deleteUser.setHeight("-1px");		
		deleteUser.addListener(this);
		mainLayout.addComponent(deleteUser, "top:88.0%;left:35.0%;");
		
		// logout
		logout = new Button();
		logout.setCaption("logout");
		logout.setImmediate(true);
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

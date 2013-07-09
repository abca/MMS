package gui;

import java.net.URL;

import java.util.LinkedList;

import objects.ModulKu;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

import control.Controller;

public class ModulReview extends Startseite implements Button.ClickListener{
	
	String savestr;	
	Window modEdCr; 
	public ListSelect module; 
	private AbsoluteLayout mainLayout;
	private Button create, aendern,back;
	private URL oldURL;
	private LinkedList<ModulKu> liste;
	private Label label;
	LoginApplication starta;
	
	public ModulReview(LinkedList<ModulKu> _liste, Controller d){
		super(d);
		 starta =cont.getStart();
		Window test = starta.getWindow("Modulbearbeiten");
		if(test != null){
			starta.removeWindow(test);	
		}
		
		liste = _liste;
		modEdCr = new Window("");
		modEdCr.setName("Modulbearbeiten");
		starta.addWindow(modEdCr);
	
		module = openModulList(liste);
		buildMainLayout();
		modEdCr.setContent(mainLayout);
		
		Window old = starta.getWindow("Startseite");
		oldURL = old.getURL();
		old.open(new ExternalResource(modEdCr.getURL()));
	}
	
	//ButtonListener
	public void buttonClick(Button.ClickEvent event){
	
		if(event.getButton() == create){
			int modul = 0;
			cont.getcDo().ändernModul(modul,this);
		}
			
		if(event.getButton()== logout){
		       starta.getMainWindow().getApplication().close();				
		}
		if(event.getButton()==back){
			modEdCr.open(new ExternalResource(oldURL));
		}
		
		if(event.getButton() == aendern){
			int modul =0;
			try{
				String tmp = module.getValue().toString();
				
				for(int i=0; i < liste.size(); i++){
				
					if(tmp.equals(liste.get(i).gettitle())){
						modul = liste.get(i).getid();
						break;
					}
				}
				cont.getcDo().ändernModul(modul,this);
			}
			catch(NullPointerException e){
			}			
		}
			
	}
	
	//erstellt Listselect-Element mit Modulliste
	public ListSelect openModulList(LinkedList<ModulKu> modList) {
	
		ListSelect test = new ListSelect();
	
		for(int i=0; i < modList.size(); i++){
			test.addItem(modList.get(i).gettitle());			
		}
		test.setNullSelectionAllowed(false);
		return test;
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
		label.setValue("Modul ändern oder bearbeiten");
		label.setStyleName(Runo.LABEL_H1);
		mainLayout.addComponent(label, "top:25.0%;left:35.0%;");
		
		// module
		module.setImmediate(false);
		module.setWidth("46.0%");
		module.setHeight("70.0%");
		mainLayout.addComponent(module, "top:35.0%;left:35.0%;");
		
		// create
		create = new Button();
		create.setCaption("Modul erstellen");
		create.setImmediate(true);
		create.setWidth("25.0%");
		create.setHeight("-1px");
		create.addListener(this);		
		mainLayout.addComponent(create, "top:88.0%;left:35.0%;");
		
		// logout
		logout = new Button();
		logout.setCaption("logout");
		logout.setImmediate(true);
		logout.setWidth("-1px");
		logout.setHeight("-1px");
		logout.addListener(this);
		logout.setStyleName(BaseTheme.BUTTON_LINK);
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
		
		// aendern
		aendern = new Button();
		aendern.setCaption("Modul ändern");
		aendern.setImmediate(false);
		aendern.setWidth("25.0%");
		aendern.setHeight("-1px");
		aendern.addListener(this);
		mainLayout.addComponent(aendern, "top:83.0%;left:35.0%;");
		
		return mainLayout;
	}
	
	public void displayError(String text) {
		InfoWindow error = new InfoWindow("Fehler",text,modEdCr);
	}
}


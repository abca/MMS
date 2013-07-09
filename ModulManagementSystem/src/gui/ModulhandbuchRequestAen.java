package gui;

import java.net.URL;
import java.util.LinkedList;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

import control.Controller;
import control.HandbookManager;

public class ModulhandbuchRequestAen extends Startseite implements Button.ClickListener {
	
	private Window mod, newBook; 
	private Button recommend,back, create, okay2, deleteHandbook;
	private Label label;
	private AbsoluteLayout mainLayout;
	private String [] tmp;
	private LinkedList<Integer> tmp2;
	private URL oldURL;
	ListSelect modules;
	TextField name;
	LoginApplication starta;
	
	public ModulhandbuchRequestAen( LinkedList<Integer> ids, String[] list, Controller d) {
		super(d);
		 starta =cont.getStart();
		Window test = starta.getWindow("M");
		if(test != null){
			starta.removeWindow(test);	
		}
		tmp = list;
		tmp2 = ids;
		mod = new Window("");
		mod.setName("M");
		starta.addWindow(mod);

		openModuleList(list);
		buildMainLayout();
		recommend.addListener(this);
		create.addListener(this);
		deleteHandbook.addListener(this);
		mod.setContent(mainLayout);			
		Window old = starta.getWindow("Startseite");
		oldURL = old.getURL();
		old.open(new ExternalResource(mod.getURL()));
	}
    
    //erstellt ListSelect-Element und fügt es in das Modulfenster (siehe moduleList())
    public void openModuleList(String[] list) {
    	
    	modules = new ListSelect();
		
    	for(int i=0; i < list.length; i++){
			modules.addItem(list[i]);			
		}
		
		modules.setNullSelectionAllowed(false);
		mod.addComponent(modules);
    }
    
    //ButtonListener
    public  void buttonClick(Button.ClickEvent event) {
    	
    	String read;
    	if (event.getButton() == recommend) {
 
				read =(String) modules.getValue();
				int modul = 0;
				try {
					for (int i = 0; i < tmp.length; i++) {
						if (read.equals(tmp[i])) {
							modul = tmp2.get(i);
						}
					}
				} catch (NullPointerException e) {
					
				}
				HandbookManager hbm;
				if(modul!=0)
					hbm = new HandbookManager(modul, cont);
				else
					displaySelectionError();
    	}
    	if(event.getButton()== logout){
    		starta.getMainWindow().getApplication().close();      
    	}    	
    	if(event.getButton()==back){
			Startseite starte = new Startseite(cont.getStart(),cont.getUserID(),mod,cont);
    	}
    	if(event.getButton() == create){
    		displayNewBook();
    	}
    	if(event.getButton() == okay2){
    		String name1 =(String) name.getValue();
    		InfoWindow err;
    		if(name1.equals(""))
    			err = new InfoWindow("Fehler","Geben Sie bitte einen Namen ein",mod);
    		else if(name1.length()>50)
    			err = new InfoWindow("Fehler","Der Handbuchname ist zu lang",mod);
    		else if(!cont.doesHandbookNameExist(name1)){
    			cont.getcDe().saveHandbook(name1);
    			mod.removeWindow(newBook);
    			cont.getcDe().requestModule();//Seite wird neu geladen
    		}
    		else{
    			err = new InfoWindow("Fehler","Dieses Modulhandbuch gibt es bereits",mod);
    		} 		
    	}
    	if (event.getButton() == deleteHandbook) {		
			boolean delete = false;
			String selected = "";
			try {
				selected = modules.getValue().toString();
				delete = true;
			} catch (NullPointerException e) {
				delete = false;
			}

			if(delete){
				cont.deleteHandbook(selected);
				modules.removeItem(selected);
			}
			else
				displaySelectionError();
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
    	    label.setValue("Modulhandbuch auswählen");
    	    label.setStyleName(Runo.LABEL_H1);
    	    mainLayout.addComponent(label, "top:25.0%;left:35.0%;");
    	    
    	    // modules    
    	    modules.setImmediate(false);
    	    modules.setWidth("46.0%");
    	    modules.setHeight("70.0%");
    	    mainLayout.addComponent(modules, "top:35.0%;left:35.0%;");
    	    
    	    // recommend
    	    recommend = new Button();
    	    recommend.setCaption("ansehen");
    	    recommend.setImmediate(true);
    	    recommend.setWidth("-1px");
    	    recommend.setHeight("-1px");
    	    mainLayout.addComponent(recommend, "top:83.0%;left:35.0%;");
    	    
    	    //löschen
    	    deleteHandbook = new Button();
    	    deleteHandbook.setCaption("löschen");
    	    deleteHandbook.setImmediate(true);
    	    deleteHandbook.setWidth("-1px");
    	    deleteHandbook.setHeight("-1px");
    	    mainLayout.addComponent(deleteHandbook, "top:83.0%;left:45.0%;");
    	    
    	    // create
    	    create = new Button();
    	    create.setCaption("Neues Modulhandbuch");
    	    create.setImmediate(true);
    	    create.setWidth("-1px");
    	    create.setHeight("-1px");
    	    mainLayout.addComponent(create,  "top:88.0%;left:35.0%;");
    	    
    	    // logout
    	    back = new Button();
    	    back.setCaption("Startseite");
    	    back.setImmediate(true);
    	    back.setWidth("-1px");
    	    back.setHeight("-1px");
    	    back.setStyleName(BaseTheme.BUTTON_LINK);
    	    back.addListener(this);
    	    mainLayout.addComponent(back,"top:93.0%;left:35.0%;");
    	    
    	    // logout
    	    logout = new Button();
    	    logout.setCaption("logout");
    	    logout.setImmediate(true);
    	    logout.setWidth("-1px");
    	    logout.setHeight("-1px");
    	    logout.setStyleName(BaseTheme.BUTTON_LINK);
    	    logout.addListener(this);
    	    mainLayout.addComponent(logout,"top:96.5%;left:35.0%;");
    	    
    	    return mainLayout;
    	}
    
    //Fehlerfenster wenn kein Modul ausgewählt wird
    public void displayNewBook() {
    	
    	newBook = new Window("Handbuch erstellen");
    	okay2 = new Button("Ok");
    	Label wrong2 = new Label("Geben sie den Namen des Handbuches ein:");
		name = new TextField();
    	Layout selError = new VerticalLayout();
    	okay2.addListener(this);
    	
    	newBook.setContent(selError);
    	newBook.addComponent(wrong2);
    	newBook.addComponent(name);
    	newBook.addComponent(okay2);
    	mod.addWindow(newBook);
    	newBook.setHeight("200px");
    	newBook.setWidth("200px");  	
    	newBook.center();
    }
    	
    	
    public void displaySelectionError() {    	
    	InfoWindow error = new InfoWindow("Fehler","Wählen Sie bitte ein Modulhandbuch aus",mod);
    }
}

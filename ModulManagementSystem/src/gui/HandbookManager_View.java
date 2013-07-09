package gui;

import java.net.URL;
import java.util.LinkedList;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

import control.Controller;
import control.ModuleTable;
import control.UnassignedModulesTable;
import control.ModuleTree;

import data.BookData;
import data.ModulDatabase;

public class HandbookManager_View extends Startseite implements Button.ClickListener{
	
	private Panel pan;
	private AbsoluteLayout mainLayout;
	private Label label;
	private Button back;
	private URL oldURL;
	
	public Window w;
	public Panel p;
	public TextField fachname;
	public Button addFach;
	public Button delete;
	
	public Tree moduletree;
	public ModuleTree mt;
	public Label choice;
	
	public Panel tablePanel1;
	public Panel tablePanel11;
	public Panel tablePanel12;
	
	public UnassignedModulesTable umta;
	public Table unassignedModules;
	public Button add1;
	
	public ModuleTable mta;
	public Table modules;
	public Button add2;
	public LoginApplication starta;
	
	public HandbookManager_View (int id, Controller d) {
		super(d);
		starta =cont.getStart();
		Window test = starta.getWindow("Fach neu anlegen, Module zuordnen");
		if(test != null){
			starta.removeWindow(test);	
		}
		
		mt = new ModuleTree();	
		w = new Window("");
		w.setName("Fach neu anlegen, Module zuordnen");
		starta.addWindow(w);
		
		moduletree = mt.generateModuleTree(id);
		choice = new Label("Ausgewähltes Element: -");

		fachname = new TextField();
		fachname.setEnabled(false);

		addFach = new Button("Neues Fach");
		addFach.setEnabled(false);

		delete = new Button("Löschen");

		umta = new UnassignedModulesTable(cont.getUserID());
		unassignedModules = umta.generateTable();
		
		add1 = new Button("Modul dem ausgewählten Fach hinzufügen");
		add1.setEnabled(false);

		mta = new ModuleTable(cont.getUserID());
		System.out.println("User-ID:" + cont.getUserID());
		modules = mta.generateTable();
		
		add2 = new Button("Modul dem ausgewählten Fach hinzufügen");
		add2.setEnabled(false);

		buildMainLayout();
		w.setContent(mainLayout);
		
		Window old = starta.getWindow("M");
		oldURL = old.getURL();
		old.open(new ExternalResource(w.getURL()));
	}
	
	public void showNotification() {
		
	}
	
	public void buttonClick (Button.ClickEvent event) {
		if(event.getButton() == logout){
			starta.getMainWindow().getApplication().close();
		}
		if(event.getButton()==back){
			Startseite starte = new Startseite(cont.getStart(),cont.getUserID(),w,cont);
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
		label.setValue("Modulhandbuch organisieren");
		label.setStyleName(Runo.LABEL_H1);
		mainLayout.addComponent(label, "top:13.0%;left:35.0%;");
		
		// panel_1		
		mainLayout.addComponent(buildPanel_1(), "top:23.0%;left:35.0%;");
		
		// fachname
		fachname.setCaption("Fachname");
		fachname.setImmediate(false);
		fachname.setWidth("30.0%");
		fachname.setHeight("-1px");
		mainLayout.addComponent(fachname, "top:49.0%;left:35.0%;");
		
		// addFach
		addFach.setCaption("neues Fach");
		addFach.setImmediate(false);
		addFach.setWidth("25.0%");
		addFach.setHeight("-1px");
		mainLayout.addComponent(addFach, "top:49.0%;left:54.5%;");
		
		// delete
		delete.setCaption("löschen");
		delete.setImmediate(false);
		delete.setWidth("20.0%");
		delete.setHeight("-1px");
		mainLayout.addComponent(delete, "top:54.0%;left:35.0%;");
		
		// unassignedModules
		unassignedModules.setCaption("Neue Module");
		unassignedModules.setImmediate(false);
		unassignedModules.setWidth("30.0%");
		unassignedModules.setHeight("80.0%");
		mainLayout.addComponent(unassignedModules, "top:61.0%;left:20.0%;");
		
		// modules
		modules.setCaption("alte Module");
		modules.setImmediate(false);
		modules.setWidth("61.0%");
		modules.setHeight("80.0%");
		mainLayout.addComponent(modules, "top:61.0%;left:60.6%;");
		
		// add1
		add1.setImmediate(false);
		add1.setWidth("-1px");
		add1.setHeight("-1px");
		mainLayout.addComponent(add1, "top:93.0%;left:20.0%;");
		
		// add2
		add2.setImmediate(false);
		add2.setWidth("-1px");
		add2.setHeight("-1px");
		mainLayout.addComponent(add2, "top:93.0%;left:60.6%;");
		
		// logout
		logout = new Button();
		logout.setCaption("logout");
		logout.setImmediate(true);
		logout.setWidth("-1px");
		logout.setHeight("-1px");
		logout.setStyleName(BaseTheme.BUTTON_LINK);
		logout.addListener(this);
		mainLayout.addComponent(logout, "top:92.0%;left:5.0%;");
				
		// back
		back = new Button();
		back.setCaption("Startseite");
		back.setImmediate(true);
		back.setWidth("-1px");
		back.setHeight("-1px");
		back.setStyleName(BaseTheme.BUTTON_LINK);
		back.addListener(this);
		mainLayout.addComponent(back, "top:89.0%;left:5.0%;");
		
		return mainLayout;
	}

	private Panel buildPanel_1() {
		// common part: create layout
		pan = new Panel();
		pan.setCaption("Fächer und Module");
		pan.setImmediate(false);
		pan.setWidth("46.0%");
		pan.setHeight("30.0%");
	
		pan.addComponent(moduletree);
		
		return pan;
	}	
}

package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

import data.ModulDatabase;
import gui.HandbookManager_View;

public class HandbookManager implements ClickListener, ItemClickListener{
	
	/*
	 * Diese Klasse erlaubt das Erzeugen neuer Fächer, Zuordnen von Modulen
	 * und Löschen von Fächern und Modulen
	*/
	
	HandbookManager_View gui;
	ModulDatabase data;
	int id;
	int selectedId;
	String selectedUnassignedModule;
	String selectedModule;
	Controller cont;
	int actualDepth;
	
	//ItemID das zu der ID selectedId gehört
	Object item;
	String x;
	
	//Der Konstruktor erwartet die ID des Modulhandbuches, dass angezeigt
	//werden soll
	public HandbookManager(int _id, Controller con) {
		cont = con;
		id = _id;
		
		gui = new HandbookManager_View(id, cont);
		gui.delete.setEnabled(false);
		
		data = new ModulDatabase();
		gui.addFach.addListener(this);
		gui.delete.addListener(this);
		gui.moduletree.addListener(this);
		gui.unassignedModules.addListener(this);
		gui.modules.addListener(this);
		gui.add1.addListener(this);
		gui.add2.addListener(this);	
	}
	
	public void buttonClick(ClickEvent e) {
		
		//Hinzufügen eines neuen Fachs
		if (e.getSource() == gui.addFach) {
			if (actualDepth < 2) {
				String s = (String) gui.fachname.getValue();
				System.out.println(s);
				String[] splittedString = s.split(" ");
				//Erzeuge neues Fach, lade Tree bzw. Window neu
				//gui.moduletree = gui.mt.generateModuleTree(id);
				data.newFach(selectedId, s);
			} else {
				System.out.println("Elemente können nur bis zu einer Tiefe von 2 hinzugefügt werden.");
			}
		}
		if (e.getSource() == gui.add1) {
			String s = selectedUnassignedModule;			
			String[] splittedString = s.split(" ");
			int fid = Integer.parseInt(splittedString[0]);
			System.out.println("Modul zugeordnet: " + data.moveNewModule(selectedId, fid));	
		}
		if (e.getSource() == gui.add2) {
			String s = selectedModule;
			String[] splittedString = s.split(" ");
			int fid = Integer.parseInt(splittedString[0]);
			System.out.println("Modul (nochmals) zugeordnet: " + data.moveModule(selectedId, fid));
		}
		if (e.getSource() == gui.delete) {
			if (selectedId % 3 == 2) {
				//Löschen eines Faches
				System.out.println("Löschvorgang durchgeführt: " + data.deleteFach(selectedId));
			} else if (selectedId % 3 == 0) {
				//Löschen eines Moduls
							
				String[] splittedString = gui.moduletree.getParent(item).toString().split(" ");
				
				int parentId = Integer.parseInt(splittedString[0]);
				System.out.println("parentId: " + parentId);
				System.out.println("parentId: " + x);
				
				System.out.println("Löschvorgang durchgeführt: " + data.deleteModul(selectedId, parentId));
				
			}
		}
		// TODO Refresh noch ausbessern
		gui.starta.removeWindow(gui.w);
		HandbookManager t = new HandbookManager(id,cont);		
	}
	
	public void itemClick(ItemClickEvent e) {
		
		//Tree
		if (e.getSource() == gui.moduletree &&
				e.getButton() == ItemClickEvent.BUTTON_LEFT) {
			//Wenn das ausgewählte Fach keine Elemente enthält, ist das Löschen
			//möglich
			if (gui.moduletree.hasChildren(e.getItemId())) {
				gui.delete.setEnabled(false);
			}else{
				gui.delete.setEnabled(true);
			}
			//String[] splittedString = ((String) e.getItemId()).split(" ");
			//String[] splittedString = ((String) (e.getComponent()).getCaption()).split(" ");
			//System.out.println((String) ((AbstractComponent) e.getItem()).getCaption());
			Collection<?> c =  e.getItem().getItemPropertyIds();
			Object[] o = c.toArray();
			for (int i = 0; i < o.length; i++) {
				System.out.println("Property "+i+": "+o[i]);
			}
			
			item = e.getItemId();
			//x = ((Tree) e.getSource()).getParent(e.getItemId()).toString();
			
			System.out.println("X"+e.getItem().toString());
			System.out.println("Y"+e.getItem().getItemProperty("caption").toString());
			System.out.println("Z"+e.getItem().getItemProperty("depth").toString());
			actualDepth = Integer.parseInt(e.getItem().getItemProperty("depth").toString());
			
			//String[] splittedString = (((String) e.getItem().getItemProperty("Bezeichnung").getValue()).split(" "));
			String[] splittedString = e.getItem().toString().split(" ");
			//String[] splittedString = e.getItem().getItemProperty("caption").toString().split(" ");
			if (Integer.parseInt(splittedString[0]) % 3 != 0) {
				//Speichere die ID des im Baum ausgewählten Elementes
				selectedId = Integer.parseInt(splittedString[0]);
				gui.addFach.setEnabled(true);
				gui.fachname.setEnabled(true);
				//Prüfe auch ob ein nicht zugeordnetes Modul markiert ist			
				if (gui.unassignedModules.getValue() != null) {
					gui.add1.setEnabled(true);
				}
				if (gui.modules.getValue() != null) {
					gui.add2.setEnabled(true);
				}
			}else{
				System.out.println("Dies ist ein Modul.");
				selectedId = Integer.parseInt(splittedString[0]);
				gui.addFach.setEnabled(false);
				gui.fachname.setEnabled(false);
				
				gui.add1.setEnabled(false);
				gui.add2.setEnabled(false);
			}
			gui.choice.setValue("Ausgewähltes Element: Fach " + splittedString[2]);
			
			//Table
		}else if (e.getSource() == gui.unassignedModules){
			selectedUnassignedModule = e.getItem().toString();
			System.out.println(selectedUnassignedModule);

			if (gui.fachname.isEnabled() == true){
				gui.add1.setEnabled(true);
			}else{
				gui.add1.setEnabled(false);
			}
		}else if (e.getSource() == gui.modules){
			selectedModule = e.getItem().toString();
			System.out.println(selectedModule);
			if (gui.fachname.isEnabled() == true) {
				gui.add2.setEnabled(true);
			}else{
				gui.add2.setEnabled(false);
			}
		}	
	}
}

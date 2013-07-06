package control;

import java.sql.SQLException;
import java.util.LinkedList;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;

import data.BookData;
import data.ModulDatabase;

public class ModuleTree {
	
	ModulDatabase m;
	public Tree tree = null;
	
	public Tree generateModuleTree (int rootID) {
		
		m = new ModulDatabase();
		String modulhandbuchname = m.getFachname(rootID);
		tree = new Tree("");
		
		//Es dürfen auch dopplete Elemente im Tree auftauchen
		String e = rootID + " - " + modulhandbuchname;
		tree.addItem(e);
		createTree(rootID, e);
		tree.setImmediate(true);
		System.out.println(tree.expandItemsRecursively(e));
		return tree;
	}
	
	//Die Methode "createTree" baut rekursiv den Tree auf
	public void createTree (int actualID, String parent) {
		
		BookData bd = new BookData();
		LinkedList<Integer> next = bd.listeFaecher(actualID);
		//TODO Entfernen
		for (int i = 0; i < next.size(); i++) {
			System.out.println("next-ID" + next.get(i).intValue());
		}	
		//Sortierung nach Fächern und Modulen
		LinkedList<Integer> nextFach = new LinkedList<Integer>();
		LinkedList<Integer> module = new LinkedList<Integer>();
		for (int n = 0 ; n < next.size(); n++) {
			if (next.get(n).intValue() % 3 != 0) {	
				nextFach.add(next.get(n));
			} else {
				module.add(next.get(n));
			}
		}	
		//TODO Entfernen
		for (int i = 0; i < nextFach.size(); i++) {
			System.out.println("nextFach-ID" + next.get(i).intValue());
		}
		//TODO Entfernen
		for (int i = 0; i < module.size(); i++) {
			System.out.println("module-ID" + next.get(i).intValue());
		}
		String fachname = "fachname";
		String modulname = "modulname";
		String element = "element";
		
		//Der Tree erhält alle seine Knoten (Fächer) und Blätter (Module)
		for (int i = 0; i < nextFach.size(); i++) {
			fachname = m.getFachname(nextFach.get(i).intValue());
			element = nextFach.get(i).intValue() + " - " + fachname;
			int number = nextFach.get(i).intValue();
			
			Object itemId = tree.addItem(fachname);
			tree.setItemCaption(itemId, String.valueOf(number));
			System.out.println("Fachname: "+fachname+", Caption: " +String.valueOf(nextFach.get(i).intValue()));
			
				if (parent != null) {
				tree.setParent(itemId, parent);
				}
			createTree(nextFach.get(i).intValue(), fachname);
		}	
		for (int i = 0; i < module.size(); i++) {
			modulname = m.getModulname(module.get(i).intValue());
			element = module.get(i).intValue() + " - " + modulname;
			int number = nextFach.get(i).intValue();
			
			Object itemId = tree.addItem(modulname);
			tree.setItemCaption(itemId, String.valueOf(number));
			System.out.println("Modulname: "+fachname+", Caption: " +String.valueOf(nextFach.get(i).intValue()));
			
			if (parent != null) {
				tree.setParent(itemId, parent);
			}
			tree.setChildrenAllowed(itemId, false);
		}
	}
	
	public static void main(String[] args) {

	}
}

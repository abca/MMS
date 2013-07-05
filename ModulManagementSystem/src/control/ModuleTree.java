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
	
	//private static final Object CAPTION_PROPERTY = "caption";
	
	public Tree generateModuleTree (int rootID) {
		
		m = new ModulDatabase();
		
		//Tree t = null;
		String modulhandbuchname = m.getFachname(rootID);
		
		//tree = new Tree(modulhandbuchname);
		
		
		tree = new Tree("");
		
		//Es dürfen auch dopplete Elemente im Tree auftauchen
		/*
		tree.addContainerProperty(CAPTION_PROPERTY, String.class, "");
		tree.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		tree.setItemCaptionPropertyId(CAPTION_PROPERTY);
		*/
		
		String e = rootID + " - " + modulhandbuchname;
		
		tree.addItem(e);
		
		//createTree(rootID, null);
		createTree(rootID, e);
		
		tree.setImmediate(true);
		System.out.println(tree.expandItemsRecursively(e));
		
		/*try {
			m.con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}*/
		
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
			
			Object itemId = tree.addItem();
			tree.setItemCaption(itemId, element);
			/*
			tree.addItem(element);
			*/
			
			//Object itemId = tree.addItem(element);
			//tree.setItemCaption(itemId, element);
			if (parent != null) {
				tree.setParent(element, parent);
			}
			createTree(nextFach.get(i).intValue(), element);
		}
		
		for (int i = 0; i < module.size(); i++) {
			modulname = m.getModulname(module.get(i).intValue());
			element = module.get(i).intValue() + " - " + modulname;
			
			Object itemId = tree.addItem();
			tree.setItemCaption(itemId, element);
			/*
			tree.addItem(element);
			*/
			
			//Object itemId = tree.addItem(element);
			//tree.setItemCaption(itemId, element);
			if (parent != null) {
				tree.setParent(element, parent);
			}
			tree.setChildrenAllowed(element, false);
		}
	}
	
	public static void main(String[] args) {

	}

}

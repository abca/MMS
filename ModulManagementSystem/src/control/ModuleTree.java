package control;

import java.sql.SQLException;
import java.util.LinkedList;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;

import data.BookData;
import data.ModulDatabase;

public class ModuleTree {
	
	ModulDatabase m;
	public Tree tree = null;
	
	private static final Object CAPTION_PROPERTY = "caption";
	private static final Object DEPTH_PROPERTY = "depth";
	private static final Object PARENTID_PROPERTY = "parentId";
	
	public Tree generateModuleTree (int rootID) {
		
		m = new ModulDatabase();
		String modulhandbuchname = m.getFachname(rootID);
		tree = new Tree("");
		
		//Es dürfen auch doppelte Elemente im Tree auftauchen
		String e = rootID + " - " + modulhandbuchname;
		//tree.addItem(e);
		
		tree.addContainerProperty(CAPTION_PROPERTY, String.class, "");
		tree.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		tree.setItemCaptionPropertyId(CAPTION_PROPERTY);
		
		tree.addContainerProperty(DEPTH_PROPERTY, Integer.class, "");
		//tree.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		//tree.setItemCaptionPropertyId(DEPTH_PROPERTY);
		tree.addContainerProperty(PARENTID_PROPERTY, Integer.class, "");
		
		
		Object itemId = tree.addItem();
		Item i = tree.getItem(itemId);
		Property p = i.getItemProperty(CAPTION_PROPERTY);
		p.setValue(e);
		p = i.getItemProperty(DEPTH_PROPERTY);
		p.setValue(new Integer(0));
		p = i.getItemProperty(PARENTID_PROPERTY);
		p.setValue(new Integer(0));
		
		//PropertysetItem i = new PropertysetItem();
		//i.addItemProperty("Bezeichnung", new ObjectProperty(e));
		//Item itemId = tree.addItem(i);
		//itemId.addItemProperty("Bezeichnung", new ObjectProperty(e));
		//tree.setItemCaption(itemId, e);
		
		createTree(rootID, itemId, 0);
		tree.setImmediate(true);
		System.out.println(tree.expandItemsRecursively(itemId));
		return tree;
	}
	
	//Die Methode "createTree" baut rekursiv den Tree auf
	public void createTree (int actualID, Object parent, int depth) {
		
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
		
		depth = depth + 1;
		//Der Tree erhält alle seine Knoten (Fächer) und Blätter (Module)
		for (int i = 0; i < nextFach.size(); i++) {
			fachname = m.getFachname(nextFach.get(i).intValue());
			element = nextFach.get(i).intValue() + " - " + fachname;
			//int number = nextFach.get(i).intValue();
			
			//Object itemId = tree.addItem(fachname);
			//tree.addItem(element);
			Object itemId = tree.addItem();
			Item it = tree.getItem(itemId);
			Property p = it.getItemProperty(CAPTION_PROPERTY);
			p.setValue(element);
			p = it.getItemProperty(DEPTH_PROPERTY);
			p.setValue(depth);
			p = it.getItemProperty(PARENTID_PROPERTY);
			p.setValue(new Integer(0));
			if (parent != null) {
				//tree.setParent(element, parent);
				tree.setParent(itemId, parent);
			}
			//createTree(nextFach.get(i).intValue(), element);
			createTree(nextFach.get(i).intValue(), itemId, depth);
		}
		
		for (int i = 0; i < module.size(); i++) {
			modulname = m.getModulname(module.get(i).intValue());
			element = module.get(i).intValue() + " - " + modulname;
			
			Object itemId = tree.addItem();
			Item it = tree.getItem(itemId);
			Property p = it.getItemProperty(CAPTION_PROPERTY);
			p.setValue(element);
			p = it.getItemProperty(DEPTH_PROPERTY);
			p.setValue(depth);
			p = it.getItemProperty(PARENTID_PROPERTY);
			Item parentItem = tree.getItem(parent);
			String[] splittedString = parentItem.getItemProperty("caption").toString().split(" ");
			p.setValue(splittedString[0]);
			
			//int number = nextFach.get(i).intValue();
			
			//Object itemId = tree.addItem(modulname);
			//tree.addItem(element);
			//tree.setItemCaption(itemId, String.valueOf(number));
			//System.out.println("Modulname: "+fachname+", Caption: " +String.valueOf(nextFach.get(i).intValue()));
			
			
			
			if (parent != null) {
				//tree.setParent(element, parent);
				tree.setParent(itemId, parent);
			}
			//tree.setChildrenAllowed(element, false);
			tree.setChildrenAllowed(itemId, false);
		}
	}
	
	public static void main(String[] args) {

	}
}

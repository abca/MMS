package gui;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ModulHandbook extends Startseite implements ClickListener {
    
	Window createW;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button anlegen;
	@AutoGenerated
	private Label label_1;
	@AutoGenerated
	private Panel panel_1;
	@AutoGenerated
	private VerticalLayout verticalLayout_1;
	@AutoGenerated
	private Tree tree;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	
	public ModulHandbook() {
		
		Window test = starta.getWindow("Modulhandbuch");
		if(test != null){
			starta.removeWindow(test);	
		}
		buildMainLayout();
		createW = new Window("");
		createW.setName("Modulhandbuch");
		createW.setContent(mainLayout);
		anlegen.addListener(this);
		starta.addWindow(createW);
		Window old = starta.getWindow("Startseite");		
		old.open(new ExternalResource(createW.getURL()));
	}	
	
	@AutoGenerated
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
		
		// panel_1
		panel_1 = buildPanel_1();
		mainLayout.addComponent(panel_1, "top:30.0%;left:20.0%;");
		
		// label_1
		label_1 = new Label();
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Modulhandbuch");
		mainLayout.addComponent(label_1, "top:25.0%;left:20.0%;");
		
		// anlegen
		anlegen = new Button();
		anlegen.setCaption("anlegen");
		anlegen.setImmediate(true);
		anlegen.setWidth("20.0%");
		anlegen.setHeight("-1px");
		mainLayout.addComponent(anlegen, "top:83.0%;left:55.0%;");
		
		return mainLayout;
	}

	@AutoGenerated
	private Panel buildPanel_1() {
		
		// common part: create layout
		panel_1 = new Panel();
		panel_1.setImmediate(false);
		panel_1.setWidth("55.0%");
		panel_1.setHeight("70.0%");
		
		// verticalLayout_1
		verticalLayout_1 = buildVerticalLayout_1();
		panel_1.setContent(verticalLayout_1);
		
		return panel_1;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_1() {
		
		// common part: create layout
		verticalLayout_1 = new VerticalLayout();
		verticalLayout_1.setImmediate(false);
		verticalLayout_1.setWidth("100.0%");
		verticalLayout_1.setHeight("100.0%");
		verticalLayout_1.setMargin(false);
		
		// tree_1
		tree = new Tree();
		tree.setImmediate(false);
		tree.setWidth("-1px");
		tree.setHeight("-1px");
		verticalLayout_1.addComponent(tree);
		
		return verticalLayout_1;
	}
	
	public void buttonClick(ClickEvent event) {
		
		if(event.getButton() == anlegen){
			//Testbaum
			tree.addItem("Fach 1");
			tree.addItem("Fach 2");
			tree.addItem("Modul 1");
			tree.addItem("Modul 2");
			tree.setParent("Modul 1","Fach 2");
			tree.setParent("Fach 2", "Fach 1");
			tree.setParent("Modul 2","Fach 2");
			tree.setChildrenAllowed("Modul 1", false);
			tree.setChildrenAllowed("Modul 2", false);			
		}
	}	
}

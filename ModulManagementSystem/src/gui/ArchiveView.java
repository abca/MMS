package gui;

import java.util.LinkedList;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

public class ArchiveView extends Startseite implements ClickListener {

	private String [] tmp;
	private LinkedList<Integer> tmp2;
	private AbsoluteLayout mainLayout;
	Window arch, down;
	Label label;
	Button request;
	ListSelect archiv;
	
	public ArchiveView(LinkedList<Integer> arids, String [] liste){
		Window test = starta.getWindow("Stellvertreter");
		if(test != null){
			starta.removeWindow(test);	
		}
		arch = new Window("Archiv");
		starta.addWindow(arch);
		
		tmp = liste;
		tmp2 = arids;
		
		openArchivListe(liste);
		
		 buildMainLayout();
			arch.setContent(mainLayout);
			
			Window old = starta.getWindow("Startseite");
			old.open(new ExternalResource(arch.getURL()));	
	}
	
	public void buttonClick (Button.ClickEvent event) {
		
		String read;
		if(event.getButton() == request){	
			read =(String) archiv.getValue(); 
			System.out.println(read);
			int book = 0; 
			for(int i=0; i<tmp2.size();i++){
				if(read.equals(tmp[i])){
					book = tmp2.get(i);
				}
			}
			Link download = contDek.requestArchBook(book, starta);
			LinkPopup(download);	
		}
		if(event.getButton() == logout){
			 starta.getMainWindow().getApplication().close();
		}
	}
	
	public void openArchivListe(String [] liste){
			
		archiv = new ListSelect();
			
		for(int i = 0; i < liste.length; i++){
				
			archiv.addItem(liste[i]);			
		}
			
		archiv.setNullSelectionAllowed(false);	//leere Auswahl ist nicht erlaubt				
	}
	
	public void LinkPopup(Link download){
		
		down = new Window("Link");
		down.addComponent(download);
    	down.setHeight("200px");
    	down.setWidth("300px");
    	arch.addWindow(down);
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
		label.setValue("Archiv");
		label.setStyleName(Runo.LABEL_H1);
		mainLayout.addComponent(label, "top:25.0%;left:35.0%;");
			
		// Archiv ListSelect-Element		
		archiv.setImmediate(false);
		archiv.setWidth("46.0%");
		archiv.setHeight("70.0%");
		mainLayout.addComponent(archiv, "top:35.0%;left:35.0%;");
			
		// request
		request = new Button();
		request.setCaption("auswählen");
		request.setImmediate(true);
		request.setWidth("-1px");
		request.setHeight("-1px");		
		request.addListener(this);
		mainLayout.addComponent(request, "top:83.0%;left:35.0%;");
			
		// logout
		logout = new Button();
		logout.setCaption("logout");
		logout.setImmediate(true);
		logout.setWidth("-1px");
		logout.setHeight("-1px");
		logout.setStyleName(BaseTheme.BUTTON_LINK);
		logout.addListener(this);
		mainLayout.addComponent(logout, "top:88.0%;left:35.0%;");
			
		return mainLayout;
	}
}

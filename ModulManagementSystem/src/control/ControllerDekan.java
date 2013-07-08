package control;

import gui.ArchiveView;
import gui.ChangeRequest;
import gui.LoginApplication;
import gui.ModulhandbuchRequest;
import gui.ModulhandbuchRequestAen;
import gui.RequestView;
import gui.Startseite;

import java.io.File;
import java.util.LinkedList;

import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Window;

import objects.Modul;
import objects.ModulKu;
import objects.Nachricht;

import data.*;

public class ControllerDekan extends Controller{
	
	BookName bookName = new BookName();
	SaveHandbook save = new SaveHandbook();
	Archive ar = new Archive();
	Controller cont;
	
	public ControllerDekan(LoginApplication starta, int id, Controller con){	  
		super(starta, id);
		cont = con;
	}
	
	//bestätigt oder verwirft Änderung
	public void okRequest(Modul modul, Nachricht tmp, boolean ok){	
		
		nachrichtenData.delete(tmp.getid());	
		
		if (ok){			
			modulPufferData.deleteBufferModule(modul.getid());	
			//Das Modul ist bereits vorhanden, wenn der Löschvorgang erfolgreich ist
			if (modulDatabase.deleteModule(modul.getid()) == 0) {
				modulDatabase.moveModuleIntoDefaultFach(modul.getid());
			}
			modulDatabase.saveModule(modul);		
			modulDatabase.setSperr(modul.getid(), false);
			Nachricht nachricht = new Nachricht(nachrichtenData.getNewId(), "Ihre Anfrage bezüglich des Moduls " +modul.gettitle()+" wurde angenommen.","Anfrage akzeptiert",modul.getdozid(), 0);
			nachrichtenData.newNachricht(nachricht);	
		}else{	
			modulDatabase.setSperr(modul.getid(), false);
			Nachricht nachricht = new Nachricht(nachrichtenData.getNewId(), "Ihre Anfrage bezüglich des Moduls " +modul.gettitle()+" wurde abgelehnt.","Anfrage abgelehnt",modul.getdozid(), 0);
			nachrichtenData.newNachricht(nachricht);
			modulPufferData.deleteBufferModule(modul.getid());
		}
		Window old = login.getWindow("Aenderungsantrag");
		Startseite tmp1 = new Startseite(login, userid,old, cont);
	}

	//lädt Änderung
	public void loadRequest(int modul, Nachricht nachricht){
		
		Modul tmp = modulPufferData.loadBufferModule(modul);
		RequestView test = new RequestView(tmp, nachricht, cont);
		
	}

	//lädt Änderungsliste
	public void loadRequestlist(){

		LinkedList<Nachricht> list = nachrichtenData.loadBenachrichtList(userid);
		LinkedList<Nachricht> listStell = nachrichtenData.loadBenachrichtList(userData.getStellID(userid));
		LinkedList<ModulKu> list2 = new LinkedList<ModulKu>();
		
		for(int i = 0; i<listStell.size();i++){
			list.add(listStell.get(i));
		}
		for (int i =0; i<list.size(); i++){
			list2.add(modulPufferData.loadModuleKu(list.get(i).getmodule()));	
		}
		ChangeRequest tmp = new ChangeRequest(list, list2, cont);	
	}
	
	//speichert Datum und Handbuch-PDF
	public void saveDatum(String datumstr){
		
	}
	
	public void requestModule() {
		
		String[] list = book.getBookNames(userid);		
		if(userData.getRangStell(userid)){
			String[] listStell = book.getBookNames(userData.getStellID(userid));
			String[] list2 = new String[list.length+listStell.length];
			for(int i=0; i<list.length;i++){
				list2[i]=list[i];
			}
			for(int i=list.length;i<list2.length;i++){
				list2[i]=listStell[i-list.length];
			}
			list=list2;
		}
		LinkedList<Integer>  ids = book.getBookID(userid);
		ModulhandbuchRequestAen req = new ModulhandbuchRequestAen(ids, list, cont);
	}

	public void scanHandbooks(String time) {
		
		String[] name = bookName.getBookNames(userid);
		LinkedList<Integer> arr = bookName.getBookID(userid);
		save.archive(arr, time, name, userid, login);
	}
	
	public void setDeadline(String Deadline) {
		
		LinkedList<Integer> tmp = new LinkedList<Integer>(); 
		tmp = deadlineData.newDeadlineMessage(userid);
		for(int i = 0; i < tmp.size(); i++) {
			int resid = tmp.get(i).intValue();
			Nachricht deadLine = new Nachricht(nachrichtenData.getNewId(),"Der Stichtag für Moduländerungen ist am " +Deadline+".","Stichtag",resid, 0);
			nachrichtenData.newNachricht(deadLine);
		}
	}
	
	public void saveHandbook(String name){
		//ist der nutzer nur stellvertreter eines dekans?
		if(!userData.getRangDekan(userid)&&cont.getDekan(userid)){
			book.newHandbook(name, userData.getStellID(userid));
		}
		else{
			book.newHandbook(name, userid);
		}
	}

	public void archivListeAusgeben() {
		
		LinkedList<Integer> ids = ar.getBookID(userid);
		String[] liste = ar.getBookNames(userid);
		ArchiveView archView = new ArchiveView(ids, liste, cont);
	}

	public Link requestArchBook(int book, LoginApplication app) {
		
		String path = ar.getBook(book);
		File f = new File(path);
		FileResource fr = new FileResource(f, app);
		Link l = new Link("Download the Modulehandbook here", fr, "Download", 0, 0, Link.TARGET_BORDER_DEFAULT);
		return l;
	}
}

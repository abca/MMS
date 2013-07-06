package control;

import gui.LoginApplication;

import gui.ModulEditCreate;
import gui.ModulReview;
import gui.Startseite;

import java.util.LinkedList;
import java.util.List;

import com.vaadin.ui.Window;

import objects.Modul;
import objects.ModulKu;
import objects.Nachricht;


public class ControllerDozent extends Controller{
	Controller cont;
	public ControllerDozent(LoginApplication starta, int id, Controller con){	  
		super(starta, id);
		cont = con;
	}
	//öffnet Datensatz zum Bearbeiten in neuem Fenster
	public void ändernModul(int modul, ModulReview tmp ){
		
		int uid=userid;
		if(getStell(userid)&&!userData.getRangDozent(userid)&&!userData.getRangDekan(userid))
			uid=userData.getStellID(userid);
		if (modul == 0){
		Modul neu = new Modul(modulDatabase.getNewId(),  null, 0, null,
				 null, 0, null, uid , 
				 null, null, null,
				 null, null,null, null,
				 null, null, null, null);
		ModulEditCreate b = new ModulEditCreate(neu,true, cont);		
		}else{
		Modul test = modulDatabase.loadModule(modul);			
		if(modulDatabase.getSperr(test.getid())== true){
			tmp.displayError("Das Modul wurde bereits geändert. Warten Sie bitte auf eine Bestätigung");		
			}else{
			 ModulEditCreate gg = new ModulEditCreate(test,false, cont);
			}
		}	
	}
	
	//gibt Änderungsantrag aus
	public void ausgebenModulList(int userid){
		System.out.println(userid);

		LinkedList<ModulKu> list = modulDatabase.loadModuleList(userid);
		if (userData.getRangStell(userid)){
			LinkedList<ModulKu> stellList = modulDatabase.loadModuleList(userData.getStellID(userid));
			for(int i=0;i<stellList.size();i++)list.add(stellList.get(i));
		}	
		ModulReview a = new ModulReview(list, cont);	
	}
	
	//Methode zum Speichern des Moduls im Puffer
	public void speichernModul(Modul modul){
		
		modulPufferData.writeBufferModule(modul);
	    modulDatabase.setSperr(modul.getid(), true);

		Nachricht test = new Nachricht(nachrichtenData.getNewId(),"", "",modul.getresponsibleid(), modul.getid());
		nachrichtenData.newNachricht(test);
		
		Window old = login.getWindow("Modulaendern");
		Startseite tmp = new Startseite(login, userid,old,cont);
	}
}

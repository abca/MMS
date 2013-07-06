
package control;
import java.util.LinkedList;

import com.vaadin.ui.Window;

import objects.Benutzer;
import objects.ModulKu;
import objects.Nachricht;

import gui.LoginApplication;
import gui.ModulhandbuchRequest;
import gui.NewMessage;
import gui.Startseite;

import data.BenutzerData;
import data.BookData;
import data.BookName;
import data.ModulDatabase;
import data.ModulPufferData;
import data.NachrichtData;
import data.DeadLineData;

public class Controller {
	
	protected ModulDatabase modulDatabase = new ModulDatabase();
	protected ModulPufferData modulPufferData = new ModulPufferData();
	protected NachrichtData nachrichtenData = new NachrichtData();
	protected DeadLineData deadlineData = new DeadLineData();
	protected BookName book = new BookName();
	protected BookData bookdata = new BookData();
	protected int userid ;
    public LoginApplication login;
    ControllerDozent contD;
    ControllerDekan contDe;
    
	BenutzerData userData = new BenutzerData();
	
	//Konstruktor
	public Controller (LoginApplication tmp){	
		login = tmp;
	}
	
	public Controller(LoginApplication tmp,int  user){
		userid =user;
		login = tmp;
	}
	
	//Zugriff auf Datenbank-Klasse, holt sich Benutzerliste
	public String[] benutzerListeAusgeben(){
		
		String[] liste;
		liste = userData.getBenutzerListe();
		return liste;	
	}
	
	//liest anhand der Benutzernamen (bzw. deren IDs über getID(Name) in einer Instanz der Benutzer-Klasse die Ränge aus
	public String rangAusgeben(String Name){
		
		String rangname = "";
		int Id;
		Id = userData.getID(Name);
		
		if(userData.getRangDozent(Id) && userData.getRangDekan(Id)){rangname="[Dozent, Dekan]"; return rangname;}
		if(userData.getRangDozent(Id)){rangname="Dozent"; return rangname;}
		if(userData.getRangDekan(Id)){rangname="Dekan"; return rangname;}

		return rangname;	
	}
	
	//liefert aus Benutzer id zu name
	public int getID(String name){
		
		return userData.getID(name);
	}
	
	//gibt an, ob ein User bereits Stellvertreter ist
	public boolean getStell(int id){
		
		return userData.getRangStell(id);
	}
	
	//gibt an, ob der User idStell Stellvertreter von User userid ist
	public  boolean getMyStell(int idStell){
		
		return (userData.getRangStell(idStell)
				&&userData.getStellID(idStell)==userid);
	}
	
	//gibt an, ob ein User Rechte hat, auch über Stellvertreter
	public boolean getAdmin(int id){
		
		if(userData.getRangAdmin(id))return true;
		else if(userData.getStellAdmin(id))return true;
		else return false;
	}
		
	public boolean getDozent(int id){
		
		if(userData.getRangDozent(id))return true;
		else if(userData.getStellDozent(id))return true;
		else return false;
	}
		
	public boolean getDekan(int id){
		
		if(userData.getRangDekan(id))return true;
		else if(userData.getStellDekan(id))return true;
		else return false;
	}
	
	//gibt neuen Rang an Datenbankklasse weiter, um diesen zu speichern
	public void aenderungSpeichern(String Name, String Rang){
		
		int Id;
		Id = userData.getID(Name);
		userData.setRangDozent(Id, Rang.contains("Dozent"));
		userData.setRangDekan(Id, Rang.contains("Dekan"));		
		Window old = login.getWindow("adminWindow");
		Startseite tmp1 = new Startseite(login, userid,old,this);
	}
	
	//gibt Username und Passwort an Benutzer-Klasse weiter zur Verifizierung, wenn verifiziert werden angegebene Aktionen ausgeführt
	public void login(String us, String pw, LoginApplication b) {
		
		if( userData.loginCheck(us,pw) == true){
		userid = userData.getID(us);
		Window old = login.getWindow("main");
		Startseite aa = new Startseite(b, userid, old, this);
		System.out.println(userid);
		}else {
			b.displayError();
		}
	}
	
	//übernimmt eingegebene Benutzerdaten, ergänzt Booleans für den Rang und ruft Methode zur Generierung einer neuen ID auf
	public void register(String us, String em, String p1) {
		
		boolean dozent = false;
		boolean dekan = false;
		boolean dez2 = false;
		boolean admin = false;
		boolean stell = false;
		int stellid = 0;
		int id = userData.getNewId();
		
		Benutzer test = new Benutzer(id, us, em, p1, dozent, dekan, admin, stell, stellid);
		userData.newUser(test);
	}

	//ruft Datenbankzugriffe für Modulhandbuchnamen und -ids auf, übergibt Loginapplication
	public void requestModule(LoginApplication start) {
		
		String[] list = book.getBookNames(0);
		LinkedList<Integer>  ids = book.getBookID(0);
		ModulhandbuchRequest req = new ModulhandbuchRequest(start, ids, list);
	}

	public void setDep (String StellName,boolean bool){
		
		int ID = userData.getID(StellName);
		if(bool){			
			userData.setRangStell(ID, true);
			userData.setStellID(ID, userid);
		}else {
			userData.setRangStell(ID,false);
			userData.setStellID(ID, 0);
		}
	}
	
	public void changeBenutzer(Benutzer neu){		
		
		userData.deleteUser(neu.getId());
		userData.newUser(neu);		
	}
	
	public Benutzer loadBenutzer(int userid1){
		
		Benutzer tmp = userData.loadBenutzer(userid1);
		return tmp;
	}
	
	public void loadNewMessageList(){

		LinkedList<Nachricht> list = nachrichtenData.loadNewBenachrichtList(userid);
		NewMessage newMessages = new NewMessage(list, this);
	}
	
	public boolean doesNameExist(String name){
		
		String[] liste = userData.getBenutzerListe();
		for (int i = 0;i<liste.length;i++){
			if(liste[i].equals(name))return true;
		}
		return false;
	} 
	
	public boolean doesModuleNameExist(String name){
		
		LinkedList<ModulKu> list = modulDatabase.loadWholeModuleList();
		LinkedList<ModulKu> list2 = modulPufferData.loadBufferModuleList();
		
		for(int i=0;i<list.size();i++)
			if(list.get(i).gettitle().equals(name)) return true;
		for(int i=0;i<list2.size();i++)
			if(list2.get(i).gettitle().equals(name)) return true;
		return false;
	}
	
	public void deleteUser(String name){
		
		userData.deleteUser(userData.getID(name));
	}
	
	public boolean deleteMessage(int messid){
		boolean delete = nachrichtenData.delete(messid);
		return delete;
	}
	public int getUserID(){
		return userid;
	}
	public LoginApplication getStart(){
		return login;
	}
	public void setController(ControllerDozent a, ControllerDekan b){
		contD =a;
		contDe= b;
		
	}
	public ControllerDozent getcDo(){
		return contD;	
	}
	public ControllerDekan getcDe(){
		
		return contDe;
	}
}

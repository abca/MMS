package control;

import java.io.File;
import java.util.LinkedList;

import com.vaadin.terminal.FileResource;

import data.Archive;

import gui.LoginApplication;

public class SaveHandbook {
	
	Archive ar = new Archive();
	ModuleHandbook tmp = new ModuleHandbook();
	
	public void archive(LinkedList<Integer> arr, String time, String[] name, int userid, LoginApplication login){
		
		for(int i=0; i<arr.size(); i++){
			int local = arr.get(i);
			FileResource file = tmp.generatePDF(local, login);
			File f = file.getSourceFile();
			String path = f.getAbsolutePath();
			String locname = name[i];
			ar.saveFile(local, path, time, locname, userid);
		}
	}
}

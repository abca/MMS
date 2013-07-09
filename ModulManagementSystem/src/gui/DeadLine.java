package gui;

import java.net.URL;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;
import  java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.data.Property;
import java.text.ParseException;

import control.Controller;

public class DeadLine extends Startseite implements Button.ClickListener {

	OptionGroup group;
	TextField year;
	Window dead, archiveW, confirmW;
	String datumstr, time;
	Label label, text;
	/*Text*/PopupDateField datum;
	private Button ok, archive, okay,back, setDline;
	private URL oldURL;
	private AbsoluteLayout lay;
	LoginApplication starta;
	
	public DeadLine(Controller d){
		super(d);
		 starta =cont.getStart();
		Window test = starta.getWindow("Stichtag");
		if(test != null){
			starta.removeWindow(test);	
		}
		dead = new Window("Stichtag");
		starta.addWindow(dead);
		
		buildMainLayout();
		dead.setContent(lay);
		
		Window old = starta.getWindow("Startseite");
		oldURL = old.getURL();
		old.open(new ExternalResource(dead.getURL()));	
	}
	
	public void buttonClick (Button.ClickEvent event) {
	
		if(event.getButton() == ok){	
			try {
				String[] split = datum.getValue().toString().split(" ");
				String tmpstr = split[1];

				if(tmpstr.equals("Jan")){tmpstr="01";}
				if(tmpstr.equals("Feb")){tmpstr="02";}
				if(tmpstr.equals("Mar")){tmpstr="03";}
				if(tmpstr.equals("Apr")){tmpstr="04";}
				if(tmpstr.equals("May")){tmpstr="05";}
				if(tmpstr.equals("Jun")){tmpstr="06";}
				if(tmpstr.equals("Jul")){tmpstr="07";}
				if(tmpstr.equals("Aug")){tmpstr="08";}
				if(tmpstr.equals("Sep")){tmpstr="09";}
				if(tmpstr.equals("Oct")){tmpstr="10";}
				if(tmpstr.equals("Nov")){tmpstr="11";}
				if(tmpstr.equals("Dec")){tmpstr="12";}

			    datumstr = split[2]+"."+tmpstr+"."+split[5];
			    System.out.println(datumstr);
			}
			catch (NullPointerException e){
				datumstr = "";
			}
			if(datumstr == ""){
			}
			else{
				cont.getcDe().setDeadline(datumstr);
				InfoWindow info = new InfoWindow("Stichtag","Der Stichtag für Änderungen wurde festgelegt für den " + datumstr + ".",dead);
			}
		}
		if(event.getButton() == setDline) {
			dead.removeWindow(confirmW);
		}
		if(event.getButton() == okay){
			try{
				String out = group.getValue().toString();
				String out2 = (String)year.getValue();
				if (out2.equals("")||Integer.parseInt(out2)<2000||Integer.parseInt(out2)>2100){
					InfoWindow fatalerr = new InfoWindow("archivieren", "Sie müssen Angaben bezüglich Jahr und Semester machen", dead);
				}else if (!(out2.equals(""))){
					if(out.equals("Sommersemester")){
						time = out + " " + out2;
					}
					else{
						time = out + " " + out2 + "/"+(Integer.parseInt(out2)-1999); // Wintersemester der Form 20XX/YY
					}
					cont.getcDe().scanHandbooks(time);
					dead.removeWindow(archiveW);					
				}
			}catch(Exception e){
				InfoWindow fatalerr = new InfoWindow("archivieren", "Sie müssen Angaben bezüglich Jahr und Semester machen", dead);
			}	
		}
		if(event.getButton() == archive){
			archiveDate();
		}
		if(event.getButton()== logout){
		       starta.getMainWindow().getApplication().close();				
		}
		if(event.getButton()==back){
			dead.open(new ExternalResource(oldURL));
		}
	}

	public void archiveDate(){
		Label l = new Label("Bei WS bitte das erste Jahr eingeben");
		year = new TextField("Jahr");
		group = new OptionGroup();
		group.setMultiSelect(false);
		group.addItem("Wintersemester");
		group.addItem("Sommersemester");
		archiveW = new Window("Daten eingeben");
		okay = new Button("ok");
		okay.addListener(this);
		Layout re = new VerticalLayout();
		
		archiveW.setContent(re);
		archiveW.addComponent(l);
		archiveW.addComponent(group);
		archiveW.addComponent(year);
		archiveW.addComponent(okay);
		dead.addWindow(archiveW);
		archiveW.setHeight("300px");
		archiveW.setWidth("300px");
		archiveW.center();
	}
	
	private AbsoluteLayout buildMainLayout() {
		
		lay = new AbsoluteLayout();
		lay.setImmediate(false);
		lay.setWidth("100%");
		lay.setHeight("100%");
		lay.setMargin(false);
		
		// top-level component properties
		lay.setWidth("100.0%");
		lay.setHeight("100.0%");
		
		// label
		label = new Label();
		label.setImmediate(false);
		label.setWidth("-1px");
		label.setHeight("-1px");
		label.setValue("Stichtag festlegen");
		label.setStyleName(Runo.LABEL_H1);
		lay.addComponent(label, "top:25.0%;left:35.0%;");
		
		//DateField
		datum = new PopupDateField(){
		    @Override
		    protected Date handleUnparsableDateString(String dateString)
		    throws Property.ConversionException {
		    	InfoWindow err = new InfoWindow("Fehler","Geben Sie bitte ein Datum im Format DD.MM.YYYY ein",dead);
		    throw new Property.ConversionException("");
		    }
		};
		
		datum.setDateFormat("dd.MM.yyyy");
		datum.setResolution(DateField.RESOLUTION_DAY);
		datum.setInputPrompt("Stichtag");
		datum.setCaption("Datum");
		datum.setImmediate(false);
		datum.setWidth("25.0%");
		datum.setHeight("-1px");
		lay.addComponent(datum, "top:40.0%;left:35.0%;");
		
		//Button
		ok = new Button();
		ok.setCaption("Festlegen");
		ok.setImmediate(false);
		ok.setWidth("-1px");
		ok.setHeight("-1px");
		ok.addListener(this);
		lay.addComponent(ok, "top:50.0%;left:35.0%;");
		
		archive = new Button();
		archive.setCaption("Archivieren");
		archive.setImmediate(false);
		archive.addListener(this);
		archive.setWidth("-1px");
		archive.setHeight("-1px");
		lay.addComponent(archive, "top:50.0%;left:50.0%");
		
		// back
		back = new Button();
		back.setCaption("Startseite");
		back.setImmediate(true);
		back.setWidth("-1px");
		back.setHeight("-1px");
		back.setStyleName(BaseTheme.BUTTON_LINK);
		back.addListener(this);
		lay.addComponent(back, "top:61.0%;left:35.0%;");
		
		// logout
		logout = new Button();
		logout.setCaption("logout");
		logout.setImmediate(false);
		logout.setWidth("-1px");
		logout.setHeight("-1px");
		logout.setStyleName(BaseTheme.BUTTON_LINK);
		logout.addListener(this);
		lay.addComponent(logout, "top:65.0%;left:35.0%;");
		
		return lay;
	}
}

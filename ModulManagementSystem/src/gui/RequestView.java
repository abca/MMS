package gui;

import java.net.URL;

import objects.Modul;
import objects.Nachricht;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;

import control.Controller;

public class RequestView extends Startseite implements Button.ClickListener {
	
	Panel title,lp,language,turn,responsibleid,doz,filing,requirements,aims,content,lit,events,work,		//Titellabels
	exams,formcond,grades, responsible;
	
	Label titlecon, lpcon, languagecon, turncon, responsibleidcon,dozcon, filingcon, requirementscon, 		//Inhaltslabels
	aimscon, contentcon, litcon, eventscon, workcon, examscon, formcondcon, gradescon, responsiblecon;
	
	Label label;
	private Button confirm, deny,back;
	private AbsoluteLayout mainLayout;
	private URL oldURL;
	Window Requw;
	Modul tmp;
	Nachricht tmp2;
	LoginApplication starta;
	
	//schreibt alle Werte In Labelsp
	public RequestView(Modul b, Nachricht d, Controller a){
		super(a);
		 starta =cont.getStart();
		Window test = starta.getWindow("Aenderungsantrag");
		if(test != null){
			starta.removeWindow(test);	
		}
		
		Requw = new Window("");
		Requw.setName("Aenderungsantrag");
		starta.addWindow(Requw);
		
		titlecon = new Label(b.gettitle());
		lpcon = new Label(String.valueOf(b.getlp()));
		languagecon = new Label(b.getlanguage());
		turncon = new Label(b.getturn());
		responsibleidcon = new Label(String.valueOf(b.getresponsibleid()));
		dozcon = new Label(b.getdoz());
		filingcon = new Label(b.getfiling());
		requirementscon = new Label(b.getrequirements());
		aimscon = new Label(b.getaims());
		contentcon = new Label(b.getcontent());
		litcon = new Label(b.getlit());
		eventscon = new Label(b.getevents());
		workcon = new Label(b.getwork());
		examscon = new Label(b.getexams());
		formcondcon = new Label(b.getformcond());
		gradescon = new Label(b.getgrades());
		responsiblecon = new Label(b.getresponsible());
		
		confirm = new Button("bestätigen");
		confirm.addListener(this);
		deny = new Button("verwerfen");
		deny.addListener(this);
		buildMainLayout();
		Requw.setContent(mainLayout);
		
		tmp = b;
		tmp2 = d;
		Window old = starta.getWindow("Aenderungsantraege");
		oldURL = old.getURL();
		old.open(new ExternalResource(Requw.getURL()));
	}
	
	//ButtonListener
	public void buttonClick(Button.ClickEvent event){
		
		if (event.getButton() == confirm){
			cont.getcDe().okRequest(tmp, tmp2, true);
		}
		if (event.getButton() == deny){
			cont.getcDe().okRequest(tmp, tmp2,false);
		}
		if(event.getButton()==logout){
			starta.getMainWindow().getApplication().close();
		}
		if(event.getButton()==back){
			Requw.open(new ExternalResource(oldURL));
		}
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
		mainLayout.setHeight("400.0%");
		
		// title
		title = new Panel();
		title.setCaption("Name:*");
		title.addComponent(titlecon);
		title.setImmediate(false);
		title.setWidth("66.5%");
		title.setHeight("100px");
		mainLayout.addComponent(title, "top:9.5%;left:25.0%;");
		
		// lp
		lp = new Panel();
		lp.setCaption("Leistungspunkte:*");
		lp.addComponent(lpcon);
		lp.setImmediate(false);
		lp.setWidth("66.5%");
		lp.setHeight("100px");
		mainLayout.addComponent(lp, "top:15.0%;left:25.0%;");
		
		// language
		language = new Panel();
		language.setCaption("Sprache:");
		language.addComponent(languagecon);
		language.setImmediate(false);
		language.setWidth("66.5%");
		language.setHeight("100px");
		mainLayout.addComponent(language, "top:20.5%;left:25.0%;");
		
		// turn
		turn = new Panel();
		turn.setCaption("Rythmus:");
		turn.addComponent(turncon);
		turn.setImmediate(false);
		turn.setWidth("66.5%");
		turn.setHeight("100px");
		mainLayout.addComponent(turn, "top:26.0%;left:25.0%;");
		
		// responsible
		responsible = new Panel();
		responsible.setCaption("Dekan:*");
		responsible.addComponent(responsiblecon);
		responsible.setImmediate(false);
		responsible.setWidth("66.5%");
		responsible.setHeight("100px");
		mainLayout.addComponent(responsible, "top:31.5%;left:25.0%;");
		
		// doz
		doz = new Panel();
		doz.setCaption("Dozent:*");
		doz.addComponent(dozcon);
		doz.setImmediate(false);
		doz.setWidth("66.5%");
		doz.setHeight("100px");
		mainLayout.addComponent(doz, "top:37.0%;left:25.0%;");
		
		// filing
		filing = new Panel();
		filing.setCaption("Einordnung:");
		filing.addComponent(filingcon);
		filing.setImmediate(false);
		filing.setWidth("66.5%");
		filing.setHeight("100px");
		mainLayout.addComponent(filing, "top:42.5%;left:25.0%;");
		
		// requirements
		requirements = new Panel();
		requirements.setCaption("Voraussetzungen:");
		requirements.addComponent(requirementscon);
		requirements.setImmediate(false);
		requirements.setWidth("66.5%");
		requirements.setHeight("100px");
		mainLayout.addComponent(requirements, "top:48.0%;left:25.0%;");
		
		// aims
		aims = new Panel();
		aims.setCaption("Lernziele");
		aims.addComponent(aimscon);
		aims.setImmediate(false);
		aims.setWidth("66.5%");
		aims.setHeight("100px");
		mainLayout.addComponent(aims, "top:53.5%;left:25.0%;");
		
		// content
		content = new Panel();
		content.setCaption("Inhalt:");
		content.addComponent(contentcon);
		content.setImmediate(false);
		content.setWidth("66.5%");
		content.setHeight("100px");
		mainLayout.addComponent(content, "top:59.0%;left:25.0%;");
		
		// lit
		lit = new Panel();
		lit.setCaption("Literatur:");
		lit.addComponent(litcon);
		lit.setImmediate(false);
		lit.setWidth("66.5%");
		lit.setHeight("100px");
		mainLayout.addComponent(lit, "top:64.5%;left:25.0%;");
		
		// events
		events = new Panel();
		events.setCaption("Lehrveranstaltungen:");
		events.addComponent(eventscon);
		events.setImmediate(false);
		events.setWidth("66.5%");
		events.setHeight("100px");
		mainLayout.addComponent(events, "top:70.0%;left:25.0%;");
		
		// work
		work = new Panel();
		work.setCaption("Arbeitsaufwand:");
		work.addComponent(workcon);
		work.setImmediate(false);
		work.setWidth("66.5%");
		work.setHeight("100px");
		mainLayout.addComponent(work, "top:75.5%;left:25.0%;");
		
		// exams
		exams = new Panel();
		exams.setCaption("Leistungsnachweis:");
		exams.addComponent(examscon);
		exams.setImmediate(false);
		exams.setWidth("66.5%");
		exams.setHeight("100px");
		mainLayout.addComponent(exams, "top:81.0%;left:25.0%;");
		
		// formcond
		formcond = new Panel();
		formcond.setCaption("Voraussetzungen formal:");
		formcond.addComponent(formcondcon);
		formcond.setImmediate(false);
		formcond.setWidth("66.5%");
		formcond.setHeight("100px");
		mainLayout.addComponent(formcond, "top:86.5%;left:25.0%;");
		
		// grades
		grades = new Panel();
		grades.setCaption("Notenbildung:");
		grades.addComponent(gradescon);
		grades.setImmediate(false);
		grades.setWidth("66.5%");
		grades.setHeight("100px");
		mainLayout.addComponent(grades, "top:92.0%;left:25.0%;");
		
		// confirm
		confirm.setImmediate(true);
		confirm.setWidth("-1px");
		confirm.setHeight("-1px");
		mainLayout.addComponent(confirm, "top:97%;left:25.0%;");
		
		// deny
		deny.setImmediate(true);
		deny.setWidth("-1px");
		deny.setHeight("-1px");
		mainLayout.addComponent(deny, "top:97%;left:35.0%;");
				
		// label
		label = new Label();
		label.setImmediate(false);
		label.setWidth("-1px");
		label.setHeight("-1px");
		label.setValue("Modulvorschlag");
		label.setStyleName(Runo.LABEL_H1);
		mainLayout.addComponent(label, "top:6.2%;left:25.0%;");
				
		// logout
		logout = new Button();
		logout.setCaption("logout");
		logout.setImmediate(false);
		logout.setWidth("-1px");
		logout.setHeight("-1px");
		logout.setStyleName(BaseTheme.BUTTON_LINK);
		logout.addListener(this);
		mainLayout.addComponent(logout, "top:99%;left:25.0%;");
				
		// back
		back = new Button();
		back.setCaption("Abbruch");
		back.setImmediate(true);
		back.setWidth("-1px");
		back.setHeight("-1px");
		back.setStyleName(BaseTheme.BUTTON_LINK);
		back.addListener(this);
		mainLayout.addComponent(back, "top:98.2%;left:25.0%;");
		
		return mainLayout;
	}
}

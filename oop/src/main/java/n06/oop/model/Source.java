package n06.oop.model;

import n06.oop.database.Setting;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFBean;
import org.cyberborean.rdfbeans.annotations.RDFSubject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RDFBean(Setting.PREFIX_PROPERTY + "/sources")
public class Source {
	
	final static String DATE_FORMAT = "dd/MM/yyyy";
	final static DateFormat D_FORMAT = new SimpleDateFormat(DATE_FORMAT);
	
	private String link;
	private Date date;
	
	public Source(String link, String date) {
		// TODO Auto-generated constructor stub
		setLink(link);
		setDate(date);
	}

	@RDF(Setting.PREFIX_PROPERTY + "/link")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@RDF(Setting.PREFIX_PROPERTY + "/date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date) {
		try {
			 this.date = D_FORMAT.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.date = new Date();
		}
	}
	
}

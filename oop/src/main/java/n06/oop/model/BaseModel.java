package n06.oop.model;

import n06.oop.database.Setting;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFBean;
import org.cyberborean.rdfbeans.annotations.RDFSubject;

import java.util.List;

public class BaseModel {
	protected String id;
	protected String name;
	protected String description;
	protected List<Source> sources;


	@RDFSubject(prefix = Setting.PEEFIX_MODEL + "#")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@RDF(value = Setting.PREFIX_PROPERTY + ":name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@RDF(value = Setting.PREFIX_PROPERTY + ":description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@RDF(value = Setting.PREFIX_PROPERTY + ":sources")
	public List<Source> getSources() {
		return sources;
	}

	public void setSources(List<Source> sources) {
		for (int i=0; i< sources.size(); i++) {
			Source source = sources.get(i);
			source.setId(this.id + "_" + i);
		}
		this.sources = sources;
	}
}

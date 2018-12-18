package n06.oop.model.entities;

import java.util.List;

public class BaseEntity {
	protected String id;
	protected String name;
	protected String description;
	protected List<Source> sources;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public String getType() {
		return null;
	}
}

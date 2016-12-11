package hu.unideb.inf.tennis.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tournament {
	
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String type;
	@XmlAttribute
	private String surface;
	@XmlElementWrapper
    @XmlElement(name="match")
	private List<Match> finals;
	@XmlElementWrapper(name = "semi-finals")
    @XmlElement(name="match")
	private List<Match> semiFinals;
	@XmlElementWrapper(name = "quarter-finals")
    @XmlElement(name="match")
	private List<Match> quarterFinals;
	
	public Tournament() {
		super();
	}

	public Tournament(String name, String type, String surface, List<Match> finals, List<Match> semiFinals,
			List<Match> quarterFinals) {
		super();
		this.name = name;
		this.type = type;
		this.surface = surface;
		this.finals = finals;
		this.semiFinals = semiFinals;
		this.quarterFinals = quarterFinals;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public List<Match> getFinals() {
		return finals;
	}

	public void setFinals(List<Match> finals) {
		this.finals = finals;
	}

	public List<Match> getSemiFinals() {
		return semiFinals;
	}

	public void setSemiFinals(List<Match> semiFinals) {
		this.semiFinals = semiFinals;
	}

	public List<Match> getQuarterFinals() {
		return quarterFinals;
	}

	public void setQuarterFinals(List<Match> quarterFinals) {
		this.quarterFinals = quarterFinals;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\n" + "Type: " + type + "\n" + "Surface: " + surface + "\n" + "Finals: " + finals + "\n" 
				+ "Semi-Finals: " + semiFinals + "\n" + "Quarter-Finals: " + quarterFinals + "\n";
	}
}

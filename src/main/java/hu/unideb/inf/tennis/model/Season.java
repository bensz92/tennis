package hu.unideb.inf.tennis.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tennis_season")
@XmlAccessorType(XmlAccessType.FIELD)
public class Season {
	
	@XmlAttribute
	private int year;
	
	@XmlElementWrapper(name="tournaments")
    @XmlElement(name="tournament")
	private List<Tournament> tournaments;
	
	public Season() {
		super();
	}
	
	public Season(int year, List<Tournament> tournaments) {
		super();
		this.year = year;
		this.tournaments = tournaments;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public List<Tournament> getTournaments() {
		return tournaments;
	}
	
	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}
	
	@Override
	public String toString() {
		return "Season [year=" + year + ", tournaments=" + tournaments + "]";
	}
}

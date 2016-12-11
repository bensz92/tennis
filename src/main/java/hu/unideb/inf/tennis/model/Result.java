package hu.unideb.inf.tennis.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "match")
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
		
    @XmlElement(name="set")
	List<Set> sets;
	
	public Result() {
		super();
	}

	public Result(List<Set> s){
		sets = s;
	}
	
	public List<Set> getSets() {
		return sets;
	}

	public void setSets(List<Set> sets) {
		this.sets = sets;
	}

	@Override
	public String toString() {
		return "Result: " + sets;
	}
}

package hu.unideb.inf.tennis.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Player{
	
	@XmlAttribute
	private String id;
	@XmlElement
	private String name;
	@XmlElement(name = "year_of_birth")
	private int yearOfBirth;
	@XmlElement
	private String birthplace;
	@XmlElement
	private int weight;
	@XmlElement
	private int height;
	@XmlElement(name = "turnedpro")
	private int turnedPro;
	@XmlElement
	private String plays;
	
	public Player() {
		super();
	}

	public Player(String id, String name, int yearOfBirth, String birthplace, int weight, int height, int turnedPro,
			String plays) {
		super();
		this.id = id;
		this.name = name;
		this.yearOfBirth = yearOfBirth;
		this.birthplace = birthplace;
		this.weight = weight;
		this.height = height;
		this.turnedPro = turnedPro;
		this.plays = plays;
	}

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
	
	public int getYearOfBirth() {
		return yearOfBirth;
	}
	
	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	
	public String getBirthplace() {
		return birthplace;
	}
	
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getTurnedPro() {
		return turnedPro;
	}
	
	public void setTurnedPro(int turnedPro) {
		this.turnedPro = turnedPro;
	}
	
	public String getPlays() {
		return plays;
	}
	
	public void setPlays(String plays) {
		this.plays = plays;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", yearOfBirth=" + yearOfBirth + ", birthplace=" + birthplace
				+ ", weight=" + weight + ", height=" + height + ", turnedPro=" + turnedPro + ", plays=" + plays + "]";
	}
}

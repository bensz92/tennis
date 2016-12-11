package hu.unideb.inf.tennis.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "set")
@XmlAccessorType(XmlAccessType.NONE)
public class Set{
	
	@XmlValue
	private String value;
	private int p1;
	private int p2;
	private boolean ret = false;

	public Set() {
		super();
	}

	public Set(String set){
		if(set.equals("RET"))
			ret=true;
		else{
			String [] parts = set.split(":"); 
			p1 = Integer.valueOf(parts[0]);
			p2 = Integer.valueOf(parts[1]);
			value = set;
		}
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getP1() {
		return p1;
	}

	public void setP1(int p1) {
		this.p1 = p1;
	}

	public int getP2() {
		return p2;
	}

	public void setP2(int p2) {
		this.p2 = p2;
	}

	public boolean isRet() {
		return ret;
	}

	public void setRet(boolean ret) {
		this.ret = ret;
	}

	@Override
	public String toString() {
		return "Set ["+ value +"]";
	}
}

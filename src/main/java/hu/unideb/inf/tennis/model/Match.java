package hu.unideb.inf.tennis.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="match")
@XmlAccessorType(XmlAccessType.FIELD)
public class Match {
	
	@XmlElementWrapper(name="players")
	@XmlElement(name = "player-ref")	
	private List<PlayerRef> playerRefs;
	@XmlElement
	private Result result;

	public Match() {
		super();
	}

	public Match(List<PlayerRef> playerRefs, Result result) {
		super();
		this.playerRefs = playerRefs;
		this.result = result;
	}

	public List<PlayerRef> getPlayerRefs() {
		return playerRefs;
	}

	public void setPlayerRefs(List<PlayerRef> playerRefs) {
		this.playerRefs = playerRefs;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Players:" + playerRefs + " " + result;
	}
}

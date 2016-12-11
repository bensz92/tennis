package hu.unideb.inf.tennis.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerRef {
	
	@XmlAttribute(name = "id")
	private String playerId;

	public PlayerRef() {
		super();
	}

	public PlayerRef(String playerId) {
		super();
		this.playerId = playerId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@Override
	public String toString() {
		return "PlayerRef [playerId=" + playerId + "]";
	}
}

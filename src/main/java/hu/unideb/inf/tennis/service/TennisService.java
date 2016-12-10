package hu.unideb.inf.tennis.service;

import java.util.List;
import hu.unideb.inf.tennis.model.Player;

public interface TennisService {
	
	//Player
	public Player findPlayerById(String id);
	public List<Player> findAllPlayers();
	public boolean addPlayer(Player player);
	public boolean removePlayer(Player player);
	public boolean updatePlayerWeight(String id, int weight);
	public boolean updatePlayerHeight(String id, int height);

}
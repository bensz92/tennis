package hu.unideb.inf.tennis.service;

import java.util.List;
import hu.unideb.inf.tennis.model.Match;
import hu.unideb.inf.tennis.model.Player;
import hu.unideb.inf.tennis.model.Season;
import hu.unideb.inf.tennis.model.Tournament;

public interface TennisService {
	
	//Player
	public Player findPlayerById(String id);
	public List<Player> findAllPlayers();
	public boolean addPlayer(Player player);
	public boolean removePlayer(Player player);
	public boolean updatePlayerWeight(String id, int weight);
	public boolean updatePlayerHeight(String id, int height);
	
	//Tournament
	public Tournament findTournamentByNameAndYear(String name, int year);
	public List<Tournament> findAllTournamentsByYear(int year);
	
	public boolean addTournament(int year, String name, String type, String surface);
	public boolean addMatchToFinals(int year, String name, Match match);
	public boolean addMatchToSemiFinals(int year, String name, Match match);
	public boolean addMatchToQuarterFinals(int year, String name, Match match);
	
	public boolean removeTournament(String name, int year);
	
	public boolean updateTournament();
	public boolean updateTournamentFinals();
	public boolean updateTournamentSemiFinals();
	public boolean updateTournamentQuarterFinals();
	public boolean updateMatchList();
	public boolean updateMatchPlayers(String p1, String p2);
	public boolean updateMatchResult();
	
	//Season
	public Season findSeasonByYear(int year);
	public List<Season> findAllSeasons();
	public boolean addSeason(int year);
	public boolean removeSeason(int year);
}
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
	
	public boolean addTournament();
	public boolean addFinalsToTournament();
	public boolean addSemiFinalsToTournament();
	public boolean addQuarterFinalsToTournament();
	public boolean addMatchToList();
	public boolean addPlayersToMatch(String p1, String p2);
	public boolean addResultToMatch();
	
	public boolean updateTournament();
	public boolean updateTournamentFinals();
	public boolean updateTournamentSemiFinals();
	public boolean updateTournamentQuarterFinals();
	public boolean updateMatchList();
	public boolean updateMatchPlayers(String p1, String p2);
	public boolean updateMatchResult();
	
	public boolean removeTournament(String name, int year);
	
	//Season
	public Season findSeasonByYear(int year);
	public List<Season> findAllSeasons();
	public boolean addSeason(int year);
	public boolean removeSeason(int year);
}
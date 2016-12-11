package hu.unideb.inf.tennis.service;

import java.util.List;
import hu.unideb.inf.tennis.model.Match;
import hu.unideb.inf.tennis.model.Player;
import hu.unideb.inf.tennis.model.Season;
import hu.unideb.inf.tennis.model.Tournament;

public interface TennisService {

	// Player
	public Player findPlayerById(String id);

	public List<Player> findAllPlayers();

	public boolean addPlayer(Player player);

	public boolean removePlayer(Player player);

	public boolean updatePlayerWeight(String id, int weight);

	public boolean updatePlayerHeight(String id, int height);

	// Tournament
	public Tournament findTournamentByNameAndYear(String name, int year);

	public List<Tournament> findAllTournamentsByYear(int year);

	public boolean addTournament(int year, String name, String type, String surface);

	public boolean addMatchToFinals(int year, String name, Match match);

	public boolean addMatchToSemiFinals(int year, String name, Match match);

	public boolean addMatchToQuarterFinals(int year, String name, Match match);

	public boolean removeTournament(String name, int year);

	public boolean updateTournament(int year, String name, String newName, String newType, String newSurface);

	public boolean updateFinalsMatch(int year, String name, Match match);

	public boolean updateSemiFinals(int year, String name, List<Match> matches);

	public boolean updateQuarterFinals(int year, String name, List<Match> matches);

	// Season
	public Season findSeasonByYear(int year);

	public List<Season> findAllSeasons();

	public boolean addSeason(int year);

	public boolean removeSeason(int year);
	
	public List<String> findAllYears();
}
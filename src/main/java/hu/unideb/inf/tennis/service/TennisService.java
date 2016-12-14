package hu.unideb.inf.tennis.service;

import java.util.List;
import java.util.Set;

import hu.unideb.inf.tennis.model.Match;
import hu.unideb.inf.tennis.model.Player;
import hu.unideb.inf.tennis.model.Season;
import hu.unideb.inf.tennis.model.Tournament;

public interface TennisService {

	// Player
	public Player findPlayerById(String id);

	public List<Player> findAllPlayers();

	public boolean addPlayer(Player player);

	public boolean removePlayer(String id);

	public boolean updatePlayer(Player updatedPlayer);

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
	
	//Other
	public List<String> findAllYears();
	
	public Match findMatchForUpdate(int year, String tournamentName, String matchType, String p1, String p2);
	
	public boolean updateMatch(int year, String tournamentName, String matchType, String p1, String p2, Match updatedMatch);
	
	//public Set<Player> getPlayersParticipatedTournament(int year, String name);
	
	//public Player getTournamentWinner(int year, String name);
	
	//Azok a playerek akik nyertek grand slament
	
	//a legtöbbször döntőt játszó player
	
	//az a 2 player akik a legtöbbször játszottak egymás ellen
	
	//a legidősebb játékos aki masters-t nyert
	
	//a döntősök "átlaga" életkor, magasság súly
	
	//az a player aki a legkevesebb gamet vesztette egy tornán
	//a legjobb game aránnyal bajnok player
	
	//az a player aki setvesztés nélkül nyert meccset
	
	//azok a playerek akik úgy nyertek meeccset, hogy feladták ellenük
	
	//azon döntősök aki 2000 után váltak profivá
	
	//azok playerek akik ugyanonnan származnak és játszottak egymással
	
	//Azok a playerek akiknek jobb a gamearánya mint Nadalnak
	
	// a legtöbb salakos tornát nyert player
}
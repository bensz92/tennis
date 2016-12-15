package hu.unideb.inf.tennis.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQItemType;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import hu.unideb.inf.tennis.jaxb.JAXBUtil;
import hu.unideb.inf.tennis.model.Match;
import hu.unideb.inf.tennis.model.Player;
import hu.unideb.inf.tennis.model.Season;
import hu.unideb.inf.tennis.model.Tournament;
import hu.unideb.inf.tennis.view.CustomTabPanel;

public class TennisServiceImpl implements TennisService{
	
	private XQConnection connection;
	private final String DB = "tennis";
	
	public TennisServiceImpl(XQConnection conn) {
		connection = conn;
	}

	@Override
	public Player findPlayerById(String id){
		
		Player player = null;

		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					    "declare variable $DB external;"
					    + " declare variable $id external;"
					    + " for $player in db:open($DB)//root/players/player[@id=$id]"
					    + " return $player");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
			expr.bindString(new QName("id"), id, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			if(rs.next())
				player = JAXBUtil.fromXML(Player.class, rs.getItemAsString(null));
			
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}

		return player;
	}

	@Override
	public List<Player> findAllPlayers() {
		
		List<Player> allPlayerList = new ArrayList<>();
		
		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					    "declare variable $DB external;"
					    + " for $player in db:open($DB)//root/players/*"
					    + " return $player");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			while(rs.next())
				allPlayerList.add(JAXBUtil.fromXML(Player.class, rs.getItemAsString(null)));
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}
		
		return allPlayerList;
	}

	@Override
	public boolean addPlayer(Player player) {
		
		if(findPlayerById(player.getId()) != null)
			return false;
		else
		{
			try {
				String playerXML = JAXBUtil.toXML(player);
				
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $newPlayer external;"
						    + " insert node ($newPlayer) into db:open($DB)//root/players");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("newPlayer"), playerXML, null,null);				
				expr.executeQuery();
				
			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		}
		
	}

	@Override
	public boolean removePlayer(String id) {
		
		if(findPlayerById(id) == null)
			return false;
		else
		{
			try {				
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $id external;"
						    + " delete node db:open($DB)//root/players/player[@id=$id]");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindString(new QName("id"), id, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));				
				expr.executeQuery();
				
			} catch (XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public Tournament findTournamentByNameAndYear(String name, int year) {
		Tournament tournament = null;

		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					    "declare variable $DB external;"
					    + " declare variable $name external;"
					    + " declare variable $year external;"
					    + " for $tournament in db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name]"
					    + " return $tournament");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
			expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
			expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));

			XQResultSequence rs = expr.executeQuery();
			if(rs.next())
				tournament = JAXBUtil.fromXML(Tournament.class, rs.getItemAsString(null));
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}

		return tournament;
	}
	
	@Override
	public List<Tournament> findAllTournamentsByYear(int year) {
		List<Tournament> allTournamentList = new ArrayList<>();	
		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					    "declare variable $DB external;"
					 	+ "declare variable $year external;"
					    + " for $tournament in db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/*"
					    + " return $tournament");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
			expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));

			XQResultSequence rs = expr.executeQuery();
			while(rs.next())
				allTournamentList.add(JAXBUtil.fromXML(Tournament.class, rs.getItemAsString(null)));
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}
		
		return allTournamentList;
	}

	@Override
	 public boolean addTournament(int year, String name, String type, String surface) {
	  if(findTournamentByNameAndYear(name, year) != null)
	   return false;
	  else
	  {
	   try {
	    Tournament newTournament = new Tournament(name, type, surface, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	    String tournamentXML = JAXBUtil.toXML(newTournament);
	    
	     XQPreparedExpression expr = connection.prepareExpression(
	          "declare variable $DB external;"
	        + "declare variable $year external;"
	        +" declare variable $newTournament external;"
	          + " insert node ($newTournament) into db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments");
	    
	    expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
	    expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
	    expr.bindDocument(new QName("newTournament"), tournamentXML, null,null);    
	    expr.executeQuery();
	    
	   } catch (JAXBException | XQException e) {
	    e.printStackTrace();
	   }
	   return true;
	  }
	 }

	@Override
	public boolean addMatchToFinals(int year, String name, Match match) {
		
		Tournament tournament; 
		if ((tournament = findTournamentByNameAndYear(name, year)) == null)
			return false;
		else {
			try {
				if(tournament.getFinals() != null && tournament.getFinals().size() == 1)
					return false;
				
				String matchXML = JAXBUtil.toXML(match);

				XQPreparedExpression expr = connection.prepareExpression(
						"declare variable $DB external;"
						+ "declare variable $year external;" 
						+ "declare variable $name external;" 
						+ " declare variable $newMatch external;"
						+ " insert node ($newMatch) into db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name]/finals");

				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
				expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("newMatch"), matchXML, null, null);
				expr.executeQuery();

			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public boolean addMatchToSemiFinals(int year, String name, Match match) {
		Tournament tournament; 
		if ((tournament = findTournamentByNameAndYear(name, year)) == null)
			return false;
		else {
			try {
				if(tournament.getSemiFinals() != null && tournament.getSemiFinals().size() == 2)
					return false;
				
				String matchXML = JAXBUtil.toXML(match);

				XQPreparedExpression expr = connection.prepareExpression(
						"declare variable $DB external;"
						+ "declare variable $year external;" 
						+ "declare variable $name external;" 
						+ " declare variable $newMatch external;"
						+ " insert node ($newMatch) into db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name]/semi-finals");

				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
				expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("newMatch"), matchXML, null, null);
				expr.executeQuery();

			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public boolean addMatchToQuarterFinals(int year, String name, Match match) {
		Tournament tournament; 
		if ((tournament = findTournamentByNameAndYear(name, year)) == null)
			return false;
		else {
			try {
				if(tournament.getQuarterFinals() != null && tournament.getQuarterFinals().size() == 4)
					return false;
				
				String matchXML = JAXBUtil.toXML(match);

				XQPreparedExpression expr = connection.prepareExpression(
						"declare variable $DB external;"
						+ "declare variable $year external;" 
						+ "declare variable $name external;" 
						+ " declare variable $newMatch external;"
						+ " insert node ($newMatch) into db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name]/quarter-finals");

				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
				expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("newMatch"), matchXML, null, null);
				expr.executeQuery();

			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	
	@Override
	public boolean removeTournament(String name, int year) {
		if (findTournamentByNameAndYear(name, year) == null)
			return false;
		else {
			try {
				XQPreparedExpression expr = connection
						.prepareExpression(
								"declare variable $DB external;"
								+ " declare variable $year external;"
								+ " declare variable $name external;"
								+ " delete node db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name]");

				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year,connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
				expr.bindString(new QName("name"), name,connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.executeQuery();

			} catch (XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public boolean updateTournament(int year, String name, String newName, String newType, String newSurface) {
		Tournament tournament;
		if((tournament = findTournamentByNameAndYear(name, year)) == null)
			return false;
		else
		{
			try {
				tournament.setName(newName);
				tournament.setType(newType);
				tournament.setSurface(newSurface);
				String updatedTournament = JAXBUtil.toXML(tournament);
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $year external;"
						 	+" declare variable $name external;"
						 	+" declare variable $updatedTournament external;"
						    + " replace node db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name] with $updatedTournament");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
				expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("updatedTournament"), updatedTournament, null,null);				
				expr.executeQuery();
				
			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public boolean updateFinalsMatch(int year, String name, Match match) {
		Tournament tournament;
		if((tournament = findTournamentByNameAndYear(name, year)) == null){
			return false;
		} else if (tournament.getFinals() != null && tournament.getFinals().size()==1)	{
			try {
				List<Match> newFinals = new ArrayList<>();
				newFinals.add(match);
				tournament.setFinals(newFinals);
				String updatedTournament = JAXBUtil.toXML(tournament);
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $year external;"
						 	+" declare variable $name external;"
						 	+" declare variable $updatedTournament external;"
						    + " replace node db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name] with $updatedTournament");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
				expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("updatedTournament"), updatedTournament, null,null);				
				expr.executeQuery();
				
			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateSemiFinals(int year, String name, List<Match> newSemiFinals) {
		Tournament tournament;
		if((tournament = findTournamentByNameAndYear(name, year)) == null){
			return false;
		} else if (tournament.getSemiFinals() != null && tournament.getSemiFinals().size() > 0)	{
			try {
				tournament.setSemiFinals(newSemiFinals);
				String updatedTournament = JAXBUtil.toXML(tournament);
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $year external;"
						 	+" declare variable $name external;"
						 	+" declare variable $updatedTournament external;"
						    + " replace node db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name] with $updatedTournament");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
				expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("updatedTournament"), updatedTournament, null,null);				
				expr.executeQuery();
				
			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateQuarterFinals(int year, String name, List<Match> newQuarterFinals) {
		Tournament tournament;
		if((tournament = findTournamentByNameAndYear(name, year)) == null){
			return false;
		} else if (tournament.getQuarterFinals() != null && tournament.getQuarterFinals().size() > 0)	{
			try {
				tournament.setQuarterFinals(newQuarterFinals);
				String updatedTournament = JAXBUtil.toXML(tournament);
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $year external;"
						 	+" declare variable $name external;"
						 	+" declare variable $updatedTournament external;"
						    + " replace node db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name] with $updatedTournament");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
				expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("updatedTournament"), updatedTournament, null,null);				
				expr.executeQuery();
				
			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Season findSeasonByYear(int year) {
		Season season = null;

		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					    "declare variable $DB external;"
					    + " declare variable $year external;"
					    + " for $season in db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]"
					    + " return $season");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
			expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));

			XQResultSequence rs = expr.executeQuery();
			if(rs.next())
				season = JAXBUtil.fromXML(Season.class, rs.getItemAsString(null));
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}

		return season;
	}

	@Override
	public List<Season> findAllSeasons() {
		List<Season> seasons = new ArrayList<>();

		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					    "declare variable $DB external;"
					    + " for $season in db:open($DB)//root/tennis_seasons/*"
					    + " return $season");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			while(rs.next())
				seasons.add(JAXBUtil.fromXML(Season.class, rs.getItemAsString(null)));
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}
		return seasons;
	}

	@Override
	public boolean addSeason(int year) {
		if(findSeasonByYear(year) != null)
			return false;
		else
		{
			try {
				String seasonXML = JAXBUtil.toXML(new Season(year, new ArrayList<>()));
				
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $newSeason external;"
						    + " insert node ($newSeason) into db:open($DB)//root/tennis_seasons");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("newSeason"), seasonXML, null,null);				
				expr.executeQuery();
				
			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public boolean removeSeason(int year) {
		if(findSeasonByYear(year) == null)
			return false;
		else
		{
			try {				
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $year external;"
						    + " delete node db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));				
				expr.executeQuery();
				
			} catch (XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public List<String> findAllYears() {
		List<String> years = new ArrayList<>();

		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					    "declare variable $DB external;"
					    + " for $season in db:open($DB)//root/tennis_seasons/*"
					    + " return $season");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			while(rs.next()){
				Season season = JAXBUtil.fromXML(Season.class, rs.getItemAsString(null));
				years.add(String.valueOf(season.getYear()));
			}
				
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}
		return years;
	}

	@Override
	public boolean updatePlayer(Player updatedPlayer) {
		if(findPlayerById(updatedPlayer.getId()) == null)
			return false;
		else
		{
			try {
				String UP = JAXBUtil.toXML(updatedPlayer);
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $id external;"
						 	+" declare variable $UP external;"
						    + " replace node db:open($DB)//root/players/player[@id=$id] with $UP");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindString(new QName("id"), updatedPlayer.getId(), connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("UP"), UP, null,null);				
				expr.executeQuery();
				
			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public Match findMatchForUpdate(int year, String tournamentName, String matchType, String p1,
			String p2) {
		List<Match> matches = new ArrayList<>();
		Tournament t = findTournamentByNameAndYear(tournamentName, year);
		switch (matchType) {
		case CustomTabPanel.FINAL:
			matches = t.getFinals();
			break;
		case CustomTabPanel.SEMIFINAL:
			matches = t.getSemiFinals();
			break;
		case CustomTabPanel.QUARTERFINAL:
			matches = t.getQuarterFinals();
			break;

		default:
			break;
		}
		
		for(Match m : matches){
			if(m.getPlayerRefs().get(0).getPlayerId().equals(p1) && m.getPlayerRefs().get(1).getPlayerId().equals(p2))
				return m;
		}
		return null;
	}

	@Override
	public boolean updateMatch(int year, String tournamentName, String matchType, String p1, String p2, Match updatedMatch) {
		Match match = findMatchForUpdate(year, tournamentName, matchType, p1, p2);
		
		List<Match> matches = new ArrayList<>();
		Tournament t = findTournamentByNameAndYear(tournamentName, year);
		switch (matchType) {
		case CustomTabPanel.FINAL:
			matches = t.getFinals();
			break;
		case CustomTabPanel.SEMIFINAL:
			matches = t.getSemiFinals();
			break;
		case CustomTabPanel.QUARTERFINAL:
			matches = t.getQuarterFinals();
			break;
		default:
			break;
		}
		
		Match mToUpdate = new Match();
		
		for(Match m : matches){
			if(m.getPlayerRefs().get(0).getPlayerId().equals(p1) && m.getPlayerRefs().get(1).getPlayerId().equals(p2)){
				m = updatedMatch;
				mToUpdate = m;
			}
		}
	
		if(mToUpdate == null){
			return false;
		} else {
			switch (matchType) {
			case CustomTabPanel.FINAL:
				updateFinalsMatch(year, tournamentName, mToUpdate);
				break;
			case CustomTabPanel.SEMIFINAL:
				updateSemiFinals(year, tournamentName, matches);
				break;
			case CustomTabPanel.QUARTERFINAL:
				updateQuarterFinals(year, tournamentName, matches);
				break;
			default:
				break;
			}
			
			return true;
		} 
	}

	@Override
	public List<Player> getPlayersParticipatedTournament(int year, String name) {

		List<Player> playerList = new ArrayList<>();
		
		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					 	"declare variable $DB external;"
					 	+" declare variable $year external;"
					 	+" declare variable $name external;"
					 	+" let $r := db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name]"
						+" let $matches := (for $match in ($r/finals/*, $r/semi-finals/*, $r/quarter-finals/*)"
						+" return $match)"
						+" let $players := (for $player in ($matches)/players/*/@id return $player)"
						+" let $distinctPlayerIds := distinct-values($players)"
						+" for $id in $distinctPlayerIds"
						+" for $p in db:open($DB)//root/players/*"
						+" where $id = $p/@id"
						+" return $p");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
			expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
			expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			while(rs.next())
				playerList.add(JAXBUtil.fromXML(Player.class, rs.getItemAsString(null)));
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}
		return playerList;
	}

	@Override
	public Player getTournamentWinner(int year, String name) {
		
		Player player = null;

		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					    "declare variable $DB external;"
					 	+" declare variable $year external;"
					 	+" declare variable $name external;"
					    +" let $id := (for $player in db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournaments/tournament[@name=$name]/finals/match/players/*"
					    +" return $player)[1]/@id"
					    +" for $p in db:open($DB)//root/players/*"
					    +" where $id = $p/@id"
					    +" return $p");
			
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
				expr.bindString(new QName("name"), name, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			if(rs.next())
				player = JAXBUtil.fromXML(Player.class, rs.getItemAsString(null));
			
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}

		return player;
	}

	@Override
	public List<Player> getPlayersWhoHasWonTournamentByType(String type) {

		List<Player> playerList = new ArrayList<>();
		
		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					 	"declare variable $DB external;"
					 	+" declare variable $type external;"
					 	+" let $gs := (for $t in db:open($DB)//root/tennis_seasons/tennis_season/tournaments/tournament[@type=$type]"
					 	+" return $t)"
					 	+" let $finals := (for $final in $gs/finals/*"
					 	+" return $final)"
					 	+" let $winners := (for $player in $finals/players/player-ref[1]/@id return $player)"
					 	+" let $distinctWinners := distinct-values($winners)"
					 	+" for $dw in $distinctWinners"
					 	+" for $p in db:open($DB)//root/players/*"
					 	+" where $dw = $p/@id"
					 	+" return $p");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
			expr.bindString(new QName("type"), type, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			while(rs.next())
				playerList.add(JAXBUtil.fromXML(Player.class, rs.getItemAsString(null)));
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}
		return playerList;
	}

	@Override
	public String getPlayerWhoHadTheMostFinals() {
		String result = null;
		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					 	"declare variable $DB external;"
					 	+" let $tournaments := (for $t in db:open($DB)//root/tennis_seasons/tennis_season/tournaments/tournament"
					 	+" return $t)"
					 	+" let $finals := (for $final in $tournaments/finals/*"
					 	+" return $final)"
					 	+" let $finalists := (for $player in $finals/players/player-ref return $player)"
					 	+" let $result := (for $finalist in $finalists"
					 	+" let $id := $finalist/@id"
					 	+" group by $id"
					 	+" order by count($finalist) descending"
					 	+" return concat($id, \": \",count($finalist)))[1]"
					 	+" return $result");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			if(rs.next())
				result = rs.getItemAsString(null);
		} catch (XQException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Player getOldestPlayerWhoHasWonTournamentByType(String type) {

		Player player = null;

		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					    "declare variable $DB external;"
					 	+" declare variable $type external;"
					 	+" let $tours := (for $t in db:open($DB)//root/tennis_seasons/tennis_season/tournaments/tournament[@type=$type]"
					 	+" return $t)"
					 	+" let $finals := (for $final in $tours/finals/*"
					 	+" return $final)"
					 	+" let $finalists := distinct-values(for $player in $finals/players/player-ref[1]/@id return $player)"
					 	+" let $players :=("
					 	+" for $finalist in $finalists"
					 	+" for $player in db:open($DB)//root/players/*"
					 	+" where $finalist = $player/@id"
					 	+" order by $player/year_of_birth"
					 	+" return $player)"
					 	+" return $players[1]");
			
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindString(new QName("type"), type, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			if(rs.next())
				player = JAXBUtil.fromXML(Player.class, rs.getItemAsString(null));
			
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}

		return player;
	}

	@Override
	public String getPlayerWhoHasWonMostTournamentsBySurface(String surface) {
		
		String result = null;
		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					 	"declare variable $DB external;"
					 	+" declare variable $surface external;"
					 	+" let $tBySurface := (for $t in db:open($DB)//root/tennis_seasons/tennis_season/tournaments/tournament[@surface=$surface]"
					 	+" return $t)"
					 	+" let $winner := (for $player in $tBySurface/finals/match/players/*[1]"
					 	+" return $player)"
					 	+" let $ result := (for $player in $winner"
					 	+" let $id := $player/@id"
					 	+" group by $id"
					 	+" order by count($player) descending"
					 	+" return concat($id, \": \", count($player)))"
					 	+" return $result[1]");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
			expr.bindString(new QName("surface"), surface, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			if(rs.next())
				result = rs.getItemAsString(null);
		} catch (XQException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public double getPlayersAvgAgeWhoHasWonMatchWithoutLosingSetByYear(int year, String type) {
		String result = null;
		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					 	"declare variable $DB external;"
					 	+" declare variable $year external;"
					 	+" declare variable $type external;"
					 	+" let $sub := (function($x, $y) { $x - $y })"
					 	+" let $setCount := if($type = \"Masters_1000\") then (2) else (3)"
					 	+" let $r := db:open($DB)//root/tennis_seasons/tennis_season/tournaments/tournament[@type=$type]"
					 	+" let $allMatches :=(for $match in ($r/finals/*, $r/semi-finals/*, $r/quarter-finals/*) return $match)"
					 	+" let $matchesNeeded := (for $match in $allMatches"
					 	+" where count($match/result/set) = $setCount"
					 	+" return $match)"
					 	+" let $playersNeeded := (for $match in $matchesNeeded"
					 	+" return $match/players/player-ref[1]/@id)"
					 	+" let $playersNeededId := (for $p in distinct-values($playersNeeded) return $p)" 
					 	+" let $actualPlayers := (for $id in $playersNeededId" 
					 	+" for $p in db:open($DB)//root/players/*" 
					 	+" where $id = $p/@id"
					 	+" return $p)"
					 	+" let $ages :=" 
					 	+" (for $p in $actualPlayers"
					 	+" return $sub ($year, $p/year_of_birth))"
					 	+" return avg($ages)");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
			expr.bindLong(new QName("year"), year, connection.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
			expr.bindString(new QName("type"), type, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			if(rs.next())
			result = rs.getItemAsString(null);
		} catch (XQException e) {
			e.printStackTrace();
		}
		return new Double(result);
	}

	@Override
	public String getTheTwoPlayersWhoHaveBeenPlayedAgainstEachOtherTheMost() {
		
		String result = null;
		
		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					 	"declare variable $DB external;"
					 	+" let $r := db:open($DB)//root/tennis_seasons/tennis_season/tournaments/tournament"
					 	+" let $playerPairs := (for $matches in ($r/finals/match/players, $r/semi-finals/match/players, $r/quarter-finals/match/players)"
					 	+" return $matches)"
					 	+" let $pairsWithCount := (for $playerPair in $playerPairs"
					 	+" let $id1 := $playerPair/player-ref[1]/@id"
					 	+" let $id2 := $playerPair/player-ref[2]/@id" 
					 	+" group by $id1, $id2"
					 	+" order by count($playerPair) descending"
					 	+" return concat($id1, \" \", $id2, \": \", count($playerPair)))"
					 	+" return $pairsWithCount[1]");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			if(rs.next())
				result = rs.getItemAsString(null);
		} catch (XQException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Player> getPlayersWhoHaveWonMastersButNotGrandSlam() {
		
		List<Player> playerList = new ArrayList<>();
		try {
			 XQPreparedExpression expr = connection.prepareExpression(
					 	"declare variable $DB external;"
					 	+" let $mastersWinnersId :=" 
					 	+" distinct-values(for $mWinner in db:open($DB)//root/tennis_seasons/tennis_season/tournaments/tournament[@type=\"Masters_1000\"]/finals/match/players/player-ref[1]/@id return $mWinner)"
					 	+" let $grandslamWinnersId :="
					 	+" distinct-values(for $gsWinner in db:open($DB)//root/tennis_seasons/tennis_season/tournaments/tournament[@type=\"Grand_Slam\"]/finals/match/players/player-ref[1]/@id return $gsWinner)"
					 	+" let $mastersWinners := (for $id in $mastersWinnersId"
					 	+" for $p in db:open($DB)//root/players/*"
					 	+" where $id = $p/@id"
					 	+" return $p)"
					 	+" let $grandslamWinners := (for $id in $grandslamWinnersId"
					 	+" for $p in db:open($DB)//root/players/*"
					 	+" where $id = $p/@id"
					 	+" return $p)"
					 	+" return $mastersWinners except $grandslamWinners");
			
			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));

			XQResultSequence rs = expr.executeQuery();
			while(rs.next())
				playerList.add(JAXBUtil.fromXML(Player.class, rs.getItemAsString(null)));
		} catch (JAXBException | XQException e) {
			e.printStackTrace();
		}
		return playerList;
	}
}

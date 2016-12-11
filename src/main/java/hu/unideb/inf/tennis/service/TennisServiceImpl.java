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
	public boolean updatePlayerWeight(String id, int weight) {

		Player player;
		if((player = findPlayerById(id)) == null)
			return false;
		else
		{
			try {
				player.setWeight(weight);
				String updatedPlayer = JAXBUtil.toXML(player);
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $id external;"
						 	+" declare variable $updatedPlayer external;"
						    + " replace node db:open($DB)//root/players/player[@id=$id] with $updatedPlayer");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindString(new QName("id"), id, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("updatedPlayer"), updatedPlayer, null,null);				
				expr.executeQuery();
				
			} catch (JAXBException | XQException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public boolean updatePlayerHeight(String id, int height) {
		Player player;
		if((player = findPlayerById(id)) == null)
			return false;
		else
		{
			try {
				player.setHeight(height);
				String updatedPlayer = JAXBUtil.toXML(player);
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $id external;"
						 	+" declare variable $updatedPlayer external;"
						    + " replace node db:open($DB)//root/players/player[@id=$id] with $updatedPlayer");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindString(new QName("id"), id, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindDocument(new QName("updatedPlayer"), updatedPlayer, null,null);				
				expr.executeQuery();
				
			} catch (JAXBException | XQException e) {
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

//	@Override
//	public Set<Player> getPlayersParticipatedTournament(int year, String name) {
//		
//		Set<String> playerSet = new HashSet<>();
//		try {
//			 XQPreparedExpression expr = connection.prepareExpression(
//					    "declare variable $DB external;"
//					    + " for $player in db:open($DB)//root/players/*"
//					    + " return $player");
//			
//			expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
//
//			XQResultSequence rs = expr.executeQuery();
//			while(rs.next())
//				playerList.add(JAXBUtil.fromXML(Player.class, rs.getItemAsString(null)));
//		} catch (JAXBException | XQException e) {
//			e.printStackTrace();
//		}
//		
//		return playerList;
//	}
//
//	@Override
//	public Player getTournamentWinner(int year, String name) {
//		// TODO Auto-generated method stub
//		return null;
//	}



}

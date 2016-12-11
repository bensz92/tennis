package hu.unideb.inf.tennis.service;

import java.util.ArrayList;
import java.util.List;

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
	public boolean removePlayer(Player player) {
		
		if(findPlayerById(player.getId()) == null)
			return false;
		else
		{
			try {				
				 XQPreparedExpression expr = connection.prepareExpression(
						    "declare variable $DB external;"
						 	+" declare variable $id external;"
						    + " delete node db:open($DB)//root/players/player[@id=$id]");
				
				expr.bindString(new QName("DB"), DB, connection.createAtomicType(XQItemType.XQBASETYPE_STRING));
				expr.bindString(new QName("id"), player.getId(), connection.createAtomicType(XQItemType.XQBASETYPE_STRING));				
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
					    + " for $tournament in db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournament[@name=$name]"
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
					    + " for $tournament in db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/*"
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
	          + " insert node ($newTournament) into db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]");
	    
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
						+ " insert node ($newMatch) into db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournament[@name=$name]/finals");

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
						+ " insert node ($newMatch) into db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournament[@name=$name]/semi-finals");

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
						+ " insert node ($newMatch) into db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournament[@name=$name]/quarter-finals");

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
								+ " delete node db:open($DB)//root/tennis_seasons/tennis_season[@year=$year]/tournament[@name=$name]");

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
	public boolean updateTournament() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTournamentFinals() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTournamentSemiFinals() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTournamentQuarterFinals() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMatchList() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMatchPlayers(String p1, String p2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMatchResult() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Season findSeasonByYear(int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Season> findAllSeasons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addSeason(int year) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean removeSeason(int year) {
		// TODO Auto-generated method stub
		return false;
	}

}

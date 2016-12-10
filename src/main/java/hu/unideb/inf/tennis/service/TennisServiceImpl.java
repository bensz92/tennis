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
import hu.unideb.inf.tennis.model.Player;

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
}

package hu.unideb.inf.tennis.app;

import java.io.IOException;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;

import org.basex.BaseXServer;
import org.basex.api.client.ClientQuery;
import org.basex.api.client.ClientSession;
import org.basex.core.cmd.CreateDB;

import hu.unideb.inf.tennis.connection.Connection;

public class TennisApp {

	public static void main(String[] args) throws IOException {
		
		try {
			BaseXServer server = new BaseXServer();
			ClientSession xqc = Connection.getSession("localhost", 1984, "admin", "admin");
			
			 xqc.execute(new CreateDB("input", "src/main/resources/tennis.xml"));
			
			ClientQuery query = xqc.query("//root"); 
		    System.out.println(query.execute());
		    server.stop();
		      
		} catch (XQException e) {
			e.printStackTrace();
		}
	}
}

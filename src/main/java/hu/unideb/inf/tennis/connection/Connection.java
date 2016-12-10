package hu.unideb.inf.tennis.connection;

import java.io.IOException;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import org.basex.BaseXServer;
import org.basex.api.client.ClientSession;

import net.xqj.basex.BaseXXQDataSource;

public class Connection {
	   
	public static ClientSession getSession(String sName, int port, String user, String pw)
			throws XQException, IOException {
		
		if (sName == null) {
			throw new NullPointerException("Servername is null!");
		}
		if (port == 0) {
			throw new NullPointerException("Port is null!");
		}
		if (user == null) {
			throw new NullPointerException("User is null!");
		}
		if (pw == null) {
			throw new NullPointerException("Password is null!");
		}
		ClientSession session = new ClientSession(sName, port, user, pw);

		return session;
	}

}

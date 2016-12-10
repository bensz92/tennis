package hu.unideb.inf.tennis.connection;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import net.xqj.basex.BaseXXQDataSource;

public class Connection {

	public static XQConnection getConnection(String sName, String port, String user, String pw)
			throws XQException {

		if (sName == null) {
			throw new NullPointerException("Servername is null!");
		}
		if (port == null) {
			throw new NullPointerException("Port is null!");
		}
		if (user == null) {
			throw new NullPointerException("User is null!");
		}
		if (pw == null) {
			throw new NullPointerException("Password is null!");
		}

		XQDataSource ds = new BaseXXQDataSource();
		ds.setProperty("serverName", sName);
		ds.setProperty("port", port);
		ds.setProperty("user", user);
		ds.setProperty("password", pw);

		return ds.getConnection();
	}

}

package hu.unideb.inf.tennis.app;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;

import hu.unideb.inf.tennis.connection.Connection;

public class TennisApp {

	public static void main(String[] args) {
		
		try {
			XQConnection xqc = Connection.getConnection("localhost", "1984", "bela", "bela");
		} catch (XQException e) {
			e.printStackTrace();
		}

	}

}

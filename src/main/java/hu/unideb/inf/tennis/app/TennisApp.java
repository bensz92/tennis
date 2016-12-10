package hu.unideb.inf.tennis.app;

import java.io.IOException;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import org.basex.BaseXServer;

import hu.unideb.inf.tennis.service.TennisServiceImpl;

public class TennisApp {

	private static final String DRIVER = "net.xqj.basex.BaseXXQDataSource";
	
	public static void main(String[] args) throws IOException {
		
		BaseXServer server = null;
		try {
			server = new BaseXServer();			
		    XQDataSource source = (XQDataSource) Class.forName(DRIVER).newInstance();
		    XQConnection conn = source.getConnection("admin", "admin");
			TennisServiceImpl service = new TennisServiceImpl(conn);
			
		} catch (XQException | IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			 
		}finally {
			server.stop();
		}
	}
}

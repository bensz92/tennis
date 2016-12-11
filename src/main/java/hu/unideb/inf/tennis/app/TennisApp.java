package hu.unideb.inf.tennis.app;

import java.io.IOException;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;

import hu.unideb.inf.tennis.service.TennisServiceImpl;
import hu.unideb.inf.tennis.view.CustomTabPanel;

public class TennisApp {
	
	private static final String DRIVER = "net.xqj.basex.BaseXXQDataSource";
	
	public static void main(String[] args) throws IOException {
		try {
			XQDataSource source = (XQDataSource) Class.forName(DRIVER).newInstance();
			XQConnection conn = source.getConnection("admin", "admin");
			TennisServiceImpl service = new TennisServiceImpl(conn);
			CustomTabPanel.initView(service);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

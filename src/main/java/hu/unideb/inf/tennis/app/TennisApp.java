package hu.unideb.inf.tennis.app;

import java.io.IOException;

import hu.unideb.inf.tennis.view.CustomTabPanel;

public class TennisApp {

	private static final String DRIVER = "net.xqj.basex.BaseXXQDataSource";
	
	public static void main(String[] args) throws IOException {
		CustomTabPanel.initView();
	}
}

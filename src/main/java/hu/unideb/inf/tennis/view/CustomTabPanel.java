package hu.unideb.inf.tennis.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import org.basex.BaseXServer;

import hu.unideb.inf.tennis.model.Player;
import hu.unideb.inf.tennis.service.TennisServiceImpl;

public class CustomTabPanel extends JPanel {
	private JTextField textFieldPlayerId;
	private JTextField textFieldTournamentName;
	private JTextField textFieldTournamentYear;
	private JTextField textFieldSeasonByYear;
   
	private static final String DRIVER = "net.xqj.basex.BaseXXQDataSource";
	private static TennisServiceImpl tennisService;
	
	public CustomTabPanel() {
		super(new GridLayout(1, 1));
		
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(SystemColor.inactiveCaption);
        
        JComponent panel1 = makeQueryPanel();
        
        tabbedPane.addTab("Query", null, panel1,
                "Ki lesz még találva");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        JComponent panel2 = makeCRUDPanel();
        tabbedPane.addTab("CRUD", null, panel2,
        		"Ki lesz még találva");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
           
        add(tabbedPane);
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    
	public JComponent makeQueryPanel() {
        JPanel panel = new JPanel(true);
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Results:");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(10, 285, 54, 24);
        panel.add(lblNewLabel);
        
        JTextArea textArea = new JTextArea();
        textArea.setBounds(10, 320, 993, 383);
        panel.add(textArea);
        
        JLabel lblBasicQueries = new JLabel("Basic queries:");
        lblBasicQueries.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBasicQueries.setBounds(10, 11, 108, 14);
        panel.add(lblBasicQueries);
        
        JLabel lblFindPlayerBy = new JLabel("Find Player by ID");
        lblFindPlayerBy.setBounds(10, 44, 108, 14);
        panel.add(lblFindPlayerBy);
        
        textFieldPlayerId = new JTextField();
        textFieldPlayerId.setBounds(128, 41, 86, 20);
        panel.add(textFieldPlayerId);
        textFieldPlayerId.setColumns(10);
        
        JLabel lblFindAllPlayers = new JLabel("Find all Players");
        lblFindAllPlayers.setBounds(10, 76, 108, 14);
        panel.add(lblFindAllPlayers);
        
        JButton btnAllPlayers = new JButton("Go");
        btnAllPlayers.setBounds(128, 72, 89, 23);
        panel.add(btnAllPlayers);
        
        JLabel lblFindTournamentBy = new JLabel("Find Tournament by name and year");
        lblFindTournamentBy.setBounds(10, 101, 239, 14);
        panel.add(lblFindTournamentBy);
        JButton btnPlayerId = new JButton("Go");
        btnPlayerId.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String playerId = textFieldPlayerId.getText();
        		
        		if(playerId != null)
        		{
        			Player player;
        			if(( player = tennisService.findPlayerById(playerId)) == null)
        				textArea.setText("No existing Player with this Id!"); 
        			else
        				textArea.setText(player.toString());
        		}
        			
        		else
        			System.out.println("null");
        	}
        });
        btnPlayerId.setBounds(224, 40, 89, 23);
        panel.add(btnPlayerId);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(10, 126, 54, 14);
        panel.add(lblName);
        
        textFieldTournamentName = new JTextField();
        textFieldTournamentName.setBounds(77, 126, 86, 20);
        panel.add(textFieldTournamentName);
        textFieldTournamentName.setColumns(10);
        
        JLabel lblYear = new JLabel("Year:");
        lblYear.setBounds(10, 151, 54, 14);
        panel.add(lblYear);
        
        textFieldTournamentYear = new JTextField();
        textFieldTournamentYear.setBounds(77, 148, 86, 20);
        panel.add(textFieldTournamentYear);
        textFieldTournamentYear.setColumns(10);
        
        JButton btnTournamentByYandN = new JButton("Go");
        btnTournamentByYandN.setBounds(173, 147, 89, 23);
        panel.add(btnTournamentByYandN);
        
        JLabel lblFindAllTournament = new JLabel("Find all Tournaments");
        lblFindAllTournament.setBounds(10, 187, 132, 14);
        panel.add(lblFindAllTournament);
        
        JButton btnAllTournaments = new JButton("Go");
        btnAllTournaments.setBounds(163, 183, 89, 23);
        panel.add(btnAllTournaments);
        
        JLabel lblNewLabel_1 = new JLabel("Find Season by year");
        lblNewLabel_1.setBounds(10, 220, 132, 14);
        panel.add(lblNewLabel_1);
        
        textFieldSeasonByYear = new JTextField();
        textFieldSeasonByYear.setBounds(163, 217, 86, 20);
        panel.add(textFieldSeasonByYear);
        textFieldSeasonByYear.setColumns(10);
        
        JButton btnSeasonByYear = new JButton("Go");
        btnSeasonByYear.setBounds(270, 216, 89, 23);
        panel.add(btnSeasonByYear);
        
        JLabel lblFindAllSeasons = new JLabel("Find all Seasons");
        lblFindAllSeasons.setBounds(10, 260, 132, 14);
        panel.add(lblFindAllSeasons);
        
        JButton btnAllSeasons = new JButton("Go");
        btnAllSeasons.setBounds(163, 256, 89, 23);
        panel.add(btnAllSeasons);
        return panel;
    }
    
	public JComponent makeCRUDPanel() {
        JPanel panel = new JPanel(true);
        panel.setBackground(SystemColor.inactiveCaption);
        JLabel filler = new JLabel("Itt lesznek majd CRUD-ok");
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
	
	public static void initView() throws IOException {
	
		try {	
		    XQDataSource source = (XQDataSource) Class.forName(DRIVER).newInstance();
		    XQConnection conn = source.getConnection("admin", "admin");
			TennisServiceImpl service = new TennisServiceImpl(conn);
			tennisService = service;
					UIManager.put("swing.boldMetal", Boolean.FALSE);
					JFrame frame = new JFrame("TennisData");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setMinimumSize(new Dimension(1024, 768));
					frame.setPreferredSize(new Dimension(1024, 768));
					frame.setBackground(SystemColor.inactiveCaption);
					frame.setResizable(false);
					frame.getContentPane().add(new CustomTabPanel(), BorderLayout.CENTER);
					frame.pack();
					frame.setVisible(true);
		
		} catch (XQException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			 
		}
	}
}

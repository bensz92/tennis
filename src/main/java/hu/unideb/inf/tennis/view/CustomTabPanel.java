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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import hu.unideb.inf.tennis.model.Player;
import hu.unideb.inf.tennis.model.Season;
import hu.unideb.inf.tennis.model.Tournament;
import hu.unideb.inf.tennis.service.TennisServiceImpl;
import java.awt.Color;

public class CustomTabPanel extends JPanel {
	
	private JTextField textFieldPlayerId;
	private JTextField textFieldTournamentName;
	private JTextField textFieldTournamentYear;
	private JTextField textFieldSeasonByYear;
	private JTextField textFieldTournamentJustbyYear;
   
	private static TennisServiceImpl tennisService;
	private JTextField textFieldAPid;
	private JTextField textFieldAPname;
	private JTextField textFieldAPyearofbirth;
	private JTextField textFieldAPbirthplace;
	private JTextField textFieldAPweight;
	private JTextField textFieldAPheight;
	private JTextField textFieldAPturnedpro;
	private JTextField textFieldAPplays;
	private JTextField textFieldATyear;
	private JTextField textFieldATname;
	private JTextField textFieldATtype;
	private JTextField textFieldATsurface;
	private JTextField textFieldASyear;
	private JTextField textFieldRemovePlayerId;
	private JTextField textFieldTourRemoveYear;
	private JTextField textFieldTourRemoveName;
	private JTextField textFieldSeasonRemoveYear;
	public CustomTabPanel() {
		super(new GridLayout(1, 1));
		
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(SystemColor.inactiveCaption);
        
        JComponent panel1 = makeQueryPanel();
        tabbedPane.addTab("Query", null, panel1,
                "Quety Tab");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        JComponent panel2 = makeCRUDPanel();
        tabbedPane.addTab("CRUD", null, panel2,
        		"CRUD Tab");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        JComponent panel3 = makeRemovePanel();
        tabbedPane.addTab("Remove", null, panel3,
        		"Remove Tab");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
           
        add(tabbedPane);
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    
	public JComponent makeQueryPanel() {
        JPanel panel = new JPanel(true);
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Results:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(10, 285, 156, 24);
        panel.add(lblNewLabel);
        
        JTextArea textArea = new JTextArea();
        textArea.setBounds(10, 320, 993, 383);
        textArea.setEditable(false);
        
        JScrollPane scroll = new JScrollPane (textArea);
        scroll.setBounds(10, 320, 993, 383);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        
        panel.add(scroll);
        
        JLabel lblBasicQueries = new JLabel("Basic queries:");
        lblBasicQueries.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblBasicQueries.setBounds(10, 11, 108, 14);
        panel.add(lblBasicQueries);
        
        JLabel lblFindPlayerBy = new JLabel("Find Player by ID");
        lblFindPlayerBy.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFindPlayerBy.setBounds(10, 45, 108, 14);
        panel.add(lblFindPlayerBy);
        
        textFieldPlayerId = new JTextField();
        textFieldPlayerId.setBounds(221, 45, 86, 20);
        panel.add(textFieldPlayerId);
        textFieldPlayerId.setColumns(10);
        
        JLabel lblFindAllPlayers = new JLabel("Find all Players");
        lblFindAllPlayers.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFindAllPlayers.setBounds(10, 75, 108, 14);
        panel.add(lblFindAllPlayers);
        
        JButton btnAllPlayers = new JButton("Go");
        btnAllPlayers.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		List<Player> playerList = new ArrayList<>();
        		textArea.setText("");
        		if((playerList = tennisService.findAllPlayers()).isEmpty())
        			textArea.setText("No results for this querry!");
        		else{
        			for(Player p : playerList)
        			{
        				textArea.append(p.toString() + "\n");
        			}
        			textArea.setText(textArea.getText().substring(0,textArea.getText().length()-1));	
        		}
        	}
        });
        btnAllPlayers.setBounds(330, 75, 89, 23);
        panel.add(btnAllPlayers);
        
        JLabel lblFindTournamentBy = new JLabel("Find Tournament by name and year");
        lblFindTournamentBy.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFindTournamentBy.setBounds(10, 105, 239, 14);
        panel.add(lblFindTournamentBy);
        
        JButton btnPlayerId = new JButton("Go");
        btnPlayerId.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String playerId = textFieldPlayerId.getText();
        		
        		if(playerId != null && !playerId.equals(""))
        		{
        			Player player;
        			if(( player = tennisService.findPlayerById(playerId)) == null)
        				textArea.setText("No existing Player with this Id!"); 
        			else
        				textArea.setText(player.toString());
        		}
        		else
        			textArea.setText("Some parameters are missing!");
        		textFieldPlayerId.setText("");
        	}
        });
        btnPlayerId.setBounds(330, 45, 89, 23);
        panel.add(btnPlayerId);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblName.setBounds(35, 135, 54, 14);
        panel.add(lblName);
        
        textFieldTournamentName = new JTextField();
        textFieldTournamentName.setBounds(221, 135, 86, 20);
        panel.add(textFieldTournamentName);
        textFieldTournamentName.setColumns(10);
        
        JLabel lblYear = new JLabel("Year:");
        lblYear.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblYear.setBounds(35, 165, 54, 14);
        panel.add(lblYear);
        
        textFieldTournamentYear = new JTextField();
        textFieldTournamentYear.setBounds(221, 165, 86, 20);
        panel.add(textFieldTournamentYear);
        textFieldTournamentYear.setColumns(10);
        
        JButton btnTournamentByYandN = new JButton("Go");
        btnTournamentByYandN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (textFieldTournamentName.getText().equals("") || textFieldTournamentYear.getText().equals(""))
						textArea.setText("Some parameters are missing!");
					else {
						Tournament tournament;
						if ((tournament = tennisService.findTournamentByNameAndYear(textFieldTournamentName.getText(),
								Integer.valueOf(textFieldTournamentYear.getText()))) == null)
							textArea.setText("No existing Tournament with this name and year!");
						else
							textArea.setText(tournament.toString() + "\n");

					}
					textFieldTournamentName.setText("");
					textFieldTournamentYear.setText("");
				} catch (NumberFormatException e) {
					textFieldTournamentName.setText("");
					textFieldTournamentYear.setText("");
					textArea.setText("Year is number!");
				}
			}
		});
        btnTournamentByYandN.setBounds(330, 165, 89, 23);
        panel.add(btnTournamentByYandN);
        
        JLabel lblFindAllTournament = new JLabel("Find all Tournaments by year");
        lblFindAllTournament.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFindAllTournament.setBounds(10, 195, 175, 14);
        panel.add(lblFindAllTournament);
        
        textFieldTournamentJustbyYear = new JTextField();
        textFieldTournamentJustbyYear.setBounds(221, 195, 86, 20);
        panel.add(textFieldTournamentJustbyYear);
        textFieldTournamentJustbyYear.setColumns(10);
        
        JButton btnAllTournaments = new JButton("Go");
        btnAllTournaments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText("");
					if (textFieldTournamentJustbyYear.getText().equals(""))
						textArea.setText("Some parameters are missing!");
					else {
						List<Tournament> tournamentList = new ArrayList<>();
						if ((tournamentList = tennisService
								.findAllTournamentsByYear(Integer.valueOf(textFieldTournamentJustbyYear.getText())))
										.isEmpty())
							textArea.setText("No results for this querry!");
						else {
							for (Tournament t : tournamentList)
								textArea.append(t.toString() + "\n");
							textArea.setText(textArea.getText().substring(0, textArea.getText().length() - 1));
						}
					}
					textFieldTournamentJustbyYear.setText("");
				} catch (NumberFormatException f) {
					textFieldTournamentJustbyYear.setText("");
					textArea.setText("Year is number!");
				}
			}
		});
        btnAllTournaments.setBounds(330, 195, 89, 23);
        panel.add(btnAllTournaments);
        
        JLabel lblNewLabel_1 = new JLabel("Find Season by year");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(10, 225, 132, 14);
        panel.add(lblNewLabel_1);
        
        textFieldSeasonByYear = new JTextField();
        textFieldSeasonByYear.setBounds(221, 225, 86, 20);
        panel.add(textFieldSeasonByYear);
        textFieldSeasonByYear.setColumns(10);
        
        JButton btnSeasonByYear = new JButton("Go");
        btnSeasonByYear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try{
        			if(textFieldSeasonByYear.getText().equals(""))
            			textArea.setText("Some parameters are missing!");
            		else{
            			Season season;
            			if((season = tennisService.findSeasonByYear(Integer.valueOf(textFieldSeasonByYear.getText()))) == null)
            				textArea.setText("No existing Season with this year!");
            			else
            				textArea.setText(season.toString());
            			textFieldSeasonByYear.setText("");
            		}
        		}catch (NumberFormatException f) {
        			textFieldSeasonByYear.setText("");
					textArea.setText("Year is number!");
				}
        	}
        });
        btnSeasonByYear.setBounds(330, 225, 89, 23);
        panel.add(btnSeasonByYear);
        
        JLabel lblFindAllSeasons = new JLabel("Find all Seasons");
        lblFindAllSeasons.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFindAllSeasons.setBounds(10, 255, 132, 14);
        panel.add(lblFindAllSeasons);
        
        JButton btnAllSeasons = new JButton("Go");
        btnAllSeasons.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		List<Season> seasonList = new ArrayList<>();
        		textArea.setText("");
        		if((seasonList = tennisService.findAllSeasons()).isEmpty())
        			textArea.setText("No results for this querry!");
        		else{
        			for(Season s : seasonList)
        			{
        				textArea.append(s.toString() + "\n");
        			}
        			textArea.setText(textArea.getText().substring(0,textArea.getText().length()-1));	
        		}
        	}
        });
        btnAllSeasons.setBounds(330, 255, 89, 23);
        panel.add(btnAllSeasons);
        
        return panel;
    }
    
	public JComponent makeCRUDPanel() {
        JPanel panel = new JPanel(true);
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setLayout(null);
        
        JLabel addResultLbl = new JLabel("");
        addResultLbl.setBounds(10, 275, 180, 14);
        panel.add(addResultLbl);
        
        JLabel lblAddPlayer = new JLabel("Add player");
        lblAddPlayer.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblAddPlayer.setBounds(10, 11, 90, 19);
        panel.add(lblAddPlayer);
        
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 40, 90, 14);
        panel.add(lblId);
        
        JLabel lblName_1 = new JLabel("Name:");
        lblName_1.setBounds(10, 65, 90, 14);
        panel.add(lblName_1);
        
        JLabel lblNewLabel_2 = new JLabel("Year of birth:");
        lblNewLabel_2.setBounds(10, 90, 90, 14);
        panel.add(lblNewLabel_2);
        
        JLabel lblBirthplace = new JLabel("Birthplace:");
        lblBirthplace.setBounds(10, 115, 90, 14);
        panel.add(lblBirthplace);
        
        JLabel lblWeight = new JLabel("Weight:");
        lblWeight.setBounds(10, 140, 90, 14);
        panel.add(lblWeight);
        
        JLabel lblHeight = new JLabel("Height:");
        lblHeight.setBounds(10, 165, 90, 14);
        panel.add(lblHeight);
        
        JLabel lblNewLabel_3 = new JLabel("Turned pro:");
        lblNewLabel_3.setBounds(10, 190, 90, 14);
        panel.add(lblNewLabel_3);
        
        JLabel lblPlays = new JLabel("Plays:");
        lblPlays.setBounds(10, 215, 90, 14);
        panel.add(lblPlays);
        
        textFieldAPid = new JTextField();
        textFieldAPid.setBounds(100, 40, 86, 20);
        panel.add(textFieldAPid);
        textFieldAPid.setColumns(10);
        
        textFieldAPname = new JTextField();
        textFieldAPname.setBounds(100, 65, 86, 20);
        panel.add(textFieldAPname);
        textFieldAPname.setColumns(10);
        
        textFieldAPyearofbirth = new JTextField();
        textFieldAPyearofbirth.setBounds(100, 90, 86, 20);
        panel.add(textFieldAPyearofbirth);
        textFieldAPyearofbirth.setColumns(10);
        
        textFieldAPbirthplace = new JTextField();
        textFieldAPbirthplace.setBounds(100, 115, 86, 20);
        panel.add(textFieldAPbirthplace);
        textFieldAPbirthplace.setColumns(10);
        
        textFieldAPweight = new JTextField();
        textFieldAPweight.setBounds(100, 140, 86, 20);
        panel.add(textFieldAPweight);
        textFieldAPweight.setColumns(10);
        
        textFieldAPheight = new JTextField();
        textFieldAPheight.setBounds(100, 165, 86, 20);
        panel.add(textFieldAPheight);
        textFieldAPheight.setColumns(10);
        
        textFieldAPturnedpro = new JTextField();
        textFieldAPturnedpro.setBounds(100, 190, 86, 20);
        panel.add(textFieldAPturnedpro);
        textFieldAPturnedpro.setColumns(10);
        
        textFieldAPplays = new JTextField();
        textFieldAPplays.setBounds(100, 215, 86, 20);
        panel.add(textFieldAPplays);
        textFieldAPplays.setColumns(10);
        
        JButton btnAddPlayer = new JButton("Add");
        btnAddPlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textFieldAPid.getText().equals("") && !textFieldAPname.getText().equals("") && !textFieldAPyearofbirth.getText().equals("") 
						&& !textFieldAPbirthplace.getText().equals("") && !textFieldAPweight.getText().equals("") && !textFieldAPheight.getText().equals("")
						&& !textFieldAPturnedpro.getText().equals("") && !textFieldAPplays.getText().equals("")){
					Player newPlayer = new Player(textFieldAPid.getText(), textFieldAPname.getText(),
							Integer.parseInt(textFieldAPyearofbirth.getText()), textFieldAPbirthplace.getText(), 
							Integer.parseInt(textFieldAPweight.getText()), Integer.parseInt(textFieldAPheight.getText()), 
							Integer.parseInt(textFieldAPturnedpro.getText()), textFieldAPplays.getText());
					tennisService.addPlayer(newPlayer);
					textFieldAPid.setText("");
					textFieldAPname.setText("");
					textFieldAPyearofbirth.setText("");
					textFieldAPbirthplace.setText("");
					textFieldAPweight.setText("");
					textFieldAPheight.setText("");
					textFieldAPturnedpro.setText("");
					textFieldAPplays.setText("");
					addResultLbl.setText("Player successfully added!");
				} else {
					addResultLbl.setText("Please fill player details!");
				}
			}
		});
        btnAddPlayer.setBounds(10, 238, 89, 23);
        panel.add(btnAddPlayer);
        
        JLabel lblNewLabel_4 = new JLabel("Add tournament");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_4.setBounds(220, 11, 157, 14);
        panel.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("Year:");
        lblNewLabel_5.setBounds(220, 40, 69, 14);
        panel.add(lblNewLabel_5);
        
        textFieldATyear = new JTextField();
        textFieldATyear.setBounds(282, 40, 86, 20);
        panel.add(textFieldATyear);
        textFieldATyear.setColumns(10);
        
        JLabel lblNewLabel_6 = new JLabel("Name:");
        lblNewLabel_6.setBounds(220, 65, 46, 14);
        panel.add(lblNewLabel_6);
        
        textFieldATname = new JTextField();
        textFieldATname.setBounds(282, 65, 86, 20);
        panel.add(textFieldATname);
        textFieldATname.setColumns(10);
        
        JLabel lblNewLabel_7 = new JLabel("Type:");
        lblNewLabel_7.setBounds(220, 90, 46, 14);
        panel.add(lblNewLabel_7);
        
        JLabel lblNewLabel_8 = new JLabel("Surface:");
        lblNewLabel_8.setBounds(220, 115, 46, 14);
        panel.add(lblNewLabel_8);
        
        textFieldATtype = new JTextField();
        textFieldATtype.setBounds(282, 90, 86, 20);
        panel.add(textFieldATtype);
        textFieldATtype.setColumns(10);
        
        textFieldATsurface = new JTextField();
        textFieldATsurface.setBounds(282, 115, 86, 20);
        panel.add(textFieldATsurface);
        textFieldATsurface.setColumns(10);
        
        JButton btnAddTournament = new JButton("Add");
        btnAddTournament.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textFieldATyear.getText().equals("") && !textFieldATname.getText().equals("") 
						&& !textFieldATtype.getText().equals("") && !textFieldATsurface.getText().equals("")){
					tennisService.addTournament(Integer.parseInt(textFieldATyear.getText()), textFieldATname.getText(), 
							textFieldATtype.getText(), textFieldATsurface.getText());
					addResultLbl.setText("Tournament successfully added!");
				} else {
					addResultLbl.setText("Please fill tournament details!");
				}
			}
		});
        btnAddTournament.setBounds(220, 140, 89, 23);
        panel.add(btnAddTournament);
        
        JLabel lblAddSeason = new JLabel("Add season");
        lblAddSeason.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblAddSeason.setBounds(400, 11, 157, 14);
        panel.add(lblAddSeason);
        
        JLabel lblSeasonYear = new JLabel("Year:");
        lblSeasonYear.setBounds(400, 40, 69, 14);
        panel.add(lblSeasonYear);
        
        textFieldASyear = new JTextField();
        textFieldASyear.setBounds(450, 40, 86, 20);
        panel.add(textFieldASyear);
        textFieldASyear.setColumns(10);
        
        JButton btnAddSeason = new JButton("Add");
        btnAddSeason.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textFieldASyear.getText().equals("")){
					tennisService.addSeason(Integer.valueOf(textFieldASyear.getText()));
					addResultLbl.setText("Season successfully added!");
				} else {
					addResultLbl.setText("Please fill season details!");
				}
				
			}
		});
        btnAddSeason.setBounds(400, 70, 89, 23);
        panel.add(btnAddSeason);
        return panel;
    }
	
	public JComponent makeRemovePanel(){
		JPanel panel = new JPanel(true);
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setLayout(null);
        
        JLabel lblRemovePlayer = new JLabel("Remove player");
        lblRemovePlayer.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblRemovePlayer.setBounds(10, 11, 123, 19);
        panel.add(lblRemovePlayer);
        
        JLabel lblNewLabel_9 = new JLabel("Remove Player");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_9.setBounds(10, 45, 155, 14);
        panel.add(lblNewLabel_9);
        
        JLabel lblNewLabel_10 = new JLabel("ID:");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_10.setBounds(10, 75, 50, 14);
        panel.add(lblNewLabel_10);
        
        textFieldRemovePlayerId = new JTextField();
        textFieldRemovePlayerId.setBounds(150, 75, 86, 20);
        panel.add(textFieldRemovePlayerId);
        textFieldRemovePlayerId.setColumns(10);
        
        JLabel lblRemoveInfo = new JLabel("");
        lblRemoveInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblRemoveInfo.setBounds(10, 265, 339, 14);
        panel.add(lblRemoveInfo);
        
        JButton btnPlayer = new JButton("Remove");
        btnPlayer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String playerId;
        		if((playerId = textFieldRemovePlayerId.getText()).equals("")){
        			lblRemoveInfo.setText("Some parameters are missing!");
        		}
        			
        		else{
        			if(tennisService.removePlayer(playerId))
        				lblRemoveInfo.setText("Player removed!");
        			else{
        				lblRemoveInfo.setText("Player can't be removed!");
        			}
        		}
        		textFieldRemovePlayerId.setText("");	
        	}
        });
        btnPlayer.setBounds(260, 75, 89, 23);
        panel.add(btnPlayer);
        
        JLabel lblNewLabel_11 = new JLabel("Remove Tournament");
        lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_11.setBounds(10, 105, 131, 14);
        panel.add(lblNewLabel_11);
        
        JLabel lblNewLabel_12 = new JLabel("Year:");
        lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_12.setBounds(10, 135, 46, 14);
        panel.add(lblNewLabel_12);
        
        JLabel lblNewLabel_13 = new JLabel("Name:");
        lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_13.setBounds(10, 165, 46, 14);
        panel.add(lblNewLabel_13);
        
        textFieldTourRemoveYear = new JTextField();
        textFieldTourRemoveYear.setBounds(150, 135, 86, 20);
        panel.add(textFieldTourRemoveYear);
        textFieldTourRemoveYear.setColumns(10);
        
        textFieldTourRemoveName = new JTextField();
        textFieldTourRemoveName.setBounds(150, 165, 86, 20);
        panel.add(textFieldTourRemoveName);
        textFieldTourRemoveName.setColumns(10);
        
        JButton btnRemoveTournament = new JButton("Remove");
        btnRemoveTournament.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try{
        			String tYear = textFieldTourRemoveYear.getText();
            		String tName = textFieldTourRemoveName.getText();
            		if(tYear.equals("") || tName.equals(""))
            			lblRemoveInfo.setText("Some parameters are missing!");
            		else{
            			if(tennisService.removeTournament(tName, Integer.parseInt(tYear)))
            				lblRemoveInfo.setText("Tournament removed successfully!");
            			else
            				lblRemoveInfo.setText("Tournament can't be removed!");
            		}	
            		textFieldTourRemoveYear.setText("");
            		textFieldTourRemoveName.setText("");
        		}catch (NumberFormatException f) {
        			textFieldTourRemoveYear.setText("");
        			textFieldTourRemoveName.setText("");
        			lblRemoveInfo.setText("Year is number!");
				}
        	}
        });
        btnRemoveTournament.setBounds(260, 165, 89, 23);
        panel.add(btnRemoveTournament);
        
        JLabel lblNewLabel_14 = new JLabel("Remove Season");
        lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_14.setBounds(10, 195, 110, 14);
        panel.add(lblNewLabel_14);
        
        JLabel lblNewLabel_15 = new JLabel("Year:");
        lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_15.setBounds(10, 225, 68, 14);
        panel.add(lblNewLabel_15);
        
        textFieldSeasonRemoveYear = new JTextField();
        textFieldSeasonRemoveYear.setBounds(150, 225, 86, 20);
        panel.add(textFieldSeasonRemoveYear);
        textFieldSeasonRemoveYear.setColumns(10);
        
        JButton btnRemoveSeason = new JButton("Remove");
        btnRemoveSeason.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
				try {
					String year;
					if ((year = textFieldSeasonRemoveYear.getText()).equals(""))
						lblRemoveInfo.setText("Some parameters are missing!");
					else {
						if (tennisService.removeSeason(Integer.parseInt(year)))
							lblRemoveInfo.setText("Season removed successfully!");
						else
							lblRemoveInfo.setText("Season can't be removed!");
					}
					textFieldSeasonRemoveYear.setText("");
				} catch (NumberFormatException f) {
					textFieldSeasonRemoveYear.setText("");
					lblRemoveInfo.setText("Year is number!");
				}
			}
		});
        btnRemoveSeason.setBounds(260, 225, 89, 23);
        panel.add(btnRemoveSeason);
        
        return panel;
	}
	
	public static void initView(TennisServiceImpl service) throws IOException {
	
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
	}
}

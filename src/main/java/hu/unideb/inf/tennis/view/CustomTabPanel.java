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
import javax.swing.JComboBox;
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

import hu.unideb.inf.tennis.model.Match;
import hu.unideb.inf.tennis.model.Player;
import hu.unideb.inf.tennis.model.PlayerRef;
import hu.unideb.inf.tennis.model.Result;
import hu.unideb.inf.tennis.model.Season;
import hu.unideb.inf.tennis.model.Set;
import hu.unideb.inf.tennis.model.Tournament;
import hu.unideb.inf.tennis.service.TennisServiceImpl;

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
	private JTextField textFieldUPname;
	private JTextField textFieldUPyearofbirth;
	private JTextField textFieldUPbirthplace;
	private JTextField textFieldUPweight;
	private JTextField textFieldUPheight;
	private JTextField textFieldUPturnedpro;
	private JTextField textFieldUPplays;
	private JTextField textFieldATname;
	private JTextField textFieldASyear;
	private JTextField textFieldAMset1;
	private JTextField textFieldAMset2;
	private JTextField textFieldAMset3;
	private JTextField textFieldAMset4;
	private JTextField textFieldAMset5;
	
    private JComboBox<String> comboBoxYears = new JComboBox<>();
    private JComboBox<String> comboBoxTours = new JComboBox<>();
    private JComboBox<String> comboAddMatchPlayer1 = new JComboBox<>();
    private JComboBox<String> comboAddMatchPlayer2 = new JComboBox<>();
    private JComboBox<String> comboAddMatchTournamentType = new JComboBox<>();
    private JComboBox<String> comboAddMatchType = new JComboBox<>();
    private JComboBox<String> comboAddTournamentYear = new JComboBox<>();
    private JComboBox<String> comboAddTournamentType = new JComboBox<>();
    private JComboBox<String> comboAddTournamentSurface = new JComboBox<>();
    private JComboBox<String> comboRemoveSeasonYear = new JComboBox<>();
    private JComboBox<String> comboRemoveTournamentYear = new JComboBox<>();
    private JComboBox<String> comboRemoveTournamentName = new JComboBox<>();
    private JComboBox<String> comboRemovePlayerID = new JComboBox<>();
    private JComboBox<String> comboUpdatePlayerID = new JComboBox<>();
   
	
	public CustomTabPanel() {
		super(new GridLayout(1, 1));
		
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(SystemColor.inactiveCaption);
        
        JComponent panel1 = makeQueryPanel();
        tabbedPane.addTab("Query", null, panel1,
                "Query Tab");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        JComponent panel2 = makeAddPanel();
        tabbedPane.addTab("Add", null, panel2,
        		"Add Tab");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        JComponent panel3 = makeUpdatePanel();
        tabbedPane.addTab("Update", null, panel3,
        		"Update Tab");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        JComponent panel4 = makeRemovePanel();
        tabbedPane.addTab("Remove", null, panel4,
        		"Remove Tab");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
           
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
    
	public JComponent makeAddPanel() {
        JPanel panel = new JPanel(true);
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setLayout(null);
        
        JLabel addResultLbl = new JLabel("");
        addResultLbl.setBounds(10, 275, 413, 14);
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
				try {
					if (!textFieldAPid.getText().equals("") && !textFieldAPname.getText().equals("")
							&& !textFieldAPyearofbirth.getText().equals("")
							&& !textFieldAPbirthplace.getText().equals("") && !textFieldAPweight.getText().equals("")
							&& !textFieldAPheight.getText().equals("") && !textFieldAPturnedpro.getText().equals("")
							&& !textFieldAPplays.getText().equals("")) {
						Player newPlayer = new Player(textFieldAPid.getText(), textFieldAPname.getText(),
								Integer.parseInt(textFieldAPyearofbirth.getText()), textFieldAPbirthplace.getText(),
								Integer.parseInt(textFieldAPweight.getText()),
								Integer.parseInt(textFieldAPheight.getText()),
								Integer.parseInt(textFieldAPturnedpro.getText()), textFieldAPplays.getText());
						if (tennisService.addPlayer(newPlayer)) {
							textFieldAPid.setText("");
							textFieldAPname.setText("");
							textFieldAPyearofbirth.setText("");
							textFieldAPbirthplace.setText("");
							textFieldAPweight.setText("");
							textFieldAPheight.setText("");
							textFieldAPturnedpro.setText("");
							textFieldAPplays.setText("");
							addResultLbl.setText("Player successfully added!");
							comboAddMatchPlayer1.addItem(newPlayer.getId());
							comboAddMatchPlayer2.addItem(newPlayer.getId());
							comboUpdatePlayerID.addItem(newPlayer.getId());
							comboRemovePlayerID.addItem(newPlayer.getId());
						} else {
							addResultLbl.setText("Add Player failed!");
						}

					} else {
						addResultLbl.setText("Please fill player details!");
					}
				} catch (NumberFormatException f) {
					textFieldAPid.setText("");
					textFieldAPname.setText("");
					textFieldAPyearofbirth.setText("");
					textFieldAPbirthplace.setText("");
					textFieldAPweight.setText("");
					textFieldAPheight.setText("");
					textFieldAPturnedpro.setText("");
					textFieldAPplays.setText("");
					addResultLbl.setText("Year, weight, height and turnedpro should be numbers!");
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
        
        for(String s : tennisService.findAllYears())
        	comboAddTournamentYear.addItem(s);
        comboAddTournamentYear.setBounds(282, 40, 86, 20);      
        panel.add(comboAddTournamentYear);
        
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
        
        comboAddTournamentType.addItem("Grand Slam");
        comboAddTournamentType.addItem("Masters 1000");
        comboAddTournamentType.setBounds(282, 90, 86, 20);       
        panel.add(comboAddTournamentType);
        
        comboAddTournamentSurface.addItem("Hard");
        comboAddTournamentSurface.addItem("Clay");
        comboAddTournamentSurface.addItem("Grass");
        comboAddTournamentSurface.setBounds(282, 115, 86, 20);      
        panel.add(comboAddTournamentSurface);
        
        JButton btnAddTournament = new JButton("Add");
        btnAddTournament.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
					if(!textFieldATname.getText().equals("")){	
						if(tennisService.addTournament(Integer.parseInt((String)comboAddTournamentYear.getSelectedItem()), textFieldATname.getText(), 
								(String) comboAddTournamentType.getSelectedItem(), (String) comboAddTournamentSurface.getSelectedItem())){
							addResultLbl.setText("Tournament successfully added!");
							comboBoxTours.addItem(textFieldATname.getText());
						}else{
							addResultLbl.setText("Add Tournament failed!");
						}
						textFieldATname.setText("");
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
				try{
					if(!textFieldASyear.getText().equals("")){
						if(tennisService.addSeason(Integer.valueOf(textFieldASyear.getText()))){
							comboBoxYears.addItem(textFieldASyear.getText());
							comboAddTournamentYear.addItem(textFieldASyear.getText());
							comboRemoveSeasonYear.addItem(textFieldASyear.getText());
							comboRemoveTournamentYear.addItem(textFieldASyear.getText());
							addResultLbl.setText("Season successfully added!");
						}else{
							addResultLbl.setText("Add season failed!");
						}
						textFieldASyear.setText("");		
					} else {
						addResultLbl.setText("Please fill season details!");
					}
				}catch (NumberFormatException f) {
					textFieldASyear.setText("");
					addResultLbl.setText("Year should be number!");
				}
			}
		});
        btnAddSeason.setBounds(400, 70, 89, 23);
        panel.add(btnAddSeason);
        
        JLabel lblAddMatch = new JLabel("Add match");
        lblAddMatch.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblAddMatch.setBounds(570, 11, 157, 14);
        panel.add(lblAddMatch);

        JLabel lblSelectYear = new JLabel("Select year:");
        lblSelectYear.setBounds(570, 40, 200, 14);
        panel.add(lblSelectYear);
        
        JLabel lblSelectTourName = new JLabel("Select tournament:");
        lblSelectTourName.setBounds(570, 65, 200, 14);
        panel.add(lblSelectTourName);

        for(String s : tennisService.findAllYears())
        	comboBoxYears.addItem(s);
        comboBoxYears.setBounds(700, 40, 100, 20);
        comboBoxYears.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxTours.removeAllItems();
				for(Tournament t : tennisService.findAllTournamentsByYear(Integer.parseInt((String)comboBoxYears.getSelectedItem())))
		        	comboBoxTours.addItem(t.getName());
				
			}
		});
        panel.add(comboBoxYears);
        
        for(Tournament t : tennisService.findAllTournamentsByYear(Integer.parseInt((String)comboBoxYears.getSelectedItem())))
        	comboBoxTours.addItem(t.getName());
        comboBoxTours.setBounds(700, 65, 100, 20);
        panel.add(comboBoxTours);
        
        JLabel lblPlayer1 = new JLabel("Player 1 id:");
        lblPlayer1.setBounds(570, 90, 200, 14);
        panel.add(lblPlayer1);
        
        JLabel lblPlayer2 = new JLabel("Player 2 id:");
        lblPlayer2.setBounds(570, 115, 200, 14);
        panel.add(lblPlayer2);
        
        for(Player p : tennisService.findAllPlayers())
        	comboAddMatchPlayer1.addItem(p.getId());
        comboAddMatchPlayer1.setBounds(700, 90, 86, 20);     
        panel.add(comboAddMatchPlayer1);
        
        for(Player p : tennisService.findAllPlayers())
        	comboAddMatchPlayer2.addItem(p.getId());
        comboAddMatchPlayer2.setBounds(700, 115, 86, 20);    
        panel.add(comboAddMatchPlayer2);
        
        JLabel lblSet1 = new JLabel("Set 1:");
        lblSet1.setBounds(570, 165, 200, 14);
        panel.add(lblSet1);
        
        textFieldAMset1 = new JTextField();
        textFieldAMset1.setBounds(700, 165, 86, 20);
        textFieldAMset1.setVisible(true);
        panel.add(textFieldAMset1);
        textFieldAMset1.setColumns(10);
        
        JLabel lblSet2 = new JLabel("Set 2:");
        lblSet2.setBounds(570, 190, 200, 14);
        panel.add(lblSet2);
        
        textFieldAMset2 = new JTextField();
        textFieldAMset2.setBounds(700, 190, 86, 20);
        textFieldAMset2.setVisible(true);
        panel.add(textFieldAMset2);
        textFieldAMset2.setColumns(10);
        
        JLabel lblSet3 = new JLabel("Set 3:");
        lblSet3.setBounds(570, 215, 200, 14);
        panel.add(lblSet3);
        
        textFieldAMset3 = new JTextField();
        textFieldAMset3.setBounds(700, 215, 86, 20);
        textFieldAMset3.setVisible(true);
        panel.add(textFieldAMset3);
        textFieldAMset3.setColumns(10);
        
        JLabel lblSet4 = new JLabel("Set 4:");
        lblSet4.setBounds(570, 240, 200, 14);
        panel.add(lblSet4);
        
        textFieldAMset4 = new JTextField();
        textFieldAMset4.setBounds(700, 240, 86, 20);
        textFieldAMset4.setVisible(true);
        panel.add(textFieldAMset4);
        textFieldAMset4.setColumns(10);
        
        JLabel lblSet5 = new JLabel("Set 5:");
        lblSet5.setBounds(570, 265, 200, 14);
        panel.add(lblSet5);
        
        textFieldAMset5 = new JTextField();
        textFieldAMset5.setBounds(700, 265, 86, 20);
        textFieldAMset5.setVisible(true);
        panel.add(textFieldAMset5);
        textFieldAMset5.setColumns(10);
        
        
        JLabel lblCombo = new JLabel("Tournament type:");
        lblCombo.setBounds(570, 140, 200, 14);
        panel.add(lblCombo);
        
        comboAddMatchTournamentType.addItem("Grand Slam");
        comboAddMatchTournamentType.addItem("Masters 1000");
        comboAddMatchTournamentType.setBounds(700, 140, 86, 20);        
        comboAddMatchTournamentType.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboAddMatchTournamentType.getSelectedItem().equals("Grand Slam")){
					lblSet4.setVisible(true);
					lblSet5.setVisible(true);
					textFieldAMset4.setVisible(true);
					textFieldAMset5.setVisible(true);
				} else if(comboAddMatchTournamentType.getSelectedItem().equals("Masters 1000")){
					lblSet4.setVisible(false);
					lblSet5.setVisible(false);
					textFieldAMset4.setVisible(false);
					textFieldAMset5.setVisible(false);
				}				
			}
		});
        panel.add(comboAddMatchTournamentType);
        
        JLabel lblComboMType = new JLabel("Match type:");
        lblComboMType.setBounds(800, 140, 100, 14);
        panel.add(lblComboMType);
        
        comboAddMatchType.addItem("Final");
        comboAddMatchType.addItem("Semifinal");
        comboAddMatchType.addItem("Quarterfinal");
        comboAddMatchType.setBounds(860, 140, 86, 20);        
        panel.add(comboAddMatchType);
        
        JButton btnAddMatch = new JButton("Add");
        btnAddMatch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboAddMatchTournamentType.getSelectedItem().equals("Grand Slam")){
					if( !textFieldAMset1.getText().equals("") 
							&& !textFieldAMset2.getText().equals("") && !textFieldAMset3.getText().equals("")){
						Match newMatch = new Match();
						List<PlayerRef> refs = new ArrayList<>();
						refs.add(new PlayerRef((String)comboAddMatchPlayer1.getSelectedItem()));
						refs.add(new PlayerRef((String)comboAddMatchPlayer2.getSelectedItem()));
						newMatch.setPlayerRefs(refs);
						List<Set> sets = new ArrayList<>();
						if(!textFieldAMset1.getText().equals(""))sets.add(new Set(textFieldAMset1.getText()));
						if(!textFieldAMset2.getText().equals(""))sets.add(new Set(textFieldAMset2.getText()));
						if(!textFieldAMset3.getText().equals(""))sets.add(new Set(textFieldAMset3.getText()));
						if(!textFieldAMset4.getText().equals(""))sets.add(new Set(textFieldAMset4.getText()));
						if(!textFieldAMset5.getText().equals(""))sets.add(new Set(textFieldAMset5.getText()));
						Result result = new Result(sets);
						newMatch.setResult(result);
						if(comboAddMatchType.getSelectedItem().equals("Final")){
							tennisService.addMatchToFinals(Integer.parseInt((String) comboBoxYears.getSelectedItem()),
									(String)comboBoxTours.getSelectedItem(),newMatch);
						} else if(comboAddMatchType.getSelectedItem().equals("Semifinal")){
							tennisService.addMatchToSemiFinals(Integer.parseInt((String) comboBoxYears.getSelectedItem()),
									(String)comboBoxTours.getSelectedItem(),newMatch);
						} else if(comboAddMatchType.getSelectedItem().equals("Quarterfinal")){
							tennisService.addMatchToQuarterFinals(Integer.parseInt((String) comboBoxYears.getSelectedItem()),
									(String)comboBoxTours.getSelectedItem(),newMatch);
						}
						addResultLbl.setText("Match successfully added!");
						textFieldAMset1.setText("");
						textFieldAMset2.setText("");
						textFieldAMset3.setText("");
						textFieldAMset4.setText("");
						textFieldAMset5.setText("");
					} else {
						addResultLbl.setText("Please fill sets information!");
					}
					 
				} else if(comboAddMatchTournamentType.getSelectedItem().equals("Masters 1000")){
					if(!textFieldAMset1.getText().equals("") 
							&& !textFieldAMset2.getText().equals("")){
						Match newMatch = new Match();
						List<PlayerRef> refs = new ArrayList<>();
						refs.add(new PlayerRef((String)comboAddMatchPlayer1.getSelectedItem()));
						refs.add(new PlayerRef((String)comboAddMatchPlayer2.getSelectedItem()));
						newMatch.setPlayerRefs(refs);
						List<Set> sets = new ArrayList<>();
						if(!textFieldAMset1.getText().equals(""))sets.add(new Set(textFieldAMset1.getText()));
						if(!textFieldAMset2.getText().equals(""))sets.add(new Set(textFieldAMset2.getText()));
						if(!textFieldAMset3.getText().equals(""))sets.add(new Set(textFieldAMset3.getText()));
						Result result = new Result(sets);
						newMatch.setResult(result);
						if(comboAddMatchType.getSelectedItem().equals("Final")){
							tennisService.addMatchToFinals(Integer.parseInt((String) comboBoxYears.getSelectedItem()),
									(String)comboBoxTours.getSelectedItem(),newMatch);
						} else if(comboAddMatchType.getSelectedItem().equals("Semifinal")){
							tennisService.addMatchToSemiFinals(Integer.parseInt((String) comboBoxYears.getSelectedItem()),
									(String)comboBoxTours.getSelectedItem(),newMatch);
						} else if(comboAddMatchType.getSelectedItem().equals("Quarterinal")){
							tennisService.addMatchToQuarterFinals(Integer.parseInt((String) comboBoxYears.getSelectedItem()),
									(String)comboBoxTours.getSelectedItem(),newMatch);
						}
						addResultLbl.setText("Match successfully added!");
						textFieldAMset1.setText("");
						textFieldAMset2.setText("");
						textFieldAMset3.setText("");
					} else {
						addResultLbl.setText("Please fill sets information!");
					}
				}
				
			}
		});
        btnAddMatch.setBounds(570, 290, 89, 23);
        panel.add(btnAddMatch);
        
        return panel;
    }
	
	public JComponent makeUpdatePanel(){
		JPanel panel = new JPanel(true);
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setLayout(null);
        
        JLabel updateResultLbl = new JLabel("");
        updateResultLbl.setBounds(10, 275, 413, 14);
        panel.add(updateResultLbl);
        
        JLabel lblUpdatePlayer = new JLabel("Update player");
        lblUpdatePlayer.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblUpdatePlayer.setBounds(10, 11, 150, 19);
        panel.add(lblUpdatePlayer);
        
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
        
        for(Player p : tennisService.findAllPlayers())
        	comboUpdatePlayerID.addItem(p.getId());
        comboUpdatePlayerID.setBounds(100, 40, 86, 20);
        comboUpdatePlayerID.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Player actualPlayer = tennisService.findPlayerById((String) comboUpdatePlayerID.getSelectedItem());
		    	textFieldUPname.setText(actualPlayer.getName());
		    	textFieldUPyearofbirth.setText(String.valueOf(actualPlayer.getYearOfBirth()));
		    	textFieldUPbirthplace.setText(actualPlayer.getBirthplace());
		    	textFieldUPweight.setText(String.valueOf(actualPlayer.getWeight()));
		    	textFieldUPheight.setText(String.valueOf(actualPlayer.getHeight()));
		    	textFieldUPturnedpro.setText(String.valueOf(actualPlayer.getTurnedPro()));
		    	textFieldUPplays.setText(actualPlayer.getPlays());
				
			}
		});
        panel.add(comboUpdatePlayerID);
        
        textFieldUPname = new JTextField();
        textFieldUPname.setBounds(100, 65, 86, 20);
        panel.add(textFieldUPname);
        textFieldUPname.setColumns(10);
        
        textFieldUPyearofbirth = new JTextField();
        textFieldUPyearofbirth.setBounds(100, 90, 86, 20);
        panel.add(textFieldUPyearofbirth);
        textFieldUPyearofbirth.setColumns(10);
        
        textFieldUPbirthplace = new JTextField();
        textFieldUPbirthplace.setBounds(100, 115, 86, 20);
        panel.add(textFieldUPbirthplace);
        textFieldUPbirthplace.setColumns(10);
        
        textFieldUPweight = new JTextField();
        textFieldUPweight.setBounds(100, 140, 86, 20);
        panel.add(textFieldUPweight);
        textFieldUPweight.setColumns(10);
        
        textFieldUPheight = new JTextField();
        textFieldUPheight.setBounds(100, 165, 86, 20);
        panel.add(textFieldUPheight);
        textFieldUPheight.setColumns(10);
        
        textFieldUPturnedpro = new JTextField();
        textFieldUPturnedpro.setBounds(100, 190, 86, 20);
        panel.add(textFieldUPturnedpro);
        textFieldUPturnedpro.setColumns(10);
        
        textFieldUPplays = new JTextField();
        textFieldUPplays.setBounds(100, 215, 86, 20);
        panel.add(textFieldUPplays);
        textFieldUPplays.setColumns(10);
        
        Player actualPlayer = tennisService.findPlayerById((String) comboUpdatePlayerID.getSelectedItem());
    	textFieldUPname.setText(actualPlayer.getName());
    	textFieldUPyearofbirth.setText(String.valueOf(actualPlayer.getYearOfBirth()));
    	textFieldUPbirthplace.setText(actualPlayer.getBirthplace());
    	textFieldUPweight.setText(String.valueOf(actualPlayer.getWeight()));
    	textFieldUPheight.setText(String.valueOf(actualPlayer.getHeight()));
    	textFieldUPturnedpro.setText(String.valueOf(actualPlayer.getTurnedPro()));
    	textFieldUPplays.setText(actualPlayer.getPlays());
        
        JButton btnUpdatePlayer = new JButton("Update");
        btnUpdatePlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!textFieldUPname.getText().equals("")
							&& !textFieldUPyearofbirth.getText().equals("")
							&& !textFieldUPbirthplace.getText().equals("") && !textFieldUPweight.getText().equals("")
							&& !textFieldUPheight.getText().equals("") && !textFieldUPturnedpro.getText().equals("")
							&& !textFieldUPplays.getText().equals("")) {
						Player newPlayer = new Player((String)comboUpdatePlayerID.getSelectedItem(), textFieldUPname.getText(),
								Integer.parseInt(textFieldUPyearofbirth.getText()), textFieldUPbirthplace.getText(),
								Integer.parseInt(textFieldUPweight.getText()),
								Integer.parseInt(textFieldUPheight.getText()),
								Integer.parseInt(textFieldUPturnedpro.getText()), textFieldUPplays.getText());
						if (tennisService.updatePlayer(newPlayer)) {
							textFieldAPid.setText("");
							textFieldAPname.setText("");
							textFieldAPyearofbirth.setText("");
							textFieldAPbirthplace.setText("");
							textFieldAPweight.setText("");
							textFieldAPheight.setText("");
							textFieldAPturnedpro.setText("");
							textFieldAPplays.setText("");
							updateResultLbl.setText("Player successfully updated!");
							comboAddMatchPlayer1.addItem(newPlayer.getId());
							comboAddMatchPlayer2.addItem(newPlayer.getId());
						} else {
							updateResultLbl.setText("Update Player failed!");
						}

					} else {
						updateResultLbl.setText("Please fill player details!");
					}
				} catch (NumberFormatException f) {
					textFieldAPid.setText("");
					textFieldAPname.setText("");
					textFieldAPyearofbirth.setText("");
					textFieldAPbirthplace.setText("");
					textFieldAPweight.setText("");
					textFieldAPheight.setText("");
					textFieldAPturnedpro.setText("");
					textFieldAPplays.setText("");
					updateResultLbl.setText("Year, weight, height and turnedpro should be numbers!");
				}
			}
		});
        btnUpdatePlayer.setBounds(10, 238, 89, 23);
        panel.add(btnUpdatePlayer);
    
        return panel;
	}
	
	public JComponent makeRemovePanel(){
		JPanel panel = new JPanel(true);
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setLayout(null);
            
        JLabel lblNewLabel_9 = new JLabel("Remove Player");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_9.setBounds(10, 45, 155, 14);
        panel.add(lblNewLabel_9);
        
        JLabel lblNewLabel_10 = new JLabel("ID:");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_10.setBounds(10, 75, 50, 14);
        panel.add(lblNewLabel_10);
        
        JLabel lblRemovePlayer = new JLabel("Remove player");
        lblRemovePlayer.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblRemovePlayer.setBounds(10, 11, 123, 19);
        panel.add(lblRemovePlayer);
        
        for(Player p : tennisService.findAllPlayers())
        	comboRemovePlayerID.addItem(p.getId());
        comboRemovePlayerID.setBounds(150, 75, 86, 20);
        panel.add(comboRemovePlayerID);
        
        JLabel lblRemoveInfo = new JLabel("");
        lblRemoveInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblRemoveInfo.setBounds(10, 265, 339, 14);
        panel.add(lblRemoveInfo);
        
        JButton btnPlayer = new JButton("Remove");
		btnPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tennisService.removePlayer((String) comboRemovePlayerID.getSelectedItem())){
					lblRemoveInfo.setText("Player removed!");
					comboAddMatchPlayer1.removeItem(comboRemovePlayerID.getSelectedItem());
					comboAddMatchPlayer2.removeItem(comboRemovePlayerID.getSelectedItem());
					comboUpdatePlayerID.removeItem(comboRemovePlayerID.getSelectedItem());
					comboRemovePlayerID.removeItem(comboRemovePlayerID.getSelectedItem());
				} else {
					lblRemoveInfo.setText("Player can't be removed!");
				}
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
        
        for(String s : tennisService.findAllYears())
        	comboRemoveTournamentYear.addItem(s);
        comboRemoveTournamentYear.setBounds(150, 135, 86, 20);
        comboRemoveTournamentYear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboRemoveTournamentName.removeAllItems();
				for(Tournament t : tennisService.findAllTournamentsByYear(Integer.parseInt((String) comboRemoveTournamentYear.getSelectedItem())))
						comboRemoveTournamentName.addItem(t.getName());
			}
		});
        panel.add(comboRemoveTournamentYear);
        
        for(Tournament t : tennisService.findAllTournamentsByYear(Integer.parseInt((String)comboRemoveTournamentYear.getSelectedItem())))
        		comboRemoveTournamentName.addItem(t.getName());
        comboRemoveTournamentName.setBounds(150, 165, 86, 20);
        panel.add(comboRemoveTournamentName);
        
        JButton btnRemoveTournament = new JButton("Remove");
		btnRemoveTournament.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tennisService.removeTournament((String) comboRemoveTournamentName.getSelectedItem(),
						Integer.parseInt((String) comboRemoveTournamentYear.getSelectedItem()))) {
					comboBoxTours.removeItem(comboRemoveTournamentName.getSelectedItem());
					comboRemoveTournamentName.removeItem(comboRemoveTournamentName.getSelectedItem());
					lblRemoveInfo.setText("Tournament removed successfully!");
				} else {
					lblRemoveInfo.setText("Tournament can't be removed!");
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
        
        for(String s : tennisService.findAllYears())
        	comboRemoveSeasonYear.addItem(s);
        comboRemoveSeasonYear.setBounds(150, 225, 86, 20);
        panel.add(comboRemoveSeasonYear);
        
        JButton btnRemoveSeason = new JButton("Remove");
		btnRemoveSeason.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tennisService.removeSeason(Integer.parseInt((String) comboRemoveSeasonYear.getSelectedItem()))) {
					comboAddTournamentYear.removeItem(comboRemoveSeasonYear.getSelectedItem());
					comboRemoveTournamentYear.removeItem(comboRemoveSeasonYear.getSelectedItem());
					comboBoxYears.removeItem(comboRemoveSeasonYear.getSelectedItem());
					comboRemoveSeasonYear.removeItem(comboRemoveSeasonYear.getSelectedItem());
					lblRemoveInfo.setText("Season removed successfully!");
				} else {
					lblRemoveInfo.setText("Season can't be removed!");
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

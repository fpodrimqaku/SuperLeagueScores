package javafxapplication7;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class Main extends Application 
{	
	Statement st = null;
	
	public void initializeMatches(Connection conn, TableView matches)
	{
		matches.getItems().clear();
		matches.getColumns().clear();
		ObservableList<Match> optMatches = FXCollections.observableArrayList();
		
		TableColumn firstTeam = new TableColumn("Team 1");
		firstTeam.setCellValueFactory(new PropertyValueFactory<Match,String>("firstTeam"));
		TableColumn secondTeam = new TableColumn("Team 2");
		secondTeam.setCellValueFactory(new PropertyValueFactory<Match,String>("secondTeam"));
		TableColumn date = new TableColumn("Date");
		date.setCellValueFactory(new PropertyValueFactory<Match,String>("date"));
		TableColumn week = new TableColumn("Week");
		week.setCellValueFactory(new PropertyValueFactory<Match,Integer>("week"));
		TableColumn season = new TableColumn("Season");
		season.setCellValueFactory(new PropertyValueFactory<Match,Integer>("season"));
		
		matches.getColumns().addAll(firstTeam, secondTeam, date, week, season);
		
		ResultSet fetchedMatches = Helper.fetchMatches(conn);
		try
		{
			while(fetchedMatches.next())
			{
				String a = fetchedMatches.getString(2);
				String b = fetchedMatches.getString(3);
				String c = fetchedMatches.getString(4);
				int d = Integer.parseInt(fetchedMatches.getString(5));
				int e = Integer.parseInt(fetchedMatches.getString(6));
				
				optMatches.add(new Match(a, b, c, d, e));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		matches.setItems(optMatches);
	}
	
	public void addResultSetIntoComboBox(ResultSet result_set, ComboBox combo_box)
	{
		combo_box.getItems().clear();
		ObservableList<String> optTeams = FXCollections.observableArrayList();
		try
		{
			while (result_set.next())
			{
				optTeams.add(result_set.getString(1));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		combo_box.setItems(optTeams);
	}
	
	@Override
	public void start(Stage stage) {
		try 
		{			
			// Initiating lines
			java.sql.Connection conn = Lidhja.getConnection();
			ResultSet fetched_teams = Helper.fetchTeams(conn);
			ResultSet fetched_seasons = Helper.fetchSeasons(conn);
			
			BorderPane pane = new BorderPane();
			
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setWidth(950);
			stage.setHeight(650);
			stage.setTitle("Super League Scores");
			
			// Left team 
			VBox left = new VBox();
			VBox leftUp = new VBox();	
			VBox leftDown = new VBox();
			
			leftUp.setAlignment(Pos.CENTER);
			leftUp.setSpacing(25);
			leftUp.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-border-insets: 10; -fx-border-style: solid inside; -fx-border-color: #00a0ff;");
			
			leftDown.setAlignment(Pos.CENTER);
			leftDown.setSpacing(25);
			leftDown.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-border-insets: 10; -fx-border-style: solid inside; -fx-border-color: #00a0ff;");
			
			left.getChildren().addAll(leftUp, leftDown);
			

			ComboBox cmbTeam1 = new ComboBox();
			ComboBox cmbTeam2 = new ComboBox();
			TableView matches = new TableView();
			matches.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			// Bottom
			VBox bottom = new VBox();
			bottom.setAlignment(Pos.CENTER);
			Label lblNotify = new Label("");
			bottom.getChildren().add(lblNotify);
			pane.setBottom(bottom);
			
			// Left up
			Label lblLeftUp = new Label ("Season and Week Manager");
			
			HBox leftUp1 = new HBox();
			HBox leftUp2 = new HBox();
			
			leftUp1.setAlignment(Pos.CENTER);
			leftUp1.setSpacing(50);
			
			leftUp2.setSpacing(50);
			leftUp2.setAlignment(Pos.CENTER);
			
			HBox leftUp3 = new HBox();
			HBox leftUp4 = new HBox();
			
			TextField txtSeason = new TextField("");
			txtSeason.setPromptText("Season");
			TextField txtWeek = new TextField("");
			txtWeek.setPromptText("Week");
			
			txtSeason.setPrefWidth(80);
			txtWeek.setPrefWidth(80);
			
			leftUp1.getChildren().addAll(txtSeason, txtWeek);
			
			ComboBox cmbSeasons = new ComboBox();
			cmbSeasons.setPrefWidth(80);
			ComboBox cmbWeeks = new ComboBox();
			cmbWeeks.setPrefWidth(80);
			addResultSetIntoComboBox(fetched_seasons, cmbSeasons);
			cmbSeasons.getSelectionModel().selectFirst();
			cmbSeasons.valueProperty().addListener(new ChangeListener<String>() {
	            @Override
	            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
	            {
	            	try
	            	{
	            		int currentSeason = Integer.parseInt(cmbSeasons.getSelectionModel().getSelectedItem().toString());
	            	
	            		ResultSet fetched_weeks = Helper.fetchWeeks(conn, currentSeason);
	            		addResultSetIntoComboBox(fetched_weeks, cmbWeeks);
	            	}
	            	catch (Exception ex)
	            	{
	            		return;
	            	}
	            }
	        });

			try
			{
				addResultSetIntoComboBox(Helper.fetchWeeks(conn, Integer.parseInt(cmbSeasons.getSelectionModel().getSelectedItem().toString())), cmbWeeks);
			}
			catch (Exception ex)
			{
			}
			
			leftUp2.getChildren().addAll(cmbSeasons, cmbWeeks);
			
			Button btnCreateSeason = new Button("New Season");
			btnCreateSeason.setPrefWidth(130);
			btnCreateSeason.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		        @Override 
		        public void handle(MouseEvent e) 
		        {
		        	int currentSeason = Integer.parseInt(txtSeason.getText());
		        	
		        	try
		        	{
		        		st = conn.createStatement();
		        		st.execute("insert into Seasons () values ("+currentSeason+");");		      
		        		
		        		addResultSetIntoComboBox(Helper.fetchSeasons(conn), cmbSeasons);
		        		
		        		lblNotify.setText("You have successfully added the season");
		        	}
		        	catch (Exception ex)
		        	{
		        		lblNotify.setText("Please write a number");
		        	}
		        }
			});
			Button btnDeleteSeason = new Button("Del Season");
			btnDeleteSeason.setPrefWidth(130);
			btnDeleteSeason.addEventHandler(MouseEvent.MOUSE_CLICKED,  new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					try
					{
						int currentSeason = Integer.parseInt(cmbSeasons.getSelectionModel().getSelectedItem().toString());
						
						st = conn.createStatement();
						st.execute("delete from Seasons where season="+currentSeason+";");
						
						addResultSetIntoComboBox(Helper.fetchSeasons(conn), cmbSeasons);
						addResultSetIntoComboBox(Helper.fetchWeeks(conn, currentSeason), cmbWeeks);
						
						lblNotify.setText("Season deleted successfully");
					}
					catch (Exception ex)
					{
						
						ex.printStackTrace();
						lblNotify.setText("The season could not be deleted");
					}

				}
				
			});
			leftUp3.getChildren().addAll(btnCreateSeason, btnDeleteSeason);
			
			Button btnCreateSW = new Button("New Week");
			btnCreateSW.addEventHandler(MouseEvent.MOUSE_CLICKED,  new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					try
					{
						int currentSeason = Integer.parseInt(cmbSeasons.getSelectionModel().getSelectedItem().toString());
						int currentWeek = Integer.parseInt(txtWeek.getText());
						
						st = conn.createStatement();
						st.execute("insert into Weeks (week, season) values ("+currentWeek+", "+currentSeason+");");
						
						addResultSetIntoComboBox(Helper.fetchWeeks(conn, currentSeason), cmbWeeks);
						
						lblNotify.setText("The week has successfully been created");
					}
					catch (Exception ex)
					{
						lblNotify.setText("The week was not created, plase provide valid information");
					}
				}
			});
			Button btnDeleteSW = new Button("Del Week");
			btnDeleteSW.addEventHandler(MouseEvent.MOUSE_CLICKED,  new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) 
				{
					try
					{
						int currentSeason = Integer.parseInt(cmbSeasons.getSelectionModel().getSelectedItem().toString());
						int currentWeek = Integer.parseInt(txtWeek.getText());
						
						st = conn.createStatement();
						st.execute("delete from Weeks where week="+currentWeek+" and season="+currentSeason+";");
						
						addResultSetIntoComboBox(Helper.fetchWeeks(conn, currentSeason), cmbWeeks);
						
						lblNotify.setText("Week succesfully deleted");
					}
					catch (Exception ex)
					{
						lblNotify.setText("Week could not be deleted");
					}
				}
			});
			
			btnCreateSW.setPrefWidth(130);
			btnDeleteSW.setPrefWidth(130);
			leftUp4.getChildren().addAll(btnCreateSW, btnDeleteSW);
			
			leftUp.getChildren().addAll(lblLeftUp, leftUp1, leftUp2, leftUp3, leftUp4);
			
			// Left down
			Label lblLeftDown = new Label("Teams Manager");
			
			TextField txtTeamName = new TextField();
			txtTeamName.setPromptText("New Team Name");
			txtTeamName.setPrefWidth(120);
			
			ComboBox cmbTeams = new ComboBox();
			addResultSetIntoComboBox(fetched_teams, cmbTeams);
			cmbTeams.setPrefSize(150, 30);
			
			Button btnCreateTeam = new Button("Create Team");
			btnCreateTeam.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) 
				{
					String current_team = txtTeamName.getText();
					
					try
					{
						st = conn.createStatement();
						
						conn.createStatement();
						st.execute("insert into Teams (name) values ('"+current_team+"');");
						
						addResultSetIntoComboBox(Helper.fetchTeams(conn), cmbTeams);
						addResultSetIntoComboBox(Helper.fetchTeams(conn), cmbTeam1);
						addResultSetIntoComboBox(Helper.fetchTeams(conn), cmbTeam2);
						
						lblNotify.setText("The team was successfully created");
					}
					catch (Exception ex)
					{
						lblNotify.setText("The team could not be created");
					}
				}
				
			});
			btnCreateTeam.setPrefSize(150, 30);
			
			Button btnDeleteTeam = new Button("Delete Team");
			btnDeleteTeam.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent e) 
		        {
					String selected_item = cmbTeams.getSelectionModel().getSelectedItem().toString();
					
					try
					{
						st = conn.createStatement();
						st.execute("delete from Teams where name='"+selected_item+"';");
						
						addResultSetIntoComboBox(Helper.fetchTeams(conn), cmbTeams);
						
						lblNotify.setText("The team was successfully deleted");
					}
					catch(Exception ex)
					{
						lblNotify.setText("The team could not be deleted");
					}		        	
		        }
			});

			btnDeleteTeam.setPrefSize(150, 30);
			leftDown.getChildren().addAll(lblLeftDown, txtTeamName, cmbTeams, btnCreateTeam, btnDeleteTeam);
			pane.setLeft(left);
			
			// Center up
			VBox center = new VBox();
			VBox centerUp = new VBox();
			centerUp.setSpacing(18);
			centerUp.setAlignment(Pos.CENTER);
			centerUp.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-border-insets: 10; -fx-border-style: solid inside; -fx-border-color: #00a0ff;");
			
			Label lblCenterUp = new Label("Matches Manager");
			
			HBox centerUp1 = new HBox();
			centerUp1.setAlignment(Pos.CENTER);
			centerUp1.setSpacing(100);
			Label lblTeam1 = new Label("Team 1");
			Label lblTeam2 = new Label("Team 2");
			Label lblDate = new Label("Date");
			
			HBox centerUp2 = new HBox();
			centerUp2.setAlignment(Pos.CENTER);
			centerUp2.setSpacing(20);
			cmbTeam1.setPrefWidth(150);
			
			cmbTeam2.setPrefWidth(150);
			addResultSetIntoComboBox(Helper.fetchTeams(conn), cmbTeam1);
			addResultSetIntoComboBox(Helper.fetchTeams(conn), cmbTeam2);
			TextField txtDate = new TextField("");
			txtDate.setPromptText("YYYY-MM-DD");
			txtDate.setPrefWidth(150);
			
			HBox centerUp3 = new HBox();
			centerUp3.setAlignment(Pos.CENTER);
			centerUp3.setSpacing(20);
			
			ComboBox cmbWeekPicker = new ComboBox();
			cmbWeekPicker.setPrefWidth(100);
			ComboBox cmbSeasonPicker = new ComboBox();
			cmbSeasonPicker.setPrefWidth(100);
			addResultSetIntoComboBox(Helper.fetchSeasons(conn), cmbSeasonPicker);
			cmbSeasonPicker.valueProperty().addListener(new ChangeListener<String>() {
	            @Override
	            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
	            {
	            	try
	            	{
	            		int currentSeason = Integer.parseInt(cmbSeasonPicker.getSelectionModel().getSelectedItem().toString());
	            	
	            		ResultSet fetched_weeks = Helper.fetchWeeks(conn, currentSeason);
	            		addResultSetIntoComboBox(fetched_weeks, cmbWeekPicker);
	            	}
	            	catch (Exception ex)
	            	{
	            		return;
	            	}
	            }
	        });
			
			HBox centerUp5 = new HBox();
			centerUp5.setSpacing(20);
			centerUp5.setAlignment(Pos.CENTER);
			Button btnAddGame = new Button("Add Game");
			btnAddGame.setPrefSize(125, 35);
			btnAddGame.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent e) 
		        {
					String team_one = cmbTeam1.getSelectionModel().getSelectedItem().toString();
					String team_two = cmbTeam2.getSelectionModel().getSelectedItem().toString();
					String date = txtDate.getText();
					int season = Integer.parseInt(cmbSeasonPicker.getSelectionModel().getSelectedItem().toString());
					int week = Integer.parseInt(cmbWeekPicker.getSelectionModel().getSelectedItem().toString());
					
					try
					{
				    	st = conn.createStatement();
						st.execute("insert into Matches (first_team, second_team, date, week, season) values ('"+team_one+"', '"+team_two+"', '"+date+"', "+week+", "+season+")");
					
						initializeMatches(conn, matches);
						
						lblNotify.setText("The match was successfully recorded");
					
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
						
						lblNotify.setText("The match could not be added, recheck information please");
					}	        	
		        }
			});
			Button btnJoinChat = new Button("Join Chat");
			btnJoinChat.setPrefSize(125, 35);
			btnJoinChat.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent e) 
		        {
		        	try
		        	{
		        		Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
		            
		        		Scene scene = new Scene(root);
			       
			            stage.setScene(scene);
			            stage.show();
		        	}
		        	catch (Exception ex)
		        	{
		        		ex.printStackTrace();
		        	}
		        }
			});
			TextField txtChatRoom = new TextField("");
			txtChatRoom.setPromptText("Room name");
			txtChatRoom.setPrefWidth(200);
			
			
			centerUp1.getChildren().addAll(lblTeam1, lblTeam2, lblDate);
			centerUp2.getChildren().addAll(cmbTeam1, cmbTeam2, txtDate);
			centerUp3.getChildren().addAll(new Label("Season"),cmbSeasonPicker, new Label("Week"), cmbWeekPicker);
			centerUp5.getChildren().addAll(btnAddGame, txtChatRoom, btnJoinChat);
			
			centerUp.getChildren().addAll(lblCenterUp, centerUp1, centerUp2, centerUp3, centerUp5);
			
			// Center down
			VBox centerDown = new VBox();
			centerDown.setAlignment(Pos.CENTER);
			centerDown.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-border-insets: 10; -fx-border-style: solid inside; -fx-border-color: #00a0ff;");
			
			initializeMatches(conn, matches);
			
			centerDown.getChildren().addAll(matches);
			
			center.getChildren().addAll(centerUp, centerDown);
			
			pane.setCenter(center);
			
			lblNotify.setText("Base window loaded");
			stage.show();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

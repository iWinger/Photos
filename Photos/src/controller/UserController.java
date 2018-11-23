package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.Serial;
import model.User;
/**
 * The controller for the user view. Accesible via signing in to the program with a valid username.
 * This controller offers the user the ability to create/delete albums, rename albums, and open the search interface where users can search for albums by tag values.
 * 
 * @author Wingjun Chan
 * @author Jessi Medina
 *
 * Date: 11/21/2018
 */
public class UserController implements Serializable{
/* Interact with the User FXML file here */
@FXML private Button OpenAlbum;
@FXML private Button RenameAlbum;
@FXML private Button CreateAlbum;
@FXML private Button SearchAlbum;
@FXML private Button DeleteAlbum;
@FXML private Button Logout;
@FXML private Button Exit;
@FXML private 
ArrayList<User> users;
@FXML ListView<String> albumList;
static String saveUser;
String[] arr = new String[]{"FILLER", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
ObservableList<String> obsAlbumList = FXCollections.observableArrayList();
int userIndex = 0;
public int realMonth;
public int realDay;
public int realYear;
public int realMonth2;
public int realDay2;
public int realYear2;
String tagTypeS1;
String tagValueS1;
String tagTypeM1;
String tagValueM1;
String tagTypeM2;
String tagValueM2;


/**
 * starts the UserController. loads the albums that are saved under the specified user.
 * 
 * @param primaryStage
 * @throws IOException
 */

public void start(Stage primaryStage) throws IOException{
	//System.out.println();
	String str = saveUser;
	
	try {
		users = Serial.readUser();
	}
	catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
	
	

	//System.out.println(saveUser);
	
	for ( int i = 0; i < users.size(); i++) {
		if(users.get(i).getUsername().equals(str)) {
			userIndex = i;
			break;
		}
	}
	
//	if(obsAlbumList.size()>0)
	//	System.out.println("EE");
	
	//System.out.println(users.get(index).getUserAlbums().get(0).getNumPhotos());	
if(users.get(userIndex).getUserAlbums() != null) {
	for (int j = 0; j < users.get(userIndex).getUserAlbums().size(); j++) {	
	//	System.out.println(users.get(index).getUserAlbums().get(j).getAlbumDetail());
		users.get(userIndex).getUserAlbums().get(j).setAlbumDetail();
	obsAlbumList.add(users.get(userIndex).getUserAlbums().get(j).getAlbumDetail());
	}
		}
	obsAlbumList.sort(String::compareTo);
	
	
	albumList.setItems(obsAlbumList);
	albumList
    .getSelectionModel()
    .selectedIndexProperty()
    .addListener(
       (obs, oldVal, newVal) -> 
           {
			
		});
	// TODO Auto-generated method stub
	// Only happens once in this code block

}




	private void showItemInputDialog(Stage primaryStage) {
		String item = albumList.getSelectionModel().getSelectedItem();     
	      int index = albumList.getSelectionModel().getSelectedIndex();
	      Alert alert = new Alert(AlertType.INFORMATION);
	      alert.initOwner(primaryStage); 
	      alert.setTitle("List Item");    
	      alert.setContentText(item);
	      alert.showAndWait();
	// TODO Auto-generated method stub
	
}

	/**
	 * This method will direct the user to a prompt that allows them to enter a name for the new album they would like to create.
	 * 
	 * @param event the event called that triggers this function: Clicking on the create album button in the user view.
	 * @throws IOException
	 */
	public void handleCreateNewAlbumButtonAction(ActionEvent event) throws IOException{
	
		Stage mainStage = new Stage();
		VBox root = new VBox(6);
		
		TextField newAlbum = new TextField();
    	root.getChildren().add(newAlbum);
    	newAlbum.setPromptText("Album Name: ");
    	newAlbum.setFocusTraversable(false);
    	
    	
    	//newAlbum.getText(); to retrieve data 
		
		Button create = new Button("Create ");
    	root.getChildren().add(create);
    	
    	Button back = new Button("Back");
    	root.getChildren().add(back);
    	
    	Button logout = new Button("Logout");
    	root.getChildren().add(logout);
    	
    	Button exit = new Button("Exit Application");
    	root.getChildren().add(exit);
    	
    	
    	Scene scene2 = new Scene(root,400,400);
        mainStage.setTitle("Create Album: ");
        mainStage.setScene(scene2);
        mainStage.show();
	    
        create.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		try {
            		users = Serial.readUser();
            	}
            	catch(ClassNotFoundException e) {
            		e.printStackTrace();
            	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	String newAlbumName = newAlbum.getText();
            	Album renamedalbum = new Album(newAlbumName);
            	users.get(userIndex).getUserAlbums().add(renamedalbum);
            	renamedalbum.setAlbumDetail();
            	obsAlbumList.add(renamedalbum.getAlbumDetail());
        		albumList.setItems(obsAlbumList);
            	
        		//System.out.println(newAlbumName);
            	try {
            		Serial.writeUser(users);
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	
        		mainStage.close();
        	}
        });
        
        back.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	mainStage.close();
        	}
        });
        
        logout.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	mainStage.close();
        	try {
    			// Close the first window by getting the stage of that.
    			Stage firstStage = (Stage)Logout.getScene().getWindow();
    			firstStage.close();
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
    		Parent root1 = (Parent)fxmlLoader.load();
    		Stage stage = new Stage();
    		
    		Scene scene2 = new Scene(root1,400,500);
    		stage.setScene(scene2);
    		stage.show();
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
        	
        	}
        });
        
        exit.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	System.exit(0);
        	
        	
        	}
        });
        
       
	}
	
	/**
	 * This method will delete the currently selected album and its contents.
	 * 
	 * @param event the event called that triggers this function: Clicking on the delete button in the user view.
	 * @throws IOException
	 */
	
	public void handleDeleteAlbumButtonAction(ActionEvent event) throws IOException{
		 
			String item = albumList.getSelectionModel().getSelectedItem();
			//System.out.println(item);
			int selected = albumList.getSelectionModel().getSelectedIndex();
			int index = 0;
			int obsindex = 0;
			try {
				users = Serial.readUser();
			}
			catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		
			
			for(int i = 0; i < users.get(userIndex).getUserAlbums().size(); i++)
			{
				
				if(users.get(userIndex).getUserAlbums().get(i).getAlbumDetail().equals(item)) {
					//System.out.println(i);
					//System.out.println(users.get(userIndex).getUserAlbums().get(i));
					index = i;
					break;
				}
			}
			
			for(int i = 0; i < obsAlbumList.size(); i++)
			{
				if(obsAlbumList.get(i).equals(item)) {
					obsindex = i;
					break;
				}
			}
			obsAlbumList.remove(obsindex);
			//System.out.println(index);
			users.get(userIndex).getUserAlbums().remove(index);
			albumList.setItems(obsAlbumList);
			
		/*	if(selected == obsUserList.size()-1)
				userList.getSelectionModel().select(selected--);
			else
				userList.getSelectionModel().select(selected++);
			*/
			try {
				Serial.writeUser(users);
				
			}
			catch(IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * This method will direct the user to a prompt that allows them to rename the selected album.
	 * 
	 * @param event the event called that triggers this function: Clicking on the rename button in the user view.
	 * @throws IOException
	 */
	
	public void handleRenameAlbumButtonAction(ActionEvent event) throws IOException{
		
		Stage mainStage = new Stage();
		VBox root = new VBox(6);
		
		TextField renamedAlbum = new TextField();
    	root.getChildren().add(renamedAlbum);
    	renamedAlbum.setPromptText("Rename Album: ");
    	renamedAlbum.setFocusTraversable(false);
    	//renamedAlbum.getText(); to retrieve data 
		
		Button rename = new Button("Rename ");
    	root.getChildren().add(rename);
    	
    	Button back = new Button("Back");
    	root.getChildren().add(back);
    	
    	Button logout = new Button("Logout");
    	root.getChildren().add(logout);
    	
    	Button exit = new Button("Exit Application");
    	root.getChildren().add(exit);
    	
    	
    	Scene scene2 = new Scene(root,400,400);
        mainStage.setTitle("Rename Album: ");
        mainStage.setScene(scene2);
        mainStage.show();
        
        rename.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		try {
            		users = Serial.readUser();
            	}
            	catch(ClassNotFoundException e) {
            		e.printStackTrace();
            	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		
        		int albumindex = 0;
        		int obsindex = 0;
        		String item = albumList.getSelectionModel().getSelectedItem();
        			
        		//System.out.println(users.get(userIndex));
        		
        		for(int i = 0; i < users.get(userIndex).getUserAlbums().size(); i++)
        		{
        			if(users.get(userIndex).getUserAlbums().get(i).equals(item)) {
        				albumindex = i;
        				break;
        			}
        		}
        		
        		
        		for(int i = 0; i < obsAlbumList.size(); i++)
        		{
        			if(obsAlbumList.get(i).equals(item)) {
        				obsindex = i;
        				break;
        			}
        		}
        		
        		
            	String renamedAlbumName = renamedAlbum.getText();
            	users.get(userIndex).getUserAlbums().get(albumindex).setAlbumName(renamedAlbumName);
            	users.get(userIndex).getUserAlbums().get(albumindex).setAlbumDetail();
            	String entireAlbumName = users.get(userIndex).getUserAlbums().get(albumindex).getAlbumDetail();
            	obsAlbumList.set(obsindex, entireAlbumName);
            	//users.get(userobsAlbumList.add(newAlbumName);
        		albumList.setItems(obsAlbumList);
            	//System.out.println(newAlbumName);
            	try {
            		Serial.writeUser(users);
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
        		
        	
        		
        		mainStage.close();
        	}
        });
	    
        back.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	mainStage.close();
        	}
        });
        
        logout.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	mainStage.close();
        	try {
    			// Close the first window by getting the stage of that.
    			Stage firstStage = (Stage)Logout.getScene().getWindow();
    			firstStage.close();
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
    		Parent root1 = (Parent)fxmlLoader.load();
    		Stage stage = new Stage();
    		
    		Scene scene2 = new Scene(root1,400,500);
    		stage.setScene(scene2);
    		stage.show();
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
        	
        	}
        });
		
        
        exit.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	System.exit(0);
        	
        	
        	}
        });
        
        
	}
	/**
	 * This method will direct the user to the open album view. In this view they can make changes to albums including adding and deleting photos, moving and copying 
	 * photos, editing and deleting captions and tags, and more.
	 * 
	 * @param event the event called that triggers this function: Clicking on the open album button in the user view.
	 * @throws IOException
	 */

	public void handleAlbumButtonAction(ActionEvent event) throws IOException {
		try {
			if(albumList.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("You must open an album first!!");
				alert.show();
				return;
					
			}
			// Close the first window by getting the stage of that.
			String item = albumList.getSelectionModel().getSelectedItem();
			int albumIndex = albumList.getSelectionModel().getSelectedIndex();
			Stage firstStage = (Stage)OpenAlbum.getScene().getWindow();
			firstStage.close();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/OpenAlbum.fxml"));
		Parent root1 = (Parent)fxmlLoader.load();
		Stage stage = new Stage();
		OpenAlbumController openAlbumController = fxmlLoader.getController();
		
	
		OpenAlbumController.saveUser = saveUser;
		OpenAlbumController.albumDetail = item;
		OpenAlbumController.userIndex = userIndex;
		OpenAlbumController.albumIndex = albumIndex;
		OpenAlbumController.obsAlbumList_ = obsAlbumList;
		openAlbumController.start(stage);
		stage.setScene(new Scene(root1));
		stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method will direct the user to the search interface where they can search for photos by tag values.
	 * 
	 * @param event the event called that triggers this function: Clicking on the search button in the user view.
	 * @throws IOException
	 */
	
	public void handleSearchAlbumButtonAction(ActionEvent event) throws IOException{
		
		
		Stage mainStage = new Stage();
		VBox root = new VBox(6);
		
		
		Button date = new Button("Date ");
    	root.getChildren().add(date);
    	
    	Button tags = new Button("Tags ");
    	root.getChildren().add(tags);
    	
    	Button back = new Button("Back");
    	root.getChildren().add(back);
    	
    	Button logout = new Button("Logout");
    	root.getChildren().add(logout);
    	
    	Button exit = new Button("Exit Application");
    	root.getChildren().add(exit);
    	
    
	    
        back.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	mainStage.close();
        	}
        });
        
        logout.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	mainStage.close();
        	try {
    			// Close the first window by getting the stage of that.
    			Stage firstStage = (Stage)Logout.getScene().getWindow();
    			firstStage.close();
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
    		Parent root1 = (Parent)fxmlLoader.load();
    		Stage stage = new Stage();
    		
    		Scene scene2 = new Scene(root1,400,500);
    		stage.setScene(scene2);
    		stage.show();
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
        	
        	}
        });
        
        exit.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	System.exit(0);
        	
        	
        	}
        });
        
        Scene scene2 = new Scene(root,400,400);
        mainStage.setTitle("Search-by");
		mainStage.setScene(scene2);
		mainStage.show();
		
		/* Date and Tags Buttons Implementation */
		
		 date.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent event) {
	        	
	        		Stage mainStage = new Stage();
	        		VBox root = new VBox(7);
	        		
	        		
	        		TextField from = new TextField("from: ");
	        		from.setDisable(true);
	        		root.getChildren().add(from);
	        		
	        		TextField date1 = new TextField();
	        		date1.setPromptText("mm/dd/yy");
	        		date1.setFocusTraversable(false);
	        		root.getChildren().add(date1);
	        		
	        		TextField to = new TextField("to: ");
	        		to.setDisable(true);
	        		root.getChildren().add(to);
	        		
	        		TextField date2 = new TextField();
	        		date2.setPromptText("mm/dd/yy");
	        		date2.setFocusTraversable(false);
	        		root.getChildren().add(date2);
	        		
	            	Button search = new Button("Search");
	            	root.getChildren().add(search);
	        		
	            	Button back = new Button("Back");
	            	root.getChildren().add(back);
	            	
	            	Button logout = new Button("Logout");
	            	root.getChildren().add(logout);
	            	
	            	Button exit = new Button("Exit Application");
	            	root.getChildren().add(exit);
	            	
	            	
	            	
	            	search.setOnAction(new EventHandler<ActionEvent>() {
	            		public void handle(ActionEvent event) {
	            			if(!date1.getText().isEmpty() && (date1.getText().length() == 10) && (!date2.getText().isEmpty()) && (date2.getText().length() == 10)) {
	        	            	String mdy = date1.getText();
	        	            	String month = mdy.substring(0,2);
	        	            	realMonth = Integer.parseInt(month);	        	            	
	        	            	
	        	            //	System.out.println(realMonth);
	        	            	String day = mdy.substring(3,5);
	        	            	realDay = Integer.parseInt(day);
	        	            	//System.out.println(realDay);
	        	            	
	        	            	String year = mdy.substring(6,10);
	        	            	realYear = Integer.parseInt(year);
	        	            	//System.out.println(realYear);
	        	            	
	        	            	String mdy2 = date2.getText();
	        	            	String month2 = mdy2.substring(0,2);
	        	            	realMonth2 = Integer.parseInt(month2);	        	            
	        	            	//System.out.println(realMonth2);
	        	            	String day2 = mdy2.substring(3,5);
	        	            	realDay2 = Integer.parseInt(day2);
	        	            //	System.out.println(realDay2);
	        	            	String year2 = mdy2.substring(6,10);
	        	            	realYear2 = Integer.parseInt(year2);
	        	            	//System.out.println(realYear2);
	        	            	}
	            			else {
	            				Alert alert = new Alert(AlertType.CONFIRMATION);
	            				alert.setContentText("Must enter valid date");
	            				alert.show();
	            				return;
	            			}
	            		try {
		        			// Close the first window by getting the stage of that.
		        			Stage firstStage = (Stage)search.getScene().getWindow();
		        			firstStage.close();
		        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Search.fxml"));
		        		Parent root1 = (Parent)fxmlLoader.load();
		        		Stage stage = new Stage();
		        		SearchController searchController = fxmlLoader.getController();
		        	
		        		SearchController.fMonth = realMonth;
		        		SearchController.fDay = realDay;
		        		SearchController.fYear = realYear;
		        		SearchController.tMonth = realMonth2;
		        		SearchController.tDay = realDay2;
		        		SearchController.tYear = realYear2;
		        		SearchController.date = true;
		        		SearchController.userIndex = userIndex;
		        		
		        		searchController.start(stage);
		        		stage.setScene(new Scene(root1));
		        		stage.show();
		        		}
		        			catch(Exception e) {
		        			e.printStackTrace();
		        			}	
	            		}
	            	});
	        	
	            	
	        	    
	                back.setOnAction(new EventHandler<ActionEvent>() {
	                	public void handle(ActionEvent event) {
	                	mainStage.close();
	                	}
	                });
	                
	                logout.setOnAction(new EventHandler<ActionEvent>() {
	                	public void handle(ActionEvent event) {
	                	mainStage.close();
	                	try {
	            			// Close the first window by getting the stage of that.
	            			Stage firstStage = (Stage)Logout.getScene().getWindow();
	            			firstStage.close();
	            		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
	            		Parent root1 = (Parent)fxmlLoader.load();
	            		Stage stage = new Stage();
	            		
	            		Scene scene2 = new Scene(root1,400,500);
	            		stage.setScene(scene2);
	            		stage.show();
	            		}
	            		catch(Exception e) {
	            			e.printStackTrace();
	            		}
	                	
	                	}
	                });
	                
	                exit.setOnAction(new EventHandler<ActionEvent>() {
	                	public void handle(ActionEvent event) {
	                	System.exit(0);
	                	
	                	
	                	}
	                });
	                
	                Scene scene2 = new Scene(root,400,400);
	                mainStage.setTitle("Search-by:Date");
	        		mainStage.setScene(scene2);
	        		mainStage.show();
	        	
	        	}
	        });
		 
		 /*tags */
		 
		 tags.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent event) {
	        		Stage mainStage = new Stage();
	        		VBox root = new VBox(15);
	        		
	        		/*single tag*/
	        		
	        
	        			
	        		
	        	
	        		
	        		TextField singleTag = new TextField("Single Tag: ");
	        		singleTag.setDisable(true);
	        		root.getChildren().add(singleTag);
	        		
	        	
	        		ComboBox selectSingle = new ComboBox();
	        		selectSingle.setPromptText("Select Tag Type");
	        		root.getChildren().add(selectSingle);
	        		
	        		for(int i = 0; i < users.get(userIndex).getUserAlbums().size(); i++) {
	        			for( int k = 0; k < users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().size(); k++) {
	        				for( int n = 0; n < users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().size(); n++) {
	        					selectSingle.getItems().add(users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().get(n).getTagName());
	        				}
	        			}
	        		}
	        		
	        	
	        		
	        		
	        		TextField enter = new TextField();
	        		enter.setPromptText("enter tag value");
	        		enter.setFocusTraversable(false);
	        		root.getChildren().add(enter);
	        		
	        		
	        		
	        		Button search = new Button("Search");
	            	root.getChildren().add(search);
	            	
	        		
	        		
	        		search.setOnAction(new EventHandler<ActionEvent>() {
	            		public void handle(ActionEvent event) {
	            			
	            			tagTypeS1 = selectSingle.getSelectionModel().getSelectedItem().toString();
	            			tagValueS1 = enter.getText();
	            		try {
		        			// Close the first window by getting the stage of that.
		        			Stage firstStage = (Stage)search.getScene().getWindow();
		        			firstStage.close();
		        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Search.fxml"));
		        		Parent root1 = (Parent)fxmlLoader.load();
		        		Stage stage = new Stage();
		        		
		        		SearchController searchController = fxmlLoader.getController();
		        		SearchController.tagTypeS1 = tagTypeS1;
		        		SearchController.tagValueS1 = tagValueS1;
		        		SearchController.tagS = true;
		        		SearchController.userIndex = userIndex;
		        		
		        		
		        		searchController.start(stage);
		        		
		        		stage.setScene(new Scene(root1));
		        		stage.show();
		        		}
		        			catch(Exception e) {
		        			e.printStackTrace();
		        			}	
	            		}
	            	});
	        		
	        		/*multiple tags*/
	        		TextField multipleTags = new TextField("Multiple Tags: ");
	        		multipleTags.setDisable(true);
	        		root.getChildren().add(multipleTags);
	        		
	        		ComboBox selectM1 = new ComboBox();
	        		selectM1.setPromptText("Select Tag Type");
	        		root.getChildren().add(selectM1	);
	        		
	        		
	        		for(int i = 0; i < users.get(userIndex).getUserAlbums().size(); i++) {
	        			for( int k = 0; k < users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().size(); k++) {
	        				for( int n = 0; n < users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().size(); n++) {
	        					selectM1.getItems().add(users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().get(n).getTagName());
	        				}
	        			}
	        		}
	        		
	        		
	        		TextField enterM1 = new TextField();
	        		enterM1.setPromptText("enter tag value");
	        		enterM1.setFocusTraversable(false);
	        		root.getChildren().add(enterM1);
	        		
	        	
	        		
	        		ComboBox selectM2 = new ComboBox();
	        		selectM2.setPromptText("Select Tag Type");
	        		root.getChildren().add(selectM2);
	        		
	        		for(int i = 0; i < users.get(userIndex).getUserAlbums().size(); i++) {
	        			for( int k = 0; k < users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().size(); k++) {
	        				for( int n = 0; n < users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().size(); n++) {
	        					selectM2.getItems().add(users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().get(n).getTagName());
	        				}
	        			}
	        		}
	        		
	        		TextField enterM2 = new TextField();
	        		enterM2.setPromptText("enter tag value");
	        		enterM2.setFocusTraversable(false);
	        		root.getChildren().add(enterM2);
	        		
	        		
	        		
	        		Button search2 = new Button("Search");
	            	root.getChildren().add(search2);
	        		
	            	Button back = new Button("Back");
	            	root.getChildren().add(back);
	            	
	            	Button logout = new Button("Logout");
	            	root.getChildren().add(logout);
	            	
	            	Button exit = new Button("Exit Application");
	            	root.getChildren().add(exit);
	            	
	            	search2.setOnAction(new EventHandler<ActionEvent>() {
	            		public void handle(ActionEvent event) {
	            		tagTypeM1 = selectM1.getSelectionModel().getSelectedItem().toString();
	            		tagValueM1 = enterM1.getText();
	            		tagTypeM2 = selectM2.getSelectionModel().getSelectedItem().toString();
	            		tagValueM2 = enterM2.getText();
	            			try {
		        			// Close the first window by getting the stage of that.
		        			Stage firstStage = (Stage)search2.getScene().getWindow();
		        			firstStage.close();
		        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Search.fxml"));
		        		Parent root1 = (Parent)fxmlLoader.load();
		        		Stage stage = new Stage();
		        		SearchController searchController = fxmlLoader.getController();
		        		SearchController.tagTypeM1 = tagTypeM1;
		        		SearchController.tagValueM1 = tagValueM1;
		        		SearchController.tagTypeM2 = tagTypeM2;
		        		SearchController.tagValueM2 = tagValueM2;
		        		SearchController.tagM = true;
		        		SearchController.userIndex = userIndex;
		        		searchController.start(stage);
		        		
		        		stage.setScene(new Scene(root1));
		        		stage.show();
		        		}
		        			catch(Exception e) {
		        			e.printStackTrace();
		        			}	
	            		}
	            	});
	        	
	            	
	        	    
	                back.setOnAction(new EventHandler<ActionEvent>() {
	                	public void handle(ActionEvent event) {
	                	mainStage.close();
	                	}
	                });
	                
	                logout.setOnAction(new EventHandler<ActionEvent>() {
	                	public void handle(ActionEvent event) {
	                	mainStage.close();
	                	try {
	            			// Close the first window by getting the stage of that.
	            			Stage firstStage = (Stage)Logout.getScene().getWindow();
	            			firstStage.close();
	            		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
	            		Parent root1 = (Parent)fxmlLoader.load();
	            		Stage stage = new Stage();
	            		
	            		Scene scene2 = new Scene(root1,400,500);
	            		stage.setScene(scene2);
	            		stage.show();
	            		}
	            		catch(Exception e) {
	            			e.printStackTrace();
	            		}
	                	
	                	}
	                });
	                
	                exit.setOnAction(new EventHandler<ActionEvent>() {
	                	public void handle(ActionEvent event) {
	                	System.exit(0);
	                	
	                	
	                	}
	                });
	                
	                Scene scene2 = new Scene(root,800,800);
	                mainStage.setTitle("Search-by:Tags");
	        		mainStage.setScene(scene2);
	        		mainStage.show();
	        	
	        	}
	        });
	        	
	        	
	    
	
		
	}
	
	/**
	 * This method logs the user out and sends the program back to the login screen, maintaining any work that the user has done in the program.
	 * 
	 * @param event the event called that triggers this function: Clicking on the log out button in the user view.
	 * @throws IOException
	 */
	
	public void handleLogoutButtonAction(ActionEvent event) throws IOException{
		try {
			// Close the first window by getting the stage of that.
			Stage firstStage = (Stage)Logout.getScene().getWindow();
			firstStage.close();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
		Parent root1 = (Parent)fxmlLoader.load();
		Stage stage = new Stage();
		
		Scene scene2 = new Scene(root1,400,500);
		stage.setScene(scene2);
		stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method ends the program and closes it entirely. 
	 * 
	 * @param event the event called that triggers this function: Clicking on the exit button in the user view.
	 * @throws IOException
	 */
	public void handleExitButtonAction(ActionEvent event) throws IOException{
		System.exit(0);
		
	}
	
	
}

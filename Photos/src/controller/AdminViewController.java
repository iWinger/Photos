package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Serial;
import model.User;

/**
 * The controller for the admin view. Admin view is accessible 
 * when the user passes the login screen using the username 'admin'
 * 
 * @author Wingjun Chan
 * @author Jessi Medina
 *
 * Date: 11/21/2018
 */
public class AdminViewController implements Serializable{
@FXML private Button CreateUser;
@FXML private Button DeleteUser;
@FXML private Button Logout;
@FXML private Button Exit;
@FXML ListView<String> userList;
static ObservableList<String> obsAlbumList_;
ArrayList<User> users;
ObservableList<String> obsUserList = FXCollections.observableArrayList();

/**
 * starts the Admin View Controller. Loads users into list for the admin to select if they would like to make changes.
 * 
 * @param primaryStage
 * @throws IOException this exception is thrown if the program cannot find the users when attempting to read in.
 */

public void start(Stage primaryStage) throws IOException{
	
	try {
		users = Serial.readUser();
	}
	catch(ClassNotFoundException e) {
		e.printStackTrace();
	}

	for(int i = 0; i < users.size(); i++) {
	obsUserList.add(users.get(i).getUsername());
	}
	obsUserList.sort(String::compareTo);
	
	
	userList.setItems(obsUserList);
	userList
    .getSelectionModel()
    .selectedIndexProperty()
    .addListener(
       (obs, oldVal, newVal) -> 
           {
			showItemInputDialog(primaryStage);
		});
	// TODO Auto-generated method stub
	// Only happens once in this code block

}
	
	private void showItemInputDialog(Stage primaryStage) {
	
	// TODO Auto-generated method stub
	
}


	/**
	 * This method takes the value from the text input box and 
	 * creates a new user with the corresponding name.
	 * 
	 * @param event the event called that triggers this function: Clicking on the create button in the admin view.
	 * @throws IOException if readUser() fails.
	 */
	
	public void handleCreateUserButtonAction(ActionEvent event) throws IOException{
		Stage mainStage = new Stage();
		VBox root = new VBox(6);
		
		TextField newUser = new TextField();
    	root.getChildren().add(newUser);
    	newUser.setPromptText("New User: ");
    	newUser.setFocusTraversable(false);
    	//newUser.getText(); to retrieve data 
		
		Button create = new Button("Create ");
    	root.getChildren().add(create);
    	
    	Button back = new Button("Back");
    	root.getChildren().add(back);
    	
    	Button logout = new Button("Logout");
    	root.getChildren().add(logout);
    	
    	Button exit = new Button("Exit Application");
    	root.getChildren().add(exit);
    	
    	
    	Scene scene2 = new Scene(root,400,400);
        mainStage.setTitle("New User: ");
        mainStage.setScene(scene2);
        mainStage.show();
	    
        create.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		try {
        			users = Serial.readUser();
        		}
        		catch(ClassNotFoundException e) {
        			
        		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		String createUser = newUser.getText();
        		for(int i = 0; i < users.size(); i++) {
        			if(users.get(i).getUsername().equals(createUser)) {
        				Alert alert = new Alert(AlertType.CONFIRMATION);
        				alert.setContentText("Another user exists with the same name. ");
        				alert.show();
        				return;
        				
        			}
        		}
        		users.add(new User(createUser));
        		
        		try {
        			Serial.writeUser(users);
        		}
        		catch(IOException e) {
        			e.printStackTrace();
        		}
        		obsUserList.add(createUser);
        		userList.setItems(obsUserList);
        		// Check if successfully implemented here.
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
	 * This method will delete the user that is currently selected in the admin's list of users.
	 * 
	 * @param event the event called that triggers this function: Clicking on the delete button in the admin view.
	 * @throws IOException if readUser() fails
	 */
	
	public void handleDeleteUserButtonAction(ActionEvent event) throws IOException{
		String item = userList.getSelectionModel().getSelectedItem();
		//System.out.println(item);
		int selected = userList.getSelectionModel().getSelectedIndex();
		int index = 0;
		int obsindex = 0;
		try {
			users = Serial.readUser();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		for(int i = 0; i < users.size(); i++)
		{
			if(users.get(i).getUsername().equals(item)) {
				index = i;
				break;
			}
		}
		
		for(int i = 0; i < obsUserList.size(); i++)
		{
			if(obsUserList.get(i).equals(item)) {
				obsindex = i;
				break;
			}
		}
		obsUserList.remove(obsindex);
		users.remove(index);
		userList.setItems(obsUserList);
		
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
		
		
		
		/*Implement here*/
	}
	
	/**
	 * This method logs the user out and sends the program back to the login screen, maintaining any work that the user has done in the program.
	 * 
	 * @param event the event called that triggers this function: Clicking on the log out button in the admin view.
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

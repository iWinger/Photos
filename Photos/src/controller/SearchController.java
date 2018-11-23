package controller;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Serial;
import model.User;

/**
 * The controller for the search view. Accesible via clicking the tags or date button via userview, searches for photos.
 * 
 * @author Wingjun Chan
 * @author Jessi Medina
 *
 * Date: 11/21/2018
 */
public class SearchController implements Serializable{
	/* Get all the buttons*/
	@FXML private Button CreateResultAlbums;
	@FXML private Button EditTags;
	@FXML private Button DeletePhoto;
	@FXML private Button AddPhoto;
	@FXML private Button CopyMovePhoto;
	@FXML private Button EditCaption;
	@FXML private Button Slideshow;
	@FXML private Button Logout;
	@FXML private Button Exit;
	@FXML private Button Back;
	public static int fMonth;
	public static int fDay;
	public static int fYear;
	public static int tMonth;
	public static int tDay;
	public static int tYear;
	static boolean date = false;
	static boolean tagS = false;
	static boolean tagM = false;
	ArrayList<User> users;
	public static int userIndex;
	@FXML private ListView<ImageView> PhotoList;
	ObservableList<ImageView> obsPhotoList = FXCollections.observableArrayList();
	static String tagTypeS1;
	static String tagValueS1;
	static String tagTypeM1;
	static String tagValueM1;
	static String tagTypeM2;
	static String tagValueM2;
	LocalDate fDate;
	LocalDate startDate;
	LocalDate endDate;
	Album album = new Album("search results");
	/*Write handler methods here*/
	/**
	 * starts the SearchController. loads the photos results from search.
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException{
		try {
			users = Serial.readUser();
		}
		catch(IOException e) {
			
		}
		//System.out.println(fMonth);
		if(date == true) {
		fDate = LocalDate.of(fYear, fMonth, fDay);
		startDate = LocalDate.of(fYear, fMonth, fDay);
        endDate = LocalDate.of(tYear, tMonth, tDay);
		}
	
	
		
	
		
		for( int i = 0; i <users.get(userIndex).getUserAlbums().size(); i++) {	
			for(int k = 0; k < users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().size(); k++) {
			
				if(date) {
					String path = null;
					
					Date thisDate = users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getCurrentDate();
					LocalDate nowDate = thisDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if(nowDate.isAfter(startDate) && nowDate.isBefore(endDate)) {
							
					path = users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getFileName();
					String completepath = path;
					
				
					if(users.get(userIndex).getUsername().equals("stock"))
					completepath = "image/" + path;
					
					
					
					//System.out.println(completepath);
					Image photo = new Image(completepath, 400, 100, false, false);
					ImageView photoView = new ImageView(photo);
					album.addPhoto(users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k));
					album.addNumPhotos();
					PhotoList.getItems().add(photoView);
					
					obsPhotoList.add(photoView);
					PhotoList.setItems(obsPhotoList);
					
					}
						
				}
				
				
				else if(tagS) {
					
	        				
	        				String path = null;
	        				boolean valid = false;
	        				String tagCheck = "(" + tagTypeS1 + ", " + tagValueS1 + ")";
	        			
	    							
	    					path = users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getFileName();
	    					String completepath = path;
	    					
	    				
	    					if(users.get(userIndex).getUsername().equals("stock"))
	    					completepath = "image/" + path;
	    					
	    					for( int n = 0; n < users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().size(); n++) {
	        					if(users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().get(n).toString().equals(tagCheck)) {
	        						valid = true;
	        					
	        					}
	        					
	    					}
	    					
	    					//System.out.println(completepath);
	    					if(valid) {
	    					Image photo = new Image(completepath, 400, 100, false, false);
	    					ImageView photoView = new ImageView(photo);
	    					album.addPhoto(users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k));
	    					album.addNumPhotos();
	    					PhotoList.getItems().add(photoView);
	    					
	    					obsPhotoList.add(photoView);
	    					}
	    					PhotoList.setItems(obsPhotoList);
	       
					
				}
				
				else if(tagM) {
					String path = null;
    				boolean valid = false;
    				boolean valid2 = false;
    				String tagCheck = "(" + tagTypeM1 + ", " + tagValueM1 + ")";
    				String tagCheck2 = "(" + tagTypeM2+ ", " + tagValueM2 + ")";
    			
							
					path = users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getFileName();
					String completepath = path;
					
				
					if(users.get(userIndex).getUsername().equals("stock"))
					completepath = "image/" + path;
					
					for( int n = 0; n < users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().size(); n++) {
    					if(users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().get(n).toString().equals(tagCheck)) {
    						valid = true;
    					
    					}
    					if(users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k).getPhotoTags().get(n).toString().equals(tagCheck2))
    						valid2 = true;
					}
					
					//System.out.println(completepath);
					if(valid && valid2) {
					Image photo = new Image(completepath, 400, 100, false, false);
					ImageView photoView = new ImageView(photo);
					album.addPhoto(users.get(userIndex).getUserAlbums().get(i).getAlbumPhotos().get(k));
					album.addNumPhotos();
					PhotoList.getItems().add(photoView);
					
					obsPhotoList.add(photoView);
					}
					PhotoList.setItems(obsPhotoList);
				}
				
			}
		}
		
		
	}
	
	
	/**
	 * Creates album from search results
	 * @param event
	 * @throws IOException
	 */
	
	public void handleCreateResultAlbumsButtonAction(ActionEvent event) throws IOException{
		album.setAlbumDetail();
		users.get(userIndex).getUserAlbums().add(album);
		try {
			Serial.writeUser(users);
		}
		catch(IOException e) {
			
		}
		
		try {
			// Close the first window by getting the stage of that.
			Stage firstStage = (Stage)Back.getScene().getWindow();
			firstStage.close();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
		Parent root1 = (Parent)fxmlLoader.load();
		Stage stage = new Stage();
		UserController userController = fxmlLoader.getController();
		userController.start(stage);
		stage.setScene(new Scene(root1));
		stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
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
	
	public void handleExitButtonAction(ActionEvent event) throws IOException{
		System.exit(0);
	}
	
	/**
	 * This method will send the user back to the previous page they were on.
	 * 
	 * @param event the event called that triggers this function: Clicking on the back button in the openAlbums view.
	 * @throws IOException
	 */
	
	public void handleBackButtonAction(ActionEvent event) throws IOException{
		try {
			// Close the first window by getting the stage of that.
			Stage firstStage = (Stage)Back.getScene().getWindow();
			firstStage.close();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
		Parent root1 = (Parent)fxmlLoader.load();
		Stage stage = new Stage();
		UserController userController = fxmlLoader.getController();
		userController.start(stage);
		stage.setScene(new Scene(root1));
		stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}

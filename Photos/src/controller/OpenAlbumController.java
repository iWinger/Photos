package controller;

import java.io.File;
import java.io.FileNotFoundException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Serial;
import model.Tag;
import model.User;

/**
 * The controller for the open album view. Accesible via clicking the open album button in the
 * user view. This controller offers the user the ability to change tag and caption information about each photo in the album, along with adding and deleting photos in the album and 
 * also moving/copying photos to other albums.
 * 
 * @author Wingjun Chan
 * @author Jessi Medina
 *
 * Date: 11/21/2018
 */
public class OpenAlbumController implements Serializable{
	/* Get all the buttons*/
	@FXML private Button DisplaySelected;
	@FXML private Button EditTags;
	@FXML private Button DeletePhoto;
	@FXML private Button AddPhoto;
	@FXML private Button CopyMovePhoto;
	@FXML private Button EditCaption;
	@FXML private Button Slideshow;
	@FXML private Button Logout;
	@FXML private Button Exit;
	@FXML private Button Back;
	ArrayList<User> users;
	public static String albumDetail;
	public static int userIndex;
	public static int albumIndex;
	public static String saveUser;
	@FXML private ListView<ImageView> photoList;
	static ObservableList<String> obsAlbumList_;
	ObservableList<ImageView> obsPhotoList = FXCollections.observableArrayList();
	int slideshowcount = 0;

	
	/**
	 * starts the OpenAlbumController. loads the photos that are currently in the selected album.
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		try{
			users = Serial.readUser();
		}
		catch(ClassNotFoundException e) {
			
		}
		
		//System.out.println(saveUser);
	
		for ( int i = 0; i < users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().size(); i++) {
		
			String path = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(i).getFileName();
			String completepath = path;
			
		
			if(users.get(userIndex).getUsername().equals("stock"))
			completepath = "image/" + path;
			
			//System.out.println(completepath);
			Image photo = new Image(completepath, 400, 100, false, false);
		 
			
			String photoCaption = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(i).getCaption();
			ArrayList<Tag> photoTag = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(i).getPhotoTags();
			
			ImageView photoView = new ImageView(photo);
			//System.out.println(completepath);
			photoList.getItems().add(photoView);
			
			obsPhotoList.add(photoView);
			//System.out.println(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(i).getCaption());
		}
		
		photoList.setItems(obsPhotoList);
	
		// TODO Auto-generated method stub
		// Only happens once in this code block
		
	}
	
	
	/*Write handler methods here*/
	/**
	 * This will display the currently selected photo and any relevant information about it in another window.
	 * 
	 * @param event the event called that triggers this function: Clicking on the Display Selected button in the openAlbum view.
	 * @throws IOException
	 */
	
	public void handleDisplaySelectedButtonAction(ActionEvent event) throws IOException{
		try {
			users = Serial.readUser();
		}
		catch(ClassNotFoundException e) {
			
		}
		
		
		Image item = photoList.getSelectionModel().getSelectedItem().getImage();
		int itemIndex = photoList.getSelectionModel().getSelectedIndex();
		String itemCaption = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(itemIndex).getCaption();
		ArrayList<Tag> itemTag = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(itemIndex).getPhotoTags();
		
		
		Stage mainStage = new Stage();
		VBox root = new VBox(6);
		
		StackPane sp = new StackPane();
		//Image displayedImage = new Image("image/Koi.png", true);
		ImageView imgView = new ImageView(item);
		TextField caption = new TextField("Caption: " + itemCaption);
		String fullTag = "Tags: ";
		for ( int i = 0; i < itemTag.size(); i++) {
			fullTag += itemTag.get(i);
		}
		
		TextField tag = new TextField(fullTag);
		caption.setDisable(true);
		tag.setDisable(true);
		root.getChildren().add(imgView);
		root.getChildren().add(caption);
		root.getChildren().add(tag);
		
		
		Button create = new Button("Create ");
    	root.getChildren().add(create);
    	
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
        mainStage.setTitle("Displayed Photo");
		mainStage.setScene(scene2);
		mainStage.show();
	}
	
	public void handleEditTagsButtonAction(ActionEvent event) throws IOException{
		if(photoList.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Must select a photo before editing the tags");
			alert.show();
			return;
		}
		
		try {
			// Close the first window by getting the stage of that.
			Stage firstStage = (Stage)EditTags.getScene().getWindow();
			firstStage.close();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EditTags.fxml"));
		Parent root1 = (Parent)fxmlLoader.load();
		
		Stage stage = new Stage();
		EditTagsController editTagsController = fxmlLoader.getController();

		int photoIndex = photoList.getSelectionModel().getSelectedIndex();
		
		EditTagsController.userIndex = userIndex;
		EditTagsController.albumIndex = albumIndex;
		EditTagsController.photoIndex = photoIndex;
		editTagsController.start(stage);
		
		stage.setScene(new Scene(root1));
		stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This will delete the currently selected photo in the list
	 * 
	 * @param event the event called that triggers this function: Clicking on the Delete button in the openAlbum view.
	 * @throws IOException
	 */
	
	public void handleDeletePhotoButtonAction(ActionEvent event) throws IOException{
		if(users.get(userIndex).getUsername().equals("stock"))
			return;
		
		String deletePhoto = photoList.getSelectionModel().getSelectedItem().getImage().impl_getUrl();
		int deleteIt = photoList.getSelectionModel().getSelectedIndex();
		//System.out.println(deletePhoto);
		int delete = -1;
		
		for(int i = 0; i < users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().size(); i++) {
			if(deletePhoto.equals(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(i))) {
				delete = i;
				break;
			}
		}
		
		if(delete != -1) {
		users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().remove(delete);
		users.get(userIndex).getUserAlbums().get(albumIndex).subtractNumPhotos();
		}
		try {
			Serial.writeUser(users);
		}
		catch(IOException e) {
			
		}
		obsPhotoList.remove(deleteIt);
		photoList.setItems(obsPhotoList);
		
		
		//Photo photo = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(deleteIndex);
		
		
		
	}
	
	/**
	 * This will open up the interface on the users computer that allows them to search for a photo to upload fromt their files
	 * 
	 * @param event the event called that triggers this function: Clicking on the Add Photo button in the openAlbum view.
	 * @throws IOException
	 */
	public void handleAddPhotoButtonAction(ActionEvent event) throws IOException{
		if(users.get(userIndex).getUsername().equals("stock"))
			return;
		
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File selectedFile = fileChooser.showOpenDialog(stage);
		if(selectedFile != null) {
			//System.out.println(selectedFile.toString());
			String filepath = selectedFile.toURI().toString();
			//System.out.println(filepath);
			Image fileImage = new Image(filepath, true);	
			ImageView photoContainer = new ImageView(fileImage);
			Photo filePhoto = new Photo(filepath);
			try {
				users = Serial.readUser();
				
			}
			catch(ClassNotFoundException e) {
				
			}
			
			users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().add(filePhoto);
			users.get(userIndex).getUserAlbums().get(albumIndex).addNumPhotos();
			try {
				Serial.writeUser(users);
				
			}
			catch(IOException e) {
				
			}
			obsPhotoList.add(photoContainer);
			photoList.setItems(obsPhotoList);
			
		}
		
	}
	
	/**
	 * This will direct the user to the move/copy page where they can duplicate photos to other albums, or move them from one album to another.
	 * 
	 * @param event the event called that triggers this function: Clicking on the Move/Copy button in the openAlbum view.
	 * @throws IOException
	 */
	
	public void handleCopyMovePhotoButtonAction(ActionEvent event) throws IOException{
		Stage mainStage = new Stage();
		VBox root = new VBox(6);
		
		TextField selectedAlbum = new TextField();
		root.getChildren().add(0,selectedAlbum);
		selectedAlbum.setPromptText("Selected Album Destination:");
		selectedAlbum.setFocusTraversable(false);
		selectedAlbum.setDisable(true);
		
		try {
    		users = Serial.readUser();
    	}
    	catch(ClassNotFoundException e) {
    		
    	}
    	
    	catch(IOException e) {
    		
    	}
    		ComboBox<String> dropdown = new ComboBox<String>();
    		dropdown.setItems(obsAlbumList_);
    		dropdown.setValue("Album");
    		root.getChildren().add(1,dropdown);
    		
    		
    		
    		try {
    			Serial.writeUser(users);
    		}
    		
    		catch(IOException e) {
    			
    		}
		
        
		Button copy = new Button("Copy ");
    	root.getChildren().add(2,copy);
    	
    	 copy.setOnAction(new EventHandler<ActionEvent>() {
         	public void handle(ActionEvent event) {
         	
         			int itemIndex = photoList.getSelectionModel().getSelectedIndex();
         			Photo photo = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(itemIndex);
         			//System.out.println(photo.getCaption());
         			String selectedAlbum = dropdown.getValue();
         			int album = 0;
         			for(int i = 0; i < users.get(userIndex).getUserAlbums().size(); i++) {
         				if(selectedAlbum.equals(users.get(userIndex).getUserAlbums().get(i).getAlbumDetail())) {	
         					album = i;
         					break;
         				}		
         			}         			
         			users.get(userIndex).getUserAlbums().get(album).addPhoto(photo);
         			users.get(userIndex).getUserAlbums().get(album).addNumPhotos();
         			//System.out.println(users.get(userIndex).getUserAlbums().get(album).getAlbumPhotos().size());
         			//System.out.println(album);
         			//System.out.println(photo.getCaption());
         		//	System.out.println(selectedAlbum);
         			mainStage.close();
         		try {
         			Serial.writeUser(users);
         		}
         		catch(IOException e) {
         			
         		}
         			
         	}
         });
         
    	
    	
    	Button move = new Button("Move ");
    	root.getChildren().add(3,move);
    	move.setOnAction(new EventHandler<ActionEvent>() {
         	public void handle(ActionEvent event) {
         		
         			int itemIndex = photoList.getSelectionModel().getSelectedIndex();
         			Photo photo = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(itemIndex);
         			//System.out.println(photo.getCaption());
         			String selectedAlbum = dropdown.getValue();
         			int album = 0;
         			for(int i = 0; i < users.get(userIndex).getUserAlbums().size(); i++) {
         				if(selectedAlbum.equals(users.get(userIndex).getUserAlbums().get(i).getAlbumDetail())) {	
         					album = i;
         					break;
         				}		
         			}
         			users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().remove(itemIndex);
         			users.get(userIndex).getUserAlbums().get(album).addPhoto(photo);
         			users.get(userIndex).getUserAlbums().get(album).addNumPhotos();
         			//System.out.println(users.get(userIndex).getUserAlbums().get(album).getAlbumPhotos().size());
         			//System.out.println(album);
         			//System.out.println(photo.getCaption());
         		//	System.out.println(selectedAlbum);
         			mainStage.close();
         			
         		try {
         			Serial.writeUser(users);
         		}
         		catch(IOException e) {
         			
         		}
         			
         	}
         });
    	
    	
 	
    	Button back = new Button("Back");
    	root.getChildren().add(4,back);
    	
    	Button logout = new Button("Logout");
    	root.getChildren().add(5,logout);
    	
    	Button exit = new Button("Exit Application");
    	root.getChildren().add(6,exit);
    	
    
	    
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
        mainStage.setTitle("Copy Move Photo");
		mainStage.setScene(scene2);
		mainStage.show();
		
		
	}
	/**
	 * This will direct the user to the Edit caption page where they can delete and modify captions.
	 * 
	 * @param event the event called that triggers this function: Clicking on the Edit Caption button in the openAlbum view.
	 * @throws IOException
	 */
	public void handleEditCaptionButtonAction(ActionEvent event) throws IOException{
		Stage mainStage = new Stage();
		VBox root = new VBox(6);
		
		TextField newCaption = new TextField();
		root.getChildren().add(newCaption);
		newCaption.setPromptText("New Caption:");
		newCaption.setFocusTraversable(false);
		
		
		
		
		
		Button accept = new Button("Accept ");
    	root.getChildren().add(accept);
    	
    	accept.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        	try {
        		users = Serial.readUser();
        	}
        	catch(ClassNotFoundException e) {
        		
        	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	String newText = newCaption.getText();
        	int itemIndex = photoList.getSelectionModel().getSelectedIndex();
        	users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(itemIndex).setCaption(newText);
        	
        	try {
        		Serial.writeUser(users);
        	}
        	catch(IOException e) {
        		
        	}
        	
        	mainStage.close();
        	
        	}
        });	
    	
    	
    	
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
        mainStage.setTitle("Edit Caption");
		mainStage.setScene(scene2);
		mainStage.show();
		
		
		
	}
	/**
	 * This method will display a slideshow of all photos currently in the album. Offers functionality for user to select to go to the next photo or the previous.
	 * 
	 * @param event the event called that triggers this function: Clicking on the slideshow button in the openAlbum view.
	 * @throws IOException
	 */
	
	public void handleSlideshowButtonAction(ActionEvent event) throws IOException{
		Stage mainStage = new Stage();
		VBox root = new VBox(6);
		StackPane sp = new StackPane();
		TextField currentPhoto = new TextField("Current Photo: ");
		currentPhoto.setDisable(true);
		root.getChildren().add(currentPhoto);
		
	/*	for ( int i = 0; i < users.get(userIndex).getUserAlbums().get(albumIndex).getNumPhotos(); i++) {
			String completepath = "image/";
			completepath += users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(i).getFileName();
		}
		*/
		ImageView firstImgView = new ImageView();
		int getPhotoIndex = 0;
		if(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().size() > 0) {
			String firstpath = "";
			if(users.get(userIndex).getUsername().equals("stock"))
			firstpath = "image/";
			
			firstpath += users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(0).getFileName();
			//System.out.println(firstpath);
			Image photo = new Image(firstpath, 100, 100, false, false);
			firstImgView.setImage(photo);
			
		root.getChildren().add(firstImgView);
		
		}
		
		
		Button previous = new Button("Previous ");
    	root.getChildren().add(previous);
    	
    	previous.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        			if(slideshowcount >= 0)
        			slideshowcount--;
        			
        			if(slideshowcount < 0) {
        				Alert alert = new Alert(AlertType.ERROR);
        				alert.setContentText("No more photos to go back to.");
        				alert.show();
        				slideshowcount++;
        				return;
        			}
        			root.getChildren().remove(firstImgView);
        			
        			String completepathprev = "";
        			
        			if(users.get(userIndex).getUsername().equals("stock"))
        			completepathprev = "image/";
        			completepathprev += users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(slideshowcount).getFileName();
        			Image photo = new Image(completepathprev, 100, 100, false, false);
        			ImageView firstImgView = new ImageView(photo);
        			root.getChildren().set(0, firstImgView);
        			
        		
        	}
        });
    	
    	
    	
    	Button next = new Button("Next ");
    	root.getChildren().add(next);
    	
    	next.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		if(slideshowcount < users.get(userIndex).getUserAlbums().get(albumIndex).getNumPhotos())
        			slideshowcount++;
        		
        		if(slideshowcount >= users.get(userIndex).getUserAlbums().get(albumIndex).getNumPhotos()) {
        			Alert alert = new Alert(AlertType.ERROR);
    				alert.setContentText("No more photos to go forward to.");
    				alert.show();
    				slideshowcount--;
    				return;
        		}
        		root.getChildren().remove(firstImgView);
        		String completepathnext = "";
        		if(users.get(userIndex).getUsername().equals("stock"))
        			completepathnext = "image/";
        		
        			completepathnext += users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(slideshowcount).getFileName();
        			Image photo = new Image(completepathnext, 100, 100, false, false);
        			ImageView firstImgView = new ImageView(photo);
        			root.getChildren().set(0, firstImgView);
        	}
        });
    	
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
        
        Scene scene2 = new Scene(root,400,400);
        mainStage.setTitle("Slideshow");
        mainStage.setScene(scene2);
		mainStage.show();
	}
	
	/**
	 * This method logs the user out and sends the program back to the login screen, maintaining any work that the user has done in the program.
	 * 
	 * @param event the event called that triggers this function: Clicking on the log out button in the openAlbums view.
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
	 * @param event the event called that triggers this function: Clicking on the exit button in the openAlbum view.
	 * @throws IOException
	 */
	
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
		UserController userController = fxmlLoader.getController();
		Stage stage = new Stage();
		userController.start(stage);
		
		stage.setScene(new Scene(root1));
		stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}

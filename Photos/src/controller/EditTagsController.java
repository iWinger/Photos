package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Serial;
import model.User;
import model.Tag;

/**
 * The controller for the edit tags view. Accesible via clicking the edit button in the
 * user view, performs changes on tags.
 * 
 * @author Wingjun Chan
 * @author Jessi Medina
 *
 * Date: 11/21/2018
 */

public class EditTagsController implements Serializable{
@FXML private Button Add;	
@FXML private Button Back;
@FXML private Button Logout;
@FXML private Button Exit;
@FXML private Text Tags;
@FXML private Text SelectTagType;
@FXML private Text EnterTagType;
@FXML private Text ValueTag;
@FXML private Text OR;
@FXML private TextField NewTagType;
@FXML private TextField NewTagValue;
@FXML private ComboBox<String> TagListCB;
/* Need users */
ArrayList<User> users;
public static int userIndex;
public static int albumIndex;
public static int photoIndex;
@FXML private ListView<String> tagList;
private ArrayList<Tag> newTagList;
ObservableList<String> obsTagList = FXCollections.observableArrayList();
ObservableList<String> obsNameList = FXCollections.observableArrayList();

/**
 * starts the EditTagsController. loads tags of specified photo into list for the user to select if they would like to make changes.
 * 
 * @param primaryStage
 * @throws IOException
 */

public void start(Stage primaryStage)throws IOException{
	try{
		users = Serial.readUser();
	}
	catch(ClassNotFoundException e) {
		
	}
	
	for(int i = 0; i < users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().size(); i++) {
		obsTagList.add(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(i).toString());
	}
	
	for(int i = 0; i < users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().size(); i++) {
		int comma = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(i).toString().indexOf(",");
		obsNameList.add(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(i).toString().substring(1, comma));
		//System.out.println(obsNameList.get(i));
	}
	
	////////////THIS IS ALL TEST SHIT. WILL REMOVE.
	//System.out.println(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(0).toString());
	ArrayList<Tag> testTagArray = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags();
//	testTagArray.get(0).setTagName("Bruce");
//	testTagArray.get(0).setTagValue("Hong Kong");
	//System.out.println(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(0));
	//users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).setPhotoTags(testTagArray);
	
//	if (users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(0).toString().equals("(Bruce, Hong Kong)"))
//	{
		/*ArrayList<Tag> testTag = users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags();
		System.out.println(testTag.get(0)); //prints the tag at specified location
		Tag tempTag = new Tag("Location", "Earth");

		testTag.get(0).setTagName("Test");
		testTag.get(0).setTagValue("Test2");
		
		System.out.println(testTag.get(0));
		testTag.add(tempTag);
		System.out.println(testTag.get(2));
		testTag.get(2).setTagValue("Venus");
		System.out.println(testTag.get(2));*/
		
	//	for(int i = 0; i < users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().size(); i++) {
		//	System.out.println(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(i).toString());
		//}
		
		//users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).setPhotoTags(testTag);
		
		/*try {
			Serial.writeUser(users);
		}
		catch(IOException e) {
			e.printStackTrace();
		}*/
		
		//System.out.println("Success");
		
//	}
	//The toString method returns the PARENTHESIZED tag. So i will have to parse substring(1, indexOf(,)) to get the first word for the drop down menu
	
	
	tagList.setItems(obsTagList);
	TagListCB.setItems(obsNameList);
	
}
	
/**
 * This method will add the new tag to the list, given the value of the tag the user entered.
 * 
 * @param event the event called that triggers this function: Clicking on the add button in the edit tags view.
 * @throws IOException
 */

	public void handleAddButtonAction(ActionEvent event) throws IOException{
		
			/*System.out.println(NewTagType.getText().isEmpty());//returns value of the type box
			System.out.println(NewTagValue.getText().isEmpty());//returns value of the value box
			System.out.println(TagListCB.getSelectionModel().getSelectedItem());//returns the value that is selected in the tag box
			*/
		
			
			if(TagListCB.getSelectionModel().getSelectedItem() == null && (!NewTagType.getText().isEmpty()) && (!NewTagValue.getText().isEmpty())) {
				
				Tag newTag = new Tag(NewTagType.getText(), NewTagValue.getText());
				for(int i = 0; i < users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().size(); i++) {
				if(newTag.toString().equals(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(i).toString())) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setContentText("Cannot have the same tag type and value for the same photo.");
					alert.show();
					return;
				}
					
				}
				users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).addTag(newTag);
				obsTagList.add(newTag.toString());
				obsNameList.add(newTag.getTagName());
			}
			
			
			
			else if(NewTagType.getText().isEmpty() && TagListCB.getSelectionModel().getSelectedItem() != null && (!NewTagValue.getText().isEmpty())) {
				Tag newTag = new Tag(TagListCB.getSelectionModel().getSelectedItem(), NewTagValue.getText());
				for(int i = 0; i < users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().size(); i++) {
					if(newTag.toString().equals(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(i).toString())) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setContentText("Cannot have the same tag type and value for the same photo.");
						alert.show();
						return;
					}
				}
				users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).addTag(newTag);
				obsTagList.add(newTag.toString());
				obsNameList.add(newTag.getTagName());
			}
			
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Not a valid option, please selected add a completely new tag type and value, or combo box's tag type and value.");
				alert.show();
				return;
			}
			
			try {
				Serial.writeUser(users);
			}
			catch(IOException e) {
				
			}
			
			tagList.setItems(obsTagList);
			TagListCB.setItems(obsNameList);
	}

	/**
	 * This method will take a full tag "(X, Y)" and return just the first half of the tag, without commas or parentheses. "X"
	 * 
	 * @param tagName value of the tag that is being assessed
	 * @return substring of first portion of tag. 
	 */
	
	public String tagType(String tagName) {
		int index = 0;
		for ( int i = 0; i < tagName.length(); i++) {
			if(tagName.charAt(i) == ',')
				index = i;
		}
		
		return tagName.substring(1, index);
	}
	
	
	
	/**
	 * This method will delete the currently selected tag key:value pair from the list of tags.
	 * 
	 * @param event the event called that triggers this function: Clicking on the delete button in the user view.
	 * @throws IOException
	 */
	public void handleDeleteButtonAction(ActionEvent event) throws IOException{
		//System.out.println(NewTagType.getText());//returns value of the type box
		//System.out.println(NewTagValue.getText());//returns value of the value box
		//System.out.println(TagListCB.getSelectionModel().getSelectedItem());//returns the value that is selected in the tag box
	
		String tag = tagList.getSelectionModel().getSelectedItem();
		//System.out.println(tag);
		
		for(int i = 0; i < users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().size(); i++) {
			if(tag.equals(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(i).toString())) {
				users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().remove(i);
				obsTagList.remove(tag);
				//System.out.println(users.get(userIndex).getUserAlbums().get(albumIndex).getAlbumPhotos().get(photoIndex).getPhotoTags().get(i).getTagName());
				String tagIt = tagType(tag);
				obsNameList.remove(tagIt);
				
			}
				
		}
		
		
		try {
			Serial.writeUser(users);
		}
		catch(IOException e) {
			
		}
		tagList.setItems(obsTagList);
		TagListCB.setItems(obsNameList);
		
		
		
		//if(NewTagType.getText().equals("")) {
			//System.out.println("null type");
		//}
		
}

	/**
	 * This method will send the user back to the previous page they were on.
	 * 
	 * @param event the event called that triggers this function: Clicking on the back button in the edit tags view.
	 * @throws IOException
	 */
	
	public void handleBackButtonAction(ActionEvent event) throws IOException{
		try {
			// Close the first window by getting the stage of that.
			Stage firstStage = (Stage)Back.getScene().getWindow();
			firstStage.close();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/OpenAlbum.fxml"));
		Parent root1 = (Parent)fxmlLoader.load();
		OpenAlbumController openAlbumController = fxmlLoader.getController();
		Stage stage = new Stage();
		openAlbumController.start(stage);

		
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
	 * @param event the event called that triggers this function: Clicking on the log out button in the edit tags view.
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
	 * @param event the event called that triggers this function: Clicking on the exit button in the edit tags view.
	 * @throws IOException
	 */
	public void handleExitButtonAction(ActionEvent event) throws IOException{
		System.exit(0);
	}
	
	








}

package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Serial;
import model.Tag;
import model.User;
import controller.UserController;

/**
 * The controller for the login view. Accesible via clicking the edit button in the
 * user view, performs changes on tags.
 * 
 * @author Wingjun Chan
 * @author Jessi Medina
 *
 * Date: 11/21/2018
 */
public class LoginController implements Serializable{

	private static final long serialVersionUID = 5894327892519229366L;
	@FXML Button Login;
	@FXML private Button Exit;
	@FXML private TextField User;
	public ArrayList<User> users;
    boolean isUser = false;

    /**
     * starts the LoginController.
     * 
     * @param primaryStage
     * @throws IOException
     */
	public void start(Stage primaryStage) throws FileNotFoundException {
		
		// TODO Auto-generated method stub
		// Only happens once in this code block
		users = new ArrayList<User>();
	}
	
	
	public void handleLoginButtonAction(ActionEvent event) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("src/dat/info.dat"));
		// Save the users 
		
		
		try {
			if(br.readLine() != null)
			users = Serial.readUser();		
			
		}
		
		
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String username = User.getText(); // get the username
		User admin = new User("admin");
		users.add(admin);
		
		User newUser = new User(username);
		boolean validLogin = false;
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(username)) {
				validLogin = true;
			}
				
				//this is ok
		}
		
			if(!validLogin) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("No user exists");
				alert.show();
				return;
			}
		
		
		boolean stock = false;
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals("stock"))
				stock = true;
		//	System.out.println(users.get(i).getUsername());
		}

		if(!stock) {
			stockIt();
			
		}
		
		
		try {
			Serial.writeUser(users);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		if(username.equals("admin")) {
			try {
				// Close the first window by getting the stage of that.
				Stage firstStage = (Stage)Login.getScene().getWindow();
				firstStage.close();
			FXMLLoader fxmlLoaderAdmin = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
			Parent rootAdmin = (Parent)fxmlLoaderAdmin.load();
			Stage stageAdmin = new Stage();
			AdminViewController adminViewController = fxmlLoaderAdmin.getController();
			adminViewController.start(stageAdmin);
			stageAdmin.setTitle("Admin Viewing Page");
			stageAdmin.setScene(new Scene(rootAdmin));
			stageAdmin.show();
			return;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
			
		
		
		
		
		/*--------------------------------------------------------------------------------*/
		
		try {
			// Close the first window by getting the stage of that.
			Stage firstStage = (Stage)Login.getScene().getWindow();
			firstStage.close();
			
			
			Stage stageUser = new Stage();
		
		FXMLLoader fxmlLoaderUser = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
		Parent rootUser = (Parent)fxmlLoaderUser.load();
		
		UserController userController = fxmlLoaderUser.getController();
		UserController.saveUser = User.getText();
		userController.start(stageUser);
		
		
		stageUser.setScene(new Scene(rootUser));
		stageUser.setTitle("UserView");
		stageUser.show();
	
		
	
		
		
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * This method ends the program and closes it entirely. 
	 * 
	 * @param event the event called that triggers this function: Clicking on the exit button in the login view.
	 * @throws IOException
	 */
	public void handleExitButtonAction(ActionEvent event) throws IOException{
		System.exit(0);
	}
	/**
	 * This method will generate the stock user and album populated with photos, captions and tags if the user 'stock' does not already exist (i.e. on first startup, or user has been deleted)
	 */
	public void stockIt() {
	
		
		User stockUser = new User("stock");
		Album stockAlbum = new Album("stock");
	
		Photo BruceLee = new Photo("BruceLee.jpg");
		BruceLee.setName("Bruce Lee");
		BruceLee.setCaption("The greatest fighter ever");
		ArrayList<Tag> BruceLeeTags = new ArrayList<Tag>();
		BruceLeeTags.add(new Tag("person", "BruceLee"));
		BruceLeeTags.add(new Tag("trait", "Strong"));
		BruceLee.setPhotoTags(BruceLeeTags);
		stockAlbum.addPhoto(BruceLee);
		
		
		Photo China = new Photo("China.jpg");
		China.setName("China");
		China.setCaption("Where all the good food is");
		ArrayList<Tag> ChinaTags = new ArrayList<Tag>();
		ChinaTags.add(new Tag("location", "China"));
		ChinaTags.add(new Tag("description", "Majestic"));
		China.setPhotoTags(ChinaTags);
		stockAlbum.addPhoto(China);
		
		Photo Deer = new Photo("Deer.jpg");
		Deer.setName("Deer");
		Deer.setCaption("Magical creature");
		ArrayList<Tag> DeerTags = new ArrayList<Tag>();
		DeerTags.add(new Tag("location", "Grass Field"));
		DeerTags.add(new Tag("season", "Spring"));
		Deer.setPhotoTags(DeerTags);
		stockAlbum.addPhoto(Deer);
		
		Photo Koi = new Photo("Koi.png");
		Koi.setName("Koi");
		Koi.setCaption("My favorite pet");
		ArrayList<Tag> KoiTags = new ArrayList<Tag>();
		KoiTags.add(new Tag("color", "Red"));
		KoiTags.add(new Tag("animal", "Fish"));
		Koi.setPhotoTags(KoiTags);
		stockAlbum.addPhoto(Koi);
		
		Photo Penguin = new Photo("Penguin.jpg");
		Penguin.setName("Penguin");
		Penguin.setCaption("Cute animal that sadly can't fly");
		ArrayList<Tag> PenguinTags = new ArrayList<Tag>();
		PenguinTags.add(new Tag("animal", "Penguin"));
		PenguinTags.add(new Tag("location", "Antarctica"));
		Penguin.setPhotoTags(PenguinTags);
		stockAlbum.addPhoto(Penguin);
		
		Photo PolarBear = new Photo("PolarBear.png");
		PolarBear.setName("Polar Bear");
		PolarBear.setCaption("Grows up into a beast!");
		ArrayList<Tag> PolarBearTags = new ArrayList<Tag>();
		PolarBearTags.add(new Tag("location", "North Pole"));
		PolarBearTags.add(new Tag("animal", "Polar Bear"));
		PolarBear.setPhotoTags(PolarBearTags);
		stockAlbum.addPhoto(PolarBear);
		
		Photo Sakurai = new Photo("Sakurai.jpg");
		Sakurai.setName("Sakurai");
		Sakurai.setCaption("Beautiful trees in Japan");
		ArrayList<Tag> SakuraiTags = new ArrayList<Tag>();
		SakuraiTags.add(new Tag("location", "Japan"));
		Sakurai.setPhotoTags(SakuraiTags);
		stockAlbum.addPhoto(Sakurai);
	
	
		stockAlbum.setNumPhotos();
		stockAlbum.setAlbumDetail();
		stockUser.addAlbum(stockAlbum);
		users.add(stockUser);
		
		//System.out.println("stock added");
	}
}

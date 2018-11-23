package application;
	
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * This photos program creates an interface in which users
 * can add photos, edit tags/captions, delete photos, etc.
 * Admins can create and delete users.
 * 
 * @author Wingjun Chan
 * @author Jessi Medina
 *
 * Date: 11/21/2018
 */

public class Photos extends Application {
	
	/**
	 * starts the program by loading the FXML and directing the user directly to the Login page. 
	 * 
	 * @param primaryStage The starting stage of the application
	 */
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();   
			// Change to Login as that is the first page. This is just testing.
			loader.setLocation(
					getClass().getResource("/view/Login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			LoginController log = loader.getController();
		
			
			
			Scene scene = new Scene(root,400,500);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			log.start(primaryStage);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * The main driver method of the program.
	 * 
	 * @param args args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

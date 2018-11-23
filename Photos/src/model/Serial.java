package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class Serial implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1820849698274855493L;
	
	public static final String storeDir = "dat";
	public static final String storeFile = "info.dat";
	
	public static void writeUser(ArrayList<User> user) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src" + File.separator + storeDir + File.separator + storeFile));
		oos.writeObject(user);
		oos.close();
	}
	
	public static ArrayList<User> readUser() throws IOException,ClassNotFoundException{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src" + File.separator + storeDir + File.separator + storeFile));
		ArrayList<User> listUsers = (ArrayList<User>)ois.readObject();
		ois.close();
		return listUsers;
	}
	
	
}

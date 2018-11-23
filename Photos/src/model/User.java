package model;

import java.util.ArrayList;
import java.io.Serializable;

public class User implements Serializable {
// Fields == what does User have? Login info, password info, arrayList of Albums
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4446736343893843005L;
	private String username;
	private ArrayList<Album> userAlbums;
	
	
	public User(String username) {
		this.username = username;
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Album> getUserAlbums(){
		if(userAlbums == null)
			userAlbums = new ArrayList<Album>();
		
		
		return userAlbums;
	}
	
	
	
	public String getUsername() {
		return username;
	}
	
	public void setloginInfo(String username) {
		this.username = username;
	}
	
	public void addAlbum(Album album) {
	if(userAlbums == null)
		userAlbums = new ArrayList<Album>();
	
	
		userAlbums.add(album);
	}
	
	public void createAlbum(String albumName) {
		userAlbums.add(new Album(albumName));
	}
	
	public void deleteAlbum() {
		
	}
	
	public void renameAlbum() {
		
	}
	
	
	public void addPhoto() {
		
	}
	
	public void removePhoto() {
		
	}
	
	public void captionPhoto() {
		
	}
	
	public void displayPhoto() {
		
	}
	
	public void searchPhoto() {
		
	}
	
	
}

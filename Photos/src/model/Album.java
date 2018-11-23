package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/* Contains Photos */
public class Album implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5610160795269752494L;
	/**
	 * 
	 */
	
	private ArrayList<Photo> albumPhotos;
	private String albumName;
	private int numPhotos;
	private Date startDate;
	private Date endDate;
	private String albumDetail;
	public Album(String albumName) {
		this.albumName = albumName;
		
	}
	
	public Album(String albumName, int numPhotos) {
		this.albumName = albumName;
		this.numPhotos = numPhotos;
	}
	
	
	public ArrayList<Photo> getAlbumPhotos(){
		if(albumPhotos == null)
			albumPhotos = new ArrayList<Photo>();
		
		return albumPhotos;
	}
	
	public void setAlbumName(String str) {
		albumName = str;
	}
	
	
	
	public String getAlbumName() {
		return albumName;
		}

	
	public void setAlbumDetail() {
		if(albumPhotos != null)
		if(albumPhotos.size() > 0) {
		startDate = albumPhotos.get(0).getCurrentDate();
		endDate = albumPhotos.get(albumPhotos.size()-1).getCurrentDate();
		//System.out.println(startDate);// index 
		}
		
		
		
		albumDetail = this.albumName + " " + "(" + numPhotos + " photos;" + " " + "startDate: " + startDate + " endDate: " + endDate + ")";
	}
	
	public String getAlbumDetail() {
		return albumDetail;
	}
	
	public String test() {
		return "AH";
	}
	

	
	public void setNumPhotos() {
		this.numPhotos = albumPhotos.size();
	}
	
	public int getNumPhotos() {
		return numPhotos;
	}
	
	public void addPhoto(Photo photo) {
		if(albumPhotos == null)
			albumPhotos = new ArrayList<Photo>();
		
	
		albumPhotos.add(photo);
		
	}
	
	public Photo getPhoto(String fileName) {
		for (int i = 0; i < albumPhotos.size(); i++) {
			if(albumPhotos.get(i).equals(fileName))
				return albumPhotos.get(i);
		}
		return null;
	}

	public void addNumPhotos() {
		this.numPhotos++;
		// TODO Auto-generated method stub
		
	}
	
	public void subtractNumPhotos() {
		this.numPhotos--;
	}
}

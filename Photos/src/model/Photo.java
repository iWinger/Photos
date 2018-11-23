package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
// Date Photo
/*Since we won't examine the contents of a photo file to get the date the photo was taken, we will instead use the last modification date of the photo file (as provided via the Java API to the filesystem) as a proxy. (The user interface will still refer to this as the date the photo was taken.)

You may want to use a java.util.Calendar instance for the date and time the photo was taken. Note: When you set a date and time in a Calendar instance, also make sure you set milliseconds to zero, as in:

     
     cal.set(Calendar.MILLISECOND,0);
Otherwise your equality checks won't work correctly.
Alternatively, you may use the classes in java.time to manipulate dates and times.*/

/*Location of Photos - Stock photos and User photos

There are two sets of photos, stock photos that come pre-loaded with the application, and user photos that are loaded/imported by a user when they run the application.

Stock photos are photos that you will keep in the application's workspace. You must have no fewer than 5 stock photos, and no more than 10.
Create a special username called "stock" (no password, or password="stock") and store the stock photos under this user, in an album named "stock".

Leave the photos in the application's workspace so the graders can test your application starting with your stock photos, then load other photos from their computer, see "User photos" below.

Try to work with low/medium resolution pictures for the stock photos because they will be on Bitbucket and downloaded by the graders, and you don't want to bloat your project size. Make sure that your application will work correctly even if there are no stock photos to start with. (Think of the scenario where a user might delete all the stock photos that comes with the application before loading any of their own - your application should be able to present a sensible interface if/when this happens.)

User photos are photos that your application can allow a user to load from their computer, so they can be housed anywhere on the user's machine. The actual photos must NOT be in your application's workspace. Instead, your application should only store the location of the photo on the user's machine. User photo information must NOT be in the released project in Bitbucket since each installation of your application on a machine will have its own set of users.*/
public class Photo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8551152147613470267L;
	// User photos and Stock photos
	private String photoName;
	private Date currentDate;
	private String caption;
	private ArrayList<Tag> photoTags;
	private String filePath; 
	
	// Stock photos
	public Photo(String filePath) {
		this.filePath = filePath;
		currentDate = Calendar.getInstance().getTime();
		
	}
	
	public void setName(String photoName) {
		this.photoName = photoName;
	}
	
	public String getName() {
		return photoName;
	}
	
	public Date getCurrentDate() {
		return currentDate;
	}
	
	public String getStringDate() {
		return currentDate.toString();
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public ArrayList<Tag> getPhotoTags(){
		if(photoTags == null)
			photoTags = new ArrayList<Tag>();
		
		return photoTags;
	}
	
	public void setPhotoTags(ArrayList<Tag> photoTags) {
		this.photoTags = photoTags;
	}
	
	public Tag getSingleTag(String tagName) {
		for(int i = 0; i < photoTags.size(); i++) {
			if(photoTags.get(i).equals(tagName))
				return photoTags.get(i);
		}
		
		return null;
	}
	
	public void addTag(Tag tag) {
		if(photoTags != null)
			photoTags.add(tag);
	}

	public String getFileName() {
		return filePath;
	}
	
	
	
	
}

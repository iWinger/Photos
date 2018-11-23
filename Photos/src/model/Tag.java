package model;

import java.io.Serializable;

/*Photos can be tagged with pretty much any attribute you think is useful to search on, or group by. 
 * Examples are location where photo was taken, and names of people in a photo, so you can search for photos by location and/or names.

From the implementation point of view, it may be useful to think of a tag as a combination of tag name 
and tag value, e.g. ("location","New Brunswick"), or ("person","susan"). 
A photo may have multiple tags (name+value pairs), but no two tags for the same photo 
will have the same name and value combination.

Additional details:
--------------------
You can set up some tag types beforehand for the user to pick from (e.g. location)
Depending on the tag type, a user can either have a single value for it, or multiple values (e.g. for any photo, location can only have one value, but if there's a person tag, that can have multiple values, one per person that appears in the photo)
A user can define their own tag type and add it to the list (so from that point on, that tag type will show up in the preset list of types the user can choose from) */

public class Tag implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 938362777832897260L;
	private String tagName;
	private String tagValue;
	
	public Tag(String tagName, String tagValue) {
		this.tagName = tagName;
		this.tagValue = tagValue;
		
	}
	/* Multiple values */
	public Tag(String tagValue) {
		this.tagValue = tagValue;
	}
		
	/*Set tag methods */
	public void setTagName(String tagName){
		this.tagName = tagName;
	}
	
	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}
	/*Get tag methods */
	public String getTagName() {
		return this.tagName;
	}
	
	public String getTagValue() {
		return this.tagValue;
	}
	
	/*Get official tag */
	@Override
	public String toString() {
		return "(" + this.tagName + ", " + this.tagValue + ")";
	}
	
	

}

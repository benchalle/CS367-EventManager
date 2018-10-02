import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Fall 2017 
//PROJECT:          Program 5 Event-Volunteer Match
//FILE:             EventManager.java
//
//TEAM:    Benjamin Challe
//Authors: Benjamin Challe, bchalle@wisc.edu, bchalle,002
//
//---------------- OTHER ASSISTANCE CREDITS 
//Persons: none 
//
//Online sources:none
////////////////////////////80 columns wide //////////////////////////////////

/**
 * EventManager manages a list of events and a separate list of volunteers.
 * 
 * IMPLEMENT THE METHODS OF THIS CLASS
 * 
 * It also provides methods for adding matches between events and volunteers
 * and for displaying the events and volunteers that exist.
 * 
 * An EventManager instance manages the list of events and volunteers.
 */
public class EventManager {
	
	/** the list of events */
	private List<Event> eventList;

	/** the list of volunteers */
	private List<Volunteer> volunteerList;

	/**
	 * Constructor for an EventManager instance
	 */
	public EventManager(){
		this.eventList = new ArrayList<Event>();
		this.volunteerList = new ArrayList<Volunteer>();
	}
	
	/**
	 * Adds an event to the list of events or returns false if the details for the event are not valid.
	 * This maintains the event list in sorted order (sort is ascending by name only).
	 * 
	 * Tip: Collections.sort can be used after a new event is added.
	 * 
	 * The following conditions result in no event being added and false being returned
	 * <ul>
	 * <li>name is null or an empty string "".</li>
	 * <li>date is not an integer in range 1 to 30, inclusive.</li>
	 * <li>the event name already exists (duplicate event names are not allowed)</li>
	 * <li>the volunteer limit is less than one</li>
	 * </ul>
	 * 
	 * @param name the name of a new event
	 * @param dateStr the string for the date of this event
	 * @param limitStr the string for the volunteers limit in this event
	 * @return true if arguments have valid format and added event successfully, otherwise false
	 */
	public boolean addEvent(String name, String dateStr, String limitStr){
		// TODO: implement this method
		if(name.equals("") || name == null){ //if name is valid continue
			return false; //otherwise return false
		}
		if(findEvent(name) != null){ //if the event already exists return false
			return false;
		}
	
		try{
			//try to convert date and limit to ints
			int dateInt = Integer.parseInt(dateStr);
			int volLimit = Integer.parseInt(limitStr);
			if(volLimit < 1){ //check to see if limit is valid if its not return false
				return false;
			}
			if(dateInt < 1 || dateInt > 30){ // check to see if dates are valid if its niot return false
				return false;
			}
			Event event = new Event(name,dateInt, volLimit); //create a new event with info
			eventList.add(event); //add it to the list
			
		}catch(NumberFormatException e){ //if the dates and limit are not valid return false
			return false;
		}
		
		
		Collections.sort(eventList); //sort the list to alphabetical order
		return true; //return true if event was correctly added
	}
		
	/**
	 * Adds a new volunteer to the list of volunteers or returns false.
	 * Maintains the volunteer list in sorted order.  
	 * 
	 * Tip: Collections.sort can be used after a new volunteer is added.
	 * 
	 * <ul>
	 * <li>Name must not be null or empty string</li>
	 * <li>Volunteer name must not be a duplicate.</li>
	 * </ul>
	 *
	 * @param name the name of a new volunteer
	 * @param availableDatesStrAry a String array that has date strings
	 */
	public boolean addVolunteer(String name, String[] availableDatesStrAry){
		ArrayList<Integer> intDates = new ArrayList<Integer>(); //create a list for dates
		if(name.equals("") || name == null){ // if the name is valid contiune otherwise return false
			return false;
		}
		if(findVolunteer(name) != null){ //if the volunteer already exists return false
			return false;
		}
		try{
			//try to parse the dates to ints
			for(int i =0; i< availableDatesStrAry.length; i++){
				int date = Integer.parseInt(availableDatesStrAry[i]);
				intDates.add(date); //add the dates to the list
			}
			Volunteer  volunteer = new Volunteer(name, intDates); //create a new volunteer with the name and dates
			volunteerList.add(volunteer); //add the volunteer to the list of volunteers
		}catch(NumberFormatException e){ //if dates are invalid return false
			return false;
		}
		
		// TODO: implement this method
		Collections.sort(volunteerList); //sort the list to alphabetical order
		return true; //return true if properly added
	}
	
	/** 
	 * USED ONLY IF AN EVENT NEEDS TO BE REMOVED WHILE READ FROM FILE
	 * 
	 * Iterates through the event list and remove the event if event exists. 
	 * This method must also remove all the event-volunteer matches corresponding to this event.
	 * 
	 * @param name the name of the event to be removed
	 * @return true if the event existed and removed successfully, otherwise false
	 */
	public boolean removeEvent(String name) {
		Iterator<Event> itr = eventList.iterator(); //create an iterator for events
		boolean removed = false; //create a variable to see if event has been removed
		int index = 0; //create an index counter
		while(itr.hasNext() && !removed){ //while there is another event
			Event event = itr.next(); //save the next event
			if(event.getName().equalsIgnoreCase(name)){ //if the event matches
				eventList.remove(index); //remove it at the index
				removed = true; //indicate an event has been removed
			}
			index++; //increment index
		}
		//TODO: implement this method
		return removed; //return if an item has been removed
	}
	
	/**
	 * Iterates through the volunteer list and removes the volunteer if volunteer exists. 
	 * Also removes all the event-volunteer matches corresponding to this volunteer
	 * 
	 * @param name the name of the volunteer to be removed
	 * @return true if volunteer existed and removed successfully, otherwise false
	 */
	public boolean removeVolunteer(String name){
		Iterator<Volunteer> itr = volunteerList.iterator(); //create an iterator for volunteers
		int index = 0; //create an index counter
		boolean removed = false; //create a variable to see if event has been removed
		while(itr.hasNext() && !removed){//while there is another volunteer
			Volunteer volunteer = itr.next();//save the next volunteer
			if(volunteer.getName().equalsIgnoreCase(name)){ // if the volunteer matches
				volunteerList.remove(index); //remove it at the index
				removed = true; //indicate an volunteer has been removed
			}
			index++;//increment index
		}
		// TODO: implement this method
		return removed;//return if an item has been removed
	}
	
	/**
	 * Given the event name,check if the event exists in the event list. 
	 * 
	 * @param name the name of the event to be found
	 * @return event if the event exists, otherwise null.
	 */
	public Event findEvent(String name){
		Iterator<Event> itr = eventList.iterator(); //create an iterator for events
		while(itr.hasNext()){ //while there is another event
			Event event = itr.next(); //save next event
			if(event.getName().equalsIgnoreCase(name)){ //if the event is found
				return event; //return the event
			}
		}
		// TODO: implement this method
		return null; //otherwise return null
	}
	
	/**
	 * Return the volunteer with the given name.
	 * 
	 * @param name the name of the volunteer
	 * @return volunteer if the volunteer exists, otherwise null.
	 */
	public Volunteer findVolunteer(String name){
		Iterator<Volunteer> itr = volunteerList.iterator(); //create an iterator for volunteer
		while(itr.hasNext()){ //while there is another volunteer
			Volunteer volunteer = itr.next(); //save next volunteer
			if(volunteer.getName().equalsIgnoreCase(name)){ //if the volunteer is found
				return volunteer; //return the volunteer
			}
		}
		// TODO: implement this method
		return null; //otherwise return null
	}
	
	/**
	 * This method is used to create a match between an event and a volunteer.
	 * 
	 * <ol>
	 * <li>Find the event and the volunteer from their names.</li>
	 * <li>If either is null, return false.</li>
	 * <li>If event has not reached volunteer limit and volunteer has the event's date in its availability list, then
	 *     <ol><li>add the volunteer node to the event's adjacency list</li>
	 *     <li>add the event to the volunteer's list</li>
	 *     <li>set the availability date for the volunteer to false</li>
	 *     </ol>
	 * </li>
	 * <li>return true if all is well</li>
	 * </ol> 
	 * 
	 * @param eventName the name of an event to be matched to a volunteer
	 * @param volunteerName the name of a volunteer to be matched to a event
	 * @return true if the match is created, otherwise false.
	 */
	public boolean createMatch(String eventName, String volunteerName){
		Event event = findEvent(eventName); //find the event
		Volunteer volunteer = findVolunteer(volunteerName); //find the volunteer
		if(event == null || volunteer == null){ //if the events and volunteers dont exist return false
			return false;
		}
		if(event.isBelowLimit()){ //if there is room for more volunteers
			event.addAdjacentNode(volunteer); //add the volunteer to the event
			volunteer.addAdjacentNode(event); //add the event to the volunteer
			volunteer.setUnavailable(event.getDate()); //set date to unavailable
		}else{
			return false; //otherwise return false
		}
		// TODO: implement this method
		return true;//if match created return true
	}
	
	/**
	 * Given the event and volunteer, remove the match between them if it exists.
	 * Return true if the match is found and removed.
	 * 
	 * If a match is found:
	 * 
	 * <ul>
	 * <li>remove the volunteer from the event's volunteer list</li>
	 * <li>remove the event from volunteer's event list</li>
	 * <li>set the event's date to available for the volunteer</li>
	 * <li>return true if all is well</li>
	 * </ul>
	 * 
	 * @param eventName the name of an event to be removed from match
	 * @param volunteer the name of a volunteer to be removed from match
	 * @return true if the match existed and removed successfully, otherwise false.
	 */
	public boolean removeMatch(String eventName, String volunteerName){
		Event event = findEvent(eventName); //find the event
		Volunteer volunteer = findVolunteer(volunteerName); //find the volunteer
		if(event == null || volunteer == null){ //if the event or volunteer doesnt exist return false
			return false;
		}
		event.removeAdjacentNode(volunteer); //remove the volunteer from event
		volunteer.removeAdjacentNode(event); //remove the event from volunteer
		volunteer.setAvailable(event.getDate()); //set the date to available
		return true; //return true if successful
		// TODO: implement this method
	}
	
	/*
	 * This method is used to display all the events along 
	 * with corresponding matches with the volunteers.
	 * Check sample files for exact format of the display.
	 * 
	 * Utilize formats defined in the Resource class
	 * to display in correct format.
	 * 
	 * Resource.STR_ERROR_DISPLAY_EVENT_FAILED
	 * Resource.STR_DISPLAY_ALL_EVENTS_PRINT_FORMAT
	 */
	public void displayAllEvents(){
		if(eventList.isEmpty()){ //if the event list is empty
			System.out.print(Resource.STR_ERROR_DISPLAY_EVENT_FAILED); //print that there is no event yet
			System.out.print("Display All Events [0 events]\n" //print 0 events line
					+ "------------------------\n");
		}else{
		System.out.print("Display All Events [" + eventList.size() + " events]\n" //print the correct events line
				+ "------------------------\n");
		Iterator<Event> itr = eventList.iterator(); //iterate through the evenlist
		while(itr.hasNext()){ //while there is another event
			Event event = itr.next(); //save the event
			System.out.print(event.toString()); //print the event
		}
		// TODO: implement this method
	}
	}
	
	/**	 
	 * This method is used to display all the volunteers along 
	 * with corresponding matches with the events.
	 * Check sample files for exact format of the display.
	 * 
	 * Utilize formats defined in the resource file for 
	 * display in the correct format.
	 * 
	 * Resource.STR_ERROR_DISPLAY_VOLUNTEER_FAILED
	 * Resource.STR_DISPLAY_ALL_VOLUNTEERS_PRINT_FORMAT
	 */
	public void displayAllVolunteers(){
		if(volunteerList.isEmpty()){ //if the volunteer list is empty
			System.out.print(Resource.STR_ERROR_DISPLAY_VOLUNTEER_FAILED); //print that there is no volunteer yet
			System.out.print("Display All Volunteers [" + 0 + " volunteers]\n" //print 0 volunteer line
			+ "------------------------\n");
		}else{
			System.out.print("Display All Volunteers [" + volunteerList.size() + " volunteers]\n" //priint the correct volunteer line
			+ "------------------------\n");
			Iterator<Volunteer> itrV = volunteerList.iterator(); //iterate through the volunteerlist
			while(itrV.hasNext()){ //while there is another volunteer
			Volunteer volunteer = itrV.next(); //save the volunteer
			System.out.print(volunteer.toString()); //print the volunteer
			}
		}
		// TODO: implement this method
	}
	
	/**
	 * This is helper method to create a string for
	 * writing all the volunteers in a file.
	 * 
	 * (Example)
	 * <pre>
	 * v;Mingi;5,23,30
	 * v;Sonu;1,2,3,4,5
	 * </pre>
	 * 
	 * @return a single string object containing all the volunteers 
	 * in the format needed to be printed in the file.
	 */
	public String toStringAllVolunteers(){
		Iterator<Volunteer> itr = volunteerList.iterator(); //iterate through the volunteer list
		StringBuilder strBuilder = new StringBuilder(); //create a new string builder
		while(itr.hasNext()){ //while there is another volunteer
			Volunteer volunteer = itr.next(); //save the volunteer
			strBuilder.append(volunteer.toFileString()); //add it to the stringbuilder
			strBuilder.append("\n"); //add a new line
		}
		// TODO: implement this method
		return strBuilder.toString(); //return the stringbuilder
	}
	
	/**
	 * This is helper method to create a string for
	 * writing all the events in a file.
	 * 
	 * (Example)
	 * e;Field trip;7
	 * e;Birthday;23;Mingi,Sonu
	 * 
	 * @return string containing all the events in the format
	 * needed to be printed in the file.
	 */
	public String toStringAllEvents(){
		Iterator<Event> itr = eventList.iterator(); //iterate through the eventlist
		StringBuilder strBuilder = new StringBuilder(); //create a new string builder
		while(itr.hasNext()){ //while there is another evnet
			Event event = itr.next(); //save the event
			strBuilder.append(event.toFileString()); //add it to the stringbuilder
			strBuilder.append("\n"); //aadd a new line
		}
		// TODO: implement this method
		return strBuilder.toString(); //return the stringbuilder
	}
}

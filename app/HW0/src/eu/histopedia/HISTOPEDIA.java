package eu.histopedia;

import com.hp.hpl.jena.rdf.model.*;

/** HISTOPEDIA Vocabulary class
 */
public class HISTOPEDIA {
	public static final String URI ="http://histopedia.eu/schema/1.0#";
	
	private static Model sModel = ModelFactory.createDefaultModel();
	
	public static final String _DATE = "Date";
	public static final Property DATE = createProperty(_DATE);
	public static final Resource DATE_R = createResource(_DATE);
	
	public static final String _DATE_FROM = "DateFrom";
	public static final Property DATE_FROM = createProperty(_DATE_FROM);
	public static final Resource DATE_FROM_R = createResource(_DATE_FROM);
	
	public static final String _DATE_TO = "DateTo";
	public static final Property DATE_TO = createProperty(_DATE_TO);
	public static final Resource DATE_TO_R = createResource(_DATE_TO);
	
	public static final String _EVENT = "Event";
	public static final Property EVENT = createProperty(_EVENT);
	public static final Resource EVENT_R = createResource(_EVENT);
	
	public static final String _PRE_EVENT = "PreEvent";
	public static final Property PRE_EVENT = createProperty(_PRE_EVENT);
	public static final Resource PRE_EVENT_R = createResource(_PRE_EVENT);
	
	public static final String _POST_EVENT = "PostEvent";
	public static final Property POST_EVENT = createProperty(_POST_EVENT);
	public static final Resource POST_EVENT_R = createResource(_POST_EVENT);
	
	public static final String _ACTOR = "Actor";
	public static final Property ACTOR = createProperty(_ACTOR);
	public static final Resource ACTOR_R = createResource(_ACTOR);
	
	public static final String _PERSON = "Person";
	public static final Property PERSON = createProperty(_PERSON);
	public static final Resource PERSON_R = createResource(_PERSON);

	public static final String _LOCATION = "Location";
	public static final Property LOCATION = createProperty(_LOCATION);
	public static final Resource LOCATION_R = createResource(_LOCATION);
	
	private static Property createProperty(String pProperty) {
		return sModel.createProperty(URI, pProperty);
	}
	
	private static Resource createResource(String pResource) {
		return sModel.createResource(URI+pResource);
	}
}
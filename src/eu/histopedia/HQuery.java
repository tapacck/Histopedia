package eu.histopedia;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;

public class HQuery {
	public static void setModel(Model pModel) {
		sModel = pModel;
	}
	
	private static String getNamespaces() {
		return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> "
				+"PREFIX history: <"+HISTOPEDIA.URI+"> ";
	}
	
	public enum Order {
		BY_LABEL,
		BY_TIME,
	};
	
	public static Order sOrder = Order.BY_LABEL;
	
	public static String events() {
		return getNamespaces()
				+"SELECT DISTINCT ?Label ?URI " 
				+"WHERE {"				
				+"?URI rdf:type history:Event . "
				+"?URI rdfs:label ?Label . "
				+"} "
				+(sOrder == Order.BY_LABEL ? "ORDER BY ?Label " : "");
	}
	
	public static String eventsWithPerson(String pPerson) {
		return getNamespaces()
				+"SELECT DISTINCT ?Label ?URI " 
				+"WHERE {"				
				+"?ps rdfs:label \""+pPerson+"\" . "
				+"?URI history:Person ?ps . "
				+"?URI rdfs:label ?Label "
				+"} "
				+(sOrder == Order.BY_LABEL ? "ORDER BY ?Label " : "");
	}
	
	private static boolean dateFitsInTime(Resource pResource, Property pDate, int pFrom, int pTo) {
		Statement tStmt;
		if ((tStmt = pResource.getProperty(pDate)) == null) return false;
		
		String tDate = tStmt.getObject().toString();
		
		int tYear = Integer.valueOf(tDate.split("-")[0]).intValue();
		
		if (tYear == 0) return false;
		
		return pFrom <= tYear && tYear <= pTo;
	}
	
	// TODO Note this is not yet completed
	public static String eventsInTime(int pFrom, int pTo, boolean pCovered) {		
		// Note, in case of time periods, only fully covered periods are considered.
		String sQuery = getNamespaces()
				+"SELECT DISTINCT ?Label ?URI " 
				+"WHERE {";						
		
		NodeIterator tIter = sModel.listObjects();
		while (tIter.hasNext()) {
			RDFNode tObject = tIter.next();
			
			if (tObject.isResource() == false) continue;
			Resource tResource = tObject.asResource();
			
			Statement tStmt = tResource.getProperty(RDF.type);
			if (tStmt == null || tStmt.getObject().equals(HISTOPEDIA.EVENT) == false) continue;
			
			boolean tMatches = dateFitsInTime(tResource, HISTOPEDIA.DATE, pFrom, pTo);
			
			if (!tMatches) {
				if (pCovered) {
					tMatches = dateFitsInTime(tResource, HISTOPEDIA.DATE_FROM, pFrom, pTo)
							&& dateFitsInTime(tResource, HISTOPEDIA.DATE_TO, pFrom, pTo);
				}
				else {
					tMatches = dateFitsInTime(tResource, HISTOPEDIA.DATE_FROM, pFrom, pTo)
							|| dateFitsInTime(tResource, HISTOPEDIA.DATE_TO, pFrom, pTo);
				}
			}
			
						
//				sQuery += "?URI rdf:about <"+tObject.toString()+"> . "
//						+"?URI rdfs:label ?Label . ";	
				
			if (tMatches) {
				System.out.println("<"+tObject.toString()+">");
			}
		}
		
		sQuery += "} ";
//				+(sOrder == Order.BY_LABEL ? "ORDER BY ?Label " : "");	
		
		return sQuery;
	}
	
	public static String eventsWith2Persons(String pPerson1, String pPerson2) {
		return getNamespaces()
				+"SELECT DISTINCT ?Label ?URI " 
				+"WHERE {"				
				+"?ps1 rdfs:label \""+pPerson1+"\" . "
				+"?ps2 rdfs:label \""+pPerson2+"\" . "
				+"?URI history:Person ?ps1 . "
				+"?URI history:Person ?ps2 . "
				+"?URI rdfs:label ?Label "
				+"} "
				+(sOrder == Order.BY_LABEL ? "ORDER BY ?Label " : "");
	}
	
	public static String locations() {
		return getNamespaces()
				+"SELECT DISTINCT ?Label ?URI " 
				+"WHERE {"				
				+"?URI rdf:type history:Location . "
				+"?URI rdfs:label ?Label . "
				+"} "
				+(sOrder == Order.BY_LABEL ? "ORDER BY ?Label " : "");
	}
	
	public static String locationsWithPerson(String pPerson) {
		return getNamespaces()
				+"SELECT DISTINCT ?Location ?URI " 
				+"WHERE {"				
				+"?ps rdfs:label \""+pPerson+"\" . "
				+"?ev history:Person ?ps . "
				+"?ev history:Location ?URI . "			
				+"?URI rdfs:label ?Location "
				+"} "
				+(sOrder == Order.BY_LABEL ? "ORDER BY ?Label " : "");
	}
	
	public static String persons() {
		return getNamespaces()
				+"SELECT DISTINCT ?Label ?URI " 
				+"WHERE {"				
				+"?URI rdf:type history:Person . "
				+"?URI rdfs:label ?Label . "
				+"} "
				+(sOrder == Order.BY_LABEL ? "ORDER BY ?Label " : "");
	}
	
	private static Model sModel = null;
}

package eu.histopedia.test;

import java.io.InputStream;

import eu.histopedia.*;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] pArgs) {
		Util.setModel(model());
		HQuery.setModel(model());
		
		String tInputFileName = "../../data/histopedia.xml";

		InputStream tIn = FileManager.get().open(tInputFileName);
		if (tIn == null) {
			throw new IllegalArgumentException("File: "+tInputFileName+ " not found");
		}

		model().read(tIn, null);				

//		Util.dumpObjects();
		
		Query tQuery = QueryFactory.create(
//				HQuery.events()	
//				HQuery.eventsWithPerson("Karl the Great")
//				HQuery.eventsWith2Persons("Karl the Great", "Widukind")
				HQuery.eventsInTime(775, 810, true)
				
//				HQuery.locations()	
//				HQuery.locationsWithPerson("Karl the Great")

//				HQuery.persons()
				);

		QueryExecution tQE = QueryExecutionFactory.create(tQuery, model());

		ResultSet tResults = tQE.execSelect();

		ResultSetFormatter.out(System.out, tResults, tQuery);

		tQE.close();
	}

	private static Model model() {
		return sModel;
	}
	
	private static Model sModel = ModelFactory.createDefaultModel();
	
}

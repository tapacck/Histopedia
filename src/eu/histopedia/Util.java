package eu.histopedia;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

public class Util {
	public static void setModel(Model pModel) {
		sModel = pModel;
	}
	
	public static void dumpResource(String pResource) {
		System.out.println("** Dumping Resource "+pResource+" ***");
		Resource tResource = sModel.getResource(pResource);
		if (tResource == null) {
			System.out.println("- no such resource -");
			return;
		}
		StmtIterator tIter = tResource.listProperties();
		while (tIter.hasNext()) {
			Statement tStatement = tIter.next();
//			dumpResource(tStatement.getObject().toString());
			
//			System.out.println("\tObject: "+tStatement.getObject().toString()
//					+" isURI: "+tStatement.getObject().isURIResource());
		}
	}
	
	public static void dumpSubjects() {
		ResIterator tIter = sModel.listSubjects();
		while (tIter.hasNext()) {
			Resource tSubject = tIter.next();
			System.out.println(tSubject.toString()
					+", person? "+tSubject.hasProperty(HISTOPEDIA.PERSON)
//					+", actor? "+tSubject.hasProperty(HISTORY.ACTOR)
//					+", event? "+tSubject.hasProperty(HISTORY.EVENT)
					+", pre-event? "+tSubject.hasProperty(HISTOPEDIA.PRE_EVENT)
					+", post-event? "+tSubject.hasProperty(HISTOPEDIA.POST_EVENT)
					);
		}
	}
	
	public static void dumpObjects() {
		NodeIterator tIter = sModel.listObjects();
		while (tIter.hasNext()) {
			RDFNode tObject = tIter.next();
			
			if (tObject.isLiteral() && tObject.isResource()) {
				System.out.println("??? "+tObject.toString()+" is both Literal and Resource!");
			}
			else if (tObject.isLiteral()) {
				System.out.println("Literal \""+tObject.toString()+"\"");
			}
			else if (tObject.isResource()) {
				String tResType = "Resource ";
				Statement tStmt = tObject.asResource().getProperty(RDF.type);
				if (tStmt != null) {
					tResType += "["+tStmt.getObject().toString()+"] ";
				}

				System.out.println(tResType+"<"+tObject.toString()+">");
			}
		}
	}
	
	public static void dumpStatements() {
		StmtIterator tIter = sModel.listStatements(
				//null, HISTORY.PRE_EVENT, (RDFNode)null
				);

		while (tIter.hasNext()) {
			Statement tStmt = tIter.nextStatement();
			Resource tSubject = tStmt.getSubject();
			Property tPredicate = tStmt.getPredicate();
			RDFNode tObject = tStmt.getObject();

			System.out.print(tSubject.toString());
			
			if (tSubject.isResource()) {
				Statement tStmt2 = tSubject.asResource().getProperty(RDF.type);
				System.out.print(" ["+tStmt2.getObject().toString()+"]");
			}
			
			System.out.print(" "+tPredicate.toString()+" ");
			if (tObject instanceof Resource) {
				System.out.print(tObject.toString());
			}
			else {
				System.out.print("\""+tObject.toString()+"\"");
			}

			System.out.println(" .");
		} 
	}

	private static Model sModel = null;
}

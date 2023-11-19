package edu.asu.crimewatch.helper;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;

public class OntologyConnector {

    static String complaintsURL = "http://localhost:3030/Shooting_Data";
    static String header = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX crimewatch: <http://www.semanticweb.org/crimewatch#>";



    public static void runComplaintsSparQLQuery(String query){
        StringBuffer queryStr = new StringBuffer();
        queryStr.append(header);
        queryStr.append(query);

        String finalQuery = queryStr.toString();

        QueryExecution qexec = QueryExecutionFactory.sparqlService(complaintsURL, finalQuery);
        ResultSet rs = qexec.execSelect();
        System.out.println(rs);


    }

}
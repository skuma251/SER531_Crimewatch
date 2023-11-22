package edu.asu.crimewatch.helper;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;


public class OWLReader {

    
    static String header = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX crimewatch: <http://www.semanticweb.org/crimewatch#>";



    public static ResultSet runSparQLQuery(String URL, String query){
        StringBuffer queryStr = new StringBuffer();
        queryStr.append(header);
        queryStr.append(query);

        String finalQuery = queryStr.toString();

        QueryExecution qexec = QueryExecutionFactory.sparqlService(URL, finalQuery);
        ResultSet resultDataSet = qexec.execSelect();
        return resultDataSet;


    }

}
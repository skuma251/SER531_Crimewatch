package edu.asu.crimewatch.service;

import org.springframework.stereotype.Service;

import edu.asu.crimewatch.helper.OntologyConnector;


@Service
public class ComplaintsService{

    public void getComplaintsbyYear(){

        String query = "SELECT ?details WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:occuredIn ?details ."+
                        "} LIMIT 10";

        OntologyConnector.runComplaintsSparQLQuery(query);

    }
}
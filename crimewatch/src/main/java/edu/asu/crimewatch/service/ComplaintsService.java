package edu.asu.crimewatch.service;

import edu.asu.crimewatch.helper.OWLReader;

import org.springframework.stereotype.Service;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

@Service
public class ComplaintsService{

    static String complaintsURL = "http://localhost:3030/Complaints_Data";
    public List<Integer> fetchYearValues(){

        String query = "SELECT DISTINCT ?YEAR WHERE {"+
                        "?crime rdf:type crimewatch:Crime; "+
                        "crimewatch:occuredDateTime ?datetime; "+
                        "BIND(YEAR(?datetime) AS ?YEAR)."+
                        "}";

        ResultSet response = OWLReader.runSparQLQuery(complaintsURL, query);
        List<Integer> yearList = new ArrayList<>();

        while(response.hasNext()) {
            QuerySolution soln = response.nextSolution();
            int year = (int) soln.getLiteral("?YEAR").getValue();
            yearList.add(year);           
        }
        Collections.sort(yearList);
        return yearList;
    }

    public Map<String,Integer> fetchCrimeTypeCountByYear(int year){

        String query = "SELECT ?offenseType (COUNT(DISTINCT ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:hasOffenseType ?offenseType;"+
                        "crimewatch:occuredDateTime ?datetime;"+
                        "BIND(YEAR(?datetime) AS ?year). "+          
                        "FILTER(?year ="+year+")."+  
                        "}Group BY ?offenseType";

        ResultSet response = OWLReader.runSparQLQuery(complaintsURL, query);
        Map<String, Integer> crimeTypeMap = new HashMap<>();
         while( response.hasNext())
        {

            QuerySolution soln = response.nextSolution();
            crimeTypeMap.put(soln.getLiteral("?offenseType").getString(),soln.getLiteral("?Count").getInt());
        }
      return crimeTypeMap;

    }


}
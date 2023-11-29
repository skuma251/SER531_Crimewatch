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

public class HateCrimeService{

    static String hateCrimeURL = "http://localhost:3030/Shooting_HateCrime_Data";

    public List<Integer> fetchYearValues(){

        String query = "SELECT DISTINCT ?YEAR WHERE {"+
                        "?crime rdf:type crimewatch:Crime; "+
                        "crimewatch:occuredDateTime ?datetime; "+
                        "crimewatch:hasDataSourceType 'Hate Crime Data'."+
                        "BIND(YEAR(?datetime) AS ?YEAR)."+
                        "FILTER(?YEAR != 2023)."+
                        "}";

        ResultSet response = OWLReader.runSparQLQuery(hateCrimeURL, query);
        List<Integer> yearList = new ArrayList<>();

        while(response.hasNext()) {
            QuerySolution soln = response.nextSolution();
            int year = (int) soln.getLiteral("?YEAR").getValue();
            yearList.add(year);           
        }
        Collections.sort(yearList);
        return yearList;
    }

    public Map<String,Integer> getBoroCountByYear(int year){

        String query = "SELECT ?BoroName (COUNT( Distinct ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:occuredIn ?Location;"+
                        "crimewatch:occuredDateTime ?datetime;"+
                        "crimewatch:hasDataSourceType 'Hate Crime Data'."+
                        "BIND(YEAR(?datetime) AS ?year)."+
                        "?Location crimewatch:underArea ?Boro."+
                        "?Boro crimewatch:hasName ?BoroName."+
                        "FILTER(?year =" + year +")."+
                        "} GROUP BY ?BoroName";

        ResultSet response = OWLReader.runSparQLQuery(hateCrimeURL, query);
        Map<String, Integer> boroCountMap = new HashMap<>();
         while( response.hasNext())
        {

            QuerySolution soln = response.nextSolution();
            boroCountMap.put(soln.getLiteral("?BoroName").getString(),soln.getLiteral("?Count").getInt());
        }
      return boroCountMap;
    }

    public Map<String,Integer> getBiasCountByYear(int year){

        String query = "SELECT ?BiasMotive (COUNT( Distinct ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:occuredDateTime ?datetime;"+
                        "crimewatch:hasDataSourceType 'Hate Crime Data';"+
                        "crimewatch:hasBiasMotiveDesc ?BiasMotive."+
                        "BIND(YEAR(?datetime) AS ?year)."+
                        "FILTER(?year =" + year +")."+
                        "} GROUP BY ?BiasMotive";

        ResultSet response = OWLReader.runSparQLQuery(hateCrimeURL, query);
        Map<String, Integer> biasCountMap = new HashMap<>();
         while( response.hasNext())
        {

            QuerySolution soln = response.nextSolution();
            biasCountMap.put(soln.getLiteral("?BiasMotive").getString(),soln.getLiteral("?Count").getInt());
        }
      return biasCountMap;

    }

}
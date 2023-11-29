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

public class ShootingService{

    static String shootingURL = "http://localhost:3030/Shooting_HateCrime_Data";

    public List<Integer> fetchYearValues(){

        String query = "SELECT DISTINCT ?YEAR WHERE {"+
                        "?crime rdf:type crimewatch:Crime; "+
                        "crimewatch:occuredDateTime ?datetime; "+
                        "crimewatch:hasDataSourceType 'Shooting Data'."+
                        "BIND(YEAR(?datetime) AS ?YEAR)."+
                        "FILTER(?YEAR >=2015)."+
                        "}";

        ResultSet response = OWLReader.runSparQLQuery(shootingURL, query);
        List<Integer> yearList = new ArrayList<>();

        while(response.hasNext()) {
            QuerySolution soln = response.nextSolution();
            int year = (int) soln.getLiteral("?YEAR").getValue();
            yearList.add(year);           
        }
        Collections.sort(yearList);
        return yearList;
    }

    public Map<String,Integer>  getVictimCountByRace(int year, String boro){

        String query = "SELECT ?Race (COUNT( Distinct ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:occuredIn ?Location;"+
                        "crimewatch:occuredDateTime ?datetime;"+
  						"crimewatch:hasVictim ?Victim;"+
                        "crimewatch:hasDataSourceType 'Shooting Data'."+
  						"?Victim crimewatch:hasRace ?Race."+
                        "BIND(YEAR(?datetime) AS ?year)."+
                        "?Location crimewatch:underArea ?Boro."+
                        "?Boro crimewatch:hasName ?BoroName."+
                        "FILTER(?BoroName ='"+boro+"')."+
  						"FILTER(?year = "+year+")."+
                        "} GROUP BY ?Race";

        ResultSet response = OWLReader.runSparQLQuery(shootingURL, query);
        Map<String, Integer> victimCountRaceMap = new HashMap<>();
         while( response.hasNext())
        {

            QuerySolution soln = response.nextSolution();
            victimCountRaceMap.put(soln.getLiteral("?Race").getString(),soln.getLiteral("?Count").getInt());
        }
      return victimCountRaceMap;

    }

    public Map<String,Integer>  getPerpCountByRace(int year, String boro){

        String query = "SELECT ?Race (COUNT( Distinct ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:occuredIn ?Location;"+
                        "crimewatch:occuredDateTime ?datetime;"+
  						"crimewatch:hasPerpetrator ?Perp;"+
                        "crimewatch:hasDataSourceType 'Shooting Data'."+
  						"?Perp crimewatch:hasRace ?Race."+
                        "BIND(YEAR(?datetime) AS ?year)."+
                        "?Location crimewatch:underArea ?Boro."+
                        "?Boro crimewatch:hasName ?BoroName."+
                        "FILTER(?BoroName ='"+boro+"')."+
  						"FILTER(?year = "+year+")."+
                        "} GROUP BY ?Race";

        ResultSet response = OWLReader.runSparQLQuery(shootingURL, query);
        Map<String, Integer> perpCountRaceMap = new HashMap<>();
         while( response.hasNext())
        {

            QuerySolution soln = response.nextSolution();
            perpCountRaceMap.put(soln.getLiteral("?Race").getString(),soln.getLiteral("?Count").getInt());
        }
      return perpCountRaceMap;

    }

    public Map<String,Integer>  getCountByYear(int year){

        String query = "SELECT ?BoroName (COUNT( Distinct ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:occuredIn ?Location;"+
                        "crimewatch:occuredDateTime ?datetime;"+
                        "crimewatch:hasDataSourceType 'Shooting Data'."+
                        "BIND(YEAR(?datetime) AS ?year)."+
                        "?Location crimewatch:underArea ?Boro."+
                        "?Boro crimewatch:hasName ?BoroName."+
  						"FILTER(?year = "+year+")."+
                        "} GROUP BY ?BoroName";

        ResultSet response = OWLReader.runSparQLQuery(shootingURL, query);
        Map<String, Integer> boroCountMap = new HashMap<>();
         while( response.hasNext())
        {

            QuerySolution soln = response.nextSolution();
            boroCountMap.put(soln.getLiteral("?BoroName").getString(),soln.getLiteral("?Count").getInt());
        }
      return boroCountMap;

    }


}

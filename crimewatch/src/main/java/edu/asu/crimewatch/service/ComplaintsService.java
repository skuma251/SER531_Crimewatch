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

    public Map<String,Integer> fetchCrimeTypeCountByYear(int year, String sex){
        String query="";
        if(sex.equals("none"))
        {
            query = "SELECT ?offenseType (COUNT(DISTINCT ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:hasOffenseType ?offenseType;"+
                        "crimewatch:occuredDateTime ?datetime;"+
                        "BIND(YEAR(?datetime) AS ?year). "+          
                        "FILTER(?year ="+year+")."+  
                        "}Group BY ?offenseType";
        }
        else
        {
            query = "SELECT ?offenseType (COUNT(DISTINCT ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        " crimewatch:hasPerpetrator ?perp;"+
                        "crimewatch:hasOffenseType ?offenseType;"+
                        "crimewatch:occuredDateTime ?datetime;"+
                        "BIND(YEAR(?datetime) AS ?year). "+ 
                        "?perp crimewatch:hasSex ?sex."+      
                        "FILTER(?year ="+ year +" && ?sex ='"+ sex +"')."+  
                        "}Group BY ?offenseType";

        }

        ResultSet response = OWLReader.runSparQLQuery(complaintsURL, query);
        Map<String, Integer> crimeTypeMap = new HashMap<>();
         while( response.hasNext())
        {

            QuerySolution soln = response.nextSolution();
            crimeTypeMap.put(soln.getLiteral("?offenseType").getString(),soln.getLiteral("?Count").getInt());
        }
      return crimeTypeMap;

    }

    public Map<String,Integer> fetchCrimeCountByBoro(int year){

        String query = "SELECT ?BoroName (COUNT( Distinct ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:occuredIn ?Location;"+
                        "crimewatch:occuredDateTime ?datetime;"+
                        "BIND(YEAR(?datetime) AS ?year)."+
                        "?Location crimewatch:underArea ?Boro."+
                        "?Boro crimewatch:hasName ?BoroName."+
                        "FILTER(?year =" + year +")."+
                        "} GROUP BY ?BoroName";

        ResultSet response = OWLReader.runSparQLQuery(complaintsURL, query);
        Map<String, Integer> boroCountMap = new HashMap<>();
         while( response.hasNext())
        {

            QuerySolution soln = response.nextSolution();
            boroCountMap.put(soln.getLiteral("?BoroName").getString(),soln.getLiteral("?Count").getInt());
        }
      return boroCountMap;

    }


    public Map<String, List<Map<Integer, Integer>>> fetchBoroMonthlyStats(int year){

        String query = "SELECT ?BoroName ?month (COUNT( Distinct ?crime) as ?Count) WHERE {"+
                        "?crime rdf:type crimewatch:Crime ;"+
                        "crimewatch:occuredIn ?Location;"+
                        "crimewatch:occuredDateTime ?datetime;"+
                        "BIND(YEAR(?datetime) AS ?year)."+
  		                "BIND (MONTH(?datetime) AS ?month)."+
                        "?Location crimewatch:underArea ?Boro."+
                        "?Boro crimewatch:hasName ?BoroName."+
                        "FILTER(?year ="+ year +")."+
                        "} GROUP BY ?BoroName ?month "+
                        "ORDERBY ?BoroName ?month";

        ResultSet response = OWLReader.runSparQLQuery(complaintsURL, query);
        Map<String, List<Map<Integer, Integer>>> boroStatsMap = new HashMap<String,List<Map<Integer,Integer>>>();
        List<Map<Integer,Integer>> listMonthCount = new ArrayList<Map<Integer,Integer>>();
        String boroName = "";
         while( response.hasNext())
        {
            QuerySolution soln = response.nextSolution();
            if(!(boroName.equals(soln.getLiteral("?BoroName").getString())) && !(boroName.equals("")))
            {
               
                boroStatsMap.put(boroName,listMonthCount);
                listMonthCount.clear();
            }
            
            Map<Integer, Integer> monthcount = new HashMap<Integer, Integer>();
            int month = soln.getLiteral("?month").getInt();
            int count = soln.getLiteral("?Count").getInt();
            monthcount.put(month,count);
            listMonthCount.add(monthcount);
            boroName = soln.getLiteral("?BoroName").getString();
        }
        boroStatsMap.put(boroName,listMonthCount);
        return boroStatsMap;

    }



}
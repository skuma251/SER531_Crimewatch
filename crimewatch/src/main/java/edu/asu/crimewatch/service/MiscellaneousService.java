package edu.asu.crimewatch.service;

import edu.asu.crimewatch.helper.OWLReader;

import org.springframework.stereotype.Service;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service

public class MiscellaneousService {

    static String URL = "http://localhost:3030/Shooting_HateCrime_Data";

    public Map<String, List<Integer>> fetchShootingHateCountByYear(int year) {

        String query = "SELECT ?month (COUNT(DISTINCT ?crime1) as ?HateCrimeCount) (COUNT(DISTINCT ?crime2) as ?ShootingCount) WHERE {"
                +
                "{  SELECT ?month ?crime1 WHERE {" +
                "?crime1 rdf:type crimewatch:Crime ;" +
                "crimewatch:occuredDateTime ?datetime1 ;" +
                "crimewatch:hasDataSourceType 'Hate Crime Data' ." +
                "FILTER(YEAR(?datetime1) =" + year + ")." +
                "BIND(MONTH(?datetime1) AS ?month)." +
                "}}" +
                "{" +
                "SELECT ?month ?crime2 WHERE {" +
                "?crime2 rdf:type crimewatch:Crime ;" +
                "crimewatch:occuredDateTime ?datetime2 ;" +
                "crimewatch:hasDataSourceType 'Shooting Data' ." +
                "FILTER(YEAR(?datetime2) =" + year + ")." +
                "BIND(MONTH(?datetime2) AS ?month)." +
                "}}" +
                "}GROUP BY ?month ORDER BY ?month";
        ResultSet response = OWLReader.runSparQLQuery(URL, query);
        Map<String, List<Integer>> countStatsMap = new HashMap<>();
        String datatype1 = "Shooting";
        String datatype2 = "Hate Crime";
        while (response.hasNext()) {
            QuerySolution soln = response.nextSolution();
            if (!countStatsMap.containsKey(datatype1)) {
                countStatsMap.put(datatype1, new ArrayList<>());
            }
            if (!countStatsMap.containsKey(datatype2)) {
                countStatsMap.put(datatype2, new ArrayList<>());
            }
            int shootingCount = soln.getLiteral("?ShootingCount").getInt();
            countStatsMap.get(datatype1).add(shootingCount);
            int hateCrimeCount = soln.getLiteral("?HateCrimeCount").getInt();
            countStatsMap.get(datatype2).add(hateCrimeCount);
        }
        return countStatsMap;
    }
}
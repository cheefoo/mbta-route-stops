package com.ty.fm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.ty.fm.models.RouteRequest;
import com.ty.fm.models.RouteRequestList;
import com.ty.fm.models.RouteStop;
import com.ty.fm.models.RouteStops;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;


//curl -X GET "https://api-v3.mbta.com/routes?page%5Boffset%5D=0&page%5Blimit%5D=10&sort=-long_name" -H "accept: application/vnd.api+json"
public class MbtaRouteStopApp
{
    public static final String ROUTE = "route";
    public static  final String STOP =  "stop";

    public static void main (String []args) throws IOException
    {
        if(args.length != 2)
        {
            System.out.println("Please enter the two stops ");
            System.exit(1);
        }
        List<RouteRequest> routeRequestList = callRouteAPI("https://api-v3.mbta.com/routes?");
        Map<String, Integer> map = new HashMap();
        Map<String, List<RouteStop>> routeStopMap;

        List<Map<String, List<String>>> allRouteStopList = new ArrayList<>();

        //ListMultimap<String, String> allRoutesStopMap = ArrayListMultimap.create();
        MyMaps<String, String> allRoutesStopMap = new MyMaps<>();

        List<String> routeNameList = new ArrayList<>();
        for(RouteRequest routeRequest: routeRequestList)
        {
            System.out.println(routeRequest.getRouteRequestAttribute().getLong_name());
            routeNameList.add(routeRequest.getId());
            routeStopMap = callStopAPI("https://api-v3.mbta.com/stops?", routeRequest.getId());
            for(int i = 0; i < routeStopMap.get(routeRequest.getId()).size(); i++)
            {
                allRoutesStopMap.put(routeRequest.getId(),routeStopMap.get(routeRequest.getId()).get(i).getRouteStopAttribute().getName());
            }
            map.put(routeRequest.getRouteRequestAttribute().getLong_name(),routeStopMap.get(routeRequest.getId()).size());
            //System.out.println("Size of  " + routeRequest.getId() + " is " + routeStopMap.get(routeRequest.getId()).size());
        }
        Map.Entry maxValueEntry = maxUsingCollectionsMaxAndLambda(map);
        System.out.println("Subway route with most number of stops is " + maxValueEntry.getKey() + " " + "  and MaxValue is " + maxValueEntry.getValue());

        Map.Entry minValueEntry = minUsingCollectionsMaxAndLambda(map);
        System.out.println("Subway route with fewest number of stops is " + minValueEntry.getKey() + " " + "  and MinValue is " + minValueEntry.getValue());


        //System.out.println(allRoutesStopMap);
        /*
        Davis to Kendall -\> Redline
        Ashmont to Arlington -\> Redline, Greenline
         */
        System.out.println(args[0] + " "  + allRoutesStopMap.getKeyByValue(args[0]));
        System.out.println(args[1] + " "  + allRoutesStopMap.getKeyByValue(args[1]));


    }

    public static <K, V extends Comparable<V>> Map.Entry<K, V> maxUsingCollectionsMaxAndLambda(Map<K, V> map) {
        Map.Entry<K, V> maxEntry = Collections.max(map.entrySet(), (Map.Entry<K, V> e1, Map.Entry<K, V> e2) -> e1.getValue()
                .compareTo(e2.getValue()));
        return maxEntry;
    }

    public static <K, V extends Comparable<V>> Map.Entry<K, V> minUsingCollectionsMaxAndLambda(Map<K, V> map) {
        Map.Entry<K, V> minEntry = Collections.min(map.entrySet(), (Map.Entry<K, V> e1, Map.Entry<K, V> e2) -> e1.getValue()
                .compareTo(e2.getValue()));
        return minEntry;
    }

    private static List<RouteRequest> callRouteAPI(String urlString) throws IOException
    {
        String query = String.format("filter[type]=%s",
                URLEncoder.encode("0,1", "UTF-8"));
        URL url = new URL(urlString+query);

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/vnd.api+json; utf-8");
        con.setRequestProperty("Accept", "application/vnd.api+json");
        con.setDoOutput(true);

        int code = con.getResponseCode();
        System.out.println(code);
        RouteRequestList routeRequestList;

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8")))
        {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null)
            {
                response.append(responseLine.trim());
            }
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<RouteRequestList> mapType = new TypeReference<RouteRequestList>() {};
            routeRequestList = mapper.readValue(response.toString(), mapType);
        }
        return routeRequestList.getRouteRequestList();
    }

    private static Map<String, List<RouteStop>> callStopAPI(String urlString, String stop) throws IOException
    {
        Map <String, List<RouteStop>> map = new HashMap<>();
        String query = String.format("filter[route]=%s",
                URLEncoder.encode(stop, "UTF-8"));

        URL url = new URL(urlString+query);

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/vnd.api+json; utf-8");
        con.setRequestProperty("Accept", "application/vnd.api+json");
        con.setDoOutput(true);

        int code = con.getResponseCode();
        System.out.println(code);
        RouteStops routeStopsList;

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8")))
        {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null)
            {
                response.append(responseLine.trim());
            }
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<RouteStops> mapType = new TypeReference<RouteStops>() {};
            routeStopsList = mapper.readValue(response.toString(), mapType);

        }
        map.put(stop,routeStopsList.getRouteStopList());
        return map;
    }

    public static <T, C extends Collection<T>> C findIntersection(C newCollection,
                                                                  Collection<T>... collections) {
        boolean first = true;
        for (Collection<T> collection : collections) {
            if (first) {
                newCollection.addAll(collection);
                first = false;
            } else {
                newCollection.retainAll(collection);
            }
        }
        return newCollection;
    }
}
package com.ty.fm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ty.fm.models.RouteRequest;
import com.ty.fm.models.RouteRequestList;
import com.ty.fm.models.RouteStop;
import com.ty.fm.models.RouteStops;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class MbtaUtils
{
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

    public static List<RouteRequest> callRouteAPI(String urlString) throws IOException
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
                //InputStream targetStream = new ByteArrayInputStream(responseLine.getBytes());
                //System.out.println(responseLine.trim() + "\n");

            }
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<RouteRequestList> mapType = new TypeReference<RouteRequestList>() {};
            routeRequestList = mapper.readValue(response.toString(), mapType);
            //System.out.println("Size is " + routeRequestList.getRouteRequestList().size());

        }

        return routeRequestList.getRouteRequestList();
    }

    //curl -X GET "https://api-v3.mbta.com/stops?filter%5Broute%5D=Orange" -H "accept: application/vnd.api+json"
    public static Map<String, List<RouteStop>> callStopAPI(String urlString, String stop) throws IOException
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

}

package com.ty.fm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class MbtaRequestApp
{
    public static final String ROUTE = "route";
    public static  final String STOP =  "stop";

    public static void main (String []args) throws IOException
    {

        List<RouteRequest> routeRequestList = callRouteAPI("https://api-v3.mbta.com/routes?");
        Map<String, Integer> map = new HashMap();
        Map<String, List<RouteStop>> routeStopMap;

        //List<Map<String, List<RouteStop>>> allRouteStopList = new ArrayList<>();
        List<Map<String, List<String>>> allRouteStopList = new ArrayList<>();
        List<String> routeNameList = new ArrayList<>();
        int k = 0;
        for(RouteRequest routeRequest: routeRequestList)
        {
            System.out.println(routeRequest.getRouteRequestAttribute().getLong_name());
            routeNameList.add(routeRequest.getId());
            routeStopMap = callStopAPI("https://api-v3.mbta.com/stops?", routeRequest.getId());
            List<String> list = new ArrayList<>();
            Map<String, List<String>> routeStopAttributeNamesMap = new HashMap<>();
            for(int i = 0; i < routeStopMap.get(routeRequest.getId()).size(); i++)
            {
                list.add(routeStopMap.get(routeRequest.getId()).get(i).getRouteStopAttribute().getName());
            }
            routeStopAttributeNamesMap.put(routeRequest.getId(),list);
            //allRouteStopList.add(routeStopMap);
            allRouteStopList.add(routeStopAttributeNamesMap);
            map.put(routeRequest.getRouteRequestAttribute().getLong_name(),routeStopMap.get(routeRequest.getId()).size());
            System.out.println("Size of  " + routeRequest.getId() + " is " + routeStopMap.get(routeRequest.getId()).size());
            k++;
        }
        System.out.println("Size of routeNames is " + routeNameList.size());
        System.out.println("Size of allRouteStopList is " + allRouteStopList.size());
        Map.Entry maxValueEntry = maxUsingCollectionsMaxAndLambda(map);
        System.out.println("Subway route with most number of stops is " + maxValueEntry.getKey() + " " + "  and MaxValue is " + maxValueEntry.getValue());

        Map.Entry minValueEntry = minUsingCollectionsMaxAndLambda(map);
        System.out.println("Subway route with fewest number of stops is " + minValueEntry.getKey() + " " + "  and MinValue is " + minValueEntry.getValue());

        Map<String, Integer>  routeStopListMap = new HashMap<String, Integer>();
        for(int j=0; j<routeNameList.size(); j++)
        {
            List list = allRouteStopList.get(j).get(routeNameList.get(j));
            System.out.println(routeNameList.get(j));
        }

        List<String> redRouteStopList = allRouteStopList.get(0).get(routeNameList.get(0));
        List<String> mattapanRouteStopList = allRouteStopList.get(1).get(routeNameList.get(1));
        List<String> orangeRouteStopList = allRouteStopList.get(2).get(routeNameList.get(2));
        List<String> green_B_RouteStopList = allRouteStopList.get(3).get(routeNameList.get(3));
        List<String> green_C_RouteStopList = allRouteStopList.get(4).get(routeNameList.get(4));
        List<String> green_D_RouteStopList = allRouteStopList.get(5).get(routeNameList.get(5));
        List<String> green_E_RouteStopList = allRouteStopList.get(6).get(routeNameList.get(6));
        List<String> blueRouteStopList = allRouteStopList.get(7).get(routeNameList.get(7));

        Collections.sort(redRouteStopList);
        Collections.sort(mattapanRouteStopList);
        Collections.sort(orangeRouteStopList);
        Collections.sort(green_B_RouteStopList);
        Collections.sort(green_C_RouteStopList);
        Collections.sort(green_D_RouteStopList);
        Collections.sort(green_E_RouteStopList);
        Collections.sort(blueRouteStopList);

        System.out.println("redRouteStopList" + redRouteStopList);
        System.out.println("mattapanRouteStopList" + mattapanRouteStopList);
        System.out.println("orangeRouteStopList" + orangeRouteStopList);
        System.out.println("green_B_RouteStopList" + green_B_RouteStopList);
        System.out.println("green_C_RouteStopList" + green_C_RouteStopList);
        System.out.println("green_D_RouteStopList" + green_D_RouteStopList);
        System.out.println("green_E_RouteStopList" + green_E_RouteStopList);
        System.out.println("blueRouteStopList" + blueRouteStopList);

        Set<String> intersection = findIntersection(new HashSet<>(),
                redRouteStopList
                ,green_D_RouteStopList, green_E_RouteStopList);

        System.out.println(intersection);





        /*for(int i = 0; i <  allRouteStopList.size(); i++)
        {
            int count = routeStopListMap.containsKey(routeStop.getRouteStopAttribute().getName()) ? routeStopMap.get(routeStop.getRouteStopAttribute().getName()) : 0;
            routeStopMap.put(routeStop.getRouteStopAttribute().getName(), count+1);
            System.out.println("Route Stop Id  is " + routeStop.getId() +  " : Stop Name for  " + routeStop.getRouteStopAttribute().getName());
        }

        Map.Entry maxValue = maxUsingCollectionsMaxAndLambda(routeStopMap);
        System.out.println("Subway route with fewest number of stops is " + maxValue.getKey() + " " + "  and MinValue is " + maxValue.getValue());*/

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

        //URL url = new URL("https://api-v3.mbta.com/routes");
        URL url = new URL(urlString+query);

        //System.out.println("URL is " + url);

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        //con.setRequestMethod("POST");
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/vnd.api+json; utf-8");
        //con.setRequestProperty("Accept", "application/json");
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
    private static Map<String, List<RouteStop>> callStopAPI(String urlString, String stop) throws IOException
    {
        Map <String, List<RouteStop>> map = new HashMap<>();
        String query = String.format("filter[route]=%s",
                URLEncoder.encode(stop, "UTF-8"));

        //URL url = new URL("https://api-v3.mbta.com/routes");
        URL url = new URL(urlString+query);

        //System.out.println("URL is " + url);

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        //con.setRequestMethod("POST");
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/vnd.api+json; utf-8");
        //con.setRequestProperty("Accept", "application/json");
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
                //InputStream targetStream = new ByteArrayInputStream(responseLine.getBytes());
                //System.out.println(responseLine.trim() + "\n");

            }
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<RouteStops> mapType = new TypeReference<RouteStops>() {};
            routeStopsList = mapper.readValue(response.toString(), mapType);
            //System.out.println("Size is " + routeStopsList.getRouteStopList().size());

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
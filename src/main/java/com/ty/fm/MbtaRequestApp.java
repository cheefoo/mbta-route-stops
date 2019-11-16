package com.ty.fm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
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
public class MbtaRequestApp
{
    public static final String ROUTE = "route";
    public static  final String STOP =  "stop";

    public static void main (String []args) throws IOException
    {

        List<RouteRequest> routeRequestList = MbtaUtils.callRouteAPI("https://api-v3.mbta.com/routes?");
        Map<String, Integer> map = new HashMap();
        Map<String, List<RouteStop>> routeStopMap = new HashMap<>();

       // List<Map<String, List<String>>> allRouteStopList = new ArrayList<>();

        ListMultimap<String, String> allRoutesStopMap = ArrayListMultimap.create();

        List<String> routeNameList = new ArrayList<>();
        for(RouteRequest routeRequest: routeRequestList)
        {
            System.out.println(routeRequest.getRouteRequestAttribute().getLong_name());
            routeNameList.add(routeRequest.getId());
            routeStopMap = MbtaUtils.callStopAPI("https://api-v3.mbta.com/stops?", routeRequest.getId());
            for(int i = 0; i < routeStopMap.get(routeRequest.getId()).size(); i++)
            {
                allRoutesStopMap.put(routeRequest.getId(),routeStopMap.get(routeRequest.getId()).get(i).getRouteStopAttribute().getName());
            }
            map.put(routeRequest.getRouteRequestAttribute().getLong_name(),routeStopMap.get(routeRequest.getId()).size());
            System.out.println("Size of  " + routeRequest.getId() + " is " + routeStopMap.get(routeRequest.getId()).size());
        }
        System.out.println("Size of routeNames is " + routeNameList.size());
        System.out.println("RouteStop Map " + routeStopMap);
        Map.Entry maxValueEntry = MbtaUtils.maxUsingCollectionsMaxAndLambda(map);
        System.out.println("Subway route with most number of stops is " + maxValueEntry.getKey() + " " + "  and MaxValue is " + maxValueEntry.getValue());

        Map.Entry minValueEntry = MbtaUtils.minUsingCollectionsMaxAndLambda(map);
        System.out.println("Subway route with fewest number of stops is " + minValueEntry.getKey() + " " + "  and MinValue is " + minValueEntry.getValue());

        System.out.println(allRoutesStopMap);

        List<String> redRouteStopList = allRoutesStopMap.get("Red");
        List<String> mattapanRouteStopList = allRoutesStopMap.get("Mattapan");
        List<String> orangeRouteStopList = allRoutesStopMap.get("Orange");
        List<String> green_B_RouteStopList = allRoutesStopMap.get("Green-B");
        List<String> green_C_RouteStopList = allRoutesStopMap.get("Green-C");
        List<String> green_D_RouteStopList = allRoutesStopMap.get("Green-D");
        List<String> green_E_RouteStopList = allRoutesStopMap.get("Green-E");
        List<String> blueRouteStopList = allRoutesStopMap.get("Blue");




        //Red Stop
        Set<String> intersection = MbtaUtils.findIntersection(new HashSet<>(),
                redRouteStopList
                ,mattapanRouteStopList);

        System.out.println("Red-Mattapan : " + intersection);

        Set<String> intersection2 = MbtaUtils.findIntersection(new HashSet<>(),
                redRouteStopList
                ,orangeRouteStopList);

        System.out.println("Red-Orange : " + intersection2);

        Set<String> intersection3 = MbtaUtils.findIntersection(new HashSet<>(),
                redRouteStopList
                ,green_B_RouteStopList);

        System.out.println("Red-Green-B : " + intersection3);

        Set<String> intersection4 = MbtaUtils.findIntersection(new HashSet<>(),
                redRouteStopList
                ,green_C_RouteStopList);

        System.out.println("Red-Green-c : " + intersection4);

        Set<String> intersection6 = MbtaUtils.findIntersection(new HashSet<>(),
                redRouteStopList
                ,green_D_RouteStopList);

        System.out.println("Red-Green-D : " + intersection6);

        Set<String> intersection7 = MbtaUtils.findIntersection(new HashSet<>(),
                redRouteStopList
                ,green_E_RouteStopList);

        System.out.println("Red-Green-E : " + intersection7);

        Set<String> intersection8 = MbtaUtils.findIntersection(new HashSet<>(),
                redRouteStopList
                ,blueRouteStopList);

        System.out.println("Red-Blue : " + intersection8);

    }

}
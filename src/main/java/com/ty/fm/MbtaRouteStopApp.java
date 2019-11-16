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
        List<RouteRequest> routeRequestList = MbtaUtils.callRouteAPI("https://api-v3.mbta.com/routes?");
        Map<String, Integer> map = new HashMap();
        Map<String, List<RouteStop>> routeStopMap;

        MyMaps<String, String> allRoutesStopMap = new MyMaps<>();

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
        }
        Map.Entry maxValueEntry = MbtaUtils.maxUsingCollectionsMaxAndLambda(map);
        System.out.println("Subway route with most number of stops is " + maxValueEntry.getKey() + " " + "  and MaxValue is " + maxValueEntry.getValue());

        Map.Entry minValueEntry = MbtaUtils.minUsingCollectionsMaxAndLambda(map);
        System.out.println("Subway route with fewest number of stops is " + minValueEntry.getKey() + " " + "  and MinValue is " + minValueEntry.getValue());


        //System.out.println(allRoutesStopMap);
        /*
        Davis to Kendall -\> Redline
        Ashmont to Arlington -\> Redline, Greenline
         */
        System.out.println(args[0] + " "  + allRoutesStopMap.getKeyByValue(args[0]));
        System.out.println(args[1] + " "  + allRoutesStopMap.getKeyByValue(args[1]));

    }

}
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
import java.util.List;


//curl -X GET "https://api-v3.mbta.com/routes?page%5Boffset%5D=0&page%5Blimit%5D=10&sort=-long_name" -H "accept: application/vnd.api+json"
public class MbtaRequestApp
{
    public static final String ROUTE = "route";
    public static  final String STOP =  "stop";

    public static void main (String []args) throws IOException
    {

        List<RouteRequest> routeRequestList = callRouteAPI("https://api-v3.mbta.com/routes?");
        for(RouteRequest routeRequest: routeRequestList)
        {
            System.out.println(routeRequest.getRouteRequestAttribute().getLong_name());
            //System.out.println(routeRequest.getRouteRequestAttribute().getDirection_destinations().length);
        }
        List<RouteStop> routeStopList = callStopAPI("https://api-v3.mbta.com/stops?", "Orange");
        System.out.println("Size is " + routeStopList.size());

    }

    private static List<RouteRequest> callRouteAPI(String urlString) throws IOException
    {
        String query = String.format("filter[type]=%s",
                URLEncoder.encode("0,1", "UTF-8"));

        //URL url = new URL("https://api-v3.mbta.com/routes");
        URL url = new URL(urlString+query);

        System.out.println("URL is " + url);

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
                System.out.println(responseLine.trim() + "\n");

            }
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<RouteRequestList> mapType = new TypeReference<RouteRequestList>() {};
            routeRequestList = mapper.readValue(response.toString(), mapType);
            System.out.println("Size is " + routeRequestList.getRouteRequestList().size());

        }

        return routeRequestList.getRouteRequestList();
    }

    //curl -X GET "https://api-v3.mbta.com/stops?filter%5Broute%5D=Orange" -H "accept: application/vnd.api+json"
    private static List<RouteStop> callStopAPI(String urlString, String stop) throws IOException
    {
        String query = String.format("filter[route]=%s",
                URLEncoder.encode(stop, "UTF-8"));

        //URL url = new URL("https://api-v3.mbta.com/routes");
        URL url = new URL(urlString+query);

        System.out.println("URL is " + url);

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
                System.out.println(responseLine.trim() + "\n");

            }
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<RouteStops> mapType = new TypeReference<RouteStops>() {};
            routeStopsList = mapper.readValue(response.toString(), mapType);
            System.out.println("Size is " + routeStopsList.getRouteStopList().size());

        }

        return routeStopsList.getRouteStopList();
    }


}
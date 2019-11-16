Question 1 
 
Execute class MbtaRequestApp.main
This will print out 

There are two ways to filter results for subway-only routes. Think about the two options below and choose: 
 
https://api-v3.mbta.com/routes 
https://api-v3.mbta.com/routes?filter[type]=0,1 
 
Please document your decision and your reasons for it. 

I chose option 2 here  because it provides the routes that I require for my analyisis, 
it is pre-filtered for Light and heavy rail and I will not have to do any filtering when I receive the results from the API call. 
Option 1 will return al the routes including bus routes that are not relevant to our analysis.

 
Question 2   
 
Extend your program so it displays the following additional information. 
 
Execute class MbtaRequestApp.main

Subway route with most number of stops is Green Line B   and MaxValue is 24
Subway route with fewest number of stops is Mattapan Trolley   and MinValue is 8

Question 3 
 
Execute MbtaRouteStopApp.main

and pass the stops as arguments Ashmont, Arlington gives Mattapan and Green-E
Davis, Kendal gives Red

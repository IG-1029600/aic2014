# aic2014
User application guideline

The application user interface runs on : http://localhost:8080/aic2014/

The user interface for this application is divided into 4 main parts:
 - home page
 - user related query page
 - ad related query page
 - settings
 
On the home page you can find the participant's information.
 
On second tab, you can perform user related queries. Those are:
 - The most influental persons
 - people which are interested in a broad range of topics
 - people which are focused on topics

On third tab, you can perform add related queries. Those are:
 - Suggest concrete ads for certain user, base on existing or potential interests



Steps to run the code:
- import sourcecode to eclipse
— update maven dependencies
- install neo4j graph db (if you want to see the graph visualized)
- install mongodb and start server
- run the listener
- run the analyzer
- run the calculator
- start webapp with tomcat at localhost

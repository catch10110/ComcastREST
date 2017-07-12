This project was written in Eclipse and is to be ran through it.  The project requires Tomcat (8 or above) and uses an mock in memory database.

To run:

Clone the repository which will give you the entire workspace in eclipse.
Open the project as a Dynamic Web Project and choose Apache Tomcat as your target runtime.
Start the tomcat server through eclipse and localhost:8080/ad will start listening.
All of the rest endpoints start at localhost:8080/ad/ad

Sending a POST to localhost:8080/ad/ad in the form of:
{"partner_id":"1","duration":"555","ad_content":"Helper"}
will add that partner's ad to the in memory mock DB.

Sending a GET request to localhost:8080/ad/ad will return all unexpired ad campaigns.  Campaigns that are expired will be removed.

Sending a GET request to localhost:8080/ad/ad/{partner_id} will list the current active ad campaign by that partner's id.  Campaigns are stored in a queue and only one will be returned at a time.  Once they expire, the old one will be deleted and the next one will show.

Sending a GET request to to localhost:8080/ad/ad/{partner_id}?all=true will return all unexpired by that partner's id.

Sending a GET request to to localhost:8080/ad/ad/{partner_id}?all=false is the same as sending a GET request to localhost:8080/ad/ad/{partner_id}

ad/test contains the unit tests
ad/intg contains the intg tests

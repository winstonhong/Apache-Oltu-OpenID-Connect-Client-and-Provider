# Apache-Oltu-OpenID-Connect-Client-and-Provider

--------------------

The OpenID Connect client and identity provider are built based on open-source Apache Oltu source repository which only consists of OAuth 2.0 client demo application. 

The OpenID Connect client and identity provider demo application has been developed to illustrate the OpenID Connect authentication flow and provide instructions on how to develop a standalone OpenID Connect server using Apache Oltu library.

Installation
------------
+ Download the source repository from GitHub.
```
git clone https://github.com/winstonhong/Apache-Oltu-OpenID-Connect-Client-and-Provider
``` 
+ Build OAuth 2.0 and OpenID Connect library from Apache Oltu source code.
```
cd Apache-Oltu-OpenID-Connect-Client-and-Provider
mvn clean
mvn package
```
+ Run OpenID Connect client demo application
```
cd Apache-Oltu-OpenID-Connect-Client-and-Provider/demos/client-demo/
mvn jetty:run
```
+ Run OpenID Connect identity provider demo application
```
cd Apache-Oltu-OpenID-Connect-Client-and-Provider/demos/provider-demo/
mvn jetty:run
```

OpenID Connect Authentication Demo
------------
+ Access the link "http://localhost:8080" to launch the OpenID Connect authentication demo
+ Click **OpenID Connect Application**
+ Input **Requested Access Scope** : openid , <br>
input **End-User Authorization URL** : http://localhost:9001/auth/oauth2/authz , <br>
input **Token Endpoint** : http://localhost:9001/auth/oauth2/token , <br>
input **Client ID** : client_id , <br>
input **Client Secret** : client_secret , <br>
and then click **Get Authorization**
+ Input **Username/Password** : username/password , <br>
and then click **Login**
+ Click **Grant permission**
+ Click **Get Token**
+ Ensure that the message "ID Token is valid" is displayed within the **OpenId Connect** block,<br>
input **Resource URL** : http://localhost:9001/auth/oauth2/resource_server/resource_query ,<br>
select **queryParameter** from the drop-down list of **Authenticated Request Type**, <br>
and then click **Get Resource** to retrieve User Info.


References
-------
Apache Oltu https://oltu.apache.org/

OAuthh 2.0 and OpenID Connect libraries https://github.com/apache/oltu

Support
-------
OpenID Connect client and identity provider developed by [winstonhong](https://github.com/winstonhong) @ [inbaytech](https://github.com/inbaytech)

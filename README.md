# Apache-Oltu-OpenID-Connect-Client-and-Provider

--------------------

The OpenID Connect client and identity provider are built based on open-source Apache Oltu source repository which only consists of OAuth 2.0 client demo application. 

The OpenID Connect client and identity provider demo application has been developed to illustrate the OpenID Connect authentication flow and provide instructions on how to develop a standalone OpenID Connect server using Apache Oltu library.

Installation
------------
+ Download the source repository from GitHub.
```
git clone https://github.com/winstonhong/Apache-Oltu-OpenID-Connect-Client-and-Provider.git
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
+ Click **Get Authorization**
+ Input **Username/Password** : username/password , and then click **Login** 
+ Click **Grant permission**
+ Click **Get Token**
+ Click **Get Resource** to retrieve User Info.


References
-------
Apache Oltu https://oltu.apache.org/

OAuthh 2.0 and OpenID Connect libraries https://oauth.net/code/

Support
-------
OpenID Connect client and identity provider developed by [winstonhong](https://github.com/winstonhong) @ [inbaytech](https://github.com/inbaytech)

<h2>Simple Weather App</h2>
<br/>
<h4>V1:</h4>
<p>Simple weather app based on React FE and Java BE. The Java BE contacts 2 API site, one to obtain the longetude
and latitude of the city reuired and one to retrieve the weather report per city.</p>

<p>Communication to the remote APIs is over Spring synchronous RestTemplate.</p>

<h4>V2:</h4>
<p>App has been split up in to different modules: composite, location, conditions and shared. The shared module is used
for common models and constants.</p>

<p>Modules communicate with each other by way of the Spring RestTemplate.</p>

<h4>V3:</h4>
<p>Modules have been dockerized, communication between the modules is by means of Spring Reactor WebFlux, while 
communication with the remote APIs is using the synchronous RestTemplate and the asynchronous WebClient.</p>

<p>A scheduler is implemented at the composite module, which is triggered by the frontend. The scheduler receives the
city to be trended from the frontend, requests the city weather from the remote API´s and sends the result via
Kafka to the asynchronous MongoDB.</p>

<p>Maven has been replaced by Gradle.</p>

<h4>V4:</h4>
<p>Spring Security with JWT Authentication has been added to the composite module, which actually serves as a 
gateway for the app too. The frontend app will not continue till a valid user and password have been entered. 
After correct authentication, a JWT token is generated and with a refresh token, they are sent back to the user. 
Both tokens have expiration dates, the refersh token later than the JWT token. </p>

<p>When the JWT token expires, the backend will send an Unauthorized 401 error back to the frontend, which is 
intersepted by frontend. The frontend will request a new JWT token by way of the refresh token. If the refresh token
is accepted, both a new JWT and a new refresh token are generated and sent back. If the refresh token has also
expired, the backend will respond with a Forbidden 403 error and the frontend will show the log in screen again to
the user.</p>

<p>User credentials and refresh tokens are stored in separate databases.</p>

<h3>Summary:</h3>
<ul>
<li>Java 17</li>
<li>Spring Boot Web, Reactor, WebFlux, Security</li>
<li>React</li>
<li>Grommet UI, Bootstrap</li>
<li>Docker</li>
<li>Kafka</li>
</ul>



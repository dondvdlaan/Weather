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



<h3>Summary:</h3>
<ul>
<li>Java 17</li>
<li>Spring Boot Web, WebFlux</li>
<li>React</li>
<li>Grommet UI</li>
<li>Docker</li>
<li>Kafka</li>
</ul>



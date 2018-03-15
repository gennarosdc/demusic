# Demusic
Java web based application that implement a "democratic" jukebox.

Users can vote songs in a party. System compute the votes and sort dynamically playlisyt playing in background. The application is integrated with Spotify Api.

To use app follow this steps:<br />
Create an Spotify Developer account.<br />
Create a new App called Demusic<br />
Get User Id e Secret key by your own Spotify developer account and place their into com.demusic.controllers.Authorize.java class.<br />
Also edit app settings in Spotify Dev dashboard adding the redirect uri "http://localhost:8080/DemusicNew/RegisterBoss"<br />

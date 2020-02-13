# HALBO
Hamming Lego inspired Baseplate and Brick building online - HALBO

Created by Luuk Hamming en Jan-Egbert Hamming!


# Basic flow
The basic flow is as follows: A HALBOServer is started, this server starts listening to incoming connections. For each incoming connection a new (client) thread is started. With this new client, a protocol handshake is performed to check for compatibility between server and client.

Each client instance at the serverside will process all client requests like Login, Get worlds, Cities, Move, Teleport etc.  

As a client you connect to the server, login, get information about Worlds, Continents, Cities, Baseplates and other users. Currently there is only a test-client with some Frames and Panels to test all the functionality. 

# Datamodel
There are two datamodels : The Server internal DataModel and the common, Data Transfer Object (DTO) based model.
The server internal model is the object oriented view of the data, a World has a list of Contintents, a Continent has a list of Cities, a City has a list of Baseplates, etc. 
The DTO datamodel has been specifically created to be able to send parts of data quickly to the client. So if you request all the worlds, you get multiple network messages, each message containing only the world specific data and only the IDs of the Continents. If you want details about each continent, you request the server for Continent details and get a ContinentDto object as a response. 

# Server
The HALBOServer class extends an (Abstract) Server, we made this, so that in the future, we could have multiple servers running parts of the HALBO world (load balancing). The HALBOServer only listens to connection requests and starts new ClientConnections per connection. 

# Protocol
There is a Protocol class which contains all the commands/actions a client can request to the server. Every Client Connection creates a new ProtocolHandler to handle the protocol (first do a handshake, then a login. After the login you are allowed to do the rest of actions). Checkout the register funtions in the ProtocolHandler.

# GameController and GameState
The HALBO Server has one instance of the GameController, this controller can receive Actions from all the conntected clients. These Actions are executed continuously. These Action classes use the Command Pattern. 
The GameController keeps track of everything in the GameState class. This GameState holds stuff like all connected users and Locations of all these users etc. 

Each Client Connection registers itself as a GameStateListener, so get updates to the GameState. So on each update to the GameState (as a result of an Action from a User) everybody gets a signal (Event) about what updated. While dealing with this event, each Client can decide if this update is applicable for the connected User. For instance: When a user(Bart) is moving on a Baseplate in City "FOO", a connected User (John) on a Baseplate in City "BAR" is not interested in these events, so the Client Connection does send the update to the client window of John.

When a user connects, the full GameState is sent to this users (still taking into account if parts of this state are applicable or not.. see sendFullGameState()

# Client
The Client is work-to-be-started. Luuk and Jan-Egbert are mostly working with/at, or skilled with, Server development. We want to tryout stuff with OpenGL, for instance lwJGL. 
Still looking for somebody that wants to design a cool 3D Client!!! 


# Auction

##Prerequisite
To use application, use JDK 11

##Build application
On root directory, launch mvn clean package

Launch java -jar target/auction-1.0-SNAPSHOT.jar server auction.yaml
##API
###Create Auction House
POST http://localhost:9000/api/b2b/auctions-house

Example of payload:

{"name":"auction-house"}

###Get all Auction House
GET http://localhost:9000/api/b2b/auctions-house
###Create auction
POST http://localhost:9000/api/b2b/auctions-house/{idAuctionHouse}/auctions

Example of payload:
{"name":"ps4",
"startingTime":"2021-05-16 17:51",
"endingTime": "2021-05-16 18:50",
"price":"30.0"}
###GET all auctions of auction house
GET http://localhost:9000/api/b2b/auctions-house/{idAuctionHouse}/auctions
###Bid auction
PUT http://localhost:9000/api/b2b/auctions-house/{idAuctionHouse}/auctions/{idAuction}

Example of payload:
{"name":"vinyles",
"startingTime":"2021-05-15 10:51",
"endingTime": "2021-05-15 21:50",
"price":"140.0"}
### Show winner
GET http://localhost:9000/api/b2b/auctions/{idAuction}/user

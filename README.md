In this readme, there will be some strategies followed to fix problems:

**Favourites:**

Although the API provided has endpoints to manage favourites, I implemented a way for them to be stored locally because, for some reason, the app could be released on the Play Store, the favourites of one person would be the favourites of the second person, due to the fact that the favourite is tied to the API key.

To save the favourites into the database, I created a different mapper class (CatDBFavouriteInformation) because, although I already had a class that could work (CatDBInformation), in Room that same class cannot be used for two different tables since it was already used to store the cat breed list into the database. 
<br>Although it's a different class, the CatDBFavouriteInformation has inside an instance of CatDBInformation with the Embedded annotation to replicate the fields of CatDBInformation.

When an item is set as favourite, I save all the item information on a different database to make sure that, if weren't able to fetch the item detail from the API, we show the last information saved on the database.


**Cat List and Search List**

1 -> The Paging3 Lib gives us the possibility to use the method **cacheIn()** to cache the list on the viewModel to not fetch the item list everytime. <br>I decided not to use this approach because, if I removed a favourite in the Favourite screen, the change wouldn't be replicated on the Cat List screen.  

2 -> When changing between Favourite and Cat List tabs, the RemoteMediator was always being reseted because what was state in the point 1. This was making the API load items from the first page. <br>Since I wasn't using the **cacheIn()** because of the reason above, I implementented a condition to skip the reload if we have items in the database and saving the last page that was last fechted on DataStore.
With this, I could continue loading items from the last position.

3 -> Picking the statement 2, I found a new problem. Now, everytime I reopened the app, it was not reloading the list. The fix for this problem was saving a control flag on DataStore that is changed everytime we load the app. If the RemoteMediator sees that flag has its value as true, it knows that the RemoteMediator has to be resetted.

4 -> On this list, to show if an item is favourite or not, the favourite list is fetched from the database and check if the ID's from both sides matches. If yes, a control flag is changed to show the correct information on screen.

5 -> Since the endpoint used to get the Breed List did not returned the complete image url, I had to got to the endpoint of the images to get the image url to use. <br>However, after some calls, I noticed that the images returned are always so I implemented a table that saves the imageId and the url of the image to not always have to call that api to get the image and to speed up the list load.
<After calling the breed endpoint, it will match the imageId from that endpoint with the one in the database. If it matches, uses the database one. If not, calls the image endpoint and saves the new url in the database.

6 -> The search for breeds uses the endpoint /search or the list of items already in the database in case the api fails for some reason. The search mechanism has a debouce system in order to save clicks to the user.


**Cat Details**

Since there is only one Screen for details, there can be two different sources to get the information to show. <br>To not create two separate screens, when clicking an item in the list, a param is sent to the detail screen to be passed when calling the method to load the information. <br>In case it fails, it just knows from which local table has to load the information.



**To setup API Key for testing**

Go to file CatInformationApi and change the content of the variable API_KEY for your own API Key

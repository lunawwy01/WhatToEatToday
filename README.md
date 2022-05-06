# My Personal Project

## *What to eat today* 🍚🥢


**- What will the application do? 🍕** <br>
Don't know what to eat and don't want to cook? <br>
This application will help you **randomly choose your food today!** 
It will randomly pick one restaurant from your favorite restaurants list, 
solving your time struggling with what to eat.

**- Who will use it? 🥦** <br>
This program is helpful for anyone who does not know what to eat. 
No matter you are an excellent chef who wants to try a new restaurant or a student who does not cook and is tired of 
deciding which restaurant to eat, this program will help you save your time and make a good decision.

**- Why is this project of interest to me? 🍤** <br>
This project is especially interesting to me because I had the same problem of choosing what to eat. 
I live in residence and barely cook anything. I already tried most of the restaurants around campus and usually spent 
half an hour deciding what to eat. I hope this application can help me save time, and help anyone who has the same 
problem.

**- User Stories** <br>
* As a user, I want to be able to add a restaurant to my restaurant list with a category
* As a user, I want to be able to view the list of restaurants on my list
* As a user, I want to be able to delete a restaurant from my food list
* As a user, I want this program will randomly choose one restaurant from my food list
* As a user, I want to be able to save my restaurant list to file
* As a user, I want to be able to load my restaurant list from file

**- Phase 4: Task 2** <br>
Mon Mar 28 19:35:48 PDT 2022 <br>
Added restaurant Ryuu <br>
Mon Mar 28 19:35:55 PDT 2022 <br>
Added restaurant Kosoo <br>
Mon Mar 28 19:35:59 PDT 2022 <br>
Added restaurant Bluechip <br>
Mon Mar 28 19:36:02 PDT 2022 <br>
Removed restaurant: Bluechip <br>

**- Phase 4: Task 3** <br>
To refactor my project, I will implement the observer pattern. When I did the GUI, 
I use a restaurantList to track all the restaurants in restaurantListModel for 
JsonWriter and JsonReader. For the observer pattern, I will make restaurantListModel 
extends observable, and restaurantList implement observers. When anything happens in 
the GUI window and restaurantListModel changes, the restaurantList will get notified 
and updated, so that I do not need to use code to perform that manually. 



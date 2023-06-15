# My Personal Project

## An Advanced Wardrobe Manager

**This program will:**
- Allow users to add and remove clothing from their wardrobe
- Categorize clothing based on type and conditions to wear them in
- Create randomized outfits based on various weather conditions
- Display the entire wardrobe as individual, interactable objects 
- Save the user's favourite outfits 

This application will be used by anyone who wants to keep track of what
clothing they have. This is of personal interest to me, because I often
forget about clothing that I haven't worn in a while. Furthermore,
this program would make it easier for people to experiment with outfits, or
just picking out an outfit for the day. I have met several people
who take a very long time deciding what to wear, and more often
than not, they end up wearing the same thing every day. This 
program would be ideal for those types of people, since it solves both of those
problems.


##User Stories

- As a user, I want to be able to add multiple pieces of 
clothing to my wardrobe
- As a user, I want to be able to categorize clothing based on 
their type
- As a user, I want to be able to remove clothing from my wardrobe
- As a user, I want to be able to display all my clothing, and be
able to interact with each of them individually
- As a user, I want to be able to save my wardrobe to a file
- As a user, I want to be able to load my wardrobe from a file

##Phase 4: Task 2

Option 1 was chosen. I made the clothing class robust, by checking
that when a new clothing piece is created, it is part of the five
accepted categories (shirt, sweater, jacket, pants, or shoes). Only
the constructor method was used.

##Phase 4: Task 3

I would make main the only runnable class, and have it give the user
the option of running either the gui or the console application. I would
also maybe make an abstract "app" class, and have WardrobeApp and WardrobeGUI
implement it to avoid the coupling.
# Overview
Community-based REST API / web app that allows users to submit and view album reviews

## Registration and authentication 
* Join the community with a unique username and password via a registration page
* Log into the community with their username and password

## Submit review 
* Submit an album review via a form with the following fields:  
  * Artist
  * Album
  * Album release year
  * Score (number between 1-100 inclusive)
  * Review (optional, and text-area instead of text-box)
* Successful submission returns a view of the album review object
* _**Note**: Artists and albums will only be populated upon album review submission, so no need for any form submission other than album review_

## View reviews
* View a list of artists, their albums, release years, average scores, and number of reviews via pagination
* Clicking on an album name takes users to a page listing the actual reviews, sortable by score
* Search reviews by:
  * Artist
  * Album name
  * Album release year
  * Artist and album name
* Filter albums by average score

## Personal profile editing
* Field for favorite artist name 

## View profiles
* View user's submitted reviews
* View user's favorite artist, linking to filtered album review list by artist

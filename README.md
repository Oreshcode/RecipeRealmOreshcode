<h1 align="center"> Recipe Realm Oreshcode </h1> <br>

<p align="center">
  A small application that allows users to Register and create cooking recipes and share them with others. Developed by Iliyan Oreshkov (Oreshcode)
</p>


## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Testing](#testing)
- [API](#API)
- [Acknowledgements](#acknowledgements)




## Introduction

Welcome to Recipe Realm Oreshcode. The goal of this application is to allow people to create accounts and then add their recipes, as well as sharing them with others.

## Features
* User Registration
* User Login
* User Authentication
* Creation/Deletion of a Recipe
* Adding/Removing an Existing Recipe from/to a Favorites list
* Searching through a list of recipes by a Keyword and Category
* MySQL Database for all the information
* Adding/Removing of Comments
* Liking/Disliking Comments and Recipes
* Security and Password Encoding
  

## Requirements
The application can be run locally.


### MySQL
A running instance of [MySQL Service](https://dev.mysql.com/downloads/installer/) is required to create the database and make the connection locally.


### Local
* [Java 21 SDK](https://www.oracle.com/java/technologies/downloads/?er=221886#java21)
* [Maven](https://maven.apache.org/download.cgi)


## Quick Start
Make sure the JWT Verification Key URL is configured, then you can run the server on your local machine.

Application will run by default on port `8080`

Configure the datasource by changing `application.yml` file in resources. The default username and password are set to "root", change that to what the local MySQL Login information is.


## Testing
There is full implementation of UnitTests with Mockito for the following Services:

* CommentService
* FavoriteService
* RecipeService
* UserService

## API
For Documentation I have used Swagger API.

## Acknowledgements
I want to thank 24/7 Jazz Music on YouTube for helping me focus and Google for allowing access to so much free information and study material.

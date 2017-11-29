# README

Senior Design API:

* Ruby version: ruby 2.4.2p198 (2017-09-14 revision 59899) [x64-mingw32]

* System dependencies: In order to run this program, you must have:
	-ruby
	-postgresql

* Configuration:
	-install rails 
	-in command line type "bundle install" to install the gems needed for the project

* Database creation: 
	-you must create a database in postgres named postgres2

* Database initialization
	-in the command line with your project folder open type "rails db:migrate" to initialize the database

* How to run and test
	-httpie is recommended for testing
	-to install you must have the latest version of python with pip so you can type "pip install httpie"
	-with httpie you can test all of the requests to the api using the rails server
	-to start, have your project open in the command line and type "rails server" to start the server on your localhost
	-then open up another command line and start testing with httpie with requests to the server, for example "http GET :3000/users"


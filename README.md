# SYNOPSIS
Spring Rest Books Project

tags: REST, JayWay,rest-assured

This project REST is created using Spring Boot and JUnit with
rest-assured for the test of the differents requests exist in
the project.

## The application
For you can run this project, is neccessary use a tools what help us 
to test the differents request. For this cause, I recommend the extension for
Chrome call Postman. For apply Postman to Chrome, is neccessary search in the 
market application of Chrome.

## Using the REST Service
*Result: Shows a list of books in JSON format.
                                                                                       
      URL: http://localhost:8080/SpringRestBooks/list
      
*Result: Shows a list of books in JSON format with the name of author for example "Gabriel"
                                                                                          
      URL: http://localhost:8080/SpringRestBooks/listbyauthor/Gabriel
      
*Result: Shows the details of the book with id:1 in * JSON format.

      URL: http://localhost:8080/SpringRestBooks/get/1
                                                                                      
*Result: Delete the book with the id:1.

      URL: http://localhost:8080/SpringRestBooks/delete/1
                                                                                          
*Result: Insert a book in the list.

      URL: http://localhost:8080/SpringRestBooks/create
                                                                                           
                                                                                           
*Method: POST
      Header: Content-Type:application/json

      Body: {"id":"1","name":"Book 1","editorial":"Editorial 1", "author": {"name":"Gustavo", "lastn":"Fernandez"}}
                                                                                           
*Result: Update a book in the list.
      URL: http://localhost:8080/SpringRestBooks/update/
      
      
* Sonar check:

http://sonarchile.nisum.com/dashboard/index/20142

* Gitlab project:

http://gitlab.nisumlatam.com/rduran/SpringRestBooks

* Repository in Gitlab

git@gitlab.nisumlatam.com:rduran/SpringRestBooks.git

* Jenkins Project:

http://jenkins-cl.nisum.com/job/SpringRestBooks/
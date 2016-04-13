# SYNOPSIS
Spring Rest Books Project

Java Version for run the Project : 1.7

tags: REST, JayWay,rest-assured,Spring Boot

This project REST is created using Spring Boot and JUnit with
rest-assured for the test of the different requests exist in
the project.

## Important! 
If you have issues with the port of Spring Boot, you can change the port
in the "application.properties" file what you will find in the resources folder.

## The application
For you can run this project, is necessary use a tools what help us 
to test the different request. For this cause, I recommend the extension for
Chrome call Postman. For apply Postman to Chrome, is necessary search in the 
market application of Chrome.

## Using the REST Service
*Result: Shows a list of books in JSON format.
                                                                                       
      URL: http://localhost:8080/book/list
      
*Result: Shows a list of books in JSON format with the name of author for example "Gabriel"
                                                                                          
      URL: http://localhost:8080/book/listbyauthor/Gabriel
      
*Result: Shows the details of the book with id:1 in * JSON format.

      URL: http://localhost:8080/book/get/1
                                                                                      
*Result: Delete the book with the id:1.

      URL: http://localhost:8080/book/delete/1
                                                                                          
*Result: Insert a book in the list.

      URL: http://localhost:8080/book/create
                                                                                           
                                                                                           
*Method: POST
      Header: Content-Type:application/json

      Body: {"id":"1","name":"Book 1","editorial":"Editorial 1", "author": {"name":"Gustavo", "lastn":"Fernandez"}}
                                                                                           
*Result: Update a book in the list.
      URL: http://localhost:8080/book/update/
      
## Run the project
 For run the project, write the command "gradle bootRun" in the terminal.
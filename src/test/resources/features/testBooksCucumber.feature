Feature: Testing Book Controller 

Scenario: Get a book by Id 
    Given have a list of books 
    And the user want get a book with id ="1" 
    When user send the request Get with "http://localhost:8080/book/get/" 
    Then returns a json object: 
        """
        {"id":"2","name":"Libro de la Selva","editorial":"Editorial 1","author":{"name":"Pedro","lastName":"Marquez"}}
        """
        
Scenario: Fail get a book by Id 
    Given have a list of books 
    And the user want get a book with idBook ="6" 
    When user send request "http://localhost:8080/book/get/" 
    Then returns a HttpStatus NOT_FOUND 
    
Scenario: Create a book 
    Given have a list of books 
    And the user want create a book 
    When user send request to create a book "http://localhost:8080/book/create" 
    Then returns a HttpStatus CREATE 
    
Scenario: Fail create a book 
    Given have a list of books 
    And the user wish create a book 
    When user send a request to create a book "http://localhost:8080/book/create" 
    Then returns a HttpStatus NOT_ACCEPTABLE 
    
Scenario: Get list of books 
    Given have a list of books 
    When user send the request "http://localhost:8080/book/list" 
    Then returns a list of books 
    
Scenario: Get Empty list of books 
    Given have a list empty of books 
    When user send the next request "http://localhost:8080/book/list" 
    Then returns a list empty of books 

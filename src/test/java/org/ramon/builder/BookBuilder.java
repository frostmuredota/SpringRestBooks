package org.ramon.builder;

import org.ramon.model.Book;

public class BookBuilder {
    
    private String id = "some id";
    private String name;
    private String editorial;
    private AuthorBuilder authorBuilder;
    
    public BookBuilder withId(String id){
        this.id=id;
        return this;
    }
    
    public BookBuilder withName(String name){
        this.name=name;
        return this;
    }
    public BookBuilder withEditorial(String editorial){
        this.editorial=editorial;
        return this;
    }
    public AuthorBuilder withAuthor(){
        authorBuilder = new AuthorBuilder(this);
        return authorBuilder;
    }
    public Book build(){
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setEditorial(editorial);
        if(authorBuilder!=null){
            book.setAuthor(authorBuilder.build());   
        }
        return book;
    }
    

}

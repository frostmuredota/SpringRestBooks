package org.ramon.model;

public class Book {
    private String id;
    private String name;
    private String editorial;
    private Author author;

    @java.beans.ConstructorProperties({"id", "name", "editorial", "author"})
    public Book(String id, String name, String editorial, Author author) {
        this.id = id;
        this.name = name;
        this.editorial = editorial;
        this.author = author;
    }

    public Book() {
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEditorial() {
        return this.editorial;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Book;
    }

    public String toString() {
        return "org.ramon.model.Book(id=" + this.getId() + ", name=" + this.getName() + ", editorial=" + this.getEditorial() + ", author=" + this.getAuthor() + ")";
    }
}

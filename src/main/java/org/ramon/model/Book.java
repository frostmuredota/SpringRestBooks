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

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Book)) return false;
        final Book other = (Book) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$editorial = this.getEditorial();
        final Object other$editorial = other.getEditorial();
        if (this$editorial == null ? other$editorial != null : !this$editorial.equals(other$editorial)) return false;
        final Object this$author = this.getAuthor();
        final Object other$author = other.getAuthor();
        if (this$author == null ? other$author != null : !this$author.equals(other$author)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 0 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 0 : $name.hashCode());
        final Object $editorial = this.getEditorial();
        result = result * PRIME + ($editorial == null ? 0 : $editorial.hashCode());
        final Object $author = this.getAuthor();
        result = result * PRIME + ($author == null ? 0 : $author.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Book;
    }

    public String toString() {
        return "org.ramon.model.Book(id=" + this.getId() + ", name=" + this.getName() + ", editorial=" + this.getEditorial() + ", author=" + this.getAuthor() + ")";
    }
}

package org.ramon.model;

public class Author {
    private String name;
    private String lastName;

    @java.beans.ConstructorProperties({"name", "lastName"})
    public Author(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public Author() {
    }

    public String getName() {
        return this.name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Author;
    }
}

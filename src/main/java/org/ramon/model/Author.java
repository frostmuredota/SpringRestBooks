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

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Author)) return false;
        final Author other = (Author) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 0 : $name.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 0 : $lastName.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Author;
    }
}

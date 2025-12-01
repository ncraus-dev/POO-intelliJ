package etsisi.upm.Model;

public abstract class User{
    protected String name;
    protected String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

  /*  @Override
    public String toString() {
        return String.format("{class:%s, name:'%s', email:'%s'}",
                getClass().getSimpleName(), name, email);
    }*/
}

package by.duzh.jse.io.etc;

import java.io.Serializable;

public class User implements Serializable {
    public int id;
    public String name;
    //public String password;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

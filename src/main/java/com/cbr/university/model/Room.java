package com.cbr.university.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int id;

    @Column(name = "room_name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", name=" + name + "]";
    }
}

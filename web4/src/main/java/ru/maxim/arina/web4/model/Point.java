package ru.maxim.arina.web4.model;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name= "points")
public class Point implements Serializable {



    public Point() {
    }

    public Point(int x, Double y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }


    public Point(int x, Double y, int r, String result) {
        this(x, y, r);
        this.result = result;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int x;

    @Column
    private Double y;

    @Column
    private int r;

    @Column
    private String result;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}


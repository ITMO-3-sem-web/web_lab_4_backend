package ru.maxim.arina.web4.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.maxim.arina.web4.model.Point;
import ru.maxim.arina.web4.service.PointService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/")
public class PointsController {


    @Autowired
    private PointService pointService;


    @GetMapping("points")
    public ResponseEntity<List<Point>> getPoints()
    {
        List list = pointService.findAll();
        Collections.reverse(list);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("addpoint")
    public ResponseEntity<?> addorUpdateExpense(@RequestParam("x") @Validated int x, @RequestParam("y") double y, @RequestParam("r") int r) {
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setR(r);
        System.out.println("x = " + x + "; y = " + y + ", r= " + r);
        if(validate(point)) {
            System.out.println("валидация пройдена");
            checkResult(point);
            System.out.println(point.getResult());
            pointService.addPoint(point);
//        this.dotRepository.save(dot);
            return new ResponseEntity(point, HttpStatus.OK);
        }
        return new ResponseEntity(point, HttpStatus.OK);
    }


    @GetMapping("clear")
    public ResponseEntity<?> deletePost() {
        pointService.deleteAll();
//        this.dotRepository.deleteAll();
        return new ResponseEntity("", HttpStatus.OK);
    }

    private boolean validate(Point point){
        try {
            double x = point.getX();
            double y = point.getY();
            double r = point.getR();

            if (!(x % 1 == 0 && x <= 3 && x >= -5)) {
                System.out.println("1");
                return false; //error
            } else if (!(y < 5 && y > -3)){
                System.out.println("2");
                return false; //error
            } else if(!( r % 1 == 0 && r >= -5 && r <= 3)){
                System.out.println("3");
                return false; //error
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();

            return false; //error
        }
    }

    private void checkResult(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        if (x >= 0) {
            if (y <= 0) {
                if (y >= -r/2 + x) {
                    point.setResult("YES");
                }
            }
            else {
                if (x <= r && y <= r) {
                    point.setResult("YES");
                }
            }
        } else {
            if (y <= 0) {
                if (  (Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(r, 2)) {
                    point.setResult("YES");
                }
            }
        }
        if (point.getResult() == null) {
            point.setResult("NO");
        }
    }

}


package ru.maxim.arina.web4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxim.arina.web4.model.Point;
import ru.maxim.arina.web4.repository.PointRepository;


import java.util.List;

@Service
public class PointService {
    @Autowired
    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Transactional
    public void addPoint(Point p) {
        pointRepository.save(p);
    }

    @Transactional
    public void deleteAll(){ pointRepository.deleteAll(); }

    @Transactional
    public List<Point> findAll(){
        return pointRepository.findAll();
    }

}

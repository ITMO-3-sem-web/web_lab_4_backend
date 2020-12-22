package ru.maxim.arina.web4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxim.arina.web4.model.Point;

public interface PointRepository extends JpaRepository<Point, Integer> {

}
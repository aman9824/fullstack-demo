package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepo extends JpaRepository<model, String> {
    List<model> findAll();

    void deleteAll();
}

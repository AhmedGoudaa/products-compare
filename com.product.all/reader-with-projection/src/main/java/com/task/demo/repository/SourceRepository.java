package com.task.demo.repository;

import com.task.demo.entity.Source;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SourceRepository extends JpaRepository<Source, Integer> {
}

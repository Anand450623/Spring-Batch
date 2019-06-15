package com.example.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.batch.entity.Batch;

public interface BatchRepository extends JpaRepository<Batch, String>
{

}

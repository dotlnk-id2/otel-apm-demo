package com.example.apm.matedata;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.opentelemetry.instrumentation.annotations.WithSpan;

public interface TestRepository extends JpaRepository<Test, Long> {

    @WithSpan
    Optional<List<Test>> findByName(String name);

    // Custom Query
    // @Query("SELECT id,name,create_time FROM test WHERE create_time > :createTime")
    // List<Test> findByPublishedDateAfter(@Param("date") LocalDate createTime);
}

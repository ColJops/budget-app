package com.budget.application.repository;

import com.budget.application.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findById(Long Long);

    Tag save(Tag tag);

    List<Tag> findAll();

    List<Tag> findByName(String name);

}

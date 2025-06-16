package com.dev.hari.database_jdbc.repositories;

import com.dev.hari.database_jdbc.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Iterable<Author> findAuthorByAgeLessThan(int age);
}

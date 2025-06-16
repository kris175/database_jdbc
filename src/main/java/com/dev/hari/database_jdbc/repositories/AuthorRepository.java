package com.dev.hari.database_jdbc.repositories;

import com.dev.hari.database_jdbc.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}

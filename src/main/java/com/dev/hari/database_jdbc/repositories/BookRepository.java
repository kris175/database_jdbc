package com.dev.hari.database_jdbc.repositories;

import com.dev.hari.database_jdbc.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, String> {
}

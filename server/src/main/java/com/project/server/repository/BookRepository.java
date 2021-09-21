package com.project.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.server.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {   //SpringData JPa，免去crud

}

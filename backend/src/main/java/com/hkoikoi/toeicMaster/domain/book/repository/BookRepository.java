package com.hkoikoi.toeicMaster.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hkoikoi.toeicMaster.domain.book.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

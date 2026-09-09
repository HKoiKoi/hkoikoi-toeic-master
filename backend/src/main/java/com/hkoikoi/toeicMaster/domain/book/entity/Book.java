package com.hkoikoi.toeicMaster.domain.book.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import com.hkoikoi.toeicMaster.domain.book.enums.BookType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@DynamicUpdate
@Table(name = "book")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE book SET is_deleted = true WHERE book_id = ?")
public class Book {

	@Id
	@Column(name = "book_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false, length = 100)
	String title;

	@Column(length = 50)
	String publisher;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	BookType bookType;

	@Column(name = "is_active", nullable = false)
	boolean isActive;

	@Column(name = "is_deleted", nullable = false)
	boolean isDeleted;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	LocalDateTime updatedAt;

	public static Book create(String title, String publisher, BookType bookType) {

		Book book = new Book();

		book.title = title;
		book.publisher = publisher;
		book.bookType = bookType;
		book.isActive = true;
		book.isDeleted = false;

		return book;
	}

	public void delete() {
		this.isDeleted = true;
	}
}

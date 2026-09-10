package com.hkoikoi.toeicMaster.domain.category.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import com.hkoikoi.toeicMaster.domain.book.entity.Book;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@DynamicUpdate
@Table(name = "category")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE category SET is_deleted = true WHERE category_id = ?")
public class Category {

	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	Book book;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	Category parent;

	@Column(nullable = false, length = 100)
	String name;

	@Column(nullable = false)
	Integer depth;

	@Column(name = "display_order", nullable = false)
	Integer displayOrder;

	@Column(name = "is_deleted", nullable = false)
	boolean isDeleted;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	LocalDateTime updatedAt;

	public static Category create(Book book, Category parent, String name, Integer depth, Integer displayOrder) {

		Category category = new Category();

		category.book = book;
		category.parent = parent;
		category.name = name;
		category.depth = depth;
		category.displayOrder = displayOrder;
		category.isDeleted = false;

		return category;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updatePosition(Category parent, Integer depth, Integer displayOrder) {
		this.parent = parent;
		this.depth = depth;
		this.displayOrder = displayOrder;
	}

	public void delete() {
		this.isDeleted = true;
	}
}

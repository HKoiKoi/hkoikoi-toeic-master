package com.hkoikoi.toeicMaster.domain.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkoikoi.toeicMaster.domain.book.entity.Book;
import com.hkoikoi.toeicMaster.domain.book.repository.BookRepository;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryCreateRequest;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryCreateResponse;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryNameUpdateRequest;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryPositionUpdateRequest;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryTreeResponse;
import com.hkoikoi.toeicMaster.domain.category.entity.Category;
import com.hkoikoi.toeicMaster.domain.category.repository.CategoryQueryRepository;
import com.hkoikoi.toeicMaster.domain.category.repository.CategoryRepository;
import com.hkoikoi.toeicMaster.global.exception.BusinessException;
import com.hkoikoi.toeicMaster.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	private final CategoryQueryRepository categoryQueryRepository;

	@Transactional(readOnly = true)
	public List<CategoryTreeResponse> getCategoryTree(Long bookId) {

		List<Category> categories = categoryRepository.findAllByBookIdOrderByDepthAscDisplayOrderAsc(bookId);

		Map<Long, CategoryTreeResponse> dtoMap = new HashMap<>();
		List<CategoryTreeResponse> rootNodes = new ArrayList<>();

		for (Category category : categories) {

			CategoryTreeResponse dto = CategoryTreeResponse.from(category);
			dtoMap.put(category.getId(), dto);

			if (category.getParent() == null) {
				rootNodes.add(dto);
			} else {

				CategoryTreeResponse parentDto = dtoMap.get(category.getParent().getId());

				if (parentDto != null) {
					parentDto.children().add(dto);
				}
			}
		}

		return rootNodes;
	}

	public CategoryCreateResponse createCategory(Long bookId, CategoryCreateRequest request) {

		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOK));

		Category parent = null;
		int depth = 1;

		if (request.parentId() != null) {
			parent = categoryRepository.findById(request.parentId())
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));

			depth = parent.getDepth() + 1;
		}

		Category category = Category.create(
			book,
			parent,
			request.name(),
			depth,
			request.displayOrder()
		);

		Category savedCategory = categoryRepository.save(category);

		return CategoryCreateResponse.from(savedCategory);
	}

	public void updateCategoryName(Long bookId, Long categoryId, CategoryNameUpdateRequest request) {

		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));

		if (!category.getBook().getId().equals(bookId)) {
			throw new BusinessException(ErrorCode.MISMATCH_CATEGORY_BOOK);
		}

		category.updateName(request.name());
	}

	public void updateCategoryPosition(Long bookId, Long categoryId, CategoryPositionUpdateRequest request) {

		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));

		if (!category.getBook().getId().equals(bookId)) {
			throw new BusinessException(ErrorCode.MISMATCH_CATEGORY_BOOK);
		}

		Category parent = null;
		int depth = 1;

		if (request.parentId() != null) {

			if (categoryId.equals(request.parentId())) {
				throw new BusinessException(ErrorCode.INVALID_CATEGORY_PARENT);
			}

			parent = categoryRepository.findById(request.parentId())
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));

			depth = parent.getDepth() + 1;
		}

		category.updatePosition(parent, depth, request.displayOrder());
	}

	public void deleteCategory(Long bookId, Long categoryId) {

		List<Category> allCategories = categoryRepository.findAllByBookIdOrderByDepthAscDisplayOrderAsc(bookId);

		Map<Long, List<Long>> childrenMap = new HashMap<>();
		boolean isCategoryExist = false;

		for (Category category : allCategories) {

			if (category.getId().equals(categoryId)) {
				isCategoryExist = true;
			}

			if (category.getParent() != null) {
				childrenMap.computeIfAbsent(category.getParent().getId(), k -> new ArrayList<>()).add(category.getId());
			}
		}

		if (!isCategoryExist) {
			throw new BusinessException(ErrorCode.NOT_FOUND_CATEGORY);
		}

		List<Long> idsToDelete = new ArrayList<>();
		Queue<Long> queue = new LinkedList<>();
		queue.add(categoryId);

		while (!queue.isEmpty()) {

			Long currentId = queue.poll();
			idsToDelete.add(currentId);

			List<Long> children = childrenMap.getOrDefault(currentId, Collections.emptyList());
			queue.addAll(children);
		}

		categoryQueryRepository.deleteAllById(idsToDelete);
	}
}

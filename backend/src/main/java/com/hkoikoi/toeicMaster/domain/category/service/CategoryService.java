package com.hkoikoi.toeicMaster.domain.category.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkoikoi.toeicMaster.domain.book.entity.Book;
import com.hkoikoi.toeicMaster.domain.book.repository.BookRepository;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryCreateRequest;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryCreateResponse;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryTreeResponse;
import com.hkoikoi.toeicMaster.domain.category.entity.Category;
import com.hkoikoi.toeicMaster.domain.category.repository.CategoryRepository;
import com.hkoikoi.toeicMaster.global.exception.BusinessException;
import com.hkoikoi.toeicMaster.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;

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

	@Transactional
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
}

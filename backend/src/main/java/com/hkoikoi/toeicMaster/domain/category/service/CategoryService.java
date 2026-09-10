package com.hkoikoi.toeicMaster.domain.category.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkoikoi.toeicMaster.domain.category.dto.CategoryTreeResponse;
import com.hkoikoi.toeicMaster.domain.category.entity.Category;
import com.hkoikoi.toeicMaster.domain.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

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
}

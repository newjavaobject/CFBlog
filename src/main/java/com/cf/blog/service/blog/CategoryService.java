package com.cf.blog.service.blog;

import com.cf.blog.dao.blog.CategoryDAO;
import com.cf.blog.model.blog.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Service("categoryService")
public class CategoryService implements ICategoryService {
    @Resource(name = "categoryDAO")
    protected CategoryDAO categoryDAO;

    @Override
    public Category getCategoryById(String id) {
        return categoryDAO.getCategoryById(id);
    }

    @Override
    public List<Category> getCategoryList(Category category, int page, int limit) {
        return categoryDAO.getCategoryList(category, page, limit);
    }

    @Override
    public int insertCategory(Category category) {
        return categoryDAO.insertCategory(category);
    }

    @Override
    public int updateCategory(Category category) {
        return categoryDAO.updateCategory(category);
    }

    @Override
    public void deleteCategory(String id) {
        categoryDAO.deleteCategory(id);
    }
}

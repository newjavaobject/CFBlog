package com.cf.blog.dao.blog;

import com.cf.blog.model.blog.Category;

import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
public interface CategoryDAO {
    /* 根据Id获取文章分类 */
    Category getCategoryById(String id);

    /* 根据条件查询文章分类列表 */
    List<Category> getCategoryList(Category category, int page, int limit);

    /* 新增一篇文章分类 */
    int insertCategory(Category category);

    /* 更新一篇文章分类 */
    int updateCategory(Category category);

    /* 根据Id删除一篇文章分类 */
    void deleteCategory(String id);

}

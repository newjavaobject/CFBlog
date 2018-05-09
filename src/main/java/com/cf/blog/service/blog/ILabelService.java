package com.cf.blog.service.blog;

import com.cf.blog.model.blog.Label;

import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
public interface ILabelService {
    /* 根据Id获取标签 */
    Label getLabelById(long id);

    /* 根据条件查询标签列表，按次数排序 */
    List<Label> getLabelList(int page, int limit);

    /* 新增一个标签 */
    int insertLabel(Label label);

    /* 新增批量标签 */
    int insertBatchLabel(List<Label> list);

    /* 根据Id删除一个标签 */
    void deleteLabel(long id);

    /* 根据标签内容删除一个标签 */
    void deleteLabel(String label);

    /* 更新标签，更新次数 */
    int updateLabel(Label label);
}

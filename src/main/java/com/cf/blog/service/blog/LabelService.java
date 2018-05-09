package com.cf.blog.service.blog;

import com.cf.blog.dao.blog.LabelDAO;
import com.cf.blog.model.blog.Label;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Service("labelService")
public class LabelService implements ILabelService {
    @Resource(name = "labelDAO")
    protected LabelDAO labelDAO;

    @Override
    public Label getLabelById(long id) {
        return labelDAO.getLabelById(id);
    }

    @Override
    public List<Label> getLabelList(int page, int limit) {
        return labelDAO.getLabelList(page, limit);
    }

    @Override
    public int insertLabel(Label label) {
        return labelDAO.insertLabel(label);
    }

    @Override
    public int insertBatchLabel(List<Label> list) {
        return labelDAO.insertBatchLabel(list);
    }

    @Override
    public void deleteLabel(long id) {
        labelDAO.deleteLabel(id);
    }

    @Override
    public void deleteLabel(String label) {
        labelDAO.deleteLabel(label);
    }

    @Override
    public int updateLabel(Label label) {
        return labelDAO.updateLabel(label);
    }
}

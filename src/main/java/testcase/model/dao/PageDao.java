package testcase.model.dao;

import testcase.entity.Page;
import testcase.exception.DaoException;

import java.util.List;

public interface PageDao {

    List<Page> findALLPublishedPagesSortedByPriority() throws DaoException;
}

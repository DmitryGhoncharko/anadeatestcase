package testcase.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.entity.Page;
import testcase.exception.DaoException;
import testcase.model.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SimplePageDao implements PageDao{
    private static final Logger LOG = LoggerFactory.getLogger(SimplePageDao.class);
    private static final String SQL_FIND_ALL_PUBLISHED_PAGES_SORTED_BY_PRIORITY = "SELECT id, title, description, slug, menu_label, h1, published_at, priority, content" +
            " FROM  page" +
            " WHERE published_at<=current_date" +
            " ORDER BY priority desc";
    private final ConnectionPool connectionPool;

    public SimplePageDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Page> findALLPublishedPagesSortedByPriority() throws DaoException {
        final List<Page> pageList = new ArrayList<>();
        try(final Connection connection = connectionPool.getConnection();final Statement statement = connection.createStatement()){
                final ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_PUBLISHED_PAGES_SORTED_BY_PRIORITY);
                while (resultSet.next()){
                    final Page page = createPageByResultSet(resultSet);
                    pageList.add(page);
                }
        }catch (SQLException e){
            LOG.error("SQL exception when try find all published pages sorted by priority",e);
            throw new DaoException("SQL exception when try find all published pages sorted by priority",e);
        }
        return pageList;
    }

    private Page createPageByResultSet(ResultSet resultSet) throws SQLException {
        return new Page.Builder().
                withId(resultSet.getInt(1)).
                withTitle(resultSet.getString(2)).
                withDescription(resultSet.getString(3)).
                withSlug(resultSet.getString(4)).
                withMenuLabel(resultSet.getString(5)).
                withH1(resultSet.getString(6)).
                withDatePublished(resultSet.getDate(7)).
                withPriority(resultSet.getInt(8)).
                withContent(resultSet.getString(9)).
                build();
    }
}

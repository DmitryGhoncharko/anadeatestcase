package testcase.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.entity.Page;
import testcase.exception.DaoException;
import testcase.model.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimplePageDao implements PageDao {
    private static final Logger LOG = LoggerFactory.getLogger(SimplePageDao.class);
    private static final String SQL_FIND_ALL_PUBLISHED_PAGES_SORTED_DESC_BY_PRIORITY = "SELECT id, title, description, slug, menu_label, h1, published_at, priority, content" +
            " FROM  page" +
            " WHERE published_at<=current_date" +
            " ORDER BY priority desc";
    private static final String SQL_FIND_ALL_NOT_PUBLISHED_PAGES = "SELECT id, title, description, slug, menu_label, h1, published_at, priority, content" +
            " FROM  page" +
            " WHERE published_at>current_date";
    private static final String SQL_CREATE_PAGE = "INSERT INTO page(title, description, slug, menu_label, h1, published_at, priority, content)" +
            " VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_PAGE_BY_PAGE_ID = "UPDATE page" +
            " SET title = ?, description = ?, slug = ?, menu_label = ?, h1 = ?, published_at = ?, priority = ?, content = ?" +
            " WHERE id= ?";
    private static final String SQL_DELETE_PAGE_BY_PAGE_SLUG = "DELETE FROM page WHERE slug=?";

    private static final String SQL_FIND_PAGE_BY_PAGE_SLUG = "SELECT id, title, description, slug, menu_label, h1, published_at, priority, content" +
            " FROM  page" +
            " WHERE slug=?";

    private final ConnectionPool connectionPool;

    public SimplePageDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Page> createPage(Page page) throws DaoException {
        try (final Connection connection = connectionPool.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_PAGE, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatementParamForCreateOrUpdatePageByPreparedStatementAndPage(preparedStatement, page);
            final int countCreatedRows = preparedStatement.executeUpdate();
            if (countCreatedRows > 0) {
                final ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return Optional.of(new Page.Builder().
                            withId(resultSet.getLong(1)).
                            withTitle(page.getTitle()).
                            withDescription(page.getDescription()).
                            withSlug(page.getSlug()).
                            withMenuLabel(page.getMenuLabel()).
                            withH1(page.getH1()).
                            withDatePublished(page.getPublishedAt()).
                            withPriority(page.getPriority()).
                            withContent(page.getContent()).
                            build());
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception when try create new page", e);
            throw new DaoException("SQL exception when try create new page", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Page> findPageByPageSlug(String slug) throws DaoException {
        try (final Connection connection = connectionPool.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PAGE_BY_PAGE_SLUG)) {
            preparedStatement.setString(1, slug);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createPageByResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("SQL exception when try find page by page slug", e);
            throw new DaoException("SQL exception when try find page by page slug", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Page> updatePage(Page page) throws DaoException {
        try (final Connection connection = connectionPool.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PAGE_BY_PAGE_ID)) {
            setPreparedStatementParamForCreateOrUpdatePageByPreparedStatementAndPageWithPageId(preparedStatement, page, page.getId());
            final int countRowsUpdated = preparedStatement.executeUpdate();
            if (countRowsUpdated > 0) {
                return Optional.of(page);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception when try update page by page slug", e);
            throw new DaoException("SQL exception when try update page by page slug", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Page> findAllPublishedPagesSortedByPriority() throws DaoException {
        final List<Page> pageList = new ArrayList<>();
        try (final Connection connection = connectionPool.getConnection(); final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_PUBLISHED_PAGES_SORTED_DESC_BY_PRIORITY);
            while (resultSet.next()) {
                final Page page = createPageByResultSet(resultSet);
                pageList.add(page);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception when try find all published pages sorted by priority", e);
            throw new DaoException("SQL exception when try find all published pages sorted by priority", e);
        }
        return pageList;
    }

    @Override
    public List<Page> findAllNotPublishedPages() throws DaoException {
        final List<Page> pageList = new ArrayList<>();
        try (final Connection connection = connectionPool.getConnection(); final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_NOT_PUBLISHED_PAGES);
            while (resultSet.next()) {
                final Page page = createPageByResultSet(resultSet);
                pageList.add(page);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception when try find all not published pages sorted by priority", e);
            throw new DaoException("SQL exception when try find all not published pages sorted by priority", e);
        }
        return pageList;
    }

    @Override
    public boolean deletePageByPageSlug(String slug) throws DaoException {
        try (final Connection connection = connectionPool.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PAGE_BY_PAGE_SLUG)) {
            preparedStatement.setString(1, slug);
            final int countRowsDeleted = preparedStatement.executeUpdate();
            return countRowsDeleted > 0;
        } catch (SQLException e) {
            LOG.error("SQL exception when we try delete page by page id", e);
            throw new DaoException("SQL exception when we try delete page by page id", e);
        }
    }

    private Page createPageByResultSet(ResultSet resultSet) throws SQLException {
        return new Page.Builder().
                withId(resultSet.getLong(1)).
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

    private void setPreparedStatementParamForCreateOrUpdatePageByPreparedStatementAndPage(PreparedStatement preparedStatement, Page page) throws SQLException {
        preparedStatement.setString(1, page.getTitle());
        preparedStatement.setString(2, page.getDescription());
        preparedStatement.setString(3, page.getSlug());
        preparedStatement.setString(4, page.getMenuLabel());
        preparedStatement.setString(5, page.getH1());
        preparedStatement.setDate(6, page.getPublishedAt());
        preparedStatement.setInt(7, page.getPriority());
        preparedStatement.setString(8, page.getContent());
    }

    private void setPreparedStatementParamForCreateOrUpdatePageByPreparedStatementAndPageWithPageId(PreparedStatement preparedStatement, Page page, Long pageId) throws SQLException {
        setPreparedStatementParamForCreateOrUpdatePageByPreparedStatementAndPage(preparedStatement, page);
        preparedStatement.setLong(9, pageId);
    }
}

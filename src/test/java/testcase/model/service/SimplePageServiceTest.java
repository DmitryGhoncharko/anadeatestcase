package testcase.model.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import testcase.entity.Page;
import testcase.exception.DaoException;
import testcase.model.dao.PageDao;
import testcase.validator.PageServiceValidator;
import testcase.validator.SimplePageServiceValidator;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SimplePageServiceTest {
    private static final Date CURRENT_DATE = new Date(new GregorianCalendar().getTimeInMillis());
    private PageServiceValidator pageServiceValidator = new SimplePageServiceValidator();
    private PageService pageService;
    private static Page pageWithoutId;
    private static Page pageWithId;
    private List<Page> pageList = Collections.singletonList(pageWithId);
    @BeforeAll
    static void createPageInstanceWithoutId(){
        pageWithoutId = new Page.Builder().
                withTitle("Title").
                withDescription("Description").
                withSlug("Slug").
                withMenuLabel("MenuLabel").
                withH1("H1").
                withDatePublished(CURRENT_DATE).
                withPriority(1).
                withContent("Content").
                build();

    }
    @BeforeAll
    static void createPageInstanceWithId(){
        pageWithId = new Page.Builder().
                withId(1L).
                withTitle("Title").
                withDescription("Description").
                withSlug("Slug").
                withMenuLabel("MenuLabel").
                withH1("H1").
                withDatePublished(CURRENT_DATE).
                withPriority(1).
                withContent("Content").
                build();

    }
    @Test
    void createPageTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.createPage(pageWithoutId)).thenReturn(Optional.of(pageWithoutId));
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final Optional<Page> optionalPage = pageService.createPage("Title","Description", "Slug", "MenuLabel", "H1",CURRENT_DATE, 1, "Content" );
        if(optionalPage.isPresent()){
            final Page createdPage = optionalPage.get();
            assertEquals(pageWithoutId,createdPage);
        }
    }
    @Test
    void createPageWithInvalidDataTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.createPage(pageWithoutId)).thenReturn(Optional.of(pageWithoutId));
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final Optional<Page> optionalPage = pageService.createPage(null,"Description", "Slug", "MenuLabel", "H1",CURRENT_DATE, 1, "Content" );
        assertEquals(Optional.empty(), optionalPage);
    }
    @Test
    void updatePageByIdTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.updatePageById(pageWithId)).thenReturn(Optional.of(pageWithId));
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final Optional<Page> optionalPage = pageService.updatePageById("Title","Description", "Slug", "MenuLabel", "H1",CURRENT_DATE, 1, "Content", 1L);
        if(optionalPage.isPresent()){
            final Page createdPage = optionalPage.get();
            assertEquals(pageWithId,createdPage);
        }
    }
    @Test
    void updatePageByIdWithInvalidDataTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.updatePageById(pageWithId)).thenReturn(Optional.of(pageWithId));
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final Optional<Page> optionalPage = pageService.updatePageById(null,"Description", "Slug", "MenuLabel", "H1",CURRENT_DATE, 1, "Content", 1L);
        assertEquals(Optional.empty(), optionalPage);
    }

    @Test
    void findAllPublishedPagesSortedByPriorityTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.findAllPublishedPagesSortedByPriority()).thenReturn(pageList);
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final List<Page> pages = pageService.findAllPublishedPagesSortedByPriority();
        assertArrayEquals(pageList.toArray(), pages.toArray());
    }

    @Test
    void findAllNotPublishedPagesTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.findAllNotPublishedPages()).thenReturn(pageList);
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final List<Page> pages = pageService.findAllNotPublishedPages();
        assertArrayEquals(pageList.toArray(), pages.toArray());
    }

    @Test
    void deletePageByPageSlugTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.deletePageByPageSlug("slug")).thenReturn(true);
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final boolean pageIsDeleted = pageService.deletePageByPageSlug("slug");
        assertTrue(pageIsDeleted);
    }
    @Test
    void deletePageByPageSlugInvalidDataTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.deletePageByPageSlug("slug")).thenReturn(true);
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final boolean pageIsDeleted = pageService.deletePageByPageSlug(null);
        assertFalse(pageIsDeleted);
    }
    @Test
    void findPageByPageSlugTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.findPageByPageSlug("slug")).thenReturn(Optional.ofNullable(pageWithId));
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final Optional<Page> optionalPage = pageService.findPageByPageSlug("slug");
        optionalPage.ifPresent(page -> assertEquals(pageWithId, page));
    }
    @Test
    void findPageByPageSlugWithInvalidDataTest() throws DaoException {
        final PageDao pageDao = Mockito.mock(PageDao.class);
        Mockito.when(pageDao.findPageByPageSlug("slug")).thenReturn(Optional.ofNullable(pageWithId));
        pageService = new SimplePageService(pageDao,pageServiceValidator);
        final Optional<Page> optionalPage = pageService.findPageByPageSlug(null);
        assertEquals(Optional.empty(), optionalPage);
    }
}
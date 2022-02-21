package testcase.command;

import testcase.controller.RequestFactory;
import testcase.controller.SimpleRequestFactory;
import testcase.model.connection.ConnectionPool;
import testcase.model.connection.HikariCPConnectionPool;
import testcase.model.dao.PageDao;
import testcase.model.dao.SimplePageDao;
import testcase.model.service.PageService;
import testcase.model.service.SimplePageService;
import testcase.validator.PageServiceValidator;
import testcase.validator.SimplePageServiceValidator;

public class InitialContext {
    private final ConnectionPool hikariCPConnectionPool = new HikariCPConnectionPool();
    private final PageDao simplePageDao = new SimplePageDao(hikariCPConnectionPool);
    private final PageServiceValidator simplePageServiceValidator = new SimplePageServiceValidator();
    private final PageService pageService = new SimplePageService(simplePageDao,simplePageServiceValidator);
    private final RequestFactory requestFactory = new SimpleRequestFactory();
    public Command lookup(String commandName){
        switch (commandName){

            case "showPage": return new ShowPageCommand(pageService,requestFactory);

            case "showCreateNewPage": return new ShowCreateNewPageCommand(pageService,requestFactory);

            case "createPage" : return new CreatePageCommand(pageService,requestFactory);
            case "menuPage":

            default: return new ShowMenuPageCommand(pageService,requestFactory);
        }
    }
}

package testcase.command;

import testcase.controller.RequestFactory;
import testcase.controller.SimpleRequestFactory;
import testcase.model.connection.HikariCPConnectionPool;
import testcase.model.dao.SimplePageDao;
import testcase.model.service.PageService;
import testcase.model.service.SimplePageService;

public class InitialContext {
    private final PageService pageService = new SimplePageService(new SimplePageDao(new HikariCPConnectionPool()));
    private final RequestFactory requestFactory = new SimpleRequestFactory();
    public Command lookup(String commandName){
        switch (commandName){
            case "menuPage": return new ShowMenuPageCommand(pageService, requestFactory);

            default: return new ShowMenuPageCommand(pageService,requestFactory);
        }
    }
}

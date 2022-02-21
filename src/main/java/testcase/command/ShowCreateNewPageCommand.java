package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.exception.ServiceException;
import testcase.model.service.PageService;

public class ShowCreateNewPageCommand implements Command{
    private static final Logger LOG = LoggerFactory.getLogger(ShowCreateNewPageCommand.class);
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public ShowCreateNewPageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request){
        return requestFactory.createForwardResponse(PagePath.CREATE_PAGE.getPath());
    }
}

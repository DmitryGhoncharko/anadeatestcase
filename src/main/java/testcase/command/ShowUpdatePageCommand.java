package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceException;
import testcase.model.service.PageService;

import java.util.Optional;

public class ShowUpdatePageCommand implements Command{
    private static final Logger LOG = LoggerFactory.getLogger(ShowCreateNewPageCommand.class);
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public ShowUpdatePageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        final String pageSlug = request.getParameter("pageSlug");
        final Optional<Page> page = pageService.findPageByPageSlug(pageSlug);
        if(page.isPresent()){
            request.addAttributeToJsp("pageForUpdate", page);
            return requestFactory.createForwardResponse(PagePath.UPDATE_PAGE.getPath());
        }
        return requestFactory.createForwardResponse(PagePath.ERROR.getPath());
    }
}

package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.exception.ServiceException;
import testcase.model.service.PageService;

public class DeletePageCommand implements Command{
    private static final Logger LOG = LoggerFactory.getLogger(DeletePageCommand.class);
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public DeletePageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        final String pageSlug = request.getParameter("pageSlug");
        final boolean pageIsDeleted = pageService.deletePageByPageSlug(pageSlug);
        if(pageIsDeleted){
            request.addAttributeToJsp("success", "Page was deleted");
            return requestFactory.createRedirectResponse("/controller?command=menuPage");
        }
        request.addAttributeToJsp("fail","Page not created, please try to create page with new params, slug may be unique");
        return requestFactory.createForwardResponse(PagePath.PAGE.getPath());
    }
}

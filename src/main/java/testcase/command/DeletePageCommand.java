package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.exception.ServiceError;
import testcase.model.service.PageService;

public class DeletePageCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(DeletePageCommand.class);
    private static final String PAGE_SLUG_PARAM_NAME = "pageSlug";
    private static final String CONTROLLER_COMMAND_MENU_PAGE = "/controller?command=menuPage";
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public DeletePageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError {
        final String pageSlug = request.getParameter(PAGE_SLUG_PARAM_NAME);
        try {
            final boolean pageIsDeleted = pageService.deletePageByPageSlug(pageSlug);
            if (pageIsDeleted) {
                LOG.info("Page was deleted pageSlug: " + pageSlug);
                return requestFactory.createRedirectResponse(CONTROLLER_COMMAND_MENU_PAGE);
            }
        } catch (ServiceError e) {
            LOG.error("Service exception when we try delete page by slug. slug: " + pageSlug, e);
            throw e;
        }
        LOG.info("Page dont deleted by slug. Slug: " + pageSlug);
        return requestFactory.createForwardResponse(PagePath.ERROR.getPath());
    }
}

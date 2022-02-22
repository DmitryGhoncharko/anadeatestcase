package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceError;
import testcase.model.service.PageService;

import java.sql.Date;
import java.util.Optional;

public class UpdatePageCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(UpdatePageCommand.class);
    private static final String PAGE_TITLE_FOR_UPDATE_PARAM_NAME = "pageTitleForUpdate";
    private static final String PAGE_DESCRIPTION_FOR_UPDATE_PARAM_NAME = "pageDescriptionForUpdate";
    private static final String PAGE_SLUG_FOR_UPDATE_PARAM_NAME = "pageSlugForUpdate";
    private static final String PAGE_MENU_LABEL_FOR_UPDATE_PARAM_NAME = "pageMenuLabelForUpdate";
    private static final String PAGE_H_1_FOR_UPDATE_PARAM_NAME = "pageH1ForUpdate";
    private static final String PAGE_CONTENT_FOR_UPDATE_PARAM_NAME = "pageContentForUpdate";
    private static final String PAGE_DATE_PUBLISHED_FOR_UPDATE_PARAM_NAME = "pageDatePublishedForUpdate";
    private static final String PAGE_PRIORITY_FOR_UPDATE_PARAM_NAME = "pagePriorityForUpdate";
    private static final String CONTROLLER_COMMAND_MENU_PAGE = "/controller?command=menuPage";
    private static final String PAGE_ID_PARAM_NAME = "pageId";
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public UpdatePageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError {
        final Long pageId = Long.valueOf(request.getParameter(PAGE_ID_PARAM_NAME));
        final String pageTitleForUpdate = request.getParameter(PAGE_TITLE_FOR_UPDATE_PARAM_NAME);
        final String pageDescriptionForUpdate = request.getParameter(PAGE_DESCRIPTION_FOR_UPDATE_PARAM_NAME);
        final String pageSlugForUpdate = request.getParameter(PAGE_SLUG_FOR_UPDATE_PARAM_NAME);
        final String pageMenuLabelForUpdate = request.getParameter(PAGE_MENU_LABEL_FOR_UPDATE_PARAM_NAME);
        final String pageH1ForUpdate = request.getParameter(PAGE_H_1_FOR_UPDATE_PARAM_NAME);
        final String pageContentForUpdate = request.getParameter(PAGE_CONTENT_FOR_UPDATE_PARAM_NAME);
        final Date pageDatePublishedForUpdate = Date.valueOf(request.getParameter(PAGE_DATE_PUBLISHED_FOR_UPDATE_PARAM_NAME));
        final Integer pagePriorityForUpdate = Integer.parseInt(request.getParameter(PAGE_PRIORITY_FOR_UPDATE_PARAM_NAME));
        try {
            final Optional<Page> updatedPage = pageService.updatePageById(pageTitleForUpdate, pageDescriptionForUpdate, pageSlugForUpdate, pageMenuLabelForUpdate, pageH1ForUpdate, pageDatePublishedForUpdate, pagePriorityForUpdate, pageContentForUpdate, pageId);
            if (updatedPage.isPresent()) {
                LOG.info("Page was updated");
                return requestFactory.createRedirectResponse(CONTROLLER_COMMAND_MENU_PAGE);
            }
        } catch (ServiceError e) {
            LOG.error("Service Exception when try update page", e);
            throw e;
        }
        LOG.info("Page don't updated");
        return requestFactory.createForwardResponse(PagePath.ERROR.getPath());
    }
}

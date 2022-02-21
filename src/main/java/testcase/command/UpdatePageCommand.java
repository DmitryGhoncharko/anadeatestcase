package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceException;
import testcase.model.service.PageService;

import java.sql.Date;
import java.util.Optional;

public class UpdatePageCommand implements Command{
    private static final Logger LOG = LoggerFactory.getLogger(UpdatePageCommand.class);
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public UpdatePageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        final String pageTitleForUpdate = request.getParameter("pageTitleForUpdate");
        final String pageDescriptionForUpdate = request.getParameter("pageDescriptionForUpdate");
        final String pageSlugForUpdate = request.getParameter("pageSlugForUpdate");
        final String pageMenuLabelForUpdate = request.getParameter("pageMenuLabelForUpdate");
        final String pageH1ForUpdate = request.getParameter("pageH1ForUpdate");
        final String pageContentForUpdate = request.getParameter("pageContentForUpdate");
        final Date pageDatePublishedForUpdate = Date.valueOf(request.getParameter("pageDatePublishedForUpdate"));
        final Integer pagePriorityForUpdate = Integer.parseInt(request.getParameter("pagePriorityForUpdate"));
        final Optional<Page> updatedPage = pageService.updatePage(pageTitleForUpdate, pageDescriptionForUpdate, pageSlugForUpdate, pageMenuLabelForUpdate, pageH1ForUpdate, pageDatePublishedForUpdate, pagePriorityForUpdate, pageContentForUpdate);
        if(updatedPage.isPresent()){

        }
        return null;
    }
}

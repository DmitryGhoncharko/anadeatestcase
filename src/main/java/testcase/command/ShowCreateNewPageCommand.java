package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;

public class ShowCreateNewPageCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(ShowCreateNewPageCommand.class);
    private final RequestFactory requestFactory;

    public ShowCreateNewPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        LOG.info("Show create page path command execute");
        return requestFactory.createForwardResponse(PagePath.CREATE_PAGE.getPath());
    }
}

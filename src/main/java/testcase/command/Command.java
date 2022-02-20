package testcase.command;

import testcase.exception.ServiceException;

public interface Command {

    CommandResponse execute(CommandRequest request) throws ServiceException;


}

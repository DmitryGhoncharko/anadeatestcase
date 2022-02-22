package testcase.command;

import testcase.exception.ServiceError;


public interface Command {

    CommandResponse execute(CommandRequest request) throws ServiceError;

}

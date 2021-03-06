package testcase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.command.Command;
import testcase.command.CommandRequest;
import testcase.command.CommandResponse;
import testcase.command.ServiceLocator;
import testcase.command.SimpleServiceLocator;
import testcase.exception.ServiceError;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    private static final String COMMAND_NAME_PARAM = "command";

    private static final RequestFactory REQUEST_FACTORY = new SimpleRequestFactory();
    private static final ServiceLocator SERVICE_LOCATOR = new SimpleServiceLocator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceError {
        LOG.trace("caught req and resp in doGet method");
        try {
            processRequest(req, resp);
        } catch (ServiceError | IOException | ServletException e) {
            LOG.error("Exception in doget method servlet", e);
            throw e;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceError {

        LOG.trace("caught req and resp in doPost method");
        try {
            processRequest(req, resp);
        } catch (ServiceError | IOException | ServletException e) {
            LOG.error("Service exception in doget method servlet", e);
            throw e;
        }
    }

    private void processRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServiceError, IOException, ServletException {
        final String commandName = httpRequest.getParameter(COMMAND_NAME_PARAM);
        final Command command = SERVICE_LOCATOR.getCommand(commandName);
        final CommandRequest commandRequest = REQUEST_FACTORY.createRequest(httpRequest);
        final CommandResponse commandResponse = command.execute(commandRequest);
        proceedWithResponse(httpRequest, httpResponse, commandResponse);
    }

    private void proceedWithResponse(HttpServletRequest req, HttpServletResponse resp,
                                     CommandResponse commandResponse) throws IOException, ServletException {

        forwardOrRedirectToResponseLocation(req, resp, commandResponse);

    }

    private void forwardOrRedirectToResponseLocation(HttpServletRequest req, HttpServletResponse resp,
                                                     CommandResponse commandResponse) throws IOException, ServletException {
        if (commandResponse.isRedirect()) {
            resp.sendRedirect(commandResponse.getPath());
        } else {
            final String desiredPath = commandResponse.getPath();
            final RequestDispatcher dispatcher = req.getRequestDispatcher(desiredPath);
            dispatcher.forward(req, resp);
        }
    }
}

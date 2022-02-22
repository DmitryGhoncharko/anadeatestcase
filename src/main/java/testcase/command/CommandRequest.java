package testcase.command;


public interface CommandRequest {

    void addAttributeToJsp(String name, Object attribute);

    String getParameter(String name);

}

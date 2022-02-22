package testcase.command;

public interface CommandResponse {

    boolean isRedirect();

    String getPath();

}
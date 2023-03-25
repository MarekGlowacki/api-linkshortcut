package online.javafun.shortlink;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("wrong password");
    }
}
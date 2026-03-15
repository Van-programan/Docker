package trod.lab.util;

public class UserCreatedException extends RuntimeException {
    public UserCreatedException() {
        super("Пользователь с таким email уже есть");
    }
}

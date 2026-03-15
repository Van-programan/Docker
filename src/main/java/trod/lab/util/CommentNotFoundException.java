package trod.lab.util;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("Такого комментария не существует");
    }
}

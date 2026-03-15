package trod.lab.util;

public class RecordNotFoundException extends RuntimeException {
  public RecordNotFoundException() {
    super("Такой записи не существует");
  }
}

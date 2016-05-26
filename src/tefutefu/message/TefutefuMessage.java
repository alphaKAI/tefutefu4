package tefutefu.message;
import tefutefu.commons.Importance;

public class TefutefuMessage<T> {
  private String      id;
  private Importance  importance = Importance.MID;
  public  T           data;

  public TefutefuMessage() {}

  public TefutefuMessage(T data) {
    this.data = data;
  }

  public String getId() {
    return this.id;
  }

  public Importance getImportance() {
    return this.importance;
  }

}
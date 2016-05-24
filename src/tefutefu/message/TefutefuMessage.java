package tefutefu.message;

public class TefutefuMessage<T> {
  private String id;
  private int    importance;// 0 : low, 1 : Normal, 2 : High
  public  T      data;

  public TefutefuMessage() {
  }

  public String getId() {
    return this.id;
  }

  public int getImportance() {
    return this.importance;
  }

  public TefutefuMessage(T data) {
    this.data = data;
  }
}
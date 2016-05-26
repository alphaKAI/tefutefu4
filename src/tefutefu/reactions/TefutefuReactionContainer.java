package tefutefu.reactions;

public class TefutefuReactionContainer {
  public TefutefuReactionTypes  type;
  public TefutefuReactionTarget target;
  public String                 generatedText;

  public TefutefuReactionContainer(TefutefuReactionTypes  type) {
    this.type   = type;
  }

  public TefutefuReactionContainer(
      TefutefuReactionTypes  type,
      TefutefuReactionTarget target
  ) {
    this.type   = type;
    this.target = target;
  }

  public TefutefuReactionContainer(
      TefutefuReactionTypes  type,
      TefutefuReactionTarget target,
      String                 generatedText
  ) {
    this.type          = type;
    this.target        = target;
    this.generatedText = generatedText;
  }
}

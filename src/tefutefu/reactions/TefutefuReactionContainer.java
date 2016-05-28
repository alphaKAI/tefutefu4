package tefutefu.reactions;

public class TefutefuReactionContainer {
  public TefutefuReactionTypes  type;
  public TefutefuReactionTarget target;
  public String                 generatedText;
  public TefutefuReaction       reaction;

  public TefutefuReactionContainer(TefutefuReactionTypes  type,
                                   TefutefuReaction reaction) {
    this.type   = type;
    this.reaction = reaction;
  }

  public TefutefuReactionContainer(
      TefutefuReactionTypes  type,
      TefutefuReactionTarget target,
      TefutefuReaction reaction
  ) {
    this.type   = type;
    this.target = target;
  }

  public TefutefuReactionContainer(
      TefutefuReactionTypes  type,
      TefutefuReactionTarget target,
      String                 generatedText,
      TefutefuReaction reaction
  ) {
    this.type          = type;
    this.target        = target;
    this.generatedText = generatedText;
  }
}

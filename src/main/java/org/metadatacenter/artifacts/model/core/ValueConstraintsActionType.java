package org.metadatacenter.artifacts.model.core;

import org.metadatacenter.model.ModelNodeNames;

public enum ValueConstraintsActionType
{
  MOVE(ModelNodeNames.VALUE_CONSTRAINTS_ACTION_MOVE),
  DELETE(ModelNodeNames.VALUE_CONSTRAINTS_ACTION_DELETE);

  private final String text;

  ValueConstraintsActionType(String text) {
    this.text = text;
  }

  public String getText() {
    return this.text;
  }

  public static ValueConstraintsActionType fromString(String text) {
    for (ValueConstraintsActionType f : ValueConstraintsActionType.values()) {
      if (f.text.equalsIgnoreCase(text)) {
        return f;
      }
    }
    throw new IllegalArgumentException("No value constraints action type constant with text " + text + " found");
  }
}

package org.metadatacenter.artifacts.model.core;

import java.util.Optional;

public sealed interface ValueConstraints permits TextValueConstraints, NumericValueConstraints,
  ControlledTermValueConstraints, TemporalValueConstraints
{
  boolean requiredValue();

  boolean multipleChoice();

  Optional<? extends DefaultValue> defaultValue();

  default TextValueConstraints asTextValueConstraints()
  {
    if (this instanceof TextValueConstraints) // TODO Use typesafe switch when available
      return (TextValueConstraints)this;
    else
      throw new ClassCastException("Cannot convert " + this.getClass().getName() + " to " + TextValueConstraints.class.getName());
  }

  default NumericValueConstraints asNumericValueConstraints()
  {
    if (this instanceof NumericValueConstraints) // TODO Use typesafe switch when available
      return (NumericValueConstraints)this;
    else
      throw new ClassCastException("Cannot convert " + this.getClass().getName() + " to " + NumericValueConstraints.class.getName());
  }

  default ControlledTermValueConstraints asControlledTermValueConstraints()
  {
    if (this instanceof ControlledTermValueConstraints) // TODO Use typesafe switch when available
      return (ControlledTermValueConstraints)this;
    else
      throw new ClassCastException("Cannot convert " + this.getClass().getName() + " to " + ControlledTermValueConstraints.class.getName());
  }

  default TemporalValueConstraints asTemporalValueConstraints()
  {
    if (this instanceof TemporalValueConstraints) // TODO Use typesafe switch when available
      return (TemporalValueConstraints)this;
    else
      throw new ClassCastException("Cannot convert " + this.getClass().getName() + " to " + TemporalValueConstraints.class.getName());
  }

}

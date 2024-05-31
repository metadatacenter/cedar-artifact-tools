package org.metadatacenter.artifacts.model.core.fields.constraints;

import org.metadatacenter.artifacts.model.core.fields.XsdTemporalDatatype;
import org.metadatacenter.artifacts.model.core.fields.TemporalDefaultValue;

import java.util.Optional;

import static org.metadatacenter.artifacts.model.core.ValidationHelper.validateOptionalFieldNotNull;
import static org.metadatacenter.artifacts.model.core.ValidationHelper.validateXsdTemporalDatatypeFieldNotNull;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_DEFAULT_VALUE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_TEMPORAL_TYPE;

public non-sealed interface TemporalValueConstraints extends ValueConstraints
{
  XsdTemporalDatatype temporalType();

  Optional<TemporalDefaultValue> defaultValue();

  static TemporalValueConstraints create(XsdTemporalDatatype temporalType, Optional<TemporalDefaultValue> defaultValue,
    boolean requiredValue, boolean recommendedValue, boolean multipleChoice)
  {
    return new TemporalValueConstraintsRecord(temporalType, defaultValue, requiredValue, recommendedValue, multipleChoice);
  }

  static Builder builder() {
    return new Builder();
  }

  static Builder builder(TemporalValueConstraints temporalValueConstraints) {
    return new Builder(temporalValueConstraints);
  }

  class Builder {
    private XsdTemporalDatatype temporalType;
    private Optional<TemporalDefaultValue> defaultValue = Optional.empty();
    private boolean requiredValue = false;
    private boolean recommendedValue = false;
    private boolean multipleChoice = false;

    private Builder() {
    }

    private Builder(TemporalValueConstraints temporalValueConstraints) {
      this.temporalType = temporalValueConstraints.temporalType();
      this.defaultValue = temporalValueConstraints.defaultValue();
      this.requiredValue = temporalValueConstraints.requiredValue();
      this.recommendedValue = temporalValueConstraints.recommendedValue();
      this.multipleChoice = temporalValueConstraints.multipleChoice();
    }

    public Builder withTemporalType(XsdTemporalDatatype temporalType) {
      this.temporalType = temporalType;
      return this;
    }

    public Builder withDefaultValue(String defaultValue) {
      this.defaultValue = Optional.of(new TemporalDefaultValue(defaultValue));
      return this;
    }

    public Builder withRequiredValue(boolean requiredValue) {
      this.requiredValue = requiredValue;
      return this;
    }

    public Builder withRecommendedValue(boolean recommendedValue) {
      this.recommendedValue = recommendedValue;
      return this;
    }

    public Builder withMultipleChoice(boolean multipleChoice) {
      this.multipleChoice = multipleChoice;
      return this;
    }

    public TemporalValueConstraints build()
    {
      return new TemporalValueConstraintsRecord(temporalType, defaultValue, requiredValue, recommendedValue, multipleChoice);
    }
  }
}

record TemporalValueConstraintsRecord(XsdTemporalDatatype temporalType, Optional<TemporalDefaultValue> defaultValue,
                                      boolean requiredValue, boolean recommendedValue, boolean multipleChoice)
  implements TemporalValueConstraints
{
  public TemporalValueConstraintsRecord
  {
    validateXsdTemporalDatatypeFieldNotNull(this, temporalType, VALUE_CONSTRAINTS_TEMPORAL_TYPE);
    validateOptionalFieldNotNull(this, defaultValue, VALUE_CONSTRAINTS_DEFAULT_VALUE);
  }
}

package org.metadatacenter.artifacts.model.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ValueConstraints
{
  private final boolean requiredValue;
  private final boolean multipleChoice;
  private final Optional<NumberType> numberType;
  private final Optional<String> unitOfMeasure;
  private final Optional<Number> minValue;
  private final Optional<Number> maxValue;
  private final Optional<Integer> decimalPlaces;
  private final Optional<Integer> minLength;
  private final Optional<Integer> maxLength;
  private final Optional<TemporalType> temporalType;
  private final List<OntologyValueConstraint> ontologies;
  private final List<ValueSetValueConstraint> valueSets;
  private final List<ClassValueConstraint> classes;
  private final List<BranchValueConstraint> branches;
  private final List<LiteralValueConstraint> literals;
  private final Optional<DefaultValue> defaultValue;
  private final List<ValueConstraintsAction> actions;

  public ValueConstraints(boolean requiredValue, boolean multipleChoice, Optional<NumberType> numberType,
    Optional<String> unitOfMeasure, Optional<Number> minValue, Optional<Number> maxValue,
    Optional<Integer> decimalPlaces, Optional<Integer> minLength, Optional<Integer> maxLength,
    Optional<TemporalType> temporalType, List<OntologyValueConstraint> ontologies, List<ValueSetValueConstraint> valueSets,
    List<ClassValueConstraint> classes, List<BranchValueConstraint> branches, List<LiteralValueConstraint> literals,
    Optional<DefaultValue> defaultValue, List<ValueConstraintsAction> actions)
  {
    this.requiredValue = requiredValue;
    this.multipleChoice = multipleChoice;
    this.numberType = numberType;
    this.unitOfMeasure = unitOfMeasure;
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.decimalPlaces = decimalPlaces;
    this.minLength = minLength;
    this.maxLength = maxLength;
    this.temporalType = temporalType;
    this.ontologies = Collections.unmodifiableList(ontologies);
    this.valueSets = Collections.unmodifiableList(valueSets);
    this.classes = Collections.unmodifiableList(classes);
    this.branches = Collections.unmodifiableList(branches);
    this.literals = Collections.unmodifiableList(literals);
    this.defaultValue = defaultValue;
    this.actions = Collections.unmodifiableList(actions);
  }

  private ValueConstraints(Builder builder) {
    this.requiredValue = builder.requiredValue;
    this.multipleChoice = builder.multipleChoice;
    this.numberType = builder.numberType;
    this.unitOfMeasure = builder.unitOfMeasure;
    this.minValue = builder.minValue;
    this.maxValue = builder.maxValue;
    this.decimalPlaces = builder.decimalPlaces;
    this.minLength = builder.minLength;
    this.maxLength = builder.maxLength;
    this.temporalType = builder.temporalType;
    this.ontologies = builder.ontologies;
    this.valueSets = builder.valueSets;
    this.classes = builder.classes;
    this.branches = builder.branches;
    this.literals = builder.literals;
    this.defaultValue = builder.defaultValue;
    this.actions = builder.actions;
  }

  public boolean isRequiredValue()
  {
    return requiredValue;
  }

  public boolean isMultipleChoice()
  {
    return multipleChoice;
  }

  public Optional<NumberType> getNumberType()
  {
    return numberType;
  }

  public Optional<String> getUnitOfMeasure()
  {
    return unitOfMeasure;
  }

  public Optional<Number> getMinValue()
  {
    return minValue;
  }

  public Optional<Number> getMaxValue()
  {
    return maxValue;
  }

  public Optional<Integer> getDecimalPlaces()
  {
    return decimalPlaces;
  }

  public Optional<Integer> getMinLength()
  {
    return minLength;
  }

  public Optional<Integer> getMaxLength()
  {
    return maxLength;
  }

  public Optional<TemporalType> getTemporalType()
  {
    return temporalType;
  }

  public List<OntologyValueConstraint> getOntologies()
  {
    return ontologies;
  }

  public List<ValueSetValueConstraint> getValueSets()
  {
    return valueSets;
  }

  public List<ClassValueConstraint> getClasses()
  {
    return classes;
  }

  public List<BranchValueConstraint> getBranches()
  {
    return branches;
  }

  public List<LiteralValueConstraint> getLiterals()
  {
    return literals;
  }

  public List<ValueConstraintsAction> getActions()
  {
    return actions;
  }

  public boolean hasLiteralBasedConstraint() {
    return !literals.isEmpty();
  }

  public boolean hasValueBasedConstraints()
  {
    return !ontologies.isEmpty() || !valueSets.isEmpty() || !classes.isEmpty() || !branches.isEmpty() || !literals.isEmpty();
  }

  public boolean hasOntologyValueBasedConstraints()
  {
    return !ontologies.isEmpty() || !valueSets.isEmpty() || !classes.isEmpty() || !branches.isEmpty();
  }

  public Optional<DefaultValue> getDefaultValue()
  {
    return defaultValue;
  }

  @Override public String toString()
  {
    return "ValueConstraints{" + "requiredValue=" + requiredValue + ", multipleChoice=" + multipleChoice
      + ", numberType=" + numberType + ", unitOfMeasure=" + unitOfMeasure + ", minValue=" + minValue + ", maxValue="
      + maxValue + ", decimalPlaces=" + decimalPlaces + ", minLength=" + minLength + ", maxLength=" + maxLength
      + ", temporalType=" + temporalType + ", ontologies=" + ontologies + ", valueSets=" + valueSets + ", classes="
      + classes + ", branches=" + branches + ", literals=" + literals + ", defaultValue=" + defaultValue + ", actions="
      + actions + '}';
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private boolean requiredValue = false;
    private boolean multipleChoice = false;
    private Optional<NumberType> numberType = Optional.empty();
    private Optional<String> unitOfMeasure = Optional.empty();
    private Optional<Number> minValue = Optional.empty();
    private Optional<Number> maxValue = Optional.empty();
    private Optional<Integer> decimalPlaces = Optional.empty();
    private Optional<Integer> minLength = Optional.empty();
    private Optional<Integer> maxLength = Optional.empty();
    private Optional<TemporalType> temporalType = Optional.empty();
    private List<OntologyValueConstraint> ontologies = new ArrayList<>();
    private List<ValueSetValueConstraint> valueSets = new ArrayList<>();
    private List<ClassValueConstraint> classes = new ArrayList<>();
    private List<BranchValueConstraint> branches = new ArrayList<>();
    private List<LiteralValueConstraint> literals = new ArrayList<>();
    private Optional<DefaultValue> defaultValue = Optional.empty();
    private List<ValueConstraintsAction> actions = new ArrayList<>();

    private Builder() {
    }

    public Builder withRequiredValue(boolean requiredValue) {
      this.requiredValue = requiredValue;
      return this;
    }

    public Builder withMultipleChoice(boolean multipleChoice) {
      this.multipleChoice = multipleChoice;
      return this;
    }

    public Builder withNumberType(NumberType numberType) {
      this.numberType = Optional.ofNullable(numberType);
      return this;
    }

    public Builder withUnitOfMeasure(String unitOfMeasure) {
      this.unitOfMeasure = Optional.ofNullable(unitOfMeasure);
      return this;
    }

    public Builder withMinValue(Number minValue) {
      this.minValue = Optional.ofNullable(minValue);
      return this;
    }

    public Builder withMaxValue(Number maxValue) {
      this.maxValue = Optional.ofNullable(maxValue);
      return this;
    }

    public Builder withDecimalPlaces(Integer decimalPlaces) {
      this.decimalPlaces = Optional.ofNullable(decimalPlaces);
      return this;
    }

    public Builder withMinLength(Integer minLength) {
      this.minLength = Optional.ofNullable(minLength);
      return this;
    }

    public Builder withMaxLength(Integer maxLength) {
      this.maxLength = Optional.ofNullable(maxLength);
      return this;
    }

    public Builder withTemporalType(TemporalType temporalType) {
      this.temporalType = Optional.ofNullable(temporalType);
      return this;
    }

    public Builder withOntologyValueConstraint(OntologyValueConstraint constraint) {
      ontologies.add(constraint);
      return this;
    }

    public Builder withValueSetValueConstraint(ValueSetValueConstraint constraint) {
      valueSets.add(constraint);
      return this;
    }

    public Builder withClassValueConstraint(ClassValueConstraint constraint) {
      classes.add(constraint);
      return this;
    }

    public Builder withBranchValueConstraint(BranchValueConstraint constraint) {
      branches.add(constraint);
      return this;
    }

    public Builder withLiteralValueConstraint(LiteralValueConstraint constraint) {
      literals.add(constraint);
      return this;
    }

    public Builder withDefaultValue(DefaultValue defaultValue) {
      this.defaultValue = Optional.ofNullable(defaultValue);
      return this;
    }

    public Builder withValueConstraintsAction(ValueConstraintsAction action) {
      actions.add(action);
      return this;
    }

    public ValueConstraints build()
    {
      return new ValueConstraints(this);
    }
  }
}

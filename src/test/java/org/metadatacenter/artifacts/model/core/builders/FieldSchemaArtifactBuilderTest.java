package org.metadatacenter.artifacts.model.core.builders;

import org.junit.Assert;
import org.junit.Test;
import org.metadatacenter.artifacts.model.core.FieldInputType;
import org.metadatacenter.artifacts.model.core.FieldSchemaArtifact;
import org.metadatacenter.artifacts.model.core.InputTimeFormat;
import org.metadatacenter.artifacts.model.core.NumericType;
import org.metadatacenter.artifacts.model.core.TemporalGranularity;
import org.metadatacenter.artifacts.model.core.TemporalType;
import org.metadatacenter.artifacts.model.core.ValueConstraints;
import org.metadatacenter.artifacts.model.core.ValueConstraintsActionType;
import org.metadatacenter.artifacts.model.core.ValueType;

import java.net.URI;

public class FieldSchemaArtifactBuilderTest
{

  @Test public void testCreateTextField()
  {
    String name = "Field name";
    String description = "Field description";
    Integer minLength = 0;
    Integer maxLength = 10;
    boolean valueRecommendationEnabled = false;

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.textFieldBuilder().
      withName(name).
      withDescription(description).
      withMinLength(minLength).
      withMaxLength(maxLength).
      withValueRecommendationEnabled(valueRecommendationEnabled).
      build();

    Assert.assertEquals(FieldInputType.TEXTFIELD, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    Assert.assertEquals(minLength, fieldSchemaArtifact.minLength().get());
    Assert.assertEquals(maxLength, fieldSchemaArtifact.maxLength().get());
    // TODO test value recommendation
  }

  @Test public void testCreateNumericField()
  {
    String name = "Field name";
    String description = "Field description";
    NumericType numericType = NumericType.DOUBLE;
    Number defaultValue = 22.3;
    Number minValue = 0.0;
    Number maxValue = 100.0;

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.numericFieldBuilder().
      withName(name).
      withDescription(description).
      withNumericType(numericType).
      withDefaultValue(defaultValue).
      withMinValue(minValue).
      withMaxValue(maxValue).
      build();

    Assert.assertEquals(FieldInputType.NUMERIC, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    Assert.assertEquals(numericType, fieldSchemaArtifact.valueConstraints().get().asNumericValueConstraints().numberType());
    Assert.assertEquals(defaultValue, fieldSchemaArtifact.valueConstraints().get().asNumericValueConstraints().defaultValue().get().value());
    Assert.assertEquals(minValue, fieldSchemaArtifact.valueConstraints().get().asNumericValueConstraints().minValue().get());
    Assert.assertEquals(maxValue, fieldSchemaArtifact.valueConstraints().get().asNumericValueConstraints().maxValue().get());
  }

  @Test public void testCreateTemporalField()
  {
    String name = "Field name";
    String description = "Field description";
    TemporalType temporalType = TemporalType.TIME;
    TemporalGranularity temporalGranularity = TemporalGranularity.SECOND;
    InputTimeFormat inputTimeFormat = InputTimeFormat.TWENTY_FOUR_HOUR;
    boolean timeZoneEnabled = false;

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.temporalFieldBuilder().
      withName(name).
      withDescription(description).
      withTemporalType(temporalType).
      withTemporalGranularity(temporalGranularity).
      withInputTimeFormat(inputTimeFormat).
      withTimeZoneEnabled(false).
      build();

    Assert.assertEquals(FieldInputType.TEMPORAL, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    Assert.assertEquals(temporalGranularity, fieldSchemaArtifact.fieldUi().asTemporalFieldUi().temporalGranularity());
    Assert.assertEquals(inputTimeFormat, fieldSchemaArtifact.fieldUi().asTemporalFieldUi().inputTimeFormat());
    Assert.assertEquals(timeZoneEnabled, fieldSchemaArtifact.fieldUi().asTemporalFieldUi().timeZoneEnabled());
    Assert.assertEquals(temporalType, fieldSchemaArtifact.valueConstraints().get().asTemporalValueConstraints().temporalType());
  }

  @Test public void testCreateControlledTermField()
  {
    String name = "Field name";
    String description = "Field description";
    URI ontologyUri = URI.create("https://data.bioontology.org/ontologies/DOID");
    String ontologyAcronym = "DOID";
    String ontologyName = "Human Disease Ontology";
    URI branchUri = URI.create("http://purl.bioontology.org/ontology/SNOMEDCT/64572001");
    String branchAcronym = "SNOMEDCT";
    String branchName = "Disease";
    String branchSource = "SNOMEDCT";
    URI classUri = URI.create("http://purl.bioontology.org/ontology/LNC/LA19711-3");
    String classSource = "LOINC";
    String classLabel= "Human";
    String classPrefLabel = "Human";
    ValueType classValueType = ValueType.ONTOLOGY_CLASS;
    URI valueSetUri = URI.create("https://cadsr.nci.nih.gov/metadata/CADSR-VS/77d61de250089d223d7153a4283e738043a15707");
    String valueSetCollection = "CADSR-VS";
    String valueSetName = "Stable Disease";
    URI actionTermUri = URI.create("http://purl.obolibrary.org/obo/NCBITaxon_51291");
    URI actionSourceUri = URI.create("https://data.bioontology.org/ontologies/DOID");
    String actionSource = "DOID";
    ValueType actionValueType = ValueType.ONTOLOGY_CLASS;
    Integer actionTo = 0;

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.controlledTermFieldBuilder().
      withName(name).
      withDescription(description).
      withOntologyValueConstraint(ontologyUri, ontologyAcronym, ontologyName).
      withBranchValueConstraint(branchUri, branchSource, branchAcronym, branchName, 3).
      withClassValueConstraint(classUri, classSource, classLabel, classPrefLabel, classValueType).
      withValueSetValueConstraint(valueSetUri, valueSetCollection, valueSetName).
      withValueConstraintsAction(actionTermUri, actionSource, actionValueType, ValueConstraintsActionType.DELETE,
      actionSourceUri, actionTo).
      build();

    Assert.assertEquals(FieldInputType.TEXTFIELD, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());

    // TODO Check controlled term values
  }

  @Test public void testCreateRadioField()
  {
    String name = "Field name";
    String description = "Field description";

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.radioFieldBuilder().
      withName(name).
      withDescription(description).
      withOption("Choice 1").
      withOption("Choice 2").
      withOption("Choice 3", true).
      build();

    Assert.assertEquals(FieldInputType.RADIO, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    // TODO test options
  }

  @Test public void testCreateListField()
  {
    String name = "Field name";
    String description = "Field description";

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.listFieldBuilder().
      withName(name).
      withDescription(description).
      withOption("Choice 1").
      withOption("Choice 2").
      withOption("Choice 3", true).
      build();

    Assert.assertEquals(FieldInputType.LIST, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    // TODO test options
  }

  @Test public void testCreateCheckboxField()
  {
    String name = "Field name";
    String description = "Field description";

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.checkboxFieldBuilder().
      withName(name).
      withDescription(description).
      withOption("Choice 1", false).
      withOption("Choice 2", false).
      withOption("Choice 3", true).
      build();

    Assert.assertEquals(FieldInputType.CHECKBOX, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    // TODO test options
  }

  @Test public void testCreatePhoneNumberField()
  {
    String name = "Field name";
    String description = "Field description";
    Integer minLength = 0;
    Integer maxLength = 10;

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.phoneNumberFieldBuilder().
      withName(name).
      withDescription(description).
      withMinLength(minLength).
      withMaxLength(maxLength).
      build();

    Assert.assertEquals(FieldInputType.PHONE_NUMBER, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    Assert.assertEquals(minLength, fieldSchemaArtifact.minLength().get());
    Assert.assertEquals(maxLength, fieldSchemaArtifact.maxLength().get());
  }

  @Test public void testCreateEmailField()
  {
    String name = "Field name";
    String description = "Field description";
    Integer minLength = 0;
    Integer maxLength = 10;

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.emailFieldBuilder().
      withName(name).
      withDescription(description).
      withMinLength(minLength).
      withMaxLength(maxLength).
      build();

    Assert.assertEquals(FieldInputType.EMAIL, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    Assert.assertEquals(minLength, fieldSchemaArtifact.minLength().get());
    Assert.assertEquals(maxLength, fieldSchemaArtifact.maxLength().get());
  }

  @Test public void testCreateLinkField()
  {
    String name = "Field name";
    String description = "Field description";
    URI defaultURI = URI.create("https://example.com/Study");
    String defaultLabel = "Study";

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.linkFieldBuilder().
      withName(name).
      withDescription(description).
      withDefaultValue(defaultURI, defaultLabel).
      build();

    Assert.assertEquals(FieldInputType.LINK, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    Assert.assertEquals(defaultURI, fieldSchemaArtifact.valueConstraints().get().asControlledTermValueConstraints().defaultValue().get().value().getLeft());
    Assert.assertEquals(defaultLabel, fieldSchemaArtifact.valueConstraints().get().asControlledTermValueConstraints().defaultValue().get().value().getRight());
  }

  @Test public void testCreateTextAreaField()
  {
    String name = "Field name";
    String description = "Field description";
    Integer minLength = 0;
    Integer maxLength = 10;

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.textAreaFieldBuilder().
      withName(name).
      withDescription(description).
      withMinLength(minLength).
      withMaxLength(maxLength).
      build();

    Assert.assertEquals(FieldInputType.TEXTAREA, fieldSchemaArtifact.fieldUi().inputType());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    Assert.assertEquals(minLength, fieldSchemaArtifact.minLength().get());
    Assert.assertEquals(maxLength, fieldSchemaArtifact.maxLength().get());
  }

}
//  SectionBreakFieldBuilder, ImageFieldBuilder, YouTubeFieldBuilder, RichTextFieldBuilder

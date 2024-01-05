package org.metadatacenter.artifacts.model.core;

import org.junit.Assert;
import org.junit.Test;
import org.metadatacenter.artifacts.model.core.fields.FieldInputType;
import org.metadatacenter.artifacts.model.core.ui.FieldUi;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.metadatacenter.model.ModelNodeNames.FIELD_SCHEMA_ARTIFACT_CONTEXT_PREFIX_MAPPINGS;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_SCHEMA_IRI;

public class FieldSchemaArtifactTest
{

  @Test public void testCreateTextField()
  {
    Map<String, URI> jsonLdContext = FIELD_SCHEMA_ARTIFACT_CONTEXT_PREFIX_MAPPINGS;
    List<URI> jsonLdTypes = Collections.singletonList(URI.create("https://schema.metadatacenter.org/core/TemplateField"));
    URI jsonLdId = URI.create("https://repo.metadatacenter.org/fields/4455");
    URI createdBy = URI.create("http://example.com/user");
    URI modifiedBy = URI.create("http://example.com/user");
    OffsetDateTime createdOn = OffsetDateTime.now();
    OffsetDateTime lastUpdatedOn = OffsetDateTime.now();

    URI jsonSchemaSchemaUri = URI.create(JSON_SCHEMA_SCHEMA_IRI);
    String jsonSchemaType = "object";
    String jsonSchemaTitle = "title";
    String jsonSchemaDescription = "description";
    String name = "Schema Org name";
    String description = "Schema Org description";
    Optional<String> identifier = Optional.of("Schema Org identifier");
    Optional<String> preferredLabel = Optional.of("My label");
    List<String> alternateLabels = Collections.emptyList();
    Version modelVersion = new Version(1, 6, 0);
    Optional<Version> version = Optional.of(new Version(2, 0, 0));
    Optional<Status> status = Optional.of(Status.DRAFT);
    Optional<URI> previousVersion = Optional.of(URI.create("https://repo.metadatacenter.org/template-fields/3232"));
    Optional<URI> derivedFrom = Optional.of(URI.create("https://repo.metadatacenter.org/fields/7666"));
    Optional<URI> propertyUri = Optional.of(URI.create("https://schema.metadatacenter.org/properties/854"));
    Optional<Integer> minItems = Optional.of(2);
    Optional<Integer> maxItems = Optional.of(4);

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.create(jsonSchemaSchemaUri, jsonSchemaType, jsonSchemaTitle, jsonSchemaDescription,
      jsonLdContext, jsonLdTypes, Optional.of(jsonLdId),
      name, description, identifier,
      modelVersion, version, status, previousVersion, derivedFrom, false, minItems, maxItems, propertyUri,
      Optional.of(createdBy), Optional.of(modifiedBy), Optional.of(createdOn), Optional.of(lastUpdatedOn),
      FieldUi.builder().withInputType(FieldInputType.TEXTFIELD).build(), preferredLabel, alternateLabels,Optional.empty());

    Assert.assertEquals(jsonSchemaSchemaUri, fieldSchemaArtifact.jsonSchemaSchemaUri());
    Assert.assertEquals(jsonSchemaType, fieldSchemaArtifact.jsonSchemaType());
    Assert.assertEquals(jsonSchemaTitle, fieldSchemaArtifact.jsonSchemaTitle());
    Assert.assertEquals(jsonSchemaDescription, fieldSchemaArtifact.jsonSchemaDescription());
    Assert.assertEquals(jsonLdTypes, fieldSchemaArtifact.jsonLdTypes());
    Assert.assertEquals(jsonLdId, fieldSchemaArtifact.jsonLdId().get());
    Assert.assertEquals(jsonLdContext, fieldSchemaArtifact.jsonLdContext());
    Assert.assertEquals(createdBy, fieldSchemaArtifact.createdBy().get());
    Assert.assertEquals(modifiedBy, fieldSchemaArtifact.modifiedBy().get());
    Assert.assertEquals(createdOn, fieldSchemaArtifact.createdOn().get());
    Assert.assertEquals(lastUpdatedOn, fieldSchemaArtifact.lastUpdatedOn().get());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    Assert.assertEquals(identifier, fieldSchemaArtifact.identifier());
    Assert.assertEquals(preferredLabel, fieldSchemaArtifact.skosPrefLabel());
    Assert.assertEquals(alternateLabels, fieldSchemaArtifact.skosAlternateLabels());
    Assert.assertEquals(modelVersion, fieldSchemaArtifact.modelVersion());
    Assert.assertEquals(version, fieldSchemaArtifact.version());
    Assert.assertEquals(status, fieldSchemaArtifact.status());
    Assert.assertEquals(previousVersion, fieldSchemaArtifact.previousVersion());
    Assert.assertEquals(derivedFrom, fieldSchemaArtifact.derivedFrom());
    Assert.assertEquals(propertyUri, fieldSchemaArtifact.propertyUri());
    Assert.assertEquals(minItems, fieldSchemaArtifact.minItems());
  }

  @Test public void testCreateTextFieldWithBuilder()
  {
    String name = "My Field";
    String description = "My Field description";
    String identifier = "id3443";
    String preferredLabel = "My label";
    List<String> alternateLabels = Collections.emptyList();
    Version version = new Version(2, 0, 0);
    Status status = Status.DRAFT;
    Boolean requiredValue = true;
    Integer minLength = 0;
    Integer maxLength = 10;
    URI jsonLdId = URI.create("https://repo.metadatacenter.org/template-fields/4455");
    URI createdBy = URI.create("http://example.com/user/1");
    URI modifiedBy = URI.create("http://example.com/user/2");
    OffsetDateTime createdOn = OffsetDateTime.now();
    OffsetDateTime lastUpdatedOn = OffsetDateTime.now();
    URI previousVersion = URI.create("https://repo.metadatacenter.org/fields/3232");
    URI derivedFrom = URI.create("https://repo.metadatacenter.org/fields/7666");
    URI propertyUri = URI.create("https://schema.metadatacenter.org/properties/854");

    FieldSchemaArtifact fieldSchemaArtifact = FieldSchemaArtifact.textFieldBuilder().
      withJsonLdId(jsonLdId).
      withName(name).
      withDescription(description).
      withIdentifier(identifier).
      withPreferredLabel(preferredLabel).
      withAlternateLabels(alternateLabels).
      withVersion(version).
      withStatus(status).
      withRequiredValue(requiredValue).
      withMinLength(minLength).
      withMaxLength(maxLength).
      withCreatedBy(createdBy).
      withCreatedOn(createdOn).
      withModifiedBy(modifiedBy).
      withLastUpdatedOn(lastUpdatedOn).
      withPreviousVersion(previousVersion).
      withDerivedFrom(derivedFrom).
      withPropertyUri(propertyUri).
      build();

    Assert.assertEquals(jsonLdId, fieldSchemaArtifact.jsonLdId().get());
    Assert.assertEquals(createdBy, fieldSchemaArtifact.createdBy().get());
    Assert.assertEquals(modifiedBy, fieldSchemaArtifact.modifiedBy().get());
    Assert.assertEquals(createdOn, fieldSchemaArtifact.createdOn().get());
    Assert.assertEquals(lastUpdatedOn, fieldSchemaArtifact.lastUpdatedOn().get());
    Assert.assertEquals(name, fieldSchemaArtifact.name());
    Assert.assertEquals(description, fieldSchemaArtifact.description());
    Assert.assertEquals(identifier, fieldSchemaArtifact.identifier().get());
    Assert.assertEquals(preferredLabel, fieldSchemaArtifact.skosPrefLabel().get());
    Assert.assertEquals(alternateLabels, fieldSchemaArtifact.skosAlternateLabels());
    Assert.assertEquals(version, fieldSchemaArtifact.version().get());
    Assert.assertEquals(status, fieldSchemaArtifact.status().get());
    Assert.assertEquals(previousVersion, fieldSchemaArtifact.previousVersion().get());
    Assert.assertEquals(derivedFrom, fieldSchemaArtifact.derivedFrom().get());
    Assert.assertEquals(propertyUri, fieldSchemaArtifact.propertyUri().get());
    Assert.assertEquals(requiredValue, fieldSchemaArtifact.requiredValue());
    Assert.assertEquals(minLength, fieldSchemaArtifact.minLength().get());
    Assert.assertEquals(maxLength, fieldSchemaArtifact.maxLength().get());
  }

}
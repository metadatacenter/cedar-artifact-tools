package org.metadatacenter.artifacts.model.core;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.metadatacenter.model.ModelNodeNames.FIELD_SCHEMA_ARTIFACT_TYPE_IRI;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_OBJECT;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_SCHEMA_IRI;
import static org.metadatacenter.model.ModelNodeNames.SCHEMA_ARTIFACT_CONTEXT_PREFIX_MAPPINGS;

public class FieldSchemaArtifactBuilder
{
  private Map<String, URI> jsonLdContext = new HashMap<>(SCHEMA_ARTIFACT_CONTEXT_PREFIX_MAPPINGS);
  private List<URI> jsonLdTypes = List.of(URI.create(FIELD_SCHEMA_ARTIFACT_TYPE_IRI));
  private Optional<URI> jsonLdId = Optional.empty();
  private Optional<URI> createdBy = Optional.empty();
  private Optional<URI> modifiedBy = Optional.empty();
  private Optional<OffsetDateTime> createdOn = Optional.empty();
  private Optional<OffsetDateTime> lastUpdatedOn = Optional.empty();
  private URI jsonSchemaSchemaUri = URI.create(JSON_SCHEMA_SCHEMA_IRI);
  private String jsonSchemaType = JSON_SCHEMA_OBJECT;
  private String jsonSchemaTitle = "";
  private String jsonSchemaDescription = "";
  private String name;
  private String description = "";
  private Optional<String> identifier = Optional.empty();
  private Version modelVersion = new Version(1, 6, 0); // TODO Put 1.6.0 in ModelNodeNames
  private Optional<Version> version = Optional.of(new Version(0, 0, 1)); // TODO Put 0.0.1. in ModelNodeNames
  private Optional<Status> status = Optional.of(Status.DRAFT);
  private Optional<URI> previousVersion = Optional.empty();
  private Optional<URI> derivedFrom = Optional.empty();
  private Optional<String> skosPrefLabel = Optional.empty();
  private List<String> skosAlternateLabels = Collections.emptyList();
  private boolean isMultiple = false;
  private Optional<Integer> minItems = Optional.empty();
  private Optional<Integer> maxItems = Optional.empty();
  private Optional<URI> propertyUri = Optional.empty();
  private FieldUi fieldUi;
  private Optional<ValueConstraints> valueConstraints = Optional.empty();

  public FieldSchemaArtifactBuilder() {}

  public FieldSchemaArtifactBuilder withJsonLdId(URI jsonLdId)
  {
    this.jsonLdId = Optional.ofNullable(jsonLdId);
    return this;
  }

  public FieldSchemaArtifactBuilder withJsonLdContext(Map<String, URI> jsonLdContext)
  {
    this.jsonLdContext = jsonLdContext;
    return this;
  }

  public FieldSchemaArtifactBuilder withCreatedBy(URI createdBy)
  {
    this.createdBy = Optional.ofNullable(createdBy);
    return this;
  }

  public FieldSchemaArtifactBuilder withModifiedBy(URI modifiedBy)
  {
    this.modifiedBy = Optional.ofNullable(modifiedBy);
    return this;
  }

  public FieldSchemaArtifactBuilder withCreatedOn(OffsetDateTime createdOn)
  {
    this.createdOn = Optional.ofNullable(createdOn);
    return this;
  }

  public FieldSchemaArtifactBuilder withLastUpdatedOn(OffsetDateTime lastUpdatedOn)
  {
    this.lastUpdatedOn = Optional.ofNullable(lastUpdatedOn);
    return this;
  }

  public FieldSchemaArtifactBuilder withJsonSchemaSchemaUri(URI jsonSchemaSchemaUri)
  {
    this.jsonSchemaSchemaUri = jsonSchemaSchemaUri;
    return this;
  }

  public FieldSchemaArtifactBuilder withJsonSchemaType(String jsonSchemaType)
  {
    this.jsonSchemaType = jsonSchemaType;
    return this;
  }

  public FieldSchemaArtifactBuilder withJsonSchemaTitle(String jsonSchemaTitle)
  {
    this.jsonSchemaTitle = jsonSchemaTitle;
    return this;
  }

  public FieldSchemaArtifactBuilder withJsonSchemaDescription(String jsonSchemaDescription)
  {
    this.jsonSchemaDescription = jsonSchemaDescription;
    return this;
  }

  public FieldSchemaArtifactBuilder withJsonLdTypes(List<URI> jsonLdTypes)
  {
    this.jsonLdTypes = jsonLdTypes;
    return this;
  }

  public FieldSchemaArtifactBuilder withName(String name)
  {
    this.name = name;

    if (this.jsonSchemaTitle.isEmpty())
      this.jsonSchemaTitle = name + " field schema";

    if (this.jsonSchemaDescription.isEmpty())
      this.jsonSchemaDescription = name + " field schema generated by the CEDAR Artifact Library";

    return this;
  }

  public FieldSchemaArtifactBuilder withDescription(String description)
  {
    this.description = description;
    return this;
  }

  public FieldSchemaArtifactBuilder withIdentifier(String identifier)
  {
    this.identifier = Optional.ofNullable(identifier);
    return this;
  }

  public FieldSchemaArtifactBuilder withModelVersion(Version modelVersion)
  {
    this.modelVersion = modelVersion;
    return this;
  }

  public FieldSchemaArtifactBuilder withVersion(Version version)
  {
    this.version = Optional.ofNullable(version);
    return this;
  }

  public FieldSchemaArtifactBuilder withStatus(Status status)
  {
    this.status = Optional.ofNullable(status);
    return this;
  }

  public FieldSchemaArtifactBuilder withPreviousVersion(URI previousVersion)
  {
    this.previousVersion = Optional.ofNullable(previousVersion);
    return this;
  }

  public FieldSchemaArtifactBuilder withDerivedFrom(URI derivedFrom)
  {
    this.derivedFrom = Optional.ofNullable(derivedFrom);
    return this;
  }

  public FieldSchemaArtifactBuilder withFieldUi(FieldUi fieldUi)
  {
    this.fieldUi = fieldUi;
    return this;
  }

  public FieldSchemaArtifactBuilder withValueConstraints(ValueConstraints valueConstraints)
  {
    this.valueConstraints = Optional.ofNullable(valueConstraints);
    return this;
  }

  public FieldSchemaArtifactBuilder withSkosPrefLabel(String skosPrefLabel)
  {
    this.skosPrefLabel = Optional.ofNullable(skosPrefLabel);
    return this;
  }

  public FieldSchemaArtifactBuilder withSkosAlternateLabels(List<String> skosAlternateLabels)
  {
    this.skosAlternateLabels = skosAlternateLabels;
    return this;
  }

  public FieldSchemaArtifactBuilder withIsMultiple(boolean isMultiple)
  {
    this.isMultiple = isMultiple;
    return this;
  }

  public FieldSchemaArtifactBuilder withMinItems(Integer minItems)
  {
    this.minItems = Optional.ofNullable(minItems);
    return this;
  }

  public FieldSchemaArtifactBuilder withMaxItems(Integer maxItems)
  {
    this.minItems = Optional.ofNullable(maxItems);
    return this;
  }

  public FieldSchemaArtifactBuilder withPropertyUri(URI propertyUri)
  {
    this.propertyUri = Optional.ofNullable(propertyUri);
    return this;
  }

  public FieldSchemaArtifact build()
  {
    return FieldSchemaArtifact.create(jsonLdContext, jsonLdTypes, jsonLdId,
      createdBy, modifiedBy, createdOn, lastUpdatedOn,
      jsonSchemaSchemaUri, jsonSchemaType, jsonSchemaTitle, jsonSchemaDescription,
      name, description, identifier,
      modelVersion, version, status, previousVersion, derivedFrom,
      skosPrefLabel, skosAlternateLabels, isMultiple, minItems, maxItems, propertyUri,
      fieldUi, valueConstraints);
  }
}
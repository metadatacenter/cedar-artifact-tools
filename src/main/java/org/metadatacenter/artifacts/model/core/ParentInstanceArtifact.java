package org.metadatacenter.artifacts.model.core;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public sealed interface ParentInstanceArtifact extends ParentArtifact, JsonLdArtifact, MonitoredArtifact
  permits TemplateInstanceArtifact, ElementInstanceArtifact
{
  LinkedHashMap<String, URI> jsonLdContext();

  Optional<URI> jsonLdId();

  Optional<URI> createdBy();

  Optional<URI> modifiedBy();

  Optional<OffsetDateTime> createdOn();

  Optional<OffsetDateTime> lastUpdatedOn();

  Optional<String> name();

  Optional<String> description();

  List<String> childKeys();

  // field key->field instance artifact
  LinkedHashMap<String, FieldInstanceArtifact> singleInstanceFieldInstances();

  // field key->[field instance artifact]
  LinkedHashMap<String, List<FieldInstanceArtifact>> multiInstanceFieldInstances();

  // element key->element instance artifact
  LinkedHashMap<String, ElementInstanceArtifact> singleInstanceElementInstances();

  // field key->[field instance artifact]
  LinkedHashMap<String, List<ElementInstanceArtifact>> multiInstanceElementInstances();

  // attribute-value field group name->(attribute-value field instance name->field instance artifact)
  LinkedHashMap<String, Map<String, FieldInstanceArtifact>> attributeValueFieldInstanceGroups();
}

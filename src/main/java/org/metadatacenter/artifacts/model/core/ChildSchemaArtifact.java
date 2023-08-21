package org.metadatacenter.artifacts.model.core;

import java.net.URI;
import java.util.Optional;

public sealed interface ChildSchemaArtifact permits ElementSchemaArtifact, FieldSchemaArtifact
{
  String name();

  boolean isMultiple();

  Optional<Integer> minItems();

  Optional<Integer> maxItems();

  Optional<URI> propertyUri();
}

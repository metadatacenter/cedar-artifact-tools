package org.metadatacenter.model.core;

import java.net.URI;

public class SchemaArtifact extends Artifact
{
  private final URI jsonSchemaSchema;
  private final String modelVersion;
  private final String version, previousVersion, status;

  public SchemaArtifact(Artifact artifact, URI jsonSchemaSchema, String modelVersion, String version,
    String previousVersion, String status)
  {
    super(artifact);
    this.jsonSchemaSchema = jsonSchemaSchema;
    this.modelVersion = modelVersion;
    this.version = version;
    this.previousVersion = previousVersion;
    this.status = status;
  }

  public SchemaArtifact(SchemaArtifact schemaArtifact)
  {
    super(schemaArtifact);
    this.jsonSchemaSchema = schemaArtifact.jsonSchemaSchema;
    this.modelVersion = schemaArtifact.modelVersion;
    this.version = schemaArtifact.version;
    this.previousVersion = schemaArtifact.previousVersion;
    this.status = schemaArtifact.status;
  }

  public URI getJsonSchemaSchema()
  {
    return jsonSchemaSchema;
  }

  public String getModelVersion()
  {
    return modelVersion;
  }

  public String getVersion()
  {
    return version;
  }

  public String getPreviousVersion()
  {
    return previousVersion;
  }

  public String getStatus()
  {
    return status;
  }

  @Override public String toString()
  {
    return super.toString() + "\n SchemaArtifact{" + "jsonSchemaSchema='" + jsonSchemaSchema + '\'' + ", modelVersion='" + modelVersion + '\''
      + ", version='" + version + '\'' + ", previousVersion='" + previousVersion + '\'' + ", status='" + status + '\''
      + '}';
  }
}

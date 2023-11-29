package org.metadatacenter.artifacts.model.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.metadatacenter.artifacts.model.core.BranchValueConstraint;
import org.metadatacenter.artifacts.model.core.ClassValueConstraint;
import org.metadatacenter.artifacts.model.core.ControlledTermValueConstraints;
import org.metadatacenter.artifacts.model.core.DefaultValue;
import org.metadatacenter.artifacts.model.core.ElementInstanceArtifact;
import org.metadatacenter.artifacts.model.core.ElementSchemaArtifact;
import org.metadatacenter.artifacts.model.core.ElementUi;
import org.metadatacenter.artifacts.model.core.FieldInputType;
import org.metadatacenter.artifacts.model.core.FieldInstanceArtifact;
import org.metadatacenter.artifacts.model.core.FieldSchemaArtifact;
import org.metadatacenter.artifacts.model.core.FieldUi;
import org.metadatacenter.artifacts.model.core.InputTimeFormat;
import org.metadatacenter.artifacts.model.core.LiteralValueConstraint;
import org.metadatacenter.artifacts.model.core.NumericFieldUi;
import org.metadatacenter.artifacts.model.core.NumericType;
import org.metadatacenter.artifacts.model.core.NumericDefaultValue;
import org.metadatacenter.artifacts.model.core.NumericValueConstraints;
import org.metadatacenter.artifacts.model.core.OntologyValueConstraint;
import org.metadatacenter.artifacts.model.core.StaticFieldUi;
import org.metadatacenter.artifacts.model.core.Status;
import org.metadatacenter.artifacts.model.core.TemporalDefaultValue;
import org.metadatacenter.artifacts.model.core.TemporalValueConstraints;
import org.metadatacenter.artifacts.model.core.TextDefaultValue;
import org.metadatacenter.artifacts.model.core.TemplateInstanceArtifact;
import org.metadatacenter.artifacts.model.core.TemplateSchemaArtifact;
import org.metadatacenter.artifacts.model.core.TemplateUi;
import org.metadatacenter.artifacts.model.core.TemporalFieldUi;
import org.metadatacenter.artifacts.model.core.TemporalGranularity;
import org.metadatacenter.artifacts.model.core.TemporalType;
import org.metadatacenter.artifacts.model.core.ControlledTermDefaultValue;
import org.metadatacenter.artifacts.model.core.TextValueConstraints;
import org.metadatacenter.artifacts.model.core.ValueConstraints;
import org.metadatacenter.artifacts.model.core.ControlledTermValueConstraintsAction;
import org.metadatacenter.artifacts.model.core.ValueConstraintsActionType;
import org.metadatacenter.artifacts.model.core.ValueSetValueConstraint;
import org.metadatacenter.artifacts.model.core.ValueType;
import org.metadatacenter.artifacts.model.core.Version;
import org.metadatacenter.model.ModelNodeValues;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.metadatacenter.model.ModelNodeNames.ARTIFACT_CONTEXT_ENTRIES;
import static org.metadatacenter.model.ModelNodeNames.BIBO_STATUS;
import static org.metadatacenter.model.ModelNodeNames.ELEMENT_INSTANCE_ARTIFACT_KEYWORDS;
import static org.metadatacenter.model.ModelNodeNames.ELEMENT_SCHEMA_ARTIFACT_TYPE_IRI;
import static org.metadatacenter.model.ModelNodeNames.FIELD_INSTANCE_ARTIFACT_KEYWORDS;
import static org.metadatacenter.model.ModelNodeNames.FIELD_SCHEMA_ARTIFACT_TYPE_IRI;
import static org.metadatacenter.model.ModelNodeNames.INPUT_TYPES;
import static org.metadatacenter.model.ModelNodeNames.INSTANCE_ARTIFACT_KEYWORDS;
import static org.metadatacenter.model.ModelNodeNames.JSON_LD_CONTEXT;
import static org.metadatacenter.model.ModelNodeNames.JSON_LD_ID;
import static org.metadatacenter.model.ModelNodeNames.JSON_LD_TYPE;
import static org.metadatacenter.model.ModelNodeNames.JSON_LD_VALUE;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_ARRAY;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_DESCRIPTION;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_ENUM;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_ITEMS;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_MAX_ITEMS;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_MIN_ITEMS;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_OBJECT;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_PROPERTIES;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_SCHEMA;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_TITLE;
import static org.metadatacenter.model.ModelNodeNames.JSON_SCHEMA_TYPE;
import static org.metadatacenter.model.ModelNodeNames.OSLC_MODIFIED_BY;
import static org.metadatacenter.model.ModelNodeNames.PAV_CREATED_BY;
import static org.metadatacenter.model.ModelNodeNames.PAV_CREATED_ON;
import static org.metadatacenter.model.ModelNodeNames.PAV_DERIVED_FROM;
import static org.metadatacenter.model.ModelNodeNames.PAV_LAST_UPDATED_ON;
import static org.metadatacenter.model.ModelNodeNames.PAV_PREVIOUS_VERSION;
import static org.metadatacenter.model.ModelNodeNames.PAV_VERSION;
import static org.metadatacenter.model.ModelNodeNames.RDFS_LABEL;
import static org.metadatacenter.model.ModelNodeNames.SCHEMA_ARTIFACT_TYPE_IRIS;
import static org.metadatacenter.model.ModelNodeNames.SCHEMA_IS_BASED_ON;
import static org.metadatacenter.model.ModelNodeNames.SCHEMA_ORG_DESCRIPTION;
import static org.metadatacenter.model.ModelNodeNames.SCHEMA_ORG_IDENTIFIER;
import static org.metadatacenter.model.ModelNodeNames.SCHEMA_ORG_NAME;
import static org.metadatacenter.model.ModelNodeNames.SCHEMA_ORG_SCHEMA_VERSION;
import static org.metadatacenter.model.ModelNodeNames.SKOS_ALTLABEL;
import static org.metadatacenter.model.ModelNodeNames.SKOS_NOTATION;
import static org.metadatacenter.model.ModelNodeNames.SKOS_PREFLABEL;
import static org.metadatacenter.model.ModelNodeNames.STATIC_FIELD_SCHEMA_ARTIFACT_TYPE_IRI;
import static org.metadatacenter.model.ModelNodeNames.TEMPLATE_INSTANCE_ARTIFACT_KEYWORDS;
import static org.metadatacenter.model.ModelNodeNames.TEMPLATE_SCHEMA_ARTIFACT_TYPE_IRI;
import static org.metadatacenter.model.ModelNodeNames.UI;
import static org.metadatacenter.model.ModelNodeNames.UI_CONTENT;
import static org.metadatacenter.model.ModelNodeNames.UI_FIELD_INPUT_TYPE;
import static org.metadatacenter.model.ModelNodeNames.UI_FOOTER;
import static org.metadatacenter.model.ModelNodeNames.UI_HEADER;
import static org.metadatacenter.model.ModelNodeNames.UI_HIDDEN;
import static org.metadatacenter.model.ModelNodeNames.UI_INPUT_TIME_FORMAT;
import static org.metadatacenter.model.ModelNodeNames.UI_ORDER;
import static org.metadatacenter.model.ModelNodeNames.UI_PAGES;
import static org.metadatacenter.model.ModelNodeNames.UI_PROPERTY_DESCRIPTIONS;
import static org.metadatacenter.model.ModelNodeNames.UI_PROPERTY_LABELS;
import static org.metadatacenter.model.ModelNodeNames.UI_TEMPORAL_GRANULARITY;
import static org.metadatacenter.model.ModelNodeNames.UI_TIMEZONE_ENABLED;
import static org.metadatacenter.model.ModelNodeNames.UI_VALUE_RECOMMENDATION_ENABLED;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_ACRONYM;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_ACTION;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_ACTIONS;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_ACTION_TO;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_BRANCHES;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_CLASSES;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_DECIMAL_PLACE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_DEFAULT_VALUE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_DEFAULT_VALUE_TERM_URI;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_LABEL;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_LITERALS;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_MAX_DEPTH;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_MAX_NUMBER_VALUE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_MAX_STRING_LENGTH;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_MIN_NUMBER_VALUE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_MIN_STRING_LENGTH;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_MULTIPLE_CHOICE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_NAME;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_NUMBER_TYPE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_NUM_TERMS;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_ONTOLOGIES;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_PREFLABEL;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_REQUIRED_VALUE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_SELECTED_BY_DEFAULT;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_SOURCE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_SOURCE_URI;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_TEMPORAL_TYPE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_TERM_URI;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_TYPE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_UNIT_OF_MEASURE;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_URI;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_VALUE_SETS;
import static org.metadatacenter.model.ModelNodeNames.VALUE_CONSTRAINTS_VS_COLLECTION;

public class JsonSchemaArtifactReader implements ArtifactReader<ObjectNode>
{
  public JsonSchemaArtifactReader()
  {
  }

  /**
   * Read a JSON Schema specification for a template schema artifact
   * <p></p>
   * An example template schema artifact specification could look as follows:
   * <pre>
   * {
   *   "$schema": "http://json-schema.org/draft-04/schema#",
   *   "type": "object",
   *   "title": "Study template schema", "description": "Study template schema generated by the CEDAR Artifact Library",
   *   "schema:name": "Study", "schema:description": "Study template",
   *   "schema:schemaVersion": "1.6.0", "schema:identifier": "id1212132",
   *   "pav:version": "0.0.1", "bibo:status": "bibo:draft",
   *   "pav:previousVersion": "https://repo.metadatacenter.org/templates/54343",
   *   "pav:derivedFrom": "https://repo.metadatacenter.org/templates/232323",
   *   "@id": "https://repo.metadatacenter.org/templates/474378",
   *   "pav:createdOn": "2023-07-28T11:10:41-07:00", "pav:createdBy": "https://metadatacenter.org/users/656433",
   *   "pav:lastUpdatedOn": "2023-07-28T11:10:41-07:00", "oslc:modifiedBy": "https://metadatacenter.org/users/524332",
   *   "@context": { ... },
   *   "properties": { ... },
   *   "required": [ "@context", "@id",
   *                 "schema:isBasedOn", "schema:name", "schema:description",
   *                 "pav:createdOn", "pav:createdBy", "pav:lastUpdatedOn", "oslc:modifiedBy",
   *                 "Child Name 1", ... "Child Name n"],
   *   "additionalProperties": false,
   *   "_ui": { ... }
   *  }
   * </pre>
   */
  public TemplateSchemaArtifact readTemplateSchemaArtifact(ObjectNode objectNode)
  {
    return readTemplateSchemaArtifact(objectNode, "");
  }

  /**
   * Read a JSON Schema specification for an element schema artifact
   * <p></p>
   * An example JSON Schema element artifact specification could look as follows:
   * <pre>
   * {
   *   "$schema": "http://json-schema.org/draft-04/schema#",
   *   "type": "object",
   *   "title": "Address element schema", "description: "Address element schema generated by the CEDAR Template Editor 2.6.19",
   *   "schema:name": "Address", "schema:description": "Address element",
   *   "schema:schemaVersion": "1.6.0", "schema:identifier": "id999434",
   *   "pav:version": "0.0.1", "bibo:status": "bibo:draft",
   *   "pav:previousVersion": "https://repo.metadatacenter.org/template-elements/54343",
   *   "pav:derivedFrom": "https://repo.metadatacenter.org/template-elements/232323",
   *   "@type": "https://schema.metadatacenter.org/core/TemplateElement",
   *   "@id": "https://repo.metadatacenter.org/templates-elements/474378",
   *   "pav:createdOn": "2023-07-28T11:10:41-07:00", "pav:createdBy": "https://metadatacenter.org/users/656433",
   *   "pav:lastUpdatedOn": "2023-07-28T11:10:41-07:00", "oslc:modifiedBy": "https://metadatacenter.org/users/524332",
   *   "@context": { ... },
   *   "properties": { ... },
   *   "required": [ "@context", "@id", "Child Name 1", ... "Child Name n"],
   *   "additionalProperties": false,
   *   "_ui": { ... }
   *  }
   * </pre>
   */
  public ElementSchemaArtifact readElementSchemaArtifact(ObjectNode objectNode)
  {
    String name = readRequiredString(objectNode, "/", SCHEMA_ORG_NAME);
    return readElementSchemaArtifact(objectNode, "", name, false, Optional.empty(), Optional.empty(), Optional.empty());
  }

  /**
   * Read a JSON Schema specification for a field schema artifact
   * <p></p>
   * An example JSON Schema field artifact could look as follows:
   * <pre>
   * {
   *   "$schema": "http://json-schema.org/draft-04/schema#",
   *   "type": "object",
   *   "title": "Disease field schema", "description": "Disease field schema generated by the CEDAR Template Editor 2.6.19",
   *   "schema:name": "Disease", "schema:description": "Please enter a disease",
   *   "schema:schemaVersion": "1.6.0", "schema:identifier": "id5666",
   *   "pav:version": "0.0.1", "bibo:status": "bibo:draft",
   *   "pav:previousVersion": "https://repo.metadatacenter.org/templates/435454",
   *   "pav:derivedFrom": "https://repo.metadatacenter.org/templates/893443",
   *   "@type": "https://schema.metadatacenter.org/core/Template",
   *   "@id": "https://repo.metadatacenter.org/templates/127666",
   *   "pav:createdOn": "2023-07-28T11:10:41-07:00", "pav:createdBy": "https://metadatacenter.org/users/656433",
   *   "pav:lastUpdatedOn": "2023-07-28T11:10:41-07:00", "oslc:modifiedBy": "https://metadatacenter.org/users/524332",
   *   "@context": { ... },
   *   "properties": { ... },
   *   "required": [ "@id" ],
   *   "additionalProperties": false,
   *   "skos:prefLabel": "Condition", "skos:altLabel": [ "Problem", "Illness" ],
   *   "_valueConstraints": { ... },
   *   "_ui": { ... }
   *  }
   * </pre>
   */
  public FieldSchemaArtifact readFieldSchemaArtifact(ObjectNode objectNode)
  {
    String name = readRequiredString(objectNode, "/", SCHEMA_ORG_NAME);
    return readFieldSchemaArtifact(objectNode, "", name, false, Optional.empty(), Optional.empty(), Optional.empty());
  }

  /**
   * Read a JSON-LD template instance artifact
   * <p></p>
   * An example template instance artifact could look as follows:
   * <pre>
   *   {
   *   "@context": {
   *     "xsd": "http://www.w3.org/2001/XMLSchema#",
   *     "pav": "http://purl.org/pav/",
   *     "schema": "http://schema.org/",
   *     "oslc": "http://open-services.net/ns/core#",
   *     "skos": "http://www.w3.org/2004/02/skos/core#",
   *     "schema:name": {"@type": "xsd:string" },
   *     "schema:description": { "@type": "xsd:string" },
   *     "pav:derivedFrom": { "@type": "@id" },
   *     "pav:createdOn": { "@type": "xsd:dateTime" },
   *     "pav:createdBy": { "@type": "@id" },
   *     "pav:lastUpdatedOn": { "@type": "xsd:dateTime" },
   *     "oslc:modifiedBy": { "@type": "@id" },
   *     "schema:isBasedOn": { "@type": "@id" },
   *     "skos:notation": { "@type": "xsd:string" },
   *     "rdfs:label": { "@type": "xsd:string" }
   *   },
   *   "@id": "https://repo.metadatacenter.org/template-instances/66776767"
   *   "schema:isBasedOn": "https://repo.metadatacenter.org/templates/5454545",
   *   "schema:name": "Study metadata", "schema:description": "",
   *   "pav:createdOn": "2023-08-01T11:03:05-07:00",
   *   "pav:createdBy": "https://metadatacenter.org/users/344343",
   *   "pav:lastUpdatedOn": "2023-08-01T11:03:05-07:00",
   *   "oslc:modifiedBy": "https://metadatacenter.org/users/5666565"
   * }
   * </pre>
   */
  public TemplateInstanceArtifact readTemplateInstanceArtifact(ObjectNode objectNode)
  {
    return readTemplateInstanceArtifact(objectNode, "");
  }

  private TemplateSchemaArtifact readTemplateSchemaArtifact(ObjectNode objectNode, String path)
  {
    Map<String, URI> jsonLdContext = readFieldNameUriValueMap(objectNode, path, JSON_LD_CONTEXT);
    List<URI> jsonLdTypes = readUriArray(objectNode, path, JSON_LD_TYPE);
    Optional<URI> jsonLdId = readUri(objectNode, path, JSON_LD_ID);
    Optional<URI> createdBy = readUri(objectNode, path, PAV_CREATED_BY);
    Optional<URI> modifiedBy = readUri(objectNode, path, OSLC_MODIFIED_BY);
    Optional<OffsetDateTime> createdOn = readOffsetDateTime(objectNode, path, PAV_CREATED_ON);
    Optional<OffsetDateTime> lastUpdatedOn = readOffsetDateTime(objectNode, path, PAV_LAST_UPDATED_ON);
    URI jsonSchemaSchemaUri = readRequiredUri(objectNode, path, JSON_SCHEMA_SCHEMA);
    String jsonSchemaType = readRequiredString(objectNode, path, JSON_SCHEMA_TYPE);
    String jsonSchemaTitle = readRequiredString(objectNode, path, JSON_SCHEMA_TITLE);

    String jsonSchemaDescription = readString(objectNode, path, JSON_SCHEMA_DESCRIPTION, "");
    Version modelVersion = Version.fromString(readRequiredString(objectNode, path, SCHEMA_ORG_SCHEMA_VERSION));
    String name = readRequiredString(objectNode, path, SCHEMA_ORG_NAME);
    String description = readRequiredString(objectNode, path, SCHEMA_ORG_DESCRIPTION);
    Optional<String> identifier = readString(objectNode, path, SCHEMA_ORG_IDENTIFIER);
    Optional<Version> version = readVersion(objectNode, path, PAV_VERSION);
    Optional<Status> status = readStatus(objectNode, path, BIBO_STATUS);
    Optional<URI> previousVersion = readUri(objectNode, path, PAV_PREVIOUS_VERSION);
    Optional<URI> derivedFrom = readUri(objectNode, path, PAV_DERIVED_FROM);
    Map<String, FieldSchemaArtifact> fieldSchemas = new HashMap<>();
    Map<String, ElementSchemaArtifact> elementSchemas = new HashMap<>();
    Map<String, URI> childPropertyUris = getChildPropertyUris(objectNode, path);
    TemplateUi templateUi = readTemplateUi(objectNode, path, UI);

    checkTemplateSchemaArtifactJsonLdType(jsonLdTypes, path);

    readNestedFieldAndElementSchemaArtifacts(objectNode, path, fieldSchemas, elementSchemas, childPropertyUris);

    return TemplateSchemaArtifact.create(jsonSchemaSchemaUri, jsonSchemaType, jsonSchemaTitle, jsonSchemaDescription,
      jsonLdContext, jsonLdTypes, jsonLdId,
      name, description, identifier,
      modelVersion, version, status, previousVersion, derivedFrom,
      createdBy, modifiedBy, createdOn, lastUpdatedOn,
      fieldSchemas, elementSchemas, templateUi);
  }

  private ElementSchemaArtifact readElementSchemaArtifact(ObjectNode objectNode, String path,
    String name, boolean isMultiple, Optional<Integer> minItems, Optional<Integer> maxItems, Optional<URI> propertyUri)
  {
    Map<String, URI> jsonLdContext = readFieldNameUriValueMap(objectNode, path, JSON_LD_CONTEXT);
    List<URI> jsonLdTypes = readUriArray(objectNode, path, JSON_LD_TYPE);
    Optional<URI> jsonLdId = readUri(objectNode, path, JSON_LD_ID);
    Optional<URI> createdBy = readUri(objectNode, path, PAV_CREATED_BY);
    Optional<URI> modifiedBy = readUri(objectNode, path, OSLC_MODIFIED_BY);
    Optional<OffsetDateTime> createdOn = readOffsetDateTime(objectNode, path, PAV_CREATED_ON);
    Optional<OffsetDateTime> lastUpdatedOn = readOffsetDateTime(objectNode, path, PAV_LAST_UPDATED_ON);
    URI jsonSchemaSchemaUri = readRequiredUri(objectNode, path, JSON_SCHEMA_SCHEMA);
    String jsonSchemaType = readRequiredString(objectNode, path, JSON_SCHEMA_TYPE);
    String jsonSchemaTitle = readRequiredString(objectNode, path, JSON_SCHEMA_TITLE);
    String jsonSchemaDescription = readString(objectNode, path, JSON_SCHEMA_DESCRIPTION, "");
    Version modelVersion = Version.fromString(readRequiredString(objectNode, path, SCHEMA_ORG_SCHEMA_VERSION));
    String description = readRequiredString(objectNode, path, SCHEMA_ORG_DESCRIPTION);
    Optional<String> identifier = readString(objectNode, path, SCHEMA_ORG_IDENTIFIER);
    Optional<Version> version = readVersion(objectNode, path, PAV_VERSION);
    Optional<Status> status = readStatus(objectNode, path, BIBO_STATUS);
    Optional<URI> previousVersion = readUri(objectNode, path, PAV_PREVIOUS_VERSION);
    Optional<URI> derivedFrom = readUri(objectNode, path, PAV_DERIVED_FROM);
    Map<String, FieldSchemaArtifact> fieldSchemas = new HashMap<>();
    Map<String, ElementSchemaArtifact> elementSchemas = new HashMap<>();
    ElementUi elementUi = readElementUi(objectNode, path, UI);
    Map<String, URI> childPropertyUris = getChildPropertyUris(objectNode, path);

    checkElementSchemaArtifactJsonLdType(jsonLdTypes, path);

    readNestedFieldAndElementSchemaArtifacts(objectNode, path, fieldSchemas, elementSchemas, childPropertyUris);

    return ElementSchemaArtifact.create(jsonSchemaSchemaUri, jsonSchemaType, jsonSchemaTitle, jsonSchemaDescription,
      jsonLdContext, jsonLdTypes, jsonLdId,
      name, description, identifier,
      modelVersion, version, status, previousVersion, derivedFrom,
      createdBy, modifiedBy, createdOn, lastUpdatedOn,
      fieldSchemas, elementSchemas, elementUi,
      isMultiple, minItems, maxItems, propertyUri);
  }

  private FieldSchemaArtifact readFieldSchemaArtifact(ObjectNode objectNode, String path,
    String name, boolean isMultiple, Optional<Integer> minItems, Optional<Integer> maxItems, Optional<URI> propertyUri)
  {
    Map<String, URI> jsonLdContext = readFieldNameUriValueMap(objectNode, path, JSON_LD_CONTEXT);
    List<URI> jsonLdTypes = readUriArray(objectNode, path, JSON_LD_TYPE);
    Optional<URI> jsonLdId = readUri(objectNode, path, JSON_LD_ID);
    Optional<URI> createdBy = readUri(objectNode, path, PAV_CREATED_BY);
    Optional<URI> modifiedBy = readUri(objectNode, path, OSLC_MODIFIED_BY);
    Optional<OffsetDateTime> createdOn = readOffsetDateTime(objectNode, path, PAV_CREATED_ON);
    Optional<OffsetDateTime> lastUpdatedOn = readOffsetDateTime(objectNode, path, PAV_LAST_UPDATED_ON);
    URI jsonSchemaSchemaUri = readRequiredUri(objectNode, path, JSON_SCHEMA_SCHEMA);
    String jsonSchemaType = readRequiredString(objectNode, path, JSON_SCHEMA_TYPE);
    String jsonSchemaTitle = readRequiredString(objectNode, path, JSON_SCHEMA_TITLE);
    String jsonSchemaDescription = readString(objectNode, path, JSON_SCHEMA_DESCRIPTION, "");
    Version modelVersion = Version.fromString(readRequiredString(objectNode, path, SCHEMA_ORG_SCHEMA_VERSION));
    String description = readRequiredString(objectNode, path, SCHEMA_ORG_DESCRIPTION);
    Optional<String> identifier = readString(objectNode, path, SCHEMA_ORG_IDENTIFIER);
    Optional<Version> version = readVersion(objectNode, path, PAV_VERSION);
    Optional<Status> status = readStatus(objectNode, path, BIBO_STATUS);
    Optional<URI> previousVersion = readUri(objectNode, path, PAV_PREVIOUS_VERSION);
    Optional<URI> derivedFrom = readUri(objectNode, path, PAV_DERIVED_FROM);
    Optional<String> skosPrefLabel = readString(objectNode, path, SKOS_PREFLABEL);
    List<String> skosAlternateLabels = readStringArray(objectNode, path, SKOS_ALTLABEL);
    FieldUi fieldUi = readFieldUi(objectNode, path, UI);
    Optional<ValueConstraints> valueConstraints = readValueConstraints(objectNode, path, VALUE_CONSTRAINTS, fieldUi.inputType());

    checkFieldSchemaArtifactJsonLdType(jsonLdTypes, path);

    return FieldSchemaArtifact.create(jsonSchemaSchemaUri, jsonSchemaType, jsonSchemaTitle, jsonSchemaDescription,
      jsonLdContext, jsonLdTypes, jsonLdId,
      name, description, identifier,
      modelVersion, version, status, previousVersion, derivedFrom,
      isMultiple, minItems, maxItems, propertyUri,
      createdBy, modifiedBy, createdOn, lastUpdatedOn,
      fieldUi,  skosPrefLabel, skosAlternateLabels, valueConstraints);
  }

  private void readNestedFieldAndElementSchemaArtifacts(ObjectNode objectNode, String path,
    Map<String, FieldSchemaArtifact> fieldSchemas, Map<String, ElementSchemaArtifact> elementSchemas,
    Map<String, URI> childPropertyUris)
  {
    JsonNode propertiesNode = objectNode.get(JSON_SCHEMA_PROPERTIES);

    if (propertiesNode == null || !propertiesNode.isObject())
      throw new ArtifactParseException("Invalid JSON Schema properties node", JSON_SCHEMA_PROPERTIES, path);

    Iterator<String> jsonChildNames = propertiesNode.fieldNames();

    while (jsonChildNames.hasNext()) {
      String childName = jsonChildNames.next();
      boolean isMultiple = false;
      Optional<Integer> minItems = Optional.empty();
      Optional<Integer> maxItems = Optional.empty();

      // The /properties field for each schema artifact contains entries constraining fields in instances
      if (!TEMPLATE_INSTANCE_ARTIFACT_KEYWORDS.contains(childName))
        if (!FIELD_INSTANCE_ARTIFACT_KEYWORDS.contains(childName) && !ELEMENT_INSTANCE_ARTIFACT_KEYWORDS.contains(
          childName)) {
          JsonNode jsonFieldOrElementSchemaArtifactNode = propertiesNode.get(childName);
          String fieldOrElementPath = path + "/properties/" + childName;

          if (jsonFieldOrElementSchemaArtifactNode.isObject()) {

            String jsonSchemaType = readRequiredString((ObjectNode)jsonFieldOrElementSchemaArtifactNode,
              fieldOrElementPath, JSON_SCHEMA_TYPE);

            if (jsonSchemaType.equals(JSON_SCHEMA_ARRAY)) {

              isMultiple = true;

              minItems = readInteger((ObjectNode)jsonFieldOrElementSchemaArtifactNode,
                fieldOrElementPath, JSON_SCHEMA_MIN_ITEMS);

              maxItems = readInteger((ObjectNode)jsonFieldOrElementSchemaArtifactNode,
                fieldOrElementPath, JSON_SCHEMA_MAX_ITEMS);

              jsonFieldOrElementSchemaArtifactNode = jsonFieldOrElementSchemaArtifactNode.get(JSON_SCHEMA_ITEMS);

              if (jsonFieldOrElementSchemaArtifactNode == null)
                throw new ArtifactParseException("No items field in array", JSON_SCHEMA_ITEMS, fieldOrElementPath);

              fieldOrElementPath += "/items";

              if (!jsonFieldOrElementSchemaArtifactNode.isObject())
                throw new ArtifactParseException("Non-object items content in array", JSON_SCHEMA_ITEMS,
                  fieldOrElementPath);
            } else if (!jsonSchemaType.equals(JSON_SCHEMA_OBJECT)) {
              throw new ArtifactParseException("Expecting array or object, got " + jsonSchemaType, JSON_SCHEMA_ITEMS,
                fieldOrElementPath);
            }

            List<URI> subSchemaArtifactJsonLdTypes = readUriArray(
              (ObjectNode)jsonFieldOrElementSchemaArtifactNode, fieldOrElementPath, JSON_LD_TYPE);

            checkSchemaArtifactJsonLdType(subSchemaArtifactJsonLdTypes, fieldOrElementPath);

            URI subSchemaArtifactJsonLdType = subSchemaArtifactJsonLdTypes.get(0);
            Optional<URI> propertyUri = childPropertyUris.containsKey(childName) ?
              Optional.of(childPropertyUris.get(childName)) :
              Optional.empty();

            switch (subSchemaArtifactJsonLdType.toString()) {
            case TEMPLATE_SCHEMA_ARTIFACT_TYPE_IRI ->
              throw new ArtifactParseException("Invalid nesting of template schema artifact", childName,
                fieldOrElementPath);
            case ELEMENT_SCHEMA_ARTIFACT_TYPE_IRI -> {
              ElementSchemaArtifact elementSchemaArtifact = readElementSchemaArtifact(
                (ObjectNode)jsonFieldOrElementSchemaArtifactNode, fieldOrElementPath, childName, isMultiple, minItems, maxItems,
                propertyUri);
              elementSchemas.put(childName, elementSchemaArtifact);
            }
            case FIELD_SCHEMA_ARTIFACT_TYPE_IRI -> {
              FieldSchemaArtifact fieldSchemaArtifact = readFieldSchemaArtifact(
                (ObjectNode)jsonFieldOrElementSchemaArtifactNode, fieldOrElementPath, childName, isMultiple, minItems, maxItems,
                propertyUri);
              fieldSchemas.put(childName, fieldSchemaArtifact);
            }
            case STATIC_FIELD_SCHEMA_ARTIFACT_TYPE_IRI -> {
              FieldSchemaArtifact fieldSchemaArtifact = readFieldSchemaArtifact(
                (ObjectNode)jsonFieldOrElementSchemaArtifactNode, fieldOrElementPath, childName, isMultiple, minItems, maxItems,
                propertyUri);
              fieldSchemas.put(childName, fieldSchemaArtifact);
            }
            default -> throw new ArtifactParseException("Unknown JSON-LD @type " + subSchemaArtifactJsonLdType,
              childName, fieldOrElementPath);
            }

          } else {
            throw new ArtifactParseException("Unknown non-object schema artifact", childName, fieldOrElementPath);
          }
        }
    }
  }

  private TemplateInstanceArtifact readTemplateInstanceArtifact(ObjectNode objectNode, String path)
  {
    Map<String, URI> jsonLdContext = readFieldNameUriValueMap(objectNode, path, JSON_LD_CONTEXT);
    List<URI> jsonLdTypes = readUriArray(objectNode, path, JSON_LD_TYPE);
    Optional<URI> jsonLdId = readUri(objectNode, path, JSON_LD_ID);
    Optional<URI> createdBy = readUri(objectNode, path, PAV_CREATED_BY);
    Optional<URI> modifiedBy = readUri(objectNode, path, OSLC_MODIFIED_BY);
    Optional<OffsetDateTime> createdOn = readOffsetDateTime(objectNode, path, PAV_CREATED_ON);
    Optional<OffsetDateTime> lastUpdatedOn = readOffsetDateTime(objectNode, path, PAV_LAST_UPDATED_ON);
    URI isBasedOn = readRequiredUri(objectNode, SCHEMA_IS_BASED_ON, path);
    String name = readRequiredString(objectNode, path, SCHEMA_ORG_NAME);
    String description = readRequiredString(objectNode, path, SCHEMA_ORG_DESCRIPTION);
    Map<String, List<FieldInstanceArtifact>> fieldInstances = new HashMap<>();
    Map<String, List<ElementInstanceArtifact>> elementInstances = new HashMap<>();

    return TemplateInstanceArtifact.create(jsonLdContext, jsonLdTypes, jsonLdId, name, description, createdBy,
      modifiedBy, createdOn, lastUpdatedOn, isBasedOn, fieldInstances, elementInstances);
  }

  private ElementInstanceArtifact readElementInstanceArtifact(ObjectNode objectNode, String path)
  {
    Map<String, URI> jsonLdContext = readFieldNameUriValueMap(objectNode, path, JSON_LD_CONTEXT);
    List<URI> jsonLdTypes = readUriArray(objectNode, path, JSON_LD_TYPE);
    Optional<URI> jsonLdId = readUri(objectNode, path, JSON_LD_ID);
    Optional<URI> createdBy = readUri(objectNode, path, PAV_CREATED_BY);
    Optional<URI> modifiedBy = readUri(objectNode, path, OSLC_MODIFIED_BY);
    Optional<OffsetDateTime> createdOn = readOffsetDateTime(objectNode, path, PAV_CREATED_ON);
    Optional<OffsetDateTime> lastUpdatedOn = readOffsetDateTime(objectNode, path, PAV_LAST_UPDATED_ON);
    String name = readRequiredString(objectNode, path, SCHEMA_ORG_NAME);
    String description = readRequiredString(objectNode, path, SCHEMA_ORG_DESCRIPTION);
    Map<String, List<FieldInstanceArtifact>> fieldInstances = new HashMap<>();
    Map<String, List<ElementInstanceArtifact>> elementInstances = new HashMap<>();

    readNestedInstanceArtifacts(objectNode, path, fieldInstances, elementInstances);

    return ElementInstanceArtifact.create(jsonLdContext, jsonLdTypes,
      jsonLdId, name, description, createdBy, modifiedBy, createdOn, lastUpdatedOn, fieldInstances, elementInstances);
  }

  private FieldInstanceArtifact readFieldInstanceArtifact(ObjectNode objectNode, String path)
  {
    Map<String, URI> jsonLdContext = readFieldNameUriValueMap(objectNode, path, JSON_LD_CONTEXT);
    List<URI> jsonLdTypes = readUriArray(objectNode, path, JSON_LD_TYPE);
    Optional<URI> jsonLdId = readUri(objectNode, path, JSON_LD_ID);
    Optional<URI> createdBy = readUri(objectNode, path, PAV_CREATED_BY);
    Optional<URI> modifiedBy = readUri(objectNode, path, OSLC_MODIFIED_BY);
    Optional<OffsetDateTime> createdOn = readOffsetDateTime(objectNode, path, PAV_CREATED_ON);
    Optional<OffsetDateTime> lastUpdatedOn = readOffsetDateTime(objectNode, path, PAV_LAST_UPDATED_ON);
    String jsonLdValue = readString(objectNode, path, JSON_LD_VALUE, null);
    Optional<String> rdfsLabel = readString(objectNode, path, RDFS_LABEL);
    Optional<String> skosNotation = readString(objectNode, path, SKOS_NOTATION);
    Optional<String> skosPrefLabel = readString(objectNode, path, SKOS_PREFLABEL);

    return FieldInstanceArtifact.create(jsonLdContext, jsonLdTypes, jsonLdId,
      jsonLdValue, rdfsLabel, skosNotation, skosPrefLabel,
      createdBy, modifiedBy, createdOn, lastUpdatedOn);
  }

  private void readNestedInstanceArtifacts(ObjectNode instanceArtifactNode, String path,
    Map<String, List<FieldInstanceArtifact>> fields, Map<String, List<ElementInstanceArtifact>> elements)
  {
    Iterator<String> instanceArtifactFieldNames = instanceArtifactNode.fieldNames();

    while (instanceArtifactFieldNames.hasNext()) {
      String instanceArtifactFieldName = instanceArtifactFieldNames.next();

      if (!INSTANCE_ARTIFACT_KEYWORDS.contains(instanceArtifactFieldName)) {
        JsonNode nestedNode = instanceArtifactNode.get(instanceArtifactFieldName);
        String nestedInstanceArtifactPath = path + "/" + instanceArtifactFieldName;

        if (nestedNode.isObject()) {
          ObjectNode nestedInstanceArtifactNode = (ObjectNode)nestedNode;

          readNestedInstanceArtifact(instanceArtifactFieldName, nestedInstanceArtifactPath, nestedInstanceArtifactNode,
            elements, fields);

        } else if (nestedNode.isArray()) {
          Iterator<JsonNode> nodeIterator = nestedNode.iterator();

          int arrayIndex = 0;
          while (nodeIterator.hasNext()) {
            String arrayEnclosedInstanceArtifactPath = nestedInstanceArtifactPath + "[" + arrayIndex + "]";
            JsonNode jsonNode = nodeIterator.next();
            if (jsonNode == null || jsonNode.isNull()) {
              throw new ArtifactParseException("Expecting field or element instance artifact entry in array, got null",
                instanceArtifactFieldName, arrayEnclosedInstanceArtifactPath);
            } else {
              if (!jsonNode.isObject())
                throw new ArtifactParseException("Expecting nested field or element instance artifact in array",
                  instanceArtifactFieldName, arrayEnclosedInstanceArtifactPath);

              ObjectNode arrayEnclosedInstanceArtifactNode = (ObjectNode)jsonNode;
              readNestedInstanceArtifact(instanceArtifactFieldName, arrayEnclosedInstanceArtifactPath,
                arrayEnclosedInstanceArtifactNode, elements, fields);
            }
            arrayIndex++;
          }
        }
      } else
        throw new ArtifactParseException("Unknown non-object instance artifact", instanceArtifactFieldName, path);
    }
  }

  private void readNestedInstanceArtifact(String instanceArtifactFieldName, String instanceArtifactPath,
    ObjectNode instanceArtifactNode, Map<String, List<ElementInstanceArtifact>> elements,
    Map<String, List<FieldInstanceArtifact>> fields)
  {
    if (hasJsonLdContextField(instanceArtifactNode)) { // Element instance artifacts have @context fields
      ObjectNode elementInstanceArtifactNode = instanceArtifactNode;
      ElementInstanceArtifact elementInstanceArtifact = readElementInstanceArtifact(elementInstanceArtifactNode,
        instanceArtifactPath);
      elements.put(instanceArtifactFieldName, new ArrayList<>());
      elements.get(instanceArtifactFieldName).add(elementInstanceArtifact);
    } else { // Field instance artifact do not
      FieldInstanceArtifact fieldInstanceArtifact = readFieldInstanceArtifact(instanceArtifactNode,
        instanceArtifactPath);
      fields.put(instanceArtifactFieldName, new ArrayList<>());
      fields.get(instanceArtifactFieldName).add(fieldInstanceArtifact);
    }
  }

  private void checkSchemaArtifactJsonLdType(List<URI> schemaArtifactJsonLdTypes, String path)
  {
    if (schemaArtifactJsonLdTypes.isEmpty())
      throw new ArtifactParseException("Unknown object - must be a JSON-LD type or array of types", JSON_LD_TYPE, path);

    if (schemaArtifactJsonLdTypes.size() != 1)
      throw new ArtifactParseException(
        "Expecting single JSON-LD @type field for schema artifact, got " + schemaArtifactJsonLdTypes.size(),
        JSON_LD_TYPE, path);

    URI schemaArtifactJsonLdType = schemaArtifactJsonLdTypes.get(0);

    if (!SCHEMA_ARTIFACT_TYPE_IRIS.contains(schemaArtifactJsonLdType.toString()))
      throw new ArtifactParseException("Unexpected schema artifact JSON-LD @type " + schemaArtifactJsonLdType,
        JSON_LD_TYPE, path);
  }

  private void checkTemplateSchemaArtifactJsonLdType(List<URI> schemaArtifactJsonLdTypes, String path)
  {
    checkSchemaArtifactJsonLdType(schemaArtifactJsonLdTypes, path);

    URI schemaArtifactJsonLdType = schemaArtifactJsonLdTypes.get(0);

    if (!schemaArtifactJsonLdType.toString().equals(TEMPLATE_SCHEMA_ARTIFACT_TYPE_IRI))
      throw new ArtifactParseException("Unexpected template schema artifact JSON-LD @type " + schemaArtifactJsonLdType,
        JSON_LD_TYPE, path);
  }

  private void checkElementSchemaArtifactJsonLdType(List<URI> schemaArtifactJsonLdTypes, String path)
  {
    checkSchemaArtifactJsonLdType(schemaArtifactJsonLdTypes, path);

    URI schemaArtifactJsonLdType = schemaArtifactJsonLdTypes.get(0);

    if (!schemaArtifactJsonLdType.toString().equals(ELEMENT_SCHEMA_ARTIFACT_TYPE_IRI))
      throw new ArtifactParseException("Unexpected element schema artifact JSON-LD @type " + schemaArtifactJsonLdType,
        JSON_LD_TYPE, path);
  }

  private void checkFieldSchemaArtifactJsonLdType(List<URI> schemaArtifactJsonLdTypes, String path)
  {
    checkSchemaArtifactJsonLdType(schemaArtifactJsonLdTypes, path);

    URI schemaArtifactJsonLdType = schemaArtifactJsonLdTypes.get(0);

    if (!schemaArtifactJsonLdType.toString().equals(FIELD_SCHEMA_ARTIFACT_TYPE_IRI) &&
        !schemaArtifactJsonLdType.toString().equals(STATIC_FIELD_SCHEMA_ARTIFACT_TYPE_IRI))
      throw new ArtifactParseException("Unexpected field schema artifact JSON-LD @type " + schemaArtifactJsonLdType,
        JSON_LD_TYPE, path);
  }

  // A parent schema artifact's JSON Schema 'properties' object contains a specification for a JSON-LD @context for
  // the corresponding instance; this @context maps each child name to a URI that represents a property specification
  // for each child.
  //
  // e.g.,
  //
  //    "properties": {
  //      "@context": {
  //         "type": "object",
  //          ....
  //          "Study ID": { "enum": [ "http://semantic-dicom.org/dcm#StudyID" ]
  //          "Disease": { "enum": [ "http://semantic-dicom.org/dcm#Disease" ]
  //      }
  //    }
  private Map<String, URI> getChildPropertyUris(ObjectNode objectNode, String path)
  {
    Map<String, URI> childName2URI = new HashMap<>();
    String contextPath = "/" + JSON_SCHEMA_PROPERTIES + "/" + JSON_LD_CONTEXT + "/" + JSON_SCHEMA_PROPERTIES;
    JsonNode contextNode = objectNode.at(contextPath);

    if (contextNode != null && contextNode.isObject()) {
      ObjectNode jsonSchemaContextSpecificationNode = (ObjectNode)contextNode;
      Iterator<String> childNames = jsonSchemaContextSpecificationNode.fieldNames();

      while (childNames.hasNext()) {
        String childName = childNames.next();
        if (!ARTIFACT_CONTEXT_ENTRIES.contains(childName)) { // Ignore standard context entries
          JsonNode enumNode = jsonSchemaContextSpecificationNode.get(childName);

          if (enumNode != null) { // A property URI specification for a child is optional
            if (!enumNode.isObject())
              throw new ArtifactParseException("Expecting object node with property URI enum specification", childName,
                path + contextPath);

            JsonNode enumArray = enumNode.get(JSON_SCHEMA_ENUM);

            if (enumArray == null || !enumArray.isArray())
              throw new ArtifactParseException("Expecting array for property URI enum specification", JSON_SCHEMA_ENUM,
                path + contextPath + childName);

            if (enumArray.size() != 1)
              throw new ArtifactParseException(
                "Expecting exactly one value for property URI enum specification, got " + enumArray.size(),
                JSON_SCHEMA_ENUM, path + contextPath + childName);

            JsonNode elementNode = enumArray.get(0);

            if (!elementNode.isTextual())
              throw new ArtifactParseException("Expecting text node for property URI enum entry, got " + elementNode.getNodeType(), JSON_SCHEMA_ENUM,
                path + contextPath + childName);

            try {
              URI propertyUri = new URI(elementNode.asText());
              childName2URI.put(childName, propertyUri);
            } catch (URISyntaxException e) {
              throw new ArtifactParseException("Invalid URI " + elementNode.asText() + " for enum specification",
                JSON_SCHEMA_ENUM, path + contextPath + childName);
            }
          }
        }
      }
    }
    return childName2URI;
  }

  private Optional<ValueConstraints> readValueConstraints(ObjectNode objectNode, String path,
    String fieldName, FieldInputType fieldInputType)
  {
    String vcPath = path + "/" + fieldName;
    ObjectNode vcNode = readValueConstraintsNode(objectNode, path, fieldName);

    if (vcNode != null) {

      boolean requiredValue = readBoolean(vcNode, vcPath, VALUE_CONSTRAINTS_REQUIRED_VALUE, false);
      boolean multipleChoice = readBoolean(vcNode, vcPath, VALUE_CONSTRAINTS_MULTIPLE_CHOICE, false);
      Optional<NumericType> numberType = readNumberType(vcNode, vcPath, VALUE_CONSTRAINTS_NUMBER_TYPE);
      Optional<TemporalType> temporalType = readTemporalType(vcNode, vcPath, VALUE_CONSTRAINTS_TEMPORAL_TYPE);
      Optional<String> unitOfMeasure = readString(vcNode, vcPath, VALUE_CONSTRAINTS_UNIT_OF_MEASURE);
      Optional<Number> minValue = readNumber(vcNode, vcPath, VALUE_CONSTRAINTS_MIN_NUMBER_VALUE);
      Optional<Number> maxValue = readNumber(vcNode, vcPath, VALUE_CONSTRAINTS_MAX_NUMBER_VALUE);
      Optional<Integer> decimalPlaces = readInteger(vcNode, vcPath, VALUE_CONSTRAINTS_DECIMAL_PLACE);
      Optional<Integer> minLength = readInteger(vcNode, vcPath, VALUE_CONSTRAINTS_MIN_STRING_LENGTH);
      Optional<Integer> maxLength = readInteger(vcNode, vcPath, VALUE_CONSTRAINTS_MAX_STRING_LENGTH);
      Optional<? extends DefaultValue> defaultValue = readDefaultValueField(vcNode, vcPath, VALUE_CONSTRAINTS_DEFAULT_VALUE);
      Optional<String> regex = readString(vcNode, vcPath, "regex"); // TODO Add 'regex' to ModelNodeNames
      List<OntologyValueConstraint> ontologies = readOntologyValueConstraints(vcNode, vcPath, VALUE_CONSTRAINTS_ONTOLOGIES);
      List<ValueSetValueConstraint> valueSets = readValueSetValueConstraints(vcNode, vcPath, VALUE_CONSTRAINTS_VALUE_SETS);
      List<ClassValueConstraint> classes = readClassValueConstraints(vcNode, vcPath, VALUE_CONSTRAINTS_CLASSES);
      List<BranchValueConstraint> branches = readBranchValueConstraints(vcNode, vcPath, VALUE_CONSTRAINTS_BRANCHES);
      List<LiteralValueConstraint> literals = readLiteralValueConstraints(vcNode, vcPath, VALUE_CONSTRAINTS_LITERALS);
      List<ControlledTermValueConstraintsAction> actions = readValueConstraintsActions(vcNode, vcPath, VALUE_CONSTRAINTS_ACTIONS);

      if (fieldInputType == FieldInputType.NUMERIC) {
        Optional<NumericDefaultValue> numericDefaultValue = defaultValue.isPresent() ?
          Optional.of(defaultValue.get().asNumericDefaultValue()) :
          Optional.empty();
        return Optional.of(
          NumericValueConstraints.create(numberType.get(), minValue, maxValue, decimalPlaces, unitOfMeasure,
            numericDefaultValue, requiredValue, multipleChoice));
      } else if (fieldInputType == FieldInputType.TEMPORAL) {
        Optional<TemporalDefaultValue> temporalDefaultValue = defaultValue.isPresent() ?
          Optional.of(defaultValue.get().asTemporalDefaultValue()) :
          Optional.empty();
        return Optional.of(TemporalValueConstraints.create(temporalType.get(), temporalDefaultValue, requiredValue, multipleChoice));

      } else if (fieldInputType == FieldInputType.LINK || (fieldInputType == FieldInputType.TEXTFIELD && (!ontologies.isEmpty() || !valueSets.isEmpty() || !classes.isEmpty() || !branches.isEmpty()))) {
        Optional<ControlledTermDefaultValue> controlledTermDefaultValue = defaultValue.isPresent() ?
          Optional.of(defaultValue.get().asControlledTermDefaultValue()) :
          Optional.empty();
        return Optional.of(
          ControlledTermValueConstraints.create(ontologies, valueSets, classes, branches, controlledTermDefaultValue,
            actions, requiredValue, multipleChoice));
      } else {
        Optional<TextDefaultValue> textDefaultValue = defaultValue.isPresent() ?
          Optional.of(defaultValue.get().asTextDefaultValue()) :
          Optional.empty();
        return Optional.of(
          TextValueConstraints.create(minLength, maxLength, textDefaultValue, literals, requiredValue, multipleChoice, regex));
      }
    } else
      return Optional.empty();
  }

  private Optional<DefaultValue> readDefaultValueField(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null || jsonNode.isNull())
      return Optional.empty();
    else if (jsonNode.isObject()) {
      String nestedPath = path + "/" + fieldName;
      ObjectNode defaultValueNode = (ObjectNode)jsonNode;
      URI termUri = readRequiredUri(defaultValueNode, nestedPath, VALUE_CONSTRAINTS_DEFAULT_VALUE_TERM_URI);
      String rdfsLabel = readRequiredString(defaultValueNode, nestedPath, RDFS_LABEL);
      return Optional.of(new ControlledTermDefaultValue(termUri, rdfsLabel));
    } else if (jsonNode.isNumber())
      return Optional.of(new NumericDefaultValue(jsonNode.asDouble()));
    else if (jsonNode.isTextual())
      return Optional.of(new TextDefaultValue(jsonNode.asText()));
    else
      throw new ArtifactParseException(
        "default value must be a string, a number, or an object containing URI/string pair",
        fieldName, path);
  }

  private List<OntologyValueConstraint> readOntologyValueConstraints(ObjectNode objectNode, String path, String fieldName)
  {
    List<OntologyValueConstraint> ontologyValueConstraints = new ArrayList<>();

    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode != null && !jsonNode.isNull() && jsonNode.isArray()) {

      for (JsonNode valueConstraintNode : jsonNode) {
        if (valueConstraintNode != null) {
          if (!valueConstraintNode.isObject())
            throw new ArtifactParseException("Value in array must be an object", fieldName, path);
          OntologyValueConstraint ontologyValueConstraint = readOntologyValueConstraint((ObjectNode)valueConstraintNode,
            path + "/" + fieldName);
          ontologyValueConstraints.add(ontologyValueConstraint);
        }
      }
    }
    return ontologyValueConstraints;
  }

  private List<ClassValueConstraint> readClassValueConstraints(ObjectNode objectNode, String path, String fieldName)
  {
    List<ClassValueConstraint> classValueConstraints = new ArrayList<>();

    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode != null && !jsonNode.isNull() && jsonNode.isArray()) {

      for (JsonNode valueConstraintNode : jsonNode) {
        if (valueConstraintNode != null) {
          if (!valueConstraintNode.isObject())
            throw new ArtifactParseException("Value in array must be an object", fieldName, path);
          ClassValueConstraint classValueConstraint = readClassValueConstraint((ObjectNode)valueConstraintNode,
            path + "/" + fieldName);
          classValueConstraints.add(classValueConstraint);
        }
      }
    }
    return classValueConstraints;
  }

  private List<ValueSetValueConstraint> readValueSetValueConstraints(ObjectNode objectNode, String path, String fieldName)
  {
    List<ValueSetValueConstraint> valueSetValueConstraints = new ArrayList<>();

    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode != null && jsonNode.isArray()) {

      for (JsonNode valueConstraintNode : jsonNode) {
        if (valueConstraintNode != null) {
          if (!valueConstraintNode.isObject())
            throw new ArtifactParseException("Value in array must be an object", fieldName, path);
          ValueSetValueConstraint valueSetValueConstraint = readValueSetValueConstraint((ObjectNode)valueConstraintNode,
            path + "/" + fieldName);
          valueSetValueConstraints.add(valueSetValueConstraint);
        }
      }
    }
    return valueSetValueConstraints;
  }

  private List<BranchValueConstraint> readBranchValueConstraints(ObjectNode objectNode, String path, String fieldName)
  {
    List<BranchValueConstraint> branchValueConstraints = new ArrayList<>();

    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode != null && jsonNode.isArray()) {

      for (JsonNode valueConstraintNode : jsonNode) {
        if (valueConstraintNode != null) {
          if (!valueConstraintNode.isObject())
            throw new ArtifactParseException("Value in array must be an object", fieldName, path);
          BranchValueConstraint branchValueConstraint = readBranchValueConstraint((ObjectNode)valueConstraintNode,
            path + "/" + fieldName);
          branchValueConstraints.add(branchValueConstraint);
        }
      }
    }

    return branchValueConstraints;
  }

  private List<LiteralValueConstraint> readLiteralValueConstraints(ObjectNode objectNode, String path, String fieldName)
  {
    List<LiteralValueConstraint> literalValueConstraints = new ArrayList<>();

    JsonNode jsonNode = objectNode.get(fieldName);
    String literalsPath = path + "/" + fieldName;

    if (jsonNode != null && jsonNode.isArray()) {

      for (JsonNode valueConstraintsNode : jsonNode) {
        if (valueConstraintsNode != null) {
          if (!valueConstraintsNode.isObject())
            throw new ArtifactParseException("Value in array must be an object", fieldName, literalsPath);
          LiteralValueConstraint literalValueConstraint = readLiteralValueConstraint((ObjectNode)valueConstraintsNode,
            literalsPath);
          literalValueConstraints.add(literalValueConstraint);
        }
      }
    }
    return literalValueConstraints;
  }

  private List<ControlledTermValueConstraintsAction> readValueConstraintsActions(ObjectNode objectNode, String path, String fieldName)
  {
    List<ControlledTermValueConstraintsAction> actions = new ArrayList<>();

    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode != null && jsonNode.isArray()) {

      for (JsonNode actionNode : jsonNode) {
        if (actionNode != null) {
          if (!actionNode.isObject())
            throw new ArtifactParseException("Value in array must be an object", fieldName, path);
          ControlledTermValueConstraintsAction action = readValueConstraintsAction((ObjectNode)actionNode,
            path + "/" + fieldName);
          actions.add(action);
        }
      }
    }
    return actions;
  }

  private ControlledTermValueConstraintsAction readValueConstraintsAction(ObjectNode objectNode, String path)
  {
    URI termUri = readRequiredUri(objectNode, path, VALUE_CONSTRAINTS_TERM_URI);
    String source = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_SOURCE);
    ValueConstraintsActionType actionType = readValueConstraintsActionType(objectNode, path, VALUE_CONSTRAINTS_ACTION);
    ValueType valueType = readValueType(objectNode, path, VALUE_CONSTRAINTS_TYPE);
    Optional<URI> sourceUri = readUri(objectNode, path, VALUE_CONSTRAINTS_SOURCE_URI);
    Optional<Integer> to = readInteger(objectNode, path, VALUE_CONSTRAINTS_ACTION_TO);

    return new ControlledTermValueConstraintsAction(termUri, source, valueType, actionType, sourceUri, to);
  }

  private Optional<TemporalType> readTemporalType(ObjectNode objectNode, String path, String fieldName)
  {
    Optional<String> temporalTypeValue = readString(objectNode, path, fieldName);

    if (temporalTypeValue.isPresent())
      return Optional.of(TemporalType.fromString(temporalTypeValue.get()));
    else
      return Optional.empty();
  }

  private Optional<NumericType> readNumberType(ObjectNode objectNode, String path, String fieldName)
  {
    Optional<String> numberTypeValue = readString(objectNode, path, fieldName);

    if (numberTypeValue.isPresent())
      return Optional.of(NumericType.fromString(numberTypeValue.get()));
    else
      return Optional.empty();
  }

  private ValueConstraintsActionType readValueConstraintsActionType(ObjectNode objectNode, String path, String fieldName)
  {
    String actionType = readRequiredString(objectNode, path, fieldName);

    return ValueConstraintsActionType.fromString(actionType);
  }

  private ValueType readValueType(ObjectNode objectNode, String path, String fieldName)
  {
    String valueType = readRequiredString(objectNode, path, fieldName);

    return ValueType.fromString(valueType);
  }

  private OntologyValueConstraint readOntologyValueConstraint(ObjectNode objectNode, String path)
  {
    URI uri = readRequiredUri(objectNode, path, VALUE_CONSTRAINTS_URI);
    String acronym = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_ACRONYM);
    String name = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_NAME);
    Optional<Integer> numTerms = readInteger(objectNode, path, VALUE_CONSTRAINTS_NUM_TERMS);

    return new OntologyValueConstraint(uri, acronym, name, numTerms);
  }

  private ClassValueConstraint readClassValueConstraint(ObjectNode objectNode, String path)
  {
    URI uri = readRequiredUri(objectNode, path, VALUE_CONSTRAINTS_URI);
    String prefLabel = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_PREFLABEL);
    ValueType valueType = readValueType(objectNode, path, VALUE_CONSTRAINTS_TYPE);
    String label = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_LABEL);
    String source = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_SOURCE);

    return new ClassValueConstraint(uri, source, label, prefLabel, valueType);
  }

  private ValueSetValueConstraint readValueSetValueConstraint(ObjectNode objectNode, String path)
  {
    URI uri = readRequiredUri(objectNode, path, VALUE_CONSTRAINTS_URI);
    String name = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_NAME);
    String vsCollection = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_VS_COLLECTION);
    Optional<Integer> numTerms = readInteger(objectNode, path, VALUE_CONSTRAINTS_NUM_TERMS);

    return new ValueSetValueConstraint(uri, vsCollection, name, numTerms);
  }

  private BranchValueConstraint readBranchValueConstraint(ObjectNode objectNode, String path)
  {
    URI uri = readRequiredUri(objectNode, path, VALUE_CONSTRAINTS_URI);
    String source = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_SOURCE);
    String acronym = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_ACRONYM);
    String name = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_NAME);
    int maxDepth = readRequiredInt(objectNode, path, VALUE_CONSTRAINTS_MAX_DEPTH);

    return new BranchValueConstraint(uri, source, acronym, name, maxDepth);
  }

  private LiteralValueConstraint readLiteralValueConstraint(ObjectNode objectNode, String path)
  {
    String label = readRequiredString(objectNode, path, VALUE_CONSTRAINTS_LABEL);
    boolean selectedByDefault = readBoolean(objectNode, path, VALUE_CONSTRAINTS_SELECTED_BY_DEFAULT, false);

    return new LiteralValueConstraint(label, selectedByDefault);
  }

  private FieldUi readFieldUi(ObjectNode objectNode, String path, String fieldName)
  {
    ObjectNode uiNode = readNode(objectNode, path, fieldName);
    String uiPath = path + "/" + fieldName;

    FieldInputType fieldInputType = readFieldInputType(uiNode, uiPath, UI_FIELD_INPUT_TYPE);
    boolean valueRecommendationEnabled = readBoolean(uiNode, uiPath, UI_VALUE_RECOMMENDATION_ENABLED, false);
    boolean hidden = readBoolean(uiNode, uiPath, UI_HIDDEN, false);

    if (fieldInputType.isTemporal()) {
      TemporalGranularity temporalGranularity = readTemporalGranularity(uiNode, uiPath, UI_TEMPORAL_GRANULARITY);
      InputTimeFormat inputTimeFormat = readInputTimeFormat(uiNode, uiPath, UI_INPUT_TIME_FORMAT, InputTimeFormat.TWELVE_HOUR);
      boolean timeZoneEnabled = readBoolean(uiNode, uiPath, UI_TIMEZONE_ENABLED, false);

      return TemporalFieldUi.create(temporalGranularity, inputTimeFormat, timeZoneEnabled, hidden);
    } else if (fieldInputType.isNumeric()) {
      return NumericFieldUi.create(hidden);
    } else if (fieldInputType.isStatic()) {
      String content = readRequiredString(uiNode, uiPath, UI_CONTENT);
      return StaticFieldUi.create(fieldInputType, content, hidden);
    } else
      return FieldUi.create(fieldInputType, hidden, valueRecommendationEnabled);
  }

  private ElementUi readElementUi(ObjectNode objectNode, String path, String fieldName)
  {
    ObjectNode uiNode = readNode(objectNode, path, fieldName);
    String uiPath = path + "/" + fieldName;

    List<String> order = readStringArray(uiNode, uiPath, UI_ORDER);
    Map<String, String> propertyLabels = readMap(uiNode, uiPath, UI_PROPERTY_LABELS);
    Map<String, String> propertyDescriptions = readMap(uiNode, uiPath, UI_PROPERTY_DESCRIPTIONS);
    Optional<String> header = readString(uiNode, uiPath, UI_HEADER);
    Optional<String> footer = readString(uiNode, uiPath, UI_FOOTER);

    return ElementUi.create(order, propertyLabels, propertyDescriptions, header, footer);
  }

  private TemplateUi readTemplateUi(ObjectNode objectNode, String path, String fieldName)
  {
    ObjectNode uiNode = readNode(objectNode, path, fieldName);
    String uiPath = path + "/" + fieldName;

    List<String> order = readStringArray(uiNode, uiPath, UI_ORDER);
    List<String> pages = readStringArray(uiNode, uiPath, UI_PAGES);
    Map<String, String> propertyLabels = readMap(uiNode, uiPath, UI_PROPERTY_LABELS);
    Map<String, String> propertyDescriptions = readMap(uiNode, uiPath, UI_PROPERTY_DESCRIPTIONS);
    Optional<String> header = readString(uiNode, uiPath, UI_HEADER);
    Optional<String> footer = readString(uiNode, uiPath, UI_FOOTER);

    return TemplateUi.create(order, pages, propertyLabels, propertyDescriptions, header, footer);
  }

  private Optional<Integer> readInteger(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null || jsonNode.isNull())
      return Optional.empty();

    if (!jsonNode.isInt())
      throw new ArtifactParseException("Value must be an integer", fieldName, path);

    return Optional.of(jsonNode.asInt());
  }

  private Optional<Number> readNumber(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null || jsonNode.isNull())
      return Optional.empty();

    if (!jsonNode.isNumber())
      throw new ArtifactParseException("Value must be a number", fieldName, path);

    if (jsonNode.isIntegralNumber())
       return Optional.of(jsonNode.asLong());
    else
      return Optional.of(jsonNode.asDouble());
  }

  private boolean readBoolean(ObjectNode objectNode, String path, String fieldName, boolean defaultValue)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null || jsonNode.isNull())
      return defaultValue;

    if (!jsonNode.isBoolean())
      throw new ArtifactParseException("Value must be boolean", fieldName, path);

    return jsonNode.asBoolean();
  }

  private FieldInputType readFieldInputType(ObjectNode objectNode, String path, String fieldName)
  {
    String inputType = readRequiredString(objectNode, path, fieldName);

    if (!INPUT_TYPES.contains(inputType))
      throw new ArtifactParseException("Invalid field input type " + inputType, fieldName, path);

    return FieldInputType.fromString(inputType);
  }

  private TemporalGranularity readTemporalGranularity(ObjectNode objectNode, String path, String fieldName)
  {
    String granularity = readRequiredString(objectNode, path, fieldName);

    if (!ModelNodeValues.TEMPORAL_GRANULARITIES.contains(granularity))
      throw new ArtifactParseException("Invalid granularity " + granularity, fieldName, path);

    return TemporalGranularity.fromString(granularity);
  }

  private InputTimeFormat readInputTimeFormat(ObjectNode objectNode, String path, String fieldName, InputTimeFormat defaultInputTimeFormat)
  {
    Optional<String> timeFormat = readString(objectNode, path, fieldName);

    if (!timeFormat.isPresent())
      return defaultInputTimeFormat;

    if (!ModelNodeValues.TIME_FORMATS.contains(timeFormat.get()))
      throw new ArtifactParseException("Invalid time format " + timeFormat.get(), UI_INPUT_TIME_FORMAT, path);

    return InputTimeFormat.fromString(timeFormat.get());
  }

  private String readDefaultValue(ObjectNode objectNode, String path)
  {
    ObjectNode valueConstraintsNode = readValueConstraintsNode(objectNode, path, VALUE_CONSTRAINTS);

    if (valueConstraintsNode == null)
      throw new ArtifactParseException("No " + VALUE_CONSTRAINTS + " field", VALUE_CONSTRAINTS, path);

    String subPath = path + "/" + VALUE_CONSTRAINTS;

    return readRequiredString(valueConstraintsNode, subPath, VALUE_CONSTRAINTS_DEFAULT_VALUE);
  }

  private ObjectNode readNode(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null)
      throw new ArtifactParseException("No " + fieldName + " field", fieldName, path);
    else if (jsonNode.isNull())
      throw new ArtifactParseException("Null " + fieldName + " field", fieldName, path);
    else if (!jsonNode.isObject())
      throw new ArtifactParseException("Value must be an object", fieldName, path);

     return (ObjectNode)jsonNode;
  }

  private ObjectNode readValueConstraintsNode(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null)
      return null;
    else if (jsonNode.isNull())
      return null;
    else if (!jsonNode.isObject())
      throw new ArtifactParseException("Value must be an object", fieldName, path);

    return (ObjectNode)jsonNode;
  }

  private Map<String, String> readMap(ObjectNode objectNode, String path, String fieldName)
  {
    Map<String, String> fieldNameStringValueMap = new HashMap<>();

    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode != null && !jsonNode.isNull()) {

      if (!jsonNode.isObject())
        throw new ArtifactParseException("Value of field must be an object", fieldName, path);

      Iterator<Map.Entry<String, JsonNode>> fieldEntries = jsonNode.fields();

      while (fieldEntries.hasNext()) {
        var fieldEntry = fieldEntries.next();

        if (fieldEntry.getValue().isTextual()) {
          String currentFieldName = fieldEntry.getKey();
          String currentFieldValue = fieldEntry.getValue().textValue();

          if (currentFieldValue != null)
            fieldNameStringValueMap.put(currentFieldName, currentFieldValue);
        } else
            throw new ArtifactParseException("Object in field must contain string values", fieldName, path);
      }
    }
    return fieldNameStringValueMap;
  }

  private Map<String, URI> readFieldNameUriValueMap(ObjectNode objectNode, String path, String fieldName)
  {
    Map<String, URI> fieldNameStringValueMap = new HashMap<>();

    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode != null) {

      if (!jsonNode.isObject())
        throw new ArtifactParseException("Value of field must be an object", fieldName, path);

      Iterator<Map.Entry<String, JsonNode>> fieldEntries = jsonNode.fields();

      while (fieldEntries.hasNext()) {
        var fieldEntry = fieldEntries.next();

        // We only record simple term->term URI entries
        if (fieldEntry.getValue().isTextual()) {
          String currentFieldName = fieldEntry.getKey();
          String currentFieldValue = fieldEntry.getValue().textValue();

          try {
            URI currentFieldURIValue = new URI(currentFieldValue);
            fieldNameStringValueMap.put(currentFieldName, currentFieldURIValue);
          } catch (Exception e) {
            throw new ArtifactParseException("Object in field must contain URI values", fieldName, path);
          }
        }
      }
    }
    return fieldNameStringValueMap;
  }

  /**
   * Attribute-value fields are defined inside the first element of an "items" array
   */
  private ObjectNode getFieldNode(ObjectNode objectNode, String path)
  {
    if (objectNode.isArray()) {
      JsonNode itemsNode = objectNode.get(JSON_SCHEMA_ITEMS);
      if (itemsNode == null || !itemsNode.isArray() || !itemsNode.iterator().hasNext())
        throw new ArtifactParseException("Expecting array",  JSON_SCHEMA_ITEMS, path);

      JsonNode itemNode = itemsNode.iterator().next();
      if (!itemNode.isObject())
        throw new ArtifactParseException("Expecting object as first element", JSON_SCHEMA_ITEMS, path);
      return  (ObjectNode)itemNode;
    } else
      return objectNode;
  }

  private Optional<Version> readVersion(ObjectNode objectNode, String path, String fieldName)
  {
    Optional<String> version = readString(objectNode, path, fieldName);

    if (version.isEmpty())
      return Optional.empty();
    else
      return Optional.of(Version.fromString(version.get()));
  }

  private Optional<Status> readStatus(ObjectNode objectNode, String path, String fieldName)
  {
    Optional<String> status = readString(objectNode, path, fieldName);

    if (status.isPresent())
      return Optional.of(Status.fromString(status.get()));
    else
      return Optional.empty();
  }

  private Optional<OffsetDateTime> readOffsetDateTime(ObjectNode objectNode, String path, String fieldName)
  {
    Optional<String> dateTimeValue = readString(objectNode, path, fieldName);

    try {
      if (dateTimeValue.isPresent())
        return Optional.of(OffsetDateTime.parse(dateTimeValue.get()));
      else
        return Optional.empty();
    } catch (DateTimeParseException e) {
      throw new ArtifactParseException(
        "Invalid offset datetime value " + dateTimeValue + ": " + e.getMessage(), fieldName, path);
    }
  }

  private OffsetDateTime readRequiredOffsetDateTime(ObjectNode objectNode, String path, String fieldName)
  {
    String dateTimeValue = readRequiredString(objectNode, path, fieldName);

    try {
      return OffsetDateTime.parse(dateTimeValue);
    } catch (DateTimeParseException e) {
      throw new ArtifactParseException(
        "Invalid offset datetime value " + dateTimeValue + ": " + e.getMessage(), fieldName, path);
    }
  }

  private Optional<URI> readUri(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null || jsonNode.isNull())
      return Optional.empty();

    if (!jsonNode.isTextual())
      throw new ArtifactParseException("Value of URI field must be textual", fieldName, path);

    try {
      return Optional.of(new URI(jsonNode.asText()));
    } catch (Exception e) {
      throw new ArtifactParseException("Value of URI field must be a valid URI", fieldName, path);
    }
  }

  private Optional<String> readString(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null || jsonNode.isNull())
      return Optional.empty();
    else if (!jsonNode.isTextual())
      throw new ArtifactParseException("Value of text field must be textual", fieldName, path);
    else
      return Optional.of(jsonNode.asText());
  }

  private String readString(ObjectNode objectNode, String path, String fieldName, String defaultValue)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null || jsonNode.isNull())
      return defaultValue;
    else if (!jsonNode.isTextual())
      throw new ArtifactParseException("Value of text field must be textual", fieldName, path);
    else
      return jsonNode.asText();
  }

  private String readRequiredString(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null)
      throw new ArtifactParseException("No text value present", fieldName, path);
    else if (jsonNode.isNull())
      throw new ArtifactParseException("Null value present", fieldName, path);
    else {
      if (!jsonNode.isTextual())
        throw new ArtifactParseException("Value must be textual", fieldName, path);

      return jsonNode.asText();
    }
  }

  private int readRequiredInt(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null)
      throw new ArtifactParseException("No int value present", fieldName,  path);
    else if (jsonNode.isNull())
      throw new ArtifactParseException("Null value present", fieldName, path);
    else {
      if (!jsonNode.isInt())
        throw new ArtifactParseException("Value must be an int", fieldName, path);

      return jsonNode.asInt();
    }
  }

  private URI readRequiredUri(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);

    if (jsonNode == null)
      throw new ArtifactParseException("No URI value present", fieldName, path);
    else if (jsonNode.isNull())
      throw new ArtifactParseException("Null value present", fieldName, path);
    else {
      if (!jsonNode.isTextual())
        throw new ArtifactParseException("Value must be a URI", fieldName, path);

      try {
        return new URI(jsonNode.asText());
      } catch (Exception e) {
        throw new ArtifactParseException("Value must be a valid URI", fieldName, path);
      }
    }
  }

  private List<String> readStringArray(ObjectNode objectNode, String path,  String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);
    List<String> textValues = new ArrayList<>();

    if (jsonNode != null && !jsonNode.isNull()) {
      if (jsonNode.isArray()) {
        Iterator<JsonNode> nodeIterator = jsonNode.iterator();

        int arrayIndex = 0;
        while (nodeIterator.hasNext()) {
          JsonNode jsonValueNode = nodeIterator.next();
          if (jsonValueNode != null) {
            if (!jsonValueNode.isTextual())
              throw new ArtifactParseException("Value in text array at index " + arrayIndex + " must be textual", fieldName, path);
            String textValue = jsonValueNode.asText();
            if (!textValue.isEmpty())
              textValues.add(textValue);
          }
          arrayIndex++;
        }
      } else {
        String textValue = readString(objectNode, path, fieldName, "");
        if (textValue != null && !textValue.isEmpty())
          textValues.add(textValue);
      }
    }
    return textValues;
  }

  private List<URI> readUriArray(ObjectNode objectNode, String path, String fieldName)
  {
    JsonNode jsonNode = objectNode.get(fieldName);
    List<URI> uriValues = new ArrayList<>();

    if (jsonNode != null && !jsonNode.isNull()) {
      if (jsonNode.isArray()) {
        Iterator<JsonNode> nodeIterator = jsonNode.iterator();

        int arrayIndex = 0;
        while (nodeIterator.hasNext()) {
          JsonNode itemNode = nodeIterator.next();
          if (itemNode != null) {
            if (!itemNode.isTextual())
              throw new ArtifactParseException("Value in URI array at index " + arrayIndex + " must be textual", fieldName, path);
            try {
              URI uriValue = new URI(itemNode.asText());
              uriValues.add(uriValue);
            } catch (Exception e) {
              throw new ArtifactParseException("Value in URI array at index " + arrayIndex + " must a valid URI", fieldName, path);
            }
          }
          arrayIndex++;
        }
      } else {
        Optional<URI> uriValue = readUri(objectNode, path, fieldName);
        if (uriValue.isPresent())
          uriValues.add(uriValue.get());
      }
    }
    return uriValues;
  }

  private boolean hasJsonLdContextField(ObjectNode objectNode)
  {
    return objectNode.get(JSON_LD_CONTEXT) != null;
  }
}

package com.aa.virtualroom.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "functions")
public class FunctionDetails {

    public FunctionDetails(UUID functionId, UUID fiuId, FunctionState state, String functionName, String s3Location,
                           Date createDate, Date lastUpdateDate, String jsonSchema) {
        super();
        this.functionId = functionId;
        this.functionName = functionName;
        this.fiuId = fiuId;
        this.state = state.name();
        this.s3Location = s3Location;
        this.jsonSchema = jsonSchema;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public FunctionDetails() {
        super();
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @Parameter(
                name = "uuid_gen_strategy_class",
                value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID functionId;

    @Column(name = "fiu_id", updatable = false, nullable = false)
    private UUID fiuId;

    @Column(name = "function_name", nullable = false)
    private String functionName;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "s3_location", nullable = false)
    private String s3Location;

    @Column(name = "result_json_schema", nullable = false)
    private String jsonSchema;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "last_updated", nullable = false, updatable = false)
    @UpdateTimestamp
    private Date lastUpdateDate;

    public UUID getFunctionId() {
        return functionId;
    }

    public void setFunctionId(UUID functionId) {
        this.functionId = functionId;
    }

    public FunctionState getState() {
        return FunctionState.valueOf(state);
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getS3Location() {
        return s3Location;
    }

    public void setS3Location(String s3Location) {
        this.s3Location = s3Location;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public UUID getFiuId() {
        return fiuId;
    }

    public void setFiuId(String fiuId) {
        this.fiuId = UUID.fromString(fiuId);
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getJsonSchema() {
        return jsonSchema;
    }

    public void setJsonSchema(String jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

}

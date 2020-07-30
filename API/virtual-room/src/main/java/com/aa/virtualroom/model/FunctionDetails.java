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

    @Column(name = "function_name", nullable = false,length = 1000)
    private String functionName;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "s3_location", nullable = false,length = 1000)
    private String s3Location;

    @Column(name = "result_json_schema", nullable = false,length = 500000)
    private String jsonSchema;

    @Column(name = "function_description",length = 1000)
    private String functionDescription;

    @Column(name = "handler",nullable = false,length = 1000)
    private String handler;

    @Column(name = "runtime",nullable = false,length = 1000)
    private String runtime;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "last_updated", nullable = false)
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

	public String getFunctionDescription() {
		return functionDescription;
	}

	public void setFunctionDescription(String functionDescription) {
		this.functionDescription = functionDescription;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public void setFiuId(UUID fiuId) {
		this.fiuId = fiuId;
	}

    
}

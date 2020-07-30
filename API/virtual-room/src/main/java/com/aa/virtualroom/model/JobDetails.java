package com.aa.virtualroom.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "jobs")
public class JobDetails {

    public JobDetails() {
        super();
    }

    public JobDetails(UUID jobId, UUID fiuId, UUID functionId, Date createDate, Date lastUpdateDate,
                      JobState state, int retry, String aaId, String requestParams, String result) {
        super();
        this.jobId = jobId;
        this.fiuId = fiuId;
        this.functionId = functionId;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.state = state.name();
        this.requestParams = requestParams;
        this.retry = retry;
        this.aaId = aaId;
        this.result = result;
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
    private UUID jobId;

    @Column(name = "fiu_id", updatable = false, nullable = false)
    private UUID fiuId;

    @Column(name = "function_id", updatable = false, nullable = false)
    private UUID functionId;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "retry_count", nullable = false)
    private int retry;

    @Column(name = "aa_id", nullable = false,length = 1000)
    private String aaId;

    @Column(name = "request_params",length = 1000)
    private String requestParams;

    @Column(name = "result",length = 500000)
    private String result;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdateDate;

    @Transient
    private FunctionDetails functionDetails;

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public UUID getFiuId() {
        return fiuId;
    }

    public void setFiuId(UUID fiuId) {
        this.fiuId = fiuId;
    }

    public UUID getFunctionId() {
        return functionId;
    }

    public void setFunctionId(UUID functionId) {
        this.functionId = functionId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public String getAaId() {
        return aaId;
    }

    public void setAaId(String aaId) {
        this.aaId = aaId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public FunctionDetails getFunctionDetails() {
        return functionDetails;
    }

    public void setFunctionDetails(FunctionDetails functionDetails) {
        this.functionDetails = functionDetails;
        this.setFiuId(functionDetails.getFiuId());
        this.setFunctionId(functionDetails.getFunctionId());
    }


}

package app.model;

import app.enumeration.UserTaskStatus;
import app.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @NotNull
    @Size(min=2, max=128)
    @Getter
    @Setter
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @Getter
    @Setter
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enumeration")
    @Getter
    @Setter
    private UserTaskStatus status = UserTaskStatus.PENDING;

    @Column(name = "process_at")
    @JsonProperty("date_time")
    @JsonFormat(pattern=Constant.API_DATE_FORMAT)
    @Getter
    @Setter
    private Timestamp processAt;

    @Column(name = "process_started_at")
    @JsonProperty("process_started_at")
    @JsonFormat(pattern=Constant.API_DATE_FORMAT)
    @Getter
    @Setter
    private Timestamp processStartedAt;

    @Column(name = "process_completed_at")
    @JsonProperty("process_completed_at")
    @JsonFormat(pattern=Constant.API_DATE_FORMAT)
    @Getter
    @Setter
    private Timestamp processCompletedAt;

    @Size(max=255)
    @Getter
    @Setter
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonProperty("created_at")
    @JsonFormat(pattern=Constant.API_DATE_FORMAT)
    @Getter
    @Setter
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    @JsonProperty("modified_at")
    @JsonFormat(pattern=Constant.API_DATE_FORMAT)
    @Getter
    @Setter
    private Timestamp modifiedAt;

    public void touchProcessStartedAt() {
        this.processStartedAt = new Timestamp(System.currentTimeMillis());
    }

    public void touchProcessCompletedAt() {
        this.processCompletedAt = new Timestamp(System.currentTimeMillis());
    }
}

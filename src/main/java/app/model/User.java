package app.model;

import app.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(unique = true, updatable = false)
    @Size(min=6, max=128)
    @NotNull
    @Getter
    @Setter
    private String username;

    @Size(min=2, max=128)
    @JsonProperty("first_name")
    @Column(name = "f_name")
    @Getter
    @Setter
    private String firstName;

    @Size(min=2, max=128)
    @JsonProperty("last_name")
    @Column(name = "l_name")
    @Getter
    @Setter
    private String lastName;

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
}

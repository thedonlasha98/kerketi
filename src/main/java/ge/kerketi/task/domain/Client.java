package ge.kerketi.task.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "f_name")
    private String fName;

    @Column(name = "l_name")
    private String lName;

    @Column(name = "pid")
    private String pid;

    @Column(name = "account_number", length = 9)
    private String accountNumber;
}

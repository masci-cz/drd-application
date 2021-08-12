package cz.masci.drd.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "TEST")
@Data
public class Test implements Serializable {
    
    @Id
    @Column(name = "TEST_ID")
    private Long id;    
    
    private String name;
}

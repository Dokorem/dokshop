package dok.dokshop.catalogueservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_product", schema = "catalogue")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_description")
    private String description;

    @Column(name = "c_price")
    private Double price;

}

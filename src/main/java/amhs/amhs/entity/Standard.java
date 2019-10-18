package amhs.amhs.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "Standard")
@Table(name = "t_standard")
public class Standard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer standardId;

    private String name;
    @Column(precision = 4, scale = 2)
    private BigDecimal upperLimit;
    @Column(precision = 4, scale = 2)
    private BigDecimal lowerLimit;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "tt_id")
    private TransducerType transducerType;

    public TransducerType getTransducerType() {
        return transducerType;
    }

    public void setTransducerType(TransducerType transducerType) {
        this.transducerType = transducerType;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(BigDecimal upperLimit) {
        this.upperLimit = upperLimit;
    }

    public BigDecimal getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(BigDecimal lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

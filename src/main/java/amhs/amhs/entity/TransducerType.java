package amhs.amhs.entity;

import javax.persistence.*;

@Entity(name = "TransducerType")
@Table(name = "t_transducertype")
public class TransducerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ttId;

    private String name;

    private Integer state;

    public Integer getTtId() {
        return ttId;
    }

    public void setTtId(Integer ttId) {
        this.ttId = ttId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}

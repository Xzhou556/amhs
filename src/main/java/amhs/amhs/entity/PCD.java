package amhs.amhs.entity;

import javax.persistence.*;

@Entity(name = "PCD")
@Table(name = "province_city_district")
public class PCD {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int pid;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PCD{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                '}';
    }
}

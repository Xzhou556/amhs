package amhs.amhs.entity;

import javax.persistence.*;

@Entity(name = "WxUserFactory")
@Table(name = "wxuser_factory")
public class WxUserFactory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "factory_id")
    private Factory factory;

    @ManyToOne
    @JoinColumn(name = "wx_id")
    private WxUser wxUser;
}

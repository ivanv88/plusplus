package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dummy")
public class DummyEntity {

    @Column(name = "id_paketa")
    private int idPaketa;

    @Column(name = "len")
    private int len;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "dely")
    private int delay;

    @Column(name = "expired")
    private boolean expired;

    @Column(name = "seen", columnDefinition = "boolean default false")
    private boolean seen;

    public DummyEntity() {
    }

    public DummyEntity(int idPaketa, int len, int id, int delay, boolean expired) {
        this.idPaketa = idPaketa;
        this.len = len;
        this.id = id;
        this.delay = delay;
        this.expired = expired;
    }

    public int getIdPaketa() {
        return idPaketa;
    }

    public void setIdPaketa(int idPaketa) {
        this.idPaketa = idPaketa;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean isExpired() {
        return expired;
    }


    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}

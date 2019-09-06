package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cancel")
public class CancelEntity {

    @Column(name = "id_paketa")
    private int idPaketa;

    @Column(name = "len")
    private int len;

    @Id
    @Column(name = "id")
    private int id;

    public CancelEntity() {
    }

    public CancelEntity(int idPaketa, int len, int id) {
        this.idPaketa = idPaketa;
        this.len = len;
        this.id = id;
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

}

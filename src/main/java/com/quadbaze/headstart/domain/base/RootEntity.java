package com.quadbaze.headstart.domain.base;

import com.quadbaze.headstart.utils.json.GsonUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Olatunji O. Longe on 6/19/2016.
 */

@MappedSuperclass
public abstract class RootEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Transient
    protected HashCodeBuilder hashCoder(){
        HashHandler hashHandler = new HashHandler(getId());
        return new HashCodeBuilder(hashHandler.PRIME, hashHandler.ID_PRIME);
    }

    @Transient
    protected EqualsBuilder equalizer(){
        return new EqualsBuilder();
    }

    @Override
    public String toString() {
        return GsonUtil.toString(this);
    }

}
package com.goose.pbtest.model;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "emitents")
public class Emitent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emt_sequence")
    @SequenceGenerator(name = "emt_sequence", sequenceName = "emt_sequence", allocationSize = 1000)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "bin", nullable = false)
    private String bin;

    @Column(name = "min_range", nullable = false, length = 19)
    private String minRange;

    @Column(name = "max_range", nullable = false, length = 19)
    private String maxRange;

    @Column(name = "alpha_code", length = 2)
    private String code;

    @Column(name = "bank_name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Emitent o1 = (Emitent) o;
        return id != null && id.equals(o1.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}

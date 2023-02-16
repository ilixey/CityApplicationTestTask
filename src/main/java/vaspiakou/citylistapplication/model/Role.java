package vaspiakou.citylistapplication.model;

import com.sun.istack.NotNull;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@Table(name = "roles")
@Data
public class Role {
    @Id
    private int id;
    @NotNull
    @Type(type = "string-array")
    private String[] authorityList;
}

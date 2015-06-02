package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.DepartamentoTb;
import modelo.InstitucionTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-02T11:20:39")
@StaticMetamodel(PaisTb.class)
public class PaisTb_ { 

    public static volatile ListAttribute<PaisTb, InstitucionTb> institucionTbList;
    public static volatile ListAttribute<PaisTb, DepartamentoTb> departamentoTbList;
    public static volatile SingularAttribute<PaisTb, String> cNombre;
    public static volatile SingularAttribute<PaisTb, String> cIdioma;
    public static volatile SingularAttribute<PaisTb, Integer> eIdpais;

}
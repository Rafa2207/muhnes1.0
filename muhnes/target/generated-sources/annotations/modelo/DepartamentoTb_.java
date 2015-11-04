package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.MunicipioTb;
import modelo.PaisTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-04T11:26:15")
@StaticMetamodel(DepartamentoTb.class)
public class DepartamentoTb_ { 

    public static volatile SingularAttribute<DepartamentoTb, Integer> eIddepto;
    public static volatile SingularAttribute<DepartamentoTb, String> cNombre;
    public static volatile SingularAttribute<DepartamentoTb, PaisTb> eIdpais;
    public static volatile ListAttribute<DepartamentoTb, MunicipioTb> municipioTbList;

}
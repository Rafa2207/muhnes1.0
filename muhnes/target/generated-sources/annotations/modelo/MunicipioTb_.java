package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AreaprotegidaTb;
import modelo.CantonTb;
import modelo.DepartamentoTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-23T17:53:25")
@StaticMetamodel(MunicipioTb.class)
public class MunicipioTb_ { 

    public static volatile ListAttribute<MunicipioTb, CantonTb> cantonTbList;
    public static volatile SingularAttribute<MunicipioTb, DepartamentoTb> eIddepto;
    public static volatile ListAttribute<MunicipioTb, AreaprotegidaTb> areaprotegidaTbList;
    public static volatile SingularAttribute<MunicipioTb, String> cNombre;
    public static volatile SingularAttribute<MunicipioTb, Integer> eIdmunicipio;

}
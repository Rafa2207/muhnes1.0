package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.LocalidadTb;
import modelo.MunicipioTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-23T16:53:20")
@StaticMetamodel(CantonTb.class)
public class CantonTb_ { 

    public static volatile ListAttribute<CantonTb, LocalidadTb> localidadTbList;
    public static volatile SingularAttribute<CantonTb, Integer> eIdcanton;
    public static volatile SingularAttribute<CantonTb, String> cNombre;
    public static volatile SingularAttribute<CantonTb, MunicipioTb> eIdmunicipio;

}
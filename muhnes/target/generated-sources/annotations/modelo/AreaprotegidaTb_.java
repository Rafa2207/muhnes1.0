package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.LocalidadTb;
import modelo.MunicipioTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-20T11:57:42")
@StaticMetamodel(AreaprotegidaTb.class)
public class AreaprotegidaTb_ { 

    public static volatile SingularAttribute<AreaprotegidaTb, Integer> eIdarea;
    public static volatile SingularAttribute<AreaprotegidaTb, Double> dLongituddecimal;
    public static volatile SingularAttribute<AreaprotegidaTb, Integer> eLongitudminutos;
    public static volatile SingularAttribute<AreaprotegidaTb, Integer> eLongitudgrados;
    public static volatile SingularAttribute<AreaprotegidaTb, MunicipioTb> eIdmunicipio;
    public static volatile SingularAttribute<AreaprotegidaTb, String> mDescripcion;
    public static volatile SingularAttribute<AreaprotegidaTb, Integer> eLatitudminutos;
    public static volatile SingularAttribute<AreaprotegidaTb, Double> dLatituddecimal;
    public static volatile ListAttribute<AreaprotegidaTb, LocalidadTb> localidadTbList;
    public static volatile SingularAttribute<AreaprotegidaTb, Double> dLongitudsegundos;
    public static volatile SingularAttribute<AreaprotegidaTb, Double> dLatitudsegundos;
    public static volatile SingularAttribute<AreaprotegidaTb, String> cNombre;
    public static volatile SingularAttribute<AreaprotegidaTb, Integer> eLatitudgrados;

}
package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AreaprotegidaTb;
import modelo.CantonTb;
import modelo.EjemplarTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-25T11:52:25")
@StaticMetamodel(LocalidadTb.class)
public class LocalidadTb_ { 

    public static volatile SingularAttribute<LocalidadTb, AreaprotegidaTb> eIdarea;
    public static volatile SingularAttribute<LocalidadTb, CantonTb> eIdcanton;
    public static volatile SingularAttribute<LocalidadTb, Integer> eLongitudminutos;
    public static volatile SingularAttribute<LocalidadTb, Integer> eIdlocalidad;
    public static volatile SingularAttribute<LocalidadTb, Integer> eLongitudgrados;
    public static volatile SingularAttribute<LocalidadTb, Integer> eAltitudMax;
    public static volatile SingularAttribute<LocalidadTb, String> mDescripcion;
    public static volatile SingularAttribute<LocalidadTb, Double> dLatitudDecimal;
    public static volatile SingularAttribute<LocalidadTb, Integer> eLatitudminutos;
    public static volatile ListAttribute<LocalidadTb, EjemplarTb> ejemplarTbList;
    public static volatile SingularAttribute<LocalidadTb, Double> dLongitudsegundos;
    public static volatile SingularAttribute<LocalidadTb, Double> dLatitudsegundos;
    public static volatile SingularAttribute<LocalidadTb, String> cNombre;
    public static volatile SingularAttribute<LocalidadTb, Integer> eLatitudgrados;
    public static volatile SingularAttribute<LocalidadTb, Double> dLongitudDecimal;

}
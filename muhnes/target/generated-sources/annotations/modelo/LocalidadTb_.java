package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.CantonTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-27T11:18:17")
@StaticMetamodel(LocalidadTb.class)
public class LocalidadTb_ { 

    public static volatile SingularAttribute<LocalidadTb, Double> dLatitudDecimal;
    public static volatile SingularAttribute<LocalidadTb, Integer> eAltitudMin;
    public static volatile SingularAttribute<LocalidadTb, CantonTb> eIdcanton;
    public static volatile SingularAttribute<LocalidadTb, Integer> eIdlocalidad;
    public static volatile SingularAttribute<LocalidadTb, String> cNombre;
    public static volatile SingularAttribute<LocalidadTb, String> cLatitud;
    public static volatile SingularAttribute<LocalidadTb, String> cLongitud;
    public static volatile SingularAttribute<LocalidadTb, Integer> eAltitudMax;
    public static volatile SingularAttribute<LocalidadTb, String> mDescripcion;
    public static volatile SingularAttribute<LocalidadTb, Double> dLongitudDecimal;

}
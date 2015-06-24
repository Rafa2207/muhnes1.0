package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.InsumoTb;
import modelo.ProyectoTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-24T15:44:18")
@StaticMetamodel(ActividadTb.class)
public class ActividadTb_ { 

    public static volatile SingularAttribute<ActividadTb, String> cTipo;
    public static volatile SingularAttribute<ActividadTb, ProyectoTb> eIdproyecto;
    public static volatile SingularAttribute<ActividadTb, Double> dTotal;
    public static volatile SingularAttribute<ActividadTb, Integer> eIdactividad;
    public static volatile SingularAttribute<ActividadTb, Date> fFecha;
    public static volatile ListAttribute<ActividadTb, InsumoTb> insumoTbList;
    public static volatile SingularAttribute<ActividadTb, String> mDescripcion;
    public static volatile SingularAttribute<ActividadTb, Date> fFechafin;
    public static volatile SingularAttribute<ActividadTb, String> mNombre;

}
package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ActividadTb;
import modelo.ProcesoejemplarTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T14:41:01")
@StaticMetamodel(ProyectoTb.class)
public class ProyectoTb_ { 

    public static volatile SingularAttribute<ProyectoTb, Integer> eIdproyecto;
    public static volatile SingularAttribute<ProyectoTb, Date> fFechaFin;
    public static volatile ListAttribute<ProyectoTb, ActividadTb> actividadTbList;
    public static volatile SingularAttribute<ProyectoTb, Integer> eResponsable;
    public static volatile ListAttribute<ProyectoTb, ProcesoejemplarTb> procesoejemplarTbList;
    public static volatile SingularAttribute<ProyectoTb, Date> fFechaInicio;
    public static volatile SingularAttribute<ProyectoTb, String> mDescripcion;
    public static volatile SingularAttribute<ProyectoTb, String> mNombre;

}
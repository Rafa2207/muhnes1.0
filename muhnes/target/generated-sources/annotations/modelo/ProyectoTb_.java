package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ActividadTb;
import modelo.DespachoTb;
import modelo.NotapreliminarTb;
import modelo.ProcesoejemplarTb;
import modelo.ProrrogaProyectoTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-30T07:08:07")
@StaticMetamodel(ProyectoTb.class)
public class ProyectoTb_ { 

    public static volatile SingularAttribute<ProyectoTb, Integer> eEstado;
    public static volatile SingularAttribute<ProyectoTb, Integer> eIdproyecto;
    public static volatile SingularAttribute<ProyectoTb, Date> fFechaFin;
    public static volatile ListAttribute<ProyectoTb, ActividadTb> actividadTbList;
    public static volatile ListAttribute<ProyectoTb, NotapreliminarTb> notapreliminarTbList;
    public static volatile SingularAttribute<ProyectoTb, Integer> eResponsable;
    public static volatile ListAttribute<ProyectoTb, ProcesoejemplarTb> procesoejemplarTbList;
    public static volatile SingularAttribute<ProyectoTb, Date> fFechaInicio;
    public static volatile ListAttribute<ProyectoTb, ProrrogaProyectoTb> prorrogaProyectoTbList;
    public static volatile SingularAttribute<ProyectoTb, String> mDescripcion;
    public static volatile ListAttribute<ProyectoTb, DespachoTb> despachoTbList;
    public static volatile SingularAttribute<ProyectoTb, String> mNombre;

}
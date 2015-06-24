package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ProyectoTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-24T11:22:48")
@StaticMetamodel(ProcesoejemplarTb.class)
public class ProcesoejemplarTb_ { 

    public static volatile SingularAttribute<ProcesoejemplarTb, String> cTipo;
    public static volatile SingularAttribute<ProcesoejemplarTb, ProyectoTb> eIdproyecto;
    public static volatile SingularAttribute<ProcesoejemplarTb, Integer> eCantidad;
    public static volatile SingularAttribute<ProcesoejemplarTb, Integer> eRelacion;
    public static volatile SingularAttribute<ProcesoejemplarTb, Integer> eIdproceso;
    public static volatile SingularAttribute<ProcesoejemplarTb, Date> fFecha;
    public static volatile SingularAttribute<ProcesoejemplarTb, String> mDescripcion;
    public static volatile SingularAttribute<ProcesoejemplarTb, Date> fFechafin;
    public static volatile SingularAttribute<ProcesoejemplarTb, String> mNombre;

}
package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.InsumoTb;
import modelo.ProyectoTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-06T12:02:27")
@StaticMetamodel(PresupuestoTb.class)
public class PresupuestoTb_ { 

    public static volatile SingularAttribute<PresupuestoTb, String> cTipo;
    public static volatile SingularAttribute<PresupuestoTb, ProyectoTb> eIdproyecto;
    public static volatile ListAttribute<PresupuestoTb, InsumoTb> insumoTbList;
    public static volatile SingularAttribute<PresupuestoTb, Integer> eIdpresupuesto;
    public static volatile SingularAttribute<PresupuestoTb, String> mDescripcion;
    public static volatile SingularAttribute<PresupuestoTb, String> mNombre;

}
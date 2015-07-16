package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ActividadTb;
import modelo.UnidadesTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-16T11:00:58")
@StaticMetamodel(InsumoTb.class)
public class InsumoTb_ { 

    public static volatile SingularAttribute<InsumoTb, Integer> eIdinsumo;
    public static volatile SingularAttribute<InsumoTb, Double> dGasto;
    public static volatile SingularAttribute<InsumoTb, String> mTiempo;
    public static volatile SingularAttribute<InsumoTb, Double> dCantidad;
    public static volatile SingularAttribute<InsumoTb, ActividadTb> eIdactividad;
    public static volatile SingularAttribute<InsumoTb, UnidadesTb> eIdunidad;
    public static volatile SingularAttribute<InsumoTb, String> mNombre;

}
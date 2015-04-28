package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.PresupuestoTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-27T11:18:17")
@StaticMetamodel(InsumoTb.class)
public class InsumoTb_ { 

    public static volatile SingularAttribute<InsumoTb, Integer> eIdinsumo;
    public static volatile SingularAttribute<InsumoTb, Double> dGasto;
    public static volatile SingularAttribute<InsumoTb, String> mTiempo;
    public static volatile SingularAttribute<InsumoTb, PresupuestoTb> eIdpresupuesto;
    public static volatile SingularAttribute<InsumoTb, String> mCantidad;
    public static volatile SingularAttribute<InsumoTb, String> mNombre;

}
package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ActividadTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-10-13T10:23:40")
@StaticMetamodel(InsumoTb.class)
public class InsumoTb_ { 

    public static volatile SingularAttribute<InsumoTb, Integer> eIdinsumo;
    public static volatile SingularAttribute<InsumoTb, Double> dGasto;
    public static volatile SingularAttribute<InsumoTb, String> mTiempo;
    public static volatile SingularAttribute<InsumoTb, Double> dCantidad;
    public static volatile SingularAttribute<InsumoTb, ActividadTb> eIdactividad;
    public static volatile SingularAttribute<InsumoTb, String> mNombre;

}
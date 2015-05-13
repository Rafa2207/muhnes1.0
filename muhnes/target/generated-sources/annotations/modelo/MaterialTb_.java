package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ActividadTb;
import modelo.PedidoTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-13T15:05:20")
@StaticMetamodel(MaterialTb.class)
public class MaterialTb_ { 

    public static volatile SingularAttribute<MaterialTb, Double> dCantidad;
    public static volatile SingularAttribute<MaterialTb, ActividadTb> eIdactividad;
    public static volatile SingularAttribute<MaterialTb, String> cNombre;
    public static volatile ListAttribute<MaterialTb, PedidoTb> pedidoTbList;
    public static volatile SingularAttribute<MaterialTb, Integer> eIdmaterial;
    public static volatile SingularAttribute<MaterialTb, String> mDescripcion;
    public static volatile SingularAttribute<MaterialTb, String> cEstado;

}
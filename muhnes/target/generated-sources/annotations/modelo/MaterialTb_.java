package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.MaterialPedidoTb;
import modelo.MaterialProyectoTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-25T10:24:12")
@StaticMetamodel(MaterialTb.class)
public class MaterialTb_ { 

    public static volatile SingularAttribute<MaterialTb, String> mMarca;
    public static volatile ListAttribute<MaterialTb, MaterialPedidoTb> materialPedidoTbList;
    public static volatile SingularAttribute<MaterialTb, Double> dCantidadmin;
    public static volatile SingularAttribute<MaterialTb, Double> dCantidad;
    public static volatile SingularAttribute<MaterialTb, String> mCodigobarras;
    public static volatile SingularAttribute<MaterialTb, String> cNombre;
    public static volatile SingularAttribute<MaterialTb, Integer> eIdmaterial;
    public static volatile ListAttribute<MaterialTb, MaterialProyectoTb> materialProyectoTbList;
    public static volatile SingularAttribute<MaterialTb, String> mDescripcion;
    public static volatile SingularAttribute<MaterialTb, String> cEstado;

}
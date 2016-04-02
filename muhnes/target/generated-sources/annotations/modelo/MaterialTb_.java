package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.MaterialDespachoTb;
import modelo.UnidadesTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-29T15:21:17")
@StaticMetamodel(MaterialTb.class)
public class MaterialTb_ { 

    public static volatile SingularAttribute<MaterialTb, String> mMarca;
    public static volatile SingularAttribute<MaterialTb, Double> dCantidadmin;
    public static volatile SingularAttribute<MaterialTb, Double> dCantidad;
    public static volatile SingularAttribute<MaterialTb, String> mCodigobarras;
    public static volatile SingularAttribute<MaterialTb, String> cNombre;
    public static volatile SingularAttribute<MaterialTb, Integer> eIdmaterial;
    public static volatile SingularAttribute<MaterialTb, String> mDescripcion;
    public static volatile SingularAttribute<MaterialTb, String> cEstado;
    public static volatile SingularAttribute<MaterialTb, UnidadesTb> eIdunidad;
    public static volatile SingularAttribute<MaterialTb, Integer> eTipo;
    public static volatile ListAttribute<MaterialTb, MaterialDespachoTb> materialDespachoTbList;

}
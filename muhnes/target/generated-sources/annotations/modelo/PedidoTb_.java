package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.MaterialPedidoTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-10-13T10:23:40")
@StaticMetamodel(PedidoTb.class)
public class PedidoTb_ { 

    public static volatile SingularAttribute<PedidoTb, Integer> eEstado;
    public static volatile ListAttribute<PedidoTb, MaterialPedidoTb> materialPedidoTbList;
    public static volatile SingularAttribute<PedidoTb, Integer> eIdpedido;
    public static volatile SingularAttribute<PedidoTb, Date> fFechaPosibleRecibir;
    public static volatile SingularAttribute<PedidoTb, Date> fFecha;
    public static volatile SingularAttribute<PedidoTb, String> mDescripcion;

}
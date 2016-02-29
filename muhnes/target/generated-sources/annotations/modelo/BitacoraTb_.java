package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.UsuarioTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-29T08:59:24")
@StaticMetamodel(BitacoraTb.class)
public class BitacoraTb_ { 

    public static volatile SingularAttribute<BitacoraTb, Date> tFecha;
    public static volatile SingularAttribute<BitacoraTb, UsuarioTb> eIdusuario;
    public static volatile SingularAttribute<BitacoraTb, String> mDescripcion;
    public static volatile SingularAttribute<BitacoraTb, Integer> eIdbitacora;

}
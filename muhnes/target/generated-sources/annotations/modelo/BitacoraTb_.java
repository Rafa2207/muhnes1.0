package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.UsuarioTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-14T12:07:54")
@StaticMetamodel(BitacoraTb.class)
public class BitacoraTb_ { 

    public static volatile SingularAttribute<BitacoraTb, Date> tFecha;
    public static volatile SingularAttribute<BitacoraTb, UsuarioTb> eIdusuario;
    public static volatile SingularAttribute<BitacoraTb, String> mDescripcion;
    public static volatile SingularAttribute<BitacoraTb, Integer> eIdbitacora;

}
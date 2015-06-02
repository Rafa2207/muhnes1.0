package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.EjemplarParticipaExhibicionTb;
import modelo.UsuarioTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-02T11:20:39")
@StaticMetamodel(ExhibicionTb.class)
public class ExhibicionTb_ { 

    public static volatile SingularAttribute<ExhibicionTb, String> cTipo;
    public static volatile SingularAttribute<ExhibicionTb, String> mObservaciones;
    public static volatile SingularAttribute<ExhibicionTb, String> cResponsable;
    public static volatile SingularAttribute<ExhibicionTb, UsuarioTb> idUsuario;
    public static volatile SingularAttribute<ExhibicionTb, Date> fFechaReingreso;
    public static volatile ListAttribute<ExhibicionTb, EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList;
    public static volatile SingularAttribute<ExhibicionTb, Integer> eIdexhibicion;
    public static volatile SingularAttribute<ExhibicionTb, Date> fFechaSalida;
    public static volatile SingularAttribute<ExhibicionTb, String> mDireccion;
    public static volatile SingularAttribute<ExhibicionTb, String> mDestino;
    public static volatile SingularAttribute<ExhibicionTb, String> mNombre;

}
package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgenteIdentificaEjemplarTb;
import modelo.EspecieTb;
import modelo.FamiliaTb;
import modelo.GeneroTb;
import modelo.LocalidadTb;
import modelo.ProyectoTb;
import modelo.SubespecieTb;
import modelo.VariedadTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-29T16:20:43")
@StaticMetamodel(EjemplarTb.class)
public class EjemplarTb_ { 

    public static volatile ListAttribute<EjemplarTb, AgenteIdentificaEjemplarTb> AgenteIdentificaEjemplarTbIDList;
    public static volatile SingularAttribute<EjemplarTb, Date> fFechaFinIdent;
    public static volatile SingularAttribute<EjemplarTb, GeneroTb> eIdgenero;
    public static volatile SingularAttribute<EjemplarTb, String> cCodigoentrada;
    public static volatile SingularAttribute<EjemplarTb, ProyectoTb> eIdproyecto;
    public static volatile SingularAttribute<EjemplarTb, Boolean> bEstadonotas;
    public static volatile SingularAttribute<EjemplarTb, LocalidadTb> eIdlocalidad;
    public static volatile SingularAttribute<EjemplarTb, Date> fFechaInicioIdent;
    public static volatile ListAttribute<EjemplarTb, AgenteIdentificaEjemplarTb> AgenteIdentificaEjemplarTbList;
    public static volatile SingularAttribute<EjemplarTb, EspecieTb> eIdespecie;
    public static volatile SingularAttribute<EjemplarTb, String> mDescripcion;
    public static volatile SingularAttribute<EjemplarTb, String> cCalificativo;
    public static volatile SingularAttribute<EjemplarTb, Integer> eIdejemplar;
    public static volatile SingularAttribute<EjemplarTb, Integer> eResponsable;
    public static volatile SingularAttribute<EjemplarTb, Integer> eCantDuplicado;
    public static volatile SingularAttribute<EjemplarTb, VariedadTb> eVariedad;
    public static volatile SingularAttribute<EjemplarTb, Integer> eCorrelativo;
    public static volatile SingularAttribute<EjemplarTb, SubespecieTb> eIdsubespecie;
    public static volatile SingularAttribute<EjemplarTb, FamiliaTb> eIdfamilia;

}
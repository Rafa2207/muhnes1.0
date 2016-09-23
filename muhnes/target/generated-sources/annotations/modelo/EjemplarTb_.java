package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgenteIdentificaEjemplarTb;
import modelo.EjemplarDonacionTb;
import modelo.EjemplarNombrecomunTb;
import modelo.EjemplarParticipaExhibicionTb;
import modelo.LocalidadTb;
import modelo.ProyectoTb;
import modelo.TaxonomiaTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-23T15:55:14")
@StaticMetamodel(EjemplarTb.class)
public class EjemplarTb_ { 

    public static volatile ListAttribute<EjemplarTb, AgenteIdentificaEjemplarTb> AgenteIdentificaEjemplarTbIDList;
    public static volatile SingularAttribute<EjemplarTb, Date> fFechaFinIdent;
    public static volatile SingularAttribute<EjemplarTb, TaxonomiaTb> eIdtaxonomia;
    public static volatile SingularAttribute<EjemplarTb, String> cCodigoentrada;
    public static volatile SingularAttribute<EjemplarTb, ProyectoTb> eIdproyecto;
    public static volatile SingularAttribute<EjemplarTb, Boolean> bEstadonotas;
    public static volatile SingularAttribute<EjemplarTb, Integer> eIdinstitucion;
    public static volatile SingularAttribute<EjemplarTb, LocalidadTb> eIdlocalidad;
    public static volatile SingularAttribute<EjemplarTb, Date> fFechaInicioIdent;
    public static volatile ListAttribute<EjemplarTb, AgenteIdentificaEjemplarTb> AgenteIdentificaEjemplarTbList;
    public static volatile SingularAttribute<EjemplarTb, String> mDescripcion;
    public static volatile SingularAttribute<EjemplarTb, String> cCalificativo;
    public static volatile ListAttribute<EjemplarTb, EjemplarDonacionTb> ejemplarDonacionTbList;
    public static volatile SingularAttribute<EjemplarTb, Integer> eEstado;
    public static volatile SingularAttribute<EjemplarTb, Integer> eIdejemplar;
    public static volatile SingularAttribute<EjemplarTb, Integer> eCantDuplicado;
    public static volatile ListAttribute<EjemplarTb, EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList;
    public static volatile SingularAttribute<EjemplarTb, Integer> eResponsable;
    public static volatile SingularAttribute<EjemplarTb, Integer> eCorrelativo;
    public static volatile ListAttribute<EjemplarTb, EjemplarNombrecomunTb> ejemplarNombrecomunTbList;

}
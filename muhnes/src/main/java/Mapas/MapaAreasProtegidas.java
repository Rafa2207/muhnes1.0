
package Mapas;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import modelo.AreaprotegidaTb;
 
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
 
@ManagedBean
public class MapaAreasProtegidas implements Serializable {
    
    private MapModel puntos;
  
    @PostConstruct
    public void init() {
        puntos = new DefaultMapModel();
          
        //Shared coordinates
        LatLng coord1 = new LatLng(13.7373423,-89.2865324);
        LatLng coord2 = new LatLng(13.670231,-89.247016);
        LatLng coord3 = new LatLng(14.3768246,-89.3857725);
        
          
        //Basic marker
        puntos.addOverlay(new Marker(coord1, "Boqueron"));
        puntos.addOverlay(new Marker(coord2, "Jardín botánico La Laguna"));
        puntos.addOverlay(new Marker(coord3, "Montecristo"));
        
    }

    public MapModel getPuntos() {
        return puntos;
    }

    public void setPuntos(MapModel puntos) {
        this.puntos = puntos;
    }
    
    public MapModel localidades(double lat, double lon,String nombre){
        MapModel punto;
        punto = new DefaultMapModel();
        LatLng coord1 = new LatLng(lat,lon);
        punto.addOverlay(new Marker(coord1, nombre)); 
        return punto;
    }
    
    public MapModel localidades(List<AreaprotegidaTb> area ){
        int i=0;
        MapModel punto;
        punto = new DefaultMapModel();
        for(AreaprotegidaTb areapunto:area){      
        LatLng coord1 = new LatLng(12,90);
        punto.addOverlay(new Marker(coord1,areapunto.getCNombre()));    
        }      
        return punto; 
    }
  
    
}

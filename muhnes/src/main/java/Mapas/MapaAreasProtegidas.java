
package Mapas;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
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
    
  
    
}

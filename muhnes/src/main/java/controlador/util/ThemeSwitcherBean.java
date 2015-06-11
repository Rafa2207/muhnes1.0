/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.util;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "themeSwitcherBean")
@SessionScoped
public class ThemeSwitcherBean {

   private Map<String, String> themes;

   private String theme= "south-street";

   public Map<String, String> getThemes() {
      return themes;
   }

   public String getTheme() {
      return theme;
   }

   public void setTheme(String theme) {
      this.theme = theme;
   }

   @PostConstruct
   public void init() {

      themes = new TreeMap<String, String>();
      themes.put("Azul cielo", "bluesky");
      themes.put("Azul nuboso", "cupertino");
      themes.put("Azul vaquero", "hot-sneaks");
      themes.put("Cohete", "rocket");
      themes.put("Emotivo", "excite-bike");
      themes.put("Flora", "south-street");
      themes.put("Naranja", "ui-lightness");
      themes.put("Película", "flick");
      themes.put("Pimienta", "pepper-grinder");
      themes.put("Rojo", "blitzer");
      
      //themes.put("Casablanca", "casablanca");
      //themes.put("Dark-Hive", "dark-hive");
      //themes.put("Eggplant", "eggplant");
      //themes.put("Glass-X", "glass-x");
      //themes.put("Le-Frog", "le-frog");
      //themes.put("Midnight", "midnight");
      //themes.put("Overcast", "overcast");
      //themes.put("Redmond", "redmond");
      //themes.put("Sam", "sam");
      //themes.put("Smoothness", "smoothness");
      //themes.put("Start", "start");
      //themes.put("Sunny", "sunny");
      //themes.put("Swanky-Purse", "swanky-purse");
      //themes.put("Trontastic", "trontastic");
      //themes.put("UI-Darkness", "ui-darkness");
      
      //themes.put("Vader", "vader");
   }
public void saveUserTheme()
{
//logic to update the user preferred theme name in DB
 
            
}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.primefaces.ultima.component.util;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author usuario
 */


@SessionScoped
@ManagedBean(name="languajeBean")
public class LanguajeBean implements Serializable{
    
    private String language;

    public LanguajeBean() {
        language = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");
        if (language==null){
            language = "es";
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
            language = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    public void localityChanged(ValueChangeEvent e){
        String newLocaleValue = e.getNewValue().toString();
        if (newLocaleValue.equals("en")){
            language= "en";
            FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
        }else{
            FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es","espana"));
            language= "es";
        }
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        HttpSession session = request.getSession(false);
//        session.setAttribute("languaje", languaje);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
    }

    
}
 
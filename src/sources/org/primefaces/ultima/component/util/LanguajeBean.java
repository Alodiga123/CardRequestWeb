/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.primefaces.ultima.component.util;

import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.alodiga.primafaces.controllers.FormCardDataController;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.Language;
import com.cms.commons.util.EJBServiceLocator;
import com.cms.commons.util.EjbConstants;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private String languaje;
    private Language language;
    private UtilsEJB utilsEJB;

    public LanguajeBean() {
        language = (Language)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");
        if (language == null || languaje==null) {
            try {
                languaje = "es";
                FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es","espana"));
                utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
                EJBRequest request = new EJBRequest();
                request.setParam(2L);
                language = utilsEJB.loadLanguage(request);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
            } catch (RegisterNotFoundException ex) {
                Logger.getLogger(LanguajeBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullParameterException ex) {
                Logger.getLogger(LanguajeBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GeneralException ex) {
                Logger.getLogger(LanguajeBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
             languaje = language.getIso();
            
        System.out.println("Entro al constructor");
    }

    public String getLanguaje() {
        System.out.println("el lenguage es:"+languaje);
        return languaje;
    }

    public void setLanguaje(String languaje) {
        this.languaje = languaje;
    }
    
    public void localityChanged(ValueChangeEvent e){
        String newLocaleValue = e.getNewValue().toString();
        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
        EJBRequest request = new EJBRequest();
        if (newLocaleValue.equals("en")){
            languaje= "en";
            FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
            request.setParam(1L);
        }else{
            FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es","espana"));
            languaje= "es";
             request.setParam(2L);
        }

        try {
            language = utilsEJB.loadLanguage(request);
        } catch (RegisterNotFoundException ex) {
            Logger.getLogger(LanguajeBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(LanguajeBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            Logger.getLogger(LanguajeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
    }

    
}
 
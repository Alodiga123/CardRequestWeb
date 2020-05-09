package com.alodiga.primafaces.controllers;

import com.cms.commons.models.Language;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class CompleteFormController {
    private Language language =null;
  
    @PostConstruct
    public void init() {
         language = (Language)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");    
    }

     
    public void doRediret() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
            FacesContext.getCurrentInstance().getExternalContext().redirect("addComplementaryCard.xhtml");
        } catch (IOException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        }
    }
    
     public void doCompleted() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {

        }
    }
    
      
}

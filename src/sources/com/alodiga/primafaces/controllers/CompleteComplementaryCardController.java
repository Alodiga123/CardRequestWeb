package com.alodiga.primafaces.controllers;

import com.cms.commons.models.Language;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class CompleteComplementaryCardController {
    private String language =null;
  
    @PostConstruct
    public void init() {
        language = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");      
    }

   
   
    public void doRediret() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
            FacesContext.getCurrentInstance().getExternalContext().redirect("addComplementaryCard.xhtml");
        } catch (IOException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.CompleteComplementaryCardController.doRediret()");
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

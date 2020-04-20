package com.alodiga.primafaces.controllers;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class CompleteComplementaryCardController {

  
    @PostConstruct
    public void init() {
              
    }

   
   
    public void doRediret() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("addComplementaryCard.xhtml");
        } catch (IOException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.CompleteComplementaryCardController.doRediret()");
        }
    }
    
     public void doCompleted() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {

        }
    }
      
}

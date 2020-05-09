/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alodiga.primafaces.controllers;

import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.models.Country;
import com.cms.commons.util.EJBServiceLocator;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.Language;
import com.ericsson.alodiga.ws.APIRegistroUnificadoProxy;
import com.ericsson.alodiga.ws.RespuestaCodigoRandom;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.ultima.domain.Solicitude;

@ManagedBean
@ViewScoped
public class SendCodeController {
    public Solicitude solicitude;
    private UtilsEJB utilsEJB;
    private String cellNumber;
    private Country country;
    private String code;
    private Map<String, String> countries = null;
    private String messages = null;
    ResourceBundle bundle = null;
    private Language language =null;
   
    @PostConstruct
    public void init() {
        try {
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            bundle = ResourceBundle.getBundle("com.alodiga.primafeces.messages/message", locale);
            utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
            language = (Language)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");
            EJBRequest request = new EJBRequest();
            request.setParam(2);
            country = utilsEJB.loadCountry(request);
            cellNumber = country.getCode();
        } catch (RegisterNotFoundException ex) {
            Logger.getLogger(SendCodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(SendCodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            ex.printStackTrace();
            Logger.getLogger(SendCodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

     public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }
    

  public Map<String, String> getCountries() {
        EJBRequest request = new EJBRequest();
        countries = new TreeMap<String, String>();
        try {
            List<Country> countries1 = utilsEJB.getCountries(request);
            for (Country country : countries1) {
                countries.put(country.getName(), country.getId().toString());
            }
        } catch (EmptyListException ex) {
//            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return countries;
    }
    
    public void setCountries(Map<String, String> countries) {
        this.countries = countries;
    }
    
     public void reloadCountry(AjaxBehaviorEvent event) {
        country = (Country) ((UIOutput) event.getSource()).getValue();
        cellNumber = country.getCode();
    }
  
    public void reset() {
        RequestContext.getCurrentInstance().reset("sendCodeForm:grid");
    }
    
    public void doRediret() {
        try {
            //enviar codigo
            System.out.println("Pais:"+country.getName() +"Telefono:"+cellNumber);
//            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cellNumber", cellNumber);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("country", country);
            APIRegistroUnificadoProxy proxy = new APIRegistroUnificadoProxy();
            
            RespuestaCodigoRandom response =proxy.generarCodigoMovilSMS("usuarioWS","passwordWS",cellNumber);
            System.out.println("Respuesta Code:"+response.getCodigoRespuesta());
            System.out.println("Respuesta Mensaje:"+response.getMensajeRespuesta());
            System.out.println("Respuesta Codigo:"+response.getDatosRespuesta());
            if (response.getCodigoRespuesta().equals("00")) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codigo", response.getDatosRespuesta());
                FacesContext.getCurrentInstance().getExternalContext().redirect("validateCode.xhtml");
//           if (true) {
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codigo", "12345");
//                FacesContext.getCurrentInstance().getExternalContext().redirect("validateCode.xhtml");
            }else{
                messages = bundle.getString("common.error.send.code");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages)); 
            }
        
        } catch (Exception ex) {
            ex.printStackTrace();
            messages = bundle.getString("common.error.send.code");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            System.out.println("Error Send Code");
        }
    }
    
      
}

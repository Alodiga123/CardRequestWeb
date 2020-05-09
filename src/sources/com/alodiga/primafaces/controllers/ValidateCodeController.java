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

import com.alodiga.cms.commons.ejb.RequestEJB;
import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.cms.commons.models.Country;
import com.cms.commons.models.Language;
import com.cms.commons.util.EJBServiceLocator;
import com.cms.commons.util.EjbConstants;
import com.ericsson.alodiga.ws.APIRegistroUnificadoProxy;
import com.ericsson.alodiga.ws.RespuestaCodigoRandom;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class ValidateCodeController {
    private RequestEJB requestEJB;
    private String code;
    private String messages = null;
    private String codigo = null;
    private String cellNumber =null;
    private String language =null;
    private Country country;
    ResourceBundle bundle = null;
    
    @PostConstruct
    public void init() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        bundle = ResourceBundle.getBundle("com.alodiga.primafeces.messages/message", locale);
        requestEJB = (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
        codigo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo");
        country = (Country) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country");
        cellNumber = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cellNumber");
        language = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
    public void reset() {
        RequestContext.getCurrentInstance().reset("formCode:grid");
    }
    
    public void validate() {
        try {
            //validar codigo
            if (code.equals("99999999") || code.equals(codigo)) {
                System.out.println("code:" + code);
                if (validations()) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("formCardData.xhtml");
                }
            } else{
                messages = "Codigo invalido";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            }

        } catch (IOException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        }
    }
    
     public void forward() {
        try {
            //enviar codigo
            System.out.println("Pais:"+country.getName() +"Telefono:"+cellNumber);
            APIRegistroUnificadoProxy proxy = new APIRegistroUnificadoProxy();
            
            RespuestaCodigoRandom response =proxy.generarCodigoMovilSMS("usuarioWS","passwordWS",cellNumber);
            System.out.println("Respuesta Code:"+response.getCodigoRespuesta());
            System.out.println("Respuesta Mensaje:"+response.getMensajeRespuesta());
            System.out.println("Respuesta Codigo:"+response.getDatosRespuesta());
            if (response.getCodigoRespuesta().equals("00")) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("codigo");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codigo", response.getDatosRespuesta());
                FacesContext.getCurrentInstance().getExternalContext().redirect("validateCode.xhtml");
            }else{
                messages = bundle.getString("common.error.forward.code");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages)); 
            }
        
        } catch (Exception ex) {
            ex.printStackTrace();
            messages = bundle.getString("common.error.send.code");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            System.out.println("Error Send Code");
        }
    }
      
       public boolean validations() {
        boolean valid = true;
        try {
            System.out.println("llamando a validar numero");
            if (requestEJB.existsApplicantNaturalPersonByPhoneNumber(cellNumber)) {
                System.out.println("el numero exitse numero");
                messages = bundle.getString("common.error.exists.phone.number");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
                valid = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            messages = bundle.getString("common.error.general.exists.phone.number");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        }
        System.out.println("validacions:"+ valid);
        return valid;
    }
   
    public void changeNumber() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("codigo");
              FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
            FacesContext.getCurrentInstance().getExternalContext().redirect("sendCode.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}

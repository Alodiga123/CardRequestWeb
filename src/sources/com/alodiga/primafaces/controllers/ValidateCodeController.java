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

import org.primefaces.ultima.view.input.*;
import com.alodiga.cms.commons.ejb.PersonEJB;
import com.alodiga.cms.commons.ejb.RequestEJB;
import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.models.City;
import com.cms.commons.models.Country;
import com.cms.commons.models.State;
import com.cms.commons.util.EJBServiceLocator;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.CivilStatus;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.EdificationType;
import com.cms.commons.models.Profession;
import com.cms.commons.models.StreetType;
import com.cms.commons.models.ZipZone;
import com.cms.commons.util.QueryConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.ultima.domain.Solicitude;

@ManagedBean
@ViewScoped
public class ValidateCodeController {
    private UtilsEJB utilsEJB;
    private PersonEJB personEJB;
    private RequestEJB requestEJB;
    private String code;
 
    
    @PostConstruct
    public void init() {

            utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
            personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);
            requestEJB= (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
          
    
       
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
    
    public void doRediret() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("formCardData.xhtml");
        } catch (IOException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        }
    }
    
       
}

/*
 * Copyright 2009 Prime Technology.
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
package org.primefaces.ultima.component.util;

import com.alodiga.cms.commons.ejb.PersonEJB;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.CivilStatus;
import com.cms.commons.models.Title;
import com.cms.commons.util.EJBServiceLocator;
import com.cms.commons.util.EjbConstants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("titleConverter")
public class TitleConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        Title title = null;
        try {
            PersonEJB  personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);
            EJBRequest request = new EJBRequest();
            request.setParam(Integer.parseInt(submittedValue));
            title = personEJB.loadTitle(request);
        } catch (RegisterNotFoundException ex) {
            Logger.getLogger(TitleConverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(TitleConverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            ex.printStackTrace();
            Logger.getLogger(TitleConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return title;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(value);
	}
		}
	}

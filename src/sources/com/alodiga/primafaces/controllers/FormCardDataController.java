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
import com.cms.commons.models.ApplicantNaturalPerson;
import com.cms.commons.models.CivilStatus;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.Language;
import com.cms.commons.models.StreetType;
import com.cms.commons.models.ZipZone;
import com.cms.commons.util.QueryConstants;
import com.ericsson.alodiga.ws.APIRegistroUnificadoProxy;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class FormCardDataController {
    private UtilsEJB utilsEJB;
    private PersonEJB personEJB;
    private RequestEJB requestEJB;
    private Country country;
    private State state;
    private City city;
    private Map<String, String> countries = null;
    private Map<String, String> countriesDoc = null;
    private Map<String, String> states = null;
    private Map<String, String> cities = null;
    ResourceBundle bundle = null;
    private String cellNumber =null;
    public List<String> civilStates; 
    public String name;
    public String lastName;
    public String recommendation; 
    public String promotion; 
    public String citizen; 
    public List<String> recommendations;  
    public List<String> promotions;  
    public List<String> citizens;  
    public Date birthdate;
    public String email;
    public String password1;
    public String password2;
    public List<String> genders;  
    public String gender; 
    public String maritalStatus;
    public String documentNumber;
    public String edification;
    public String street;
    public String street2;
    public String number;
    private DocumentsPersonType documentsPersonType;
    private StreetType streetType;
    private CivilStatus civilStatus;
    private Map<String, String> documentsPersonTypes = null;
    private Map<String, String> civilStatuses = null;
    private Map<String, String> zipZones = null;
    private String zipZone;
    private String messages = null;
    private String codigo = null;
    private String pin;
    private String ipRemoteAddress;
    private String language =null;
    private Language idioma= null;
    Long personTypeId = null;

    @PostConstruct
    public void init() {
        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
        personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);
        requestEJB = (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
        recommendations = new ArrayList<String>();
        promotions = new ArrayList<String>();
        citizens = new ArrayList<String>();
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        bundle = ResourceBundle.getBundle("com.alodiga.primafeces.messages/message", locale);
        recommendations.add(bundle.getString("option.yes"));
        recommendations.add(bundle.getString("option.no"));
        promotions.add(bundle.getString("option.yes"));
        promotions.add(bundle.getString("option.no"));
        citizens.add(bundle.getString("option.yes"));
        citizens.add(bundle.getString("option.no"));
        genders = new ArrayList<String>();
        genders.add(bundle.getString("common.female"));
        genders.add(bundle.getString("common.male"));
        country = (Country) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country");
        language = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");
        System.out.println("... lenguage ....:" + language);
        EJBRequest request = new EJBRequest();
        if (language.equals("es"))
            request.setParam(2L);
        else
            request.setParam(1L);
        try {
            idioma = utilsEJB.loadLanguage(request);
        } catch (RegisterNotFoundException ex) {
            Logger.getLogger(FormCardDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(FormCardDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            Logger.getLogger(FormCardDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cellNumber = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cellNumber");
        if (country != null && cellNumber != null) {
            if (country.getId() == 1) {
                personTypeId = 3L;
            } else {
                personTypeId = 4L;
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {

            }
        }
        codigo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo");
            ipRemoteAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr(); 
            
            System.out.println("Ip remota:"+ipRemoteAddress);
    }


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public List<String> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<String> promotions) {
        this.promotions = promotions;
    }

    public List<String> getCitizens() {
        return citizens;
    }

    public void setCitizens(List<String> citizens) {
        this.citizens = citizens;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public List<String> getGenders() {
        return genders;
    }

    public void setGenders(List<String> genders) {
        this.genders = genders;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getEdification() {
        return edification;
    }

    public void setEdification(String edification) {
        this.edification = edification;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public DocumentsPersonType getDocumentsPersonType() {
        return documentsPersonType;
    }

    public void setDocumentsPersonType(DocumentsPersonType documentsPersonType) {
        this.documentsPersonType = documentsPersonType;
    }

    public CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public List<String> getCivilStates() {
        return civilStates;
    }

    public void setCivilStates(List<String> civilStates) {
        this.civilStates = civilStates;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public StreetType getStreetType() {
        return streetType;
    }

    public void setStreetType(StreetType streetType) {
        this.streetType = streetType;
    }

    public String getZipZone() {
        return zipZone;
    }

    public void setZipZone(String zipZone) {
        this.zipZone = zipZone;
    }
    

    public Map<String, String> getDocumentsPersonTypes() {
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(EjbConstants.PARAM_PERSON_TYPE_ID, personTypeId != null ? personTypeId : null);
        request.setParams(params);
        documentsPersonTypes = new TreeMap<String, String>();
        try {
            List<DocumentsPersonType> dts = personEJB.getDocumentsPersonTypeByPersonType(request);
            for (DocumentsPersonType documentType : dts) {
                documentsPersonTypes.put(documentType.getDescription(), documentType.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return documentsPersonTypes;
    }

    public void setDocumentsPersonTypes(Map<String, String> documentsPersonTypes) {
        this.documentsPersonTypes = documentsPersonTypes;
    }

    public Map<String, String> getCivilStatuses() {
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(QueryConstants.PARAM_LANGUAGE_ID, idioma != null ? idioma.getId() : null);
        request.setParams(params);
        civilStatuses = new TreeMap<String, String>();
        try {
            List<CivilStatus> dts = personEJB.getCivilStatusByLanguage(request);
            for (CivilStatus civil : dts) {
                civilStatuses.put(civil.getDescription(), civil.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return civilStatuses;
    }

    public void setCivilStatuses(Map<String, String> civilStatuses) {
        this.civilStatuses = civilStatuses;
    }
    
       
       public Map<String, String> getStates() {
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(QueryConstants.PARAM_COUNTRY_ID, country != null ? country.getId() : null);
        request.setParams(params);
        states = new TreeMap<String, String>();
        try {
            List<State> states1 = utilsEJB.getStatesByCountry(request);
            for (State state : states1) {
                states.put(state.getName(), state.getId().toString());
            }
        } catch (EmptyListException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            ex.printStackTrace();
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
             ex.printStackTrace();
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return states;
    }
    
    public void setStates(Map<String, String> states) {
        this.states = states;
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

    public Map<String, String> getCountriesDoc() {
        EJBRequest request = new EJBRequest();
        countriesDoc = new TreeMap<String, String>();
        try {
            List<Country> countries1 = utilsEJB.getCountries(request);
            for (Country country : countries1) {
                countriesDoc.put(country.getName(), country.getId().toString());
            }
        } catch (EmptyListException ex) {
//            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return countriesDoc;
    }

    public void setCountriesDoc(Map<String, String> countriesDoc) {
        this.countriesDoc = countriesDoc;
    }
    
    
    public Map<String, String> getCities() {
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(QueryConstants.PARAM_STATE_ID, state != null ? state.getId() : null);
        request.setParams(params);
        cities = new TreeMap<String, String>();
      
            List<City> cities1;
        try {
            cities1 = utilsEJB.getCitiesByState(request);
            for (City city : cities1) {
                cities.put(city.getName(), city.getId().toString());
            }
        } catch (EmptyListException ex) {
            Logger.getLogger(FormCardDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            Logger.getLogger(FormCardDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(FormCardDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return cities;
    }

     public void setCities(Map<String, String> cities) {
        this.cities = cities;
    }
   
    
   
   
    
    public void reloadStates(AjaxBehaviorEvent event) {
        Country country1 = (Country) ((UIOutput) event.getSource()).getValue();
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(QueryConstants.PARAM_COUNTRY_ID, country1.getId());
        request.setParams(params);
        states = new TreeMap<String, String>();
        try {
            List<State> states1 = utilsEJB.getStatesByCountry(request);
            for (State state1 : states1) {
                states.put(state1.getName(), state1.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void reloadSCivilStatus() {
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(QueryConstants.PARAM_LANGUAGE_ID, idioma != null ? idioma.getId() : null);
        request.setParams(params);
        civilStatuses = new TreeMap<String, String>();
        try {
            List<CivilStatus> dts = personEJB.getCivilStatusByLanguage(request);
            for (CivilStatus civil : dts) {
                civilStatuses.put(civil.getDescription(), civil.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
     public void reloadSDocumentPersonType(AjaxBehaviorEvent event) {
        Country country1 = (Country) ((UIOutput) event.getSource()).getValue();
        if (country1!=null){
            System.out.println(".............countryId:"+country1.getId());
             if (country1.getId()==1)
                 personTypeId=3L;
             else
                 personTypeId=4L; 
            }
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(EjbConstants.PARAM_PERSON_TYPE_ID, personTypeId != null ? personTypeId : null);
        request.setParams(params);
        documentsPersonTypes = new TreeMap<String, String>();
        try {
           List<DocumentsPersonType> dts = personEJB.getDocumentsPersonTypeByPersonType(request);
           for (DocumentsPersonType documentType : dts) {
                documentsPersonTypes.put(documentType.getDescription(), documentType.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
    
     
     
     public Map<String, String> getZipZones() {
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(QueryConstants.PARAM_CITY_ID, city != null ? city.getId() : null);
        request.setParams(params);
        zipZones = new TreeMap<String, String>();
        try {
            List<ZipZone> zipZoneByCities = utilsEJB.getZipZoneByCities(request);
            for (ZipZone zipZone : zipZoneByCities) {
                zipZones.put(zipZone.getName(), zipZone.getId().toString());
            }
        } catch (EmptyListException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            ex.printStackTrace();
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
             ex.printStackTrace();
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zipZones;
    }

    public void setZipZones(Map<String, String> zipZones) {
        this.zipZones = zipZones;
    }
    
    public void reloadZipZOne(AjaxBehaviorEvent event) {
        City city = (City) ((UIOutput) event.getSource()).getValue();
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(QueryConstants.PARAM_CITY_ID, city != null ? city.getId() : null);
        request.setParams(params);
        zipZones = new TreeMap<String, String>();
        try {
            List<ZipZone> zipZoneByCities = utilsEJB.getZipZoneByCities(request);
            for (ZipZone zone : zipZoneByCities) {
                zipZones.put(zone.getName(), zone.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reloadCities(AjaxBehaviorEvent event) {
        State state = (State) ((UIOutput) event.getSource()).getValue();
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(QueryConstants.PARAM_STATE_ID, state.getId());
        request.setParams(params);
        cities = new TreeMap<String, String>();
        try {
            List<City> cities1 = utilsEJB.getCitiesByState(request);
            for (City city1 : cities1) {
                cities.put(city1.getName(), city1.getId().toString());
            }
        } catch (EmptyListException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      public void save() {
         String address = null;
         try {
             if (validations()) {
                 ZipZone postalZone = new ZipZone();
                 postalZone.setName(street);
                 postalZone.setCode(zipZone);
                 postalZone = utilsEJB.saveZipZone(postalZone);
                 System.out.println("postalZone guardado:"+postalZone.getId());
                 address = street + " " + number;
                 ApplicantNaturalPerson applicantNaturalPerson = requestEJB.saveRequestPersonData(country.getId(), email, new Date((new java.util.Date()).getTime()), name, lastName, birthdate,
                         cellNumber, country.getId(), state.getId(), city.getId(), postalZone, recommendation.equals(bundle.getString("option.yes")) ? true : false,
                         promotion.equals(bundle.getString("option.yes")) ? true : false, citizen.equals(bundle.getString("option.yes")) ? true : false,  documentsPersonType,
                         documentNumber,gender.equals(bundle.getString("common.female"))?"F":"M",civilStatus,street,street2,number);
                 try {
                     APIRegistroUnificadoProxy proxy = new APIRegistroUnificadoProxy();
                     proxy.guardarUsuarioAplicacionMovil("usuarioWS", "passwordWS", null, name, lastName, password1, email, cellNumber, birthdate.toString(), address, String.valueOf(country.getId()), String.valueOf(state.getId()), String.valueOf(city.getId()),
                             null, zipZone, codigo, null, null, ipRemoteAddress, pin);
                 } catch (Exception ex) {
                     System.out.println("Registro Unificado no esta funcionando no registro applicantNaturalPersonId" +applicantNaturalPerson.getId());
                 }
                 if (applicantNaturalPerson != null) {
                     FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                     FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("applicantNaturalPerson", applicantNaturalPerson);
                     FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("country", country);
                     FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
                     FacesContext.getCurrentInstance().getExternalContext().redirect("takePhoto.xhtml");
                 } else {
                     messages = bundle.getString("common.error.save.form");
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
                 }
             } 
        } catch (RegisterNotFoundException ex) {
            System.out.println("RegisterNotFoundException..");
        } catch (EmptyListException ex) {
            System.out.println("EmptyListException..");
        } catch (NullParameterException ex) {
            System.out.println("NullParameterException..");
        } catch (GeneralException ex) {
             System.out.println("GeneralException..");
        } catch (IOException ex) {
            System.out.println("IOException..");
        } 
    }

    public boolean validations() {
        boolean valid = true;
        if (documentsPersonType == null) {
            messages = bundle.getString("common.error.document.type");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (documentNumber == null || documentNumber.isEmpty()) {
            messages = bundle.getString("common.error.document.number");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (name == null || name.isEmpty()) {
            messages = bundle.getString("common.error.name");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (lastName == null || lastName.isEmpty()) {
            messages = bundle.getString("common.error.last.name");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (gender == null) {
            messages = bundle.getString("common.error.gender");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (birthdate == null) {
            messages = bundle.getString("common.error.birthday");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (civilStatus == null) {
            messages = bundle.getString("common.error.marital.status");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (email == null || email.isEmpty()) {
            messages = bundle.getString("common.error.email");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (password1 == null || password1.isEmpty()) {
            messages = bundle.getString("common.error.require.password");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (password2 == null || password2.isEmpty()) {
            messages = bundle.getString("common.error.confir.password");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (!password2.equals(password2)) {
            messages = bundle.getString("common.password.not.match");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (pin == null || pin.isEmpty()) {
            messages = bundle.getString("common.error.pin");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (country == null) {
            messages = bundle.getString("common.error.country");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (state == null) {
            messages = bundle.getString("common.error.state");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (city == null) {
            messages = bundle.getString("common.error.city");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (street == null || street.isEmpty()) {
            messages = bundle.getString("common.error.street");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (zipZone == null || zipZone.isEmpty()) {
            messages = bundle.getString("common.error.postal.zone");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (recommendation == null) {
            messages = bundle.getString("common.error.recommendation");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (promotion == null) {
            messages = bundle.getString("common.error.promotion");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (citizen == null) {
            messages = bundle.getString("common.error.citizen");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (getEdad(birthdate) < 18) {
            messages = bundle.getString("common.adult");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        }else if (email != null && !email.isEmpty()) {
            try {
                if (requestEJB.existsApplicantNaturalPersonByEmail(email)) {
                    messages = bundle.getString("common.error.exists.email");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
                    valid = false;
                }
            } catch (Exception ex) {
                messages = bundle.getString("common.error.general.exists.email");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
                valid = false;
            }
        }
        return valid;
    }
    
    public static int getEdad(Date fechaNacimiento) {
        if (fechaNacimiento != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (fechaNacimiento != null) {
                Calendar c = new GregorianCalendar();
                c.setTime(fechaNacimiento);
                return calcularEdad(c);
            }
            
        }
        return 0;
    }
    
    private static int calcularEdad(Calendar fechaNac) {
        Calendar today = Calendar.getInstance();
        int diffYear = today.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
        int diffMonth = today.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
        int diffDay = today.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
        // Si está en ese año pero todavía no los ha cumplido
        if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0)) {
            diffYear = diffYear - 1;
        }
        return diffYear;
    }
    
    public void reset() {
        RequestContext.getCurrentInstance().reset("CardDataCreateForm:grid");
    }
    
   
}

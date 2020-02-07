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
package org.primefaces.ultima.view.input;

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
public class RadioView {
    public Solicitude solicitude;
    private UtilsEJB utilsEJB;
    private PersonEJB personEJB;
    private RequestEJB requestEJB;
    private Country countryDocument;
    private Country country;
    private State state;
    private City city;
    private DocumentsPersonType documentsPersonType;
    private EdificationType edificationType;
    private StreetType streetType;
    private CivilStatus civilStatus;
    private Profession profession;
    private ZipZone zipZone;
    private Map<String, String> countries = null;
    private Map<String, String> countriesDoc = null;
    private Map<String, String> states = null;
    private Map<String, String> cities = null;
    private Map<String, String> edificationTypes = null;
    private Map<String, String> streetTypes = null;
    private Map<String, String> documentsPersonTypes = null;
    private Map<String, String> civilStatuses = null;
    private Map<String, String> professions = null;
    private Map<String, String> zipZones = null;
    
    
    @PostConstruct
    public void init() {
        try {
            utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
            personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);
            requestEJB= (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
            solicitude = new Solicitude();
            solicitude.genders = new ArrayList<String>();
            solicitude.genders.add("Masculino");
            solicitude.genders.add("Feminino");
            EJBRequest request = new EJBRequest();
            request.setParam(2);
            country = utilsEJB.loadCountry(request);
            countryDocument = utilsEJB.loadCountry(request);
        } catch (RegisterNotFoundException ex) {
            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            ex.printStackTrace();
            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

   public Solicitude getSolicitude() {
        return solicitude;
    }

    public void setSolicitude(Solicitude solicitude) {
        this.solicitude = solicitude;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountryDocument() {
        return countryDocument;
    }

    public void setCountryDocument(Country countryDocument) {
        this.countryDocument = countryDocument;
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

    public EdificationType getEdificationType() {
        return edificationType;
    }

    public void setEdificationType(EdificationType edificationType) {
        this.edificationType = edificationType;
    }

    public StreetType getStreetType() {
        return streetType;
    }

    public void setStreetType(StreetType streetType) {
        this.streetType = streetType;
    }

    public CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public DocumentsPersonType getDocumentsPersonType() {
        return documentsPersonType;
    }

    public void setDocumentsPersonType(DocumentsPersonType documentsPersonType) {
        this.documentsPersonType = documentsPersonType;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public ZipZone getZipZone() {
        return zipZone;
    }

    public void setZipZone(ZipZone zipZone) {
        this.zipZone = zipZone;
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
            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return cities;
    }

     public void setCities(Map<String, String> cities) {
        this.cities = cities;
    }
   
    
   public Map<String, String> getDocumentsPersonTypes() {
        EJBRequest request = new EJBRequest();
        
        documentsPersonTypes = new TreeMap<String, String>();
        try {
            List<DocumentsPersonType> dts = utilsEJB.getDocumentsPersonType(request);
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
     
   public Map<String, String> getEdificationTypes() {
        EJBRequest request = new EJBRequest();  
        edificationTypes = new TreeMap<String, String>();
        try {
            List<EdificationType> dts = utilsEJB.getEdificationTypes(request);
            for (EdificationType edificationType1 : dts) {
                edificationTypes.put(edificationType1.getDescription(), edificationType1.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return edificationTypes;
    }
   
    public Map<String, String> getStreetTypes() {
        EJBRequest request = new EJBRequest();
        streetTypes = new TreeMap<String, String>();
        try {
            List<StreetType> dts = utilsEJB.getStreetTypes(request);
            for (StreetType streetType : dts) {
                streetTypes.put(streetType.getDescription(), streetType.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return streetTypes;
    }
    
    
    public Map<String, String> getCivilStatuses() {
        EJBRequest request = new EJBRequest();
        civilStatuses = new TreeMap<String, String>();
        try {
            List<CivilStatus> dts = personEJB.getCivilStatus(request);
            for (CivilStatus civilStatus : dts) {
                civilStatuses.put(civilStatus.getDescription(), civilStatus.getId().toString());
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
    
     public Map<String, String> getProfessions() {
        EJBRequest request = new EJBRequest();
        professions = new TreeMap<String, String>();
        try {
            List<Profession> dts = personEJB.getProfession(request);
            for (Profession profession : dts) {
                professions.put(profession.getName(), profession.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return professions;
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
    
    public void setEdificationTypes(Map<String, String> edificationTypes) {
        this.edificationTypes = edificationTypes;
    }

    public void setStreetTypes(Map<String, String> streetTypes) {
        this.streetTypes = streetTypes;
    }

 
    public void setCivilStatuses(Map<String, String> civilStatuses) {
        this.civilStatuses = civilStatuses;
    }
    
        
    public void setProfessions(Map<String, String> professions) {
        this.professions = professions;
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
         
//int countryId, String email, int documentPersonTypeId, String identificationNumber, Date dueDateIdentification,
//                                         String firstNames, String lastNames, String marriedLastName, String gender, String placeBirth, Date dateBirth, int familyResponsabilities,  
//                                         int civilStatusId, int professionId, String roomPhone, String cellPhone, int countryAddress, int state, int city, int zipZone, int edificationType, String nameEdification,
//                                         String tower, int floor, int streetType, String nameStreet, String Urbanization, String firstNamesFamilyOne, String lastNamesFamilyOne, String cellPhoneFamilyOne,
//                                         String roomPhoneFamilyOne, String cityFamilyOne, String firstNamesFamilyTwo, String lastNamesFamilyTwo, String cellPhoneFamilyTwo, String roomPhoneFamilyTwo, String cityFamilyTwo
         try {
            Long requestPersonId = requestEJB.saveRequestPersonData(countryDocument.getId(), solicitude.getEmail(), documentsPersonType.getId(), solicitude.getDocumentNumber(),new Date((new java.util.Date()).getTime()) , solicitude.getName(), solicitude.getLastName(),
                     solicitude.getMarriedLastName(), solicitude.getGender().equals("Feminino")?"F":"M", solicitude.getPlaceBirth(), solicitude.getBirthdate(), Integer.valueOf(solicitude.getFamiliar()), civilStatus.getId(), profession.getId(), solicitude.getPhoneNumber(), solicitude.getCellNumber(), country.getId(), state.getId(), city.getId(), zipZone.getId(),
                     edificationType.getId(),  solicitude.getEdification(), solicitude.getTorre(), solicitude.getPiso(), streetType.getId(), solicitude.getCalle(), solicitude.getUrbanizacion(), solicitude.getRefName1(), solicitude.getRefLastName1(), solicitude.getRefPMobile1(), solicitude.getRefPhoneNumber1(), solicitude.getRefCiudad1(), solicitude.getRefName2(), 
                     solicitude.getRefLastName2(), solicitude.getRefPMobile2(), solicitude.getRefPMobile2(), solicitude.getRefCiudad2());
            solicitude.setCity(city.getName());
            solicitude.setCountry(country.getName());
            solicitude.setState(state.getName());
            solicitude.setDocumentType(documentsPersonType.getDescription());
            solicitude.setEdificationType(edificationType);
            solicitude.setDocumentsPersonType(documentsPersonType);
            solicitude.setStreetType(streetType);
            solicitude.setProfession(profession.getName());
            solicitude.setPostalCode(zipZone.getName());

            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("solicitude", solicitude);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("requestPersonId", requestPersonId);
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("cards.xhtml");
        } catch (RegisterNotFoundException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        } catch (EmptyListException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        } catch (NullParameterException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        } catch (GeneralException ex) {
             System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        } catch (IOException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        } 
    }

    public void reset() {
        RequestContext.getCurrentInstance().reset("CardDataCreateForm:grid");
    }
    
    public void doRediret() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("formCardData.xhtml");
        } catch (IOException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        }
    }
    
     public void doRediretIndex() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("formCardData.xhtml");
        } catch (IOException ex) {
            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
        }
    }
    
}

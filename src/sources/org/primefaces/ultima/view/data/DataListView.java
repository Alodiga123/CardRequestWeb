///*
// * Copyright 2009-2014 PrimeTek.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.primefaces.ultima.view.data;
//
////import com.alodiga.cms.commons.ejb.DataFormEJB;
//import com.alodiga.cms.commons.ejb.PersonEJB;
//import com.alodiga.cms.commons.ejb.RequestEJB;
//import com.alodiga.cms.commons.ejb.UtilsEJB;
//import com.alodiga.cms.commons.exception.EmptyListException;
//import com.alodiga.cms.commons.exception.GeneralException;
//import com.alodiga.cms.commons.exception.NullParameterException;
//import com.alodiga.cms.commons.exception.RegisterNotFoundException;
//import com.cms.commons.genericEJB.EJBRequest;
//import com.cms.commons.models.ApplicantNaturalPerson;
//import com.cms.commons.models.City;
//import com.cms.commons.models.CivilStatus;
//import com.cms.commons.models.Country;
//import com.cms.commons.models.DocumentsPersonType;
//import com.cms.commons.models.EdificationType;
//import com.cms.commons.models.KinShipApplicant;
//import com.cms.commons.models.Profession;
//import com.cms.commons.models.State;
//import com.cms.commons.models.StreetType;
//import com.cms.commons.models.ZipZone;
//import com.cms.commons.util.EJBServiceLocator;
//import com.cms.commons.util.EjbConstants;
//import com.cms.commons.util.QueryConstants;
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.annotation.PostConstruct;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.ViewScoped;
//import javax.faces.component.UIOutput;
//import javax.faces.context.FacesContext;
//import javax.faces.event.AjaxBehaviorEvent;
//import org.primefaces.context.RequestContext;
//import org.primefaces.event.SelectEvent;
//import org.primefaces.ultima.domain.CreditCard;
//import org.primefaces.ultima.domain.Solicitude;
//import org.primefaces.ultima.service.CreditCardService;
//import org.primefaces.ultima.view.input.RadioView;
//
//
//@ManagedBean(name="dataListView")
//@ViewScoped
//public class DataListView implements Serializable {
//    
//    
//    private List<CreditCard> cards = new ArrayList<CreditCard>();
//      
//    private CreditCard addCreditCard;
//    public Solicitude solicitude = new Solicitude();
//    private Country countryDocument;
//    private Country country;
//    private State state;
//    private City city;
//    private DocumentsPersonType documentsPersonType;
//    private EdificationType edificationType;
//    private StreetType streetType;
//    private CivilStatus civilStatus;
//    private Profession profession;
//    private KinShipApplicant kinShipApplicant;
//    private ZipZone zipZone;
//    private UtilsEJB utilsEJB;
//    private PersonEJB personEJB;
//    private RequestEJB requestEJB;
//    private Long requestPersonId;
//    private Map<String, String> countries = null;
//    private Map<String, String> countriesDoc = null;
//    private Map<String, String> states = null;
//    private Map<String, String> cities = null;
//    private Map<String, String> edificationTypes = null;
//    private Map<String, String> streetTypes = null;
//    private Map<String, String> documentsPersonTypes = null;
//    private Map<String, String> civilStatuses = null;
//    private Map<String, String> professions = null;
//    private Map<String, String>  kinShipApplicants= null;
//    private Map<String, String> zipZones = null;
//    
//    
//    private String messages = null;
//
//    
//    @ManagedProperty("#{creditCardService}")
//    private CreditCardService service;
//    
//    @PostConstruct
//    public void init() {
//        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
//        personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);
//        requestEJB = (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
//        solicitude = (Solicitude) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("solicitude");
//        requestPersonId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("requestPersonId");
//        cards = (List<CreditCard>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cards");
//        //cards = (List<CreditCard>) session.getAttribute("cards");
//        //solicitude = (Solicitude) session.getAttribute("solicitude");
//        //requestPersonId = (Long) session.getAttribute("requestPersonId");
//        EJBRequest request = new EJBRequest();
//        request.setParam(2);
//        try {
//            country = utilsEJB.loadCountry(request);
//            countryDocument = utilsEJB.loadCountry(request);
//        } catch (RegisterNotFoundException ex) {
//            Logger.getLogger(DataListView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
//            Logger.getLogger(DataListView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
//            Logger.getLogger(DataListView.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        if (cards!=null && !cards.isEmpty()){
//            service.createCars(cards);
//        }
////        else{
////            cards = new ArrayList<CreditCard>();
////            addCreditCard = new CreditCard(null,solicitude.getCity(), solicitude.getCivilState(), solicitude.getCountry(), solicitude.getDocumentType(), solicitude.getDocumentNumber(), 
////            solicitude.getName(), solicitude.getLastName(), solicitude.getGender(), solicitude.getPlaceBirth(), solicitude.getBirthdate(), solicitude.getMaritalStatus(),
////            solicitude.getProfession(), solicitude.getEmail(), solicitude.getPhoneNumber(), solicitude.getState(), solicitude.getAddress(), solicitude.getPostalCode());
////            cards.add(addCreditCard);
////        }
//        addCreditCard = new CreditCard();
//        addCreditCard.genders = new ArrayList<String>();
//        addCreditCard.genders.add("Masculino");
//        addCreditCard.genders.add("Feminino");
//    }
//
//    public List<CreditCard> getCards() {
//        return cards;
//    }
//
//    public void setCards(List<CreditCard> cards) {
//        this.cards = cards;
//    }
//    
//
//    public void setService(CreditCardService service) {
//        this.service = service;
//    }
//
//  public Country getCountry() {
//        return country;
//    }
//
//    public void setCountry(Country country) {
//        this.country = country;
//    }
//
//    public Country getCountryDocument() {
//        return countryDocument;
//    }
//
//    public void setCountryDocument(Country countryDocument) {
//        this.countryDocument = countryDocument;
//    }
//
//    public State getState() {
//        return state;
//    }
//
//    public void setState(State state) {
//        this.state = state;
//    }
//
//    public City getCity() {
//        return city;
//    }
//
//    public void setCity(City city) {
//        this.city = city;
//    }
//
//    public CreditCard getAddCreditCard() {
//        return addCreditCard;
//    }
//
//    public void setAddCreditCard(CreditCard addCreditCard) {
//        this.addCreditCard = addCreditCard;
//    }
//   
//     public EdificationType getEdificationType() {
//        return edificationType;
//    }
//
//    public void setEdificationType(EdificationType edificationType) {
//        this.edificationType = edificationType;
//    }
//
//    public StreetType getStreetType() {
//        return streetType;
//    }
//
//    public void setStreetType(StreetType streetType) {
//        this.streetType = streetType;
//    }
//
//    public CivilStatus getCivilStatus() {
//        return civilStatus;
//    }
//
//    public void setCivilStatus(CivilStatus civilStatus) {
//        this.civilStatus = civilStatus;
//    }
//
//    public DocumentsPersonType getDocumentsPersonType() {
//        return documentsPersonType;
//    }
//
//    public void setDocumentsPersonType(DocumentsPersonType documentsPersonType) {
//        this.documentsPersonType = documentsPersonType;
//    }
//
//    public Profession getProfession() {
//        return profession;
//    }
//
//    public void setProfession(Profession profession) {
//        this.profession = profession;
//    }
//
//    public KinShipApplicant getKinShipApplicant() {
//        return kinShipApplicant;
//    }
//
//    public void setKinShipApplicant(KinShipApplicant kinShipApplicant) {
//        this.kinShipApplicant = kinShipApplicant;
//    }
//
// 
//    
//    
//
//    
//     public ZipZone getZipZone() {
//        return zipZone;
//    }
//
//    public void setZipZone(ZipZone zipZone) {
//        this.zipZone = zipZone;
//    }
//    
//        
//    
//   public Map<String, String> getStates() {
//        EJBRequest request = new EJBRequest();
//        Map params = new HashMap();
//        params.put(QueryConstants.PARAM_COUNTRY_ID, country != null ? country.getId() : null);
//        request.setParams(params);
//        states = new TreeMap<String, String>();
//        try {
//            List<State> states1 = utilsEJB.getStatesByCountry(request);
//            for (State state : states1) {
//                states.put(state.getName(), state.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
//            ex.printStackTrace();
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//             ex.printStackTrace();
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return states;
//    }
//    
//    public void setStates(Map<String, String> states) {
//        this.states = states;
//    }
//    
//    public Map<String, String> getCountries() {
//        EJBRequest request = new EJBRequest();
//        countries = new TreeMap<String, String>();
//        try {
//            List<Country> countries1 = utilsEJB.getCountries(request);
//            for (Country country : countries1) {
//                countries.put(country.getName(), country.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }        
//        return countries;
//    }
//    
//    public void setCountries(Map<String, String> countries) {
//        this.countries = countries;
//    }
//    
//        public Map<String, String> getCountriesDoc() {
//        EJBRequest request = new EJBRequest();
//        countriesDoc = new TreeMap<String, String>();
//        try {
//            List<Country> countries1 = utilsEJB.getCountries(request);
//            for (Country country : countries1) {
//                countriesDoc.put(country.getName(), country.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////            Logger.getLogger(ListStoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }        
//        return countriesDoc;
//    }
//
//    public void setCountriesDoc(Map<String, String> countriesDoc) {
//        this.countriesDoc = countriesDoc;
//    }
//    
//    public Map<String, String> getCities() {
//        EJBRequest request = new EJBRequest();
//        Map params = new HashMap();
//        params.put(QueryConstants.PARAM_STATE_ID, state != null ? state.getId() : null);
//        request.setParams(params);
//        cities = new TreeMap<String, String>();
//      
//            List<City> cities1;
//        try {
//            cities1 = utilsEJB.getCitiesByState(request);
//            for (City city : cities1) {
//                cities.put(city.getName(), city.getId().toString());
//            }
//        } catch (EmptyListException ex) {
//            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
//            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
//            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
//        }
// 
//        return cities;
//    }
//    
//    public void setCities(Map<String, String> cities) {
//        this.cities = cities;
//    }
//    
//    public void reloadStates(AjaxBehaviorEvent event) {
//        Country country1 = (Country) ((UIOutput) event.getSource()).getValue();
//        EJBRequest request = new EJBRequest();
//        Map params = new HashMap();
//        params.put(QueryConstants.PARAM_COUNTRY_ID, country1.getId());
//        request.setParams(params);
//        states = new TreeMap<String, String>();
//        try {
//            List<State> states1 = utilsEJB.getStatesByCountry(request);
//            for (State state1 : states1) {
//                states.put(state1.getName(), state1.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public void reloadCities(AjaxBehaviorEvent event) {
//        State state = (State) ((UIOutput) event.getSource()).getValue();
//        EJBRequest request = new EJBRequest();
//        Map params = new HashMap();
//        params.put(QueryConstants.PARAM_STATE_ID, state.getId());
//        request.setParams(params);
//        cities = new TreeMap<String, String>();
//        try {
//            List<City> cities1 = utilsEJB.getCitiesByState(request);
//            for (City city1 : cities1) {
//                cities.put(city1.getName(), city1.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//       public void reloadZipZOne(AjaxBehaviorEvent event) {
//        City city = (City) ((UIOutput) event.getSource()).getValue();
//        EJBRequest request = new EJBRequest();
//        Map params = new HashMap();
//        params.put(QueryConstants.PARAM_CITY_ID, city != null ? city.getId() : null);
//        request.setParams(params);
//        zipZones = new TreeMap<String, String>();
//        try {
//            List<ZipZone> zipZoneByCities = utilsEJB.getZipZoneByCities(request);
//            for (ZipZone zone : zipZoneByCities) {
//                zipZones.put(zone.getName(), zone.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//     public Map<String, String> getDocumentsPersonTypes() {
//        EJBRequest request = new EJBRequest();
//        
//        documentsPersonTypes = new TreeMap<String, String>();
//        try {
//            List<DocumentsPersonType> dts = utilsEJB.getDocumentsPersonType(request);
//            for (DocumentsPersonType documentType : dts) {
//                documentsPersonTypes.put(documentType.getDescription(), documentType.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return documentsPersonTypes;
//    }
//   
//     public void setDocumentsPersonTypes(Map<String, String> documentsPersonTypes) {
//        this.documentsPersonTypes = documentsPersonTypes;
//    }
//     
//   public Map<String, String> getEdificationTypes() {
//        EJBRequest request = new EJBRequest();  
//        edificationTypes = new TreeMap<String, String>();
//        try {
//            List<EdificationType> dts = utilsEJB.getEdificationTypes(request);
//            for (EdificationType edificationType1 : dts) {
//                edificationTypes.put(edificationType1.getDescription(), edificationType1.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return edificationTypes;
//    }
//   
//    public Map<String, String> getStreetTypes() {
//        EJBRequest request = new EJBRequest();
//        streetTypes = new TreeMap<String, String>();
//        try {
//            List<StreetType> dts = utilsEJB.getStreetTypes(request);
//            for (StreetType streetType : dts) {
//                streetTypes.put(streetType.getDescription(), streetType.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return streetTypes;
//    }
//    
//    
//    public Map<String, String> getCivilStatuses() {
//        EJBRequest request = new EJBRequest();
//        civilStatuses = new TreeMap<String, String>();
//        try {
//            List<CivilStatus> dts = personEJB.getCivilStatus(request);
//            for (CivilStatus civilStatus : dts) {
//                civilStatuses.put(civilStatus.getDescription(), civilStatus.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return civilStatuses;
//    }
//    
//     public Map<String, String> getProfessions() {
//        EJBRequest request = new EJBRequest();
//        professions = new TreeMap<String, String>();
//        try {
//            List<Profession> dts = personEJB.getProfession(request);
//            for (Profession profession : dts) {
//                professions.put(profession.getName(), profession.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return professions;
//    }
//     
//     public Map<String, String> getKinShipApplicants() {
//        EJBRequest request = new EJBRequest();
//        kinShipApplicants = new TreeMap<String, String>();
//        try {
//            List<KinShipApplicant> dts = personEJB.getKinShipApplicant(request);
//            for (KinShipApplicant kinShipApplicant : dts) {
//                kinShipApplicants.put(kinShipApplicant.getDescription(), kinShipApplicant.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return kinShipApplicants;
//    }
//     
//      public Map<String, String> getZipZones() {
//        EJBRequest request = new EJBRequest();
//        Map params = new HashMap();
//        params.put(QueryConstants.PARAM_CITY_ID, city != null ? city.getId() : null);
//        request.setParams(params);
//        zipZones = new TreeMap<String, String>();
//        try {
//            List<ZipZone> zipZoneByCities = utilsEJB.getZipZoneByCities(request);
//            for (ZipZone zipZone : zipZoneByCities) {
//                zipZones.put(zipZone.getName(), zipZone.getId().toString());
//            }
//        } catch (EmptyListException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
//            ex.printStackTrace();
////           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//             ex.printStackTrace();
////            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return zipZones;
//    }
//
//    public void setZipZones(Map<String, String> zipZones) {
//        this.zipZones = zipZones;
//    }
//    
//    public void setEdificationTypes(Map<String, String> edificationTypes) {
//        this.edificationTypes = edificationTypes;
//    }
//
//    public void setStreetTypes(Map<String, String> streetTypes) {
//        this.streetTypes = streetTypes;
//    }
//
// 
//    public void setCivilStatuses(Map<String, String> civilStatuses) {
//        this.civilStatuses = civilStatuses;
//    }
//    
//   
//    public void setProfessions(Map<String, String> professions) {
//        this.professions = professions;
//    }
//
//
//    public void setKinShipApplicants(Map<String, String> kinShipApplicants) {
//        this.kinShipApplicants = kinShipApplicants;
//    }
//    
//    public void saveCreditCard() {
//        try {
//            ApplicantNaturalPerson person= requestEJB.saveCardComplementary(countryDocument.getId(), addCreditCard.getEmail(), documentsPersonType.getId(), addCreditCard.getDocumentNumber(), new Date((new java.util.Date()).getTime()), addCreditCard.getName(), addCreditCard.getLastName(), addCreditCard.getMarriedLastName(), addCreditCard.getGender().equals("Feminino")?"F":"M",
//                    addCreditCard.getPlaceBirth(), addCreditCard.getBirthdate(), civilStatus.getId(), profession.getId(), addCreditCard.getPhoneNumber(), addCreditCard.getCellNumber(),
//                    country.getId(), state.getId(), city.getId(), zipZone.getId(), edificationType.getId(), addCreditCard.getEdification(), addCreditCard.getTorre(), addCreditCard.getPiso(), streetType.getId(), addCreditCard.getCalle(), addCreditCard.getUrbanizacion(), requestPersonId, kinShipApplicant.getId());
//            addCreditCard = new CreditCard(person,city.getName(), civilStatus.getDescription(), country.getName(), documentsPersonType.getDescription(), addCreditCard.getDocumentNumber(),
//                    addCreditCard.getName(), addCreditCard.getLastName(), addCreditCard.getGender(), addCreditCard.getPlaceBirth(), addCreditCard.getBirthdate(), civilStatus.getDescription(),
//                    profession.getName(), addCreditCard.getEmail(), addCreditCard.getPhoneNumber(), state.getName(), zipZone.getName(), zipZone.getName());
//
//            cards.add(addCreditCard);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cards", cards);
//            //session.setAttribute("cards", cards);
//            messages = "Se agrego la solicitud de tarjeta complementaria para " + addCreditCard.name + " " + addCreditCard.lastName + " ha sido guardado con exito";
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(messages));
//        } catch (EmptyListException ex) {
//            Logger.getLogger(DataListView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (RegisterNotFoundException ex) {
//            Logger.getLogger(DataListView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
//            Logger.getLogger(DataListView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
//            Logger.getLogger(DataListView.class.getName()).log(Level.SEVERE, null, ex);
//        }
//       
//    }
//    
//    public void save() {
//          messages = "Se genero la solicitud de" + solicitude.name + " " + solicitude.lastName + " de manera satisfactoria";
//          FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(messages));
//    }
//        
//    public void resetCreditCard() {
//        RequestContext.getCurrentInstance().reset("CityCreateForm:grid");
//    }
//    
//    public void doRediretCreditCard() {
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("cards.xhtml");
//        } catch (IOException ex) {
//            System.out.println("com.alodiga.primefaces.ultima.controller.CityController.doRediret()");
//        }
//    }
//    
//     public void reset() {
//        RequestContext.getCurrentInstance().reset("form:cardTable");
//    }
//    
//    public void doRediret() {
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("formCardData.xhtml");
//        } catch (IOException ex) {
//            System.out.println("com.alodiga.primefaces.ultima.controller.CityController.doRediret()");
//        }
//    }
//    
//      public void doAddCard() {
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("creditCardView.xhtml");
//        } catch (IOException ex) {
//            System.out.println("com.alodiga.primefaces.ultima.controller.DataListView.doRediret()");
//        }
//    }
//    
//      public void handleReturnDialog(SelectEvent event) {
//        if (event != null && event.getObject() != null) {
//        }
//    }
//    
//}

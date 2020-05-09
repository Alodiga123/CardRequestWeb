package com.alodiga.primafaces.controllers;

import com.alodiga.cms.commons.ejb.PersonEJB;
import com.alodiga.cms.commons.ejb.RequestEJB;
import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import static com.alodiga.primafaces.controllers.FormCardDataController.getEdad;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.ApplicantNaturalPerson;
import com.cms.commons.models.City;
import com.cms.commons.models.CivilStatus;
import com.cms.commons.models.Country;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.KinShipApplicant;
import com.cms.commons.models.Language;
import com.cms.commons.models.State;
import com.cms.commons.models.ZipZone;
import com.cms.commons.util.EJBServiceLocator;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.ultima.domain.CreditCard;
import org.primefaces.ultima.domain.Solicitude;
import org.primefaces.ultima.service.CreditCardService;
import org.primefaces.ultima.view.input.RadioView;


@ManagedBean(name="addComplementaryCard")
@ViewScoped
public class AddComplementaryCard implements Serializable {
    
         
    private CreditCard addCreditCard;
    public Solicitude solicitude = new Solicitude();
    private Country country;
    private State state;
    private City city;
    private DocumentsPersonType documentsPersonType;
    private CivilStatus civilStatus;
    private KinShipApplicant kinShipApplicant;
    private UtilsEJB utilsEJB;
    private PersonEJB personEJB;
    private RequestEJB requestEJB;
    private ApplicantNaturalPerson applicantNaturalPerson;
    private Map<String, String> countries = null;
    private Map<String, String> countriesDoc = null;
    private Map<String, String> states = null;
    private Map<String, String> cities = null;
    private Map<String, String> documentsPersonTypes = null;
    private Map<String, String> civilStatuses = null;
    private Map<String, String>  kinShipApplicants= null;
    private Map<String, String> zipZones = null;
    ResourceBundle bundle = null;
    Long personTypeId = null;
    private String messages = null;
    private Long countCardComplementaryByApplicant = 0L;
    private Language language =null;
    
    @ManagedProperty("#{creditCardService}")
    private CreditCardService service;
    
    @PostConstruct
    public void init() {
        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
        personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);
        requestEJB = (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
        applicantNaturalPerson = (ApplicantNaturalPerson) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("applicantNaturalPerson");
        country = (Country) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country");
        language = (Language)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");
        if (country != null && applicantNaturalPerson!=null){
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
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        bundle = ResourceBundle.getBundle("com.alodiga.primafeces.messages/message", locale);
        addCreditCard = new CreditCard();
        addCreditCard.genders = new ArrayList<String>();
        addCreditCard.genders.add(bundle.getString("common.female"));
        addCreditCard.genders.add(bundle.getString("common.male"));
        addCreditCard.recommendations = new ArrayList<String>();
        addCreditCard.recommendations.add(bundle.getString("option.yes"));
        addCreditCard.recommendations.add(bundle.getString("option.no"));
        addCreditCard.promotions = new ArrayList<String>();
        addCreditCard.promotions.add(bundle.getString("option.yes"));
        addCreditCard. promotions.add(bundle.getString("option.no"));
        addCreditCard.citizens = new ArrayList<String>();
        addCreditCard.citizens.add(bundle.getString("option.yes"));
        addCreditCard.citizens.add(bundle.getString("option.no"));
        addCreditCard.genders = new ArrayList<String>();
        addCreditCard.genders.add(bundle.getString("common.female"));
        addCreditCard.genders.add(bundle.getString("common.male"));

    }
  
    public void setService(CreditCardService service) {
        this.service = service;
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

    public CreditCard getAddCreditCard() {
        return addCreditCard;
    }

    public void setAddCreditCard(CreditCard addCreditCard) {
        this.addCreditCard = addCreditCard;
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


    public KinShipApplicant getKinShipApplicant() {
        return kinShipApplicant;
    }

    public void setKinShipApplicant(KinShipApplicant kinShipApplicant) {
        this.kinShipApplicant = kinShipApplicant;
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
    
     public void reloadSDocumentPersonType(AjaxBehaviorEvent event) {
        Country country1 = (Country) ((UIOutput) event.getSource()).getValue();
        if (country1!=null){
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
        params.put(QueryConstants.PARAM_LANGUAGE_ID, language != null ? language.getId() : null);
        request.setParams(params);
        civilStatuses = new TreeMap<String, String>();
        try {
            List<CivilStatus> dts = personEJB.getCivilStatusByLanguage(request);
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
    
    
    
     public Map<String, String> getKinShipApplicants() {
        EJBRequest request = new EJBRequest();
        Map params = new HashMap();
        params.put(QueryConstants.PARAM_LANGUAGE_ID, language != null ? language.getId() : null);
        request.setParams(params);
        kinShipApplicants = new TreeMap<String, String>();
        try {
            List<KinShipApplicant> dts = personEJB.getKinShipApplicantByLanguage(request);
            for (KinShipApplicant kinShipApplicant : dts) {
                kinShipApplicants.put(kinShipApplicant.getDescription(), kinShipApplicant.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kinShipApplicants;
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
    
 
    public void setCivilStatuses(Map<String, String> civilStatuses) {
        this.civilStatuses = civilStatuses;
    }
    
   
    public void setKinShipApplicants(Map<String, String> kinShipApplicants) {
        this.kinShipApplicants = kinShipApplicants;
    }
    
    public void saveCreditCard() {
        try {
            if (validations()) {
             
                ZipZone postalZone = new ZipZone();
                postalZone.setName(addCreditCard.getStreet());
                postalZone.setCode(addCreditCard.getZipZone());
                postalZone = utilsEJB.saveZipZone(postalZone);
                ApplicantNaturalPerson person= requestEJB.saveCardComplementary(country.getId(), addCreditCard.getEmail(), documentsPersonType.getId(), addCreditCard.getDocumentNumber(), new Date((new java.util.Date()).getTime()), addCreditCard.getName(), addCreditCard.getLastName(), addCreditCard.getGender().equals("Feminino")?"F":"M",
                        addCreditCard.getBirthdate(), civilStatus.getId(), addCreditCard.getPhoneNumber(),
                        country.getId(), state.getId(), city.getId(), postalZone.getId(),  addCreditCard.getStreet(),addCreditCard.getStreet2(),  applicantNaturalPerson.getId(), kinShipApplicant.getId());
                if (person != null) {
                    try {
                        countCardComplementaryByApplicant = personEJB.countCardComplementaryByApplicant(applicantNaturalPerson.getId());
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
                        if (countCardComplementaryByApplicant < 2L) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("completeComplementaryCard.xhtml");
                        }else{
                            FacesContext.getCurrentInstance().getExternalContext().redirect("applicationCompleted.xhtml");
                        }
                    } catch (Exception ex) {
                        messages = bundle.getString("Ha ocurrido un error");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
                    }
                    
                } else {
                    messages = bundle.getString("common.error.save.form");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AddComplementaryCard.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
     public boolean validations() {
        boolean valid = true;
        if (documentsPersonType == null) {
            messages = bundle.getString("common.error.document.type");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (addCreditCard.documentNumber == null) {
            messages = bundle.getString("common.error.document.number");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (addCreditCard.name == null) {
            messages = bundle.getString("common.error.name");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (addCreditCard.lastName == null) {
            messages = bundle.getString("common.error.last.name");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (addCreditCard.gender == null) {
            messages = bundle.getString("common.error.gender");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (addCreditCard.birthdate == null) {
            messages = bundle.getString("common.error.birthday");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (civilStatus == null) {
            messages = bundle.getString("common.error.marital.status");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (addCreditCard.email == null) {
            messages = bundle.getString("common.error.email");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (addCreditCard.password1 == null) {
            messages = bundle.getString("common.error.require.password");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (addCreditCard.password2 == null) {
            messages = bundle.getString("common.error.confir.password");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (!addCreditCard.password2.equals(addCreditCard.password2)) {
            messages = bundle.getString("common.password.not.match");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (addCreditCard.pin == null) {
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
        } else if (addCreditCard.street == null) {
            messages = bundle.getString("common.error.street");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        }  else if (addCreditCard.zipZone == null) {
            messages = bundle.getString("common.error.postal.zone");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        } else if (getEdad(addCreditCard.birthdate) < 18) {
            messages = bundle.getString("common.adult");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
            valid = false;
        }
        try {
             if (requestEJB.existsApplicantNaturalPersonByEmail(addCreditCard.email)) {
                 messages = bundle.getString("common.error.exists.email");
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
                valid = false;
             }
         } catch (Exception ex) {
             messages = bundle.getString("common.error.general.exists.email");
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
             valid = false;
         }
         try {
             if (requestEJB.existsApplicantNaturalPersonByPhoneNumber(addCreditCard.phoneNumber)) {
                 messages = bundle.getString("common.error.exists.phone.number");
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
                valid = false;
             }
         } catch (Exception ex) {
             messages = bundle.getString("common.error.general.exists.phone_number");
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
             valid = false;
         }

        return valid;
    }
    
        
    public void resetCreditCard() {
        RequestContext.getCurrentInstance().reset("CityCreateForm:grid");
    }
    
       
      public void handleReturnDialog(SelectEvent event) {
        if (event != null && event.getObject() != null) {
        }
    }
    
}

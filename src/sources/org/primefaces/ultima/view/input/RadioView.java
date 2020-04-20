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
import com.cms.commons.models.ApplicantNaturalPerson;
import com.cms.commons.util.QueryConstants;
import java.io.IOException;
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
    private Country country;
    private State state;
    private City city;
    private Map<String, String> countries = null;
    private Map<String, String> countriesDoc = null;
    private Map<String, String> states = null;
    private Map<String, String> cities = null;
    ResourceBundle bundle = null;
    String cellNumber =null;
        
    @PostConstruct
    public void init() {
//        try {
            utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
            personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);
            requestEJB= (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
            solicitude = new Solicitude();
//            solicitude.genders = new ArrayList<String>();
            solicitude.recommendations = new ArrayList<String>();
            solicitude.promotions = new ArrayList<String>();
            solicitude.citizens = new ArrayList<String>();
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            bundle = ResourceBundle.getBundle("com.alodiga.primafeces.messages/message", locale);
//            solicitude.genders.add(bundle.getString("option.yes"));
//            solicitude.genders.add(bundle.getString("option.no"));
            solicitude.recommendations.add(bundle.getString("option.yes"));
            solicitude.recommendations.add(bundle.getString("option.no"));
            solicitude.promotions.add(bundle.getString("option.yes"));
            solicitude.promotions.add(bundle.getString("option.no"));
            solicitude.citizens.add(bundle.getString("option.yes"));
            solicitude.citizens.add(bundle.getString("option.no"));
            
//            EJBRequest request = new EJBRequest();
//            request.setParam(2);
//            country = utilsEJB.loadCountry(request);
//              countryDocument = utilsEJB.loadCountry(request);
            country = (Country) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country");
//            countryDocument =  (Country) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country");
            cellNumber = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cellNumber");
//        } catch (RegisterNotFoundException ex) {
//            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullParameterException ex) {
//            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GeneralException ex) {
//            ex.printStackTrace();
//            Logger.getLogger(RadioView.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
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
//         try {
//            ApplicantNaturalPerson requestPersonId = requestEJB. saveRequestPersonData(country.getId(),  solicitude.getEmail(), new Date((new java.util.Date()).getTime()) , solicitude.getName(), solicitude.getLastName(),solicitude.getBirthdate(),  
//                                         solicitude.getCellNumber(), country.getId(), state.getId(), city.getId(), solicitude.getPostalCode(), solicitude.getAddress(),solicitude.getRecommendation().equals(bundle.getString("option.yes"))?true:false,
//                                         solicitude.getPromotion().equals(bundle.getString("option.yes"))?true:false,solicitude.getCitizen().equals(bundle.getString("option.yes"))?true:false, solicitude.getPassword1(), title.getId()); 
//
//            solicitude.setCity(city.getName());
//            solicitude.setCountry(country.getName());
//            solicitude.setState(state.getName());
//            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("solicitude", solicitude);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("requestPersonId", requestPersonId);
//            
//            FacesContext.getCurrentInstance().getExternalContext().redirect("cards.xhtml");
//        } catch (RegisterNotFoundException ex) {
//            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
//        } catch (EmptyListException ex) {
//            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
//        } catch (NullParameterException ex) {
//            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
//        } catch (GeneralException ex) {
//             System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
//        } catch (IOException ex) {
//            System.out.println("com.alodiga.primefaces.ultima.controller.StoreController.doRediret()");
//        } 
    }

    public void reset() {
        RequestContext.getCurrentInstance().reset("CardDataCreateForm:grid");
    }
    
   
}

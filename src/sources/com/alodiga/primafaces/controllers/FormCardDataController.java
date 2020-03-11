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
import com.cms.commons.models.Title;
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
public class FormCardDataController {
//    public Solicitude solicitude;
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
    private Map<String, String> titles = null;
    private Title title;
    String cellNumber =null;
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
    public String address;
    public String postalCode;
    public String password1;
    public String password2;
    private String messages = null;
        
    @PostConstruct
    public void init() {
            utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
            personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);
            requestEJB= (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
//            solicitude = new Solicitude();
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

            country = (Country) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country");
            cellNumber = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cellNumber");

       
    }

//   public Solicitude getSolicitude() {
//        return solicitude;
//    }
//
//    public void setSolicitude(Solicitude solicitude) {
//        this.solicitude = solicitude;
//    }

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

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
   
    
   
     public Map<String, String> getTitles() {
        EJBRequest request = new EJBRequest();
        titles = new TreeMap<String, String>();
        try {
            List<Title> dts = personEJB.getTitles(request);
            for (Title title : dts) {
                titles.put(title.getDescription(), title.getId().toString());
            }
        } catch (EmptyListException ex) {
//           Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
//            Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
//          Logger.getLogger(com.alodiga.primefaces.ultima.controller.store.StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return titles;
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
         try {
            Long requestPersonId = requestEJB. saveRequestPersonData(country.getId(),  email, new Date((new java.util.Date()).getTime()) , name, lastName,birthdate,  
                                        cellNumber, country.getId(), state.getId(), city.getId(), postalCode, address,recommendation.equals(bundle.getString("option.yes"))?true:false,
                                         promotion.equals(bundle.getString("option.yes"))?true:false,citizen.equals(bundle.getString("option.yes"))?true:false, password1, title.getId()); 

             if (requestPersonId != 0) {
                 FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("solicitude", solicitude);
                 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("requestPersonId", requestPersonId);

                 FacesContext.getCurrentInstance().getExternalContext().redirect("takePhoto.xhtml");
             }else{
                messages = "Ha ocurrido un error al guardar la solicitud";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
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

    public void reset() {
        RequestContext.getCurrentInstance().reset("CardDataCreateForm:grid");
    }
    
   
}

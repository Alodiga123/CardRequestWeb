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

import com.alodiga.cms.commons.ejb.ProductEJB;
import com.alodiga.cms.commons.ejb.ProgramEJB;
import com.alodiga.cms.commons.ejb.RequestEJB;
import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.cms.commons.models.Country;
import com.cms.commons.util.EJBServiceLocator;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.ApplicantNaturalPerson;
import com.cms.commons.models.CollectionType;
import com.cms.commons.models.CollectionsRequest;
import com.cms.commons.models.Language;
import com.cms.commons.models.PersonType;
import com.cms.commons.models.ProductType;
import com.cms.commons.models.Program;
import com.cms.commons.models.RequestHasCollectionsRequest;
import com.cms.commons.util.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.ultima.domain.Solicitude;

@ManagedBean
@ViewScoped
public class TakePhotoController {
    public Solicitude solicitude;
    private UtilsEJB utilsEJB;
    private RequestEJB requestEJB;
    private ProductEJB productEJB;
    private ProgramEJB programEJB;
    private String cellNumber;
    private Country country;
    private Map<String, String> countries = null;
    private UploadedFile file;
    private ApplicantNaturalPerson applicantNaturalPerson;
    private String language =null;
  
    @PostConstruct
    public void init() {
            utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
            requestEJB = (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
            productEJB  = (ProductEJB) EJBServiceLocator.getInstance().get(EjbConstants.PRODUCT_EJB);
            programEJB = (ProgramEJB) EJBServiceLocator.getInstance().get(EjbConstants.PROGRAM_EJB);
            country = (Country) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country");
            applicantNaturalPerson = (ApplicantNaturalPerson) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("applicantNaturalPerson");
            language = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language");
       
    }



    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
            String name = fmt.format(new Date()) + event.getFile().getFileName().substring( event.getFile().getFileName().lastIndexOf('.'));
            String id =applicantNaturalPerson!=null?applicantNaturalPerson.getId().toString():"0";
            File file = new File("C:\\Users\\yamea\\OneDrive\\Documentos\\NetBeansProjects\\upload\\document-" + id + "-"+name);
//            File file = new File("/opt/proyecto/cms/imagenes/document-" + id + "-"+name);

            InputStream is = event.getFile().getInputstream();
            OutputStream out = new FileOutputStream(file);
            byte buf[] = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            is.close();
            out.close();
            short indApproved = 1;
            EJBRequest request1 = null;
            Map params = null;
            PersonType personTypeApp = null;
            CollectionsRequest collectionsRequest = null;
            try {
                List<CollectionsRequest> collectionsRequests = null;

                request1 = new EJBRequest();
                params = new HashMap();
                params.put(Constants.COUNTRY_KEY, country.getId());
                params.put(Constants.ORIGIN_APPLICATION_KEY, Constants.ORIGIN_APPLICATION_WALLET_ID);
                request1.setParams(params);
                List<PersonType> personTypes = utilsEJB.getPersonTypeByCountry(request1);
                for (PersonType p : personTypes) {
                    if (p.getOriginApplicationId().getId() == Constants.ORIGIN_APPLICATION_WALLET_ID) {
                        personTypeApp = p;
                    }
                }

                params = new HashMap();
                request1 = new EJBRequest();
                params.put(Constants.COUNTRY_KEY, country.getId());
                params.put(Constants.PRODUCT_TYPE_KEY, Constants.PRODUCT_TYPE_WALLET_APP_ID);
                params.put(Constants.PROGRAM_KEY, Constants.PROGRAM_WALLET_APP_ID);
                params.put(Constants.PERSON_TYPE_KEY, personTypeApp.getId());
                request1.setParams(params);
                try {
                    collectionsRequests = requestEJB.getCollectionsByRequest(request1);
                    for (CollectionsRequest collectionsRequest1 : collectionsRequests) {
                        if (collectionsRequest1.getCollectionTypeId().getId() == Constants.APP_IDENTIFICATION_DOCUMENT) {
                            request1 = new EJBRequest();
                            request1.setParam(collectionsRequest1.getId());
                            collectionsRequest = requestEJB.loadCollectionsRequest(request1);
                            System.out.println("encontro"+ collectionsRequest.getId());
                        }
                    }
                } catch (EmptyListException e) {
                    request1 = new EJBRequest();
                    request1.setParam(Constants.PROGRAM_WALLET_APP_ID);
                    Program program = programEJB.loadProgram(request1);
                    request1 = new EJBRequest();
                    request1.setParam(Constants.PRODUCT_TYPE_WALLET_APP_ID);
                    ProductType productType = productEJB.loadProductType(request1);
                    request1 = new EJBRequest();
                    request1.setParam(Constants.APP_IDENTIFICATION_DOCUMENT);
                    CollectionType collestionType = requestEJB.loadCollectionType(request1);
                    collectionsRequest = new CollectionsRequest();
                    collectionsRequest.setCountryId(country);
                    collectionsRequest.setProductTypeId(productType);
                    collectionsRequest.setProgramId(program);
                    collectionsRequest.setPersonTypeId(personTypeApp);
                    collectionsRequest.setCollectionTypeId(collestionType);
                    collectionsRequest = requestEJB.saveCollectionRequest(collectionsRequest);

                }

                RequestHasCollectionsRequest requestHasCollectionsRequest = new RequestHasCollectionsRequest();

                requestHasCollectionsRequest.setCollectionsRequestid(collectionsRequest);
                requestHasCollectionsRequest.setRequestId(applicantNaturalPerson.getRequest());
                requestHasCollectionsRequest.setIndApproved(indApproved);
                requestHasCollectionsRequest.setObservations("Imagen del documento de ApplicantNaturalPersonId: " + applicantNaturalPerson.getId());
                requestHasCollectionsRequest.setUrlImageFile(file.getAbsolutePath());
                requestHasCollectionsRequest.setCreateDate(new Timestamp(new Date().getTime()));
                requestHasCollectionsRequest = requestEJB.saveRequestHasCollectionsRequest(requestHasCollectionsRequest);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
                FacesContext.getCurrentInstance().getExternalContext().redirect("sendPhoto.xhtml");
            } catch (NullParameterException ex) {
                Logger.getLogger(SendPhotoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GeneralException ex) {
               System.out.println("Error no al guardar la imagen" + ex);
            }
        } catch (Exception ex) {
            System.out.println("Erro no upload de imagem" + ex);
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
  
    public void reset() {
        RequestContext.getCurrentInstance().reset("sendCodeForm:grid");
    }
    
       
      
}

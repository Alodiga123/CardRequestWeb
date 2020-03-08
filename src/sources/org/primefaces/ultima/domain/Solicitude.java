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
package org.primefaces.ultima.domain;

import com.cms.commons.models.CivilStatus;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.EdificationType;
import com.cms.commons.models.StreetType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Solicitude implements Serializable {
    
    public String id;
    
    public String city;
    public String civilState;
    public List<String> genders;  
    public List<String> civilStates; 
    public String documentType;
    public String documentNumber;
    public String name;
    public String lastName;
    public String marriedLastName;
    public String gender; 
    public String recommendation; 
    public String promotion; 
    public String citizen; 
    public List<String> recommendations;  
    public List<String> promotions;  
    public List<String> citizens;  
    public String placeBirth;
    public Date birthdate;
    public String maritalStatus;
    public String profession;
    public String email;
    public String phoneNumber;
    public String cellNumber;
    public String country;
    public String state;
    public String address;
    public String refName1;
    public String refLastName1;
    public String refPhoneNumber1;
    public String refPMobile1;
    public String refCiudad1;
    public String refName2;
    public String refLastName2;
    public String refPhoneNumber2;
    public String refPMobile2;
    public String refCiudad2;
    public String postalCode;
    public String edification;
    public String torre;
    public String Urbanizacion;
    public int piso;
    public String calle;
    private String familiar;
    public String password1;
    public String password2;
    private DocumentsPersonType documentsPersonType;
    private EdificationType edificationType;
    private StreetType streetType;
    private CivilStatus civilStatus;

    public Solicitude() {}

    public Solicitude(String city, List<String> genders, List<String> civilStates, String country, String documentType, String documentNumber, String name, String lastName, String gender, String placeBirth, Date birthdate, String maritalStatus, String profession, String email, String phoneNumber, String state, String address, String refName1, String refLastName1, String refPhoneNumber1, String refPMobile1, String refName2, String refLastName2, String refPhoneNumber2, String refPMobile2, String postalCode, String familiar) {
        this.city = city;
        this.genders = genders;
        this.civilStates = civilStates;
        this.country = country;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.placeBirth = placeBirth;
        this.birthdate = birthdate;
        this.maritalStatus = maritalStatus;
        this.profession = profession;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.address = address;
        this.refName1 = refName1;
        this.refLastName1 = refLastName1;
        this.refPhoneNumber1 = refPhoneNumber1;
        this.refPMobile1 = refPMobile1;
        this.refName2 = refName2;
        this.refLastName2 = refLastName2;
        this.refPhoneNumber2 = refPhoneNumber2;
        this.refPMobile2 = refPMobile2;
        this.postalCode = postalCode;
        this.familiar = familiar;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCivilState() {
        return civilState;
    }

    public void setCivilState(String civilState) {
        this.civilState = civilState;
    }

    public List<String> getGenders() {
        return genders;
    }

    public void setGenders(List<String> genders) {
        this.genders = genders;
    }

    public List<String> getCivilStates() {
        return civilStates;
    }

    public void setCivilStates(List<String> civilStates) {
        this.civilStates = civilStates;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlaceBirth() {
        return placeBirth;
    }

    public void setPlaceBirth(String placeBirth) {
        this.placeBirth = placeBirth;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getRefName1() {
        return refName1;
    }

    public void setRefName1(String refName1) {
        this.refName1 = refName1;
    }

    public String getRefLastName1() {
        return refLastName1;
    }

    public void setRefLastName1(String refLastName1) {
        this.refLastName1 = refLastName1;
    }

    public String getRefPhoneNumber1() {
        return refPhoneNumber1;
    }

    public void setRefPhoneNumber1(String refPhoneNumber1) {
        this.refPhoneNumber1 = refPhoneNumber1;
    }

    public String getRefPMobile1() {
        return refPMobile1;
    }

    public void setRefPMobile1(String refPMobile1) {
        this.refPMobile1 = refPMobile1;
    }

    public String getRefName2() {
        return refName2;
    }

    public void setRefName2(String refName2) {
        this.refName2 = refName2;
    }

    public String getRefLastName2() {
        return refLastName2;
    }

    public void setRefLastName2(String refLastName2) {
        this.refLastName2 = refLastName2;
    }

    public String getRefPhoneNumber2() {
        return refPhoneNumber2;
    }

    public void setRefPhoneNumber2(String refPhoneNumber2) {
        this.refPhoneNumber2 = refPhoneNumber2;
    }

    public String getRefPMobile2() {
        return refPMobile2;
    }

    public void setRefPMobile2(String refPMobile2) {
        this.refPMobile2 = refPMobile2;
    }

    public String getMarriedLastName() {
        return marriedLastName;
    }

    public void setMarriedLastName(String marriedLastName) {
        this.marriedLastName = marriedLastName;
    }

    public DocumentsPersonType getDocumentsPersonType() {
        return documentsPersonType;
    }

    public void setDocumentsPersonType(DocumentsPersonType documentsPersonType) {
        this.documentsPersonType = documentsPersonType;
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

    public String getEdification() {
        return edification;
    }

    public void setEdification(String edification) {
        this.edification = edification;
    }

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getRefCiudad1() {
        return refCiudad1;
    }

    public void setRefCiudad1(String refCiudad1) {
        this.refCiudad1 = refCiudad1;
    }

    public String getRefCiudad2() {
        return refCiudad2;
    }

    public void setRefCiudad2(String refCiudad2) {
        this.refCiudad2 = refCiudad2;
    }

    public String getUrbanizacion() {
        return Urbanizacion;
    }

    public void setUrbanizacion(String Urbanizacion) {
        this.Urbanizacion = Urbanizacion;
    }

    public String getFamiliar() {
        return familiar;
    }

    public void setFamiliar(String familiar) {
        this.familiar = familiar;
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

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Solicitude other = (Solicitude) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}

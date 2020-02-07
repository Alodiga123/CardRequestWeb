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

import com.cms.commons.models.ApplicantNaturalPerson;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CreditCard implements Serializable {
    
    public String id;
    
    public String city;
    public String civilState;
    public List<String> genders;  
    public List<String> civilStates; 
    public String country;
    public String documentType;
    public String documentNumber;
    public String name;
    public String lastName;
    public String gender; 
    public String placeBirth;
    public Date birthdate;
    public String maritalStatus;
    public String profession;
    public String email;
    public String phoneNumber;
    public String cellNumber;
    public String state;
    public String address;
    public String postalCode;
    public String edification;
    public String torre;
    public int piso;
    public String calle;
    public String Urbanizacion;
    public String marriedLastName;
    public ApplicantNaturalPerson person;

    public CreditCard() {}

    public CreditCard(ApplicantNaturalPerson person,String city, String civilState, String country, String documentType, String documentNumber, String name, String lastName, String gender, String placeBirth, Date birthdate, String maritalStatus, String profession, String email, String phoneNumber, String state, String address, String postalCode) {
        this.person = person;
        this.city = city;
        this.civilState = civilState;
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
        this.postalCode = postalCode;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
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

  
    public String getUrbanizacion() {
        return Urbanizacion;
    }

    public void setUrbanizacion(String Urbanizacion) {
        this.Urbanizacion = Urbanizacion;
    }

    public String getMarriedLastName() {
        return marriedLastName;
    }

    public void setMarriedLastName(String marriedLastName) {
        this.marriedLastName = marriedLastName;
    }

    public ApplicantNaturalPerson getPerson() {
        return person;
    }

    public void setPerson(ApplicantNaturalPerson person) {
        this.person = person;
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
        final CreditCard other = (CreditCard) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}

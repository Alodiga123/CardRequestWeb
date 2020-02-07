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
package org.primefaces.ultima.service;


import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.ultima.domain.CreditCard;

@ManagedBean(name = "creditCardService")
@ApplicationScoped
public class CreditCardService {
    
     List<CreditCard> list = null;
    


    
    public List<CreditCard> createCars(List<CreditCard> creditCards) {
        list = new  ArrayList<CreditCard>();
        for (CreditCard creditCard: creditCards){
        list.add(new CreditCard(creditCard.getPerson(), creditCard.getCity(), creditCard.getCivilState(), creditCard.getCountry(), creditCard.getDocumentType(), creditCard.getDocumentNumber(), 
                creditCard.getName(), creditCard.getLastName(), creditCard.getGender(), creditCard.getPlaceBirth(), creditCard.getBirthdate(), creditCard.getMaritalStatus(),
                creditCard.getProfession(), creditCard.getEmail(), creditCard.getPhoneNumber(), creditCard.getState(), creditCard.getAddress(), creditCard.getPostalCode()));
        }
        return list;
    }
    
   
}

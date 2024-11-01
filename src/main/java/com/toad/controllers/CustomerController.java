
package com.toad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toad.entities.Customer;
import com.toad.repositories.CustomerRepository;
import com.toad.repositories.RentalRepository;

import org.springframework.web.bind.annotation.DeleteMapping;

@Controller
@RequestMapping(path = "/toad/customer") // This means URL's start with /film (after Application path)
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewCustomer(@PathVariable Integer id,
            @RequestParam Integer store_id,
            @RequestParam String prenom,
            @RequestParam String nom,
            @RequestParam String mail,
            @RequestParam Integer adresse_id,
            @RequestParam int active,
            @RequestParam java.sql.Timestamp create_update,
            @RequestParam java.sql.Timestamp last_update) {

        Customer n = new Customer();
        n.setNom(nom);
        n.setMail(mail);
        n.setPrenom(prenom);
        n.setActive(active);
        n.setCreateUpdate(create_update);
        n.setLastUpdate(last_update);

        customerRepository.save(n);
        return "Sauvegardé";
    }

    @PutMapping(path = "/update/{id}")
    public @ResponseBody String updateRepository(
            @PathVariable Integer id,
            @RequestParam Integer store_id,
            @RequestParam String prenom,
            @RequestParam String nom,
            @RequestParam String mail,
            @RequestParam Integer adresse_id,
            @RequestParam int active,
            @RequestParam java.sql.Timestamp create_update,
            @RequestParam java.sql.Timestamp last_update) {

        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return "Customer not found";
        }

        customer.setId(id);
        customer.setStore_Id(store_id);

        customer.setPrenom(prenom);
        customer.setNom(nom);
        customer.setMail(mail);
        customer.setAdresseId(adresse_id);
        customer.setActive(active);
        customer.setCreateUpdate(create_update);
        customer.setLastUpdate(last_update);

        customerRepository.save(customer);
        return "Customer Updated";
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteCustomer(@PathVariable Integer id) {
        String resultString = "";
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            resultString = "this customer doesn't exist";
        } else {
            try {
                customerRepository.delete(customer);
            } catch (Exception e) {
            }
            {
                resultString = "cannot be deleted because of foreign key";
            }
        }
        return resultString;

    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @GetMapping(path = "/getById")
    public @ResponseBody Customer getCustomerById(@RequestParam Integer id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            Customer filteredCustomer = new Customer();
            filteredCustomer.setId(customer.getId());
            filteredCustomer.setStore_Id(customer.getStore_Id());
            filteredCustomer.setPrenom(customer.getPrenom());
            filteredCustomer.setNom(customer.getNom());
            filteredCustomer.setMail(customer.getMail());
            filteredCustomer.setAdresseId(customer.getAdresseId());
            filteredCustomer.setActive(customer.getActive());
            filteredCustomer.setCreateUpdate(customer.getCreateUpdate());
            filteredCustomer.setLastUpdate(customer.getLastUpdate());
            return filteredCustomer;
        }
        return null;
    }
}

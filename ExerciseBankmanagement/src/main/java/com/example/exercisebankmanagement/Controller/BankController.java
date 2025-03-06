package com.example.exercisebankmanagement.Controller;

  // Corrected the import statement
import com.example.exercisebankmanagement.Model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class BankController {
    private final List<Customer> customers = new ArrayList<>();  // List to store customers
    private int idCounter = 1;  // Changed from Long to int, starting ID counter at 1

    // get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customers;
    }

    // Endpoint to add a new customer
    @PostMapping("/add")
    public Customer addCustomer(@RequestBody Customer customer) {
        customer.setId(idCounter++);  // Assigns an ID to the new customer and increments the counter
        customers.add(customer);  // Adds the new customer to the list
        return customer;
    }

    // update an existing customer
    @PutMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
        // Find customer by ID
        Optional<Customer> existingCustomer = customers.stream()
                .filter(c -> c.getId() == id)  // Compare with the id
                .findFirst();
        if (existingCustomer.isPresent()) {  // If customer exists
            Customer customer = existingCustomer.get();
            customer.setUsername(updatedCustomer.getUsername());  // Update username
            customer.setBalance(updatedCustomer.getBalance());  // Update balance
            return customer;  // Return updated customer
        }
        return null;  // If customer not found, return null
    }

    // Endpoint to delete a customer
    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customers.removeIf(customer -> customer.getId() == id);  // Removes customer based on ID
        return "Customer with ID " + id + " deleted.";  // Returns a confirmation message
    }

    // Endpoint to deposit money into a customer's account
    @PatchMapping("/deposit/{id}")
    public Customer depositMoney(@PathVariable int id, @RequestParam double amount) {
        // Find customer by ID
        Optional<Customer> customer = customers.stream()
                .filter(c -> c.getId() == id)  // Compare with the id
                .findFirst();
        if (customer.isPresent()) {  // If customer exists
            customer.get().setBalance(customer.get().getBalance() + amount);  // Add deposit amount to balance
            return customer.get();  // Return the updated customer
        }
        return null;  // If customer not found, return null
    }

    // to withdraw money from a customer's account
    @PatchMapping("/withdraw/{id}")
    public Customer withdrawMoney(@PathVariable int id, @RequestParam double amount) {
        // Find customer by ID
        Optional<Customer> customer = customers.stream()
                .filter(c -> c.getId() == id)  // Compare with the id
                .findFirst();
        if (customer.isPresent() && customer.get().getBalance() >= amount) {  // Check if balance is enough
            customer.get().setBalance(customer.get().getBalance() - amount);  // Subtract withdrawal amount
            return customer.get();  // Return the updated customer
        }
        return null;  // If customer not found
    }
}
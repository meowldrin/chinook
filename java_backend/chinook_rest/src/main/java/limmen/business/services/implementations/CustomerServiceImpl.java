package limmen.business.services.implementations;

import limmen.business.services.CustomerService;
import limmen.business.services.exceptions.SortException;
import limmen.business.services.filters.CustomerFilter;
import limmen.integration.entities.Customer;
import limmen.integration.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the CustomerService interface, uses a repository for database interaction.
 *
 * @author Kim Hammar on 2016-03-22.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CustomerRepository customerRepository;

    @Inject
    public CustomerServiceImpl(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public List<Customer> getAllCustomers(CustomerFilter customerFilter) throws SortException {
        List<Customer> customers = getAllCustomers();
        customers = customerFilter.filter(customers);
        try {
            return customerFilter.sort(customers);
        } catch (Exception e) {
            throw new SortException("Invalid query string for sorting: " + customerFilter.getSort());
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public Customer getCustomer(int customerId) {
        return customerRepository.getCustomer(customerId);
    }

    @Override
    public Customer createNewCustomer(Customer customer) {
        customer.setCustomerId(customerRepository.getMaxId() + 1);
        return customerRepository.createNewCustomer(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.updateCustomer(customer);
    }

    @Override
    public List<Customer> updateCustomers(List<Customer> customers) {
        customerRepository.deleteCustomers();
        customers.forEach((customer) -> {
            createNewCustomer(customer);
        });
        return getAllCustomers();
    }

    @Override
    public Customer deleteCustomer(int customerId) {
        Customer customer = getCustomer(customerId);
        customerRepository.deleteCustomer(customerId);
        return customer;
    }

}

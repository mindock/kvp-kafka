package com.kvp.streams.transformer;

import com.kvp.domain.Customer;
import com.kvp.domain.PurchaseCustomer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

@RequiredArgsConstructor
public class CustomerTransformer implements ValueTransformer<PurchaseCustomer, Customer> {
    private KeyValueStore<String, Long> stateStore;
    private final String storeName;
    private ProcessorContext context;

    public void init(ProcessorContext context) {
        this.context = context;
        stateStore = this.context.getStateStore(storeName);
    }

    public Customer transform(PurchaseCustomer value) {
        Customer customer = Customer.of(value);
        Long accumulatedAmount = stateStore.get(customer.getName());

        if (accumulatedAmount != null) {
            System.out.println("값이 있네? " + accumulatedAmount);
            customer.addPurchaseAmount(accumulatedAmount);
        }

        stateStore.put(customer.getName(), customer.getTotalPurchaseAmount());

        return customer;
    }

    // 뭘 해야하지??
    public void close() {}
}

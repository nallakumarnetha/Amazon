package com.amazon.address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {

//	List<Address> searchByCityOrPincode(String query);

}

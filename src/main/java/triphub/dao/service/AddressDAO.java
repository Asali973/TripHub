package triphub.dao.service;

import javax.persistence.EntityManager;


import triphub.entity.util.Address;

public class AddressDAO {
	
	private EntityManager em;
	
	public AddressDAO(EntityManager em) {
		this.em = em;
		
		}
		
		public Address create(Address address) {
			em.persist(address);
			return address;
		}

}

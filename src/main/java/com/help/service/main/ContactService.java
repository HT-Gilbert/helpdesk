package com.help.service.main;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.help.entity.main.Contact;
import com.help.entity.main.ContactRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContactService {   
	
	private final ContactRepository contactRepository;

	@Transactional(readOnly = true)
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }
	/*
    @Transactional(readOnly = true)
	//List<Contact> contactList = contactRepository.findAll();
	
	public HashMap<String, Object> findAll(Integer page, Integer size) throws Exception {
		
		HashMap<String, Object> contactMap = new HashMap<String, Object>();
				
		Page<Contact> list = contactRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "showOrder")));
		
		contactMap.put("list", list.stream().map(null).collect(Collectors.toList()));
		contactMap.put("paging", list.getPageable());
		contactMap.put("totalCnt", list.getTotalElements());
		contactMap.put("totalPage", list.getTotalPages());
		
		return contactMap;
	}
	*/
}

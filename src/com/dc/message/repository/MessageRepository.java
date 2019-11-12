package com.dc.message.repository;

/**
 * 
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dc.esb.domain.MessageBean;

@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<MessageBean, String> {

}

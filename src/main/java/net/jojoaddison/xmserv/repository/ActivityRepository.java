package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Activity;



public interface ActivityRepository extends MongoRepository<Activity,String>{

}

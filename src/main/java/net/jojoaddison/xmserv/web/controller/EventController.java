package net.jojoaddison.xmserv.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.jojoaddison.xmserv.domain.Event;
import net.jojoaddison.xmserv.service.EventService;

@Controller
@RequestMapping("/events")
public class EventController {
	
	private final EventService eventService;
    private final Logger log = LoggerFactory.getLogger(EventController.class);
	
	public EventController(final EventService service){
		this.eventService = service;
	}
	
	@GetMapping("/view/{id}")
	public String getEvent(@PathVariable("id") String id, Model model){
        log.debug("REST request for Event ID: {}", id);		
		Event event = eventService.findOne(id);
		log.debug("Event {} found", event);
		model.addAttribute("event", event);
		return "event";
	}
}

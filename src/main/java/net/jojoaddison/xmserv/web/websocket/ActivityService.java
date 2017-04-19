package net.jojoaddison.xmserv.web.websocket;

import static net.jojoaddison.xmserv.config.WebsocketConfiguration.IP_ADDRESS;

import java.security.Principal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import net.jojoaddison.xmserv.domain.Activity;
import net.jojoaddison.xmserv.repository.ActivityRepository;
import net.jojoaddison.xmserv.security.SecurityUtils;
import net.jojoaddison.xmserv.web.websocket.dto.ActivityDTO;

@Controller
public class ActivityService implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final SimpMessageSendingOperations messagingTemplate;

    private final ActivityRepository activityRepository;

    public ActivityService(SimpMessageSendingOperations messagingTemplate, ActivityRepository activityRepository) {
        this.messagingTemplate = messagingTemplate;
        this.activityRepository = activityRepository;
    }

    @SubscribeMapping("/topic/activity")
    @SendTo("/topic/tracker")
    public ActivityDTO sendActivity(@Payload ActivityDTO activityDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        activityDTO.setUserLogin(SecurityUtils.getCurrentUserLogin());
        activityDTO.setUserLogin(principal.getName());
        activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
        activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        Instant instant = Instant.ofEpochMilli(Calendar.getInstance().getTimeInMillis());
        activityDTO.setTime(dateTimeFormatter.format(ZonedDateTime.ofInstant(instant, ZoneOffset.systemDefault())));
        log.debug("Sending user tracking data {}", activityDTO);
        activityRepository.save(createActivity(activityDTO));
        return activityDTO;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setSessionId(event.getSessionId());
        activityDTO.setPage("logout");
        activityRepository.save(createActivity(activityDTO));
        messagingTemplate.convertAndSend("/topic/tracker", activityDTO);
    }

    private Activity createActivity(ActivityDTO activityDTO){
    	Activity activity = new Activity();
    	activity.setIpAddress(activityDTO.getIpAddress());
    	activity.setPage(activityDTO.getPage());
    	activity.setSessionId(activityDTO.getSessionId());
    	activity.setTime(activityDTO.getTime());
    	activity.setUserLogin(activityDTO.getUserLogin());
    	return activity;
    }
}

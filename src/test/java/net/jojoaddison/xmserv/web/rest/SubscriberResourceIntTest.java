package net.jojoaddison.xmserv.web.rest;

import net.jojoaddison.xmserv.AfripointApp;

import net.jojoaddison.xmserv.domain.Subscriber;
import net.jojoaddison.xmserv.repository.SubscriberRepository;
import net.jojoaddison.xmserv.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static net.jojoaddison.xmserv.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SubscriberResource REST controller.
 *
 * @see SubscriberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AfripointApp.class)
public class SubscriberResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSubscriberMockMvc;

    private Subscriber subscriber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SubscriberResource subscriberResource = new SubscriberResource(subscriberRepository);
        this.restSubscriberMockMvc = MockMvcBuilders.standaloneSetup(subscriberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subscriber createEntity() {
        Subscriber subscriber = new Subscriber()
                .email(DEFAULT_EMAIL)
                .createdDate(DEFAULT_CREATED_DATE)
                .active(DEFAULT_ACTIVE);
        return subscriber;
    }

    @Before
    public void initTest() {
        subscriberRepository.deleteAll();
        subscriber = createEntity();
    }

    @Test
    public void createSubscriber() throws Exception {
        int databaseSizeBeforeCreate = subscriberRepository.findAll().size();

        // Create the Subscriber

        restSubscriberMockMvc.perform(post("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriber)))
            .andExpect(status().isCreated());

        // Validate the Subscriber in the database
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeCreate + 1);
        Subscriber testSubscriber = subscriberList.get(subscriberList.size() - 1);
        assertThat(testSubscriber.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSubscriber.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSubscriber.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    public void createSubscriberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriberRepository.findAll().size();

        // Create the Subscriber with an existing ID
        Subscriber existingSubscriber = new Subscriber();
        existingSubscriber.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriberMockMvc.perform(post("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSubscriber)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriberRepository.findAll().size();
        // set the field null
        subscriber.setEmail(null);

        // Create the Subscriber, which fails.

        restSubscriberMockMvc.perform(post("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriber)))
            .andExpect(status().isBadRequest());

        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSubscribers() throws Exception {
        // Initialize the database
        subscriberRepository.save(subscriber);

        // Get all the subscriberList
        restSubscriberMockMvc.perform(get("/api/subscribers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriber.getId())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    public void getSubscriber() throws Exception {
        // Initialize the database
        subscriberRepository.save(subscriber);

        // Get the subscriber
        restSubscriberMockMvc.perform(get("/api/subscribers/{id}", subscriber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subscriber.getId()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    public void getNonExistingSubscriber() throws Exception {
        // Get the subscriber
        restSubscriberMockMvc.perform(get("/api/subscribers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSubscriber() throws Exception {
        // Initialize the database
        subscriberRepository.save(subscriber);
        int databaseSizeBeforeUpdate = subscriberRepository.findAll().size();

        // Update the subscriber
        Subscriber updatedSubscriber = subscriberRepository.findOne(subscriber.getId());
        updatedSubscriber
                .email(UPDATED_EMAIL)
                .createdDate(UPDATED_CREATED_DATE)
                .active(UPDATED_ACTIVE);

        restSubscriberMockMvc.perform(put("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubscriber)))
            .andExpect(status().isOk());

        // Validate the Subscriber in the database
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeUpdate);
        Subscriber testSubscriber = subscriberList.get(subscriberList.size() - 1);
        assertThat(testSubscriber.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSubscriber.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSubscriber.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    public void updateNonExistingSubscriber() throws Exception {
        int databaseSizeBeforeUpdate = subscriberRepository.findAll().size();

        // Create the Subscriber

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubscriberMockMvc.perform(put("/api/subscribers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriber)))
            .andExpect(status().isCreated());

        // Validate the Subscriber in the database
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteSubscriber() throws Exception {
        // Initialize the database
        subscriberRepository.save(subscriber);
        int databaseSizeBeforeDelete = subscriberRepository.findAll().size();

        // Get the subscriber
        restSubscriberMockMvc.perform(delete("/api/subscribers/{id}", subscriber.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        assertThat(subscriberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subscriber.class);
    }
}

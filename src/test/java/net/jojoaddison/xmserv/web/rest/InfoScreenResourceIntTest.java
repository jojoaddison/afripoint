package net.jojoaddison.xmserv.web.rest;

import net.jojoaddison.xmserv.AfripointApp;

import net.jojoaddison.xmserv.domain.InfoScreen;
import net.jojoaddison.xmserv.repository.InfoScreenRepository;
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
 * Test class for the InfoScreenResource REST controller.
 *
 * @see InfoScreenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AfripointApp.class)
public class InfoScreenResourceIntTest {

    private static final String DEFAULT_SCREEN_URL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SCREEN_URL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private InfoScreenRepository infoScreenRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restInfoScreenMockMvc;

    private InfoScreen infoScreen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            InfoScreenResource infoScreenResource = new InfoScreenResource(infoScreenRepository);
        this.restInfoScreenMockMvc = MockMvcBuilders.standaloneSetup(infoScreenResource)
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
    public static InfoScreen createEntity() {
        InfoScreen infoScreen = new InfoScreen()
                .screenUrl(DEFAULT_SCREEN_URL)
                .caption(DEFAULT_CAPTION)
                .createdDate(DEFAULT_CREATED_DATE)
                .modifiedDate(DEFAULT_MODIFIED_DATE)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return infoScreen;
    }

    @Before
    public void initTest() {
        infoScreenRepository.deleteAll();
        infoScreen = createEntity();
    }

    @Test
    public void createInfoScreen() throws Exception {
        int databaseSizeBeforeCreate = infoScreenRepository.findAll().size();

        // Create the InfoScreen

        restInfoScreenMockMvc.perform(post("/api/info-screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoScreen)))
            .andExpect(status().isCreated());

        // Validate the InfoScreen in the database
        List<InfoScreen> infoScreenList = infoScreenRepository.findAll();
        assertThat(infoScreenList).hasSize(databaseSizeBeforeCreate + 1);
        InfoScreen testInfoScreen = infoScreenList.get(infoScreenList.size() - 1);
        assertThat(testInfoScreen.getScreenUrl()).isEqualTo(DEFAULT_SCREEN_URL);
        assertThat(testInfoScreen.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testInfoScreen.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInfoScreen.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testInfoScreen.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    public void createInfoScreenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infoScreenRepository.findAll().size();

        // Create the InfoScreen with an existing ID
        InfoScreen existingInfoScreen = new InfoScreen();
        existingInfoScreen.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoScreenMockMvc.perform(post("/api/info-screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingInfoScreen)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<InfoScreen> infoScreenList = infoScreenRepository.findAll();
        assertThat(infoScreenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkScreenUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = infoScreenRepository.findAll().size();
        // set the field null
        infoScreen.setScreenUrl(null);

        // Create the InfoScreen, which fails.

        restInfoScreenMockMvc.perform(post("/api/info-screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoScreen)))
            .andExpect(status().isBadRequest());

        List<InfoScreen> infoScreenList = infoScreenRepository.findAll();
        assertThat(infoScreenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllInfoScreens() throws Exception {
        // Initialize the database
        infoScreenRepository.save(infoScreen);

        // Get all the infoScreenList
        restInfoScreenMockMvc.perform(get("/api/info-screens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoScreen.getId())))
            .andExpect(jsonPath("$.[*].screenUrl").value(hasItem(DEFAULT_SCREEN_URL.toString())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    public void getInfoScreen() throws Exception {
        // Initialize the database
        infoScreenRepository.save(infoScreen);

        // Get the infoScreen
        restInfoScreenMockMvc.perform(get("/api/info-screens/{id}", infoScreen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(infoScreen.getId()))
            .andExpect(jsonPath("$.screenUrl").value(DEFAULT_SCREEN_URL.toString()))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    public void getNonExistingInfoScreen() throws Exception {
        // Get the infoScreen
        restInfoScreenMockMvc.perform(get("/api/info-screens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInfoScreen() throws Exception {
        // Initialize the database
        infoScreenRepository.save(infoScreen);
        int databaseSizeBeforeUpdate = infoScreenRepository.findAll().size();

        // Update the infoScreen
        InfoScreen updatedInfoScreen = infoScreenRepository.findOne(infoScreen.getId());
        updatedInfoScreen
                .screenUrl(UPDATED_SCREEN_URL)
                .caption(UPDATED_CAPTION)
                .createdDate(UPDATED_CREATED_DATE)
                .modifiedDate(UPDATED_MODIFIED_DATE)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restInfoScreenMockMvc.perform(put("/api/info-screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInfoScreen)))
            .andExpect(status().isOk());

        // Validate the InfoScreen in the database
        List<InfoScreen> infoScreenList = infoScreenRepository.findAll();
        assertThat(infoScreenList).hasSize(databaseSizeBeforeUpdate);
        InfoScreen testInfoScreen = infoScreenList.get(infoScreenList.size() - 1);
        assertThat(testInfoScreen.getScreenUrl()).isEqualTo(UPDATED_SCREEN_URL);
        assertThat(testInfoScreen.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testInfoScreen.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInfoScreen.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testInfoScreen.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    public void updateNonExistingInfoScreen() throws Exception {
        int databaseSizeBeforeUpdate = infoScreenRepository.findAll().size();

        // Create the InfoScreen

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInfoScreenMockMvc.perform(put("/api/info-screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoScreen)))
            .andExpect(status().isCreated());

        // Validate the InfoScreen in the database
        List<InfoScreen> infoScreenList = infoScreenRepository.findAll();
        assertThat(infoScreenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteInfoScreen() throws Exception {
        // Initialize the database
        infoScreenRepository.save(infoScreen);
        int databaseSizeBeforeDelete = infoScreenRepository.findAll().size();

        // Get the infoScreen
        restInfoScreenMockMvc.perform(delete("/api/info-screens/{id}", infoScreen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InfoScreen> infoScreenList = infoScreenRepository.findAll();
        assertThat(infoScreenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoScreen.class);
    }
}

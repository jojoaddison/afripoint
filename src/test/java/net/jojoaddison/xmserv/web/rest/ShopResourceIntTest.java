package net.jojoaddison.xmserv.web.rest;

import net.jojoaddison.xmserv.AfripointApp;

import net.jojoaddison.xmserv.domain.Shop;
import net.jojoaddison.xmserv.repository.ShopRepository;
import net.jojoaddison.xmserv.service.ShopService;
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

import net.jojoaddison.xmserv.domain.enumeration.ShopCategory;
/**
 * Test class for the ShopResource REST controller.
 *
 * @see ShopResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AfripointApp.class)
public class ShopResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTS = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTS = "BBBBBBBBBB";

    private static final String DEFAULT_PHYSICAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PHYSICAL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_VIRTUAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_VIRTUAL_ADDRESS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ShopCategory DEFAULT_CATEGORY = ShopCategory.CLOTHING;
    private static final ShopCategory UPDATED_CATEGORY = ShopCategory.ACCESSORIES;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restShopMockMvc;

    private Shop shop;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShopResource shopResource = new ShopResource(shopService);
        this.restShopMockMvc = MockMvcBuilders.standaloneSetup(shopResource)
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
    public static Shop createEntity() {
        Shop shop = new Shop()
                .name(DEFAULT_NAME)
                .owner(DEFAULT_OWNER)
                .products(DEFAULT_PRODUCTS)
                .physicalAddress(DEFAULT_PHYSICAL_ADDRESS)
                .virtualAddress(DEFAULT_VIRTUAL_ADDRESS)
                .createdDate(DEFAULT_CREATED_DATE)
                .modifiedDate(DEFAULT_MODIFIED_DATE)
                .category(DEFAULT_CATEGORY);
        return shop;
    }

    @Before
    public void initTest() {
        shopRepository.deleteAll();
        shop = createEntity();
    }

    @Test
    public void createShop() throws Exception {
        int databaseSizeBeforeCreate = shopRepository.findAll().size();

        // Create the Shop

        restShopMockMvc.perform(post("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shop)))
            .andExpect(status().isCreated());

        // Validate the Shop in the database
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeCreate + 1);
        Shop testShop = shopList.get(shopList.size() - 1);
        assertThat(testShop.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShop.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testShop.getProducts()).isEqualTo(DEFAULT_PRODUCTS);
        assertThat(testShop.getPhysicalAddress()).isEqualTo(DEFAULT_PHYSICAL_ADDRESS);
        assertThat(testShop.getVirtualAddress()).isEqualTo(DEFAULT_VIRTUAL_ADDRESS);
        assertThat(testShop.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testShop.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testShop.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    public void createShopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shopRepository.findAll().size();

        // Create the Shop with an existing ID
        Shop existingShop = new Shop();
        existingShop.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restShopMockMvc.perform(post("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingShop)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllShops() throws Exception {
        // Initialize the database
        shopRepository.save(shop);

        // Get all the shopList
        restShopMockMvc.perform(get("/api/shops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shop.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())))
            .andExpect(jsonPath("$.[*].products").value(hasItem(DEFAULT_PRODUCTS.toString())))
            .andExpect(jsonPath("$.[*].physicalAddress").value(hasItem(DEFAULT_PHYSICAL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].virtualAddress").value(hasItem(DEFAULT_VIRTUAL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }

    @Test
    public void getShop() throws Exception {
        // Initialize the database
        shopRepository.save(shop);

        // Get the shop
        restShopMockMvc.perform(get("/api/shops/{id}", shop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shop.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()))
            .andExpect(jsonPath("$.products").value(DEFAULT_PRODUCTS.toString()))
            .andExpect(jsonPath("$.physicalAddress").value(DEFAULT_PHYSICAL_ADDRESS.toString()))
            .andExpect(jsonPath("$.virtualAddress").value(DEFAULT_VIRTUAL_ADDRESS.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    public void getNonExistingShop() throws Exception {
        // Get the shop
        restShopMockMvc.perform(get("/api/shops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateShop() throws Exception {
        // Initialize the database
        shopService.save(shop);

        int databaseSizeBeforeUpdate = shopRepository.findAll().size();

        // Update the shop
        Shop updatedShop = shopRepository.findOne(shop.getId());
        updatedShop
                .name(UPDATED_NAME)
                .owner(UPDATED_OWNER)
                .products(UPDATED_PRODUCTS)
                .physicalAddress(UPDATED_PHYSICAL_ADDRESS)
                .virtualAddress(UPDATED_VIRTUAL_ADDRESS)
                .createdDate(UPDATED_CREATED_DATE)
                .modifiedDate(UPDATED_MODIFIED_DATE)
                .category(UPDATED_CATEGORY);

        restShopMockMvc.perform(put("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShop)))
            .andExpect(status().isOk());

        // Validate the Shop in the database
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeUpdate);
        Shop testShop = shopList.get(shopList.size() - 1);
        assertThat(testShop.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShop.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testShop.getProducts()).isEqualTo(UPDATED_PRODUCTS);
        assertThat(testShop.getPhysicalAddress()).isEqualTo(UPDATED_PHYSICAL_ADDRESS);
        assertThat(testShop.getVirtualAddress()).isEqualTo(UPDATED_VIRTUAL_ADDRESS);
        assertThat(testShop.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testShop.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testShop.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    public void updateNonExistingShop() throws Exception {
        int databaseSizeBeforeUpdate = shopRepository.findAll().size();

        // Create the Shop

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShopMockMvc.perform(put("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shop)))
            .andExpect(status().isCreated());

        // Validate the Shop in the database
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteShop() throws Exception {
        // Initialize the database
        shopService.save(shop);

        int databaseSizeBeforeDelete = shopRepository.findAll().size();

        // Get the shop
        restShopMockMvc.perform(delete("/api/shops/{id}", shop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shop.class);
    }
}

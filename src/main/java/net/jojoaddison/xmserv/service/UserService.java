package net.jojoaddison.xmserv.service;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.coobird.thumbnailator.Thumbnails;
import net.jojoaddison.xmserv.domain.Authority;
import net.jojoaddison.xmserv.domain.User;
import net.jojoaddison.xmserv.repository.AuthorityRepository;
import net.jojoaddison.xmserv.repository.UserRepository;
import net.jojoaddison.xmserv.security.AuthoritiesConstants;
import net.jojoaddison.xmserv.security.SecurityUtils;
import net.jojoaddison.xmserv.service.dto.UserDTO;
import net.jojoaddison.xmserv.service.util.RandomUtil;
import net.jojoaddison.xmserv.service.util.Tools;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;
    
    private final Environment env;
    
    private final String USER_PHOTOS = "data/user-management/photos";
    private final String DATA = "data/";
    private final int HEIGHT = 120;
    private final int WIDTH = 120;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, Environment env) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.env = env;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                userRepository.save(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
       log.debug("Reset user password for reset key {}", key);

       return userRepository.findOneByResetKey(key)
            .filter(user -> {
                ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                return user.getResetDate().isAfter(oneDayAgo);
           })
           .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                userRepository.save(user);
                return user;
           });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                userRepository.save(user);
                return user;
            });
    }

    public User createUser(String login, String password, String firstName, String lastName, String email,
        String imageUrl, String langKey, String imageContentType, byte[] image) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setImageContentType(imageContentType);
        newUser.setImage(image);
        newUser.setImageUrl(imageUrl);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        newUser = convert(newUser);
        newUser = createThumbnail(newUser);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImage(userDTO.getImage());
        user.setImageContentType(userDTO.getImageContentType());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey("en"); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            userDTO.getAuthorities().forEach(
                authority -> authorities.add(authorityRepository.findOne(authority))
            );
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(ZonedDateTime.now());
        user.setActivated(true);
        user = convert(user);
        user = createThumbnail(user);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageContentType, byte[] image) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setImage(image);
            user.setImageContentType(imageContentType);
            user.setLangKey(langKey);
            user = convert(user);
            user = createThumbnail(user);
            userRepository.save(user);
            log.debug("Changed Information for Current User: {}", user);
        });
    }
    
    private User convert(User user){
    	if(user.getImage() != null){
			log.info("converting: {}", user);
    		String fileExt = user.getImageContentType().split("/")[1];
    		String root = env.getProperty("client.root");
			log.info("root path: {}", root);
    		String sep = Tools.getSeparator();
    		String userDir = Tools.removeSpaces(user.getLogin()).concat(sep);
    		String directory = USER_PHOTOS.concat(sep).concat(userDir).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
    		String fullPath = root.concat(directory).toLowerCase();
			log.info("before create full path: {}", fullPath);
    		try {
				fullPath = Tools.createDirectory(fullPath);
				if(fullPath != null){
					if(user.getImageUrl() != null){
						Tools.removeFile(root.concat(user.getImageUrl()));
					}
					log.info("full path: {}", fullPath);
					String url = directory.concat(sep).concat(Tools.getDate()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, user.getImage());
					Tools.setReadPermissions(root.concat(DATA));
					user.setImage(null);
					user.setImageUrl(url);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}
    	}   
    	return user;
    }

    public User createThumbnail(User user){
    	if(user.getThumbnail() == null && user.getImageUrl() != null){
    		String fileExt = user.getImageContentType().split("/")[1].toLowerCase();
    		String photoUrl = user.getImageUrl();
    		String thumbnail = photoUrl.substring(0, photoUrl.lastIndexOf('.')).concat("_thumb").concat(".").concat(fileExt);
    		user.setThumbnail(thumbnail);
    		String root = env.getProperty("client.root");
    		String thumbFilename = root.concat(thumbnail);
    		String photoFile = root.concat(photoUrl);
    		try {
				Thumbnails.of(new File(photoFile)).size(WIDTH, HEIGHT).outputQuality(0.7).outputFormat(fileExt).toFile(thumbFilename);
				Tools.setReadPermissions(root.concat(DATA));
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e.getMessage(), e.getCause());
			}
    	}
    	return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findOne(userDTO.getId()))
            .map(user -> {
                user.setLogin(userDTO.getLogin());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImage(userDTO.getImage());
                user.setImageContentType(userDTO.getImageContentType());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findOne)
                    .forEach(managedAuthorities::add);
                userRepository.save(convert(user));
                log.debug("Changed Information for Specific User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            log.debug("Changed password for User: {}", user);
        });
    }


    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public User getUserWithAuthorities(String id) {
        return userRepository.findOne(id);
    }

    public User getUserWithAuthorities() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
    }

	public List<User> findByAuthority(String authority) {
		return userRepository.findAllByAuthorities_Name(authority);
	}

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }
}

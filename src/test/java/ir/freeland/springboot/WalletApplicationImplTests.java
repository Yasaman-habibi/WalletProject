package ir.freeland.springboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ir.freeland.springboot.persistence.model.Account;
import ir.freeland.springboot.persistence.model.AccountDto;
import ir.freeland.springboot.persistence.model.Person;
import ir.freeland.springboot.persistence.model.PersonDetails;
import ir.freeland.springboot.persistence.repo.PersonRepository;
import ir.freeland.springboot.web.AccountController;
import ir.freeland.springboot.web.AccountService;
import ir.freeland.springboot.web.ChangePasswordDto;
import ir.freeland.springboot.web.PersonController;
import ir.freeland.springboot.web.PersonService;
import ir.freeland.springboot.web.PersonValidator;
import ir.freeland.springboot.web.TransactionService;
import ir.freeland.springboot.web.exception.ErrorResponseService;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
class WalletApplicationImplTests {
	
	 @Autowired
	    private MockMvc mockMvc;
	    
	    @MockBean
	    PersonRepository personRepository;
	    
	    @MockBean
	    PersonService personService;
	    
	    @MockBean
	    private AccountService accountService;
	    
	    @MockBean
	    private TransactionService transactionService;

	    @MockBean
	    private ErrorResponseService errorResponseService;
	    
	    @MockBean
	    PersonValidator personValidator;
	    
	    @Mock
	    private Authentication authentication;
	    
	    @InjectMocks
	    private AccountController accountController;
	    
	    @InjectMocks
	    private PersonController personController;

	    Person person;
	    PersonDetails personDetails;
	    
	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
	        SecurityContext securityContext = mock(SecurityContext.class);
	        when(securityContext.getAuthentication()).thenReturn(authentication);
	        SecurityContextHolder.setContext(securityContext);
	    }

	    
	    @Test
	    public void testGetAccountDetails() throws Exception {
	        String nationalId = "258741963";
	        Person person = new Person();
	        person.setNationalId(nationalId);

	        when(authentication.getName()).thenReturn(nationalId);
	        when(personService.findByNationalId(nationalId)).thenReturn(person);

	        mockMvc.perform(get("/api/persons/myInformation")
	                .contentType("application/json"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.nationalId").value(nationalId));
	    }

	    
	    @Test
	    public void testChangePassword() throws Exception {
	        String nationalId = "758469321";
	        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
	        changePasswordDto.setOldPassword("oldPassword");
	        changePasswordDto.setNewPassword("newPassword");

	        when(authentication.getName()).thenReturn(nationalId);

	        mockMvc.perform(put("/api/persons/mypassword")
	                .contentType("application/json")
	                .content("{\"oldPassword\":\"oldPassword\",\"newPassword\":\"newPassword\"}"))
	                .andExpect(status().isOk());

	        verify(personService, times(1)).changePassword(eq(nationalId), any(ChangePasswordDto.class));
	    }

	    @Test
	    public void testDeactivatePerson() throws Exception {
	        String nationalId = "987654321";

	        when(personService.deactivatePerson(nationalId)).thenReturn(true);

	        mockMvc.perform(delete("/api/persons/deleteAccount/{nationalId}", nationalId)
	                .contentType("application/json"))
	                .andExpect(status().isOk())
	                .andExpect(content().string(" account of person deleted successfully."));

	        when(personService.deactivatePerson(nationalId)).thenReturn(false);

	        mockMvc.perform(delete("/api/persons/deleteAccount/{nationalId}", nationalId)
	                .contentType("application/json"))
	                .andExpect(status().isNotFound())
	                .andExpect(content().string("person not found."));
	    }


	    @Test
	    public void testGetActiveWalletsForCurrentUser() throws Exception {
	        when(accountService.getActiveWalletsForCurrentUser()).thenReturn(Collections.emptyList());

	        mockMvc.perform(get("/api/account/myWallets")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$").isArray())
	                .andExpect(jsonPath("$").isEmpty());
	    }

	    
	    @Test
	    public void testGetBalance() throws Exception {
	        String accountNumber = "154362";
	        BigDecimal balance = BigDecimal.valueOf(1000);

	        when(accountService.getBalance(accountNumber)).thenReturn(balance);

	        mockMvc.perform(get("/api/account/balance/{accountNumber}", accountNumber)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$").value(balance));
	    }

	    
	    @Test
	    public void testCreateAccount() throws Exception {
	        AccountDto accountDto = new AccountDto();
	        accountDto.setAccountNumber("256341");
	        accountDto.setAccountBalance(BigDecimal.valueOf(1000));
	        Account account = new Account();
	        account.setId(1L);

	        when(accountService.create(any(AccountDto.class))).thenReturn(account);

	        mockMvc.perform(post("/api/account/newWallet")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content("{\"accountNumber\":\"123456\",\"accountBalance\":1000}"))
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.id").value(1L));
	    }

	    
	    @Test
	    public void testUpdateAccount() throws Exception {
	        String accountNumber = "123456";
	        AccountDto accountDto = new AccountDto();
	        accountDto.setAccountNumber(accountNumber);
	        accountDto.setAccountBalance(BigDecimal.valueOf(2000));
	        Account updatedAccount = new Account();
	        updatedAccount.setAccountNumber(accountNumber);
	        updatedAccount.setAccountBalance(BigDecimal.valueOf(2000));

	        when(accountService.updateAccount(eq(accountNumber), any(AccountDto.class))).thenReturn(updatedAccount);

	        mockMvc.perform(put("/api/account/updatewallet/{accountNumber}", accountNumber)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content("{\"accountNumber\":\"123456\",\"accountBalance\":2000}"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.accountNumber").value(accountNumber))
	                .andExpect(jsonPath("$.accountBalance").value(2000));
	    }
	}

		
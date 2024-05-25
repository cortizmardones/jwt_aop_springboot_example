package com.example.demo.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.dto.User;
import com.example.demo.dto.ValidTokenResponse;
import com.example.demo.feignclient.TokenFeignClient;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

@SpringBootTest
public class UserServiceImplTest {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private Firestore firebase;
	
	@MockBean
	private TokenFeignClient tokenFeignClient;
	
	
	@Test
	void saveUser() throws InterruptedException, ExecutionException, InstantiationException, IllegalAccessException {
		
        Map<String, Object> docData = new HashMap<>();
        docData.put("rut", "11111111-1");
        docData.put("name", "dummyName");
        docData.put("lastName", "dummyLastName");
        docData.put("age", "30");
        docData.put("active", true);
        
        // Mocks pruebas firebase.
        WriteResult writeResult = mock(WriteResult.class);
		ApiFuture<WriteResult> apiFuture = mock(ApiFuture.class);
        CollectionReference collectionReference = mock(CollectionReference.class);
        DocumentReference documentReference = mock(DocumentReference.class);
        
        when(apiFuture.get()).thenReturn(writeResult);
        com.google.cloud.Timestamp fakeTimestamp = com.google.cloud.Timestamp.of(new Date());
        when(writeResult.getUpdateTime()).thenReturn(fakeTimestamp);

        when(firebase.collection("users")).thenReturn(collectionReference);
        when(collectionReference.document("11111111-1")).thenReturn(documentReference);
        when(documentReference.set(docData)).thenReturn(apiFuture);
        
		when(tokenFeignClient.validToken("Bearer token1234")).thenReturn(ValidTokenResponse.builder().tokenType("JWT").isValid(true).errorMessage("").build());
		
		userService.saveUser(User.builder().name("dummyName").lastName("dummyLastName").age("30").rut("11111111-1").build(), "Bearer token1234");
		
	}

}

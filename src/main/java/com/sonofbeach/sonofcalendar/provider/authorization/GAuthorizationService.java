package com.sonofbeach.sonofcalendar.provider.authorization;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Person;
import com.sonofbeach.sonofcalendar.database.UserProvider;
import com.sonofbeach.sonofcalendar.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RestController
public class GAuthorizationService {

	private final String APPLICATION_NAME = "Putin";
	private final String CLIENT_ID = "1015847485708-9quajuechksb90314nhm42l392r3dkv1.apps.googleusercontent.com";
	private final String CLIENT_SECRET = "oqQfVqdYMewCEeMR7PVMMa3C";

    private final List<String> SCOPE = Arrays.asList("https://picasaweb.google.com/data/","https://www.googleapis.com/auth/calendar",
            PlusScopes.USERINFO_EMAIL, PlusScopes.USERINFO_PROFILE, PlusScopes.PLUS_ME, PlusScopes.PLUS_LOGIN);;
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private final String REDIRECT_URI = "http://localhost:8080/gAuthorize";
	private final VerificationCodeReceiver receiver = new LocalServerReceiver();
    private final UserProvider userProvider;

    @Autowired
	public GAuthorizationService(UserProvider userProvider){
        this.userProvider = userProvider;
	}

	private AuthorizationCodeFlow initializeFlow() throws IOException {
		return new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE)
                .setAccessType("offline")
                .build();
	}

    public String checkGoogleAuthorization(String user) throws IOException {
	    if(getFreshCredential(user) == null){
            return initializeFlow()
                    .newAuthorizationUrl()
                    .setRedirectUri(REDIRECT_URI)
                    .setState(user)
                    .build();
        }
        else
            return "success";
    }

    @RequestMapping("/gAuthorize")
    public void generateCredentialFromCode(HttpServletResponse response, String code, String state) throws IOException {
        try {
            AuthorizationCodeFlow authorizationCodeFlow = initializeFlow();
            TokenResponse tokenResponse = authorizationCodeFlow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
            //must be changed for multiple users, variable "state" contains the username
            User user = userProvider.getUser();
            user.getGoogleCredentials().setRefreshToken(tokenResponse.getRefreshToken());
            user.getGoogleCredentials().setAccessToken(tokenResponse.getAccessToken());
            userProvider.setUser(user);
        } finally {
            receiver.stop();
        }
        response.sendRedirect("http://localhost:8080/index.html");


    }

    public String getEmail(String user)  {
        Plus plus = new Plus.Builder(HTTP_TRANSPORT, JSON_FACTORY, getFreshCredential(user))
                .setApplicationName(APPLICATION_NAME).build();
        try {
            Person mePerson = plus.people().get("me").execute();
            List<Person.Emails> emails = mePerson.getEmails();
            for(Person.Emails email: emails){
                if(email.getType().equals("account")){
                    return email.getValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Credential getFreshCredential(String username){
        User user = userProvider.getUser();
        GoogleCredential credential = null;
        if(user.getGoogleCredentials().getRefreshToken()!=null && !user.getGoogleCredentials().getRefreshToken().equals("")){
            credential = new GoogleCredential.Builder()
                    .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
                    .setJsonFactory(JSON_FACTORY)
                    .setTransport(HTTP_TRANSPORT)
                    .build()
                    .setRefreshToken(user.getGoogleCredentials().getRefreshToken())
                    .setAccessToken(user.getGoogleCredentials().getAccessToken());
            try {
                credential.refreshToken();
                user.getGoogleCredentials().setAccessToken(credential.getAccessToken());
                userProvider.setUser(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return credential;
    }

	public JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}

	public HttpTransport getHTTP_TRANSPORT() {
		return HTTP_TRANSPORT;
	}

	public String getAPPLICATION_NAME() {
		return APPLICATION_NAME;
	}
}
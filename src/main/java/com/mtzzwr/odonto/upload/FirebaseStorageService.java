package com.mtzzwr.odonto.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseStorageService {
	
	@PostConstruct
	public void init() throws IOException {
		if(FirebaseApp.getApps().isEmpty()) {
			InputStream firebaseDataAuth = FirebaseStorageService.class.getResourceAsStream("/firebase-account-key.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(firebaseDataAuth))
					.setStorageBucket("odonto-inf4.appspot.com")
					.setDatabaseUrl("https://odonto-inf4.firebaseio.com")
					.build();
			
			FirebaseApp.initializeApp(options);
		}
	}
	
	public String upload(UploadInput uploadInput) {
		
		Bucket bucket = StorageClient.getInstance().bucket();
		
		byte[] bytes = Base64.getDecoder().decode(uploadInput.getBase64());
		
		String filename = uploadInput.getFilename();
		String mimetype = uploadInput.getMimetype();
		
		Blob file = bucket.create(filename, bytes, mimetype);
		file.createAcl(Acl.of(User.ofAllUsers(), Acl.Role.READER));
		
		return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), filename);
		
	}
	
}

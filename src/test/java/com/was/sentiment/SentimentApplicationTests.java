package com.was.sentiment;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class SentimentApplicationTests {

	@Test
	void jasypt(){

		String url = "";
		String username = "";
		String password = "";

		String encryptUrl = jasyptEncrypt(url);
		String encryptUsername = jasyptEncrypt(username);
		String encryptPassword = jasyptEncrypt(password);

		System.out.println("encryptUrl : " + encryptUrl);
		System.out.println("encryptUsername : " + encryptUsername);
		System.out.println("encryptPassword" + encryptPassword);

		Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
}
	private String jasyptEncrypt(String input) {
		String key = ""; // key here
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(key);
		config.setPoolSize("1");
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setStringOutputType("base64");
		config.setKeyObtentionIterations("1000");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		encryptor.setConfig(config);

		return encryptor.encrypt(input);
	}


	private String jasyptDecryt(String input){
		String key = ""; // key here
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(key);
		config.setPoolSize("1");
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setStringOutputType("base64");
		config.setKeyObtentionIterations("1000");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		encryptor.setConfig(config);

		return encryptor.decrypt(input);

	}

}

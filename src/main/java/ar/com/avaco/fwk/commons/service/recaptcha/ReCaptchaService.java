package ar.com.avaco.fwk.commons.service.recaptcha;

public interface ReCaptchaService {
    
	boolean verifyRecaptcha(String response);

    String getReCaptchaSite();

    String getReCaptchaSecret();

}
package consent.receipt;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xdi2.core.ContextNode;
import xdi2.core.features.keys.Keys;
import xdi2.core.features.signatures.KeyPairSignature;
import xdi2.core.features.signatures.Signatures;
import xdi2.core.io.XDIWriter;
import xdi2.core.io.XDIWriterRegistry;
import xdi2.core.xri3.CloudNumber;

public class IssueConsentReceipt extends HttpServlet {

	private static final long serialVersionUID = 6222742990137314344L;

	private static String PRIVATEKEYSTRING = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCeRGpk3kz50vIZXIcwLbeN471U4NS09ZtJ4hB5ymNiEd47P0jHo9ZzudjF2nQH0JScK5hDw8E3PpC2BDS5roJI3OdL9qCDDZJKUK3fgV1rtZeskzi5oJdRxWAvdD9E8NqQO4qvEY+D4qDLJ81i9sBici1HX4BX78oipCx+KFhiqI6EgQ6vPIG9M4u2PHRaVEhcKP/TU/hcvcJaBFjFAf3ntX2RS3lgw12dfR9u8vYG2FH8MkSREjefw8ZJojtLcGtUN0gL+O456zXo4I/N9b5cFEMenPqQD5uRY0uKTN9sdF/7f657e1ySnzEYMXfyopRm4txfDpr1DsymhLu6pQR1AgMBAAECggEAU8a9VuWBc6Cg/KHJAczBY/9QYlSc46k66TIcLIvgbbIto8kM+niUdS/EOn83ekeD0uMUt5IwfdDFg0PYsQ1Z873TRZeoQZb2gBYS48OzsucuAXDwzvnd/6/uCVCMiS7BUyXhdGhkQpi9KqzIAIHDNplftG8uHd5Vbl7fGmy61IW0GWE1Ogchk29ro+Uiqti56cblxjZv/SBFXyEMWyEFfH67uHDX7JsVuR4KnHVQGg5pTePgsOsDTIVN8bAQV/QEfxTzbHxT0FV2G8It40AwiVYCCnDqRygOqGcM8g4Oa9AzUCAea5oRKyVT/2x0Da+sLCNFhwzYocTpyHO2bj6KQQKBgQDkRVkZLMYe1Lc9u8M/K0kFniU79XQ1ZOhGLO1uln0tq+7TcQeWP2cFXkkf3AB22kVy0eJSZdmd7ltY+D985DNfHbGlfnNy0Od5p8MRtqbEtHU72k/1pfG4zDB046lrOq7vSstjlqVbcrgEj76sCxMkxAf7nPqDl5AkcrPpLz4kMQKBgQCxfiFC6wxtj1PGtCJkVMPHI+75IXIs7N29Lbp59DYKcE0dxJQzyqb8OzDA3gTQ1vBfuF9ZlfO5+0HBXqdk+MXlYbQC0Hgwgu3cBW2e/ibEKEj3dOydx2xtMLvfPWSZhydz8xYjzLlHJ2WY7m3MDvfv84BmsytU2n+VMLZy/vTnhQKBgHGWPG61g+RljsTuQIqXXF+qQPbj/a5wBtCcnI0R3zoxusaGEPNnmzjloNd0ntqFZFdtLwQ3YCEhMV1FMjpQR2vGF9a05He2kehMXVncHIH3FfrxeCZRK8X7/QS9IFmWOuQhwLFOjfvCSuUiLgvn+t+pmBtREqsWaQhp9zJKas6RAoGAcLoRt+V+nCq2Y3HOkfg/6z9ILFPwptW9MsRTNddDZzfR0oEiOw6PJY0WNrgLSPPp8HExpb0V8V3BcmWCXR+e2ENIR61NWEofdWjBKYRy7IxFY1yQVtjsZSDZgTwFo3hyNzMBMbV5StabsRhlBX/QOkZlm1RZxeeLth4dEkYZFt0CgYEAyNZcuXEOuipZfFRmJv4toJH1PqkxA3JuWi0N8vSP2XeIw3JGeJxXg1paQYFLTb3NRewwjNnup9Phhzqlz0oQocIyi+gpsjXAgi81aqRFQ41KRINtwFz7Lj5CiYIKYBERMI/t2sA7Enq35ePeih5FNO3MCFfOCD3wTWFAj3rAwhc=";
	private static PrivateKey PRIVATEKEY;

	public IssueConsentReceipt() {

		try {

			PRIVATEKEY = Keys.privateKeyFromPrivateKeyString(PRIVATEKEYSTRING);
		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// input

		String dataSubject = request.getParameter("dataSubject");

		// create receipt

		ConsentReceipt consentReceipt = new ConsentReceipt();
		consentReceipt.setDataSubject(CloudNumber.create(dataSubject));
		consentReceipt.setDataController(CloudNumber.create("[+]!:uuid:9999"));
		consentReceipt.setLocationDigital(Boolean.TRUE);
		consentReceipt.setLocationUri(URI.create(request.getRequestURI()));
		consentReceipt.setPurposes(new String[] {
				"We need to process your payment.",
				"We need to be able to deliver your product.",
				"We need your data to prevent fraud.",
				"We will advertise to you."
		});
		consentReceipt.setTimestamp(new Date());
		consentReceipt.setPrivacyPolicyUri(URI.create("https://amazon-respect.com/privacypolicy.html"));
		consentReceipt.setCookiePolicyUri(URI.create("https://amazon-respect.com/cookiepolicy.html"));
		consentReceipt.setTosUri(URI.create("https://amazon-respect.com/tos.html"));
		consentReceipt.setShortPrivacyNoticeUri(URI.create("https://amazon-respect.com/shortprivacynotice.html"));
		consentReceipt.setDnt(Boolean.TRUE);
		consentReceipt.setSensitive(Boolean.FALSE);
		consentReceipt.setJurisdiction("DE");

		ContextNode contextNode = consentReceipt.toXdi();

		// sign receipt

		KeyPairSignature signature = (KeyPairSignature) Signatures.createSignature(contextNode, KeyPairSignature.DIGEST_ALGORITHM_SHA, 256, KeyPairSignature.KEY_ALGORITHM_RSA, 2048, true);

		try {

			signature.sign(PRIVATEKEY);
		} catch (GeneralSecurityException ex) {

			throw new ServletException("Cannot sign consent receipt: " + ex.getMessage(), ex);
		}

		// return receipt

		XDIWriter xdiWriter = XDIWriterRegistry.forFormat("XDI DISPLAY", null);
		xdiWriter.write(contextNode.getGraph(), response.getOutputStream());
	}
}

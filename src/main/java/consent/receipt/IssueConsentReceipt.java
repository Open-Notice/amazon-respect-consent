package consent.receipt;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xdi2.core.ContextNode;
import xdi2.core.features.signatures.KeyPairSignature;
import xdi2.core.features.signatures.Signatures;
import xdi2.core.io.XDIWriter;
import xdi2.core.io.XDIWriterRegistry;
import xdi2.core.xri3.CloudNumber;
import consent.datacontroller.AmazonRespectDataController;
import consent.datacontroller.DataController;

public class IssueConsentReceipt extends HttpServlet {

	private static final long serialVersionUID = 6222742990137314344L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// data subject

		String dataSubject = request.getParameter("dataSubject");

		// data controller
		
		DataController dataController = new AmazonRespectDataController();
		
		// create receipt

		ConsentReceipt consentReceipt = new ConsentReceipt();
		consentReceipt.setDataSubject(CloudNumber.create(dataSubject));
		consentReceipt.setDataController(dataController);
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

			signature.sign(dataController.getPrivateKey());
		} catch (GeneralSecurityException ex) {

			throw new ServletException("Cannot sign consent receipt: " + ex.getMessage(), ex);
		}

		// return receipt

		XDIWriter xdiWriter = XDIWriterRegistry.forFormat("XDI DISPLAY", null);
		xdiWriter.write(contextNode.getGraph(), response.getOutputStream());
	}
}

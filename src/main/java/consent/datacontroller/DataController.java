package consent.datacontroller;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

import xdi2.core.xri3.CloudName;
import xdi2.core.xri3.CloudNumber;

public interface DataController {

	public CloudName getCloudName();
	public CloudNumber getCloudNumber();
	public String getXdiEndpointUri();
	public PrivateKey getPrivateKey() throws GeneralSecurityException;
	public PublicKey getPublicKey() throws GeneralSecurityException;
}

package consent.receipt;

import java.net.URI;
import java.util.Date;

import xdi2.core.ContextNode;
import xdi2.core.Graph;
import xdi2.core.features.nodetypes.XdiAttributeCollection;
import xdi2.core.features.nodetypes.XdiEntityCollection;
import xdi2.core.features.nodetypes.XdiEntityMember;
import xdi2.core.features.nodetypes.XdiInnerRoot;
import xdi2.core.features.nodetypes.XdiLocalRoot;
import xdi2.core.features.timestamps.Timestamps;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.xri3.CloudNumber;
import xdi2.core.xri3.XDI3Segment;

public class ConsentReceipt {

	private CloudNumber dataSubject;
	private CloudNumber dataController;
	private Boolean locationDigital; 
	private URI locationUri;
	private String[] purposes;
	private Date timestamp;
	private URI privacyPolicyUri;
	private URI cookiePolicyUri;
	private URI tosUri;
	private URI shortPrivacyNoticeUri;
	private Boolean dnt;
	private Boolean sensitive;
	private String jurisdiction;

	private ContextNode contextNode;

	public ConsentReceipt() {

		this.contextNode = null;
	}

	public ContextNode toXdi() {

		if (this.contextNode == null) {

			Graph graph = MemoryGraphFactory.getInstance().openGraph();

			XdiInnerRoot innerRoot = XdiLocalRoot.findLocalRoot(graph).getInnerRoot(this.dataSubject.getXri(), this.dataController.getXri(), true);
			XdiEntityCollection receipts = innerRoot.getXdiEntityCollection(XDI3Segment.create("[#receipt]"), true);
			XdiEntityMember receipt = receipts.setXdiMemberUnordered(null);

			if (this.locationDigital != null)
				receipt.getXdiAttributeSingleton(XDI3Segment.create("<#location><#digital>"), true).getXdiValue(true).setLiteralBoolean(this.locationDigital);

			if (this.locationUri != null)
				receipt.getXdiAttributeSingleton(XDI3Segment.create("<#location><$uri>"), true).getXdiValue(true).setLiteralString(this.locationUri.toString());

			if (this.purposes != null) {

				XdiAttributeCollection purposes = receipt.getXdiAttributeCollection(XDI3Segment.create("[<#purpose>]"), true);

				for (String purpose : this.purposes)
					purposes.setXdiMemberOrdered(-1).getXdiValue(true).setLiteralString(purpose);
			}

			if (this.timestamp != null)
				Timestamps.setContextNodeTimestamp(receipt.getContextNode(), this.timestamp);

			if (this.privacyPolicyUri != null)
				receipt.getXdiAttributeSingleton(XDI3Segment.create("<#privacy><#policy><$uri>"), true).getXdiValue(true).setLiteralString(this.privacyPolicyUri.toString());

			if (this.cookiePolicyUri != null)
				receipt.getXdiAttributeSingleton(XDI3Segment.create("<#cookie><#policy><$uri>"), true).getXdiValue(true).setLiteralString(this.cookiePolicyUri.toString());

			if (this.tosUri != null)
				receipt.getXdiAttributeSingleton(XDI3Segment.create("<#tos><$uri>"), true).getXdiValue(true).setLiteralString(this.tosUri.toString());

			if (this.shortPrivacyNoticeUri != null)
				receipt.getXdiAttributeSingleton(XDI3Segment.create("<#short><#privacy><#notice><$uri>"), true).getXdiValue(true).setLiteralString(this.shortPrivacyNoticeUri.toString());

			if (this.dnt != null)
				receipt.getXdiAttributeSingleton(XDI3Segment.create("<#dnt>"), true).getXdiValue(true).setLiteralBoolean(this.dnt);

			if (this.sensitive != null)
				receipt.getXdiAttributeSingleton(XDI3Segment.create("<#sensitive>"), true).getXdiValue(true).setLiteralBoolean(this.sensitive);

			if (this.jurisdiction != null)
				receipt.getXdiAttributeSingleton(XDI3Segment.create("<#jurisdiction>"), true).getXdiValue(true).setLiteralString(this.jurisdiction.toString());

			this.contextNode = receipt.getContextNode();
		}

		return this.contextNode;
	}

	public CloudNumber getDataSubject() {
		return dataSubject;
	}

	public void setDataSubject(CloudNumber dataSubject) {
		this.dataSubject = dataSubject;
	}

	public CloudNumber getDataController() {
		return dataController;
	}

	public void setDataController(CloudNumber dataController) {
		this.dataController = dataController;
	}

	public Boolean getLocationDigital() {
		return locationDigital;
	}

	public void setLocationDigital(Boolean locationDigital) {
		this.locationDigital = locationDigital;
	}

	public URI getLocationUri() {
		return locationUri;
	}

	public void setLocationUri(URI locationUri) {
		this.locationUri = locationUri;
	}

	public String[] getPurposes() {
		return purposes;
	}

	public void setPurposes(String[] purposes) {
		this.purposes = purposes;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public URI getPrivacyPolicyUri() {
		return privacyPolicyUri;
	}

	public void setPrivacyPolicyUri(URI privacyPolicyUri) {
		this.privacyPolicyUri = privacyPolicyUri;
	}

	public URI getCookiePolicyUri() {
		return cookiePolicyUri;
	}

	public void setCookiePolicyUri(URI cookiePolicyUri) {
		this.cookiePolicyUri = cookiePolicyUri;
	}

	public URI getTosUri() {
		return tosUri;
	}

	public void setTosUri(URI tosUri) {
		this.tosUri = tosUri;
	}

	public URI getShortPrivacyNoticeUri() {
		return shortPrivacyNoticeUri;
	}

	public void setShortPrivacyNoticeUri(URI shortPrivacyNoticeUri) {
		this.shortPrivacyNoticeUri = shortPrivacyNoticeUri;
	}

	public Boolean getDnt() {
		return dnt;
	}

	public void setDnt(Boolean dnt) {
		this.dnt = dnt;
	}

	public Boolean getSensitive() {
		return sensitive;
	}

	public void setSensitive(Boolean sensitive) {
		this.sensitive = sensitive;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
}

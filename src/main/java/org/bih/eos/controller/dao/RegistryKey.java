package org.bih.eos.controller.dao;

import java.util.Objects;

public class RegistryKey {
	private String ehrId;
	private String sourceId;

	public RegistryKey(String ehrId, String sourceId) {
		this.ehrId = ehrId;
		this.sourceId = sourceId;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof RegistryKey)) return false;
		RegistryKey targetCompare = (RegistryKey) object;
		return Objects.equals(ehrId, targetCompare.ehrId) && Objects.equals(sourceId, targetCompare.sourceId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ehrId, sourceId);
	}

	public String getEhrId() {
		return ehrId;
	}

	public String getSourceId() {
		return sourceId;
	}
}

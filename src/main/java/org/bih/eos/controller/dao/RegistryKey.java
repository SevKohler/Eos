package org.bih.eos.controller.dao;

import java.util.Objects;

public class RegistryKey {
    private String ehrId;
    private String sourceId;

    public RegistryKey(String ehrId, String sourceId) {
        this.ehrId = ehrId;
        this.sourceId = sourceId;
    }

    // equals and hashCode (important for using in HashMap)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistryKey)) return false;
        RegistryKey that = (RegistryKey) o;
        return Objects.equals(ehrId, that.ehrId) && Objects.equals(sourceId, that.sourceId);
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

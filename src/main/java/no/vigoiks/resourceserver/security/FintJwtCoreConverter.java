package no.vigoiks.resourceserver.security;

import no.vigoiks.resourceserver.security.FintJwtDefaultConverter;

public class FintJwtCoreConverter extends FintJwtDefaultConverter {
    public FintJwtCoreConverter() {
        this.addMapping("fintAssetIDs", "ORGID_");
        this.addMapping("Roles", "ROLE_");
    }
}
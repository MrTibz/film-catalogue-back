package org.ano.app.config;


import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@SecurityScheme(
        securitySchemeName = "sessionAuth",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,  // Utilisation de In.HEADER au lieu de Parameter.In.HEADER
        apiKeyName = "X-Token"
)
public class OpenApiSecurityConfig {}

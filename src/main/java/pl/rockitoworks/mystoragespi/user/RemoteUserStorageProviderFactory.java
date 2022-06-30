package pl.rockitoworks.mystoragespi.user;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;
import pl.rockitoworks.mystoragespi.client.RemoteKeycloakClientProvider;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RemoteUserStorageProviderFactory implements UserStorageProviderFactory<RemoteUserStorageProvider> {

    private final ConcurrentMap<String, RemoteKeycloakClientProvider> remoteKeycloakClientCache
            = new ConcurrentHashMap<>();

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }


    @Override
    public RemoteUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new RemoteUserStorageProvider(session, model,this::createRestEasyClient, remoteKeycloakClientCache );
    }

    @Override
    public String getId() {
        return "keycloak-remote";
    }

    @Override
    public String getHelpText() {
        return "Remote Keycloak User Storage";
    }

    protected ResteasyClient createRestEasyClient(ComponentModel componentModel) {
        ResteasyClient client = new ResteasyClientBuilder()
                .connectionPoolSize(128)
                //.keyStore()
                //
                .build();

        return client;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {

        // this configuration is configurable in the admin-console
        List<ProviderConfigProperty> config = (List<ProviderConfigProperty>) ProviderConfigurationBuilder.create()
                .property()
                .name("authServerUrl")
                .label("Auth Server URL")
                .helpText("URL of the Keycloak Auth Server")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("http://localhost:8083/auth")
                .add()
                .property()
                .name("clientId")
                .label("Client ID")
                .helpText("Client ID")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("admin-cli")
                .add()
                .property()
                .name("clientSecret")
                .label("Client Secret")
                .helpText("Client Secret")
                .type(ProviderConfigProperty.PASSWORD)
                .defaultValue("")
                .add()
                .property()
                .name("username")
                .label("Username")
                .helpText("Username")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("")
                .add()
                .property()
                .name("password")
                .label("Password")
                .helpText("Password")
                .type(ProviderConfigProperty.PASSWORD)
                .defaultValue("");
        return config;
    }
}
